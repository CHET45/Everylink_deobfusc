package reactor.core.publisher;

import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelArraySource<T> extends ParallelFlux<T> implements SourceProducer<T> {
    final Publisher<T>[] sources;

    ParallelArraySource(Publisher<T>[] publisherArr) {
        if (publisherArr == null || publisherArr.length == 0) {
            throw new IllegalArgumentException("Zero publishers not supported");
        }
        this.sources = publisherArr;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.sources.length;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            for (int i = 0; i < length; i++) {
                Flux.from(this.sources[i]).subscribe((CoreSubscriber) coreSubscriberArr[i]);
            }
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
