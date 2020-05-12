package com.github.lotty.component;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import com.github.lotty.R;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_activtiy);
        ViewGroup vg = findViewById(R.id.view_group);
        vg.requestDisallowInterceptTouchEvent(true);
    }
}
