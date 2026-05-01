package reactor.core;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public final class Disposables {
    static final Disposable DISPOSED = disposed();

    private Disposables() {
    }

    public static Disposable.Composite composite() {
        return new ListCompositeDisposable();
    }

    public static Disposable.Composite composite(Disposable... disposableArr) {
        return new ListCompositeDisposable(disposableArr);
    }

    public static Disposable.Composite composite(Iterable<? extends Disposable> iterable) {
        return new ListCompositeDisposable(iterable);
    }

    public static Disposable disposed() {
        return new AlwaysDisposable();
    }

    public static Disposable never() {
        return new NeverDisposable();
    }

    public static Disposable single() {
        return new SimpleDisposable();
    }

    public static Disposable.Swap swap() {
        return new SwapDisposable();
    }

    static final class ListCompositeDisposable implements Disposable.Composite, Scannable {
        volatile boolean disposed;

        @Nullable
        List<Disposable> resources;

        ListCompositeDisposable() {
        }

        ListCompositeDisposable(Disposable... disposableArr) {
            Objects.requireNonNull(disposableArr, "resources is null");
            this.resources = new LinkedList();
            for (Disposable disposable : disposableArr) {
                Objects.requireNonNull(disposable, "Disposable item is null");
                this.resources.add(disposable);
            }
        }

        ListCompositeDisposable(Iterable<? extends Disposable> iterable) {
            Objects.requireNonNull(iterable, "resources is null");
            this.resources = new LinkedList();
            for (Disposable disposable : iterable) {
                Objects.requireNonNull(disposable, "Disposable item is null");
                this.resources.add(disposable);
            }
        }

        @Override // reactor.core.Disposable.Composite, reactor.core.Disposable
        public void dispose() {
            if (this.disposed) {
                return;
            }
            synchronized (this) {
                if (this.disposed) {
                    return;
                }
                this.disposed = true;
                List<Disposable> list = this.resources;
                this.resources = null;
                dispose(list);
            }
        }

        @Override // reactor.core.Disposable.Composite, reactor.core.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }

        @Override // reactor.core.Disposable.Composite
        public boolean add(Disposable disposable) {
            Objects.requireNonNull(disposable, "d is null");
            if (!this.disposed) {
                synchronized (this) {
                    if (!this.disposed) {
                        List linkedList = this.resources;
                        if (linkedList == null) {
                            linkedList = new LinkedList();
                            this.resources = linkedList;
                        }
                        linkedList.add(disposable);
                        return true;
                    }
                }
            }
            disposable.dispose();
            return false;
        }

        @Override // reactor.core.Disposable.Composite
        public boolean addAll(Collection<? extends Disposable> collection) {
            Objects.requireNonNull(collection, "ds is null");
            if (!this.disposed) {
                synchronized (this) {
                    if (!this.disposed) {
                        List linkedList = this.resources;
                        if (linkedList == null) {
                            linkedList = new LinkedList();
                            this.resources = linkedList;
                        }
                        for (Disposable disposable : collection) {
                            Objects.requireNonNull(disposable, "d is null");
                            linkedList.add(disposable);
                        }
                        return true;
                    }
                }
            }
            Iterator<? extends Disposable> it = collection.iterator();
            while (it.hasNext()) {
                it.next().dispose();
            }
            return false;
        }

        @Override // reactor.core.Disposable.Composite
        public boolean remove(Disposable disposable) {
            Objects.requireNonNull(disposable, "Disposable item is null");
            if (this.disposed) {
                return false;
            }
            synchronized (this) {
                if (this.disposed) {
                    return false;
                }
                List<Disposable> list = this.resources;
                if (list != null && list.remove(disposable)) {
                    return true;
                }
                return false;
            }
        }

        @Override // reactor.core.Disposable.Composite
        public int size() {
            List<Disposable> list = this.resources;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        Stream<Disposable> asStream() {
            List<Disposable> list = this.resources;
            return list == null ? Stream.empty() : list.stream();
        }

        public void clear() {
            if (this.disposed) {
                return;
            }
            synchronized (this) {
                if (this.disposed) {
                    return;
                }
                List<Disposable> list = this.resources;
                this.resources = null;
                dispose(list);
            }
        }

        void dispose(@Nullable List<Disposable> list) {
            if (list == null) {
                return;
            }
            Iterator<Disposable> it = list.iterator();
            ArrayList arrayList = null;
            while (it.hasNext()) {
                try {
                    it.next().dispose();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(th);
                }
            }
            if (arrayList != null) {
                if (arrayList.size() == 1) {
                    throw Exceptions.propagate((Throwable) arrayList.get(0));
                }
                throw Exceptions.multiple(arrayList);
            }
        }

        @Override // reactor.core.Scannable
        public Stream<? extends Scannable> inners() {
            Stream<Disposable> streamAsStream = asStream();
            final Class<Scannable> cls = Scannable.class;
            Objects.requireNonNull(Scannable.class);
            return streamAsStream.filter(new Predicate() { // from class: reactor.core.Disposables$ListCompositeDisposable$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return cls.isInstance((Disposable) obj);
                }
            }).map(new Function() { // from class: reactor.core.Disposables$ListCompositeDisposable$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Scannable.from((Disposable) obj);
                }
            });
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(isDisposed());
            }
            return null;
        }
    }

    static final class SwapDisposable implements Disposable.Swap {
        static final AtomicReferenceFieldUpdater<SwapDisposable, Disposable> INNER = AtomicReferenceFieldUpdater.newUpdater(SwapDisposable.class, Disposable.class, "inner");
        volatile Disposable inner;

        SwapDisposable() {
        }

        @Override // reactor.core.Disposable.Swap
        public boolean update(@Nullable Disposable disposable) {
            return Disposables.set(INNER, this, disposable);
        }

        @Override // reactor.core.Disposable.Swap
        public boolean replace(@Nullable Disposable disposable) {
            return Disposables.replace(INNER, this, disposable);
        }

        @Override // java.util.function.Supplier
        @Nullable
        public Disposable get() {
            return this.inner;
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            Disposables.dispose(INNER, this);
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return Disposables.isDisposed(INNER.get(this));
        }
    }

    static final class SimpleDisposable extends AtomicBoolean implements Disposable {
        SimpleDisposable() {
        }

        @Override // reactor.core.Disposable
        public void dispose() {
            set(true);
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return get();
        }
    }

    static final class AlwaysDisposable implements Disposable {
        @Override // reactor.core.Disposable
        public void dispose() {
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return true;
        }

        AlwaysDisposable() {
        }
    }

    static final class NeverDisposable implements Disposable {
        @Override // reactor.core.Disposable
        public void dispose() {
        }

        @Override // reactor.core.Disposable
        public boolean isDisposed() {
            return false;
        }

        NeverDisposable() {
        }
    }

    static <T> boolean set(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t, @Nullable Disposable disposable) {
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

    static <T> boolean replace(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t, @Nullable Disposable disposable) {
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

    static <T> boolean dispose(AtomicReferenceFieldUpdater<T, Disposable> atomicReferenceFieldUpdater, T t) {
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

    static boolean isDisposed(Disposable disposable) {
        return disposable == DISPOSED;
    }
}
