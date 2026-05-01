package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class SinkManyUnicastNoBackpressure<T> extends Flux<T> implements InternalManySink<T>, Subscription, ContextHolder {
    private volatile CoreSubscriber<? super T> actual = null;
    volatile long requested;
    volatile State state;
    private static final AtomicReferenceFieldUpdater<SinkManyUnicastNoBackpressure, State> STATE = AtomicReferenceFieldUpdater.newUpdater(SinkManyUnicastNoBackpressure.class, State.class, "state");
    static final AtomicLongFieldUpdater<SinkManyUnicastNoBackpressure> REQUESTED = AtomicLongFieldUpdater.newUpdater(SinkManyUnicastNoBackpressure.class, "requested");

    enum State {
        INITIAL,
        SUBSCRIBED,
        TERMINATED,
        CANCELLED
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Flux<T> asFlux() {
        return this;
    }

    public static <E> SinkManyUnicastNoBackpressure<E> create() {
        return new SinkManyUnicastNoBackpressure<>();
    }

    SinkManyUnicastNoBackpressure() {
        STATE.lazySet(this, State.INITIAL);
    }

    @Override // reactor.core.publisher.Sinks.Many
    public int currentSubscriberCount() {
        return this.state == State.SUBSCRIBED ? 1 : 0;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "subscribe");
        CoreSubscriber<? super T> coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        if (!C0162xc40028dd.m5m(STATE, this, State.INITIAL, State.SUBSCRIBED)) {
            Operators.reportThrowInSubscribe(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled, new IllegalStateException("Unicast Sinks.Many allows only a single Subscriber"));
        } else {
            this.actual = coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled;
            coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        if (Operators.validate(j)) {
            Operators.addCap(REQUESTED, this, j);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        if (STATE.getAndSet(this, State.CANCELLED) == State.SUBSCRIBED) {
            this.actual = null;
        }
    }

    @Override // reactor.core.publisher.ContextHolder
    public Context currentContext() {
        CoreSubscriber<? super T> coreSubscriber = this.actual;
        return coreSubscriber != null ? coreSubscriber.currentContext() : Context.empty();
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitNext(T t) {
        Objects.requireNonNull(t, "t");
        int iOrdinal = this.state.ordinal();
        if (iOrdinal == 0) {
            return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        if (iOrdinal != 1) {
            if (iOrdinal == 2) {
                return Sinks.EmitResult.FAIL_TERMINATED;
            }
            if (iOrdinal == 3) {
                return Sinks.EmitResult.FAIL_CANCELLED;
            }
            throw new IllegalStateException();
        }
        if (this.requested == 0) {
            return Sinks.EmitResult.FAIL_OVERFLOW;
        }
        this.actual.onNext(t);
        Operators.produced(REQUESTED, this, 1L);
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitError(Throwable th) {
        State state;
        Objects.requireNonNull(th, "t");
        do {
            state = this.state;
            int iOrdinal = state.ordinal();
            if (iOrdinal == 0) {
                return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
            }
            if (iOrdinal != 1) {
                if (iOrdinal == 2) {
                    return Sinks.EmitResult.FAIL_TERMINATED;
                }
                if (iOrdinal == 3) {
                    return Sinks.EmitResult.FAIL_CANCELLED;
                }
                throw new IllegalStateException();
            }
        } while (!C0162xc40028dd.m5m(STATE, this, state, State.TERMINATED));
        this.actual.onError(th);
        this.actual = null;
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitComplete() {
        State state;
        do {
            state = this.state;
            int iOrdinal = state.ordinal();
            if (iOrdinal == 0) {
                return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
            }
            if (iOrdinal != 1) {
                if (iOrdinal == 2) {
                    return Sinks.EmitResult.FAIL_TERMINATED;
                }
                if (iOrdinal == 3) {
                    return Sinks.EmitResult.FAIL_CANCELLED;
                }
                throw new IllegalStateException();
            }
        } while (!C0162xc40028dd.m5m(STATE, this, state, State.TERMINATED));
        this.actual.onComplete();
        this.actual = null;
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        CoreSubscriber<? super T> coreSubscriber = this.actual;
        return coreSubscriber == null ? Stream.empty() : Stream.of(Scannable.from(coreSubscriber));
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.ACTUAL) {
            return this.actual;
        }
        if (attr == Scannable.Attr.TERMINATED) {
            return Boolean.valueOf(this.state == State.TERMINATED);
        }
        if (attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(this.state == State.CANCELLED);
        }
        return attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
