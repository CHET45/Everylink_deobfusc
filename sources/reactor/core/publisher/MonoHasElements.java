package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoHasElements<T> extends MonoFromFluxOperator<T, Boolean> implements Fuseable {
    MonoHasElements(Flux<? extends T> flux) {
        super(flux);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Boolean> coreSubscriber) {
        return new HasElementsSubscriber(coreSubscriber);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class HasElementsSubscriber<T> extends Operators.BaseFluxToMonoOperator<T, Boolean> {
        boolean done;

        HasElementsSubscriber(CoreSubscriber<? super Boolean> coreSubscriber) {
            super(coreSubscriber);
        }

        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : super.scanUnsafe(attr);
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            Operators.onDiscard(t, currentContext());
            if (this.done) {
                return;
            }
            this.f2280s.cancel();
            this.done = true;
            this.actual.onNext((Object) true);
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
            } else {
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

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        public Boolean accumulatedValue() {
            return false;
        }
    }
}
