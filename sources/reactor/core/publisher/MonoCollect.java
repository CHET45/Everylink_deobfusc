package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoCollect<T, R> extends MonoFromFluxOperator<T, R> implements Fuseable {
    final BiConsumer<? super R, ? super T> action;
    final Supplier<R> supplier;

    MonoCollect(Flux<? extends T> flux, Supplier<R> supplier, BiConsumer<? super R, ? super T> biConsumer) {
        super(flux);
        this.supplier = (Supplier) Objects.requireNonNull(supplier, "supplier");
        this.action = (BiConsumer) Objects.requireNonNull(biConsumer);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        return new CollectSubscriber(coreSubscriber, this.action, Objects.requireNonNull(this.supplier.get(), "The supplier returned a null container"));
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class CollectSubscriber<T, R> extends Operators.BaseFluxToMonoOperator<T, R> {
        final BiConsumer<? super R, ? super T> action;
        R container;
        boolean done;

        CollectSubscriber(CoreSubscriber<? super R> coreSubscriber, BiConsumer<? super R, ? super T> biConsumer, R r) {
            super(coreSubscriber);
            this.action = biConsumer;
            this.container = r;
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : super.scanUnsafe(attr);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                synchronized (this) {
                    R r = this.container;
                    if (r != null) {
                        this.action.accept(r, t);
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
            R r;
            if (this.done) {
                Operators.onErrorDropped(th, this.actual.currentContext());
                return;
            }
            this.done = true;
            synchronized (this) {
                r = this.container;
                this.container = null;
            }
            if (r == null) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            } else {
                discard(r);
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
            R r;
            super.cancel();
            synchronized (this) {
                r = this.container;
                this.container = null;
            }
            if (r != null) {
                discard(r);
            }
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        R accumulatedValue() {
            R r;
            synchronized (this) {
                r = this.container;
                this.container = null;
            }
            return r;
        }

        void discard(R r) {
            if (r instanceof Collection) {
                Operators.onDiscardMultiple((Collection<?>) r, this.actual.currentContext());
            } else {
                Operators.onDiscard(r, this.actual.currentContext());
            }
        }
    }
}
