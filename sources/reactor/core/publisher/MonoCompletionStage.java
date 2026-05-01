package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoCompletionStage<T> extends Mono<T> implements Scannable {
    final CompletionStage<? extends T> future;
    final boolean suppressCancellation;

    MonoCompletionStage(CompletionStage<? extends T> completionStage, boolean z) {
        this.future = (CompletionStage) Objects.requireNonNull(completionStage, "future");
        this.suppressCancellation = z;
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        CoreSubscriber coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(new MonoCompletionStageSubscription(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled, this.future, this.suppressCancellation));
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    static class MonoCompletionStageSubscription<T> implements InnerProducer<T>, BiFunction<T, Throwable, Void> {
        static final AtomicIntegerFieldUpdater<MonoCompletionStageSubscription> REQUESTED_ONCE = AtomicIntegerFieldUpdater.newUpdater(MonoCompletionStageSubscription.class, "requestedOnce");
        final CoreSubscriber<? super T> actual;
        volatile boolean cancelled;
        final CompletionStage<? extends T> future;
        volatile int requestedOnce;
        final boolean suppressCancellation;

        MonoCompletionStageSubscription(CoreSubscriber<? super T> coreSubscriber, CompletionStage<? extends T> completionStage, boolean z) {
            this.actual = coreSubscriber;
            this.future = completionStage;
            this.suppressCancellation = z;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // java.util.function.BiFunction
        public Void apply(@Nullable T t, @Nullable Throwable th) {
            CoreSubscriber<? super T> coreSubscriber = this.actual;
            if (this.cancelled) {
                Context contextCurrentContext = coreSubscriber.currentContext();
                if (th == null || (th instanceof CancellationException)) {
                    Operators.onDiscard(t, contextCurrentContext);
                } else {
                    Operators.onErrorDropped(th, contextCurrentContext);
                    Operators.onDiscard(t, contextCurrentContext);
                }
                return null;
            }
            try {
                if (th instanceof CompletionException) {
                    coreSubscriber.onError(th.getCause());
                } else if (th != null) {
                    coreSubscriber.onError(th);
                } else if (t != null) {
                    coreSubscriber.onNext(t);
                    coreSubscriber.onComplete();
                } else {
                    coreSubscriber.onComplete();
                }
                return null;
            } catch (Throwable th2) {
                Operators.onErrorDropped(th2, coreSubscriber.currentContext());
                throw Exceptions.bubble(th2);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.cancelled || this.requestedOnce == 1 || !REQUESTED_ONCE.compareAndSet(this, 0, 1)) {
                return;
            }
            this.future.handle(this);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.cancelled = true;
            CompletionStage<? extends T> completionStage = this.future;
            if (this.suppressCancellation || !(completionStage instanceof Future)) {
                return;
            }
            try {
                ((Future) completionStage).cancel(true);
            } catch (Throwable th) {
                Operators.onErrorDropped(th, this.actual.currentContext());
            }
        }
    }
}
