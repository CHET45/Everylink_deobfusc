package reactor.core.publisher;

import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.util.concurrent.Queues;

/* JADX INFO: loaded from: classes5.dex */
final class SinksSpecs {
    static final Sinks.RootSpec UNSAFE_ROOT_SPEC = new UnsafeSpecImpl();
    static final DefaultSinksSpecs DEFAULT_SINKS = new DefaultSinksSpecs();

    SinksSpecs() {
    }

    static abstract class AbstractSerializedSink {
        volatile Thread lockedAt;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<AbstractSerializedSink> WIP = AtomicIntegerFieldUpdater.newUpdater(AbstractSerializedSink.class, "wip");
        static final AtomicReferenceFieldUpdater<AbstractSerializedSink, Thread> LOCKED_AT = AtomicReferenceFieldUpdater.newUpdater(AbstractSerializedSink.class, Thread.class, "lockedAt");

        AbstractSerializedSink() {
        }

        boolean tryAcquire(Thread thread) {
            AtomicIntegerFieldUpdater<AbstractSerializedSink> atomicIntegerFieldUpdater = WIP;
            if (atomicIntegerFieldUpdater.get(this) == 0 && atomicIntegerFieldUpdater.compareAndSet(this, 0, 1)) {
                LOCKED_AT.lazySet(this, thread);
            } else {
                if (LOCKED_AT.get(this) != thread) {
                    return false;
                }
                atomicIntegerFieldUpdater.incrementAndGet(this);
            }
            return true;
        }
    }

    static final class UnsafeSpecImpl implements Sinks.RootSpec, Sinks.ManySpec, Sinks.ManyWithUpstreamUnsafeSpec, Sinks.MulticastSpec, Sinks.MulticastReplaySpec {
        final Sinks.UnicastSpec unicastSpec = new UnicastSpecImpl(false);

        @Override // reactor.core.publisher.Sinks.RootSpec
        public Sinks.ManySpec many() {
            return this;
        }

        @Override // reactor.core.publisher.Sinks.RootSpec
        public Sinks.ManyWithUpstreamUnsafeSpec manyWithUpstream() {
            return this;
        }

        @Override // reactor.core.publisher.Sinks.ManySpec
        public Sinks.MulticastSpec multicast() {
            return this;
        }

        @Override // reactor.core.publisher.Sinks.ManySpec
        public Sinks.MulticastReplaySpec replay() {
            return this;
        }

        UnsafeSpecImpl() {
        }

        @Override // reactor.core.publisher.Sinks.RootSpec
        public <T> Sinks.Empty<T> empty() {
            return new SinkEmptyMulticast();
        }

        @Override // reactor.core.publisher.Sinks.RootSpec
        public <T> Sinks.One<T> one() {
            return new SinkOneMulticast();
        }

        @Override // reactor.core.publisher.Sinks.ManySpec
        public Sinks.UnicastSpec unicast() {
            return this.unicastSpec;
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer() {
            return new SinkManyEmitterProcessor(true, Queues.SMALL_BUFFER_SIZE);
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer(int i) {
            return new SinkManyEmitterProcessor(true, i);
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer(int i, boolean z) {
            return new SinkManyEmitterProcessor(z, i);
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> directAllOrNothing() {
            return new SinkManyBestEffort(true);
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> directBestEffort() {
            return new SinkManyBestEffort(false);
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> all() {
            return SinkManyReplayProcessor.create();
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> all(int i) {
            return SinkManyReplayProcessor.create(i);
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> latest() {
            return SinkManyReplayProcessor.cacheLast();
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> latestOrDefault(T t) {
            return SinkManyReplayProcessor.cacheLastOrDefault(t);
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(int i) {
            return SinkManyReplayProcessor.create(i);
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(Duration duration) {
            return SinkManyReplayProcessor.createTimeout(duration);
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(Duration duration, Scheduler scheduler) {
            return SinkManyReplayProcessor.createTimeout(duration, scheduler);
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(int i, Duration duration) {
            return SinkManyReplayProcessor.createSizeAndTimeout(i, duration);
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(int i, Duration duration, Scheduler scheduler) {
            return SinkManyReplayProcessor.createSizeAndTimeout(i, duration, scheduler);
        }

        @Override // reactor.core.publisher.Sinks.ManyWithUpstreamUnsafeSpec
        public <T> Sinks.ManyWithUpstream<T> multicastOnBackpressureBuffer() {
            return new SinkManyEmitterProcessor(true, Queues.SMALL_BUFFER_SIZE);
        }

        @Override // reactor.core.publisher.Sinks.ManyWithUpstreamUnsafeSpec
        public <T> Sinks.ManyWithUpstream<T> multicastOnBackpressureBuffer(int i, boolean z) {
            return new SinkManyEmitterProcessor(z, i);
        }
    }

    static final class DefaultSinksSpecs implements Sinks.ManySpec, Sinks.MulticastSpec, Sinks.MulticastReplaySpec {
        final Sinks.UnicastSpec unicastSpec = new UnicastSpecImpl(true);

        Sinks.ManySpec many() {
            return this;
        }

        @Override // reactor.core.publisher.Sinks.ManySpec
        public Sinks.MulticastSpec multicast() {
            return this;
        }

        @Override // reactor.core.publisher.Sinks.ManySpec
        public Sinks.MulticastReplaySpec replay() {
            return this;
        }

        DefaultSinksSpecs() {
        }

        /* JADX WARN: Incorrect types in method signature: <T:Ljava/lang/Object;EMPTY::Lreactor/core/publisher/Sinks$Empty<TT;>;:Lreactor/core/publisher/ContextHolder;>(TEMPTY;)Lreactor/core/publisher/Sinks$Empty<TT;>; */
        Sinks.Empty wrapEmpty(Sinks.Empty empty) {
            return new SinkEmptySerialized(empty, (ContextHolder) empty);
        }

        /* JADX WARN: Incorrect types in method signature: <T:Ljava/lang/Object;ONE::Lreactor/core/publisher/Sinks$One<TT;>;:Lreactor/core/publisher/ContextHolder;>(TONE;)Lreactor/core/publisher/Sinks$One<TT;>; */
        Sinks.One wrapOne(Sinks.One one) {
            return new SinkOneSerialized(one, (ContextHolder) one);
        }

        /* JADX WARN: Incorrect types in method signature: <T:Ljava/lang/Object;MANY::Lreactor/core/publisher/Sinks$Many<TT;>;:Lreactor/core/publisher/ContextHolder;>(TMANY;)Lreactor/core/publisher/Sinks$Many<TT;>; */
        Sinks.Many wrapMany(Sinks.Many many) {
            return new SinkManySerialized(many, (ContextHolder) many);
        }

        <T> Sinks.Empty<T> empty() {
            return wrapEmpty(new SinkEmptyMulticast());
        }

        <T> Sinks.One<T> one() {
            return wrapOne(new SinkOneMulticast());
        }

        @Override // reactor.core.publisher.Sinks.ManySpec
        public Sinks.UnicastSpec unicast() {
            return this.unicastSpec;
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer() {
            return wrapMany(new SinkManyEmitterProcessor(true, Queues.SMALL_BUFFER_SIZE));
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer(int i) {
            return wrapMany(new SinkManyEmitterProcessor(true, i));
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer(int i, boolean z) {
            return wrapMany(new SinkManyEmitterProcessor(z, i));
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> directAllOrNothing() {
            return wrapMany(SinkManyBestEffort.createAllOrNothing());
        }

        @Override // reactor.core.publisher.Sinks.MulticastSpec
        public <T> Sinks.Many<T> directBestEffort() {
            return wrapMany(SinkManyBestEffort.createBestEffort());
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> all() {
            return wrapMany(SinkManyReplayProcessor.create());
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> all(int i) {
            return wrapMany(SinkManyReplayProcessor.create(i, true));
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> latest() {
            return wrapMany(SinkManyReplayProcessor.cacheLast());
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> latestOrDefault(T t) {
            return wrapMany(SinkManyReplayProcessor.cacheLastOrDefault(t));
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("historySize must be > 0");
            }
            return wrapMany(SinkManyReplayProcessor.create(i));
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(Duration duration) {
            return wrapMany(SinkManyReplayProcessor.createTimeout(duration));
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(Duration duration, Scheduler scheduler) {
            return wrapMany(SinkManyReplayProcessor.createTimeout(duration, scheduler));
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(int i, Duration duration) {
            if (i <= 0) {
                throw new IllegalArgumentException("historySize must be > 0");
            }
            return wrapMany(SinkManyReplayProcessor.createSizeAndTimeout(i, duration));
        }

        @Override // reactor.core.publisher.Sinks.MulticastReplaySpec
        public <T> Sinks.Many<T> limit(int i, Duration duration, Scheduler scheduler) {
            if (i <= 0) {
                throw new IllegalArgumentException("historySize must be > 0");
            }
            return wrapMany(SinkManyReplayProcessor.createSizeAndTimeout(i, duration, scheduler));
        }
    }

    static final class UnicastSpecImpl implements Sinks.UnicastSpec {
        final boolean serialized;

        UnicastSpecImpl(boolean z) {
            this.serialized = z;
        }

        /* JADX WARN: Incorrect types in method signature: <T:Ljava/lang/Object;MANY::Lreactor/core/publisher/Sinks$Many<TT;>;:Lreactor/core/publisher/ContextHolder;>(TMANY;)Lreactor/core/publisher/Sinks$Many<TT;>; */
        Sinks.Many wrapMany(Sinks.Many many) {
            return this.serialized ? new SinkManySerialized(many, (ContextHolder) many) : many;
        }

        @Override // reactor.core.publisher.Sinks.UnicastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer() {
            return wrapMany(SinkManyUnicast.create());
        }

        @Override // reactor.core.publisher.Sinks.UnicastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer(Queue<T> queue) {
            return wrapMany(SinkManyUnicast.create(queue));
        }

        @Override // reactor.core.publisher.Sinks.UnicastSpec
        public <T> Sinks.Many<T> onBackpressureBuffer(Queue<T> queue, Disposable disposable) {
            return wrapMany(SinkManyUnicast.create(queue, disposable));
        }

        @Override // reactor.core.publisher.Sinks.UnicastSpec
        public <T> Sinks.Many<T> onBackpressureError() {
            return wrapMany(SinkManyUnicastNoBackpressure.create());
        }
    }
}
