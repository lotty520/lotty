package com.github.lotty.component;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.github.component.edit.PwdEditView;
import com.github.lotty.R;

/**
 * @author lotty
 */
public class EditActivity extends AppCompatActivity implements View.OnClickListener {

  private final static int MAX_PWD_LEN = 8;
  private final static int MIN_PWD_LEN = 4;

  private EditText mLengthEt;
  private PwdEditView mPwdEt;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);
    findViewById(R.id.edit_set_len_bt).setOnClickListener(this);
    mLengthEt = findViewById(R.id.edit_input_len);
    mPwdEt = findViewById(R.id.edit_self);
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.edit_set_len_bt:
        String len = mLengthEt.getText().toString();
        if (!TextUtils.isEmpty(len)) {
          try {
            int realLength = Integer.parseInt(len);
            if (realLength >= MIN_PWD_LEN && realLength <= MAX_PWD_LEN) {
              mPwdEt.setLength(realLength);
            }
          } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
          }
        }
        break;
      default:
        break;
    }
  }
}
