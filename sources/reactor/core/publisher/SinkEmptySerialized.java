package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.stream.Stream;
import reactor.core.Scannable;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.SinksSpecs;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
class SinkEmptySerialized<T> extends SinksSpecs.AbstractSerializedSink implements InternalEmptySink<T>, ContextHolder {
    final ContextHolder contextHolder;
    final Sinks.Empty<T> sink;

    SinkEmptySerialized(Sinks.Empty<T> empty, ContextHolder contextHolder) {
        this.sink = empty;
        this.contextHolder = contextHolder;
    }

    @Override // reactor.core.publisher.Sinks.Empty
    public final Sinks.EmitResult tryEmitEmpty() {
        Thread threadCurrentThread = Thread.currentThread();
        if (!tryAcquire(threadCurrentThread)) {
            return Sinks.EmitResult.FAIL_NON_SERIALIZED;
        }
        try {
            return this.sink.tryEmitEmpty();
        } finally {
            if (WIP.decrementAndGet(this) == 0) {
                C0162xc40028dd.m5m(LOCKED_AT, this, threadCurrentThread, null);
            }
        }
    }

    @Override // reactor.core.publisher.Sinks.Empty
    public final Sinks.EmitResult tryEmitError(Throwable th) {
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

    @Override // reactor.core.publisher.Sinks.Empty
    public int currentSubscriberCount() {
        return this.sink.currentSubscriberCount();
    }

    @Override // reactor.core.publisher.Sinks.Empty
    public Mono<T> asMono() {
        return this.sink.asMono();
    }

    @Override // reactor.core.Scannable
    public Stream<? extends Scannable> inners() {
        return this.sink.inners();
    }

    @Override // reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return this.sink.scanUnsafe(attr);
    }

    @Override // reactor.core.publisher.ContextHolder
    public Context currentContext() {
        return this.contextHolder.currentContext();
    }
}
