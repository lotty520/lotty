package com.github.lotty;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Choreographer;

/**
 * @author lotty
 */
public class App extends Application implements Choreographer.FrameCallback {

  @Override
  public void onCreate() {
    super.onCreate();
    //RootHooker.init(this);

    Choreographer choreographer = Choreographer.getInstance();
    choreographer.postFrameCallback(this);
    try {
      Bundle meta =
          getPackageManager().getApplicationInfo(getPackageName(),
              PackageManager.GET_META_DATA).metaData;
      Log.e("wh", "meta:channel= " + meta.getString("channel"));
      Log.e("wh", "meta:client= " + meta.getString("client"));
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

  }

  @Override public void doFrame(long frameTimeNanos) {
    Log.e("wh", "do frame...");
  }
}


