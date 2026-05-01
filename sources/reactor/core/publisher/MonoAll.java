package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoAll<T> extends MonoFromFluxOperator<T, Boolean> implements Fuseable {
    final Predicate<? super T> predicate;

    MonoAll(Flux<? extends T> flux, Predicate<? super T> predicate) {
        super(flux);
        this.predicate = (Predicate) Objects.requireNonNull(predicate, "predicate");
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Boolean> coreSubscriber) {
        return new AllSubscriber(coreSubscriber, this.predicate);
    }

    @Override // reactor.core.publisher.MonoFromFluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class AllSubscriber<T> extends Operators.BaseFluxToMonoOperator<T, Boolean> {
        boolean done;
        final Predicate<? super T> predicate;

        AllSubscriber(CoreSubscriber<? super Boolean> coreSubscriber, Predicate<? super T> predicate) {
            super(coreSubscriber);
            this.predicate = predicate;
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
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            try {
                if (this.predicate.test(t)) {
                    return;
                }
                this.done = true;
                this.f2280s.cancel();
                this.actual.onNext((Object) false);
                this.actual.onComplete();
            } catch (Throwable th) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.f2280s, th, t, this.actual.currentContext()));
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
            completePossiblyEmpty();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // reactor.core.publisher.Operators.BaseFluxToMonoOperator
        public Boolean accumulatedValue() {
            return true;
        }
    }
}
