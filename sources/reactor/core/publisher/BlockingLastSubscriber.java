package reactor.core.publisher;

import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class BlockingLastSubscriber<T> extends BlockingSingleSubscriber<T> {
    public BlockingLastSubscriber(Context context) {
        super(context);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        this.value = t;
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.value = null;
        this.error = th;
        countDown();
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        return "blockLast";
    }
}
