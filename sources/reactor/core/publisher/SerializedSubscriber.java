package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class SerializedSubscriber<T> implements InnerOperator<T, T> {
    final CoreSubscriber<? super T> actual;
    volatile boolean cancelled;
    boolean concurrentlyAddedContent;
    volatile boolean done;
    boolean drainLoopInProgress;
    Throwable error;
    LinkedArrayNode<T> head;

    /* JADX INFO: renamed from: s */
    Subscription f2293s;
    LinkedArrayNode<T> tail;

    SerializedSubscriber(CoreSubscriber<? super T> coreSubscriber) {
        this.actual = coreSubscriber;
    }

    @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        if (Operators.validate(this.f2293s, subscription)) {
            this.f2293s = subscription;
            this.actual.onSubscribe(this);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.cancelled) {
            Operators.onDiscard(t, this.actual.currentContext());
            return;
        }
        if (this.done) {
            Operators.onNextDropped(t, this.actual.currentContext());
            return;
        }
        synchronized (this) {
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (this.drainLoopInProgress) {
                serAdd(t);
                this.concurrentlyAddedContent = true;
            } else {
                this.drainLoopInProgress = true;
                this.actual.onNext(t);
                serDrainLoop(this.actual);
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.cancelled || this.done) {
            return;
        }
        synchronized (this) {
            if (!this.cancelled && !this.done) {
                this.done = true;
                this.error = th;
                if (this.drainLoopInProgress) {
                    this.concurrentlyAddedContent = true;
                } else {
                    this.actual.onError(th);
                }
            }
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        if (this.cancelled || this.done) {
            return;
        }
        synchronized (this) {
            if (!this.cancelled && !this.done) {
                this.done = true;
                if (this.drainLoopInProgress) {
                    this.concurrentlyAddedContent = true;
                } else {
                    this.actual.onComplete();
                }
            }
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        this.f2293s.request(j);
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.cancelled = true;
        this.f2293s.cancel();
    }

    void serAdd(T t) {
        if (this.cancelled) {
            Operators.onDiscard(t, this.actual.currentContext());
            return;
        }
        LinkedArrayNode<T> linkedArrayNode = this.tail;
        if (linkedArrayNode == null) {
            LinkedArrayNode<T> linkedArrayNode2 = new LinkedArrayNode<>(t);
            this.head = linkedArrayNode2;
            this.tail = linkedArrayNode2;
        } else if (linkedArrayNode.count == 16) {
            LinkedArrayNode<T> linkedArrayNode3 = new LinkedArrayNode<>(t);
            linkedArrayNode.next = linkedArrayNode3;
            this.tail = linkedArrayNode3;
        } else {
            T[] tArr = linkedArrayNode.array;
            int i = linkedArrayNode.count;
            linkedArrayNode.count = i + 1;
            tArr[i] = t;
        }
        if (this.cancelled) {
            Operators.onDiscard(t, this.actual.currentContext());
        }
    }

    void serDrainLoop(CoreSubscriber<? super T> coreSubscriber) {
        while (!this.cancelled) {
            synchronized (this) {
                if (this.cancelled) {
                    discardMultiple(this.head);
                    return;
                }
                if (!this.concurrentlyAddedContent) {
                    this.drainLoopInProgress = false;
                    return;
                }
                this.concurrentlyAddedContent = false;
                boolean z = this.done;
                Throwable th = this.error;
                this.head = null;
                this.tail = null;
                for (LinkedArrayNode<T> linkedArrayNode = this.head; linkedArrayNode != null; linkedArrayNode = linkedArrayNode.next) {
                    T[] tArr = linkedArrayNode.array;
                    int i = linkedArrayNode.count;
                    for (int i2 = 0; i2 < i; i2++) {
                        if (this.cancelled) {
                            synchronized (this) {
                                discardMultiple(linkedArrayNode);
                            }
                            return;
                        }
                        coreSubscriber.onNext(tArr[i2]);
                    }
                }
                if (this.cancelled) {
                    synchronized (this) {
                        discardMultiple(this.head);
                    }
                    return;
                } else if (th != null) {
                    coreSubscriber.onError(th);
                    return;
                } else if (z) {
                    coreSubscriber.onComplete();
                    return;
                }
            }
        }
        synchronized (this) {
            discardMultiple(this.head);
        }
    }

    private void discardMultiple(LinkedArrayNode<T> linkedArrayNode) {
        LinkedArrayNode<T> linkedArrayNode2;
        while (true) {
            LinkedArrayNode<T> linkedArrayNode3 = linkedArrayNode;
            while (linkedArrayNode != null) {
                for (int i = 0; i < linkedArrayNode.count; i++) {
                    Operators.onDiscard(linkedArrayNode.array[i], this.actual.currentContext());
                }
                linkedArrayNode = linkedArrayNode.next;
                if (linkedArrayNode != null || (linkedArrayNode2 = this.head) == linkedArrayNode3) {
                }
            }
            return;
            linkedArrayNode = linkedArrayNode2;
        }
    }

    @Override // reactor.core.publisher.InnerProducer
    public CoreSubscriber<? super T> actual() {
        return this.actual;
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.f2293s;
        }
        if (attr == Scannable.Attr.ERROR) {
            return this.error;
        }
        if (attr == Scannable.Attr.BUFFERED) {
            return Integer.valueOf(producerCapacity());
        }
        if (attr == Scannable.Attr.CAPACITY) {
            return 16;
        }
        return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(this.done) : super.scanUnsafe(attr);
    }

    int producerCapacity() {
        LinkedArrayNode<T> linkedArrayNode = this.tail;
        if (linkedArrayNode != null) {
            return linkedArrayNode.count;
        }
        return 0;
    }

    static final class LinkedArrayNode<T> {
        static final int DEFAULT_CAPACITY = 16;
        final T[] array;
        int count;
        LinkedArrayNode<T> next;

        LinkedArrayNode(T t) {
            T[] tArr = (T[]) new Object[16];
            this.array = tArr;
            tArr[0] = t;
            this.count = 1;
        }
    }
}
