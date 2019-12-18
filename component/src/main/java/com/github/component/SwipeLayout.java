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

/**
 * @author lotty
 */
public class SwipeLayout extends LinearLayout {

    private final static int MAX_HEADER_SIZE = 400;
    private final static int MAX_FOOTER_SIZE = 200;
    private final static int MIN_SWIPE_DIS = 5;
    private FrameLayout body;
    private FrameLayout header;
    private FrameLayout footer;
    private int startY;
    private int endY;
    private int headerScrolledDistance;
    private Scroller scroller = new Scroller(getContext());

    private boolean isPulling;

    private boolean isRefreshing;

    private SwipeListener mListener;

    public SwipeLayout(Context context) {
        super(context);
        setUp();

    }

    private void setUp() {
        setOrientation(LinearLayout.VERTICAL);
        header = new FrameLayout(getContext());
        LayoutParams headerLp = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_HEADER_SIZE);
        headerLp.topMargin = -MAX_HEADER_SIZE;
        header.setLayoutParams(headerLp);
        header.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        header.setClickable(false);

        body = new FrameLayout(getContext());
        LayoutParams bodyLp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        bodyLp.weight = 1;
        body.setLayoutParams(bodyLp);


        footer = new FrameLayout(getContext());
        LayoutParams footerLp = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_FOOTER_SIZE);
        footer.setLayoutParams(footerLp);
        footer.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
        fill();
    }

    private void fill() {
        addView(header, 0);
        addView(body, 1);
        addView(footer, 2);
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
        // 修改控件的高度，将Footer隐藏
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int measureSpec = MeasureSpec.makeMeasureSpec(size + MAX_FOOTER_SIZE, mode);
        super.onMeasure(widthMeasureSpec, measureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isPulling = true;
            endY = startY = (int) event.getRawY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isPulling = false;
            if (headerScrolledDistance == -MAX_HEADER_SIZE && !isRefreshing) {
                isRefreshing = true;
                refreshing();
            }
            if (!isRefreshing) {
                smoothScrollToTop();
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (!isRefreshing) {
                headerScrolledDistance = getScrollY();
                endY = (int) event.getRawY();
                int deltaY = endY - startY;
                // 在起点位置下方
                if (deltaY > 0) {
                    if (deltaY < MAX_HEADER_SIZE) {
                        scrollTo(0, -deltaY);
                    } else {
                        if (headerScrolledDistance > -MAX_HEADER_SIZE) {
                            // 强制滑动到最大距离
                            scrollTo(0, -MAX_HEADER_SIZE);
                        }
                    }
                } else {
                    if (headerScrolledDistance < 0) {
                        scrollTo(0, 0);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (!isPulling && scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            Log.e("wh", "computeScroll");
            postInvalidate();
        }
    }

    private void refreshing() {
        // TODO: 2019-12-17 refresh animation
        if (mListener != null) {
            mListener.onRefreshing();
        }
    }

    private void smoothScrollToTop() {
        scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 1000);
        invalidate();
    }

    public void setHeader(View view) {
        header.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void notifySwipeFinished() {
        if (isRefreshing) {
            smoothScrollToTop();
            Log.e("wh", "notifySwipeFinished");
            isRefreshing = false;
        }
    }


    public interface SwipeListener {

        void onRefreshing();

    }
}
