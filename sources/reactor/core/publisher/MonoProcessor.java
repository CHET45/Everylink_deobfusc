package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.reactivestreams.Processor;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
@Deprecated
public abstract class MonoProcessor<O> extends Mono<O> implements Processor<O, O>, CoreSubscriber<O>, Disposable, Subscription, Scannable {
    @Override // org.reactivestreams.Subscription
    @Deprecated
    public void cancel() {
    }

    @Nullable
    public Throwable getError() {
        return null;
    }

    @Deprecated
    public boolean isCancelled() {
        return false;
    }

    public boolean isTerminated() {
        return false;
    }

    @Nullable
    @Deprecated
    public O peek() {
        return null;
    }

    @Deprecated
    public static <T> MonoProcessor<T> create() {
        return new NextProcessor(null);
    }

    @Override // org.reactivestreams.Subscription
    @Deprecated
    public void request(long j) {
        Operators.validate(j);
    }

    @Override // reactor.core.Disposable
    public void dispose() {
        onError(new CancellationException("Disposed"));
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public O block() {
        return block(null);
    }

    @Override // reactor.core.publisher.Mono
    @Nullable
    public O block(@Nullable Duration duration) {
        return peek();
    }

    public final boolean isError() {
        return getError() != null;
    }

    public final boolean isSuccess() {
        return isTerminated() && !isError();
    }

    @Override // reactor.core.Disposable
    public boolean isDisposed() {
        return isTerminated() || isCancelled();
    }

    @Override // reactor.core.CoreSubscriber
    public Context currentContext() {
        Stream<? extends Scannable> streamInners = inners();
        final Class<InnerProducer> cls = InnerProducer.class;
        Objects.requireNonNull(InnerProducer.class);
        Stream<? extends Scannable> streamFilter = streamInners.filter(new Predicate() { // from class: reactor.core.publisher.MonoProcessor$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance((Scannable) obj);
            }
        });
        final Class<InnerProducer> cls2 = InnerProducer.class;
        Objects.requireNonNull(InnerProducer.class);
        return Operators.multiSubscribersContext((InnerProducer[]) streamFilter.map(new Function() { // from class: reactor.core.publisher.MonoProcessor$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (InnerProducer) cls2.cast((Scannable) obj);
            }
        }).toArray(new IntFunction() { // from class: reactor.core.publisher.MonoProcessor$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return MonoProcessor.lambda$currentContext$0(i);
            }
        }));
    }

    static /* synthetic */ InnerProducer[] lambda$currentContext$0(int i) {
        return new InnerProducer[i];
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        boolean zIsTerminated = isTerminated();
        if (attr == Scannable.Attr.TERMINATED) {
            return Boolean.valueOf(zIsTerminated);
        }
        if (attr == Scannable.Attr.ERROR) {
            return getError();
        }
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (attr == Scannable.Attr.CANCELLED) {
            return Boolean.valueOf(isCancelled());
        }
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    public long downstreamCount() {
        return inners().count();
    }

    public final boolean hasDownstreams() {
        return downstreamCount() != 0;
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Stream.empty();
    }
}
