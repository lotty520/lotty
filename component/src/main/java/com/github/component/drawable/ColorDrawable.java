package com.github.component.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author lotty
 */
public class ColorDrawable extends Drawable {

  private Paint paint = new Paint();

  public ColorDrawable(int color) {
    paint.setColor(color);
  }

  @Override public void draw(@NonNull Canvas canvas) {
    Rect bounds = getBounds();
    canvas.drawRect(bounds, paint);

    //Path path = new Path();
    //path.moveTo(bounds.left, bounds.top);
    //path.lineTo(bounds.right, bounds.bottom);
    //
    //Paint pathPaint = new Paint();
    //pathPaint.setColor(Color.parseColor("#ff0000"));
    //pathPaint.setStrokeWidth(3);
    //pathPaint.setStyle(Paint.Style.STROKE);
    //canvas.drawPath(path, pathPaint);
    //
    //Paint textPaint = new Paint();
    //textPaint.setColor(Color.parseColor("#ff0000"));
    //textPaint.setTextSize(48);
    //canvas.drawTextOnPath("this is text view self", path, 100, 0, textPaint);
    Log.e("DR", "draw called: "
        + bounds.left
        + ","
        + bounds.right
        + ", "
        + bounds.top
        + ", "
        + bounds.bottom);
  }

  public void setBackColor(int color) {
    paint.setColor(color);
    invalidateSelf();
  }

  @Override public void setAlpha(int alpha) {
    paint.setAlpha(alpha);
    invalidateSelf();
  }

  @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {
    paint.setColorFilter(colorFilter);
    invalidateSelf();
  }

  @Override public int getOpacity() {
    return PixelFormat.UNKNOWN;
  }
}
