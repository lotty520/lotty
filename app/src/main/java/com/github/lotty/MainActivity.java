package com.github.lotty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.github.common.Router;
import com.github.frameworkaly.service.IntentServiceImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.e("wh","Main onCreate ");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.algorithm).setOnClickListener(this);
    findViewById(R.id.hook).setOnClickListener(this);
    findViewById(R.id.grant).setOnClickListener(this);
    findViewById(R.id.component).setOnClickListener(this);
    findViewById(R.id.drawable).setOnClickListener(this);
    findViewById(R.id.animation).setOnClickListener(this);
    findViewById(R.id.framealy).setOnClickListener(this);
  }

  @Override protected void onResume() {
    Log.e("wh","Main onResume ");
    super.onResume();
  }

  @Override protected void onPostResume() {
    Log.e("wh","Main onPostResume ");
    super.onPostResume();
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

      case R.id.framealy:
        Intent intent = new Intent(this.getApplicationContext(), IntentServiceImpl.class);
        getApplicationContext().startService(intent);
        break;
      default:
        break;
    }
  }
}
