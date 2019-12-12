package com.github.lotty;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String[] PERMISSION_ALL = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.algorithm).setOnClickListener(this);
        findViewById(R.id.hook).setOnClickListener(this);
        findViewById(R.id.grant).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grant:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSION_ALL, 11);
                }
                break;
            case R.id.hook:
                if (hasPms(this, Manifest.permission.READ_CONTACTS)) {
                    Cursor query = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, "11", null, null);
                    String[] columnNames = query.getColumnNames();
                    Log.e("wh",String.join(",",columnNames));
                }
                break;
            default:
                break;
        }
    }


    public static boolean hasPms(Context context, String permission) {
        return context.checkCallingOrSelfPermission(permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
