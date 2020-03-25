package com.github.lotty;

import android.app.IntentService;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.github.component.list.EdgeSlideView;
import java.util.Arrays;

public class ClockAvtivity extends AppCompatActivity implements View.OnClickListener {

  private static final String[] STRINGS = {
      "A", "B", "C", "D", "E", "F", "G", "H"
  };

  private static final String[] STRINGS_EX = {
      "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "#"
  };

  private EdgeSlideView slideView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clock_avtivity);
    //findViewById(R.id.component_deal).setOnClickListener(this);
    //findViewById(R.id.component_change).setOnClickListener(this);
    //findViewById(R.id.component_text_size).setOnClickListener(this);
    //
    //slideView = findViewById(R.id.component_slide);

    LocalBroadcastManager instance = LocalBroadcastManager.getInstance(this);
    instance.registerReceiver(null,null);
  }

  @Override public void onClick(View v) {
    //int id = v.getId();
    //switch (id) {
    //  case R.id.component_deal:
    //    slideView.setKeys(Arrays.asList(STRINGS_EX));
    //    break;
    //  case R.id.component_change:
    //    slideView.setSelectPosition((int) (Math.random() * 8));
    //    break;
    //  case R.id.component_text_size:
    //    slideView.setTextSize(48);
    //    break;
    //  default:
    //    break;
    //}
  }
}
