package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
class NextProcessor<O> extends MonoProcessor<O> implements CoreSubscriber<O>, Disposable, Scannable {

    @Nullable
    Throwable error;
    final boolean isRefCounted;

    @Nullable
    CorePublisher<? extends O> source;
    volatile NextInner<O>[] subscribers;
    volatile Subscription subscription;

    @Nullable
    O value;
    static final AtomicReferenceFieldUpdater<NextProcessor, NextInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(NextProcessor.class, NextInner[].class, "subscribers");
    static final NextInner[] EMPTY = new NextInner[0];
    static final NextInner[] TERMINATED = new NextInner[0];
    static final NextInner[] EMPTY_WITH_SOURCE = new NextInner[0];
    static final AtomicReferenceFieldUpdater<NextProcessor, Subscription> UPSTREAM = AtomicReferenceFieldUpdater.newUpdater(NextProcessor.class, Subscription.class, "subscription");

    @Override // reactor.core.publisher.MonoProcessor, reactor.core.publisher.Mono
    @Nullable
    public O block() {
        return block(null);
    }

    @Override // reactor.core.publisher.MonoProcessor, reactor.core.Disposable
    public boolean isDisposed() {
        return isTerminated();
    }

    NextProcessor(@Nullable CorePublisher<? extends O> corePublisher) {
        this(corePublisher, false);
    }

    NextProcessor(@Nullable CorePublisher<? extends O> corePublisher, boolean z) {
        this.source = corePublisher;
        this.isRefCounted = z;
        SUBSCRIBERS.lazySet(this, corePublisher != null ? EMPTY_WITH_SOURCE : EMPTY);
    }

    @Override // reactor.core.publisher.MonoProcessor
    @Nullable
    public O peek() {
        if (!isTerminated()) {
            return null;
        }
        O o = this.value;
        if (o != null) {
            return o;
        }
        Throwable th = this.error;
        if (th == null) {
            return null;
        }
        throw Exceptions.addSuppressed(Exceptions.propagate(th), (Throwable) new Exception("Mono#peek terminated with an error"));
    }

    @Override // reactor.core.publisher.MonoProcessor, reactor.core.publisher.Mono
    @Nullable
    public O block(@Nullable Duration duration) {
        try {
            if (isTerminated()) {
                return peek();
            }
            connect();
            long jNanoTime = duration == null ? 0L : System.nanoTime() + duration.toNanos();
            while (!isTerminated()) {
                if (duration != null && jNanoTime < System.nanoTime()) {
                    doCancel();
                    throw new IllegalStateException("Timeout on Mono blocking read");
                }
                Thread.sleep(1L);
            }
            Throwable th = this.error;
            if (th != null) {
                throw Exceptions.addSuppressed(Exceptions.propagate(th), (Throwable) new Exception("Mono#block terminated with an error"));
            }
            return this.value;
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread Interruption on Mono blocking read");
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onComplete() {
        tryEmitValue(null);
    }

    void emitEmpty(Sinks.EmitFailureHandler emitFailureHandler) {
        Sinks.EmitResult emitResultTryEmitValue;
        do {
            emitResultTryEmitValue = tryEmitValue(null);
            if (emitResultTryEmitValue.isSuccess()) {
                return;
            }
        } while (emitFailureHandler.onEmitFailure(SignalType.ON_COMPLETE, emitResultTryEmitValue));
        int i = C51571.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitValue.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            return;
        }
        if (i == 5) {
            throw new Sinks.EmissionException(emitResultTryEmitValue, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
        }
        throw new Sinks.EmissionException(emitResultTryEmitValue, "Unknown emitResult value");
    }

    /* JADX INFO: renamed from: reactor.core.publisher.NextProcessor$1 */
    static /* synthetic */ class C51571 {
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

    @Override // org.reactivestreams.Subscriber
    public final void onError(Throwable th) {
        Sinks.EmitResult emitResultTryEmitError;
        do {
            emitResultTryEmitError = tryEmitError(th);
            if (emitResultTryEmitError.isSuccess()) {
                return;
            }
        } while (Sinks.EmitFailureHandler.FAIL_FAST.onEmitFailure(SignalType.ON_ERROR, emitResultTryEmitError));
        int i = C51571.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitError.ordinal()];
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

    Sinks.EmitResult tryEmitError(Throwable th) {
        Objects.requireNonNull(th, "onError cannot be null");
        if (UPSTREAM.getAndSet(this, Operators.cancelledSubscription()) == Operators.cancelledSubscription()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = th;
        this.value = null;
        this.source = null;
        for (NextInner nextInner : SUBSCRIBERS.getAndSet(this, TERMINATED)) {
            nextInner.onError(th);
        }
        return Sinks.EmitResult.OK;
    }

    @Override // org.reactivestreams.Subscriber
    public final void onNext(@Nullable O o) {
        Sinks.EmitResult emitResultTryEmitValue;
        if (o == null) {
            emitEmpty(Sinks.EmitFailureHandler.FAIL_FAST);
            return;
        }
        do {
            emitResultTryEmitValue = tryEmitValue(o);
            if (emitResultTryEmitValue.isSuccess()) {
                return;
            }
        } while (Sinks.EmitFailureHandler.FAIL_FAST.onEmitFailure(SignalType.ON_NEXT, emitResultTryEmitValue));
        int i = C51571.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[emitResultTryEmitValue.ordinal()];
        if (i != 1) {
            if (i == 2) {
                Operators.onDiscard(o, currentContext());
                onError(Exceptions.failWithOverflow("Backpressure overflow during Sinks.Many#emitNext"));
            } else if (i == 3) {
                Operators.onDiscard(o, currentContext());
            } else if (i == 4) {
                Operators.onNextDropped(o, currentContext());
            } else {
                if (i == 5) {
                    throw new Sinks.EmissionException(emitResultTryEmitValue, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
                }
                throw new Sinks.EmissionException(emitResultTryEmitValue, "Unknown emitResult value");
            }
        }
    }

    Sinks.EmitResult tryEmitValue(@Nullable O o) {
        Subscription andSet = UPSTREAM.getAndSet(this, Operators.cancelledSubscription());
        if (andSet == Operators.cancelledSubscription()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.value = o;
        CorePublisher<? extends O> corePublisher = this.source;
        this.source = null;
        NextInner[] andSet2 = SUBSCRIBERS.getAndSet(this, TERMINATED);
        int i = 0;
        if (o == null) {
            int length = andSet2.length;
            while (i < length) {
                andSet2[i].onComplete();
                i++;
            }
        } else {
            if (andSet != null && !(corePublisher instanceof Mono)) {
                andSet.cancel();
            }
            int length2 = andSet2.length;
            while (i < length2) {
                andSet2[i].complete(o);
                i++;
            }
        }
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.MonoProcessor, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        boolean zIsTerminated = isTerminated();
        if (attr == Scannable.Attr.TERMINATED) {
            return Boolean.valueOf(zIsTerminated);
        }
        if (attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(!zIsTerminated && this.subscription == Operators.cancelledSubscription());
        }
        if (attr == Scannable.Attr.ERROR) {
            return getError();
        }
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override // reactor.core.publisher.MonoProcessor, reactor.core.CoreSubscriber
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    @Override // reactor.core.publisher.MonoProcessor
    public long downstreamCount() {
        return this.subscribers.length;
    }

    @Override // reactor.core.publisher.MonoProcessor, reactor.core.Disposable
    public void dispose() {
        Subscription andSet = UPSTREAM.getAndSet(this, Operators.cancelledSubscription());
        if (andSet == Operators.cancelledSubscription()) {
            return;
        }
        this.source = null;
        if (andSet != null) {
            andSet.cancel();
        }
        AtomicReferenceFieldUpdater<NextProcessor, NextInner[]> atomicReferenceFieldUpdater = SUBSCRIBERS;
        NextInner[] nextInnerArr = TERMINATED;
        NextInner[] andSet2 = atomicReferenceFieldUpdater.getAndSet(this, nextInnerArr);
        if (andSet2 != nextInnerArr) {
            CancellationException cancellationException = new CancellationException("Disposed");
            this.error = cancellationException;
            this.value = null;
            for (NextInner nextInner : andSet2) {
                nextInner.onError(cancellationException);
            }
        }
    }

    @Override // reactor.core.publisher.MonoProcessor, org.reactivestreams.Subscription
    public void cancel() {
        doCancel();
    }

    void doCancel() {
        Subscription andSet;
        if (isTerminated() || (andSet = UPSTREAM.getAndSet(this, Operators.cancelledSubscription())) == Operators.cancelledSubscription()) {
            return;
        }
        this.source = null;
        if (andSet != null) {
            andSet.cancel();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        if (Operators.setOnce(UPSTREAM, this, subscription)) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override // reactor.core.publisher.MonoProcessor
    public boolean isCancelled() {
        return this.subscription == Operators.cancelledSubscription() && !isTerminated();
    }

    @Override // reactor.core.publisher.MonoProcessor
    public boolean isTerminated() {
        return this.subscribers == TERMINATED;
    }

    @Override // reactor.core.publisher.MonoProcessor
    @Nullable
    public Throwable getError() {
        return this.error;
    }

    boolean add(NextInner<O> nextInner) {
        NextInner<O>[] nextInnerArr;
        NextInner[] nextInnerArr2;
        do {
            nextInnerArr = this.subscribers;
            if (nextInnerArr == TERMINATED) {
                return false;
            }
            int length = nextInnerArr.length;
            nextInnerArr2 = new NextInner[length + 1];
            System.arraycopy(nextInnerArr, 0, nextInnerArr2, 0, length);
            nextInnerArr2[length] = nextInner;
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, nextInnerArr, nextInnerArr2));
        CorePublisher<? extends O> corePublisher = this.source;
        if (corePublisher == null || nextInnerArr != EMPTY_WITH_SOURCE) {
            return true;
        }
        corePublisher.subscribe((Subscriber<? super Object>) this);
        return true;
    }

    void remove(NextInner<O> nextInner) {
        NextInner<O>[] nextInnerArr;
        boolean z;
        NextInner[] nextInnerArr2;
        Subscription andSet;
        do {
            nextInnerArr = this.subscribers;
            int length = nextInnerArr.length;
            if (length == 0) {
                return;
            }
            z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (nextInnerArr[i] == nextInner) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                if (this.isRefCounted && this.source != null) {
                    nextInnerArr2 = EMPTY_WITH_SOURCE;
                    z = true;
                } else {
                    nextInnerArr2 = EMPTY;
                }
            } else {
                NextInner[] nextInnerArr3 = new NextInner[length - 1];
                System.arraycopy(nextInnerArr, 0, nextInnerArr3, 0, i);
                System.arraycopy(nextInnerArr, i + 1, nextInnerArr3, i, (length - i) - 1);
                nextInnerArr2 = nextInnerArr3;
            }
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, nextInnerArr, nextInnerArr2));
        if (!z || (andSet = UPSTREAM.getAndSet(this, null)) == null) {
            return;
        }
        andSet.cancel();
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super O> coreSubscriber) {
        NextInner<O> nextInner = new NextInner<>(coreSubscriber, this);
        coreSubscriber.onSubscribe(nextInner);
        if (add(nextInner)) {
            if (nextInner.isCancelled()) {
                remove(nextInner);
                return;
            }
            return;
        }
        Throwable th = this.error;
        if (th != null) {
            coreSubscriber.onError(th);
            return;
        }
        O o = this.value;
        if (o != null) {
            nextInner.complete(o);
        } else {
            nextInner.onComplete();
        }
    }

    @Override // reactor.core.publisher.MonoProcessor, reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[]) this.subscribers);
    }

    void connect() {
        CorePublisher<? extends O> corePublisher = this.source;
        if (corePublisher == null || !C0162xc40028dd.m5m(SUBSCRIBERS, this, EMPTY_WITH_SOURCE, EMPTY)) {
            return;
        }
        corePublisher.subscribe((Subscriber<? super Object>) this);
    }

    static final class NextInner<T> extends Operators.MonoSubscriber<T, T> {
        final NextProcessor<T> parent;

        NextInner(CoreSubscriber<? super T> coreSubscriber, NextProcessor<T> nextProcessor) {
            super(coreSubscriber);
            this.parent = nextProcessor;
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, org.reactivestreams.Subscription
        public void cancel() {
            if (STATE.getAndSet(this, 4) != 4) {
                this.parent.remove(this);
            }
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (isCancelled()) {
                return;
            }
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (isCancelled()) {
                return;
            }
            this.actual.onError(th);
        }

        @Override // reactor.core.publisher.Operators.MonoSubscriber, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }
    }
}
