package com.github.component.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BitmapDrawable extends Drawable {

  private Bitmap bitmap;

  public BitmapDrawable(Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  @Override public void draw(@NonNull Canvas canvas) {
    canvas.drawBitmap(bitmap, 0, 0, null);
  }

  @Override public void setAlpha(int alpha) {

  }

  @Override public void setColorFilter(@Nullable ColorFilter colorFilter) {

  }

  @Override public int getOpacity() {
    return PixelFormat.UNKNOWN;
  }
}
