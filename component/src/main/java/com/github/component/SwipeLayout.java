package com.github.component;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
public class SwipeLayout extends LinearLayout {

  private final static int MAX_HEADER_SIZE = 800;

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
  private Scroller mScroller;

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
    setUp();
  }

  public SwipeLayout(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    setUp();
  }

  public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setUp();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    setUp();
  }

  private void setUp() {
    mScroller = new Scroller(getContext());
    setOrientation(LinearLayout.VERTICAL);
    header = new FrameLayout(getContext());
    LayoutParams headerLp = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_HEADER_SIZE);
    headerLp.topMargin = -MAX_HEADER_SIZE;
    header.setLayoutParams(headerLp);
    header.setClickable(false);

    body = new FrameLayout(getContext());
    LayoutParams bodyLp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
    bodyLp.weight = 1;
    body.setLayoutParams(bodyLp);

    footer = new FrameLayout(getContext());
    LayoutParams footerLp = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_FOOTER_SIZE);
    footer.setLayoutParams(footerLp);
    footer.setClickable(false);

    init();
    fill();
  }

  private void init() {
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

  private void fill() {
    addView(header, 0);
    addView(body, 1);
    addView(footer, 2);
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
    int measureSpec = MeasureSpec.makeMeasureSpec(size + MAX_FOOTER_SIZE, mode);
    super.onMeasure(widthMeasureSpec, measureSpec);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    //滑动偏移量。负值乡下，正值向上
    int scrollPix = getScrollY();
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (mCurPosition == POSITION_LOADING) {
        if (!isRefreshing) {
          mHeaderView.onRefreshStart();
          isRefreshing = true;
        }
      } else if (mCurPosition > POSITION_LOADING) {
        smoothScroll(scrollPix, -MAX_LOADING_OFFSET, SCROLL_DURATION);
      } else if (mCurPosition < POSITION_LOADING) {
        smoothScroll(scrollPix, 0, SCROLL_DURATION);
      }
    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
      int scrollTarget = scrollPix - mScrollDelta;
      if (scrollTarget < -MAX_HEADER_SIZE) {
        scrollTarget = -MAX_HEADER_SIZE;
      }
      if (scrollTarget > 0) {
        scrollTarget = 0;
      }
      // 执行滑动
      scrollTo(0, scrollTarget);
      // TODO: 2019-12-19下拉刷新过程中，如果ListView第一个可见元素不是第一个元素，此时仍然会先执行header
      //  的滑动，事实上，应该先执行ListView的滑动
    } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
      dispatchEvent(event);
    }
    return true;
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_MOVE) {
      float rawY = ev.getRawY();
      if (mLastMovePoint != 0) {
        if (rawY - mLastMovePoint > 0) {
          mCurOrientation = FINGER_ORIENTATION_DOWN;
        } else if (rawY - mLastMovePoint == 0) {
          mCurOrientation = FINGER_ORIENTATION_FLAT;
        } else {
          mCurOrientation = FINGER_ORIENTATION_UP;
        }
        int slot = (int) (rawY - mLastMovePoint);
        if (Math.abs(slot) > MIN_SLOT) {
          mScrollDelta = (int) (rawY - mLastMovePoint);
        }
      }
      mLastMovePoint = rawY;
    } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      isTouching = true;
    } else if (ev.getAction() == MotionEvent.ACTION_CANCEL) {
      mLastMovePoint = 0;
      mScrollDelta = 0;
      isTouching = false;
    } else if (ev.getAction() == MotionEvent.ACTION_UP) {
      mLastMovePoint = 0;
      mScrollDelta = 0;
      isTouching = false;
    }
    int action = ev.getAction();
    if (action == MotionEvent.ACTION_MOVE) {
      if (!mRecyclerView.canScrollVertically(-1) && mCurOrientation == FINGER_ORIENTATION_DOWN) {
        onTouchEvent(ev);
      }
    }
    super.dispatchTouchEvent(ev);
    return true;
  }

  /**
   * 所有滑动中的状态均在此处理
   */
  @Override
  public void computeScroll() {
    int scrollY = getScrollY();
    updateState(scrollY);
    if (mCurPosition != POSITION_LOADING) {
      isRefreshing = false;
    }
    if (!isRefreshing) {
      if (mCurState == STATE_INIT && mCurPosition == POSITION_EXTRA) {
        mCurState = STATE_READY_START;
        mHeaderView.onReadyToStart();
      } else if (mCurState == STATE_RUNNING && mCurPosition == POSITION_LESS) {
        mCurState = STATE_READY_FINISH;
        mHeaderView.onReadyToFinish();
      } else if (mCurState != STATE_INIT && mCurPosition == POSITION_INIT) {
        mCurState = STATE_INIT;
      }
      if (!isTouching) {
        if (mCurPosition == POSITION_LOADING) {
          mHeaderView.onRefreshStart();
          mCurState = STATE_RUNNING;
          isRefreshing = true;
        }
      }
    }
    //手指已经离开，并且触发过差值器，则回弹到指定位置
    if (!isTouching && !mScroller.isFinished() && mScroller.computeScrollOffset()) {
      scrollTo(0, mScroller.getCurrY());
      postInvalidate();
    }
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
    mScroller.startScroll(0, start, 0, dis, duration);
    invalidate();
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return false;
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

  private boolean dispatchEvent(MotionEvent event) {
    return getChildAt(1).dispatchTouchEvent(event);
  }

  public interface SwipeListener {

    void onRefreshing();
  }
}
