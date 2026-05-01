package reactor.core.publisher;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
abstract class DrainUtils {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    static <T, F> boolean postCompleteRequest(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLongFieldUpdater<F> atomicLongFieldUpdater, F f, BooleanSupplier booleanSupplier) {
        long j2;
        do {
            j2 = atomicLongFieldUpdater.get(f);
        } while (!atomicLongFieldUpdater.compareAndSet(f, j2, (j2 & Long.MIN_VALUE) | Operators.addCap(Long.MAX_VALUE & j2, j)));
        if (j2 != Long.MIN_VALUE) {
            return false;
        }
        postCompleteDrain(j | Long.MIN_VALUE, subscriber, queue, atomicLongFieldUpdater, f, booleanSupplier);
        return true;
    }

    static <T, F> boolean postCompleteDrain(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLongFieldUpdater<F> atomicLongFieldUpdater, F f, BooleanSupplier booleanSupplier) {
        long j2 = j & Long.MIN_VALUE;
        while (true) {
            if (j2 != j) {
                if (booleanSupplier.getAsBoolean()) {
                    return true;
                }
                T tPoll = queue.poll();
                if (tPoll == null) {
                    subscriber.onComplete();
                    return true;
                }
                subscriber.onNext(tPoll);
                j2++;
            } else {
                if (booleanSupplier.getAsBoolean()) {
                    return true;
                }
                if (queue.isEmpty()) {
                    subscriber.onComplete();
                    return true;
                }
                j = atomicLongFieldUpdater.get(f);
                if (j == j2) {
                    long jAddAndGet = atomicLongFieldUpdater.addAndGet(f, -(j2 & Long.MAX_VALUE));
                    if ((Long.MAX_VALUE & jAddAndGet) == 0) {
                        return false;
                    }
                    j = jAddAndGet;
                    j2 = jAddAndGet & Long.MIN_VALUE;
                } else {
                    continue;
                }
            }
        }
    }

    public static <T, F> void postComplete(CoreSubscriber<? super T> coreSubscriber, Queue<T> queue, AtomicLongFieldUpdater<F> atomicLongFieldUpdater, F f, BooleanSupplier booleanSupplier) {
        long j;
        long j2;
        if (queue.isEmpty()) {
            coreSubscriber.onComplete();
            return;
        }
        if (postCompleteDrain(atomicLongFieldUpdater.get(f), coreSubscriber, queue, atomicLongFieldUpdater, f, booleanSupplier)) {
            return;
        }
        do {
            j = atomicLongFieldUpdater.get(f);
            if ((j & Long.MIN_VALUE) != 0) {
                return;
            } else {
                j2 = j | Long.MIN_VALUE;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(f, j, j2));
        if (j != 0) {
            postCompleteDrain(j2, coreSubscriber, queue, atomicLongFieldUpdater, f, booleanSupplier);
        }
    }

    public static <T, F> boolean postCompleteRequestDelayError(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLongFieldUpdater<F> atomicLongFieldUpdater, F f, BooleanSupplier booleanSupplier, Throwable th) {
        long j2;
        do {
            j2 = atomicLongFieldUpdater.get(f);
        } while (!atomicLongFieldUpdater.compareAndSet(f, j2, (j2 & Long.MIN_VALUE) | Operators.addCap(Long.MAX_VALUE & j2, j)));
        if (j2 != Long.MIN_VALUE) {
            return false;
        }
        postCompleteDrainDelayError(j | Long.MIN_VALUE, subscriber, queue, atomicLongFieldUpdater, f, booleanSupplier, th);
        return true;
    }

    static <T, F> boolean postCompleteDrainDelayError(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLongFieldUpdater<F> atomicLongFieldUpdater, F f, BooleanSupplier booleanSupplier, @Nullable Throwable th) {
        long j2 = j & Long.MIN_VALUE;
        while (true) {
            if (j2 != j) {
                if (booleanSupplier.getAsBoolean()) {
                    return true;
                }
                T tPoll = queue.poll();
                if (tPoll == null) {
                    if (th == null) {
                        subscriber.onComplete();
                    } else {
                        subscriber.onError(th);
                    }
                    return true;
                }
                subscriber.onNext(tPoll);
                j2++;
            } else {
                if (booleanSupplier.getAsBoolean()) {
                    return true;
                }
                if (queue.isEmpty()) {
                    if (th == null) {
                        subscriber.onComplete();
                    } else {
                        subscriber.onError(th);
                    }
                    return true;
                }
                j = atomicLongFieldUpdater.get(f);
                if (j == j2) {
                    long jAddAndGet = atomicLongFieldUpdater.addAndGet(f, -(j2 & Long.MAX_VALUE));
                    if ((Long.MAX_VALUE & jAddAndGet) == 0) {
                        return false;
                    }
                    j = jAddAndGet;
                    j2 = jAddAndGet & Long.MIN_VALUE;
                } else {
                    continue;
                }
            }
        }
    }

    public static <T, F> void postCompleteDelayError(CoreSubscriber<? super T> coreSubscriber, Queue<T> queue, AtomicLongFieldUpdater<F> atomicLongFieldUpdater, F f, BooleanSupplier booleanSupplier, @Nullable Throwable th) {
        long j;
        long j2;
        if (queue.isEmpty()) {
            if (th == null) {
                coreSubscriber.onComplete();
                return;
            } else {
                coreSubscriber.onError(th);
                return;
            }
        }
        if (postCompleteDrainDelayError(atomicLongFieldUpdater.get(f), coreSubscriber, queue, atomicLongFieldUpdater, f, booleanSupplier, th)) {
            return;
        }
        do {
            j = atomicLongFieldUpdater.get(f);
            if ((j & Long.MIN_VALUE) != 0) {
                return;
            } else {
                j2 = j | Long.MIN_VALUE;
            }
        } while (!atomicLongFieldUpdater.compareAndSet(f, j, j2));
        if (j != 0) {
            postCompleteDrainDelayError(j2, coreSubscriber, queue, atomicLongFieldUpdater, f, booleanSupplier, th);
        }
    }

    DrainUtils() {
    }
}
