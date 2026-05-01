package reactor.core.observability;

import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
public interface SignalListener<T> {
    default Context addToContext(Context context) {
        return context;
    }

    void doAfterComplete() throws Throwable;

    void doAfterError(Throwable th) throws Throwable;

    void doFinally(SignalType signalType) throws Throwable;

    void doFirst() throws Throwable;

    void doOnCancel() throws Throwable;

    void doOnComplete() throws Throwable;

    void doOnError(Throwable th) throws Throwable;

    void doOnFusion(int i) throws Throwable;

    void doOnMalformedOnComplete() throws Throwable;

    void doOnMalformedOnError(Throwable th) throws Throwable;

    void doOnMalformedOnNext(T t) throws Throwable;

    void doOnNext(T t) throws Throwable;

    void doOnRequest(long j) throws Throwable;

    void doOnSubscription() throws Throwable;

    void handleListenerError(Throwable th);
}
