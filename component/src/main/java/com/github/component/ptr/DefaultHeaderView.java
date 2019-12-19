package com.github.component.ptr;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.component.R;

/**
 * @author lotty
 */
public class DefaultHeaderView implements HeaderView {

    private final static int TIMEOUT = 5000;

    private View realView;

    private ImageView content;
    private TextView hint;

    private Context ctx;

    private ValueAnimator mAnimator;

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            content.setRotation((Float) animation.getAnimatedValue());
        }
    };


    public DefaultHeaderView(Context ctx) {
        this.ctx = ctx;
        realView = LayoutInflater.from(ctx).inflate(R.layout.default_header_layout, null);
        content = realView.findViewById(R.id.ptr_header_content);
        hint = realView.findViewById(R.id.ptr_header_hint);
        mAnimator = ValueAnimator.ofFloat(0F, 360F);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(800);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(animatorUpdateListener);
    }

    @Override
    public void onReadyToFinish() {
        Log.e("wh", "onReadyToFinish");
        hint.setText("下拉刷新");
        content.setImageResource(R.drawable.ptr_header_down);
    }

    @Override
    public void onRefreshStart() {
        Log.e("wh", "onRefreshStart");
        hint.setText("刷新中");
        content.setImageResource(R.drawable.ptr_header_loading);
        if (mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mAnimator.start();

    }

    @Override
    public void onRefreshFinish() {
        Log.e("wh", "onRefreshFinish");
        mAnimator.end();
    }

    @Override
    public void onReadyToStart() {
        Log.e("wh", "onReadyToStart");
        hint.setText("松开刷新");
        content.setImageResource(R.drawable.ptr_header_up);
    }

    @Override
    public View getView() {
        return realView;
    }


    private static class RTypeEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            return null;
        }
    }
}
