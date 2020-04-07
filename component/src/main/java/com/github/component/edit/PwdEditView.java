package com.github.component.edit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 实现一种类似支付确认页面输入支付密码的控件
 * 方格输入框
 *
 * @author lotty
 */
public class PwdEditView extends androidx.appcompat.widget.AppCompatEditText {

  private final static int DEFAULT_INPUT_LENGTH = 6;

  private Paint mPaint;

  private String currentText;

  private int mInputLength = DEFAULT_INPUT_LENGTH;

  public PwdEditView(Context context) {
    super(context);
    init();
  }

  public PwdEditView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PwdEditView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void setInputLength(int len) {
    mInputLength = len;
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setAntiAlias(true);
    mPaint.setColor(getCurrentTextColor());
    mPaint.setStrokeWidth(2);
    //设置背景为null，去掉下划线
    setBackground(null);
    // 设置文字带大小为0
    setTextSize(0);
    setInputType(InputType.TYPE_CLASS_NUMBER);
    // 隐藏光标
    setCursorVisible(false);
  }

  @Override public void setCursorVisible(boolean visible) {
    // no op
  }

  @Override public void setTextSize(float size) {
    mPaint.setTextSize(size);
  }

  @Override public void setTextSize(int unit, float size) {
    // TODO: 2020/4/7 no op
  }

  @Override public void setTextColor(int color) {
    mPaint.setColor(color);
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    Log.e("wh", "onKeyDown: " + keyCode);
    int length = getText().length();
    if (length >= mInputLength) {
      if (keyCode == KeyEvent.KEYCODE_DEL) {
        return super.onKeyDown(keyCode, event);
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int height = getMeasuredHeight();
    int width = getMeasuredWidth();
    int eleWidth = width / mInputLength;
    int radius = eleWidth / 6;

    // 绘制外边框
    canvas.drawLine(1, 1, width, 1, mPaint);
    canvas.drawLine(1, 1, 1, height - 1, mPaint);
    canvas.drawLine(width - 1, 1, width - 1, height - 1, mPaint);
    canvas.drawLine(1, height - 1, width, height - 1, mPaint);
    // 绘制纵向分割线
    for (int i = 1; i < mInputLength; i++) {
      canvas.drawLine(eleWidth * i, 1, eleWidth * i, height - 1, mPaint);
    }
    String content = getText().toString();
    int length = content.length();
    if (length >= mInputLength) {
      length = mInputLength;
      currentText = content.substring(0, mInputLength);
    } else {
      currentText = content;
    }
    for (int i = 0; i < length; i++) {
      canvas.drawCircle(eleWidth * i + eleWidth / 2, height / 2, radius, mPaint);
    }
  }
}
