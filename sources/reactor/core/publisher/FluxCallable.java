package reactor.core.publisher;

import java.util.concurrent.Callable;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.MonoCallable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxCallable<T> extends Flux<T> implements Callable<T>, Fuseable, SourceProducer<T> {
    final Callable<T> callable;

    FluxCallable(Callable<T> callable) {
        this.callable = callable;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        coreSubscriber.onSubscribe(new MonoCallable.MonoCallableSubscription(coreSubscriber, this.callable));
    }

    @Override // java.util.concurrent.Callable
    @Nullable
    public T call() throws Exception {
        return this.callable.call();
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
