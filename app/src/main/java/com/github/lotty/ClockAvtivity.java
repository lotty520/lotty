package com.github.lotty;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.github.component.poke.PokerView;

public class ClockAvtivity extends AppCompatActivity implements View.OnClickListener {

  private PokerView pokerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_clock_avtivity);
    findViewById(R.id.component_deal).setOnClickListener(this);

    pokerView = findViewById(R.id.component_poker);
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.component_deal:
        pokerView.deal((int) (Math.random() * 22 + 4));
        break;
      default:
        break;
    }
  }
}
