package com.github.lotty;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HookActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hook);
    findViewById(R.id.hook_ams).setOnClickListener(this);
    findViewById(R.id.hook_wifi).setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.hook_ams:
        Toast.makeText(this, "ams", Toast.LENGTH_SHORT).show();
        break;
      case R.id.hook_wifi:
        Toast.makeText(this, "wifi", Toast.LENGTH_SHORT).show();
        break;
      default:
        break;
    }
  }
}
