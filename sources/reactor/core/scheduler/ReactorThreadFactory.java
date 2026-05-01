package reactor.core.scheduler;

import java.lang.Thread;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
class ReactorThreadFactory implements ThreadFactory, Supplier<String>, Thread.UncaughtExceptionHandler {
    private final AtomicLong counterReference;
    private final boolean daemon;
    private final String name;
    private final boolean rejectBlocking;

    @Nullable
    private final BiConsumer<Thread, Throwable> uncaughtExceptionHandler;

    ReactorThreadFactory(String str, AtomicLong atomicLong, boolean z, boolean z2, @Nullable BiConsumer<Thread, Throwable> biConsumer) {
        this.name = str;
        this.counterReference = atomicLong;
        this.daemon = z;
        this.rejectBlocking = z2;
        this.uncaughtExceptionHandler = biConsumer;
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(@NonNull Runnable runnable) {
        Thread thread;
        String str = this.name + "-" + this.counterReference.incrementAndGet();
        if (this.rejectBlocking) {
            thread = new NonBlockingThread(runnable, str);
        } else {
            thread = new Thread(runnable, str);
        }
        if (this.daemon) {
            thread.setDaemon(true);
        }
        if (this.uncaughtExceptionHandler != null) {
            thread.setUncaughtExceptionHandler(this);
        }
        return thread;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        BiConsumer<Thread, Throwable> biConsumer = this.uncaughtExceptionHandler;
        if (biConsumer == null) {
            return;
        }
        biConsumer.accept(thread, th);
    }

    @Override // java.util.function.Supplier
    public final String get() {
        return this.name;
    }

    static final class NonBlockingThread extends Thread implements NonBlocking {
        public NonBlockingThread(Runnable runnable, String str) {
            super(runnable, str);
        }
    }
}
