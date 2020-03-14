package com.github.lotty;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
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
    findViewById(R.id.drawable).setOnClickListener(this);
    findViewById(R.id.animation).setOnClickListener(this);

    WifiManager wifi = (WifiManager) MainActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    Log.e("wh", "MainActivity 中通过 getApplicationContext() 获取 WifiManager hash" + wifi.hashCode());

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
      case R.id.drawable:
        Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/drawable")).go();
        break;
      case R.id.animation:
        Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/animation")).go();
        break;
      default:
        break;
    }
  }
}
