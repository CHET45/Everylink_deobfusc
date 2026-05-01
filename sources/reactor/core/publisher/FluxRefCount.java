package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxRefCount<T> extends Flux<T> implements Scannable, Fuseable {

    @Nullable
    RefCountMonitor<T> connection;

    /* JADX INFO: renamed from: n */
    final int f2176n;
    final ConnectableFlux<? extends T> source;

    FluxRefCount(ConnectableFlux<? extends T> connectableFlux, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("n > 0 required but it was " + i);
        }
        this.source = ConnectableFlux.from((ConnectableFlux) Objects.requireNonNull(connectableFlux, "source"));
        this.f2176n = i;
    }

    @Override // reactor.core.publisher.Flux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        RefCountMonitor<T> refCountMonitor;
        boolean z;
        RefCountInner refCountInner = new RefCountInner(coreSubscriber);
        this.source.subscribe((CoreSubscriber<? super Object>) refCountInner);
        synchronized (this) {
            refCountMonitor = this.connection;
            if (refCountMonitor == null || refCountMonitor.terminated) {
                refCountMonitor = new RefCountMonitor<>(this);
                this.connection = refCountMonitor;
            }
            long j = refCountMonitor.subscribers + 1;
            refCountMonitor.subscribers = j;
            if (refCountMonitor.connected || j != this.f2176n) {
                z = false;
            } else {
                z = true;
                refCountMonitor.connected = true;
            }
        }
        refCountInner.setRefCountMonitor(refCountMonitor);
        if (z) {
            this.source.connect(refCountMonitor);
        }
    }

    void cancel(RefCountMonitor refCountMonitor) {
        synchronized (this) {
            if (refCountMonitor.terminated) {
                return;
            }
            long j = refCountMonitor.subscribers - 1;
            refCountMonitor.subscribers = j;
            if (j == 0 && refCountMonitor.connected) {
                Disposable disposable = null;
                if (refCountMonitor == this.connection) {
                    Disposable andSet = RefCountMonitor.DISCONNECT.getAndSet(refCountMonitor, Disposables.disposed());
                    this.connection = null;
                    disposable = andSet;
                }
                if (disposable != null) {
                    disposable.dispose();
                }
            }
        }
    }

    void terminated(RefCountMonitor refCountMonitor) {
        synchronized (this) {
            if (!refCountMonitor.terminated) {
                refCountMonitor.terminated = true;
                this.connection = null;
            }
        }
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static final class RefCountMonitor<T> implements Consumer<Disposable> {
        static final AtomicReferenceFieldUpdater<RefCountMonitor, Disposable> DISCONNECT = AtomicReferenceFieldUpdater.newUpdater(RefCountMonitor.class, Disposable.class, "disconnect");
        boolean connected;
        volatile Disposable disconnect;
        final FluxRefCount<? extends T> parent;
        long subscribers;
        boolean terminated;

        RefCountMonitor(FluxRefCount<? extends T> fluxRefCount) {
            this.parent = fluxRefCount;
        }

        @Override // java.util.function.Consumer
        public void accept(Disposable disposable) {
            OperatorDisposables.replace(DISCONNECT, this, disposable);
        }

        void innerCancelled() {
            this.parent.cancel(this);
        }

        void upstreamFinished() {
            this.parent.terminated(this);
        }
    }

    static final class RefCountInner<T> implements Fuseable.QueueSubscription<T>, InnerOperator<T, T> {
        static final int CANCELLED_FLAG = Integer.MIN_VALUE;
        static final int MONITOR_SET_FLAG = 536870912;
        static final AtomicIntegerFieldUpdater<RefCountInner> STATE = AtomicIntegerFieldUpdater.newUpdater(RefCountInner.class, "state");
        static final int TERMINATED_FLAG = 1073741824;
        final CoreSubscriber<? super T> actual;
        RefCountMonitor<T> connection;
        Throwable error;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2177qs;

        /* JADX INFO: renamed from: s */
        Subscription f2178s;
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

        RefCountInner(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2178s : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isTerminated(this.state)) : attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(isCancelled(this.state)) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2178s, subscription)) {
                this.f2178s = subscription;
            }
        }

        void setRefCountMonitor(RefCountMonitor<T> refCountMonitor) {
            int i;
            this.connection = refCountMonitor;
            this.actual.onSubscribe(this);
            do {
                i = this.state;
                if (isCancelled(i)) {
                    return;
                }
                if (isTerminated(i)) {
                    refCountMonitor.upstreamFinished();
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
                this.connection.upstreamFinished();
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
                this.connection.upstreamFinished();
                this.actual.onComplete();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2178s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2178s.cancel();
            int i = this.state;
            if (isTerminated(i) || isCancelled(i) || !STATE.compareAndSet(this, i, Integer.MIN_VALUE | i)) {
                return;
            }
            this.connection.innerCancelled();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            Subscription subscription = this.f2178s;
            if (!(subscription instanceof Fuseable.QueueSubscription)) {
                return 0;
            }
            Fuseable.QueueSubscription<T> queueSubscription = (Fuseable.QueueSubscription) subscription;
            this.f2177qs = queueSubscription;
            return queueSubscription.requestFusion(i);
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            return this.f2177qs.poll();
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2177qs.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2177qs.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2177qs.clear();
        }
    }
}
