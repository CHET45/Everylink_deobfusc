package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxIndex<T, I> extends InternalFluxOperator<T, I> {
    private final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

    FluxIndex(Flux<T> flux, BiFunction<? super Long, ? super T, ? extends I> biFunction) {
        super(flux);
        this.indexMapper = NullSafeIndexMapper.create((BiFunction) Objects.requireNonNull(biFunction, "indexMapper must be non null"));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super I> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new IndexConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this.indexMapper);
        }
        return new IndexSubscriber(coreSubscriber, this.indexMapper);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class IndexSubscriber<T, I> implements InnerOperator<T, I> {
        final CoreSubscriber<? super I> actual;
        boolean done;
        long index = 0;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

        /* JADX INFO: renamed from: s */
        Subscription f2142s;

        IndexSubscriber(CoreSubscriber<? super I> coreSubscriber, BiFunction<? super Long, ? super T, ? extends I> biFunction) {
            this.actual = coreSubscriber;
            this.indexMapper = biFunction;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2142s, subscription)) {
                this.f2142s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
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
                onError(Operators.onOperatorError(this.f2142s, th, t, this.actual.currentContext()));
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
            this.f2142s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2142s.cancel();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2142s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static final class IndexConditionalSubscriber<T, I> implements InnerOperator<T, I>, Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super I> actual;
        boolean done;
        long index;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

        /* JADX INFO: renamed from: s */
        Subscription f2141s;

        IndexConditionalSubscriber(Fuseable.ConditionalSubscriber<? super I> conditionalSubscriber, BiFunction<? super Long, ? super T, ? extends I> biFunction) {
            this.actual = conditionalSubscriber;
            this.indexMapper = biFunction;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2141s, subscription)) {
                this.f2141s = subscription;
                this.actual.onSubscribe(this);
            }
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
                onError(Operators.onOperatorError(this.f2141s, th, t, this.actual.currentContext()));
                return true;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
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
                onError(Operators.onOperatorError(this.f2141s, th, t, this.actual.currentContext()));
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
            this.f2141s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2141s.cancel();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2141s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }

    static class NullSafeIndexMapper<T, I> implements BiFunction<Long, T, I> {
        private final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

        private NullSafeIndexMapper(BiFunction<? super Long, ? super T, ? extends I> biFunction) {
            this.indexMapper = biFunction;
        }

        @Override // java.util.function.BiFunction
        public I apply(Long l, T t) {
            I iApply = this.indexMapper.apply(l, t);
            if (iApply != null) {
                return iApply;
            }
            throw new NullPointerException("indexMapper returned a null value at raw index " + l + " for value " + t);
        }

        static <T, I> BiFunction<? super Long, ? super T, ? extends I> create(BiFunction<? super Long, ? super T, ? extends I> biFunction) {
            return biFunction == Flux.TUPLE2_BIFUNCTION ? biFunction : new NullSafeIndexMapper(biFunction);
        }
    }
}
