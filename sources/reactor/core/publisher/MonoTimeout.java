package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxTimeout;

/* JADX INFO: loaded from: classes5.dex */
final class MonoTimeout<T, U, V> extends InternalMonoOperator<T, T> {
    static final Function NEVER = new Function() { // from class: reactor.core.publisher.MonoTimeout$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Flux.never();
        }
    };
    final Publisher<U> firstTimeout;
    final Publisher<? extends T> other;
    final String timeoutDescription;

    MonoTimeout(Mono<? extends T> mono, Publisher<U> publisher, String str) {
        super(mono);
        this.firstTimeout = Mono.fromDirect((Publisher) Objects.requireNonNull(publisher, "firstTimeout"));
        this.other = null;
        this.timeoutDescription = str;
    }

    MonoTimeout(Mono<? extends T> mono, Publisher<U> publisher, Publisher<? extends T> publisher2) {
        super(mono);
        this.firstTimeout = Mono.fromDirect((Publisher) Objects.requireNonNull(publisher, "firstTimeout"));
        this.other = Mono.fromDirect((Publisher) Objects.requireNonNull(publisher2, "other"));
        this.timeoutDescription = null;
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new FluxTimeout.TimeoutMainSubscriber(coreSubscriber, this.firstTimeout, NEVER, this.other, FluxTimeout.addNameToTimeoutDescription(this.source, this.timeoutDescription));
    }

    @Override // reactor.core.publisher.InternalMonoOperator, reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }
}
