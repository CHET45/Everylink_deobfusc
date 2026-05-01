package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxElapsed;
import reactor.core.scheduler.Scheduler;
import reactor.util.function.Tuple2;

/* JADX INFO: loaded from: classes5.dex */
final class MonoElapsed<T> extends InternalMonoOperator<T, Tuple2<Long, T>> implements Fuseable {
    final Scheduler scheduler;

    MonoElapsed(Mono<T> mono, Scheduler scheduler) {
        super(mono);
        this.scheduler = scheduler;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Tuple2<Long, T>> coreSubscriber) {
        return new FluxElapsed.ElapsedSubscriber(coreSubscriber, this.scheduler);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
