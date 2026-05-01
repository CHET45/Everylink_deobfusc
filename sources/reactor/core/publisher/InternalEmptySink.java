package reactor.core.publisher;

import reactor.core.publisher.Sinks;

/* JADX INFO: loaded from: classes5.dex */
interface InternalEmptySink<T> extends Sinks.Empty<T>, ContextHolder {
    @Override // reactor.core.publisher.Sinks.Empty
    default void emitEmpty(Sinks.EmitFailureHandler emitFailureHandler) {
        Sinks.EmitResult emitResultTryEmitEmpty;
        do {
            emitResultTryEmitEmpty = tryEmitEmpty();
            if (emitResultTryEmitEmpty.isSuccess()) {
                return;
            }
        } while (emitFailureHandler.onEmitFailure(SignalType.ON_COMPLETE, emitResultTryEmitEmpty));
        int i = C51511.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitEmpty.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            return;
        }
        if (i == 5) {
            throw new Sinks.EmissionException(emitResultTryEmitEmpty, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
        }
        throw new Sinks.EmissionException(emitResultTryEmitEmpty, "Unknown emitResult value");
    }

    /* JADX INFO: renamed from: reactor.core.publisher.InternalEmptySink$1 */
    static /* synthetic */ class C51511 {
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

    @Override // reactor.core.publisher.Sinks.Empty
    default void emitError(Throwable th, Sinks.EmitFailureHandler emitFailureHandler) {
        Sinks.EmitResult emitResultTryEmitError;
        do {
            emitResultTryEmitError = tryEmitError(th);
            if (emitResultTryEmitError.isSuccess()) {
                return;
            }
        } while (emitFailureHandler.onEmitFailure(SignalType.ON_ERROR, emitResultTryEmitError));
        int i = C51511.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitError.ordinal()];
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
