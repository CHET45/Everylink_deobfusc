package reactor.core;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Exceptions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String BACKPRESSURE_ERROR_QUEUE_FULL = "Queue is full: Reactive Streams source doesn't respect backpressure";
    private static final Logger LOGGER = Loggers.getLogger((Class<?>) Exceptions.class);
    public static final Throwable TERMINATED = new StaticThrowable("Operator has been terminated");
    static final RejectedExecutionException REJECTED_EXECUTION = new StaticRejectedExecutionException("Scheduler unavailable");
    static final RejectedExecutionException NOT_TIME_CAPABLE_REJECTED_EXECUTION = new StaticRejectedExecutionException("Scheduler is not capable of time-based scheduling");
    public static final Consumer<? super AutoCloseable> AUTO_CLOSE = new Consumer() { // from class: reactor.core.Exceptions$$ExternalSyntheticLambda0
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            Exceptions.lambda$static$0((AutoCloseable) obj);
        }
    };

    public static <T> boolean addThrowable(AtomicReferenceFieldUpdater<T, Throwable> atomicReferenceFieldUpdater, T t, Throwable th) {
        Throwable th2;
        do {
            th2 = atomicReferenceFieldUpdater.get(t);
            if (th2 == TERMINATED) {
                return false;
            }
            if (th2 instanceof CompositeException) {
                th2.addSuppressed(th);
                return true;
            }
        } while (!C0162xc40028dd.m5m(atomicReferenceFieldUpdater, t, th2, th2 == null ? th : multiple(th2, th)));
        return true;
    }

    public static Throwable wrapSource(Throwable th) {
        return new SourceException(th);
    }

    public static RuntimeException multiple(Throwable... thArr) {
        CompositeException compositeException = new CompositeException();
        if (thArr != null) {
            for (Throwable th : thArr) {
                compositeException.addSuppressed(th);
            }
        }
        return compositeException;
    }

    public static RuntimeException multiple(Iterable<Throwable> iterable) {
        CompositeException compositeException = new CompositeException();
        if (iterable != null) {
            Iterator<Throwable> it = iterable.iterator();
            while (it.hasNext()) {
                compositeException.addSuppressed(it.next());
            }
        }
        return compositeException;
    }

    public static RuntimeException bubble(Throwable th) {
        throwIfFatal(th);
        return new BubblingException(th);
    }

    public static IllegalStateException duplicateOnSubscribeException() {
        return new IllegalStateException("Spec. Rule 2.12 - Subscriber.onSubscribe MUST NOT be called more than once (based on object equality)");
    }

    public static UnsupportedOperationException errorCallbackNotImplemented(Throwable th) {
        Objects.requireNonNull(th, "cause");
        return new ErrorCallbackNotImplemented(th);
    }

    public static RuntimeException failWithCancel() {
        return new CancelException();
    }

    public static IllegalStateException failWithOverflow() {
        return new OverflowException("The receiver is overrun by more signals than expected (bounded queue...)");
    }

    public static IllegalStateException failWithOverflow(String str) {
        return new OverflowException(str);
    }

    public static RejectedExecutionException failWithRejected() {
        return REJECTED_EXECUTION;
    }

    public static RejectedExecutionException failWithRejectedNotTimeCapable() {
        return NOT_TIME_CAPABLE_REJECTED_EXECUTION;
    }

    public static RejectedExecutionException failWithRejected(Throwable th) {
        if (th instanceof ReactorRejectedExecutionException) {
            return (RejectedExecutionException) th;
        }
        return new ReactorRejectedExecutionException("Scheduler unavailable", th);
    }

    public static RejectedExecutionException failWithRejected(String str) {
        return new ReactorRejectedExecutionException(str);
    }

    public static RuntimeException retryExhausted(String str, @Nullable Throwable th) {
        return th == null ? new RetryExhaustedException(str) : new RetryExhaustedException(str, th);
    }

    public static boolean isOverflow(@Nullable Throwable th) {
        return th instanceof OverflowException;
    }

    public static boolean isBubbling(@Nullable Throwable th) {
        return th instanceof BubblingException;
    }

    public static boolean isCancel(@Nullable Throwable th) {
        return th instanceof CancelException;
    }

    public static boolean isErrorCallbackNotImplemented(@Nullable Throwable th) {
        return th instanceof ErrorCallbackNotImplemented;
    }

    public static boolean isMultiple(@Nullable Throwable th) {
        return th instanceof CompositeException;
    }

    public static boolean isRetryExhausted(@Nullable Throwable th) {
        return th instanceof RetryExhaustedException;
    }

    public static boolean isTraceback(@Nullable Throwable th) {
        if (th == null) {
            return false;
        }
        return "reactor.core.publisher.FluxOnAssembly.OnAssemblyException".equals(th.getClass().getCanonicalName());
    }

    public static IllegalArgumentException nullOrNegativeRequestException(long j) {
        return new IllegalArgumentException("Spec. Rule 3.9 - Cannot request a non strictly positive number: " + j);
    }

    public static RuntimeException propagate(Throwable th) {
        throwIfFatal(th);
        if (th instanceof RuntimeException) {
            return (RuntimeException) th;
        }
        return new ReactiveException(th);
    }

    @Nullable
    public static <T> Throwable terminate(AtomicReferenceFieldUpdater<T, Throwable> atomicReferenceFieldUpdater, T t) {
        Throwable th = atomicReferenceFieldUpdater.get(t);
        Throwable th2 = TERMINATED;
        return th != th2 ? atomicReferenceFieldUpdater.getAndSet(t, th2) : th;
    }

    public static boolean isJvmFatal(@Nullable Throwable th) {
        return (th instanceof VirtualMachineError) || (th instanceof ThreadDeath) || (th instanceof LinkageError);
    }

    public static boolean isFatal(@Nullable Throwable th) {
        return isFatalButNotJvmFatal(th) || isJvmFatal(th);
    }

    static boolean isFatalButNotJvmFatal(@Nullable Throwable th) {
        return (th instanceof BubblingException) || (th instanceof ErrorCallbackNotImplemented);
    }

    public static void throwIfFatal(@Nullable Throwable th) {
        if (th == null) {
            return;
        }
        if (isFatalButNotJvmFatal(th)) {
            LOGGER.warn("throwIfFatal detected a fatal exception, which is thrown and logged below:", th);
            throw ((RuntimeException) th);
        }
        if (isJvmFatal(th)) {
            LOGGER.warn("throwIfFatal detected a jvm fatal exception, which is thrown and logged below:", th);
            throw ((Error) th);
        }
    }

    public static void throwIfJvmFatal(@Nullable Throwable th) {
        if (th != null && isJvmFatal(th)) {
            LOGGER.warn("throwIfJvmFatal detected a jvm fatal exception, which is thrown and logged below:", th);
            throw ((Error) th);
        }
    }

    public static Throwable unwrap(Throwable th) {
        Throwable cause = th;
        while (cause instanceof ReactiveException) {
            cause = cause.getCause();
        }
        return cause == null ? th : cause;
    }

    public static List<Throwable> unwrapMultiple(@Nullable Throwable th) {
        if (th == null) {
            return Collections.emptyList();
        }
        if (isMultiple(th)) {
            return Arrays.asList(th.getSuppressed());
        }
        return Collections.singletonList(th);
    }

    public static List<Throwable> unwrapMultipleExcludingTracebacks(@Nullable Throwable th) {
        if (th == null) {
            return Collections.emptyList();
        }
        if (isMultiple(th)) {
            Throwable[] suppressed = th.getSuppressed();
            ArrayList arrayList = new ArrayList(suppressed.length);
            for (Throwable th2 : suppressed) {
                if (!isTraceback(th2)) {
                    arrayList.add(th2);
                }
            }
            return arrayList;
        }
        return Collections.singletonList(th);
    }

    public static final RuntimeException addSuppressed(RuntimeException runtimeException, Throwable th) {
        if (runtimeException == th) {
            return runtimeException;
        }
        if (runtimeException == REJECTED_EXECUTION || runtimeException == NOT_TIME_CAPABLE_REJECTED_EXECUTION) {
            RejectedExecutionException rejectedExecutionException = new RejectedExecutionException(runtimeException.getMessage());
            rejectedExecutionException.addSuppressed(th);
            return rejectedExecutionException;
        }
        runtimeException.addSuppressed(th);
        return runtimeException;
    }

    public static final Throwable addSuppressed(Throwable th, Throwable th2) {
        if (th == th2 || th == TERMINATED) {
            return th;
        }
        if (th == REJECTED_EXECUTION || th == NOT_TIME_CAPABLE_REJECTED_EXECUTION) {
            RejectedExecutionException rejectedExecutionException = new RejectedExecutionException(th.getMessage());
            rejectedExecutionException.addSuppressed(th2);
            return rejectedExecutionException;
        }
        th.addSuppressed(th2);
        return th;
    }

    Exceptions() {
    }

    static class CompositeException extends ReactiveException {
        private static final long serialVersionUID = 8070744939537687606L;

        CompositeException() {
            super("Multiple exceptions");
        }
    }

    static class BubblingException extends ReactiveException {
        private static final long serialVersionUID = 2491425277432776142L;

        BubblingException(String str) {
            super(str);
        }

        BubblingException(Throwable th) {
            super(th);
        }
    }

    static class ReactiveException extends RuntimeException {
        private static final long serialVersionUID = 2491425227432776143L;

        ReactiveException(Throwable th) {
            super(th);
        }

        ReactiveException(String str) {
            super(str);
        }

        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            return getCause() != null ? getCause().fillInStackTrace() : super.fillInStackTrace();
        }
    }

    public static class SourceException extends ReactiveException {
        private static final long serialVersionUID = 5747581575202629465L;

        @Override // reactor.core.Exceptions.ReactiveException, java.lang.Throwable
        public /* bridge */ /* synthetic */ Throwable fillInStackTrace() {
            return super.fillInStackTrace();
        }

        SourceException(Throwable th) {
            super(th);
        }
    }

    static final class ErrorCallbackNotImplemented extends UnsupportedOperationException {
        private static final long serialVersionUID = 2491425227432776143L;

        ErrorCallbackNotImplemented(Throwable th) {
            super(th);
        }

        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    static final class CancelException extends BubblingException {
        private static final long serialVersionUID = 2491425227432776144L;

        CancelException() {
            super("The subscriber has denied dispatching");
        }
    }

    static final class OverflowException extends IllegalStateException {
        OverflowException(String str) {
            super(str);
        }
    }

    static final class RetryExhaustedException extends IllegalStateException {
        RetryExhaustedException(String str) {
            super(str);
        }

        RetryExhaustedException(String str, Throwable th) {
            super(str, th);
        }
    }

    static class ReactorRejectedExecutionException extends RejectedExecutionException {
        ReactorRejectedExecutionException(String str, Throwable th) {
            super(str, th);
        }

        ReactorRejectedExecutionException(String str) {
            super(str);
        }
    }

    static final class StaticRejectedExecutionException extends RejectedExecutionException {
        StaticRejectedExecutionException(String str, Throwable th) {
            super(str, th);
        }

        StaticRejectedExecutionException(String str) {
            super(str);
        }

        @Override // java.lang.Throwable
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }

    static final class StaticThrowable extends Error {
        StaticThrowable(String str) {
            super(str, null, false, false);
        }

        StaticThrowable(String str, Throwable th) {
            super(str, th, false, false);
        }

        StaticThrowable(Throwable th) {
            super(th.toString(), th, false, false);
        }
    }

    static /* synthetic */ void lambda$static$0(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Throwable th) {
            throw propagate(th);
        }
    }
}
