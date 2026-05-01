package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxRange extends Flux<Integer> implements Fuseable, SourceProducer<Integer> {
    final long end;
    final long start;

    FluxRange(int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("count >= required but it was " + i2);
        }
        long j = i;
        long j2 = ((long) i2) + j;
        if (j2 - 1 > 2147483647L) {
            throw new IllegalArgumentException("start + count must be less than Integer.MAX_VALUE + 1");
        }
        this.start = j;
        this.end = j2;
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super Integer> coreSubscriber) {
        long j = this.start;
        long j2 = this.end;
        if (j == j2) {
            Operators.complete(coreSubscriber);
            return;
        }
        if (1 + j == j2) {
            coreSubscriber.onSubscribe(Operators.scalarSubscription(coreSubscriber, Integer.valueOf((int) j)));
        } else if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            coreSubscriber.onSubscribe(new RangeSubscriptionConditional((Fuseable.ConditionalSubscriber) coreSubscriber, j, j2));
        } else {
            coreSubscriber.onSubscribe(new RangeSubscription(coreSubscriber, j, j2));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class RangeSubscription implements InnerProducer<Integer>, Fuseable.SynchronousSubscription<Integer> {
        static final AtomicLongFieldUpdater<RangeSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(RangeSubscription.class, "requested");
        final CoreSubscriber<? super Integer> actual;
        volatile boolean cancelled;
        final long end;
        long index;
        volatile long requested;

        RangeSubscription(CoreSubscriber<? super Integer> coreSubscriber, long j, long j2) {
            this.actual = coreSubscriber;
            this.index = j;
            this.end = j2;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Integer> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && Operators.addCap(REQUESTED, this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    fastPath();
                } else {
                    slowPath(j);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        void fastPath() {
            long j = this.end;
            CoreSubscriber<? super Integer> coreSubscriber = this.actual;
            for (long j2 = this.index; j2 != j; j2++) {
                if (this.cancelled) {
                    return;
                }
                coreSubscriber.onNext(Integer.valueOf((int) j2));
            }
            if (this.cancelled) {
                return;
            }
            coreSubscriber.onComplete();
        }

        void slowPath(long j) {
            CoreSubscriber<? super Integer> coreSubscriber = this.actual;
            long j2 = this.end;
            long j3 = this.index;
            do {
                long j4 = 0;
                while (!this.cancelled) {
                    while (j4 != j && j3 != j2) {
                        coreSubscriber.onNext(Integer.valueOf((int) j3));
                        if (this.cancelled) {
                            return;
                        }
                        j4++;
                        j3++;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    if (j3 == j2) {
                        coreSubscriber.onComplete();
                        return;
                    }
                    j = this.requested;
                    if (j == j4) {
                        this.index = j3;
                        j = REQUESTED.addAndGet(this, -j4);
                    }
                }
                return;
            } while (j != 0);
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isEmpty()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // java.util.Queue
        @Nullable
        public Integer poll() {
            long j = this.index;
            if (j == this.end) {
                return null;
            }
            this.index = 1 + j;
            return Integer.valueOf((int) j);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.index == this.end;
        }

        @Override // java.util.Collection
        public void clear() {
            this.index = this.end;
        }

        @Override // java.util.Collection
        public int size() {
            return (int) (this.end - this.index);
        }
    }

    static final class RangeSubscriptionConditional implements InnerProducer<Integer>, Fuseable.SynchronousSubscription<Integer> {
        static final AtomicLongFieldUpdater<RangeSubscriptionConditional> REQUESTED = AtomicLongFieldUpdater.newUpdater(RangeSubscriptionConditional.class, "requested");
        final Fuseable.ConditionalSubscriber<? super Integer> actual;
        volatile boolean cancelled;
        final long end;
        long index;
        volatile long requested;

        RangeSubscriptionConditional(Fuseable.ConditionalSubscriber<? super Integer> conditionalSubscriber, long j, long j2) {
            this.actual = conditionalSubscriber;
            this.index = j;
            this.end = j2;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Integer> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && Operators.addCap(REQUESTED, this, j) == 0) {
                if (j == Long.MAX_VALUE) {
                    fastPath();
                } else {
                    slowPath(j);
                }
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
        }

        void fastPath() {
            long j = this.end;
            Fuseable.ConditionalSubscriber<? super Integer> conditionalSubscriber = this.actual;
            for (long j2 = this.index; j2 != j; j2++) {
                if (this.cancelled) {
                    return;
                }
                conditionalSubscriber.tryOnNext(Integer.valueOf((int) j2));
            }
            if (this.cancelled) {
                return;
            }
            conditionalSubscriber.onComplete();
        }

        void slowPath(long j) {
            Fuseable.ConditionalSubscriber<? super Integer> conditionalSubscriber = this.actual;
            long j2 = this.end;
            long j3 = this.index;
            do {
                long j4 = 0;
                while (!this.cancelled) {
                    while (j4 != j && j3 != j2) {
                        boolean zTryOnNext = conditionalSubscriber.tryOnNext(Integer.valueOf((int) j3));
                        if (this.cancelled) {
                            return;
                        }
                        if (zTryOnNext) {
                            j4++;
                        }
                        j3++;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    if (j3 == j2) {
                        conditionalSubscriber.onComplete();
                        return;
                    }
                    j = this.requested;
                    if (j == j4) {
                        this.index = j3;
                        j = REQUESTED.addAndGet(this, -j4);
                    }
                }
                return;
            } while (j != 0);
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.CANCELLED ? Boolean.valueOf(this.cancelled) : attr == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM ? Long.valueOf(this.requested) : attr == Scannable.Attr.TERMINATED ? Boolean.valueOf(isEmpty()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // java.util.Queue
        @Nullable
        public Integer poll() {
            long j = this.index;
            if (j == this.end) {
                return null;
            }
            this.index = 1 + j;
            return Integer.valueOf((int) j);
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.index == this.end;
        }

        @Override // java.util.Collection
        public void clear() {
            this.index = this.end;
        }

        @Override // java.util.Collection
        public int size() {
            return (int) (this.end - this.index);
        }
    }
}
