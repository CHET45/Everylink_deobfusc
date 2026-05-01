package reactor.core.publisher;

import java.time.Duration;
import java.util.function.Consumer;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/* JADX INFO: loaded from: classes5.dex */
public abstract class ConnectableFlux<T> extends Flux<T> {
    static final Consumer<Disposable> NOOP_DISCONNECT = new Consumer() { // from class: reactor.core.publisher.ConnectableFlux$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            ConnectableFlux.lambda$static$1((Disposable) obj);
        }
    };

    static /* synthetic */ void lambda$static$1(Disposable disposable) {
    }

    public abstract void connect(Consumer<? super Disposable> consumer);

    static <T> ConnectableFlux<T> from(ConnectableFlux<T> connectableFlux) {
        return ContextPropagationSupport.shouldWrapPublisher(connectableFlux) ? new ConnectableFluxRestoringThreadLocals(connectableFlux) : connectableFlux;
    }

    public final Flux<T> autoConnect() {
        return autoConnect(1);
    }

    public final Flux<T> autoConnect(int i) {
        return autoConnect(i, NOOP_DISCONNECT);
    }

    public final Flux<T> autoConnect(int i, Consumer<? super Disposable> consumer) {
        if (i == 0) {
            connect(consumer);
            return this;
        }
        if (this instanceof Fuseable) {
            return onAssembly(new FluxAutoConnectFuseable(this, i, consumer));
        }
        return onAssembly(new FluxAutoConnect(this, i, consumer));
    }

    public final Disposable connect() {
        final Disposable[] disposableArr = {null};
        connect(new Consumer() { // from class: reactor.core.publisher.ConnectableFlux$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ConnectableFlux.lambda$connect$0(disposableArr, (Disposable) obj);
            }
        });
        return disposableArr[0];
    }

    static /* synthetic */ void lambda$connect$0(Disposable[] disposableArr, Disposable disposable) {
        disposableArr[0] = disposable;
    }

    @Override // reactor.core.publisher.Flux
    public final ConnectableFlux<T> hide() {
        return new ConnectableFluxHide(this);
    }

    public final Flux<T> refCount() {
        return refCount(1);
    }

    public final Flux<T> refCount(int i) {
        return onAssembly(new FluxRefCount(this, i));
    }

    public final Flux<T> refCount(int i, Duration duration) {
        return refCount(i, duration, Schedulers.parallel());
    }

    public final Flux<T> refCount(int i, Duration duration, Scheduler scheduler) {
        return onAssembly(new FluxRefCountGrace(this, i, duration, scheduler));
    }
}
