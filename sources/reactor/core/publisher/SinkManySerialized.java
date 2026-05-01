package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.stream.Stream;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.SinksSpecs;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class SinkManySerialized<T> extends SinksSpecs.AbstractSerializedSink implements InternalManySink<T>, Scannable {
    final ContextHolder contextHolder;
    final Sinks.Many<T> sink;

    SinkManySerialized(Sinks.Many<T> many, ContextHolder contextHolder) {
        this.sink = many;
        this.contextHolder = contextHolder;
    }

    @Override // reactor.core.publisher.Sinks.Many
    public int currentSubscriberCount() {
        return this.sink.currentSubscriberCount();
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Flux<T> asFlux() {
        return this.sink.asFlux();
    }

    @Override // reactor.core.publisher.ContextHolder
    public Context currentContext() {
        return this.contextHolder.currentContext();
    }

    public boolean isCancelled() {
        return ((Boolean) Scannable.from(this.sink).scanOrDefault(Scannable.Attr.CANCELLED, false)).booleanValue();
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitComplete() {
        Thread threadCurrentThread = Thread.currentThread();
        if (!tryAcquire(threadCurrentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            return this.sink.tryEmitComplete();
        } finally {
            if (WIP.decrementAndGet(this) == 0) {
                C0162xc40028dd.m5m(LOCKED_AT, this, threadCurrentThread, null);
            }
        }
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitError(Throwable th) {
        Objects.requireNonNull(th, "t is null in sink.error(t)");
        Thread threadCurrentThread = Thread.currentThread();
        if (!tryAcquire(threadCurrentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            return this.sink.tryEmitError(th);
        } finally {
            if (WIP.decrementAndGet(this) == 0) {
                C0162xc40028dd.m5m(LOCKED_AT, this, threadCurrentThread, null);
            }
        }
    }

    @Override // reactor.core.publisher.Sinks.Many
    public Sinks.EmitResult tryEmitNext(T t) {
        Objects.requireNonNull(t, "t is null in sink.next(t)");
        Thread threadCurrentThread = Thread.currentThread();
        if (!tryAcquire(threadCurrentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            return this.sink.tryEmitNext(t);
        } finally {
            if (WIP.decrementAndGet(this) == 0) {
                C0162xc40028dd.m5m(LOCKED_AT, this, threadCurrentThread, null);
            }
        }
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return this.sink.scanUnsafe(attr);
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return Scannable.from(this.sink).inners();
    }

    public String toString() {
        return this.sink.toString();
    }
}
