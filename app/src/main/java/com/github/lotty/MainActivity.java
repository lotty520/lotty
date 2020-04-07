package com.github.lotty;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.legacy.content.WakefulBroadcastReceiver;
import com.github.common.Router;
import com.github.frameworkaly.job.IJobService;
import com.github.frameworkaly.service.IntentServiceImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.e("wh", "Main onCreate ");
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
    Log.e("wh", "Main onResume ");
    super.onResume();

    PowerManager pw = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
    PowerManager.WakeLock wakeLock =
        pw.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "lotty:MainActivity");
    // 获取cpu唤醒锁，最多保持事件
    //wakeLock.acquire(3000);
    // TODO: 2020/3/29 someThing todo

    // 释放cpu唤醒锁
    //wakeLock.release();

  }

  @Override protected void onPostResume() {
    Log.e("wh", "Main onPostResume ");
    super.onPostResume();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) public void deleverJob() {
    JobScheduler js =
        (JobScheduler) this.getBaseContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
    JobInfo.Builder builder = new JobInfo.Builder(333, new ComponentName(this.getApplication(),
        IJobService.class));
    JobInfo build = builder.setMinimumLatency(1000).build();
    js.schedule(build);
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
        //Intent intent = new Intent(this.getApplicationContext(), IntentServiceImpl.class);
        //getApplicationContext().startService(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          deleverJob();
        }
        break;
      default:
        break;
    }
  }
}
