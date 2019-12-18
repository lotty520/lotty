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

/**
 * @author lotty
 */
public class SwipeLayout extends LinearLayout {

    private final static int MAX_HEADER_SIZE = 400;
    private final static int MAX_FOOTER_SIZE = 200;
    private final static int MIN_SWIPE_DIS = 10;
    private FrameLayout body;
    private FrameLayout header;
    private FrameLayout footer;
    private RecyclerView mRecyclerView;
    private int startY;
    private int endY;
    private boolean isBacking;
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
        header.setClickable(false);

        body = new FrameLayout(getContext());
        LayoutParams bodyLp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        bodyLp.weight = 1;
        body.setLayoutParams(bodyLp);
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        body.addView(mRecyclerView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("wh", "down:" + isRefreshing);
            isPulling = true;
            endY = startY = (int) event.getRawY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.e("wh", "up:" + isRefreshing);
            isPulling = false;
            if (headerScrolledDistance == -MAX_HEADER_SIZE && !isRefreshing) {
                isRefreshing = true;
                refreshing();
            }
            if (!isRefreshing) {
                smoothScrollToTop();
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e("wh", "move:" + isRefreshing);
            if (!isRefreshing) {
                headerScrolledDistance = getScrollY();
                endY = (int) event.getRawY();
                int deltaY = endY - startY;
                if (Math.abs(deltaY) > MIN_SWIPE_DIS) {
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
        }
        return super.dispatchTouchEvent(event);
    }

    private void refreshing() {
        // TODO: 2019-12-17 refresh animation
        if (mListener != null) {
            mListener.onRefreshing();
        }
    }

    private void smoothScrollToTop() {
        isBacking = true;
        scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (!isPulling && isBacking && scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            Log.e("wh", "computeScroll");
            postInvalidate();
        }
        if (getScrollY() == 0) {
            isBacking = false;
            isRefreshing = false;
        }
    }

    public void setHeader(View view) {
        header.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public RecyclerView getBody() {
        return mRecyclerView;
    }

    public void setBody(View view) {
        body.removeAllViews();
        body.addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void notifySwipeFinished() {
        if (isRefreshing) {
            isBacking = true;
            smoothScrollToTop();
            Log.e("wh", "notifySwipeFinished");
        }
    }


    public interface SwipeListener {

        void onRefreshing();

    }
}
