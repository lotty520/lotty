package com.github.lotty;

import android.app.Application;
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
  }

  @Override public void doFrame(long frameTimeNanos) {
    Log.e("wh", "start sleep");
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Log.e("wh", "stop sleep");
  }
}


