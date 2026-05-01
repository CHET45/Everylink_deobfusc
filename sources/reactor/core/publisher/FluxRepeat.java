package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;

/* JADX INFO: loaded from: classes5.dex */
final class FluxRepeat<T> extends InternalFluxOperator<T, T> {
    final long times;

    FluxRepeat(Flux<? extends T> flux, long j) {
        super(flux);
        if (j <= 0) {
            throw new IllegalArgumentException("times > 0 required");
        }
        this.times = j;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        RepeatSubscriber repeatSubscriber = new RepeatSubscriber(this.source, coreSubscriber, this.times + 1);
        coreSubscriber.onSubscribe(repeatSubscriber);
        if (repeatSubscriber.isCancelled()) {
            return null;
        }
        repeatSubscriber.onComplete();
        return null;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class RepeatSubscriber<T> extends Operators.MultiSubscriptionSubscriber<T, T> {
        static final AtomicIntegerFieldUpdater<RepeatSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(RepeatSubscriber.class, "wip");
        long produced;
        long remaining;
        final CorePublisher<? extends T> source;
        volatile int wip;

        RepeatSubscriber(CorePublisher<? extends T> corePublisher, CoreSubscriber<? super T> coreSubscriber, long j) {
            super(coreSubscriber);
            this.source = corePublisher;
            this.remaining = j;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.produced++;
            this.actual.onNext((Object) t);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            long j = this.remaining;
            if (j != Long.MAX_VALUE) {
                if (j == 0) {
                    this.actual.onComplete();
                    return;
                }
                this.remaining = j - 1;
            }
            resubscribe();
        }

        void resubscribe() {
            if (WIP.getAndIncrement(this) == 0) {
                while (!isCancelled()) {
                    long j = this.produced;
                    if (j != 0) {
                        this.produced = 0L;
                        produced(j);
                    }
                    this.source.subscribe((CoreSubscriber<? super Object>) this);
                    if (WIP.decrementAndGet(this) == 0) {
                        return;
                    }
                }
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
