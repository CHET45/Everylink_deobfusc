package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnErrorResume;

/* JADX INFO: loaded from: classes5.dex */
final class MonoOnErrorResume<T> extends InternalMonoOperator<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory;

    MonoOnErrorResume(Mono<? extends T> mono, Function<? super Throwable, ? extends Mono<? extends T>> function) {
        super(mono);
        this.nextFactory = (Function) Objects.requireNonNull(function, "nextFactory");
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxOnErrorResume.ResumeSubscriber(coreSubscriber, this.nextFactory);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
