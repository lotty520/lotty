package com.github.lotty;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.common.Router;
import com.github.frameworkaly.job.IJobService;
import com.github.lotty.util.IpScanner;
import com.github.lotty.util.SysUtil;
import com.github.lotty.util.TaskCenter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String HEX = "010300010025D5D1";
    private final static String HEX_N = "010300010025";
    private final static String HEX_READ = "010300010036941C";

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
        findViewById(R.id.framealy).setOnClickListener(this);
        findViewById(R.id.link).setOnClickListener(this);
        findViewById(R.id.mac).setOnClickListener(this);
        findViewById(R.id.info).setOnClickListener(this);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(new Fragment(), "testFragment");
        fragmentTransaction.commit();

        TaskCenter.sharedCenter().setReceivedCallback(new OnReceiveCallback());
        TaskCenter.sharedCenter().setDisconnectedCallback(new OnServerDisconnectedCallback());

        initOrientationDetect();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onResume() {
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
            case R.id.link:
                TaskCenter.sharedCenter().sendInstructions("192.186.4.1", 8080, SysUtil.hex2Byte(HEX));
                break;
            case R.id.mac:
                IpScanner scanner = new IpScanner();
                scanner.setOnScanListener(new IpScanner.OnScanListener() {
                    @Override
                    public void onScan(String mac) {
                        Log.e("wh", mac);
                    }
                });
                scanner.startScan();
                break;
            case R.id.info:
                TaskCenter.sharedCenter().sendInstructions("192.186.4.1", 8080, SysUtil.hex2Byte(HEX));
                break;

            default:
                break;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void deleverJob() {
        JobScheduler js =
                (JobScheduler) this.getBaseContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(333, new ComponentName(this.getApplication(),
                IJobService.class));
        JobInfo build = builder.setMinimumLatency(1000).build();
        js.schedule(build);
    }

    private void initOrientationDetect() {
        // 利用加速度传感器计算涉笔方向
        OrientationEventListener listener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {
            Log.e("wh","orientation: "+ orientation);
            }
        };
        if (listener.canDetectOrientation()) {
            listener.enable();
        }
    }

    static class OnServerConnectedCallback implements TaskCenter.OnServerConnectedCallbackBlock {

        @Override
        public void callback() {

        }
    }

    static class OnServerDisconnectedCallback implements TaskCenter.OnServerDisconnectedCallbackBlock {

        @Override
        public void callback(IOException e) {
            Log.e("wh", "断开连接");
        }
    }

    static class OnReceiveCallback implements TaskCenter.OnReceiveCallbackBlock {
        @Override
        public void callback(String msg) {
            Log.e("wh", msg + "");
        }
    }
}
