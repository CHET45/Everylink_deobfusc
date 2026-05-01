package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoElementAt<T> extends MonoFromFluxOperator<T, T> implements Fuseable {
    final T defaultValue;
    final long index;

    MonoElementAt(Flux<? extends T> flux, long j) {
        super(flux);
        if (j < 0) {
            throw new IndexOutOfBoundsException("index >= required but it was " + j);
        }
        this.index = j;
        this.defaultValue = null;
    }

    MonoElementAt(Flux<? extends T> flux, long j, T t) {
        super(flux);
        if (j < 0) {
            throw new IndexOutOfBoundsException("index >= required but it was " + j);
        }
        this.index = j;
        this.defaultValue = (T) Objects.requireNonNull(t, "defaultValue");
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new ElementAtSubscriber(coreSubscriber, this.index, this.defaultValue);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ElementAtSubscriber<T> extends Operators.BaseFluxToMonoOperator<T, T> {

        @Nullable
        final T defaultValue;
        boolean done;
        long index;
        final long target;

        ElementAtSubscriber(CoreSubscriber<? super T> coreSubscriber, long j, @Nullable T t) {
            super(coreSubscriber);
            this.index = j;
            this.target = j;
            this.defaultValue = t;
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : super.scanUnsafe(attr);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long j = this.index;
            if (j == 0) {
                this.done = true;
                this.f2280s.cancel();
                this.actual.onNext((Object) t);
                this.actual.onComplete();
                return;
            }
            this.index = j - 1;
            Operators.onDiscard(t, this.actual.currentContext());
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
            if (this.defaultValue != null) {
                completePossiblyEmpty();
            } else {
                this.actual.onError(Operators.onOperatorError(new IndexOutOfBoundsException("source had " + (this.target - this.index) + " elements, expected at least " + (this.target + 1)), this.actual.currentContext()));
            }
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        T accumulatedValue() {
            return this.defaultValue;
        }
    }
}
