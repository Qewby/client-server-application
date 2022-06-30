package com.qewby.network.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);

    public static void stopThreadPool() {
        threadPool.shutdown();
    }

    public static Future<?> submitTask(final Runnable task) {
        return threadPool.submit(task);
    }

    public static Future<?> submitTask(final Runnable task, final long delay, final TimeUnit unit) {
        final Future<?> handler = threadPool.submit(task);
        threadPool.schedule(new Runnable() {
            public void run() {
                handler.cancel(false);
            }
        }, delay, unit);
        return handler;
    }
}
