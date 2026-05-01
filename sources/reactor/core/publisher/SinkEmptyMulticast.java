package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
class SinkEmptyMulticast<T> extends Mono<T> implements InternalEmptySink<T> {
    static final int STATE_ADDED = 0;
    static final int STATE_EMPTY = -2;
    static final int STATE_ERROR = -1;

    @Nullable
    Throwable error;
    volatile Inner<T>[] subscribers;
    static final AtomicReferenceFieldUpdater<SinkEmptyMulticast, Inner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkEmptyMulticast.class, Inner[].class, "subscribers");
    static final Inner[] EMPTY = new Inner[0];
    static final Inner[] TERMINATED_EMPTY = new Inner[0];
    static final Inner[] TERMINATED_ERROR = new Inner[0];

    interface Inner<T> extends InnerProducer<T> {
        void complete();

        void complete(T t);

        void error(Throwable th);

        boolean isCancelled();
    }

    @Override // reactor.core.publisher.Sinks.Empty
    public Mono<T> asMono() {
        return this;
    }

    SinkEmptyMulticast() {
        SUBSCRIBERS.lazySet(this, EMPTY);
    }

    @Override // reactor.core.publisher.Sinks.Empty
    public int currentSubscriberCount() {
        return this.subscribers.length;
    }

    boolean isTerminated(Inner<?>[] innerArr) {
        return innerArr == TERMINATED_EMPTY || innerArr == TERMINATED_ERROR;
    }

    @Override // reactor.core.publisher.Sinks.Empty
    public Sinks.EmitResult tryEmitEmpty() {
        Inner<T>[] innerArr;
        do {
            innerArr = this.subscribers;
            if (isTerminated(innerArr)) {
                return Sinks.EmitResult.FAIL_TERMINATED;
            }
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, innerArr, TERMINATED_EMPTY));
        for (Inner<T> inner : innerArr) {
            inner.complete();
        }
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.publisher.Sinks.Empty
    public Sinks.EmitResult tryEmitError(Throwable th) {
        Objects.requireNonNull(th, "onError cannot be null");
        Inner<T>[] innerArr = this.subscribers;
        if (isTerminated(innerArr)) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = th;
        while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, innerArr, TERMINATED_ERROR)) {
            innerArr = this.subscribers;
            if (isTerminated(innerArr)) {
                return Sinks.EmitResult.FAIL_TERMINATED;
            }
        }
        for (Inner<T> inner : innerArr) {
            inner.error(th);
        }
        return Sinks.EmitResult.OK;
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED) {
            return Boolean.valueOf(isTerminated(this.subscribers));
        }
        if (attr != Scannable.Attr.ERROR) {
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
        }
        if (this.subscribers == TERMINATED_ERROR) {
            return this.error;
        }
        return null;
    }

    @Override // reactor.core.publisher.ContextHolder
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    int add(Inner<T> inner) {
        Inner<T>[] innerArr;
        Inner[] innerArr2;
        do {
            innerArr = this.subscribers;
            if (innerArr == TERMINATED_EMPTY) {
                return -2;
            }
            if (innerArr == TERMINATED_ERROR) {
                return -1;
            }
            int length = innerArr.length;
            innerArr2 = new Inner[length + 1];
            System.arraycopy(innerArr, 0, innerArr2, 0, length);
            innerArr2[length] = inner;
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, innerArr, innerArr2));
        return 0;
    }

    void remove(Inner<T> inner) {
        Inner<T>[] innerArr;
        Inner[] innerArr2;
        do {
            innerArr = this.subscribers;
            int length = innerArr.length;
            if (length == 0) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= length) {
                    i = -1;
                    break;
                } else if (innerArr[i] == inner) {
                    break;
                } else {
                    i++;
                }
            }
            if (i < 0) {
                return;
            }
            if (length == 1) {
                innerArr2 = EMPTY;
            } else {
                Inner[] innerArr3 = new Inner[length - 1];
                System.arraycopy(innerArr, 0, innerArr3, 0, i);
                System.arraycopy(innerArr, i + 1, innerArr3, i, (length - i) - 1);
                innerArr2 = innerArr3;
            }
        } while (!C0162xc40028dd.m5m(SUBSCRIBERS, this, innerArr, innerArr2));
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        CoreSubscriber coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        VoidInner voidInner = new VoidInner(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled, this);
        coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(voidInner);
        int iAdd = add(voidInner);
        if (iAdd == 0) {
            if (voidInner.isCancelled()) {
                remove(voidInner);
            }
        } else if (iAdd == -1) {
            coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onError(this.error);
        } else {
            voidInner.complete();
        }
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.of((Object[]) this.subscribers);
    }

    static final class VoidInner<T> extends AtomicBoolean implements Inner<T> {
        final CoreSubscriber<? super T> actual;
        final SinkEmptyMulticast<T> parent;

        @Override // reactor.core.publisher.SinkEmptyMulticast.Inner
        public void complete(T t) {
        }

        VoidInner(CoreSubscriber<? super T> coreSubscriber, SinkEmptyMulticast<T> sinkEmptyMulticast) {
            this.actual = coreSubscriber;
            this.parent = sinkEmptyMulticast;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (getAndSet(true)) {
                return;
            }
            this.parent.remove(this);
        }

        @Override // reactor.core.publisher.SinkEmptyMulticast.Inner
        public boolean isCancelled() {
            return get();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            Operators.validate(j);
        }

        @Override // reactor.core.publisher.SinkEmptyMulticast.Inner
        public void complete() {
            if (get()) {
                return;
            }
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.SinkEmptyMulticast.Inner
        public void error(Throwable th) {
            if (get()) {
                Operators.onOperatorError(th, this.actual.currentContext());
            } else {
                this.actual.onError(th);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(get());
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }
    }
}
