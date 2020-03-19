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
   * 绘制刻度盘，主要包括：
   * 1、表盘圆形
   * 2、60个刻度
   */
  private void drawPanel(Canvas canvas) {
    if (!isPanelDraw) {
      // 获取对应的参数属性：控件宽高，矩形内切圆半径，内切圆圆心位置，三个指针轨迹圆的半径等。
      int measuredHeight = getMeasuredHeight();
      int measuredWidth = getMeasuredWidth();
      // 表盘内切圆半径
      mRadius = Math.min(measuredHeight, measuredWidth) / 2;
      // 表盘圆心x y 坐标
      mCenterX = measuredWidth / 2;
      mCenterY = measuredHeight / 2;
      // 时针轨迹圆半径：内切圆的一半
      mHourHandRadius = (int) (mRadius * 0.50);
      // 分针轨迹圆半径：内切圆的6/10
      mMinuteHandRadius = (int) (mRadius * 0.60);
      // 秒针轨迹圆半径：内切圆的8/10
      mSecondHandRadius = (int) (mRadius * 0.80);
    }
    // 画笔设置
    mPanelPaint.setStyle(Paint.Style.STROKE);
    mPanelPaint.setStrokeWidth(2);
    // 绘制表盘内切圆
    canvas.drawCircle(mCenterX, mCenterY, mRadius, mPanelPaint);

    // 绘制圆心位置
    mPanelPaint.setStyle(Paint.Style.FILL);
    canvas.drawCircle(mCenterX, mCenterY, DOT_RADIUS, mPanelPaint);

    int moveDegree = 0;
    // 从0-360开始绘制刻度
    while (moveDegree < CIRCLE_DEGREE) {
      // 计算刻度在圆弧上的位置，也就是起始位置
      float offX = (float) (mCenterX + mRadius * Math.sin(Math.toRadians(moveDegree)));
      float offY = (float) (mRadius - mRadius * Math.cos(Math.toRadians(moveDegree)));
      if (moveDegree % OFF_HOUR_DEGREE == 0) {
        // 计算刻度在圆内的位置，也就是终点位置，整点位置刻度加长
        float endX = (float) (offX - HOUR_SCALE_LENGTH * Math.sin(Math.toRadians(moveDegree)));
        float endY = (float) (offY + HOUR_SCALE_LENGTH * Math.cos(Math.toRadians(moveDegree)));
        mPanelPaint.setStrokeWidth(HOUR_STROKE);
        canvas.drawLine(offX, offY, endX, endY, mPanelPaint);
      } else {
        // 计算刻度在圆内的位置，也就是终点位置
        float endX = (float) (offX - MINUTE_SCALE_LENGTH * Math.sin(Math.toRadians(moveDegree)));
        float endY = (float) (offY + MINUTE_SCALE_LENGTH * Math.cos(Math.toRadians(moveDegree)));
        mPanelPaint.setStrokeWidth(MINUTE_STROKE);
        // 绘制刻度
        canvas.drawLine(offX, offY, endX, endY, mPanelPaint);
      }
      moveDegree += OFF_MINUTE_DEGREE;
    }
    isPanelDraw = true;
  }

  /**
   * 绘制指针，主要包括：
   * 1、时针
   * 2、分针
   * 3、秒针
   */
  private void drawScale(Canvas canvas) {
    // 获取当前时间
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    int second = calendar.get(Calendar.SECOND);

    // 计算三个表针的行进角度
    float secondDegree = CIRCLE_DEGREE / 60 * second;
    float minuteDegree = CIRCLE_DEGREE / 60 * minute;
    float hourDegree = CIRCLE_DEGREE / 12 * hour + minute * 30 / 60;

    // 计算时针当前的终点位置
    float endHourX = (float) (mCenterX + mHourHandRadius * Math.sin(Math.toRadians(hourDegree)));
    float endHourY = (float) (mCenterY - mHourHandRadius * Math.cos(Math.toRadians(hourDegree)));

    // 计算分针当前的终点位置
    float endMinuteX =
        (float) (mCenterX + mMinuteHandRadius * Math.sin(Math.toRadians(minuteDegree)));
    float endMinuteY =
        (float) (mCenterY - mMinuteHandRadius * Math.cos(Math.toRadians(minuteDegree)));

    // 计算秒针当前的终点位置
    float endSecondX =
        (float) (mCenterX + mSecondHandRadius * Math.sin(Math.toRadians(secondDegree)));
    float endSecondY =
        (float) (mCenterY - mSecondHandRadius * Math.cos(Math.toRadians(secondDegree)));

    // 绘制时针
    mPointerPaint.setStrokeWidth(HOUR_STROKE);
    canvas.drawLine(mCenterX, mCenterY, endHourX, endHourY, mPointerPaint);
    // 绘制分针
    mPointerPaint.setStrokeWidth(MINUTE_STROKE);
    canvas.drawLine(mCenterX, mCenterY, endMinuteX, endMinuteY, mPointerPaint);
    // 绘制秒针
    mPointerPaint.setStrokeWidth(SECOND_STROKE);
    canvas.drawLine(mCenterX, mCenterY, endSecondX, endSecondY, mPointerPaint);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    removeCallbacks(runnable);
  }
}
