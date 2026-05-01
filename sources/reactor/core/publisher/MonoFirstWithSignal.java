package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFirstWithSignal;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoFirstWithSignal<T> extends Mono<T> implements SourceProducer<T> {
    final Mono<? extends T>[] array;
    final Iterable<? extends Mono<? extends T>> iterable;

    @SafeVarargs
    MonoFirstWithSignal(Mono<? extends T>... monoArr) {
        this.array = (Mono[]) Objects.requireNonNull(monoArr, "array");
        this.iterable = null;
    }

    MonoFirstWithSignal(Iterable<? extends Mono<? extends T>> iterable) {
        this.array = null;
        this.iterable = (Iterable) Objects.requireNonNull(iterable);
    }

    @Nullable
    Mono<T> orAdditionalSource(Mono<? extends T> mono) {
        Mono<? extends T>[] monoArr = this.array;
        if (monoArr == null) {
            return null;
        }
        int length = monoArr.length;
        Mono[] monoArr2 = new Mono[length + 1];
        System.arraycopy(monoArr, 0, monoArr2, 0, length);
        monoArr2[length] = mono;
        return new MonoFirstWithSignal(monoArr2);
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        int length;
        Publisher<? extends T>[] publisherArr = this.array;
        if (publisherArr == null) {
            publisherArr = new Publisher[8];
            try {
                Iterator it = (Iterator) Objects.requireNonNull(this.iterable.iterator(), "The iterator returned is null");
                length = 0;
                while (it.hasNext()) {
                    try {
                        try {
                            Publisher<? extends T> publisher = (Publisher) Objects.requireNonNull((Publisher) it.next(), "The Publisher returned by the iterator is null");
                            if (length == publisherArr.length) {
                                Publisher<? extends T>[] publisherArr2 = new Publisher[(length >> 2) + length];
                                System.arraycopy(publisherArr, 0, publisherArr2, 0, length);
                                publisherArr = publisherArr2;
                            }
                            publisherArr[length] = publisher;
                            length++;
                        } catch (Throwable th) {
                            Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
                            return;
                        }
                    } catch (Throwable th2) {
                        Operators.error(coreSubscriber, Operators.onOperatorError(th2, coreSubscriber.currentContext()));
                        return;
                    }
                }
            } catch (Throwable th3) {
                Operators.error(coreSubscriber, Operators.onOperatorError(th3, coreSubscriber.currentContext()));
                return;
            }
        } else {
            length = publisherArr.length;
        }
        if (length == 0) {
            Operators.complete(coreSubscriber);
            return;
        }
        if (length == 1) {
            Publisher<? extends T> publisher2 = publisherArr[0];
            if (publisher2 == null) {
                Operators.error(coreSubscriber, Operators.onOperatorError(new NullPointerException("The single source Publisher is null"), coreSubscriber.currentContext()));
                return;
            } else {
                Operators.toFluxOrMono(publisher2).subscribe((Subscriber) coreSubscriber);
                return;
            }
        }
        Operators.toFluxOrMono(publisherArr);
        new FluxFirstWithSignal.RaceCoordinator(length).subscribe(publisherArr, length, coreSubscriber);
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
