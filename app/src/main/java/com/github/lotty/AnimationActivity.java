package com.github.lotty;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author lotty
 */
public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

  private View mView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_animation);
    mView = findViewById(R.id.object_view);
    findViewById(R.id.transition).setOnClickListener(this);
    findViewById(R.id.rotation).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.transition:
        ObjectAnimator.ofFloat(mView, "translationX", 100).setDuration(2000).start();
        break;
      case R.id.rotation:
        ObjectAnimator.ofFloat(mView, "rotation", 0, 180).setDuration(2000).start();
        break;
      default:
        break;
    }
  }

  @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }
}
