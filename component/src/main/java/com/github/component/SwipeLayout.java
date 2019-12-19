package com.github.component;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.component.ptr.DefaultHeaderView;
import com.github.component.ptr.HeaderView;

/**
 * @author lotty
 */
public class SwipeLayout extends LinearLayout {

    private final static int MAX_HEADER_SIZE = 600;
    private final static int MAX_LOADING_OFFSET = 400;
    private final static int MAX_FOOTER_SIZE = 200;
    private final static int MIN_SLOT = 10;

    private final static int SCROLL_DURATION = 1000;

    /**
     * 初始状态
     */
    private final static int STATE_INIT = 1;
    /**
     * 向下滑动中，且滑动距离小于{@link MAX_LOADING_OFFSET}
     */
    private final static int STATE_PULLING_DOWN = 2;
    /**
     * 向下滑动中，且滑动距离介于{@link MAX_LOADING_OFFSET,MAX_HEADER_SIZE}之间
     */
    private final static int STATE_PULLING_DOWN_EXTRA = 3;
    /**
     * 滑动距离等于{@link MAX_LOADING_OFFSET}且悬停
     */
    private final static int STATE_PULLING_LOADING = 4;
    /**
     * 向上滑动中，且滑动距离小于{@link MAX_LOADING_OFFSET}
     */
    private final static int STATE_PULLING_UP = 5;
    /**
     * 向上滑动中，且滑动距离介于{@link MAX_LOADING_OFFSET,MAX_HEADER_SIZE}之间
     */
    private final static int STATE_PULLING_UP_EXTRA = 6;
    /**
     * 滑动距离等于{@link MAX_HEADER_SIZE}且悬停
     */
    private final static int STATE_PULLING_MAX = 7;


    private FrameLayout body;
    private FrameLayout header;
    private FrameLayout footer;

    private RecyclerView mRecyclerView;
    private int startY;
    private int endY;
    private Scroller mScroller;

    private HeaderView mHeaderView;

    private boolean isTouching;

    private boolean isRefreshing;
    private float lastY;
    private int lastScrollY;

    private SwipeListener mListener;
    /**
     * 当前状态
     */
    private int mState = STATE_INIT;

    public SwipeLayout(Context context) {
        super(context);
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
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        body.addView(mRecyclerView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void fill() {
        addView(header, 0);
        addView(body, 1);
        addView(footer, 2);
    }

    public void setHeader(HeaderView view) {
        mHeaderView = view;
        header.removeAllViews();
        header.addView(view.getView(), new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
    public SwipeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
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
    public boolean dispatchTouchEvent(MotionEvent event) {
        int scrollPix = getScrollY();
        float rawY = event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTouching = true;
            if (mState == STATE_INIT) {
                endY = startY = Math.round(rawY);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isTouching = false;
            if (mState == STATE_PULLING_DOWN || mState == STATE_PULLING_UP) {
                smoothScroll(scrollPix, 0, SCROLL_DURATION);
            } else if (mState == STATE_PULLING_LOADING) {
                mHeaderView.onRefreshStart();
                isRefreshing = true;
            } else if (mState == STATE_PULLING_DOWN_EXTRA || mState == STATE_PULLING_UP_EXTRA || mState == STATE_PULLING_MAX) {
                smoothScroll(scrollPix, -MAX_LOADING_OFFSET, SCROLL_DURATION);
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            updateState(scrollPix, rawY - lastY > 0);
            endY = Math.round(rawY);
            int deltaY = endY - startY;
            int absDeltaY = Math.abs(deltaY);
            // 大于最小滑动识别单位
            if (absDeltaY > MIN_SLOT) {
                // 在起点位置下方
                if (deltaY > 0) {
                    if (absDeltaY < MAX_HEADER_SIZE) {
                        scrollTo(0, -deltaY);
                    } else {
                        if (scrollPix > -MAX_HEADER_SIZE) {
                            // 强制滑动到最大距离
                            scrollTo(0, -MAX_HEADER_SIZE);
                        }
                    }
                } else {
                    if (scrollPix < 0) {
                        scrollTo(0, 0);
                    }
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            isTouching = false;
        }
        lastY = rawY;
        return super.dispatchTouchEvent(event);
    }

    private void updateState(int scrollY, boolean down) {
        int realY = Math.abs(scrollY);
        if (realY <= 0) {
            mState = STATE_INIT;
        } else if (realY < MAX_LOADING_OFFSET) {
            if (down) {
                mState = STATE_PULLING_DOWN;
            } else {
                mState = STATE_PULLING_UP;
            }
        } else if (realY == MAX_LOADING_OFFSET) {
            mState = STATE_PULLING_LOADING;
        } else if (realY < MAX_HEADER_SIZE) {
            if (down) {
                mState = STATE_PULLING_DOWN_EXTRA;
            } else {
                mState = STATE_PULLING_UP_EXTRA;
            }
        } else {
            mState = STATE_PULLING_MAX;
        }
    }

    private void refreshing() {
        // TODO: 2019-12-17 refresh animation
        if (mListener != null) {
            mListener.onRefreshing();
        }
    }

    @Override
    public void computeScroll() {
        int scrollY = getScrollY();
        if (scrollY > -MAX_LOADING_OFFSET) {
            isRefreshing = false;
        }
        if (scrollY < lastScrollY) {
            if (scrollY < -MAX_LOADING_OFFSET && mState == STATE_PULLING_DOWN) {
                mHeaderView.onReadyToStart();
            }
            updateState(scrollY, true);
        } else {
            if (scrollY > -MAX_LOADING_OFFSET && (mState == STATE_PULLING_UP_EXTRA || mState == STATE_PULLING_LOADING)) {
                mHeaderView.onReadyToFinish();
            }
            updateState(scrollY, false);
        }
        //回弹到指定位置
        if (!isTouching && !isRefreshing && mScroller.computeScrollOffset()) {
            int currY = mScroller.getCurrY();
            Log.e("wh", "------>" + currY);
            scrollTo(0, currY);
            postInvalidate();
        }
        if (!isTouching && !isRefreshing && mState == STATE_PULLING_LOADING && mScroller.isFinished()) {
            mHeaderView.onRefreshStart();
            isRefreshing = true;
        }
        lastScrollY = scrollY;
    }

    public RecyclerView getBody() {
        return mRecyclerView;
    }

    public void setBody(View view) {
        body.removeAllViews();
        body.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void notifySwipeFinished() {
        Log.e("wh", "notifySwipeFinished");
        if (isRefreshing) {
            isRefreshing = false;
            smoothScrollToTop();
        }
    }

    public void notifySwipeStarted() {
        Log.e("wh", "notifySwipeStarted");
        if (!isRefreshing) {
            smoothScrollToRefresh();
        }
    }


    /**
     * @param start    当前位置的scrollY
     * @param end      目的位置的scrollY
     * @param duration 时长
     */
    private void smoothScroll(int start, int end, int duration) {
        int dis = end - start;
        mScroller.startScroll(0, start, 0, dis, duration);
        invalidate();
    }

    private void smoothScrollToTop() {
        mScroller.startScroll(0, getScrollY(), 0, MAX_LOADING_OFFSET, 1000);
        invalidate();
    }

    private void smoothScrollToRefresh() {
        mScroller.startScroll(0, 0, 0, -MAX_LOADING_OFFSET, 1000);
        invalidate();
    }


    public interface SwipeListener {

        void onRefreshing();

    }
}
