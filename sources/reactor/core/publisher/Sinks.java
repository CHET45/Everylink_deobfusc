package reactor.core.publisher;

import java.time.Duration;
import java.util.Queue;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public final class Sinks {

    public interface Empty<T> extends Scannable {
        Mono<T> asMono();

        int currentSubscriberCount();

        void emitEmpty(EmitFailureHandler emitFailureHandler);

        void emitError(Throwable th, EmitFailureHandler emitFailureHandler);

        EmitResult tryEmitEmpty();

        EmitResult tryEmitError(Throwable th);
    }

    public interface Many<T> extends Scannable {
        Flux<T> asFlux();

        int currentSubscriberCount();

        void emitComplete(EmitFailureHandler emitFailureHandler);

        void emitError(Throwable th, EmitFailureHandler emitFailureHandler);

        void emitNext(T t, EmitFailureHandler emitFailureHandler);

        EmitResult tryEmitComplete();

        EmitResult tryEmitError(Throwable th);

        EmitResult tryEmitNext(T t);
    }

    public interface ManySpec {
        MulticastSpec multicast();

        MulticastReplaySpec replay();

        UnicastSpec unicast();
    }

    public interface ManyWithUpstream<T> extends Many<T> {
        Disposable subscribeTo(Publisher<? extends T> publisher);
    }

    public interface ManyWithUpstreamUnsafeSpec {
        <T> ManyWithUpstream<T> multicastOnBackpressureBuffer();

        <T> ManyWithUpstream<T> multicastOnBackpressureBuffer(int i, boolean z);
    }

    public interface MulticastReplaySpec {
        <T> Many<T> all();

        <T> Many<T> all(int i);

        <T> Many<T> latest();

        <T> Many<T> latestOrDefault(T t);

        <T> Many<T> limit(int i);

        <T> Many<T> limit(int i, Duration duration);

        <T> Many<T> limit(int i, Duration duration, Scheduler scheduler);

        <T> Many<T> limit(Duration duration);

        <T> Many<T> limit(Duration duration, Scheduler scheduler);
    }

    public interface MulticastSpec {
        <T> Many<T> directAllOrNothing();

        <T> Many<T> directBestEffort();

        <T> Many<T> onBackpressureBuffer();

        <T> Many<T> onBackpressureBuffer(int i);

        <T> Many<T> onBackpressureBuffer(int i, boolean z);
    }

    public interface One<T> extends Empty<T> {
        void emitValue(@Nullable T t, EmitFailureHandler emitFailureHandler);

        EmitResult tryEmitValue(@Nullable T t);
    }

    public interface RootSpec {
        <T> Empty<T> empty();

        ManySpec many();

        ManyWithUpstreamUnsafeSpec manyWithUpstream();

        <T> One<T> one();
    }

    public interface UnicastSpec {
        <T> Many<T> onBackpressureBuffer();

        <T> Many<T> onBackpressureBuffer(Queue<T> queue);

        <T> Many<T> onBackpressureBuffer(Queue<T> queue, Disposable disposable);

        <T> Many<T> onBackpressureError();
    }

    private Sinks() {
    }

    public static <T> Empty<T> empty() {
        return SinksSpecs.DEFAULT_SINKS.empty();
    }

    public static <T> One<T> one() {
        return SinksSpecs.DEFAULT_SINKS.one();
    }

    public static ManySpec many() {
        return SinksSpecs.DEFAULT_SINKS.many();
    }

    public static RootSpec unsafe() {
        return SinksSpecs.UNSAFE_ROOT_SPEC;
    }

    public enum EmitResult {
        OK,
        FAIL_TERMINATED,
        FAIL_OVERFLOW,
        FAIL_CANCELLED,
        FAIL_NON_SERIALIZED,
        FAIL_ZERO_SUBSCRIBER;

        public boolean isSuccess() {
            return this == OK;
        }

        public boolean isFailure() {
            return this != OK;
        }

        public void orThrow() {
            if (this != OK) {
                throw new EmissionException(this);
            }
        }

        public void orThrowWithCause(Throwable th) {
            if (this != OK) {
                throw new EmissionException(th, this);
            }
        }
    }

    public static final class EmissionException extends IllegalStateException {
        final EmitResult reason;

        public EmissionException(EmitResult emitResult) {
            this(emitResult, "Sink emission failed with " + emitResult);
        }

        public EmissionException(Throwable th, EmitResult emitResult) {
            super("Sink emission failed with " + emitResult, th);
            this.reason = emitResult;
        }

        public EmissionException(EmitResult emitResult, String str) {
            super(str);
            this.reason = emitResult;
        }

        public EmitResult getReason() {
            return this.reason;
        }
    }

    static class OptimisticEmitFailureHandler implements EmitFailureHandler {
        private final long deadline;

        OptimisticEmitFailureHandler(Duration duration) {
            this.deadline = System.nanoTime() + duration.toNanos();
        }

        @Override // reactor.core.publisher.Sinks.EmitFailureHandler
        public boolean onEmitFailure(SignalType signalType, EmitResult emitResult) {
            return emitResult.equals(EmitResult.FAIL_NON_SERIALIZED) && System.nanoTime() < this.deadline;
        }
    }

    public interface EmitFailureHandler {
        public static final EmitFailureHandler FAIL_FAST = new EmitFailureHandler() { // from class: reactor.core.publisher.Sinks$EmitFailureHandler$$ExternalSyntheticLambda0
            @Override // reactor.core.publisher.Sinks.EmitFailureHandler
            public final boolean onEmitFailure(SignalType signalType, Sinks.EmitResult emitResult) {
                return Sinks.EmitFailureHandler.lambda$static$0(signalType, emitResult);
            }
        };

        static /* synthetic */ boolean lambda$static$0(SignalType signalType, EmitResult emitResult) {
            return false;
        }

        boolean onEmitFailure(SignalType signalType, EmitResult emitResult);

        static EmitFailureHandler busyLooping(Duration duration) {
            return new OptimisticEmitFailureHandler(duration);
        }
    }
}
