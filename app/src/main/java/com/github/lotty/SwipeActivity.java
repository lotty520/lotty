package com.github.lotty;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.component.SwipeLayout;

public class SwipeActivity extends AppCompatActivity implements View.OnClickListener {

    private SwipeLayout swipeLayout;


    private SwipeLayout.SwipeListener swipeListener = new SwipeLayout.SwipeListener() {
        @Override
        public void onRefreshing() {
            Log.e("wh", "onRefreshing");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        swipeLayout = findViewById(R.id.swipe);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.finish).setOnClickListener(this);
        swipeLayout.setSwipeListener(swipeListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                // TODO: 2019-12-17
                break;
            case R.id.finish:
                swipeLayout.notifySwipeFinished();
                break;
            default:
                break;
        }
    }
}
