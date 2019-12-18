package com.github.lotty;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean hasPms(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.algorithm).setOnClickListener(this);
        findViewById(R.id.hook).setOnClickListener(this);
        findViewById(R.id.grant).setOnClickListener(this);
        findViewById(R.id.component).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        long l = System.currentTimeMillis();
        switch (v.getId()) {
            case R.id.grant:
                Toast.makeText(this, "空实现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.component:
                startActivity(new Intent(this, SwipeActivity.class));
                break;
            default:
                break;
        }
    }
}
