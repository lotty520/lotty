package com.github.component.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Calendar;

public class ClockView extends View {

  private final static int MINUTE_SCALE_LENGTH = 20;
  private final static int HOUR_SCALE_LENGTH = 40;

  private final static int OFF_MINUTE_DEGREE = 6;
  private final static int OFF_HOUR_DEGREE = 30;

  private final static int SECOND_STROKE = 3;
  private final static int MINUTE_STROKE = 4;
  private final static int HOUR_STROKE = 8;

  private final static int DOT_RADIUS = 8;

  private final static int CIRCLE_DEGREE = 360;

  private boolean isPanelDraw;

  private int mRadius;
  private float mCenterX;
  private float mCenterY;

  private int mHourHandRadius;
  private int mMinuteHandRadius;
  private int mSecondHandRadius;

  private Paint mPanelPaint;

  private Paint mPointerPaint;

  private Runnable runnable = new Runnable() {
    @Override public void run() {
      invalidate();
    }
  };

  public ClockView(Context context) {
    super(context);
    initPaint();
  }

  public ClockView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initPaint();
  }

  public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initPaint();
  }

  private void initPaint() {

    mPanelPaint = new Paint();
    mPanelPaint.setAntiAlias(true);
    mPanelPaint.setColor(Color.WHITE);

    mPointerPaint = new Paint();
    mPointerPaint.setColor(Color.WHITE);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawPanel(canvas);
    drawScale(canvas);
    postDelayed(runnable, 1000);
  }

  /**
   * 绘制刻度盘
   */
  private void drawPanel(Canvas canvas) {
    if (!isPanelDraw) {
      int measuredHeight = getMeasuredHeight();
      int measuredWidth = getMeasuredWidth();
      mRadius = Math.min(measuredHeight, measuredWidth) / 2;
      mCenterX = measuredWidth / 2;
      mCenterY = measuredHeight / 2;
      mHourHandRadius = (int) (mRadius * 0.50);
      mMinuteHandRadius = (int) (mRadius * 0.60);
      mSecondHandRadius = (int) (mRadius * 0.80);
    }
    mPanelPaint.setStyle(Paint.Style.STROKE);
    mPanelPaint.setStrokeWidth(2);
    canvas.drawCircle(mCenterX, mCenterY, mRadius, mPanelPaint);

    mPanelPaint.setStyle(Paint.Style.FILL);
    canvas.drawCircle(mCenterX, mCenterY, DOT_RADIUS, mPanelPaint);

    int moveDegree = 0;
    while (moveDegree < CIRCLE_DEGREE) {
      float offX = (float) (mCenterX + mRadius * Math.sin(Math.toRadians(moveDegree)));
      float offY = (float) (mRadius - mRadius * Math.cos(Math.toRadians(moveDegree)));
      if (moveDegree % OFF_HOUR_DEGREE == 0) {
        float endX = (float) (offX - HOUR_SCALE_LENGTH * Math.sin(Math.toRadians(moveDegree)));
        float endY = (float) (offY + HOUR_SCALE_LENGTH * Math.cos(Math.toRadians(moveDegree)));
        mPanelPaint.setStrokeWidth(HOUR_STROKE);
        canvas.drawLine(offX, offY, endX, endY, mPanelPaint);
      } else {
        float endX = (float) (offX - MINUTE_SCALE_LENGTH * Math.sin(Math.toRadians(moveDegree)));
        float endY = (float) (offY + MINUTE_SCALE_LENGTH * Math.cos(Math.toRadians(moveDegree)));
        mPanelPaint.setStrokeWidth(MINUTE_STROKE);
        canvas.drawLine(offX, offY, endX, endY, mPanelPaint);
      }
      moveDegree += OFF_MINUTE_DEGREE;
    }
    isPanelDraw = true;
  }

  private void drawScale(Canvas canvas) {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    float secondDegree = CIRCLE_DEGREE / 60 * second;

    float minuteDegree = CIRCLE_DEGREE / 60 * minute;

    float hourDegree = CIRCLE_DEGREE / 12 * hour + minute * 30 / 60;

    float endHourX = (float) (mCenterX + mHourHandRadius * Math.sin(Math.toRadians(hourDegree)));
    float endHourY = (float) (mCenterY - mHourHandRadius * Math.cos(Math.toRadians(hourDegree)));

    float endMinuteX =
        (float) (mCenterX + mMinuteHandRadius * Math.sin(Math.toRadians(minuteDegree)));
    float endMinuteY =
        (float) (mCenterY - mMinuteHandRadius * Math.cos(Math.toRadians(minuteDegree)));

    float endSecondX =
        (float) (mCenterX + mSecondHandRadius * Math.sin(Math.toRadians(secondDegree)));
    float endSecondY =
        (float) (mCenterY - mSecondHandRadius * Math.cos(Math.toRadians(secondDegree)));

    mPointerPaint.setStrokeWidth(HOUR_STROKE);
    canvas.drawLine(mCenterX, mCenterY, endHourX, endHourY, mPointerPaint);

    mPointerPaint.setStrokeWidth(MINUTE_STROKE);
    canvas.drawLine(mCenterX, mCenterY, endMinuteX, endMinuteY, mPointerPaint);

    mPointerPaint.setStrokeWidth(SECOND_STROKE);
    canvas.drawLine(mCenterX, mCenterY, endSecondX, endSecondY, mPointerPaint);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    removeCallbacks(runnable);
  }
}
