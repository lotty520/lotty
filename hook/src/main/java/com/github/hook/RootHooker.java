package com.github.hook;

import android.app.Application;

import com.github.app.HookLifecycleMonitor;

public class RootHooker {

    public static void init(Application app) {
        app.registerActivityLifecycleCallbacks(HookLifecycleMonitor.obtain());
        ContentResolverHooker.init(app.getBaseContext());

    }

}
