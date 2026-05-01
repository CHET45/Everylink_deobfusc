package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.SinkManyBestEffort;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
public final class DirectProcessor<T> extends FluxProcessor<T, T> implements DirectInnerContainer<T> {
    private static final AtomicReferenceFieldUpdater<DirectProcessor, SinkManyBestEffort.DirectInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(DirectProcessor.class, SinkManyBestEffort.DirectInner[].class, "subscribers");
    Throwable error;
    private volatile SinkManyBestEffort.DirectInner<T>[] subscribers = SinkManyBestEffort.EMPTY;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override // reactor.core.publisher.FluxProcessor
    protected boolean isIdentityProcessor() {
        return true;
    }

    @Deprecated
    public static <E> DirectProcessor<E> create() {
        return new DirectProcessor<>();
    }

    DirectProcessor() {
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.CoreSubscriber
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        Objects.requireNonNull(subscription, "s");
        if (this.subscribers != SinkManyBestEffort.TERMINATED) {
            subscription.request(Long.MAX_VALUE);
        } else {
            subscription.cancel();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        tryEmitComplete();
    }

    private void emitComplete() {
        tryEmitComplete();
    }

    private Sinks.EmitResult tryEmitComplete() {
        SinkManyBestEffort.DirectInner[] andSet = SUBSCRIBERS.getAndSet(this, SinkManyBestEffort.TERMINATED);
        if (andSet == SinkManyBestEffort.TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        for (SinkManyBestEffort.DirectInner directInner : andSet) {
            directInner.emitComplete();
        }
        return Sinks.EmitResult.OK;
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        emitError(th);
    }

    private void emitError(Throwable th) {
        if (tryEmitError(th) == Sinks.EmitResult.FAIL_TERMINATED) {
            Operators.onErrorDroppedMulticast(th, this.subscribers);
        }
    }

    private Sinks.EmitResult tryEmitError(Throwable th) {
        Objects.requireNonNull(th, "t");
        SinkManyBestEffort.DirectInner[] andSet = SUBSCRIBERS.getAndSet(this, SinkManyBestEffort.TERMINATED);
        if (andSet == SinkManyBestEffort.TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = th;
        for (SinkManyBestEffort.DirectInner directInner : andSet) {
            directInner.emitError(th);
        }
        return Sinks.EmitResult.OK;
    }

    /* JADX INFO: renamed from: reactor.core.publisher.DirectProcessor$1 */
    static /* synthetic */ class C51301 {
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
                $SwitchMap$reactor$core$publisher$Sinks$EmitResult[Sinks.EmitResult.OK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private void emitNext(T t) {
        int i = C51301.$SwitchMap$reactor$core$publisher$Sinks$EmitResult[tryEmitNext(t).ordinal()];
        if (i != 1) {
            if (i == 2) {
                Operators.onDiscard(t, currentContext());
                emitError(Exceptions.failWithOverflow("Backpressure overflow during Sinks.Many#emitNext"));
            } else if (i == 3) {
                Operators.onDiscard(t, currentContext());
            } else if (i == 4) {
                Operators.onNextDroppedMulticast(t, this.subscribers);
            } else if (i != 5) {
                throw new IllegalStateException("unexpected return code");
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        emitNext(t);
    }

    private Sinks.EmitResult tryEmitNext(T t) {
        Objects.requireNonNull(t, "t");
        SinkManyBestEffort.DirectInner<T>[] directInnerArr = this.subscribers;
        if (directInnerArr == SinkManyBestEffort.TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (directInnerArr == SinkManyBestEffort.EMPTY) {
            return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        for (SinkManyBestEffort.DirectInner<T> directInner : directInnerArr) {
            directInner.directEmitNext(t);
        }
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "subscribe");
        SinkManyBestEffort.DirectInner<T> directInner = new SinkManyBestEffort.DirectInner<>(coreSubscriber, this);
        coreSubscriber.onSubscribe(directInner);
        if (add(directInner)) {
            if (directInner.isCancelled()) {
                remove(directInner);
            }
        } else {
            Throwable th = this.error;
            if (th != null) {
                coreSubscriber.onError(th);
            } else {
                coreSubscriber.onComplete();
            }
        }
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[]) this.subscribers);
    }

    @Override // reactor.core.publisher.FluxProcessor
    public boolean isTerminated() {
        return SinkManyBestEffort.TERMINATED == this.subscribers;
    }

    @Override // reactor.core.publisher.FluxProcessor
    public long downstreamCount() {
        return this.subscribers.length;
    }

    @Override // reactor.core.publisher.DirectInnerContainer
    public boolean add(SinkManyBestEffort.DirectInner<T> directInner) {
        if (this.subscribers == SinkManyBestEffort.TERMINATED) {
            return false;
        }
        synchronized (this) {
            SinkManyBestEffort.DirectInner<T>[] directInnerArr = this.subscribers;
            if (directInnerArr == SinkManyBestEffort.TERMINATED) {
                return false;
            }
            int length = directInnerArr.length;
            SinkManyBestEffort.DirectInner<T>[] directInnerArr2 = new SinkManyBestEffort.DirectInner[length + 1];
            System.arraycopy(directInnerArr, 0, directInnerArr2, 0, length);
            directInnerArr2[length] = directInner;
            this.subscribers = directInnerArr2;
            return true;
        }
    }

    @Override // reactor.core.publisher.DirectInnerContainer
    public void remove(SinkManyBestEffort.DirectInner<T> directInner) {
        SinkManyBestEffort.DirectInner<T>[] directInnerArr = this.subscribers;
        if (directInnerArr == SinkManyBestEffort.TERMINATED || directInnerArr == SinkManyBestEffort.EMPTY) {
            return;
        }
        synchronized (this) {
            SinkManyBestEffort.DirectInner<T>[] directInnerArr2 = this.subscribers;
            if (directInnerArr2 != SinkManyBestEffort.TERMINATED && directInnerArr2 != SinkManyBestEffort.EMPTY) {
                int length = directInnerArr2.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        i = -1;
                        break;
                    } else if (directInnerArr2[i] == directInner) {
                        break;
                    } else {
                        i++;
                    }
                }
                if (i < 0) {
                    return;
                }
                if (length == 1) {
                    this.subscribers = SinkManyBestEffort.EMPTY;
                    return;
                }
                SinkManyBestEffort.DirectInner<T>[] directInnerArr3 = new SinkManyBestEffort.DirectInner[length - 1];
                System.arraycopy(directInnerArr2, 0, directInnerArr3, 0, i);
                System.arraycopy(directInnerArr2, i + 1, directInnerArr3, i, (length - i) - 1);
                this.subscribers = directInnerArr3;
            }
        }
    }

    @Override // reactor.core.publisher.FluxProcessor
    public boolean hasDownstreams() {
        SinkManyBestEffort.DirectInner<T>[] directInnerArr = this.subscribers;
        return (directInnerArr == SinkManyBestEffort.EMPTY || directInnerArr == SinkManyBestEffort.TERMINATED) ? false : true;
    }

    @Override // reactor.core.publisher.FluxProcessor
    @Nullable
    public Throwable getError() {
        if (this.subscribers == SinkManyBestEffort.TERMINATED) {
            return this.error;
        }
        return null;
    }
}
