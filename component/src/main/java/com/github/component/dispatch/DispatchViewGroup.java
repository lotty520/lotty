package com.github.component.dispatch;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author lotty
 */
public class DispatchViewGroup extends FrameLayout {

  public DispatchViewGroup(@NonNull Context context) {
    super(context);
  }

  public DispatchViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public DispatchViewGroup(@NonNull Context context, @Nullable AttributeSet attrs,
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public DispatchViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    Log.e("wh", "ViewGroup:dispatchTouchEvent:" + ActionUtil.action(ev.getAction()));
    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    Log.e("wh", "ViewGroup:onInterceptTouchEvent:" + ActionUtil.action(ev.getAction()));
    return super.onInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    Log.e("wh", "ViewGroup:onTouchEvent:" + ActionUtil.action(event.getAction()));
    return super.onTouchEvent(event);
  }
}
