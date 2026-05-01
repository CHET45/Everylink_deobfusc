package reactor.core.publisher;

import io.micrometer.context.ContextSnapshot;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.observability.SignalListener;
import reactor.core.observability.SignalListenerFactory;
import reactor.core.publisher.FluxTapRestoringThreadLocals;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoTapRestoringThreadLocals<T, STATE> extends MonoOperator<T, T> {
    final STATE commonTapState;
    final SignalListenerFactory<T, STATE> tapFactory;

    MonoTapRestoringThreadLocals(Mono<? extends T> mono, SignalListenerFactory<T, STATE> signalListenerFactory) {
        super(mono);
        this.tapFactory = signalListenerFactory;
        this.commonTapState = signalListenerFactory.initializePublisherState(mono);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Context contextCurrentContext;
        try {
            SignalListener<T> signalListenerCreateListener = this.tapFactory.createListener(this.source, coreSubscriber.currentContext().readOnly(), this.commonTapState);
            try {
                signalListenerCreateListener.doFirst();
                try {
                    contextCurrentContext = signalListenerCreateListener.addToContext(coreSubscriber.currentContext());
                } catch (Throwable th) {
                    signalListenerCreateListener.handleListenerError(new IllegalStateException("Unable to augment tap Context at construction via addToContext", th));
                    contextCurrentContext = coreSubscriber.currentContext();
                }
                ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(contextCurrentContext);
                try {
                    this.source.subscribe((CoreSubscriber<? super Object>) new FluxTapRestoringThreadLocals.TapSubscriber(coreSubscriber, signalListenerCreateListener, contextCurrentContext));
                    if (threadLocals != null) {
                        threadLocals.close();
                    }
                } catch (Throwable th2) {
                    if (threadLocals != null) {
                        try {
                            threadLocals.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                    }
                    throw th2;
                }
            } catch (Throwable th4) {
                signalListenerCreateListener.handleListenerError(th4);
                Operators.error(coreSubscriber, th4);
            }
        } catch (Throwable th5) {
            Operators.error(coreSubscriber, th5);
        }
    }

    @Override // reactor.core.publisher.MonoOperator, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PREFETCH) {
            return -1;
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        if (attr == InternalProducerAttr.INSTANCE) {
            return true;
        }
        return super.scanUnsafe(attr);
    }
}
