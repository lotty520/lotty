package com.github.lotty;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.github.common.Router;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.algorithm).setOnClickListener(this);
    findViewById(R.id.hook).setOnClickListener(this);
    findViewById(R.id.grant).setOnClickListener(this);
    findViewById(R.id.component).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.grant:
        Toast.makeText(this, "空实现", Toast.LENGTH_SHORT).show();
        break;
      case R.id.algorithm:
        Toast.makeText(this, "还没有实现", Toast.LENGTH_SHORT).show();
        break;
      case R.id.component:
        Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/component")).go();
        break;
      case R.id.hook:
        Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/hook")).go();
        break;
      default:
        break;
    }
  }
}
