package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxRepeatWhen;

/* JADX INFO: loaded from: classes5.dex */
final class MonoRepeatWhen<T> extends FluxFromMonoOperator<T, T> {
    final Function<? super Flux<Long>, ? extends Publisher<?>> whenSourceFactory;

    MonoRepeatWhen(Mono<? extends T> mono, Function<? super Flux<Long>, ? extends Publisher<?>> function) {
        super(mono);
        this.whenSourceFactory = (Function) Objects.requireNonNull(function, "whenSourceFactory");
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        FluxRepeatWhen.RepeatWhenOtherSubscriber repeatWhenOtherSubscriber = new FluxRepeatWhen.RepeatWhenOtherSubscriber();
        CoreSubscriber coreSubscriberSerialize = Operators.serialize(coreSubscriber);
        FluxRepeatWhen.RepeatWhenMainSubscriber<?> repeatWhenMainSubscriber = new FluxRepeatWhen.RepeatWhenMainSubscriber<>(coreSubscriberSerialize, repeatWhenOtherSubscriber.completionSignal, this.source);
        repeatWhenOtherSubscriber.main = repeatWhenMainSubscriber;
        coreSubscriberSerialize.onSubscribe(repeatWhenMainSubscriber);
        try {
            ((Publisher) Objects.requireNonNull(this.whenSourceFactory.apply(repeatWhenOtherSubscriber), "The whenSourceFactory returned a null Publisher")).subscribe(repeatWhenOtherSubscriber);
            if (repeatWhenMainSubscriber.cancelled) {
                return null;
            }
            return repeatWhenMainSubscriber;
        } catch (Throwable th) {
            coreSubscriber.onError(Operators.onOperatorError(th, coreSubscriber.currentContext()));
            return null;
        }
    }

    @Override // reactor.core.publisher.FluxFromMonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
