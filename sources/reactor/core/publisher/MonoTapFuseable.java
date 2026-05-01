package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.observability.SignalListener;
import reactor.core.observability.SignalListenerFactory;
import reactor.core.publisher.FluxTapFuseable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoTapFuseable<T, STATE> extends InternalMonoOperator<T, T> implements Fuseable {
    final STATE commonTapState;
    final SignalListenerFactory<T, STATE> tapFactory;

    MonoTapFuseable(Mono<? extends T> mono, SignalListenerFactory<T, STATE> signalListenerFactory) {
        super(mono);
        this.tapFactory = signalListenerFactory;
        this.commonTapState = signalListenerFactory.initializePublisherState(mono);
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) throws Throwable {
        try {
            SignalListener<T> signalListenerCreateListener = this.tapFactory.createListener(this.source, coreSubscriber.currentContext().readOnly(), this.commonTapState);
            if (ContextPropagationSupport.isContextPropagationAvailable()) {
                Objects.requireNonNull(coreSubscriber);
                signalListenerCreateListener = ContextPropagation.contextRestoreForTap(signalListenerCreateListener, new FluxHandle$$ExternalSyntheticLambda0(coreSubscriber));
            }
            try {
                signalListenerCreateListener.doFirst();
                try {
                    Context contextAddToContext = signalListenerCreateListener.addToContext(coreSubscriber.currentContext());
                    if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                        return new FluxTapFuseable.TapConditionalFuseableSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, signalListenerCreateListener, contextAddToContext);
                    }
                    return new FluxTapFuseable.TapFuseableSubscriber(coreSubscriber, signalListenerCreateListener, contextAddToContext);
                } catch (Throwable th) {
                    IllegalStateException illegalStateException = new IllegalStateException("Unable to augment tap Context at subscription via addToContext", th);
                    signalListenerCreateListener.handleListenerError(illegalStateException);
                    Operators.error(coreSubscriber, illegalStateException);
                    return null;
                }
            } catch (Throwable th2) {
                signalListenerCreateListener.handleListenerError(th2);
                Operators.error(coreSubscriber, th2);
                return null;
            }
        } catch (Throwable th3) {
            Operators.error(coreSubscriber, th3);
            return null;
        }
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PREFETCH) {
            return -1;
        }
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
