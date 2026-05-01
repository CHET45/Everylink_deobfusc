package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxZipIterable<T, U, R> extends InternalFluxOperator<T, R> {
    final Iterable<? extends U> other;
    final BiFunction<? super T, ? super U, ? extends R> zipper;

    FluxZipIterable(Flux<? extends T> flux, Iterable<? extends U> iterable, BiFunction<? super T, ? super U, ? extends R> biFunction) {
        super(flux);
        this.other = (Iterable) Objects.requireNonNull(iterable, "other");
        this.zipper = (BiFunction) Objects.requireNonNull(biFunction, "zipper");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        Iterator it = (Iterator) Objects.requireNonNull(this.other.iterator(), "The other iterable produced a null iterator");
        if (!it.hasNext()) {
            Operators.complete(coreSubscriber);
            return null;
        }
        return new ZipSubscriber(coreSubscriber, it, this.zipper);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ZipSubscriber<T, U, R> implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        boolean done;

        /* JADX INFO: renamed from: it */
        final Iterator<? extends U> f2244it;

        /* JADX INFO: renamed from: s */
        Subscription f2245s;
        final BiFunction<? super T, ? super U, ? extends R> zipper;

        ZipSubscriber(CoreSubscriber<? super R> coreSubscriber, Iterator<? extends U> it, BiFunction<? super T, ? super U, ? extends R> biFunction) {
            this.actual = coreSubscriber;
            this.f2244it = it;
            this.zipper = biFunction;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : attr == Scannable.Attr.PARENT ? this.f2245s : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2245s, subscription)) {
                this.f2245s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                try {
                    this.actual.onNext(Objects.requireNonNull(this.zipper.apply(t, this.f2244it.next()), "The zipper returned a null value"));
                    try {
                        if (this.f2244it.hasNext()) {
                            return;
                        }
                        this.done = true;
                        this.f2245s.cancel();
                        this.actual.onComplete();
                    } catch (Throwable th) {
                        this.done = true;
                        CoreSubscriber<? super R> coreSubscriber = this.actual;
                        coreSubscriber.onError(Operators.onOperatorError(this.f2245s, th, t, coreSubscriber.currentContext()));
                    }
                } catch (Throwable th2) {
                    this.done = true;
                    CoreSubscriber<? super R> coreSubscriber2 = this.actual;
                    coreSubscriber2.onError(Operators.onOperatorError(this.f2245s, th2, t, coreSubscriber2.currentContext()));
                }
            } catch (Throwable th3) {
                this.done = true;
                CoreSubscriber<? super R> coreSubscriber3 = this.actual;
                coreSubscriber3.onError(Operators.onOperatorError(this.f2245s, th3, t, coreSubscriber3.currentContext()));
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
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2245s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2245s.cancel();
        }
    }
}
