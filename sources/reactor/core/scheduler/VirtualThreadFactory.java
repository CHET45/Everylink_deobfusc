package reactor.core.scheduler;

import java.lang.Thread;
import java.util.concurrent.ThreadFactory;
import java.util.function.BiConsumer;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
class VirtualThreadFactory implements ThreadFactory, Thread.UncaughtExceptionHandler {
    VirtualThreadFactory(String str, boolean z, @Nullable BiConsumer<Thread, Throwable> biConsumer) {
        throw new UnsupportedOperationException("Virtual Threads are not supported in JVM lower than 21");
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(@NonNull Runnable runnable) {
        throw new UnsupportedOperationException("Virtual Threads are not supported in JVM lower than 21");
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        throw new UnsupportedOperationException("Virtual Threads are not supported in JVM lower than 21");
    }
}
