package com.github.lotty.mnn.common;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 全局线程池处理
 *
 * @author lotty
 */
public final class SdkThreadPool {

    private final ThreadPoolExecutor threadPoolExecutor;

    private SdkThreadPool() {
        int processors = Runtime.getRuntime().availableProcessors();
        threadPoolExecutor = new ThreadPoolExecutor(processors, processors + 3, 0,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new SdkThreadFactory());
    }

    public static void execute(Runnable runnable) {
        instance().threadPoolExecutor.execute(runnable);
    }

    public static SdkThreadPool instance() {
        return Holder.instance;
    }

    private static class SdkThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("XdCs");
            return thread;
        }
    }

    private static class Holder {
        final static SdkThreadPool instance = new SdkThreadPool();
    }
}
