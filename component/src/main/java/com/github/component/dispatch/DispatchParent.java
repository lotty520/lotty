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
public class DispatchParent extends FrameLayout {

  public DispatchParent(@NonNull Context context) {
    super(context);
  }

  public DispatchParent(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public DispatchParent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public DispatchParent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    Log.e("wh", "ViewParent:dispatchTouchEvent:" + ActionUtil.action(ev.getAction()));
    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    //if (ev.getAction() == MotionEvent.ACTION_DOWN){
    //    return true;
    //}
    Log.e("wh", "ViewParent:onInterceptTouchEvent:" + ActionUtil.action(ev.getAction()));
    return super.onInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    Log.e("wh", "ViewParent:onTouchEvent:" + ActionUtil.action(event.getAction()));
    return true;
  }
}
