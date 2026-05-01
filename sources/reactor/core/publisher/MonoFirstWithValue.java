package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFirstWithValue;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoFirstWithValue<T> extends Mono<T> implements SourceProducer<T> {
    final Mono<? extends T>[] array;
    final Iterable<? extends Mono<? extends T>> iterable;

    private MonoFirstWithValue(Mono<? extends T>[] monoArr) {
        this.array = (Mono[]) Objects.requireNonNull(monoArr, "array");
        this.iterable = null;
    }

    @SafeVarargs
    MonoFirstWithValue(Mono<? extends T> mono, Mono<? extends T>... monoArr) {
        Objects.requireNonNull(mono, "first");
        Objects.requireNonNull(monoArr, "others");
        Mono<? extends T>[] monoArr2 = new Mono[monoArr.length + 1];
        monoArr2[0] = mono;
        System.arraycopy(monoArr, 0, monoArr2, 1, monoArr.length);
        this.array = monoArr2;
        this.iterable = null;
    }

    MonoFirstWithValue(Iterable<? extends Mono<? extends T>> iterable) {
        this.array = null;
        this.iterable = (Iterable) Objects.requireNonNull(iterable);
    }

    @SafeVarargs
    @Nullable
    final MonoFirstWithValue<T> firstValuedAdditionalSources(Mono<? extends T>... monoArr) {
        Objects.requireNonNull(monoArr, "others");
        if (monoArr.length == 0) {
            return this;
        }
        Mono<? extends T>[] monoArr2 = this.array;
        if (monoArr2 == null) {
            return null;
        }
        int length = monoArr2.length;
        int length2 = monoArr.length;
        Mono[] monoArr3 = new Mono[length + length2];
        System.arraycopy(monoArr2, 0, monoArr3, 0, length);
        System.arraycopy(monoArr, 0, monoArr3, length, length2);
        return new MonoFirstWithValue<>(monoArr3);
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
            Mono monoFrom = Mono.from(publisherArr[0]);
            if (monoFrom == null) {
                Operators.error(coreSubscriber, Operators.onOperatorError(new NullPointerException("The single source Publisher is null"), coreSubscriber.currentContext()));
                return;
            } else {
                monoFrom.subscribe((Subscriber) coreSubscriber);
                return;
            }
        }
        new FluxFirstWithValue.RaceValuesCoordinator(length).subscribe(publisherArr, length, coreSubscriber);
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
