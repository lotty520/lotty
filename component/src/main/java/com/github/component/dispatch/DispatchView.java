package com.github.component.dispatch;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author lotty
 */
public class DispatchView extends View {

  public DispatchView(Context context) {
    super(context);
  }

  public DispatchView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public DispatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public DispatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    Log.e("wh", "View:dispatchTouchEvent:" + ActionUtil.action(event.getAction()));
    return super.dispatchTouchEvent(event);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    Log.e("wh", "View:onTouchEvent:" + ActionUtil.action(event.getAction()));
    //return true;
    return super.onTouchEvent(event);
  }



  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    Log.e("DR", "onLayout");
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    Log.e("DR", "onMeasure:" + MeasureSpec.getSize(widthMeasureSpec) + "," + MeasureSpec.getSize(heightMeasureSpec));
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override protected void onDraw(Canvas canvas) {
    Log.e("DR", "onDraw");
    super.onDraw(canvas);
  }

  @Override public void layout(int l, int t, int r, int b) {
    Log.e("DR", "layout:" + l + "," + t + "," + r + "," + b);
    super.layout(l, t, r, b);
  }

  @Override public void draw(Canvas canvas) {
    Log.e("DR", "draw");
    super.draw(canvas);
  }

  @Override protected void dispatchDraw(Canvas canvas) {
    Log.e("DR", "dispatchDraw");
    super.dispatchDraw(canvas);
  }

}
