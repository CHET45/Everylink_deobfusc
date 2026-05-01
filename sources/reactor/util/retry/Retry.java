package reactor.util.retry;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;
import reactor.util.context.ContextView;
import reactor.util.retry.Retry;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Retry {
    public final ContextView retryContext;

    static /* synthetic */ boolean lambda$backoff$0(Throwable th) {
        return true;
    }

    static /* synthetic */ boolean lambda$fixedDelay$1(Throwable th) {
        return true;
    }

    static /* synthetic */ boolean lambda$indefinitely$4(Throwable th) {
        return true;
    }

    static /* synthetic */ boolean lambda$max$2(Throwable th) {
        return true;
    }

    static /* synthetic */ boolean lambda$maxInARow$3(Throwable th) {
        return true;
    }

    public abstract Publisher<?> generateCompanion(Flux<RetrySignal> flux);

    public Retry() {
        this(Context.empty());
    }

    protected Retry(ContextView contextView) {
        this.retryContext = contextView;
    }

    public ContextView retryContext() {
        return this.retryContext;
    }

    public interface RetrySignal {
        Throwable failure();

        long totalRetries();

        long totalRetriesInARow();

        default ContextView retryContextView() {
            return Context.empty();
        }

        default RetrySignal copy() {
            return new ImmutableRetrySignal(totalRetries(), totalRetriesInARow(), failure(), retryContextView());
        }
    }

    public static RetryBackoffSpec backoff(long j, Duration duration) {
        return new RetryBackoffSpec(Context.empty(), j, new Predicate() { // from class: reactor.util.retry.Retry$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Retry.lambda$backoff$0((Throwable) obj);
            }
        }, false, duration, RetrySpec.MAX_BACKOFF, 2.0d, 0.5d, new Retry$$ExternalSyntheticLambda3(), RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.NO_OP_BIFUNCTION, RetryBackoffSpec.BACKOFF_EXCEPTION_GENERATOR);
    }

    public static RetryBackoffSpec fixedDelay(long j, Duration duration) {
        return new RetryBackoffSpec(Context.empty(), j, new Predicate() { // from class: reactor.util.retry.Retry$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Retry.lambda$fixedDelay$1((Throwable) obj);
            }
        }, false, duration, duration, 2.0d, 0.0d, new Retry$$ExternalSyntheticLambda3(), RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.NO_OP_BIFUNCTION, RetryBackoffSpec.BACKOFF_EXCEPTION_GENERATOR);
    }

    public static RetrySpec max(long j) {
        return new RetrySpec(Context.empty(), j, new Predicate() { // from class: reactor.util.retry.Retry$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Retry.lambda$max$2((Throwable) obj);
            }
        }, false, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.RETRY_EXCEPTION_GENERATOR);
    }

    public static RetrySpec maxInARow(long j) {
        return new RetrySpec(Context.empty(), j, new Predicate() { // from class: reactor.util.retry.Retry$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Retry.lambda$maxInARow$3((Throwable) obj);
            }
        }, true, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.RETRY_EXCEPTION_GENERATOR);
    }

    public static RetrySpec indefinitely() {
        return new RetrySpec(Context.empty(), Long.MAX_VALUE, new Predicate() { // from class: reactor.util.retry.Retry$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Retry.lambda$indefinitely$4((Throwable) obj);
            }
        }, false, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_CONSUMER, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.NO_OP_BIFUNCTION, RetrySpec.RETRY_EXCEPTION_GENERATOR);
    }

    public static final Retry from(final Function<Flux<RetrySignal>, ? extends Publisher<?>> function) {
        return new Retry(Context.empty()) { // from class: reactor.util.retry.Retry.1
            @Override // reactor.util.retry.Retry
            public Publisher<?> generateCompanion(Flux<RetrySignal> flux) {
                return (Publisher) function.apply(flux);
            }
        };
    }

    public static final Retry withThrowable(final Function<Flux<Throwable>, ? extends Publisher<?>> function) {
        return new Retry(Context.empty()) { // from class: reactor.util.retry.Retry.2
            @Override // reactor.util.retry.Retry
            public Publisher<?> generateCompanion(Flux<RetrySignal> flux) {
                return (Publisher) function.apply(flux.map(new Function() { // from class: reactor.util.retry.Retry$2$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((Retry.RetrySignal) obj).failure();
                    }
                }));
            }
        };
    }
}
