package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class FluxErrorOnRequest<T> extends Flux<T> implements SourceProducer<T> {
    final Throwable error;

    FluxErrorOnRequest(Throwable th) {
        this.error = (Throwable) Objects.requireNonNull(th);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        coreSubscriber.onSubscribe(new ErrorSubscription(coreSubscriber, this.error));
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ErrorSubscription implements InnerProducer {
        static final AtomicIntegerFieldUpdater<ErrorSubscription> ONCE = AtomicIntegerFieldUpdater.newUpdater(ErrorSubscription.class, "once");
        final CoreSubscriber<?> actual;
        final Throwable error;
        volatile int once;

        ErrorSubscription(CoreSubscriber<?> coreSubscriber, Throwable th) {
            this.actual = coreSubscriber;
            this.error = th;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (Operators.validate(j) && ONCE.compareAndSet(this, 0, 1)) {
                this.actual.onError(this.error);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.once = 1;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (attr == Scannable.Attr.CANCELLED || attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.once == 1);
            }
            return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }
    }
}
