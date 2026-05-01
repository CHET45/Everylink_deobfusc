package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxRefCountGrace<T> extends Flux<T> implements Scannable, Fuseable {
    RefConnection connection;
    final Duration gracePeriod;

    /* JADX INFO: renamed from: n */
    final int f2179n;
    final Scheduler scheduler;
    final ConnectableFlux<T> source;

    FluxRefCountGrace(ConnectableFlux<T> connectableFlux, int i, Duration duration, Scheduler scheduler) {
        this.source = ConnectableFlux.from((ConnectableFlux) Objects.requireNonNull(connectableFlux, "source"));
        this.f2179n = i;
        this.gracePeriod = duration;
        this.scheduler = scheduler;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        RefConnection refConnection;
        boolean z;
        RefCountInner refCountInner = new RefCountInner(coreSubscriber, this);
        this.source.subscribe((CoreSubscriber) refCountInner);
        synchronized (this) {
            refConnection = this.connection;
            if (refConnection == null || refConnection.isTerminated()) {
                refConnection = new RefConnection(this);
                this.connection = refConnection;
            }
            long j = refConnection.subscriberCount;
            if (j == 0 && refConnection.timer != null) {
                refConnection.timer.dispose();
            }
            long j2 = j + 1;
            refConnection.subscriberCount = j2;
            if (refConnection.connected || j2 != this.f2179n) {
                z = false;
            } else {
                z = true;
                refConnection.connected = true;
            }
        }
        refCountInner.setRefConnection(refConnection);
        if (z) {
            this.source.connect(refConnection);
        }
    }

    void cancel(RefConnection refConnection) {
        boolean z;
        Disposable.Swap swap;
        synchronized (this) {
            if (refConnection.terminated) {
                return;
            }
            long j = refConnection.subscriberCount - 1;
            refConnection.subscriberCount = j;
            if (j == 0 && refConnection.connected) {
                Disposable andSet = null;
                if (!this.gracePeriod.isZero()) {
                    swap = Disposables.swap();
                    refConnection.timer = swap;
                    z = true;
                } else {
                    z = false;
                    if (refConnection == this.connection) {
                        this.connection = null;
                        andSet = RefConnection.SOURCE_DISCONNECTOR.getAndSet(refConnection, Disposables.disposed());
                        swap = null;
                    } else {
                        swap = null;
                    }
                }
                if (z) {
                    swap.replace(this.scheduler.schedule(refConnection, this.gracePeriod.toNanos(), TimeUnit.NANOSECONDS));
                } else if (andSet != null) {
                    andSet.dispose();
                }
            }
        }
    }

    void terminated(RefConnection refConnection) {
        synchronized (this) {
            if (!refConnection.terminated) {
                refConnection.terminated = true;
                this.connection = null;
            }
        }
    }

    void timeout(RefConnection refConnection) {
        Disposable andSet;
        synchronized (this) {
            andSet = null;
            if (refConnection.subscriberCount == 0 && refConnection == this.connection) {
                this.connection = null;
                andSet = RefConnection.SOURCE_DISCONNECTOR.getAndSet(refConnection, Disposables.disposed());
            }
        }
        if (andSet != null) {
            andSet.dispose();
        }
    }

    static final class RefConnection implements Runnable, Consumer<Disposable> {
        static final AtomicReferenceFieldUpdater<RefConnection, Disposable> SOURCE_DISCONNECTOR = AtomicReferenceFieldUpdater.newUpdater(RefConnection.class, Disposable.class, "sourceDisconnector");
        boolean connected;
        final FluxRefCountGrace<?> parent;
        volatile Disposable sourceDisconnector;
        long subscriberCount;
        boolean terminated;
        Disposable timer;

        RefConnection(FluxRefCountGrace<?> fluxRefCountGrace) {
            this.parent = fluxRefCountGrace;
        }

        boolean isTerminated() {
            Disposable disposable = this.sourceDisconnector;
            return this.terminated || (disposable != null && disposable.isDisposed());
        }

        @Override // java.lang.Runnable
        public void run() {
            this.parent.timeout(this);
        }

        @Override // java.util.function.Consumer
        public void accept(Disposable disposable) {
            OperatorDisposables.replace(SOURCE_DISCONNECTOR, this, disposable);
        }
    }

    static final class RefCountInner<T> implements Fuseable.QueueSubscription<T>, InnerOperator<T, T> {
        static final int CANCELLED_FLAG = Integer.MIN_VALUE;
        static final int MONITOR_SET_FLAG = 536870912;
        static final AtomicIntegerFieldUpdater<RefCountInner> STATE = AtomicIntegerFieldUpdater.newUpdater(RefCountInner.class, "state");
        static final int TERMINATED_FLAG = 1073741824;
        final CoreSubscriber<? super T> actual;
        RefConnection connection;
        Throwable error;
        final FluxRefCountGrace<T> parent;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2180qs;

        /* JADX INFO: renamed from: s */
        Subscription f2181s;
        volatile int state;

        static boolean isCancelled(int i) {
            return (i & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        }

        static boolean isMonitorSet(int i) {
            return (i & MONITOR_SET_FLAG) == MONITOR_SET_FLAG;
        }

        static boolean isTerminated(int i) {
            return (i & 1073741824) == 1073741824;
        }

        RefCountInner(CoreSubscriber<? super T> coreSubscriber, FluxRefCountGrace<T> fluxRefCountGrace) {
            this.actual = coreSubscriber;
            this.parent = fluxRefCountGrace;
        }

        void setRefConnection(RefConnection refConnection) {
            int i;
            this.connection = refConnection;
            this.actual.onSubscribe(this);
            do {
                i = this.state;
                if (isCancelled(i)) {
                    return;
                }
                if (isTerminated(i)) {
                    this.parent.terminated(refConnection);
                    Throwable th = this.error;
                    if (th != null) {
                        this.actual.onError(th);
                        return;
                    } else {
                        this.actual.onComplete();
                        return;
                    }
                }
            } while (!STATE.compareAndSet(this, i, MONITOR_SET_FLAG | i));
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            int i;
            this.error = th;
            do {
                i = this.state;
                if (isTerminated(i) || isCancelled(i)) {
                    Operators.onErrorDropped(th, this.actual.currentContext());
                    return;
                }
            } while (!STATE.compareAndSet(this, i, 1073741824 | i));
            if (isMonitorSet(i)) {
                this.parent.terminated(this.connection);
                this.actual.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            int i;
            do {
                i = this.state;
                if (isTerminated(i) || isCancelled(i)) {
                    return;
                }
            } while (!STATE.compareAndSet(this, i, 1073741824 | i));
            if (isMonitorSet(i)) {
                this.parent.terminated(this.connection);
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2181s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2181s.cancel();
            int i = this.state;
            if (isTerminated(i) || isCancelled(i) || !STATE.compareAndSet(this, i, Integer.MIN_VALUE | i)) {
                return;
            }
            this.parent.cancel(this.connection);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2181s, subscription)) {
                this.f2181s = subscription;
            }
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            Subscription subscription = this.f2181s;
            if (!(subscription instanceof Fuseable.QueueSubscription)) {
                return 0;
            }
            Fuseable.QueueSubscription<T> queueSubscription = (Fuseable.QueueSubscription) subscription;
            this.f2180qs = queueSubscription;
            return queueSubscription.requestFusion(i);
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            return this.f2180qs.poll();
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2180qs.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2180qs.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2180qs.clear();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == Scannable.Attr.PARENT ? this.f2181s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isTerminated(this.state)) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(isCancelled(this.state)) : super.scanUnsafe(attr);
        }
    }
}
