package reactor.util.retry;

import java.time.Duration;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;
import reactor.util.retry.Retry;

/* JADX INFO: loaded from: classes5.dex */
public final class RetrySpec extends Retry {
    final BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> asyncPostRetry;
    final BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> asyncPreRetry;
    final Consumer<Retry.RetrySignal> doPostRetry;
    final Consumer<Retry.RetrySignal> doPreRetry;
    public final Predicate<Throwable> errorFilter;
    public final boolean isTransientErrors;
    public final long maxAttempts;
    final BiFunction<RetrySpec, Retry.RetrySignal, Throwable> retryExhaustedGenerator;
    static final Duration MAX_BACKOFF = Duration.ofMillis(Long.MAX_VALUE);
    static final Consumer<Retry.RetrySignal> NO_OP_CONSUMER = new Consumer() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            RetrySpec.lambda$static$0((Retry.RetrySignal) obj);
        }
    };
    static final BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> NO_OP_BIFUNCTION = new BiFunction() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda2
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return RetrySpec.lambda$static$1((Retry.RetrySignal) obj, (Mono) obj2);
        }
    };
    static final BiFunction<RetrySpec, Retry.RetrySignal, Throwable> RETRY_EXCEPTION_GENERATOR = new BiFunction() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda3
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return RetrySpec.lambda$static$2((RetrySpec) obj, (Retry.RetrySignal) obj2);
        }
    };

    static /* synthetic */ void lambda$static$0(Retry.RetrySignal retrySignal) {
    }

    static /* synthetic */ Mono lambda$static$1(Retry.RetrySignal retrySignal, Mono mono) {
        return mono;
    }

    @Override // reactor.util.retry.Retry
    public /* bridge */ /* synthetic */ Publisher generateCompanion(Flux flux) {
        return generateCompanion((Flux<Retry.RetrySignal>) flux);
    }

    static /* synthetic */ Throwable lambda$static$2(RetrySpec retrySpec, Retry.RetrySignal retrySignal) {
        String str;
        StringBuilder sb = new StringBuilder("Retries exhausted: ");
        if (retrySpec.isTransientErrors) {
            str = retrySignal.totalRetriesInARow() + "/" + retrySpec.maxAttempts + " in a row (" + retrySignal.totalRetries() + " total)";
        } else {
            str = retrySignal.totalRetries() + "/" + retrySpec.maxAttempts;
        }
        return Exceptions.retryExhausted(sb.append(str).toString(), retrySignal.failure());
    }

    RetrySpec(ContextView contextView, long j, Predicate<? super Throwable> predicate, boolean z, Consumer<Retry.RetrySignal> consumer, Consumer<Retry.RetrySignal> consumer2, BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> biFunction, BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> biFunction2, BiFunction<RetrySpec, Retry.RetrySignal, Throwable> biFunction3) {
        super(contextView);
        this.maxAttempts = j;
        Objects.requireNonNull(predicate);
        this.errorFilter = new RetryBackoffSpec$$ExternalSyntheticLambda0(predicate);
        this.isTransientErrors = z;
        this.doPreRetry = consumer;
        this.doPostRetry = consumer2;
        this.asyncPreRetry = biFunction;
        this.asyncPostRetry = biFunction2;
        this.retryExhaustedGenerator = biFunction3;
    }

    public RetrySpec withRetryContext(ContextView contextView) {
        return new RetrySpec(contextView, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.doPreRetry, this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetrySpec maxAttempts(long j) {
        return new RetrySpec(this.retryContext, j, this.errorFilter, this.isTransientErrors, this.doPreRetry, this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetrySpec filter(Predicate<? super Throwable> predicate) {
        return new RetrySpec(this.retryContext, this.maxAttempts, (Predicate) Objects.requireNonNull(predicate, "errorFilter"), this.isTransientErrors, this.doPreRetry, this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetrySpec modifyErrorFilter(Function<Predicate<Throwable>, Predicate<? super Throwable>> function) {
        Objects.requireNonNull(function, "predicateAdjuster");
        return new RetrySpec(this.retryContext, this.maxAttempts, (Predicate) Objects.requireNonNull(function.apply(this.errorFilter), "predicateAdjuster must return a new predicate"), this.isTransientErrors, this.doPreRetry, this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetrySpec doBeforeRetry(Consumer<Retry.RetrySignal> consumer) {
        return new RetrySpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.doPreRetry.andThen(consumer), this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetrySpec doAfterRetry(Consumer<Retry.RetrySignal> consumer) {
        return new RetrySpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.doPreRetry, this.doPostRetry.andThen(consumer), this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    public RetrySpec doBeforeRetryAsync(final Function<Retry.RetrySignal, Mono<Void>> function) {
        return new RetrySpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.doPreRetry, this.doPostRetry, new BiFunction() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.m4309lambda$doBeforeRetryAsync$3$reactorutilretryRetrySpec(function, (Retry.RetrySignal) obj, (Mono) obj2);
            }
        }, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    /* JADX INFO: renamed from: lambda$doBeforeRetryAsync$3$reactor-util-retry-RetrySpec, reason: not valid java name */
    /* synthetic */ Mono m4309lambda$doBeforeRetryAsync$3$reactorutilretryRetrySpec(Function function, Retry.RetrySignal retrySignal, Mono mono) {
        return this.asyncPreRetry.apply(retrySignal, mono).then((Mono) function.apply(retrySignal));
    }

    public RetrySpec doAfterRetryAsync(final Function<Retry.RetrySignal, Mono<Void>> function) {
        return new RetrySpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.doPreRetry, this.doPostRetry, this.asyncPreRetry, new BiFunction() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.m4308lambda$doAfterRetryAsync$4$reactorutilretryRetrySpec(function, (Retry.RetrySignal) obj, (Mono) obj2);
            }
        }, this.retryExhaustedGenerator);
    }

    /* JADX INFO: renamed from: lambda$doAfterRetryAsync$4$reactor-util-retry-RetrySpec, reason: not valid java name */
    /* synthetic */ Mono m4308lambda$doAfterRetryAsync$4$reactorutilretryRetrySpec(Function function, Retry.RetrySignal retrySignal, Mono mono) {
        return this.asyncPostRetry.apply(retrySignal, mono).then((Mono) function.apply(retrySignal));
    }

    public RetrySpec onRetryExhaustedThrow(BiFunction<RetrySpec, Retry.RetrySignal, Throwable> biFunction) {
        return new RetrySpec(this.retryContext, this.maxAttempts, this.errorFilter, this.isTransientErrors, this.doPreRetry, this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, (BiFunction) Objects.requireNonNull(biFunction, "retryExhaustedGenerator"));
    }

    public RetrySpec transientErrors(boolean z) {
        return new RetrySpec(this.retryContext, this.maxAttempts, this.errorFilter, z, this.doPreRetry, this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, this.retryExhaustedGenerator);
    }

    @Override // reactor.util.retry.Retry
    public Flux<Long> generateCompanion(final Flux<Retry.RetrySignal> flux) {
        return Flux.deferContextual(new Function() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m4311lambda$generateCompanion$6$reactorutilretryRetrySpec(flux, (ContextView) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateCompanion$6$reactor-util-retry-RetrySpec, reason: not valid java name */
    /* synthetic */ Publisher m4311lambda$generateCompanion$6$reactorutilretryRetrySpec(Flux flux, final ContextView contextView) {
        return flux.contextWrite(contextView).concatMap(new Function() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m4310lambda$generateCompanion$5$reactorutilretryRetrySpec(contextView, (Retry.RetrySignal) obj);
            }
        }).onErrorStop();
    }

    /* JADX INFO: renamed from: lambda$generateCompanion$5$reactor-util-retry-RetrySpec, reason: not valid java name */
    /* synthetic */ Publisher m4310lambda$generateCompanion$5$reactorutilretryRetrySpec(ContextView contextView, Retry.RetrySignal retrySignal) {
        Retry.RetrySignal retrySignalCopy = retrySignal.copy();
        Throwable thFailure = retrySignalCopy.failure();
        long j = this.isTransientErrors ? retrySignalCopy.totalRetriesInARow() : retrySignalCopy.totalRetries();
        if (thFailure == null) {
            return Mono.error(new IllegalStateException("RetryWhenState#failure() not expected to be null"));
        }
        if (!this.errorFilter.test(thFailure)) {
            return Mono.error(thFailure);
        }
        if (j >= this.maxAttempts) {
            return Mono.error(this.retryExhaustedGenerator.apply(this, retrySignalCopy));
        }
        return applyHooks(retrySignalCopy, Mono.just(Long.valueOf(j)), this.doPreRetry, this.doPostRetry, this.asyncPreRetry, this.asyncPostRetry, contextView);
    }

    static <T> Mono<T> applyHooks(final Retry.RetrySignal retrySignal, Mono<T> mono, Consumer<Retry.RetrySignal> consumer, final Consumer<Retry.RetrySignal> consumer2, BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> biFunction, BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> biFunction2, ContextView contextView) {
        final Mono<Void> monoEmpty;
        Consumer<Retry.RetrySignal> consumer3 = NO_OP_CONSUMER;
        if (consumer != consumer3) {
            try {
                consumer.accept(retrySignal);
            } catch (Throwable th) {
                return Mono.error(th);
            }
        }
        if (consumer2 != consumer3) {
            monoEmpty = Mono.fromRunnable(new Runnable() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    consumer2.accept(retrySignal);
                }
            });
        } else {
            monoEmpty = Mono.empty();
        }
        BiFunction<Retry.RetrySignal, Mono<Void>, Mono<Void>> biFunction3 = NO_OP_BIFUNCTION;
        Mono<Void> monoEmpty2 = biFunction == biFunction3 ? Mono.empty() : biFunction.apply(retrySignal, Mono.empty());
        if (biFunction2 != biFunction3) {
            monoEmpty = biFunction2.apply(retrySignal, monoEmpty);
        }
        Mono<V> monoThen = monoEmpty2.then(mono);
        Objects.requireNonNull(monoEmpty);
        return monoThen.flatMap(new Function() { // from class: reactor.util.retry.RetrySpec$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return monoEmpty.thenReturn(obj);
            }
        }).contextWrite(contextView);
    }
}
