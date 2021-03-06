package com.github.lotty;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.common.Router;

/**
 * @author lotty
 */
public class ComponentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
        findViewById(R.id.component_swipe).setOnClickListener(this);
        findViewById(R.id.component_image).setOnClickListener(this);
        findViewById(R.id.component_dispatch).setOnClickListener(this);
        findViewById(R.id.component_clock).setOnClickListener(this);
        findViewById(R.id.component_edit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.component_swipe:
                Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/swipe")).go();
                break;
            case R.id.component_image:
                Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/image")).go();
                break;
            case R.id.component_dispatch:
                Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/dispatch")).go();
                break;
            case R.id.component_clock:
                Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/clock")).go();
                break;
            case R.id.component_edit:
                Router.from(this).uri(Uri.parse("https://nav.github.com/lotty/edit")).go();
                break;
            default:
                break;
        }
    }

}
