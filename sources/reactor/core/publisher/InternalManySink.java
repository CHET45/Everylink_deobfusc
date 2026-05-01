package reactor.core.publisher;

import reactor.core.Exceptions;
import reactor.core.publisher.Sinks;

/* JADX INFO: loaded from: classes5.dex */
interface InternalManySink<T> extends Sinks.Many<T>, ContextHolder {
    @Override // reactor.core.publisher.Sinks.Many
    default void emitNext(T t, Sinks.EmitFailureHandler emitFailureHandler) {
        Sinks.EmitResult emitResultTryEmitNext;
        do {
            emitResultTryEmitNext = tryEmitNext(t);
            if (emitResultTryEmitNext.isSuccess()) {
                return;
            }
        } while (emitFailureHandler.onEmitFailure(SignalType.ON_NEXT, emitResultTryEmitNext));
        int i = C51521.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitNext.ordinal()];
        if (i != 1) {
            if (i == 2) {
                Operators.onDiscard(t, currentContext());
                emitError(Exceptions.failWithOverflow("Backpressure overflow during Sinks.Many#emitNext"), emitFailureHandler);
            } else if (i == 3) {
                Operators.onDiscard(t, currentContext());
            } else if (i == 4) {
                Operators.onNextDropped(t, currentContext());
            } else {
                if (i == 5) {
                    throw new Sinks.EmissionException(emitResultTryEmitNext, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
                }
                throw new Sinks.EmissionException(emitResultTryEmitNext, "Unknown emitResult value");
            }
        }
    }

    /* JADX INFO: renamed from: reactor.core.publisher.InternalManySink$1 */
    static /* synthetic */ class C51521 {
        static final /* synthetic */ int[] $SwitchMap$reactor$core$publisher$Sinks$EmitResult;

        static {
            int[] iArr = new int[Sinks.EmitResult.values().length];
            $SwitchMap$reactor$core$publisher$Sinks$EmitResult = iArr;
            try {
                iArr[Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$reactor$core$publisher$Sinks$EmitResult[Sinks.EmitResult.FAIL_OVERFLOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$reactor$core$publisher$Sinks$EmitResult[Sinks.EmitResult.FAIL_CANCELLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$reactor$core$publisher$Sinks$EmitResult[Sinks.EmitResult.FAIL_TERMINATED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$reactor$core$publisher$Sinks$EmitResult[Sinks.EmitResult.FAIL_NON_SERIALIZED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    @Override // reactor.core.publisher.Sinks.Many
    default void emitComplete(Sinks.EmitFailureHandler emitFailureHandler) {
        Sinks.EmitResult emitResultTryEmitComplete;
        do {
            emitResultTryEmitComplete = tryEmitComplete();
            if (emitResultTryEmitComplete.isSuccess()) {
                return;
            }
        } while (emitFailureHandler.onEmitFailure(SignalType.ON_COMPLETE, emitResultTryEmitComplete));
        int i = C51521.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitComplete.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            return;
        }
        if (i == 5) {
            throw new Sinks.EmissionException(emitResultTryEmitComplete, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
        }
        throw new Sinks.EmissionException(emitResultTryEmitComplete, "Unknown emitResult value");
    }

    @Override // reactor.core.publisher.Sinks.Many
    default void emitError(Throwable th, Sinks.EmitFailureHandler emitFailureHandler) {
        Sinks.EmitResult emitResultTryEmitError;
        do {
            emitResultTryEmitError = tryEmitError(th);
            if (emitResultTryEmitError.isSuccess()) {
                return;
            }
        } while (emitFailureHandler.onEmitFailure(SignalType.ON_ERROR, emitResultTryEmitError));
        int i = C51521.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitError.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return;
        }
        if (i == 4) {
            Operators.onErrorDropped(th, currentContext());
        } else {
            if (i == 5) {
                throw new Sinks.EmissionException(emitResultTryEmitError, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
            }
            throw new Sinks.EmissionException(emitResultTryEmitError, "Unknown emitResult value");
        }
    }
}
