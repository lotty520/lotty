package com.github.lotty;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * @author lotty
 */
public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    //RootHooker.init(this);
  }
}


