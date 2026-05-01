package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxConcatMap;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxConcatMapNoPrefetch<T, R> extends InternalFluxOperator<T, R> {
    final FluxConcatMap.ErrorMode errorMode;
    final Function<? super T, ? extends Publisher<? extends R>> mapper;

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return 0;
    }

    FluxConcatMapNoPrefetch(Flux<? extends T> flux, Function<? super T, ? extends Publisher<? extends R>> function, FluxConcatMap.ErrorMode errorMode) {
        super(flux);
        this.mapper = (Function) Objects.requireNonNull(function, "mapper");
        this.errorMode = errorMode;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, coreSubscriber, this.mapper, false, true)) {
            return null;
        }
        return new FluxConcatMapNoPrefetchSubscriber(coreSubscriber, this.mapper, this.errorMode);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class FluxConcatMapNoPrefetchSubscriber<T, R> implements FluxConcatMap.FluxConcatMapSupport<T, R> {
        final CoreSubscriber<? super R> actual;
        volatile Throwable error;
        final FluxConcatMap.ErrorMode errorMode;
        final FluxConcatMap.ConcatMapInner<R> inner = new FluxConcatMap.ConcatMapInner<>(this);
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        volatile State state;
        Subscription upstream;
        static final AtomicReferenceFieldUpdater<FluxConcatMapNoPrefetchSubscriber, State> STATE = AtomicReferenceFieldUpdater.newUpdater(FluxConcatMapNoPrefetchSubscriber.class, State.class, "state");
        static final AtomicReferenceFieldUpdater<FluxConcatMapNoPrefetchSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FluxConcatMapNoPrefetchSubscriber.class, Throwable.class, "error");

        enum State {
            INITIAL,
            REQUESTED,
            ACTIVE,
            LAST_ACTIVE,
            TERMINATED,
            CANCELLED
        }

        FluxConcatMapNoPrefetchSubscriber(CoreSubscriber<? super R> coreSubscriber, Function<? super T, ? extends Publisher<? extends R>> function, FluxConcatMap.ErrorMode errorMode) {
            this.actual = coreSubscriber;
            this.mapper = function;
            this.errorMode = errorMode;
            STATE.lazySet(this, State.INITIAL);
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.upstream;
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.state == State.TERMINATED);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.state == State.CANCELLED);
            }
            if (attr == Scannable.Attr.DELAY_ERROR) {
                return Boolean.valueOf(this.errorMode != FluxConcatMap.ErrorMode.IMMEDIATE);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.upstream, subscription)) {
                this.upstream = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!C0162xc40028dd.m5m(STATE, this, State.REQUESTED, State.ACTIVE)) {
                int iOrdinal = this.state.ordinal();
                if (iOrdinal == 4) {
                    Operators.onNextDropped(t, currentContext());
                    return;
                } else {
                    if (iOrdinal != 5) {
                        return;
                    }
                    Operators.onDiscard(t, currentContext());
                    return;
                }
            }
            try {
                Publisher<? extends R> publisherApply = this.mapper.apply(t);
                Objects.requireNonNull(publisherApply, "The mapper returned a null Publisher");
                if (publisherApply instanceof Callable) {
                    Object objCall = ((Callable) publisherApply).call();
                    if (objCall == null) {
                        innerComplete();
                        return;
                    } else if (this.inner.isUnbounded()) {
                        this.actual.onNext(objCall);
                        innerComplete();
                        return;
                    } else {
                        this.inner.set(new FluxConcatMap.WeakScalarSubscription(objCall, this.inner));
                        return;
                    }
                }
                Operators.toFluxOrMono(publisherApply).subscribe((Subscriber) this.inner);
            } catch (Throwable th) {
                Context contextCurrentContext = this.actual.currentContext();
                Operators.onDiscard(t, contextCurrentContext);
                if (maybeOnError(Operators.onNextError(t, th, contextCurrentContext), contextCurrentContext, this.upstream)) {
                    return;
                }
                innerComplete();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (maybeOnError(th, currentContext(), this.inner)) {
                return;
            }
            onComplete();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            while (true) {
                State state = this.state;
                int iOrdinal = state.ordinal();
                if (iOrdinal == 0 || iOrdinal == 1) {
                    if (C0162xc40028dd.m5m(STATE, this, state, State.TERMINATED)) {
                        Throwable th = this.error;
                        if (th != null) {
                            this.actual.onError(th);
                            return;
                        } else {
                            this.actual.onComplete();
                            return;
                        }
                    }
                } else if (iOrdinal != 2 || C0162xc40028dd.m5m(STATE, this, state, State.LAST_ACTIVE)) {
                    return;
                }
            }
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public synchronized void innerNext(R r) {
            int iOrdinal = this.state.ordinal();
            if (iOrdinal == 2 || iOrdinal == 3) {
                this.actual.onNext(r);
            } else {
                Operators.onDiscard(r, currentContext());
            }
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerComplete() {
            while (true) {
                State state = this.state;
                int iOrdinal = state.ordinal();
                if (iOrdinal != 2) {
                    if (iOrdinal != 3) {
                        return;
                    }
                    if (C0162xc40028dd.m5m(STATE, this, state, State.TERMINATED)) {
                        Throwable th = this.error;
                        if (th != null) {
                            this.actual.onError(th);
                            return;
                        } else {
                            this.actual.onComplete();
                            return;
                        }
                    }
                } else if (C0162xc40028dd.m5m(STATE, this, state, State.REQUESTED)) {
                    this.upstream.request(1L);
                    return;
                }
            }
        }

        @Override // reactor.core.publisher.FluxConcatMap.FluxConcatMapSupport
        public void innerError(Throwable th) {
            Context contextCurrentContext = currentContext();
            if (maybeOnError(Operators.onNextInnerError(th, contextCurrentContext, null), contextCurrentContext, this.upstream)) {
                return;
            }
            innerComplete();
        }

        private boolean maybeOnError(@Nullable Throwable th, Context context, Subscription subscription) {
            State state;
            if (th == null) {
                return false;
            }
            if (!C0162xc40028dd.m5m(ERROR, this, null, th)) {
                Operators.onErrorDropped(th, context);
            }
            if (this.errorMode == FluxConcatMap.ErrorMode.END) {
                return false;
            }
            do {
                state = this.state;
                int iOrdinal = state.ordinal();
                if (iOrdinal == 4 || iOrdinal == 5) {
                    return true;
                }
            } while (!C0162xc40028dd.m5m(STATE, this, state, State.TERMINATED));
            subscription.cancel();
            synchronized (this) {
                this.actual.onError(this.error);
            }
            return true;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (C0162xc40028dd.m5m(STATE, this, State.INITIAL, State.REQUESTED)) {
                this.upstream.request(1L);
            }
            this.inner.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            int iOrdinal = STATE.getAndSet(this, State.CANCELLED).ordinal();
            if (iOrdinal == 4) {
                this.inner.cancel();
            } else if (iOrdinal != 5) {
                this.inner.cancel();
                this.upstream.cancel();
            }
        }
    }
}
