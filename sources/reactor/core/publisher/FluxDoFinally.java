package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxDoFinally<T> extends InternalFluxOperator<T, T> {
    final Consumer<SignalType> onFinally;

    static <T> CoreSubscriber<T> createSubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<SignalType> consumer) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new DoFinallyConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, consumer);
        }
        return new DoFinallySubscriber(coreSubscriber, consumer);
    }

    FluxDoFinally(Flux<? extends T> flux, Consumer<SignalType> consumer) {
        super(flux);
        this.onFinally = consumer;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return createSubscriber(coreSubscriber, this.onFinally);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static class DoFinallySubscriber<T> implements InnerOperator<T, T> {
        static final AtomicIntegerFieldUpdater<DoFinallySubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(DoFinallySubscriber.class, "once");
        final CoreSubscriber<? super T> actual;
        final Consumer<SignalType> onFinally;
        volatile int once;

        /* JADX INFO: renamed from: s */
        Subscription f2118s;

        DoFinallySubscriber(CoreSubscriber<? super T> coreSubscriber, Consumer<SignalType> consumer) {
            this.actual = coreSubscriber;
            this.onFinally = consumer;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2118s;
            }
            if (attr == Scannable.Attr.TERMINATED || attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.once == 1);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2118s, subscription)) {
                this.f2118s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            try {
                this.actual.onError(th);
            } finally {
                runFinally(SignalType.ON_ERROR);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
            runFinally(SignalType.ON_COMPLETE);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2118s.cancel();
            runFinally(SignalType.CANCEL);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2118s.request(j);
        }

        void runFinally(SignalType signalType) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                try {
                    this.onFinally.accept(signalType);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    Operators.onErrorDropped(th, this.actual.currentContext());
                }
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    static final class DoFinallyConditionalSubscriber<T> extends DoFinallySubscriber<T> implements Fuseable.ConditionalSubscriber<T> {
        DoFinallyConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, Consumer<SignalType> consumer) {
            super(conditionalSubscriber, consumer);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            return ((Fuseable.ConditionalSubscriber) this.actual).tryOnNext(t);
        }
    }
}
