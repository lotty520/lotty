package com.github.component.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lotty
 */
public final class EdgeSlideView extends View {

  /**
   * 默认字体大小
   */
  private final static int DEFAULT_TEXT_SIZE = 32;

  private Paint mPaint;

  private int mSelectPosition = -1;

  private List<String> keys = new ArrayList<>();

  private OnItemChangeListener mOnItemChangeListener;

  private Rect mDrawRect;

  public EdgeSlideView(Context context) {
    super(context);
    init();
  }

  public EdgeSlideView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public EdgeSlideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void setOnItemChangeListener(
      OnItemChangeListener itemChangeListener) {
    this.mOnItemChangeListener = itemChangeListener;
  }

  /**
   * 设置字体大小
   */
  public void setTextSize(int size) {
    if (mPaint.getTextSize() != size) {
      mPaint.setTextSize(size);
      if (this.keys.size() > 0) {
        invalidate();
      }
    }
  }

  private void init() {
    mDrawRect = new Rect();
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setTextSize(DEFAULT_TEXT_SIZE);
    mPaint.setTextAlign(Paint.Align.CENTER);
  }

  public void setKeys(List<String> keys) {
    this.keys.clear();
    this.keys.addAll(keys);
    mSelectPosition = -1;
    invalidate();
  }

  public void setSelectPosition(int position) {
    if (mSelectPosition != position) {
      mSelectPosition = position;
      invalidate();
    }
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    int size = this.keys.size();
    if (size > 0) {
      float x = getMeasuredWidth() / 2;
      int itemHeight = getMeasuredHeight() / size;

      float y = itemHeight / 2 - mPaint.getFontMetrics().ascent / 2;

      mPaint.setColor(Color.BLACK);
      // 绘制除当前位置外其他的文字
      for (int i = 0; i < size; i++) {
        if (i != mSelectPosition) {
          canvas.drawText(keys.get(i), x, y + itemHeight * i, mPaint);
        }
      }
      if (mSelectPosition >= 0) {
        // 绘制被选择的Item的底层背景
        mPaint.setColor(Color.parseColor("#1D953F"));
        mDrawRect.left = 0;
        mDrawRect.right = getMeasuredWidth();
        mDrawRect.top = itemHeight * mSelectPosition;
        mDrawRect.bottom = mDrawRect.top + itemHeight;
        canvas.drawRect(mDrawRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(keys.get(mSelectPosition), x, y + itemHeight * mSelectPosition, mPaint);
      }
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    float y = event.getY();
    int measuredHeight = getMeasuredHeight();
    int singleH = measuredHeight / this.keys.size();
    int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_MOVE:
      case MotionEvent.ACTION_UP:
        int vp = (int) (y / singleH);
        if (vp < this.keys.size() && vp >= 0 && mSelectPosition != vp) {
          int old = mSelectPosition;
          mSelectPosition = vp;
          invalidate();
          if (mOnItemChangeListener != null) {
            mOnItemChangeListener.onItemSelect(old, mSelectPosition);
          }
        }
        break;
      default:
        break;
    }
    return true;
  }

  @Override public void setOnClickListener(@Nullable OnClickListener l) {
    // no op
  }

  @Override public void setOnTouchListener(OnTouchListener l) {
    // no op
  }

  @Override public void setOnLongClickListener(@Nullable OnLongClickListener l) {
    // no op
  }

  @Override public void setOnDragListener(OnDragListener l) {
    // no op
  }

  @Override public void setOnScrollChangeListener(OnScrollChangeListener l) {
    // no op
  }

  /**
   * 被选Item改变事件监听，尽在点击、滑动时触发
   *
   * 手动设置时，不触发
   */
  public interface OnItemChangeListener {
    /**
     * item 改变回调
     *
     * @param oldPosition 改变前的位置
     * @param newPosition 改变后的位置
     */
    void onItemSelect(int oldPosition, int newPosition);
  }
}
