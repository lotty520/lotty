package com.github.lotty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.component.GlsSurfaceView;
import com.github.component.image.XImageView;

public class ImageActivity extends AppCompatActivity implements SurfaceHolder.Callback {

  private Bitmap mBp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image);
    XImageView xiv = findViewById(R.id.image);
    xiv.setBitmap(null);
    mBp = BitmapFactory.decodeResource(getResources(), R.drawable.iv);
    if (mBp != null) {
      Log.e("wh", "加载图片");
      xiv.setBitmap(mBp);
    }
    SurfaceView sv = findViewById(R.id.surface);
    sv.getHolder().addCallback(this);
    GlsSurfaceView gsc = findViewById(R.id.gsc);
  }

  @Override public void surfaceCreated(SurfaceHolder holder) {
    Log.e("wh", "surfaceCreated");
    Rect surfaceFrame = holder.getSurfaceFrame();
    Canvas canvas = holder.lockCanvas();
    canvas.drawBitmap(mBp, surfaceFrame.left, surfaceFrame.top, null);
    holder.unlockCanvasAndPost(canvas);
  }

  @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    Log.e("wh", "surfaceChanged");
  }

  @Override public void surfaceDestroyed(SurfaceHolder holder) {
    Log.e("wh", "surfaceDestroyed");
  }
}
