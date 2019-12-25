package com.github.component;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.github.component.ptr.BodyView;
import com.github.component.ptr.DefaultBodyView;
import com.github.component.ptr.DefaultHeaderView;
import com.github.component.ptr.HeaderView;

/**
 * @author lotty
 */
public class SwipeLayout extends ViewGroup {

  private final static int MAX_HEADER_SIZE = 400;
  private final static int MAX_SCROLL_SIZE = MAX_HEADER_SIZE * 2;
  private final static int MAX_FOOTER_SIZE = 200;
  private final static int SCROLL_DURATION = 1000;

  private final static int ORIENTATION_INIT = 0;
  private final static int ORIENTATION_DOWN = 1;
  private final static int ORIENTATION_UP = 2;

  private final static int STATE_INIT = 0;
  private final static int STATE_READY_START = 1;
  private final static int STATE_RUNNING = 2;
  private final static int STATE_READY_FINISH = 3;

  private FrameLayout body;
  private FrameLayout header;
  private FrameLayout footer;

  private int mMinimumVelocity;
  private int mMaximumVelocity;
  private int mCurrentVelocity;
  private int mTouchSlot;
  private Scroller mScroller;
  private VelocityTracker mVelocityTracker;

  private HeaderView mHeaderView;
  private BodyView mBodyView;

  private boolean isDragging;

  private SwipeListener mListener;

  private float mLastMovePoint;
  private float mTouchDownPoint;
  private int mDirection = ORIENTATION_INIT;

  /**
   * 当前状态
   */
  private int mCurState = STATE_INIT;

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
  public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int mode = MeasureSpec.getMode(heightMeasureSpec);
    int size = MeasureSpec.getSize(heightMeasureSpec);
    // 增加高度
    int height = MeasureSpec.makeMeasureSpec(size + MAX_HEADER_SIZE, mode);
    // 设置ViewGroupz自身的高度
    super.onMeasure(widthMeasureSpec, height);
    int headerSpec = MeasureSpec.makeMeasureSpec(MAX_HEADER_SIZE, mode);
    // 设置header的高度
    header.measure(widthMeasureSpec, headerSpec);
    // 设置body的高度
    body.measure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();
    int paddingTop = getPaddingTop();
    int paddingBottom = getPaddingBottom();
    int measuredHeight = getMeasuredHeight();

    // 相当于设置header margin = -MAX_HEADER_SIZE
    header.layout(l + paddingLeft, -MAX_HEADER_SIZE, r - paddingRight, 0);

    body.layout(l + paddingLeft, paddingTop, r - paddingRight,
        measuredHeight - paddingBottom);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    mVelocityTracker.addMovement(event);
    int scrollY = getScrollY();
    float rawY = event.getRawY();
    int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        mLastMovePoint = mTouchDownPoint = rawY;
        isDragging = true;
        break;
      case MotionEvent.ACTION_MOVE:
        //  body控件是否能够向下滑动
        boolean canScrollVertically = mBodyView.getView().canScrollVertically(-1);
        float v = rawY - mLastMovePoint;
        if (v > 0) {
          mDirection = ORIENTATION_DOWN;
        } else {
          mDirection = ORIENTATION_UP;
        }
        // 触发可识别的最小滑动距离
        if (Math.abs(rawY - mTouchDownPoint) > mTouchSlot) {

          if (mDirection == ORIENTATION_UP) {
            if (scrollY < 0) {
              boolean reachTop = scrollY + Math.abs(v) >= 0;
              float dis = reachTop ? -scrollY : Math.abs(v);
              scrollBy(0, (int) dis);
              mLastMovePoint = rawY;
              isDragging = true;
              if (reachTop) {
                // 重新定位Body的DownEvent,防止跳跃性的滑动
                MotionEvent obtain = MotionEvent.obtain(event);
                obtain.setAction(MotionEvent.ACTION_DOWN);
                dispatchEventToBody(obtain);
              }
              return true;
            }
          } else if (mDirection == ORIENTATION_DOWN && scrollY > -MAX_SCROLL_SIZE) {
            if (!canScrollVertically) {
              float dis = scrollY - Math.abs(v) > -MAX_SCROLL_SIZE ? -Math.abs(v)
                  : -MAX_SCROLL_SIZE - scrollY;
              Log.e("down", "dis-> " + dis + ",  scroll-> " + scrollY);
              scrollBy(0, (int) dis);
              mLastMovePoint = rawY;
              isDragging = true;
              return true;
            }
          }
        }
        mLastMovePoint = rawY;
        isDragging = true;
        dispatchEventToBody(event);
        break;
      case MotionEvent.ACTION_UP:
        mVelocityTracker.computeCurrentVelocity(SCROLL_DURATION);
        mCurrentVelocity = (int) mVelocityTracker.getYVelocity();
        mLastMovePoint = 0;
        isDragging = false;
        scrollToPosition();
        mTouchDownPoint = 0;
        break;
      case MotionEvent.ACTION_CANCEL:
        mLastMovePoint = 0;
        isDragging = false;
        mTouchDownPoint = 0;
        break;
      default:
        break;
    }
    // 分发事件
    if (action != MotionEvent.ACTION_MOVE) {
      dispatchEventToBody(event);
    }
    return true;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    Log.e("IN", "--------- . " + ev.getAction() + ",  ");
    return true;
  }

  @Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    Log.e("on", "-------> " + t);
    if (t - oldt < 0) {
      mDirection = ORIENTATION_DOWN;
    } else if (t - oldt > 0) {
      mDirection = ORIENTATION_UP;
    }
    if (isDragging) {
      if (mDirection == ORIENTATION_DOWN) {
        if (t <= -MAX_HEADER_SIZE && mCurState != STATE_READY_START) {
          mHeaderView.onReadyToStart();
          mCurState = STATE_READY_START;
        }
      } else {
        if (t > -MAX_HEADER_SIZE && mCurState == STATE_RUNNING) {
          mHeaderView.onReadyToFinish();
          mCurState = STATE_READY_FINISH;
        }
      }
    } else {
      if (mDirection == ORIENTATION_UP) {
        if (t <= -MAX_HEADER_SIZE && mCurState != STATE_RUNNING) {
          mHeaderView.onStart();
          if (mListener != null) {
            mListener.onRefreshing();
          }
          mCurState = STATE_RUNNING;
        } else if (t > -MAX_HEADER_SIZE && mCurState == STATE_RUNNING) {
          mHeaderView.onReadyToFinish();
          mCurState = STATE_READY_FINISH;
        }
      } else if (t <= -MAX_HEADER_SIZE && mCurState != STATE_RUNNING) {
        mHeaderView.onStart();
        if (mListener != null) {
          mListener.onRefreshing();
        }
        mCurState = STATE_RUNNING;
      }
    }
    if (t == 0) {
      mCurState = STATE_INIT;
    }
  }

  /**
   * 所有滑动中的状态均在此处理
   */
  @Override
  public void computeScroll() {
    //手指已经离开，并且触发过差值器，则回弹到指定位置
    if (!isDragging && mScroller.computeScrollOffset()) {
      int currY = mScroller.getCurrY();
      scrollTo(0, currY);
      postInvalidate();
    }
  }

  private void init() {
    ViewConfiguration configuration = ViewConfiguration.get(getContext());
    mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
    mTouchSlot = configuration.getScaledTouchSlop();
    Log.e("component",
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
    body.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));

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
    setBody(new DefaultBodyView(getContext()));
  }

  public View getHeader() {
    return mHeaderView != null ? mHeaderView.getView() : null;
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

  private void scrollToPosition() {
    int scrollY = getScrollY();
    if (scrollY < -MAX_HEADER_SIZE) {
      smoothScroll(scrollY, -MAX_HEADER_SIZE, SCROLL_DURATION);
    } else {
      smoothScroll(scrollY, 0, SCROLL_DURATION);
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

  public View getBody() {
    return mBodyView != null ? mBodyView.getView() : null;
  }

  public void setBody(BodyView view) {
    mBodyView = view;
    body.removeAllViews();
    body.addView(view.getView(),
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
  }

  public void notifySwipeFinished() {
    Log.e("component", "notifySwipeFinished -> " + mCurState);
    if (mCurState == STATE_RUNNING) {
      smoothScroll(getScrollY(), 0, SCROLL_DURATION);
    }
  }

  public void notifySwipeStarted() {
    Log.e("component", "notifySwipeStarted -> " + mCurState);
    if (mCurState != STATE_RUNNING) {
      smoothScroll(0, -MAX_HEADER_SIZE, SCROLL_DURATION);
    }
  }

  /**
   * 待解决问题：滑动后，body接收到点击事件时，真实的点击位置跟ITEMd的位置不匹配。是由于BODY滑动后位置发生改变
   * 解决办法：刷新中，禁止点击
   * @param event
   */
  private void dispatchEventToBody(MotionEvent event) {
    float evX = event.getRawX();
    float evY = event.getRawY();
    int[] location = new int[2];
    body.getLocationOnScreen(location);
    float x = location[0];
    float y = location[1];
    if (evX >= x && evX <= (x + body.getWidth()) && evY > y && evY < (y + body.getHeight())) {
      body.dispatchTouchEvent(event);
    }
  }

  public interface SwipeListener {

    /**
     * 开始刷新
     */
    void onRefreshing();
  }
}
