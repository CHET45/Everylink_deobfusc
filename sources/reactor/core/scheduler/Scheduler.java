package reactor.core.scheduler;

import java.util.concurrent.TimeUnit;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

/* JADX INFO: loaded from: classes5.dex */
public interface Scheduler extends Disposable {
    Worker createWorker();

    @Override // reactor.core.Disposable
    default void dispose() {
    }

    Disposable schedule(Runnable runnable);

    @Deprecated
    default void start() {
    }

    default Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
        throw Exceptions.failWithRejectedNotTimeCapable();
    }

    default Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
        throw Exceptions.failWithRejectedNotTimeCapable();
    }

    default long now(TimeUnit timeUnit) {
        if (timeUnit.compareTo(TimeUnit.MILLISECONDS) >= 0) {
            return timeUnit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
        return timeUnit.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    default Mono<Void> disposeGracefully() {
        return Mono.fromRunnable(new Runnable() { // from class: reactor.core.scheduler.Scheduler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.dispose();
            }
        });
    }

    default void init() {
        start();
    }

    public interface Worker extends Disposable {
        Disposable schedule(Runnable runnable);

        default Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        default Disposable schedulePeriodically(Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }
    }
}
