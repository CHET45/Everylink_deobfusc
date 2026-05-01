package reactor.core.publisher;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class BlockingIterable<T> implements Iterable<T>, Scannable {
    final int batchSize;
    final Supplier<Context> contextSupplier;
    final Supplier<Queue<T>> queueSupplier;
    final CorePublisher<? extends T> source;

    BlockingIterable(CorePublisher<? extends T> corePublisher, int i, Supplier<Queue<T>> supplier, Supplier<Context> supplier2) {
        this.contextSupplier = supplier2;
        if (i <= 0) {
            throw new IllegalArgumentException("batchSize > 0 required but it was " + i);
        }
        this.source = (CorePublisher) Objects.requireNonNull(corePublisher, "source");
        this.batchSize = i;
        this.queueSupplier = (Supplier) Objects.requireNonNull(supplier, "queueSupplier");
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.valueOf(Math.min(Integer.MAX_VALUE, this.batchSize));
        }
        if (attr == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        SubscriberIterator<T> subscriberIteratorCreateIterator = createIterator();
        this.source.subscribe((CoreSubscriber<? super Object>) subscriberIteratorCreateIterator);
        return subscriberIteratorCreateIterator;
    }

    @Override // java.lang.Iterable
    public Spliterator<T> spliterator() {
        return stream().spliterator();
    }

    public Stream<T> stream() {
        SubscriberIterator<T> subscriberIteratorCreateIterator = createIterator();
        this.source.subscribe((CoreSubscriber<? super Object>) subscriberIteratorCreateIterator);
        return (Stream) StreamSupport.stream(Spliterators.spliteratorUnknownSize(subscriberIteratorCreateIterator, 0), false).onClose(subscriberIteratorCreateIterator);
    }

    SubscriberIterator<T> createIterator() {
        try {
            return new SubscriberIterator<>((Queue) Objects.requireNonNull(this.queueSupplier.get(), "The queueSupplier returned a null queue"), this.contextSupplier.get(), this.batchSize);
        } catch (Throwable th) {
            throw Exceptions.propagate(th);
        }
    }

    static final class SubscriberIterator<T> implements InnerConsumer<T>, Iterator<T>, Runnable {

        /* JADX INFO: renamed from: S */
        static final AtomicReferenceFieldUpdater<SubscriberIterator, Subscription> f2078S = AtomicReferenceFieldUpdater.newUpdater(SubscriberIterator.class, Subscription.class, "s");
        final int batchSize;
        final Condition condition;
        final Context context;
        volatile boolean done;
        Throwable error;
        final int limit;
        final Lock lock;
        long produced;
        final Queue<T> queue;

        /* JADX INFO: renamed from: s */
        volatile Subscription f2079s;

        SubscriberIterator(Queue<T> queue, Context context, int i) {
            this.queue = queue;
            this.batchSize = i;
            this.limit = Operators.unboundedOrLimit(i);
            ReentrantLock reentrantLock = new ReentrantLock();
            this.lock = reentrantLock;
            this.condition = reentrantLock.newCondition();
            this.context = context;
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.context;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (Schedulers.isInNonBlockingThread()) {
                throw new IllegalStateException("Iterating over a toIterable() / toStream() is blocking, which is not supported in thread " + Thread.currentThread().getName());
            }
            while (true) {
                boolean z = this.done;
                boolean zIsEmpty = this.queue.isEmpty();
                if (z) {
                    Throwable th = this.error;
                    if (th != null) {
                        throw Exceptions.propagate(th);
                    }
                    if (zIsEmpty) {
                        return false;
                    }
                }
                if (!zIsEmpty) {
                    return true;
                }
                this.lock.lock();
                while (!this.done && this.queue.isEmpty()) {
                    try {
                        try {
                            this.condition.await();
                        } catch (InterruptedException e) {
                            run();
                            Thread.currentThread().interrupt();
                            throw Exceptions.propagate(e);
                        }
                    } finally {
                        this.lock.unlock();
                    }
                }
            }
        }

        @Override // java.util.Iterator
        public T next() {
            if (hasNext()) {
                T tPoll = this.queue.poll();
                if (tPoll == null) {
                    run();
                    throw new IllegalStateException("Queue is empty: Expected one element to be available from the Reactive Streams source.");
                }
                long j = this.produced + 1;
                if (j == this.limit) {
                    this.produced = 0L;
                    this.f2079s.request(j);
                } else {
                    this.produced = j;
                }
                return tPoll;
            }
            throw new NoSuchElementException();
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.setOnce(f2078S, this, subscription)) {
                subscription.request(Operators.unboundedOrPrefetch(this.batchSize));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                Operators.terminate(f2078S, this);
                onError(Operators.onOperatorError(null, Exceptions.failWithOverflow(Exceptions.BACKPRESSURE_ERROR_QUEUE_FULL), t, currentContext()));
            } else {
                signalConsumer();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.error = th;
            this.done = true;
            signalConsumer();
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.done = true;
            signalConsumer();
        }

        void signalConsumer() {
            this.lock.lock();
            try {
                this.condition.signalAll();
            } finally {
                this.lock.unlock();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Operators.terminate(f2078S, this);
            signalConsumer();
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.done);
            }
            if (attr == Scannable.Attr.PARENT) {
                return this.f2079s;
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.f2079s == Operators.cancelledSubscription());
            }
            if (attr == Scannable.Attr.PREFETCH) {
                return Integer.valueOf(this.batchSize);
            }
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}
