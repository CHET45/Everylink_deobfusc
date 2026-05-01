package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;

/* JADX INFO: loaded from: classes5.dex */
final class FluxConcatIterable<T> extends Flux<T> implements SourceProducer<T> {
    final Iterable<? extends Publisher<? extends T>> iterable;

    FluxConcatIterable(Iterable<? extends Publisher<? extends T>> iterable) {
        this.iterable = (Iterable) Objects.requireNonNull(iterable, "iterable");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            ConcatIterableSubscriber concatIterableSubscriber = new ConcatIterableSubscriber(coreSubscriber, (Iterator) Objects.requireNonNull(this.iterable.iterator(), "The Iterator returned is null"));
            coreSubscriber.onSubscribe(concatIterableSubscriber);
            if (concatIterableSubscriber.isCancelled()) {
                return;
            }
            concatIterableSubscriber.onComplete();
        } catch (Throwable th) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ConcatIterableSubscriber<T> extends Operators.MultiSubscriptionSubscriber<T, T> {
        static final AtomicIntegerFieldUpdater<ConcatIterableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(ConcatIterableSubscriber.class, "wip");

        /* JADX INFO: renamed from: it */
        final Iterator<? extends Publisher<? extends T>> f2100it;
        long produced;
        volatile int wip;

        ConcatIterableSubscriber(CoreSubscriber<? super T> coreSubscriber, Iterator<? extends Publisher<? extends T>> it) {
            super(coreSubscriber);
            this.f2100it = it;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.produced++;
            this.actual.onNext((Object) t);
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (WIP.getAndIncrement(this) == 0) {
                Iterator<? extends Publisher<? extends T>> it = this.f2100it;
                while (!isCancelled()) {
                    try {
                        boolean zHasNext = it.hasNext();
                        if (isCancelled()) {
                            return;
                        }
                        if (!zHasNext) {
                            this.actual.onComplete();
                            return;
                        }
                        try {
                            Publisher publisher = (Publisher) Objects.requireNonNull(this.f2100it.next(), "The Publisher returned by the iterator is null");
                            if (isCancelled()) {
                                return;
                            }
                            long j = this.produced;
                            if (j != 0) {
                                this.produced = 0L;
                                produced(j);
                            }
                            Operators.toFluxOrMono(publisher).subscribe((Subscriber) this);
                            if (isCancelled() || WIP.decrementAndGet(this) == 0) {
                                return;
                            }
                        } catch (Throwable th) {
                            this.actual.onError(Operators.onOperatorError(this, th, this.actual.currentContext()));
                            return;
                        }
                    } catch (Throwable th2) {
                        onError(Operators.onOperatorError(this, th2, this.actual.currentContext()));
                        return;
                    }
                }
            }
        }

        @Override // reactor.core.publisher.Operators.MultiSubscriptionSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
