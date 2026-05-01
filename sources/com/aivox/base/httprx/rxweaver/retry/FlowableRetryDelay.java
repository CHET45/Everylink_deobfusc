package com.aivox.base.httprx.rxweaver.retry;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes.dex */
public class FlowableRetryDelay implements Function<Flowable<Throwable>, Publisher<?>> {
    private Function<Throwable, RetryConfig> provider;
    private int retryCount;

    static /* synthetic */ int access$104(FlowableRetryDelay flowableRetryDelay) {
        int i = flowableRetryDelay.retryCount + 1;
        flowableRetryDelay.retryCount = i;
        return i;
    }

    public FlowableRetryDelay(Function<Throwable, RetryConfig> function) {
        if (function == null) {
            throw new NullPointerException("The parameter provider can't be null!");
        }
        this.provider = function;
    }

    @Override // io.reactivex.functions.Function
    public Publisher<?> apply(Flowable<Throwable> flowable) throws Exception {
        return flowable.flatMap(new Function<Throwable, Publisher<?>>() { // from class: com.aivox.base.httprx.rxweaver.retry.FlowableRetryDelay.1
            @Override // io.reactivex.functions.Function
            public Publisher<?> apply(final Throwable th) throws Exception {
                RetryConfig retryConfig = (RetryConfig) FlowableRetryDelay.this.provider.apply(th);
                final long delay = retryConfig.getDelay();
                if (FlowableRetryDelay.access$104(FlowableRetryDelay.this) <= retryConfig.getMaxRetries()) {
                    return retryConfig.getRetryCondition().call().flatMapPublisher(new Function<Boolean, Publisher<?>>() { // from class: com.aivox.base.httprx.rxweaver.retry.FlowableRetryDelay.1.1
                        @Override // io.reactivex.functions.Function
                        public Publisher<?> apply(Boolean bool) throws Exception {
                            if (bool.booleanValue()) {
                                return Flowable.timer(delay, TimeUnit.MILLISECONDS);
                            }
                            return Flowable.error(th);
                        }
                    });
                }
                return Flowable.error(th);
            }
        });
    }
}
