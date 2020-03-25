package com.github.frameworkaly.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

public class IntentServiceImpl extends IntentService {

  public IntentServiceImpl(String name) {
    super(name);
  }

  public IntentServiceImpl() {
    super("");
  }

  @Override protected void onHandleIntent(@Nullable Intent intent) {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
