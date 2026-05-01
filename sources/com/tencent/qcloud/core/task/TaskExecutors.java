package com.tencent.qcloud.core.task;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes4.dex */
public class TaskExecutors {
    public static final ThreadPoolExecutor COMMAND_EXECUTOR;
    public static ThreadPoolExecutor DOWNLOAD_EXECUTOR = null;
    public static int DOWNLOAD_THREAD_COUNT = 6;
    public static final UIThreadExecutor UI_THREAD_EXECUTOR;
    public static ThreadPoolExecutor UPLOAD_EXECUTOR = null;
    public static int UPLOAD_THREAD_COUNT = 4;

    public static void initExecutor(int i, int i2) {
        UPLOAD_THREAD_COUNT = i;
        DOWNLOAD_THREAD_COUNT = i2;
        UPLOAD_EXECUTOR.setCorePoolSize(i);
        UPLOAD_EXECUTOR.setMaximumPoolSize(UPLOAD_THREAD_COUNT);
        DOWNLOAD_EXECUTOR.setCorePoolSize(DOWNLOAD_THREAD_COUNT);
        DOWNLOAD_EXECUTOR.setMaximumPoolSize(DOWNLOAD_THREAD_COUNT);
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(Integer.MAX_VALUE), new TaskThreadFactory("Command-", 8));
        COMMAND_EXECUTOR = threadPoolExecutor;
        int i = UPLOAD_THREAD_COUNT;
        UPLOAD_EXECUTOR = new ThreadPoolExecutor(i, i, 5L, TimeUnit.SECONDS, new PriorityBlockingQueue(), new TaskThreadFactory("Upload-", 3));
        int i2 = DOWNLOAD_THREAD_COUNT;
        DOWNLOAD_EXECUTOR = new ThreadPoolExecutor(i2, i2, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue(Integer.MAX_VALUE), new TaskThreadFactory("Download-", 3));
        UI_THREAD_EXECUTOR = new UIThreadExecutor();
        UPLOAD_EXECUTOR.allowCoreThreadTimeOut(true);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        DOWNLOAD_EXECUTOR.allowCoreThreadTimeOut(true);
    }

    static final class TaskThreadFactory implements ThreadFactory {
        private final AtomicInteger increment = new AtomicInteger(1);
        private final int priority;
        private final String tag;

        TaskThreadFactory(String str, int i) {
            this.tag = str;
            this.priority = i;
        }

        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "QCloud-" + this.tag + this.increment.getAndIncrement());
            thread.setDaemon(false);
            thread.setPriority(this.priority);
            return thread;
        }
    }
}
