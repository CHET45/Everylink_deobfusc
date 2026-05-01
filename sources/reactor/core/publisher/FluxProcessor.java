package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.stream.Stream;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxCreate;
import reactor.core.publisher.FluxSink;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
public abstract class FluxProcessor<IN, OUT> extends Flux<OUT> implements Processor<IN, OUT>, CoreSubscriber<IN>, Scannable, Disposable, ContextHolder {
    public int getBufferSize() {
        return Integer.MAX_VALUE;
    }

    @Nullable
    public Throwable getError() {
        return null;
    }

    protected boolean isIdentityProcessor() {
        return false;
    }

    public boolean isSerialized() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    protected boolean serializeAlways() {
        return true;
    }

    @Deprecated
    public static <T> FluxProcessor<Publisher<? extends T>, T> switchOnNext() {
        UnicastProcessor unicastProcessorCreate = UnicastProcessor.create();
        return wrap(unicastProcessorCreate, switchOnNext(unicastProcessorCreate));
    }

    public static <IN, OUT> FluxProcessor<IN, OUT> wrap(Subscriber<IN> subscriber, Publisher<OUT> publisher) {
        return new DelegateProcessor(publisher, subscriber);
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        onError(new CancellationException("Disposed"));
    }

    public long downstreamCount() {
        return inners().count();
    }

    public boolean hasDownstreams() {
        return downstreamCount() != 0;
    }

    public final boolean hasCompleted() {
        return isTerminated() && getError() == null;
    }

    public final boolean hasError() {
        return isTerminated() && getError() != null;
    }

    public Stream<? extends Scannable> inners() {
        return Stream.empty();
    }

    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.TERMINATED) {
            return Boolean.valueOf(isTerminated());
        }
        if (attr == Scannable.Attr.ERROR) {
            return getError();
        }
        if (attr == Scannable.Attr.CAPACITY) {
            return Integer.valueOf(getBufferSize());
        }
        return null;
    }

    public Context currentContext() {
        return Context.empty();
    }

    public final FluxProcessor<IN, OUT> serialize() {
        return new DelegateProcessor(this, Operators.serialize(this));
    }

    @Deprecated
    public final FluxSink<IN> sink() {
        return sink(FluxSink.OverflowStrategy.IGNORE);
    }

    @Deprecated
    public final FluxSink<IN> sink(FluxSink.OverflowStrategy overflowStrategy) {
        Objects.requireNonNull(overflowStrategy, "strategy");
        if (getBufferSize() == Integer.MAX_VALUE) {
            overflowStrategy = FluxSink.OverflowStrategy.IGNORE;
        }
        FluxCreate.BaseSink baseSinkCreateSink = FluxCreate.createSink(this, overflowStrategy);
        onSubscribe(baseSinkCreateSink);
        if (baseSinkCreateSink.isCancelled() || (isSerialized() && getBufferSize() == Integer.MAX_VALUE)) {
            return baseSinkCreateSink;
        }
        if (serializeAlways()) {
            return new FluxCreate.SerializedFluxSink(baseSinkCreateSink);
        }
        return new FluxCreate.SerializeOnRequestSink(baseSinkCreateSink);
    }
}
