package reactor.util.retry;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;
import reactor.util.context.ContextView;
import reactor.util.retry.Retry;

/* JADX INFO: loaded from: classes5.dex */
public final class RetryBackoffSpec extends Retry {
    static final BiFunction<RetryBackoffSpec, Retry.RetrySignal, Throwable> BACKOFF_EXCEPTION_GENERATOR = new BiFunction() { // from class: reactor.util.retry.RetryBackoffSpec$$ExternalSyntheticLambda5
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return RetryBackoffSpec.lambda$static$0((RetryBackoffSpec) obj, (Retry.RetrySignal) obj2);
        }
    };
    final BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> asyncPostRetry;
    final BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> asyncPreRetry;
    public final Supplier<Scheduler> backoffSchedulerSupplier;
    public final Predicate<Throwable> errorFilter;
    public final boolean isTransientErrors;
    public final double jitterFactor;
    public final long maxAttempts;
    public final Duration maxBackoff;
    public final Duration minBackoff;
    public final double multiplier;
    final BiFunction<RetryBackoffSpec, Retry.RetrySignal, Throwable> retryExhaustedGenerator;
    final Consumer<Retry.RetrySignal> syncPostRetry;
    final Consumer<Retry.RetrySignal> syncPreRetry;

    static /* synthetic */ Scheduler lambda$scheduler$3(Scheduler scheduler) {
        return scheduler;
    }

    @Override // reactor.util.retry.Retry
    public /* bridge */ /* synthetic */ Publisher generateCompanion(Flux flux) {
        return generateCompanion((Flux<Retry.RetrySignal>) flux);
    }

    static /* synthetic */ Throwable lambda$static$0(RetryBackoffSpec retryBackoffSpec, Retry.RetrySignal retrySignal) {
        String str;
        StringBuilder sb = new StringBuilder("Retries exhausted: ");
        if (retryBackoffSpec.isTransientErrors) {
            str = retrySignal.totalRetriesInARow() + "/" + retryBackoffSpec.maxAttempts + " in a row (" + retrySignal.totalRetries() + " total)";
        } else {
            str = retrySignal.totalRetries() + "/" + retryBackoffSpec.maxAttempts;
        }
        return Exceptions.retryExhausted(sb.append(str).toString(), retrySignal.failure());
    }

    RetryBackoffSpec(ContextView contextView, long j, Predicate<? super Throwable> predicate, boolean z, Duration duration, Duration duration2, double d, double d2, Supplier<Scheduler> supplier, Consumer<Retry.RetrySignal> consumer, Consumer<Retry.RetrySignal> consumer2, BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> biFunction, BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> biFunction2, BiFunction<RetryBackoffSpec, Retry.RetrySignal, Throwable> biFunction3) {
        super(contextView);
        this.maxAttempts = j;
        Objects.requireNonNull(predicate);
        this.errorFilter = new RetryBackoffSpec$$ExternalSyntheticLambda0(predicate);
        this.isTransientErrors = z;
        this.minBackoff = duration;
        this.maxBackoff = duration2;
        this.multiplier = d > 1.0d ? d : 1.0d;
        this.jitterFactor = d2;
        this.backoffSchedulerSupplier = supplier;
        this.syncPreRetry = consumer;
        this.syncPostRetry = consumer2;
        this.asyncPreRetry = biFunction;
        this.asyncPostRetry = biFunction2;
        this.retryExhaustedGenerator = biFunction3;
    }

    public RetryBackoffSpec withRetryContext(ContextView contextView) {
        return new RetryBackoffSpec(contextView, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec maxAttempts(long j) {
        return new RetryBackoffSpec(this.retryContext, j, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec filter(Predicate<? super Throwable> predicate) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, (Predicate) Objects.requireNonNull(predicate, "errorFilter"), this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec modifyErrorFilter(Function<Predicate<Throwable>, Predicate<? super Throwable>> function) {
        Objects.requireNonNull(function, "predicateAdjuster");
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, (Predicate) Objects.requireNonNull(function.apply(this.errorFilter), "predicateAdjuster must return a new predicate"), this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec doBeforeRetry(Consumer<Retry.RetrySignal> consumer) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry.andThen(consumer), this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec doAfterRetry(Consumer<Retry.RetrySignal> consumer) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry.andThen(consumer), this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec doBeforeRetryAsync(final Function<Retry.RetrySignal, Mono<Void>> function) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, new BiFunction() { // from class: reactor.util.retry.RetryBackoffSpec$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.m4305lambda$doBeforeRetryAsync$1$reactorutilretryRetryBackoffSpec(function, (Retry.RetrySignal) obj, (Mono) obj2);
            }
        }, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    /* JADX INFO: renamed from: lambda$doBeforeRetryAsync$1$reactor-util-retry-RetryBackoffSpec, reason: not valid java name */
    /* synthetic */ Mono m4305lambda$doBeforeRetryAsync$1$reactorutilretryRetryBackoffSpec(Function function, Retry.RetrySignal retrySignal, Mono mono) {
        return this.asyncPreRetry.apply(retrySignal, mono).then((Mono) function.apply(retrySignal));
    }

    public RetryBackoffSpec doAfterRetryAsync(final Function<Retry.RetrySignal, Mono<Void>> function) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, new BiFunction() { // from class: reactor.util.retry.RetryBackoffSpec$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.m4304lambda$doAfterRetryAsync$2$reactorutilretryRetryBackoffSpec(function, (Retry.RetrySignal) obj, (Mono) obj2);
            }
        }, this.retryExhaustedGenerator);
    }

    /* JADX INFO: renamed from: lambda$doAfterRetryAsync$2$reactor-util-retry-RetryBackoffSpec, reason: not valid java name */
    /* synthetic */ Mono m4304lambda$doAfterRetryAsync$2$reactorutilretryRetryBackoffSpec(Function function, Retry.RetrySignal retrySignal, Mono mono) {
        return this.asyncPostRetry.apply(retrySignal, mono).then((Mono) function.apply(retrySignal));
    }

    public RetryBackoffSpec onRetryExhaustedThrow(BiFunction<RetryBackoffSpec, Retry.RetrySignal, Throwable> biFunction) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, (BiFunction) Objects.requireNonNull(biFunction, "retryExhaustedGenerator"));
    }

    public RetryBackoffSpec transientErrors(boolean z) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, z, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec minBackoff(Duration duration) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, duration, this.maxBackoff, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec maxBackoff(Duration duration) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, duration, this.multiplier, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec jitter(double d) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, d, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec multiplier(double d) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, d, this.jitterFactor, this.backoffSchedulerSupplier, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetryBackoffSpec scheduler(@Nullable final Scheduler scheduler) {
        return new RetryBackoffSpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.minBackoff, this.maxBackoff, this.multiplier, this.jitterFactor, scheduler == null ? new Retry$$ExternalSyntheticLambda3() : new Supplier() { // from class: reactor.util.retry.RetryBackoffSpec$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return RetryBackoffSpec.lambda$scheduler$3(scheduler);
            }
        }, this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    protected void validateArguments() {
        double d = this.jitterFactor;
        if (d < 0.0d || d > 1.0d) {
            throw new IllegalArgumentException("jitterFactor must be between 0 and 1 (default 0.5)");
        }
    }

    @Override // reactor.util.retry.Retry
    public Flux<Long> generateCompanion(final Flux<Retry.RetrySignal> flux) {
        validateArguments();
        return Flux.deferContextual(new Function() { // from class: reactor.util.retry.RetryBackoffSpec$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m4307lambda$generateCompanion$5$reactorutilretryRetryBackoffSpec(flux, (ContextView) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateCompanion$5$reactor-util-retry-RetryBackoffSpec, reason: not valid java name */
    /* synthetic */ Publisher m4307lambda$generateCompanion$5$reactorutilretryRetryBackoffSpec(Flux flux, final ContextView contextView) {
        return flux.contextWrite(contextView).concatMap(new Function() { // from class: reactor.util.retry.RetryBackoffSpec$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m4306lambda$generateCompanion$4$reactorutilretryRetryBackoffSpec(contextView, (Retry.RetrySignal) obj);
            }
        }).onErrorStop();
    }

    /* JADX INFO: renamed from: lambda$generateCompanion$4$reactor-util-retry-RetryBackoffSpec, reason: not valid java name */
    /* synthetic */ Publisher m4306lambda$generateCompanion$4$reactorutilretryRetryBackoffSpec(ContextView contextView, Retry.RetrySignal retrySignal) {
        Duration durationOfNanos;
        long jRound;
        long jNextLong;
        Retry.RetrySignal retrySignalCopy = retrySignal.copy();
        Throwable thFailure = retrySignalCopy.failure();
        long j = this.isTransientErrors ? retrySignalCopy.totalRetriesInARow() : retrySignalCopy.totalRetries();
        if (thFailure == null) {
            return Mono.error(new IllegalStateException("Retry.RetrySignal#failure() not expected to be null"));
        }
        if (!this.errorFilter.test(thFailure)) {
            return Mono.error(thFailure);
        }
        if (j >= this.maxAttempts) {
            return Mono.error(this.retryExhaustedGenerator.apply(this, retrySignalCopy));
        }
        try {
            durationOfNanos = Duration.ofNanos((long) (this.minBackoff.toNanos() * Math.pow(this.multiplier, j)));
            if (durationOfNanos.compareTo(this.maxBackoff) > 0) {
                durationOfNanos = this.maxBackoff;
            }
        } catch (ArithmeticException unused) {
            durationOfNanos = this.maxBackoff;
        }
        if (durationOfNanos.isZero()) {
            return RetrySpec.applyHooks(retrySignalCopy, Mono.just(Long.valueOf(j)), this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, contextView);
        }
        ThreadLocalRandom threadLocalRandomCurrent = ThreadLocalRandom.current();
        try {
            jRound = durationOfNanos.multipliedBy((long) (this.jitterFactor * 100.0d)).dividedBy(100L).toMillis();
        } catch (ArithmeticException unused2) {
            jRound = Math.round(this.jitterFactor * 9.223372036854776E18d);
        }
        long jMax = Math.max(this.minBackoff.minus(durationOfNanos).toMillis(), -jRound);
        long jMin = Math.min(this.maxBackoff.minus(durationOfNanos).toMillis(), jRound);
        if (jMin == jMax) {
            jNextLong = 0;
            if (jMin != 0) {
                jNextLong = threadLocalRandomCurrent.nextLong(jMin);
            }
        } else {
            jNextLong = threadLocalRandomCurrent.nextLong(jMax, jMin);
        }
        return RetrySpec.applyHooks(retrySignalCopy, Mono.delay(durationOfNanos.plusMillis(jNextLong), this.backoffSchedulerSupplier.get()), this.syncPreRetry, this.syncPostRetry, this.asyncPreRetry, this.asyncPostRetry, contextView);
    }
}
