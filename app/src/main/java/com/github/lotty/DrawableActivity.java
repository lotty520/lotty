package com.github.lotty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.github.component.drawable.BitmapDrawable;
import com.github.component.drawable.ColorDrawable;
import com.github.component.drawable.RoundCornerDrawable;

public class DrawableActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drawable);

    // colorDrawable
    View colorDrawableView = findViewById(R.id.color_dr);
    ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0000ff"));
    colorDrawable.setBounds(colorDrawableView.getLeft(), colorDrawableView.getTop(),
        colorDrawableView.getRight(), colorDrawableView.getBottom());
    colorDrawableView.setBackground(colorDrawable);

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.iv);
    View bitmapDrawableView = findViewById(R.id.bitmap_dr);
    BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
    bitmapDrawable.setBounds(bitmapDrawableView.getLeft(), bitmapDrawableView.getTop(),
        bitmapDrawableView.getRight(), bitmapDrawableView.getBottom());
    bitmapDrawableView.setBackground(bitmapDrawable);

    View cornerDrawableView = findViewById(R.id.corner_dr);
    RoundCornerDrawable cornerDrawable = new RoundCornerDrawable(Color.parseColor("#00ff00"));
    cornerDrawable.setBounds(cornerDrawableView.getLeft(), cornerDrawableView.getTop(),
        cornerDrawableView.getRight(), cornerDrawableView.getBottom());
    cornerDrawableView.setBackground(cornerDrawable);
  }
}
