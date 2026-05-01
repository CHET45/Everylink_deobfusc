package reactor.core.publisher;

import java.util.Objects;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
final class DelegateProcessor<IN, OUT> extends FluxProcessor<IN, OUT> {
    final Publisher<OUT> downstream;
    final Subscriber<IN> upstream;

    DelegateProcessor(Publisher<OUT> publisher, Subscriber<IN> subscriber) {
        this.downstream = (Publisher) Objects.requireNonNull(publisher, "Downstream must not be null");
        this.upstream = (Subscriber) Objects.requireNonNull(subscriber, "Upstream must not be null");
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.CoreSubscriber
    public Context currentContext() {
        Subscriber<IN> subscriber = this.upstream;
        if (subscriber instanceof CoreSubscriber) {
            return ((CoreSubscriber) subscriber).currentContext();
        }
        return Context.empty();
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        this.upstream.onComplete();
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        this.upstream.onError(th);
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(IN in) {
        this.upstream.onNext(in);
    }

    @Override // org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        this.upstream.onSubscribe(subscription);
    }

    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super OUT> coreSubscriber) {
        Objects.requireNonNull(coreSubscriber, "subscribe");
        this.downstream.subscribe(coreSubscriber);
    }

    @Override // reactor.core.publisher.FluxProcessor
    public boolean isSerialized() {
        Subscriber<IN> subscriber = this.upstream;
        return (subscriber instanceof SerializedSubscriber) || ((subscriber instanceof FluxProcessor) && ((FluxProcessor) subscriber).isSerialized());
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Scannable.from(this.upstream).inners();
    }

    @Override // reactor.core.publisher.FluxProcessor
    public int getBufferSize() {
        return ((Integer) Scannable.from(this.upstream).scanOrDefault(Scannable.Attr.CAPACITY, Integer.valueOf(super.getBufferSize()))).intValue();
    }

    @Override // reactor.core.publisher.FluxProcessor
    @Nullable
    public Throwable getError() {
        return (Throwable) Scannable.from(this.upstream).scanOrDefault(Scannable.Attr.ERROR, super.getError());
    }

    @Override // reactor.core.publisher.FluxProcessor
    public boolean isTerminated() {
        return ((Boolean) Scannable.from(this.upstream).scanOrDefault(Scannable.Attr.TERMINATED, Boolean.valueOf(super.isTerminated()))).booleanValue();
    }

    @Override // reactor.core.publisher.FluxProcessor, reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PARENT) {
            return this.downstream;
        }
        return Scannable.from(this.upstream).scanUnsafe(attr);
    }
}
