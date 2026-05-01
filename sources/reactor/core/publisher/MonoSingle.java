package reactor.core.publisher;

import java.util.NoSuchElementException;
import java.util.Objects;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSingle<T> extends MonoFromFluxOperator<T, T> {
    final boolean completeOnEmpty;
    final T defaultValue;

    MonoSingle(Flux<? extends T> flux) {
        super(flux);
        this.defaultValue = null;
        this.completeOnEmpty = false;
    }

    MonoSingle(Flux<? extends T> flux, @Nullable T t, boolean z) {
        super(flux);
        this.defaultValue = z ? t : (T) Objects.requireNonNull(t, "defaultValue");
        this.completeOnEmpty = z;
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new SingleSubscriber(coreSubscriber, this.defaultValue, this.completeOnEmpty);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SingleSubscriber<T> extends Operators.MonoInnerProducerBase<T> implements InnerConsumer<T> {
        final boolean completeOnEmpty;
        int count;

        @Nullable
        final T defaultValue;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2270s;

        @Override // reactor.core.publisher.Operators.MonoInnerProducerBase, reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2270s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return actual().currentContext();
        }

        SingleSubscriber(CoreSubscriber<? super T> coreSubscriber, @Nullable T t, boolean z) {
            super(coreSubscriber);
            this.defaultValue = t;
            this.completeOnEmpty = z;
        }

        @Override // reactor.core.publisher.Operators.MonoInnerProducerBase
        public void doOnRequest(long j) {
            this.f2270s.request(Long.MAX_VALUE);
        }

        @Override // reactor.core.publisher.Operators.MonoInnerProducerBase
        public void doOnCancel() {
            this.f2270s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2270s, subscription)) {
                this.f2270s = subscription;
                actual().onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (isCancelled()) {
                discard(t);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, actual().currentContext());
                return;
            }
            int i = this.count + 1;
            this.count = i;
            if (i > 1) {
                discard(t);
                cancel();
                onError(new IndexOutOfBoundsException("Source emitted more than one item"));
                return;
            }
            setValue(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, actual().currentContext());
                return;
            }
            this.done = true;
            discardTheValue();
            actual().onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            int i = this.count;
            if (i != 0) {
                if (i == 1) {
                    complete();
                }
            } else {
                if (this.completeOnEmpty) {
                    actual().onComplete();
                    return;
                }
                T t = this.defaultValue;
                if (t != null) {
                    complete(t);
                } else {
                    actual().onError(Operators.onOperatorError(this, new NoSuchElementException("Source was empty"), actual().currentContext()));
                }
            }
        }
    }
}
