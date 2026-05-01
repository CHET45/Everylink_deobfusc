package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxIndex;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxIndexFuseable<T, I> extends InternalFluxOperator<T, I> implements Fuseable {
    private final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

    FluxIndexFuseable(Flux<T> flux, BiFunction<? super Long, ? super T, ? extends I> biFunction) {
        super(flux);
        this.indexMapper = FluxIndex.NullSafeIndexMapper.create((BiFunction) Objects.requireNonNull(biFunction, "indexMapper must be non null"));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super I> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new IndexFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.indexMapper);
        }
        return new IndexFuseableSubscriber(coreSubscriber, this.indexMapper);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class IndexFuseableSubscriber<I, T> implements InnerOperator<T, I>, Fuseable.QueueSubscription<I> {
        final CoreSubscriber<? super I> actual;
        boolean done;
        long index;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2144s;
        int sourceMode;

        IndexFuseableSubscriber(CoreSubscriber<? super I> coreSubscriber, BiFunction<? super Long, ? super T, ? extends I> biFunction) {
            this.actual = coreSubscriber;
            this.indexMapper = biFunction;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2144s, subscription)) {
                this.f2144s = (Fuseable.QueueSubscription) subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // java.util.Queue
        @Nullable
        public I poll() {
            T tPoll = this.f2144s.poll();
            if (tPoll == null) {
                return null;
            }
            long j = this.index;
            I iApply = this.indexMapper.apply(Long.valueOf(j), tPoll);
            this.index = j + 1;
            return iApply;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.index;
            try {
                I iApply = this.indexMapper.apply(Long.valueOf(j), t);
                this.index = j + 1;
                this.actual.onNext(iApply);
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2144s, th, t, this.actual.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super I> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2144s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2144s.cancel();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2144s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2144s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if (this.indexMapper != Flux.TUPLE2_BIFUNCTION && (i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2144s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2144s.size();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2144s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class IndexFuseableConditionalSubscriber<I, T> implements InnerOperator<T, I>, Fuseable.ConditionalSubscriber<T>, Fuseable.QueueSubscription<I> {
        final Fuseable.ConditionalSubscriber<? super I> actual;
        boolean done;
        long index;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

        /* JADX INFO: renamed from: s */
        Fuseable.QueueSubscription<T> f2143s;
        int sourceMode;

        IndexFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super I> conditionalSubscriber, BiFunction<? super Long, ? super T, ? extends I> biFunction) {
            this.actual = conditionalSubscriber;
            this.indexMapper = biFunction;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2143s, subscription)) {
                this.f2143s = (Fuseable.QueueSubscription) subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // java.util.Queue
        @Nullable
        public I poll() {
            T tPoll = this.f2143s.poll();
            if (tPoll == null) {
                return null;
            }
            long j = this.index;
            I iApply = this.indexMapper.apply(Long.valueOf(j), tPoll);
            this.index = j + 1;
            return iApply;
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            long j = this.index;
            try {
                I iApply = this.indexMapper.apply(Long.valueOf(j), t);
                this.index = j + 1;
                return this.actual.tryOnNext(iApply);
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2143s, th, t, this.actual.currentContext()));
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.index;
            try {
                I iApply = this.indexMapper.apply(Long.valueOf(j), t);
                this.index = j + 1;
                this.actual.onNext(iApply);
            } catch (Throwable th) {
                onError(Operators.onOperatorError(this.f2143s, th, t, this.actual.currentContext()));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                this.done = true;
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super I> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2143s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2143s.cancel();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2143s.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2143s.clear();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            if (this.indexMapper != Flux.TUPLE2_BIFUNCTION && (i & 4) != 0) {
                return 0;
            }
            int iRequestFusion = this.f2143s.requestFusion(i);
            this.sourceMode = iRequestFusion;
            return iRequestFusion;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2143s.size();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2143s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
