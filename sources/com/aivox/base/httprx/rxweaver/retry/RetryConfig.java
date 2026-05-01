package com.aivox.base.httprx.rxweaver.retry;

import com.aivox.base.httprx.rxweaver.func.Suppiler;
import io.reactivex.Single;

/* JADX INFO: loaded from: classes.dex */
public class RetryConfig {
    private static int DEFAULT_DELAY_DURATION = 1000;
    private static Suppiler<Single<Boolean>> DEFAULT_FUNCTION = new Suppiler<Single<Boolean>>() { // from class: com.aivox.base.httprx.rxweaver.retry.RetryConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.aivox.base.httprx.rxweaver.func.Suppiler
        public Single<Boolean> call() {
            return Single.just(false);
        }
    };
    private static int DEFAULT_RETRY_TIMES = 1;
    private int delay;
    private int maxRetries;
    private Suppiler<Single<Boolean>> retryCondition;

    public RetryConfig() {
        this(DEFAULT_RETRY_TIMES, DEFAULT_DELAY_DURATION, DEFAULT_FUNCTION);
    }

    public RetryConfig(int i) {
        this(i, DEFAULT_DELAY_DURATION, DEFAULT_FUNCTION);
    }

    public RetryConfig(int i, int i2) {
        this(i, i2, DEFAULT_FUNCTION);
    }

    public RetryConfig(Suppiler<Single<Boolean>> suppiler) {
        this(DEFAULT_RETRY_TIMES, DEFAULT_DELAY_DURATION, suppiler);
    }

    public RetryConfig(int i, int i2, Suppiler<Single<Boolean>> suppiler) {
        if (suppiler == null) {
            throw new NullPointerException("the parameter retryCondition can't be null.");
        }
        this.maxRetries = i;
        this.delay = i2;
        this.retryCondition = suppiler;
    }

    public int getMaxRetries() {
        return this.maxRetries;
    }

    public int getDelay() {
        return this.delay;
    }

    public Suppiler<Single<Boolean>> getRetryCondition() {
        return this.retryCondition;
    }
}
