package com.github.lotty;

import android.app.Application;

import com.github.hook.RootHooker;

/**
 * @author lotty
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RootHooker.init(this);
    }
}
