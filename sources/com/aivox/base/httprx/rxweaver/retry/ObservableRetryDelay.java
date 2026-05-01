package com.aivox.base.httprx.rxweaver.retry;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class ObservableRetryDelay implements Function<Observable<Throwable>, ObservableSource<?>> {
    private Function<Throwable, RetryConfig> provider;
    private int retryCount;

    static /* synthetic */ int access$104(ObservableRetryDelay observableRetryDelay) {
        int i = observableRetryDelay.retryCount + 1;
        observableRetryDelay.retryCount = i;
        return i;
    }

    public ObservableRetryDelay(Function<Throwable, RetryConfig> function) {
        if (function == null) {
            throw new NullPointerException("The parameter provider can't be null!");
        }
        this.provider = function;
    }

    @Override // io.reactivex.functions.Function
    public ObservableSource<?> apply(Observable<Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() { // from class: com.aivox.base.httprx.rxweaver.retry.ObservableRetryDelay.1
            @Override // io.reactivex.functions.Function
            public ObservableSource<?> apply(final Throwable th) throws Exception {
                RetryConfig retryConfig = (RetryConfig) ObservableRetryDelay.this.provider.apply(th);
                final long delay = retryConfig.getDelay();
                if (ObservableRetryDelay.access$104(ObservableRetryDelay.this) <= retryConfig.getMaxRetries()) {
                    return retryConfig.getRetryCondition().call().flatMapObservable(new Function<Boolean, ObservableSource<?>>() { // from class: com.aivox.base.httprx.rxweaver.retry.ObservableRetryDelay.1.1
                        @Override // io.reactivex.functions.Function
                        public ObservableSource<?> apply(Boolean bool) throws Exception {
                            if (bool.booleanValue()) {
                                return Observable.timer(delay, TimeUnit.MILLISECONDS);
                            }
                            return Observable.error(th);
                        }
                    });
                }
                return Observable.error(th);
            }
        });
    }
}
