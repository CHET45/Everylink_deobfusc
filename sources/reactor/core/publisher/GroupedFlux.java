package reactor.core.publisher;

import reactor.util.annotation.NonNull;

/* JADX INFO: loaded from: classes5.dex */
public abstract class GroupedFlux<K, V> extends Flux<V> {
    @NonNull
    public abstract K key();
}
