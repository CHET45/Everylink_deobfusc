package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
final class MonoPeekFuseable<T> extends InternalMonoOperator<T, T> implements Fuseable, SignalPeek<T> {
    final Runnable onCancelCall;
    final Consumer<? super T> onNextCall;
    final LongConsumer onRequestCall;
    final Consumer<? super Subscription> onSubscribeCall;

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onAfterTerminateCall() {
        return null;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onCompleteCall() {
        return null;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super Throwable> onErrorCall() {
        return null;
    }

    MonoPeekFuseable(Mono<? extends T> mono, @Nullable Consumer<? super Subscription> consumer, @Nullable Consumer<? super T> consumer2, @Nullable LongConsumer longConsumer, @Nullable Runnable runnable) {
        super(mono);
        this.onSubscribeCall = consumer;
        this.onNextCall = consumer2;
        this.onRequestCall = longConsumer;
        this.onCancelCall = runnable;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
            return new FluxPeekFuseable.PeekFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, this);
        }
        return new FluxPeekFuseable.PeekFuseableSubscriber(coreSubscriber, this);
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super Subscription> onSubscribeCall() {
        return this.onSubscribeCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Consumer<? super T> onNextCall() {
        return this.onNextCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public LongConsumer onRequestCall() {
        return this.onRequestCall;
    }

    @Override // reactor.core.publisher.SignalPeek
    @Nullable
    public Runnable onCancelCall() {
        return this.onCancelCall;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
