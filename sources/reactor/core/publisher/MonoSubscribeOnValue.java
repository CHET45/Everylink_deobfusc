package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxSubscribeOnValue;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoSubscribeOnValue<T> extends Mono<T> implements Scannable {
    final Scheduler scheduler;
    final T value;

    MonoSubscribeOnValue(@Nullable T t, Scheduler scheduler) {
        this.value = t;
        this.scheduler = (Scheduler) Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        T t = this.value;
        if (t == null) {
            FluxSubscribeOnValue.ScheduledEmpty scheduledEmpty = new FluxSubscribeOnValue.ScheduledEmpty(coreSubscriber);
            coreSubscriber.onSubscribe(scheduledEmpty);
            try {
                scheduledEmpty.setFuture(this.scheduler.schedule(scheduledEmpty));
                return;
            } catch (RejectedExecutionException e) {
                if (scheduledEmpty.future != OperatorDisposables.DISPOSED) {
                    coreSubscriber.onError(Operators.onRejectedExecution(e, coreSubscriber.currentContext()));
                    return;
                }
                return;
            }
        }
        coreSubscriber.onSubscribe(new FluxSubscribeOnValue.ScheduledScalar(coreSubscriber, t, this.scheduler));
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.ASYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }
}
