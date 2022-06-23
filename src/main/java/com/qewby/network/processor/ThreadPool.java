package com.qewby.network.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPool {
    
    private static ExecutorService threadPool = Executors.newFixedThreadPool(8);

    public static void stopThreadPool() {
        threadPool.shutdown();
    }

    public static Future<?> submitTask(Runnable task) {
        return threadPool.submit(task);
    }
}
