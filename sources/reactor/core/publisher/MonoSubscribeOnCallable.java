package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxSubscribeOnCallable;
import reactor.core.scheduler.Scheduler;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSubscribeOnCallable<T> extends Mono<T> implements Fuseable, Scannable {
    final Callable<? extends T> callable;
    final Scheduler scheduler;

    MonoSubscribeOnCallable(Callable<? extends T> callable, Scheduler scheduler) {
        this.callable = (Callable) Objects.requireNonNull(callable, "callable");
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        FluxSubscribeOnCallable.CallableSubscribeOnSubscription callableSubscribeOnSubscription = new FluxSubscribeOnCallable.CallableSubscribeOnSubscription(coreSubscriber, this.callable, this.scheduler);
        coreSubscriber.onSubscribe(callableSubscribeOnSubscription);
        try {
            callableSubscribeOnSubscription.setMainFuture(this.scheduler.schedule(callableSubscribeOnSubscription));
        } catch (RejectedExecutionException e) {
            if (callableSubscribeOnSubscription.state != 4) {
                coreSubscriber.onError(Operators.onRejectedExecution(e, coreSubscriber.currentContext()));
            }
        }
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
