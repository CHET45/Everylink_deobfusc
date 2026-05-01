package reactor.core.publisher;

import com.azure.core.implementation.logging.LoggingKeys;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxCancelOn<T> extends InternalFluxOperator<T, T> {
    final Scheduler scheduler;

    public FluxCancelOn(Flux<T> flux, Scheduler scheduler) {
        super(flux);
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new CancelSubscriber(coreSubscriber, this.scheduler);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
    }

    static final class CancelSubscriber<T> implements InnerOperator<T, T>, Runnable {
        static final AtomicIntegerFieldUpdater<CancelSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(CancelSubscriber.class, LoggingKeys.CANCELLED_ERROR_TYPE);
        final CoreSubscriber<? super T> actual;
        volatile int cancelled = 0;

        /* JADX INFO: renamed from: s */
        Subscription f2095s;
        final Scheduler scheduler;

        CancelSubscriber(CoreSubscriber<? super T> coreSubscriber, Scheduler scheduler) {
            this.actual = coreSubscriber;
            this.scheduler = scheduler;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2095s, subscription)) {
                this.f2095s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2095s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.cancelled == 1);
            }
            return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.f2095s.cancel();
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2095s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                try {
                    this.scheduler.schedule(this);
                } catch (RejectedExecutionException e) {
                    throw Operators.onRejectedExecution(e, this.actual.currentContext());
                }
            }
        }
    }
}
