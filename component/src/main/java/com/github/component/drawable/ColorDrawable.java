package com.github.component.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ColorDrawable extends Drawable {

  private Paint paint = new Paint();

  @Override public void draw(@NonNull Canvas canvas) {
    paint.setColor(Color.parseColor("#DFDF44"));
    Rect bounds = getBounds();
    canvas.drawRect(bounds, paint);

    Path path = new Path();
    path.moveTo(bounds.left, bounds.top);
    path.lineTo(bounds.right, bounds.bottom);

    Paint pathPaint = new Paint();
    pathPaint.setColor(Color.parseColor("#ff0000"));
    pathPaint.setStrokeWidth(3);
    pathPaint.setStyle(Paint.Style.STROKE);
    canvas.drawPath(path, pathPaint);

    Paint textPaint = new Paint();
    textPaint.setColor(Color.parseColor("#ff0000"));
    textPaint.setTextSize(48);
    canvas.drawTextOnPath("this is text view self", path, 100, 0, textPaint);
    Log.e("DR", "draw called: "
        + bounds.left
        + ","
        + bounds.right
        + ", "
        + bounds.top
        + ", "
        + bounds.bottom);
  }

  @Override public void setAlpha(int alpha) {
    Log.e("DR", "alpha:" + alpha);
    paint.setAlpha(alpha);
  }

  @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {
    Log.e("DR", colorFilter.getClass().getSimpleName());
    paint.setColorFilter(colorFilter);
    invalidateSelf();
  }

  @Override public int getOpacity() {
    return PixelFormat.UNKNOWN;
  }
}
