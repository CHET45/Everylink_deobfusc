package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;

/* JADX INFO: loaded from: classes5.dex */
class ConnectableFluxRestoringThreadLocals<T> extends ConnectableFlux<T> {
    private final ConnectableFlux<T> source;

    public ConnectableFluxRestoringThreadLocals(ConnectableFlux<T> connectableFlux) {
        this.source = (ConnectableFlux) Objects.requireNonNull(connectableFlux, "source");
    }

    @Override // reactor.core.publisher.ConnectableFlux
    public void connect(Consumer<? super Disposable> consumer) {
        this.source.connect(consumer);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        ConnectableFlux<T> connectableFlux = this.source;
        connectableFlux.subscribe((CoreSubscriber) Operators.restoreContextOnSubscriber(connectableFlux, coreSubscriber));
    }
}
