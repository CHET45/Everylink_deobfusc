package reactor.core.publisher;

import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class BlockingFirstSubscriber<T> extends BlockingSingleSubscriber<T> {
    public BlockingFirstSubscriber(Context context) {
        super(context);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(T t) {
        if (this.value == null) {
            this.value = t;
            dispose();
            countDown();
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        if (this.value == null) {
            this.error = th;
        }
        countDown();
    }

    @Override // reactor.core.Scannable
    public String stepName() {
        return "blockFirst";
    }
}
