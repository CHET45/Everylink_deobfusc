package reactor.core.publisher;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxStream<T> extends Flux<T> implements Fuseable, SourceProducer<T> {
    final Supplier<? extends Stream<? extends T>> streamSupplier;

    FluxStream(Supplier<? extends Stream<? extends T>> supplier) {
        this.streamSupplier = (Supplier) Objects.requireNonNull(supplier, "streamSupplier");
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        try {
            final Stream stream = (Stream) Objects.requireNonNull(this.streamSupplier.get(), "The stream supplier returned a null Stream");
            try {
                Spliterator spliterator = (Spliterator) Objects.requireNonNull(stream.spliterator(), "The stream returned a null Spliterator");
                boolean zHasCharacteristics = spliterator.hasCharacteristics(64);
                Objects.requireNonNull(stream);
                FluxIterable.subscribe(coreSubscriber, spliterator, zHasCharacteristics, new Runnable() { // from class: reactor.core.publisher.FluxStream$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        stream.close();
                    }
                });
            } catch (Throwable th) {
                Operators.error(coreSubscriber, Operators.onOperatorError(th, coreSubscriber.currentContext()));
            }
        } catch (Throwable th2) {
            Operators.error(coreSubscriber, Operators.onOperatorError(th2, coreSubscriber.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
