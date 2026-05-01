package reactor.core;

import java.util.Collection;
import java.util.function.Supplier;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Disposable {

    public interface Swap extends Disposable, Supplier<Disposable> {
        boolean replace(@Nullable Disposable disposable);

        boolean update(@Nullable Disposable disposable);
    }

    void dispose();

    default boolean isDisposed() {
        return false;
    }

    public interface Composite extends Disposable {
        boolean add(Disposable disposable);

        @Override // reactor.core.Disposable
        void dispose();

        @Override // reactor.core.Disposable
        boolean isDisposed();

        boolean remove(Disposable disposable);

        int size();

        default boolean addAll(Collection<? extends Disposable> collection) {
            boolean zIsDisposed = isDisposed();
            for (Disposable disposable : collection) {
                if (zIsDisposed) {
                    disposable.dispose();
                } else {
                    zIsDisposed = !add(disposable);
                }
            }
            return !zIsDisposed;
        }
    }
}
