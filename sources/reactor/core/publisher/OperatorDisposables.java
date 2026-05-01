package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class OperatorDisposables {
    static final Disposable DISPOSED = Disposables.disposed();

    OperatorDisposables() {
    }

    public static <T> boolean set(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t, @Nullable Disposable disposable) {
        Disposable disposable2;
        do {
            disposable2 = atomicReferenceFieldUpdater.get(t);
            if (disposable2 == DISPOSED) {
                if (disposable == null) {
                    return false;
                }
                disposable.dispose();
                return false;
            }
        } while (!C0162xc40028dd.m5m(atomicReferenceFieldUpdater, t, disposable2, disposable));
        if (disposable2 == null) {
            return true;
        }
        disposable2.dispose();
        return true;
    }

    public static <T> boolean setOnce(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t, Disposable disposable, Consumer<RuntimeException> consumer) {
        Objects.requireNonNull(disposable, "newValue is null");
        if (C0162xc40028dd.m5m(atomicReferenceFieldUpdater, t, null, disposable)) {
            return true;
        }
        disposable.dispose();
        if (atomicReferenceFieldUpdater.get(t) == DISPOSED) {
            return false;
        }
        consumer.accept(new IllegalStateException("Disposable already pushed"));
        return false;
    }

    public static <T> boolean replace(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t, @Nullable Disposable disposable) {
        Disposable disposable2;
        do {
            disposable2 = atomicReferenceFieldUpdater.get(t);
            if (disposable2 == DISPOSED) {
                if (disposable == null) {
                    return false;
                }
                disposable.dispose();
                return false;
            }
        } while (!C0162xc40028dd.m5m(atomicReferenceFieldUpdater, t, disposable2, disposable));
        return true;
    }

    public static <T> boolean dispose(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t) {
        Disposable andSet;
        Disposable disposable = atomicReferenceFieldUpdater.get(t);
        Disposable disposable2 = DISPOSED;
        if (disposable == disposable2 || (andSet = atomicReferenceFieldUpdater.getAndSet(t, disposable2)) == disposable2) {
            return false;
        }
        if (andSet == null) {
            return true;
        }
        andSet.dispose();
        return true;
    }

    public static boolean validate(@Nullable Disposable disposable, Disposable disposable2, Consumer<RuntimeException> consumer) {
        if (disposable2 == null) {
            consumer.accept(new NullPointerException("next is null"));
            return false;
        }
        if (disposable == null) {
            return true;
        }
        disposable2.dispose();
        consumer.accept(new IllegalStateException("Disposable already pushed"));
        return false;
    }

    public static <T> boolean trySet(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t, Disposable disposable) {
        if (C0162xc40028dd.m5m(atomicReferenceFieldUpdater, t, null, disposable)) {
            return true;
        }
        if (atomicReferenceFieldUpdater.get(t) != DISPOSED) {
            return false;
        }
        disposable.dispose();
        return false;
    }

    public static boolean isDisposed(Disposable disposable) {
        return disposable == DISPOSED;
    }
}
