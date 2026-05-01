package reactor.core.scheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.SchedulerState;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class SchedulerState<T> {
    final T currentResource;

    @Nullable
    final T initialResource;
    final Mono<Void> onDispose;

    interface DisposeAwaiter<T> {
        boolean await(T t, long j, TimeUnit timeUnit) throws InterruptedException;
    }

    private SchedulerState(@Nullable T t, T t2, Mono<Void> mono) {
        this.initialResource = t;
        this.currentResource = t2;
        this.onDispose = mono;
    }

    static <T> SchedulerState<T> init(T t) {
        return new SchedulerState<>(t, t, Mono.empty());
    }

    static <T> SchedulerState<T> transition(@Nullable final T t, T t2, final DisposeAwaiter<T> disposeAwaiter) {
        Mono<T> next;
        if (t == null) {
            next = Mono.empty();
        } else {
            next = Flux.create(new Consumer() { // from class: reactor.core.scheduler.SchedulerState$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SchedulerState.DisposeAwaiterRunnable.awaitInPool(disposeAwaiter, t, (FluxSink) obj, 100);
                }
            }).replay().refCount().next();
        }
        return new SchedulerState<>(t, t2, next);
    }

    static class DisposeAwaiterRunnable<T> implements Runnable {
        static final ScheduledExecutorService TRANSITION_AWAIT_POOL;
        private final int awaitMs;
        private final DisposeAwaiter<T> awaiter;
        volatile boolean cancelled;
        private final T initial;
        private final FluxSink<Void> sink;

        static {
            ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(0);
            scheduledThreadPoolExecutor.setKeepAliveTime(10L, TimeUnit.SECONDS);
            scheduledThreadPoolExecutor.allowCoreThreadTimeOut(true);
            scheduledThreadPoolExecutor.setMaximumPoolSize(Schedulers.DEFAULT_POOL_SIZE);
            TRANSITION_AWAIT_POOL = scheduledThreadPoolExecutor;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static <R> void awaitInPool(DisposeAwaiter<R> disposeAwaiter, R r, FluxSink<Void> fluxSink, int i) {
            TRANSITION_AWAIT_POOL.submit(new DisposeAwaiterRunnable(disposeAwaiter, r, fluxSink, i));
        }

        DisposeAwaiterRunnable(DisposeAwaiter<T> disposeAwaiter, T t, FluxSink<Void> fluxSink, int i) {
            this.awaiter = disposeAwaiter;
            this.initial = t;
            this.sink = fluxSink;
            this.awaitMs = i;
            fluxSink.onCancel(new Disposable() { // from class: reactor.core.scheduler.SchedulerState$DisposeAwaiterRunnable$$ExternalSyntheticLambda0
                @Override // reactor.core.Disposable
                public final void dispose() {
                    this.f$0.cancel();
                }
            });
        }

        void cancel() {
            this.cancelled = true;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.cancelled) {
                return;
            }
            try {
                if (this.awaiter.await(this.initial, this.awaitMs, TimeUnit.MILLISECONDS)) {
                    this.sink.complete();
                } else if (this.cancelled) {
                } else {
                    TRANSITION_AWAIT_POOL.submit(this);
                }
            } catch (InterruptedException unused) {
            }
        }
    }
}
