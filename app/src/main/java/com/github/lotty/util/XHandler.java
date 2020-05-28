package com.github.lotty.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 获取主线程与一个后台工作线程 handler 的便捷类
 * <p>
 * Created by lotty_wh on 2017/5/5.
 */

public final class XHandler {

    private static final String TAG = "XHandler";
    private static final Handler sUiHandler = new Handler(Looper.getMainLooper());

    private static final HandlerThread sWorkingThread = new HandlerThread(TAG);
    private static final Handler sWorkingHandler;

    static {
        // Initialize working thread handler
        sWorkingThread.start();
        sWorkingHandler = new Handler(sWorkingThread.getLooper());
    }

    private XHandler() {
    }

    public static void runOnUiThread(Runnable runnable) {
        sUiHandler.post(runnable);
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delay) {
        sUiHandler.postDelayed(runnable, delay);
    }

    public static void runOnWorkingThread(Runnable runnable) {
        sWorkingHandler.post(runnable);
    }

    public static void runOnWorkingThreadDelay(Runnable runnable, long delay) {
        sWorkingHandler.postDelayed(runnable, delay);
    }
}
