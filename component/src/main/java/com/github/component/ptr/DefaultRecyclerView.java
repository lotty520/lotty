package com.github.component.ptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author lotty
 */
public class DefaultRecyclerView extends RecyclerView {
  public DefaultRecyclerView(@NonNull Context context) {
    super(context);
  }

  public DefaultRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public DefaultRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return super.dispatchTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent e) {
    //Log.e("wh","DefaultRecyclerView--->" +e.getAction());
    return super.onTouchEvent(e);
  }
}
