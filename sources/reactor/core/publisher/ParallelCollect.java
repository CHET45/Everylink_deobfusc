package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelCollect<T, C> extends ParallelFlux<C> implements Scannable, Fuseable {
    final BiConsumer<? super C, ? super T> collector;
    final Supplier<? extends C> initialCollection;
    final ParallelFlux<? extends T> source;

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    ParallelCollect(ParallelFlux<? extends T> parallelFlux, Supplier<? extends C> supplier, BiConsumer<? super C, ? super T> biConsumer) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.initialCollection = supplier;
        this.collector = biConsumer;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super C>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super Object>[] coreSubscriberArr2 = new CoreSubscriber[length];
            for (int i = 0; i < length; i++) {
                try {
                    coreSubscriberArr2[i] = new ParallelCollectSubscriber(coreSubscriberArr[i], Objects.requireNonNull(this.initialCollection.get(), "The initialSupplier returned a null value"), this.collector);
                } catch (Throwable th) {
                    reportError(coreSubscriberArr, Operators.onOperatorError(th, coreSubscriberArr[i].currentContext()));
                    return;
                }
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }

    void reportError(Subscriber<?>[] subscriberArr, Throwable th) {
        for (Subscriber<?> subscriber : subscriberArr) {
            Operators.error(subscriber, th);
        }
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    static final class ParallelCollectSubscriber<T, C> extends Operators.BaseFluxToMonoOperator<T, C> {
        C collection;
        final BiConsumer<? super C, ? super T> collector;
        boolean done;

        ParallelCollectSubscriber(CoreSubscriber<? super C> coreSubscriber, C c, BiConsumer<? super C, ? super T> biConsumer) {
            super(coreSubscriber);
            this.collection = c;
            this.collector = biConsumer;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                synchronized (this) {
                    C c = this.collection;
                    if (c != null) {
                        this.collector.accept(c, t);
                    }
                }
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2280s, th, t, this.actual.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            C c;
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            synchronized (this) {
                c = this.collection;
                this.collection = null;
            }
            if (c == null) {
                return;
            }
            Operators.onDiscard(c, this.actual.currentContext());
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            completePossiblyEmpty();
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, org.reactivestreams.Subscription
        public void cancel() {
            C c;
            this.f2280s.cancel();
            synchronized (this) {
                c = this.collection;
                this.collection = null;
            }
            if (c != null) {
                Operators.onDiscard(c, this.actual.currentContext());
            }
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        C accumulatedValue() {
            C c;
            synchronized (this) {
                c = this.collection;
                this.collection = null;
            }
            return c;
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.collection == null && !this.done);
            }
            return super.scanUnsafe(attr);
        }
    }
}
