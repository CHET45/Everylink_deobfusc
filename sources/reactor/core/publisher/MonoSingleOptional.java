package reactor.core.publisher;

import java.util.Optional;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSingleOptional<T> extends InternalMonoOperator<T, Optional<T>> {
    MonoSingleOptional(Mono<? extends T> mono) {
        super(mono);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Optional<T>> coreSubscriber) {
        return new SingleOptionalSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class SingleOptionalSubscriber<T> extends Operators.MonoInnerProducerBase<Optional<T>> implements InnerConsumer<T> {
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2271s;

        @Override // reactor.core.publisher.Operators.MonoInnerProducerBase, reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2271s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return actual().currentContext();
        }

        SingleOptionalSubscriber(CoreSubscriber<? super Optional<T>> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.Operators.MonoInnerProducerBase
        public void doOnRequest(long j) {
            this.f2271s.request(Long.MAX_VALUE);
        }

        @Override // reactor.core.publisher.Operators.MonoInnerProducerBase
        public void doOnCancel() {
            this.f2271s.cancel();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2271s, subscription)) {
                this.f2271s = subscription;
                actual().onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, actual().currentContext());
            } else {
                this.done = true;
                complete(Optional.of(t));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, actual().currentContext());
            } else {
                this.done = true;
                actual().onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            complete(Optional.empty());
        }
    }
}
