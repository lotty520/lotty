package com.github.lotty;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

public class DispatchActivtiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_activtiy);
        ViewGroup vg = findViewById(R.id.view_group);
        vg.requestDisallowInterceptTouchEvent(true);
    }
}
