package com.github.component.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoundCornerDrawable extends Drawable {

  private Paint paint;

  public RoundCornerDrawable(int color) {
    paint = new Paint();
    paint.setColor(color);
    paint.setAntiAlias(true);
    paint.setStyle(Paint.Style.FILL);
    paint.setStrokeWidth(2);
  }

  @Override public void draw(@NonNull Canvas canvas) {
    Rect bounds = getBounds();
    RectF rectF = new RectF(bounds);
    canvas.drawRoundRect(rectF, rectF.left + rectF.width() /2, rectF.bottom + rectF.height() /2, paint);
  }

  @Override public void setAlpha(int alpha) {

  }

  @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {

  }

  @Override public int getOpacity() {
    return PixelFormat.UNKNOWN;
  }
}
