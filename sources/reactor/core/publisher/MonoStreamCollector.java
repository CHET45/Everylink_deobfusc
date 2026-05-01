package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoStreamCollector<T, A, R> extends MonoFromFluxOperator<T, R> implements Fuseable {
    final Collector<? super T, A, ? extends R> collector;

    MonoStreamCollector(Flux<? extends T> flux, Collector<? super T, A, ? extends R> collector) {
        super(flux);
        this.collector = (Collector) Objects.requireNonNull(collector, "collector");
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        return new StreamCollectorSubscriber(coreSubscriber, this.collector.supplier().get(), this.collector.accumulator(), this.collector.finisher());
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class StreamCollectorSubscriber<T, A, R> extends Operators.BaseFluxToMonoOperator<T, R> {
        final BiConsumer<? super A, ? super T> accumulator;
        A container;
        boolean done;
        final Function<? super A, ? extends R> finisher;

        StreamCollectorSubscriber(CoreSubscriber<? super R> coreSubscriber, A a, BiConsumer<? super A, ? super T> biConsumer, Function<? super A, ? extends R> function) {
            super(coreSubscriber);
            this.container = a;
            this.accumulator = biConsumer;
            this.finisher = function;
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : super.scanUnsafe(attr);
        }

        void discardIntermediateContainer(A a) {
            Context contextCurrentContext = this.actual.currentContext();
            if (a instanceof Collection) {
                Operators.onDiscardMultiple((Collection<?>) a, contextCurrentContext);
            } else {
                Operators.onDiscard(a, contextCurrentContext);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                synchronized (this) {
                    A a = this.container;
                    if (a != null) {
                        this.accumulator.accept(a, t);
                    } else {
                        Operators.onDiscard(t, this.actual.currentContext());
                    }
                }
            } catch (Throwable th) {
                Context contextCurrentContext = this.actual.currentContext();
                Operators.onDiscard(t, contextCurrentContext);
                onError(Operators.onOperatorError(this.f2280s, th, t, contextCurrentContext));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            A a;
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            synchronized (this) {
                a = this.container;
                this.container = null;
            }
            if (a == null) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                discardIntermediateContainer(a);
                this.actual.onError(th);
            }
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
            A a;
            super.cancel();
            synchronized (this) {
                a = this.container;
                this.container = null;
            }
            if (a != null) {
                discardIntermediateContainer(a);
            }
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        R accumulatedValue() {
            A a;
            synchronized (this) {
                a = this.container;
                this.container = null;
            }
            if (a == null) {
                return null;
            }
            try {
                R rApply = this.finisher.apply(a);
                if (rApply == null) {
                    this.actual.onError(Operators.onOperatorError(new NullPointerException("Collector returned null"), this.actual.currentContext()));
                }
                return rApply;
            } catch (Throwable th) {
                discardIntermediateContainer(a);
                this.actual.onError(Operators.onOperatorError(th, this.actual.currentContext()));
                return null;
            }
        }
    }
}
