package com.github.component;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.component.ptr.DefaultHeaderView;
import com.github.component.ptr.DefaultRecyclerView;
import com.github.component.ptr.HeaderView;

/**
 * @author lotty
 */
public class SwipeLayout extends ViewGroup {

  private final static int MAX_HEADER_SIZE = 600;

  private final static int MAX_LOADING_OFFSET = 300;
  private final static int MAX_FOOTER_SIZE = 200;
  private final static int MIN_SLOT = 4;

  private final static int SCROLL_DURATION = 1000;

  /**
   * 手指滑动方向：初始
   */
  private final static int FINGER_ORIENTATION_FLAT = 0;
  /**
   * 手指滑动方向：向上
   */
  private final static int FINGER_ORIENTATION_UP = 1;
  /**
   * 手指滑动方向：向上
   */
  private final static int FINGER_ORIENTATION_DOWN = 2;

  /**
   * 初始状态
   */
  private final static int POSITION_INIT = 1;
  /**
   * 滑动距离小于{@link com.github.component.SwipeLayout#MAX_LOADING_OFFSET}
   */
  private final static int POSITION_LESS = 2;
  /**
   * 滑动距离等于{@link com.github.component.SwipeLayout#MAX_LOADING_OFFSET}且悬停
   */
  private final static int POSITION_LOADING = 3;
  /**
   * 滑动距离介于
   * {@link com.github.component.SwipeLayout#MAX_LOADING_OFFSET,com.github.component.SwipeLayout#MAX_HEADER_SIZE}之间
   */
  private final static int POSITION_EXTRA = 4;
  /**
   * 滑动距离等于{@link com.github.component.SwipeLayout#MAX_HEADER_SIZE}且悬停
   */
  private final static int POSITION_MAX = 5;

  private final static int STATE_INIT = 0;
  private final static int STATE_READY_START = 1;
  private final static int STATE_RUNNING = 2;
  private final static int STATE_READY_FINISH = 3;

  private FrameLayout body;
  private FrameLayout header;
  private FrameLayout footer;

  private RecyclerView mRecyclerView;

  private int mMinimumVelocity;
  private int mMaximumVelocity;
  private int mCurrentVelocity;
  private int mTouchSlot;
  private Scroller mScroller;
  private VelocityTracker mVelocityTracker;

  private HeaderView mHeaderView;

  private boolean isTouching;

  private boolean isRefreshing;
  private SwipeListener mListener;

  private float mLastMovePoint;

  /**
   * 当前位置
   */
  private int mCurPosition = POSITION_INIT;

  /**
   * 当前状态
   */
  private int mCurState = POSITION_INIT;

  private int mCurOrientation = FINGER_ORIENTATION_FLAT;

  private int mScrollDelta = 0;

  public SwipeLayout(Context context) {
    super(context);
    init();
  }

  public SwipeLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    ViewConfiguration configuration = ViewConfiguration.get(getContext());
    mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
    mTouchSlot = configuration.getScaledTouchSlop();
    Log.e("wh",
        "最大滑动速度:" + mMaximumVelocity + ",最小滑动速度:" + mMinimumVelocity + ",最小滑动识别距离:" + mTouchSlot);

    mScroller = new Scroller(getContext());
    mVelocityTracker = VelocityTracker.obtain();

    header = new FrameLayout(getContext());
    LayoutParams headerLp = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_HEADER_SIZE);
    header.setLayoutParams(headerLp);
    header.setClickable(false);
    header.setLongClickable(false);
    header.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    body = new FrameLayout(getContext());
    LayoutParams bodyLp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
    body.setLayoutParams(bodyLp);
    body.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

    footer = new FrameLayout(getContext());
    LayoutParams footerLp = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_FOOTER_SIZE);
    footer.setLayoutParams(footerLp);
    footer.setClickable(false);
    footer.setLongClickable(false);
    footer.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    defaultInit();
    addView(header, 0);
    addView(body, 1);
    addView(footer, 2);
  }

  private void defaultInit() {
    // header
    setHeader(new DefaultHeaderView(getContext()));
    // body
    mRecyclerView = new DefaultRecyclerView(getContext());
    mRecyclerView.requestDisallowInterceptTouchEvent(false);
    mRecyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    body.addView(mRecyclerView,
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
  }

  public void setHeader(HeaderView view) {
    mHeaderView = view;
    header.removeAllViews();
    header.addView(view.getView(),
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
  }

  public void setSwipeListener(SwipeListener listener) {
    this.mListener = listener;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // 修改控件的高度，将Footer隐藏到屏幕底部
    int mode = MeasureSpec.getMode(heightMeasureSpec);
    int size = MeasureSpec.getSize(heightMeasureSpec);

    int height = MeasureSpec.makeMeasureSpec(size + MAX_HEADER_SIZE, mode);

    super.onMeasure(widthMeasureSpec, height);
    int headerSpec = MeasureSpec.makeMeasureSpec(MAX_HEADER_SIZE, mode);

    header.measure(widthMeasureSpec, headerSpec);

    body.measure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override public boolean dispatchTouchEvent(MotionEvent event) {
    mVelocityTracker.addMovement(event);
    float rawY = event.getRawY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        mLastMovePoint = rawY;
        isTouching = true;
        break;
      case MotionEvent.ACTION_MOVE:
        float v = rawY - mLastMovePoint;
        if (Math.abs(v) > mTouchSlot) {
          scrollBy(0, (int) -v);
        }
        mLastMovePoint = rawY;
        isTouching = true;
        break;
      case MotionEvent.ACTION_UP:
        mVelocityTracker.computeCurrentVelocity(1000);
        mCurrentVelocity = (int) mVelocityTracker.getYVelocity();
        mLastMovePoint = 0;
        isTouching = false;
        scrollToPosition();
        break;
      case MotionEvent.ACTION_CANCEL:
        mLastMovePoint = 0;
        isTouching = false;
        break;
      default:
        break;
    }
    return super.dispatchTouchEvent(event);
  }

  /**
   * 所有滑动中的状态均在此处理
   */
  @Override
  public void computeScroll() {
    //手指已经离开，并且触发过差值器，则回弹到指定位置
    if (mScroller.computeScrollOffset()) {
      int currY = mScroller.getCurrY();
      int finalY = mScroller.getFinalY();
      Log.e("wh", "---" + currY);
      Log.e("wh", "--------------------" + finalY);
      //if (currY == finalY) {
      //  scrollTo(0, 0);
      //} else {
      //  scrollTo(0, -currY);
      //}

      scrollTo(0, -currY);
      postInvalidate();
    }
  }

  private void scrollToPosition() {
    int scrollY = getScrollY();
    Log.e("wh", "scrollY==" + scrollY + ",mCurrentVelocity==" + mCurrentVelocity);
    int dis = scrollY;
    if (scrollY < -MAX_HEADER_SIZE) {
      dis = scrollY + MAX_HEADER_SIZE;
    }
    mScroller.startScroll(0, Math.abs(scrollY), 0, dis, 1000);
    invalidate();
  }

  private void updateState(int scrollY) {
    if (scrollY >= 0) {
      mCurPosition = POSITION_INIT;
    } else if (scrollY > -MAX_LOADING_OFFSET) {
      mCurPosition = POSITION_LESS;
    } else if (scrollY == -MAX_LOADING_OFFSET) {
      mCurPosition = POSITION_LOADING;
    } else if (scrollY > -MAX_HEADER_SIZE) {
      mCurPosition = POSITION_EXTRA;
    } else {
      mCurPosition = POSITION_MAX;
    }
  }

  /**
   * @param start 当前位置的scrollY
   * @param end 目的位置的scrollY
   * @param duration 时长
   */
  private void smoothScroll(int start, int end, int duration) {
    int dis = end - start;
    //mScroller.startScroll(0, start, 0, dis, duration);
    Log.e("wh", "dis====" + dis);
    mScroller.fling(0, 0, 0, -500, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
    invalidate();
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return false;
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();
    int paddingTop = getPaddingTop();
    int paddingBottom = getPaddingBottom();
    int measuredHeight = getMeasuredHeight();

    header.layout(l + paddingLeft, -MAX_HEADER_SIZE, r - paddingRight, 0);

    body.layout(l + paddingLeft, paddingTop, r - paddingRight,
        measuredHeight - paddingBottom);
  }

  public RecyclerView getBody() {
    return mRecyclerView;
  }

  public void notifySwipeFinished() {
    if (isRefreshing) {
      smoothScroll(getScrollY(), 0, SCROLL_DURATION);
      mHeaderView.onRefreshFinish();
    }
  }

  public void notifySwipeStarted() {
    if (!isRefreshing) {
      smoothScroll(0, -MAX_LOADING_OFFSET, SCROLL_DURATION);
    }
  }

  public interface SwipeListener {

    void onRefreshing();
  }
}
