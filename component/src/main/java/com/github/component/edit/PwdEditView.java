package com.github.component.edit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputType;
import android.util.AttributeSet;

/**
 * 实现一种类似支付确认页面输入支付密码的控件
 * 方格输入框
 *
 * @author lotty
 */
public class PwdEditView extends androidx.appcompat.widget.AppCompatEditText {

  private final static int DEFAULT_INPUT_LENGTH = 6;
  private final static int DEFAULT_RADIUS_SCALE = 6;
  private final static int DEFAULT_STROKE_WIDTH = 2;

  private Paint mPaint;

  private int mTextLength = DEFAULT_INPUT_LENGTH;

  private int mElementW;
  private int mDotRadius;

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

  @Override
  protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    Editable editable = getText();
    if (editable != null && editable.length() > mTextLength) {
      editable.delete(mTextLength, editable.length());
    }
  }

  public void setLength(int len) {
    mTextLength = len;
    Editable editable = getText();
    if (editable != null && editable.length() > mTextLength) {
      editable.delete(mTextLength, editable.length());
    }
    postInvalidate();
  }

  private void init() {
    mPaint = new Paint();
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setAntiAlias(true);
    mPaint.setColor(getCurrentTextColor());
    mPaint.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    //设置背景为null，去掉下划线
    setBackground(null);
    // 设置文字带大小为0
    setTextSize(0);
    setInputType(InputType.TYPE_CLASS_NUMBER);
    // 隐藏光标
    setCursorVisible(false);
  }

  @Override public void setTextSize(float size) {
    mPaint.setTextSize(0);
  }

  @Override public void setTextSize(int unit, float size) {
    mPaint.setTextSize(0);
  }

  @Override public void setTextColor(int color) {
    mPaint.setColor(color);
  }

  @Override protected void onDraw(Canvas canvas) {
    int height = getMeasuredHeight();
    int width = getMeasuredWidth();
    mElementW = width / mTextLength;
    mDotRadius = mElementW / DEFAULT_RADIUS_SCALE;
    // 绘制外边框
    canvas.drawLine(1, 1, width, 1, mPaint);
    canvas.drawLine(1, 1, 1, height - 1, mPaint);
    canvas.drawLine(width - 1, 1, width - 1, height - 1, mPaint);
    canvas.drawLine(1, height - 1, width, height - 1, mPaint);
    for (int i = 1; i < mTextLength; i++) {
      canvas.drawLine(mElementW * i, 1, mElementW * i, height - 1, mPaint);
    }
    Editable editable = getText();
    if (editable != null) {
      String content = editable.toString();
      int length = content.length();
      for (int i = 0; i < length; i++) {
        canvas.drawCircle(mElementW * i + mElementW / 2F, height / 2F, mDotRadius, mPaint);
      }
    }
  }
}
