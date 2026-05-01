package reactor.core.observability;

import reactor.core.publisher.SignalType;

/* JADX INFO: loaded from: classes5.dex */
public abstract class DefaultSignalListener<T> implements SignalListener<T> {
    int fusionMode = 0;

    @Override // reactor.core.observability.SignalListener
    public void doAfterComplete() throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doAfterError(Throwable th) throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doFinally(SignalType signalType) throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doFirst() throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnCancel() throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnComplete() throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnError(Throwable th) throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnMalformedOnComplete() throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnMalformedOnError(Throwable th) throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnMalformedOnNext(T t) throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnNext(T t) throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnRequest(long j) throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnSubscription() throws Throwable {
    }

    @Override // reactor.core.observability.SignalListener
    public void handleListenerError(Throwable th) {
    }

    @Override // reactor.core.observability.SignalListener
    public void doOnFusion(int i) throws Throwable {
        this.fusionMode = i;
    }

    protected int getFusionMode() {
        return this.fusionMode;
    }
}
