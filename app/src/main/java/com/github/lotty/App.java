package com.github.lotty;

import android.app.Application;
import android.view.Choreographer;

/**
 * @author lotty
 */
public class App extends Application implements Choreographer.FrameCallback {

    @Override
    public void onCreate() {
        super.onCreate();
        Choreographer choreographer = Choreographer.getInstance();
        choreographer.postFrameCallback(this);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
    }
}


