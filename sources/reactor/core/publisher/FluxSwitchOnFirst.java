package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxSwitchOnFirst<T, R> extends InternalFluxOperator<T, R> {
    static final int HAS_FIRST_VALUE_RECEIVED_FLAG = 1;
    static final int HAS_FIRST_VALUE_SENT_FLAG = 16;
    static final int HAS_INBOUND_CANCELLED_FLAG = 32;
    static final int HAS_INBOUND_CLOSED_PREMATURELY_FLAG = 64;
    static final int HAS_INBOUND_REQUESTED_ONCE_FLAG = 8;
    static final int HAS_INBOUND_SUBSCRIBED_ONCE_FLAG = 2;
    static final int HAS_INBOUND_SUBSCRIBER_SET_FLAG = 4;
    static final int HAS_INBOUND_TERMINATED_FLAG = 128;
    static final int HAS_OUTBOUND_CANCELLED_FLAG = 512;
    static final int HAS_OUTBOUND_SUBSCRIBED_FLAG = 256;
    static final int HAS_OUTBOUND_TERMINATED_FLAG = 1024;
    final boolean cancelSourceOnComplete;
    final BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer;

    static boolean hasFirstValueReceived(long j) {
        return (j & 1) == 1;
    }

    static boolean hasFirstValueSent(long j) {
        return (j & 16) == 16;
    }

    static boolean hasInboundCancelled(long j) {
        return (j & 32) == 32;
    }

    static boolean hasInboundClosedPrematurely(long j) {
        return (j & 64) == 64;
    }

    static boolean hasInboundRequestedOnce(long j) {
        return (j & 8) == 8;
    }

    static boolean hasInboundSubscribedOnce(long j) {
        return (j & 2) == 2;
    }

    static boolean hasInboundSubscriberSet(long j) {
        return (j & 4) == 4;
    }

    static boolean hasInboundTerminated(long j) {
        return (j & 128) == 128;
    }

    static boolean hasOutboundCancelled(long j) {
        return (j & 512) == 512;
    }

    static boolean hasOutboundSubscribed(long j) {
        return (j & 256) == 256;
    }

    static boolean hasOutboundTerminated(long j) {
        return (j & 1024) == 1024;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return 1;
    }

    FluxSwitchOnFirst(Flux<? extends T> flux, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> biFunction, boolean z) {
        super(flux);
        this.transformer = (BiFunction) Objects.requireNonNull(biFunction, "transformer");
        this.cancelSourceOnComplete = z;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new SwitchOnFirstConditionalMain((Fuseable.ConditionalSubscriber) coreSubscriber, this.transformer, this.cancelSourceOnComplete, null);
        }
        return new SwitchOnFirstMain(coreSubscriber, this.transformer, this.cancelSourceOnComplete);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(attr);
    }

    static <T, R> long markFirstValueReceived(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundCancelled(j) || hasInboundClosedPrematurely(j)) {
                break;
            }
            int i2 = i | 1;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "mfvr", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markInboundSubscribedOnce(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        int i;
        long j;
        int i2;
        do {
            i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundSubscribedOnce(j)) {
                return j;
            }
            i2 = i | 2;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2));
        if (abstractSwitchOnFirstMain.logger != null) {
            abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "miso", i, i2);
        }
        return j;
    }

    static <T, R> long markInboundSubscriberSet(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundCancelled(j) || hasInboundClosedPrematurely(j)) {
                break;
            }
            int i2 = i | 4;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "miss", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markInboundRequestedOnce(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundCancelled(j) || hasInboundClosedPrematurely(j)) {
                break;
            }
            int i2 = i | 8;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "miro", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markFirstValueSent(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundCancelled(j) || hasInboundClosedPrematurely(j)) {
                break;
            }
            int i2 = i | 16;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "mfvs", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markInboundTerminated(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundCancelled(j) || hasInboundClosedPrematurely(j)) {
                break;
            }
            int i2 = i | 128;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "mitd", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markInboundCancelled(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        int i;
        long j;
        int i2;
        do {
            i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundCancelled(j)) {
                return j;
            }
            i2 = i | 32;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2));
        if (abstractSwitchOnFirstMain.logger != null) {
            abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "micd", i, i2);
        }
        return j;
    }

    static <T, R> long markInboundClosedPrematurely(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundTerminated(j) || hasInboundCancelled(j)) {
                break;
            }
            int i2 = i | 64;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "micp", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markInboundCancelledAndOutboundTerminated(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasInboundCancelled(j) || hasOutboundCancelled(j)) {
                break;
            }
            int i2 = i | 1056;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "icot", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markOutboundSubscribed(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        int i;
        long j;
        int i2;
        do {
            i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasOutboundCancelled(j)) {
                return j;
            }
            i2 = i | 256;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2));
        if (abstractSwitchOnFirstMain.logger != null) {
            abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "mosd", i, i2);
        }
        return j;
    }

    static <T, R> long markOutboundTerminated(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasOutboundCancelled(j) || hasOutboundTerminated(j)) {
                break;
            }
            int i2 = i | 1024;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "motd", i, i2);
                }
            }
        }
        return j;
    }

    static <T, R> long markOutboundCancelled(AbstractSwitchOnFirstMain<T, R> abstractSwitchOnFirstMain) {
        long j;
        while (true) {
            int i = abstractSwitchOnFirstMain.state;
            j = i;
            if (hasOutboundTerminated(j) || hasOutboundCancelled(j)) {
                break;
            }
            int i2 = i | 512;
            if (AbstractSwitchOnFirstMain.STATE.compareAndSet(abstractSwitchOnFirstMain, i, i2)) {
                if (abstractSwitchOnFirstMain.logger != null) {
                    abstractSwitchOnFirstMain.logger.log(abstractSwitchOnFirstMain.toString(), "mocd", i, i2);
                }
            }
        }
        return j;
    }

    static abstract class AbstractSwitchOnFirstMain<T, R> extends Flux<T> implements InnerOperator<T, R> {
        static final AtomicIntegerFieldUpdater<AbstractSwitchOnFirstMain> STATE = AtomicIntegerFieldUpdater.newUpdater(AbstractSwitchOnFirstMain.class, "state");
        boolean done;
        T firstValue;
        CoreSubscriber<? super T> inboundSubscriber;
        boolean isFirstOnNextReceivedOnce;
        boolean isInboundRequestedOnce;

        @Nullable
        final StateLogger logger;
        final SwitchOnFirstControlSubscriber<? super R> outboundSubscriber;

        /* JADX INFO: renamed from: s */
        Subscription f2203s;
        volatile int state;
        Throwable throwable;
        final BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer;

        abstract CoreSubscriber<? super T> convert(CoreSubscriber<? super T> coreSubscriber);

        abstract boolean tryDirectSend(CoreSubscriber<? super T> coreSubscriber, T t);

        AbstractSwitchOnFirstMain(CoreSubscriber<? super R> coreSubscriber, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> biFunction, boolean z, @Nullable StateLogger stateLogger) {
            SwitchOnFirstControlSubscriber<? super R> switchOnFirstControlSubscriber;
            if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                switchOnFirstControlSubscriber = new SwitchOnFirstConditionalControlSubscriber<>(this, (Fuseable.ConditionalSubscriber) coreSubscriber, z);
            } else {
                switchOnFirstControlSubscriber = new SwitchOnFirstControlSubscriber<>(this, coreSubscriber, z);
            }
            this.outboundSubscriber = switchOnFirstControlSubscriber;
            this.transformer = biFunction;
            this.logger = stateLogger;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public final Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(FluxSwitchOnFirst.hasInboundCancelled((long) this.state) || FluxSwitchOnFirst.hasInboundClosedPrematurely((long) this.state));
            }
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(FluxSwitchOnFirst.hasInboundTerminated((long) this.state) || FluxSwitchOnFirst.hasInboundClosedPrematurely((long) this.state));
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super R> actual() {
            return this.outboundSubscriber;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2203s, subscription)) {
                this.f2203s = subscription;
                this.outboundSubscriber.sendSubscription();
                if (FluxSwitchOnFirst.hasInboundCancelled(this.state)) {
                    return;
                }
                subscription.request(1L);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, currentContext());
                return;
            }
            if (!this.isFirstOnNextReceivedOnce) {
                this.isFirstOnNextReceivedOnce = true;
                this.firstValue = t;
                long jMarkFirstValueReceived = FluxSwitchOnFirst.markFirstValueReceived(this);
                if (FluxSwitchOnFirst.hasInboundCancelled(jMarkFirstValueReceived) || FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkFirstValueReceived)) {
                    this.firstValue = null;
                    Operators.onDiscard(t, this.outboundSubscriber.currentContext());
                    return;
                }
                SwitchOnFirstControlSubscriber<? super R> switchOnFirstControlSubscriber = this.outboundSubscriber;
                try {
                    Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.transformer.apply(Signal.next(t, switchOnFirstControlSubscriber.currentContext()), this), "The transformer returned a null value")).subscribe((Subscriber) switchOnFirstControlSubscriber);
                    return;
                } catch (Throwable th) {
                    this.done = true;
                    long jMarkInboundCancelledAndOutboundTerminated = FluxSwitchOnFirst.markInboundCancelledAndOutboundTerminated(this);
                    if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundCancelledAndOutboundTerminated) || FluxSwitchOnFirst.hasOutboundCancelled(jMarkInboundCancelledAndOutboundTerminated)) {
                        Operators.onErrorDropped(th, switchOnFirstControlSubscriber.currentContext());
                        return;
                    }
                    this.firstValue = null;
                    Operators.onDiscard(t, switchOnFirstControlSubscriber.currentContext());
                    switchOnFirstControlSubscriber.errorDirectly(Operators.onOperatorError(this.f2203s, th, t, switchOnFirstControlSubscriber.currentContext()));
                    return;
                }
            }
            synchronized (this) {
                this.inboundSubscriber.onNext(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.outboundSubscriber.currentContext());
                return;
            }
            this.done = true;
            this.throwable = th;
            long jMarkInboundTerminated = FluxSwitchOnFirst.markInboundTerminated(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundTerminated) || FluxSwitchOnFirst.hasInboundTerminated(jMarkInboundTerminated) || FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkInboundTerminated)) {
                Operators.onErrorDropped(th, this.outboundSubscriber.currentContext());
                return;
            }
            if (FluxSwitchOnFirst.hasFirstValueSent(jMarkInboundTerminated)) {
                synchronized (this) {
                    this.inboundSubscriber.onError(th);
                }
            } else {
                if (FluxSwitchOnFirst.hasFirstValueReceived(jMarkInboundTerminated)) {
                    return;
                }
                SwitchOnFirstControlSubscriber<? super R> switchOnFirstControlSubscriber = this.outboundSubscriber;
                try {
                    Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.transformer.apply(Signal.error(th, switchOnFirstControlSubscriber.currentContext()), this), "The transformer returned a null value")).subscribe((Subscriber) switchOnFirstControlSubscriber);
                } catch (Throwable th2) {
                    switchOnFirstControlSubscriber.onError(Exceptions.addSuppressed(th, th2));
                }
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long jMarkInboundTerminated = FluxSwitchOnFirst.markInboundTerminated(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundTerminated) || FluxSwitchOnFirst.hasInboundTerminated(jMarkInboundTerminated) || FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkInboundTerminated)) {
                return;
            }
            if (FluxSwitchOnFirst.hasFirstValueSent(jMarkInboundTerminated)) {
                synchronized (this) {
                    this.inboundSubscriber.onComplete();
                }
            } else {
                if (FluxSwitchOnFirst.hasFirstValueReceived(jMarkInboundTerminated)) {
                    return;
                }
                SwitchOnFirstControlSubscriber<? super R> switchOnFirstControlSubscriber = this.outboundSubscriber;
                try {
                    Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.transformer.apply(Signal.complete(switchOnFirstControlSubscriber.currentContext()), this), "The transformer returned a null value")).subscribe((Subscriber) switchOnFirstControlSubscriber);
                } catch (Throwable th) {
                    switchOnFirstControlSubscriber.onError(th);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void cancel() {
            long jMarkInboundCancelled = FluxSwitchOnFirst.markInboundCancelled(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundCancelled) || FluxSwitchOnFirst.hasInboundTerminated(jMarkInboundCancelled) || FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkInboundCancelled)) {
                return;
            }
            this.f2203s.cancel();
            if (!FluxSwitchOnFirst.hasFirstValueReceived(jMarkInboundCancelled) || FluxSwitchOnFirst.hasInboundRequestedOnce(jMarkInboundCancelled)) {
                return;
            }
            T t = this.firstValue;
            this.firstValue = null;
            Operators.onDiscard(t, currentContext());
        }

        final void cancelAndError() {
            long jMarkInboundClosedPrematurely = FluxSwitchOnFirst.markInboundClosedPrematurely(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundClosedPrematurely) || FluxSwitchOnFirst.hasInboundTerminated(jMarkInboundClosedPrematurely)) {
                return;
            }
            this.f2203s.cancel();
            if (FluxSwitchOnFirst.hasFirstValueReceived(jMarkInboundClosedPrematurely) && !FluxSwitchOnFirst.hasFirstValueSent(jMarkInboundClosedPrematurely)) {
                if (FluxSwitchOnFirst.hasInboundRequestedOnce(jMarkInboundClosedPrematurely)) {
                    return;
                }
                T t = this.firstValue;
                this.firstValue = null;
                Operators.onDiscard(t, currentContext());
                if (FluxSwitchOnFirst.hasInboundSubscriberSet(jMarkInboundClosedPrematurely)) {
                    this.inboundSubscriber.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                    return;
                }
                return;
            }
            if (FluxSwitchOnFirst.hasInboundSubscriberSet(jMarkInboundClosedPrematurely)) {
                synchronized (this) {
                    this.inboundSubscriber.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public final void request(long j) {
            if (Operators.validate(j)) {
                if (!this.isInboundRequestedOnce) {
                    this.isInboundRequestedOnce = true;
                    if (this.isFirstOnNextReceivedOnce) {
                        long jMarkInboundRequestedOnce = FluxSwitchOnFirst.markInboundRequestedOnce(this);
                        if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundRequestedOnce) || FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkInboundRequestedOnce)) {
                            return;
                        }
                        T t = this.firstValue;
                        this.firstValue = null;
                        if (sendFirst(t) && j != Long.MAX_VALUE) {
                            long j2 = j - 1;
                            if (j2 > 0) {
                                this.f2203s.request(j2);
                                return;
                            }
                            return;
                        }
                    }
                }
                this.f2203s.request(j);
            }
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public final void subscribe(CoreSubscriber<? super T> coreSubscriber) {
            long jMarkInboundSubscribedOnce = FluxSwitchOnFirst.markInboundSubscribedOnce(this);
            if (FluxSwitchOnFirst.hasInboundSubscribedOnce(jMarkInboundSubscribedOnce)) {
                Operators.error(coreSubscriber, new IllegalStateException("FluxSwitchOnFirst allows only one Subscriber"));
                return;
            }
            if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundSubscribedOnce) || FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkInboundSubscribedOnce)) {
                Operators.error(coreSubscriber, new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                return;
            }
            if (!FluxSwitchOnFirst.hasFirstValueReceived(jMarkInboundSubscribedOnce)) {
                Throwable th = this.throwable;
                if (th != null) {
                    Operators.error(coreSubscriber, th);
                    return;
                } else {
                    Operators.complete(coreSubscriber);
                    return;
                }
            }
            this.inboundSubscriber = convert(coreSubscriber);
            coreSubscriber.onSubscribe(this);
            long jMarkInboundSubscriberSet = FluxSwitchOnFirst.markInboundSubscriberSet(this);
            if (FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkInboundSubscriberSet)) {
                if ((!FluxSwitchOnFirst.hasInboundRequestedOnce(jMarkInboundSubscriberSet) || FluxSwitchOnFirst.hasFirstValueSent(jMarkInboundSubscriberSet)) && !FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundSubscriberSet)) {
                    coreSubscriber.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                }
            }
        }

        final boolean sendFirst(T t) {
            CoreSubscriber<? super T> coreSubscriber = this.inboundSubscriber;
            boolean zTryDirectSend = tryDirectSend(coreSubscriber, t);
            long jMarkFirstValueSent = FluxSwitchOnFirst.markFirstValueSent(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(jMarkFirstValueSent)) {
                return zTryDirectSend;
            }
            if (FluxSwitchOnFirst.hasInboundClosedPrematurely(jMarkFirstValueSent)) {
                coreSubscriber.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                return zTryDirectSend;
            }
            if (FluxSwitchOnFirst.hasInboundTerminated(jMarkFirstValueSent)) {
                Throwable th = this.throwable;
                if (th != null) {
                    coreSubscriber.onError(th);
                } else {
                    coreSubscriber.onComplete();
                }
            }
            return zTryDirectSend;
        }
    }

    static final class SwitchOnFirstMain<T, R> extends AbstractSwitchOnFirstMain<T, R> {
        @Override // reactor.core.publisher.FluxSwitchOnFirst.AbstractSwitchOnFirstMain
        CoreSubscriber<? super T> convert(CoreSubscriber<? super T> coreSubscriber) {
            return coreSubscriber;
        }

        SwitchOnFirstMain(CoreSubscriber<? super R> coreSubscriber, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> biFunction, boolean z) {
            super(coreSubscriber, biFunction, z, null);
        }

        SwitchOnFirstMain(CoreSubscriber<? super R> coreSubscriber, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> biFunction, boolean z, @Nullable StateLogger stateLogger) {
            super(coreSubscriber, biFunction, z, stateLogger);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // reactor.core.publisher.FluxSwitchOnFirst.AbstractSwitchOnFirstMain
        boolean tryDirectSend(CoreSubscriber<? super T> coreSubscriber, T t) {
            coreSubscriber.onNext(t);
            return true;
        }
    }

    static final class SwitchOnFirstConditionalMain<T, R> extends AbstractSwitchOnFirstMain<T, R> implements Fuseable.ConditionalSubscriber<T> {
        SwitchOnFirstConditionalMain(Fuseable.ConditionalSubscriber<? super R> conditionalSubscriber, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> biFunction, boolean z) {
            super(conditionalSubscriber, biFunction, z, null);
        }

        SwitchOnFirstConditionalMain(Fuseable.ConditionalSubscriber<? super R> conditionalSubscriber, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> biFunction, boolean z, @Nullable StateLogger stateLogger) {
            super(conditionalSubscriber, biFunction, z, stateLogger);
        }

        @Override // reactor.core.publisher.FluxSwitchOnFirst.AbstractSwitchOnFirstMain
        CoreSubscriber<? super T> convert(CoreSubscriber<? super T> coreSubscriber) {
            return Operators.toConditionalSubscriber(coreSubscriber);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            boolean zTryOnNext;
            if (this.done) {
                Operators.onNextDropped(t, currentContext());
                return false;
            }
            if (!this.isFirstOnNextReceivedOnce) {
                this.isFirstOnNextReceivedOnce = true;
                this.firstValue = t;
                if (FluxSwitchOnFirst.hasInboundCancelled(FluxSwitchOnFirst.markFirstValueReceived(this))) {
                    this.firstValue = null;
                    Operators.onDiscard(t, this.outboundSubscriber.currentContext());
                    return true;
                }
                SwitchOnFirstControlSubscriber<? super R> switchOnFirstControlSubscriber = this.outboundSubscriber;
                try {
                    Operators.toFluxOrMono((Publisher) Objects.requireNonNull(this.transformer.apply(Signal.next(t, switchOnFirstControlSubscriber.currentContext()), this), "The transformer returned a null value")).subscribe((Subscriber) switchOnFirstControlSubscriber);
                    return true;
                } catch (Throwable th) {
                    this.done = true;
                    long jMarkInboundCancelledAndOutboundTerminated = FluxSwitchOnFirst.markInboundCancelledAndOutboundTerminated(this);
                    if (FluxSwitchOnFirst.hasInboundCancelled(jMarkInboundCancelledAndOutboundTerminated) || FluxSwitchOnFirst.hasOutboundCancelled(jMarkInboundCancelledAndOutboundTerminated)) {
                        Operators.onErrorDropped(th, switchOnFirstControlSubscriber.currentContext());
                        return true;
                    }
                    this.firstValue = null;
                    Operators.onDiscard(t, switchOnFirstControlSubscriber.currentContext());
                    switchOnFirstControlSubscriber.errorDirectly(Operators.onOperatorError(this.f2203s, th, t, switchOnFirstControlSubscriber.currentContext()));
                    return true;
                }
            }
            synchronized (this) {
                zTryOnNext = ((Fuseable.ConditionalSubscriber) this.inboundSubscriber).tryOnNext(t);
            }
            return zTryOnNext;
        }

        @Override // reactor.core.publisher.FluxSwitchOnFirst.AbstractSwitchOnFirstMain
        boolean tryDirectSend(CoreSubscriber<? super T> coreSubscriber, T t) {
            return ((Fuseable.ConditionalSubscriber) coreSubscriber).tryOnNext(t);
        }
    }

    static class SwitchOnFirstControlSubscriber<T> extends Operators.DeferredSubscription implements InnerOperator<T, T>, CoreSubscriber<T> {
        final boolean cancelSourceOnComplete;
        final CoreSubscriber<? super T> delegate;
        boolean done;
        final AbstractSwitchOnFirstMain<?, T> parent;

        SwitchOnFirstControlSubscriber(AbstractSwitchOnFirstMain<?, T> abstractSwitchOnFirstMain, CoreSubscriber<? super T> coreSubscriber, boolean z) {
            this.parent = abstractSwitchOnFirstMain;
            this.delegate = coreSubscriber;
            this.cancelSourceOnComplete = z;
        }

        final void sendSubscription() {
            this.delegate.onSubscribe(this);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public final void onSubscribe(Subscription subscription) {
            if (set(subscription) && FluxSwitchOnFirst.hasOutboundCancelled(FluxSwitchOnFirst.markOutboundSubscribed(this.parent))) {
                subscription.cancel();
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public final CoreSubscriber<? super T> actual() {
            return this.delegate;
        }

        @Override // org.reactivestreams.Subscriber
        public final void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, currentContext());
            } else {
                this.delegate.onNext(t);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public final void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, currentContext());
                return;
            }
            this.done = true;
            AbstractSwitchOnFirstMain<?, T> abstractSwitchOnFirstMain = this.parent;
            long jMarkOutboundTerminated = FluxSwitchOnFirst.markOutboundTerminated(abstractSwitchOnFirstMain);
            if (FluxSwitchOnFirst.hasOutboundCancelled(jMarkOutboundTerminated) || FluxSwitchOnFirst.hasOutboundTerminated(jMarkOutboundTerminated)) {
                Operators.onErrorDropped(th, this.delegate.currentContext());
                return;
            }
            if (!FluxSwitchOnFirst.hasInboundCancelled(jMarkOutboundTerminated) && !FluxSwitchOnFirst.hasInboundTerminated(jMarkOutboundTerminated)) {
                abstractSwitchOnFirstMain.cancelAndError();
            }
            this.delegate.onError(th);
        }

        final void errorDirectly(Throwable th) {
            this.done = true;
            this.delegate.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            AbstractSwitchOnFirstMain<?, T> abstractSwitchOnFirstMain = this.parent;
            long jMarkOutboundTerminated = FluxSwitchOnFirst.markOutboundTerminated(abstractSwitchOnFirstMain);
            if (this.cancelSourceOnComplete && !FluxSwitchOnFirst.hasInboundCancelled(jMarkOutboundTerminated) && !FluxSwitchOnFirst.hasInboundTerminated(jMarkOutboundTerminated)) {
                abstractSwitchOnFirstMain.cancelAndError();
            }
            this.delegate.onComplete();
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, org.reactivestreams.Subscription
        public final void cancel() {
            Operators.DeferredSubscription.REQUESTED.lazySet(this, -2L);
            long jMarkOutboundCancelled = FluxSwitchOnFirst.markOutboundCancelled(this.parent);
            if (FluxSwitchOnFirst.hasOutboundCancelled(jMarkOutboundCancelled) || FluxSwitchOnFirst.hasOutboundTerminated(jMarkOutboundCancelled)) {
                return;
            }
            boolean z = (FluxSwitchOnFirst.hasInboundTerminated(jMarkOutboundCancelled) || FluxSwitchOnFirst.hasInboundCancelled(jMarkOutboundCancelled)) ? false : true;
            if (!FluxSwitchOnFirst.hasOutboundSubscribed(jMarkOutboundCancelled)) {
                if (z) {
                    this.parent.cancel();
                }
            } else {
                this.f2281s.cancel();
                if (z) {
                    this.parent.cancelAndError();
                }
            }
        }

        @Override // reactor.core.publisher.Operators.DeferredSubscription, reactor.core.Scannable
        public final Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.parent : attr == Scannable.Attr.ACTUAL ? this.delegate : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(FluxSwitchOnFirst.hasOutboundCancelled(this.parent.state)) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(FluxSwitchOnFirst.hasOutboundTerminated(this.parent.state)) : super.scanUnsafe(attr);
        }
    }

    static final class SwitchOnFirstConditionalControlSubscriber<T> extends SwitchOnFirstControlSubscriber<T> implements InnerOperator<T, T>, Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> delegate;

        SwitchOnFirstConditionalControlSubscriber(AbstractSwitchOnFirstMain<?, T> abstractSwitchOnFirstMain, Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber, boolean z) {
            super(abstractSwitchOnFirstMain, conditionalSubscriber, z);
            this.delegate = conditionalSubscriber;
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, currentContext());
                return true;
            }
            return this.delegate.tryOnNext(t);
        }
    }
}
