package reactor.core.scheduler;

import java.util.Collection;
import reactor.core.Disposable;

/* JADX INFO: loaded from: classes5.dex */
final class EmptyCompositeDisposable implements Disposable.Composite {
    @Override // reactor.core.Disposable.Composite
    public boolean add(Disposable disposable) {
        return false;
    }

    @Override // reactor.core.Disposable.Composite
    public boolean addAll(Collection<? extends Disposable> collection) {
        return false;
    }

    @Override // reactor.core.Disposable.Composite, reactor.core.Disposable
    public void dispose() {
    }

    @Override // reactor.core.Disposable.Composite, reactor.core.Disposable
    public boolean isDisposed() {
        return false;
    }

    @Override // reactor.core.Disposable.Composite
    public boolean remove(Disposable disposable) {
        return false;
    }

    @Override // reactor.core.Disposable.Composite
    public int size() {
        return 0;
    }

    EmptyCompositeDisposable() {
    }
}
