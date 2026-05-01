package reactor.core.publisher;

import reactor.core.publisher.SinkManyBestEffort;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
interface DirectInnerContainer<T> {
    boolean add(SinkManyBestEffort.DirectInner<T> directInner);

    void remove(SinkManyBestEffort.DirectInner<T> directInner);
}
