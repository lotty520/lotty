package com.github.component.dispatch;

import android.view.MotionEvent;

public class ActionUtil {

  public static String action(int actionMode) {
    switch (actionMode) {
      case MotionEvent.ACTION_DOWN:
        return "DOWN";
      case MotionEvent.ACTION_MOVE:
        return "MOVE";
      case MotionEvent.ACTION_UP:
        return "UP";
      case MotionEvent.ACTION_CANCEL:
        return "CANCEL";
      default:
        return "其他事件";
    }
  }
}
