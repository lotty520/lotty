package com.github.component.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 直接绘制Bitmap
 *
 * @author lotty
 */
public class XImageView extends View {

  private Bitmap mBitmap;

  public XImageView(Context context) {
    super(context);
  }

  public XImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public XImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public XImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setBitmap(Bitmap bitmap) {
    mBitmap = bitmap;
    invalidate();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mBitmap != null) {
      canvas.drawBitmap(mBitmap, 0, 0, null);
      mBitmap.recycle();
    }
  }
}
