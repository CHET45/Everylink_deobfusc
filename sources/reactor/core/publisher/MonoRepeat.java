package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxRepeat;

/* JADX INFO: loaded from: classes5.dex */
final class MonoRepeat<T> extends FluxFromMonoOperator<T, T> {
    final long times;

    MonoRepeat(Mono<? extends T> mono, long j) {
        super(mono);
        if (j <= 0) {
            throw new IllegalArgumentException("times > 0 required");
        }
        this.times = j;
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        FluxRepeat.RepeatSubscriber repeatSubscriber = new FluxRepeat.RepeatSubscriber(this.source, coreSubscriber, this.times + 1);
        coreSubscriber.onSubscribe(repeatSubscriber);
        if (repeatSubscriber.isCancelled()) {
            return null;
        }
        repeatSubscriber.onComplete();
        return null;
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
