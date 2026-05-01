package reactor.core.publisher;

import io.micrometer.context.ContextAccessor;
import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.context.ThreadLocalAccessor;
import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import reactor.core.observability.SignalListener;
import reactor.core.publisher.ContextPropagation;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
final class ContextPropagation {
    static final Function<Context, Context> NO_OP;
    static final Function<Context, Context> WITH_GLOBAL_REGISTRY_NO_PREDICATE;
    static ContextSnapshotFactory globalContextSnapshotFactory;

    static /* synthetic */ Context lambda$static$0(Context context) {
        return context;
    }

    ContextPropagation() {
    }

    static {
        Function<Context, Context> contextCaptureNoPredicate = new Function() { // from class: reactor.core.publisher.ContextPropagation$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ContextPropagation.lambda$static$0((Context) obj);
            }
        };
        NO_OP = contextCaptureNoPredicate;
        globalContextSnapshotFactory = null;
        if (ContextPropagationSupport.isContextPropagationAvailable()) {
            contextCaptureNoPredicate = new ContextCaptureNoPredicate();
        }
        WITH_GLOBAL_REGISTRY_NO_PREDICATE = contextCaptureNoPredicate;
        if (ContextPropagationSupport.isContextPropagation103Available()) {
            globalContextSnapshotFactory = ContextSnapshotFactory.builder().clearMissing(false).build();
        }
    }

    static <T> Flux<T> fluxRestoreThreadLocals(Flux<? extends T> flux, boolean z) {
        if (z) {
            return new FluxContextWriteRestoringThreadLocalsFuseable(flux, Function.identity());
        }
        return new FluxContextWriteRestoringThreadLocals(flux, Function.identity());
    }

    static <T> Mono<T> monoRestoreThreadLocals(Mono<? extends T> mono) {
        return new MonoContextWriteRestoringThreadLocals(mono, Function.identity());
    }

    static void configureContextSnapshotFactory(boolean z) {
        if (ContextPropagationSupport.isContextPropagation103OnClasspath) {
            globalContextSnapshotFactory = ContextSnapshotFactory.builder().clearMissing(z).build();
        }
    }

    static <C> ContextSnapshot.Scope setThreadLocals(Object obj) {
        if (ContextPropagationSupport.isContextPropagation103OnClasspath) {
            return globalContextSnapshotFactory.setThreadLocalsFrom(obj, new String[0]);
        }
        ContextRegistry contextRegistry = ContextRegistry.getInstance();
        ContextAccessor contextAccessorForRead = contextRegistry.getContextAccessorForRead(obj);
        Map<Object, Object> threadLocal = null;
        for (ThreadLocalAccessor threadLocalAccessor : contextRegistry.getThreadLocalAccessors()) {
            Object objKey = threadLocalAccessor.key();
            threadLocal = setThreadLocal(objKey, contextAccessorForRead.readValue(obj, objKey), threadLocalAccessor, threadLocal);
        }
        if (ContextPropagationSupport.isContextPropagation101Available()) {
            return ReactorScopeImpl.from(threadLocal, contextRegistry);
        }
        return ReactorScopeImpl100.from(threadLocal, contextRegistry);
    }

    private static <V> Map<Object, Object> setThreadLocal(Object obj, @Nullable V v, ThreadLocalAccessor<?> threadLocalAccessor, @Nullable Map<Object, Object> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(obj, threadLocalAccessor.getValue());
        if (v != null) {
            threadLocalAccessor.setValue(v);
        } else {
            threadLocalAccessor.reset();
        }
        return map;
    }

    static ContextSnapshot captureThreadLocals() {
        if (ContextPropagationSupport.isContextPropagation103OnClasspath) {
            return globalContextSnapshotFactory.captureAll(new Object[0]);
        }
        return ContextSnapshot.captureAll(new Object[0]);
    }

    public static Function<Runnable, Runnable> scopePassingOnScheduleHook() {
        return new Function() { // from class: reactor.core.publisher.ContextPropagation$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ContextPropagation.captureThreadLocals().wrap((Runnable) obj);
            }
        };
    }

    static Function<Context, Context> contextCapture() {
        return WITH_GLOBAL_REGISTRY_NO_PREDICATE;
    }

    static Context contextCaptureToEmpty() {
        return contextCapture().apply(Context.empty());
    }

    static <T, R> BiConsumer<T, SynchronousSink<R>> contextRestoreForHandle(final BiConsumer<T, SynchronousSink<R>> biConsumer, Supplier<Context> supplier) {
        if (!ContextPropagationSupport.shouldRestoreThreadLocalsInSomeOperators()) {
            return biConsumer;
        }
        final Context context = supplier.get();
        if (context.isEmpty()) {
            return biConsumer;
        }
        if (ContextPropagationSupport.isContextPropagation103OnClasspath) {
            return new BiConsumer() { // from class: reactor.core.publisher.ContextPropagation$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ContextPropagation.lambda$contextRestoreForHandle$2(context, biConsumer, obj, (SynchronousSink) obj2);
                }
            };
        }
        return new BiConsumer() { // from class: reactor.core.publisher.ContextPropagation$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ContextPropagation.lambda$contextRestoreForHandle$3(context, biConsumer, obj, (SynchronousSink) obj2);
            }
        };
    }

    static /* synthetic */ void lambda$contextRestoreForHandle$2(Context context, BiConsumer biConsumer, Object obj, SynchronousSink synchronousSink) {
        ContextSnapshot.Scope threadLocalsFrom = globalContextSnapshotFactory.setThreadLocalsFrom(context, new String[0]);
        try {
            biConsumer.accept(obj, synchronousSink);
            if (threadLocalsFrom != null) {
                threadLocalsFrom.close();
            }
        } catch (Throwable th) {
            if (threadLocalsFrom != null) {
                try {
                    threadLocalsFrom.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    static /* synthetic */ void lambda$contextRestoreForHandle$3(Context context, BiConsumer biConsumer, Object obj, SynchronousSink synchronousSink) {
        ContextSnapshot.Scope allThreadLocalsFrom = ContextSnapshot.setAllThreadLocalsFrom(context);
        try {
            biConsumer.accept(obj, synchronousSink);
            if (allThreadLocalsFrom != null) {
                allThreadLocalsFrom.close();
            }
        } catch (Throwable th) {
            if (allThreadLocalsFrom != null) {
                try {
                    allThreadLocalsFrom.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    static <T> SignalListener<T> contextRestoreForTap(SignalListener<T> signalListener, Supplier<Context> supplier) {
        if (!ContextPropagationSupport.isContextPropagationAvailable()) {
            return signalListener;
        }
        Context context = supplier.get();
        if (context.isEmpty()) {
            return signalListener;
        }
        if (ContextPropagationSupport.isContextPropagation103OnClasspath) {
            return new ContextRestore103SignalListener(signalListener, context, globalContextSnapshotFactory);
        }
        return new ContextRestoreSignalListener(signalListener, context);
    }

    static class ContextRestoreSignalListener<T> implements SignalListener<T> {
        final ContextView context;
        final SignalListener<T> original;

        public ContextRestoreSignalListener(SignalListener<T> signalListener, ContextView contextView) {
            this.original = signalListener;
            this.context = contextView;
        }

        ContextSnapshot.Scope restoreThreadLocals() {
            return ContextSnapshot.setAllThreadLocalsFrom(this.context);
        }

        @Override // reactor.core.observability.SignalListener
        public final void doFirst() throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doFirst();
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doFinally(SignalType signalType) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doFinally(signalType);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnSubscription() throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnSubscription();
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnFusion(int i) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnFusion(i);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnRequest(long j) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnRequest(j);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnCancel() throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnCancel();
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnNext(T t) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnNext(t);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnComplete() throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnComplete();
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnError(Throwable th) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnError(th);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th2) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doAfterComplete() throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doAfterComplete();
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doAfterError(Throwable th) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doAfterError(th);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th2) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnMalformedOnNext(T t) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnMalformedOnNext(t);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnMalformedOnError(Throwable th) throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnMalformedOnError(th);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th2) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void doOnMalformedOnComplete() throws Throwable {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.doOnMalformedOnComplete();
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final void handleListenerError(Throwable th) {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                this.original.handleListenerError(th);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
            } catch (Throwable th2) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }

        @Override // reactor.core.observability.SignalListener
        public final Context addToContext(Context context) {
            ContextSnapshot.Scope scopeRestoreThreadLocals = restoreThreadLocals();
            try {
                Context contextAddToContext = this.original.addToContext(context);
                if (scopeRestoreThreadLocals != null) {
                    scopeRestoreThreadLocals.close();
                }
                return contextAddToContext;
            } catch (Throwable th) {
                if (scopeRestoreThreadLocals != null) {
                    try {
                        scopeRestoreThreadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    static final class ContextRestore103SignalListener<T> extends ContextRestoreSignalListener<T> {
        final ContextSnapshotFactory contextSnapshotFactory;

        public ContextRestore103SignalListener(SignalListener<T> signalListener, ContextView contextView, ContextSnapshotFactory contextSnapshotFactory) {
            super(signalListener, contextView);
            this.contextSnapshotFactory = contextSnapshotFactory;
        }

        @Override // reactor.core.publisher.ContextPropagation.ContextRestoreSignalListener
        ContextSnapshot.Scope restoreThreadLocals() {
            return this.contextSnapshotFactory.setThreadLocalsFrom(this.context, new String[0]);
        }
    }

    static final class ContextCaptureNoPredicate implements Function<Context, Context> {
        ContextCaptureNoPredicate() {
        }

        @Override // java.util.function.Function
        public Context apply(Context context) {
            return (Context) ContextPropagation.captureThreadLocals().updateContext(context);
        }
    }

    static final class ContextQueue<T> extends AbstractQueue<T> {
        static final String NOT_SUPPORTED_MESSAGE = "ContextQueue wrapper is intended for use with instances returned by Queues class. Iterator based methods are usually unsupported.";
        boolean cleanOnNull;
        final Queue<Envelope<T>> envelopeQueue;
        boolean hasPrevious = false;
        Thread lastReader;
        ContextSnapshot.Scope scope;

        /* JADX WARN: Multi-variable type inference failed */
        ContextQueue(Queue<?> queue) {
            this.envelopeQueue = queue;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.envelopeQueue.size();
        }

        @Override // java.util.Queue
        public boolean offer(T t) {
            return this.envelopeQueue.offer(new Envelope<>(t, ContextPropagation.captureThreadLocals()));
        }

        @Override // java.util.Queue
        public T poll() {
            ContextSnapshot.Scope scope;
            Envelope<T> envelopePoll = this.envelopeQueue.poll();
            if (envelopePoll == null) {
                if (this.cleanOnNull && (scope = this.scope) != null) {
                    scope.close();
                }
                this.cleanOnNull = true;
                this.lastReader = Thread.currentThread();
                this.hasPrevious = false;
                return null;
            }
            restoreTheContext(envelopePoll);
            this.hasPrevious = true;
            return envelopePoll.body;
        }

        private void restoreTheContext(Envelope<T> envelope) {
            ContextSnapshot contextSnapshot = envelope.contextSnapshot;
            if (!contextSnapshot.equals(ContextPropagation.captureThreadLocals())) {
                if (!this.hasPrevious || !Thread.currentThread().equals(this.lastReader)) {
                    this.cleanOnNull = true;
                    this.lastReader = Thread.currentThread();
                }
                this.scope = contextSnapshot.setThreadLocals();
                return;
            }
            if (this.hasPrevious && Thread.currentThread().equals(this.lastReader)) {
                return;
            }
            this.cleanOnNull = false;
            this.lastReader = Thread.currentThread();
        }

        @Override // java.util.Queue
        @Nullable
        public T peek() {
            Envelope<T> envelopePeek = this.envelopeQueue.peek();
            if (envelopePeek == null) {
                return null;
            }
            return envelopePeek.body;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<T> iterator() {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }
    }

    static class Envelope<T> {
        final T body;
        final ContextSnapshot contextSnapshot;

        Envelope(T t, ContextSnapshot contextSnapshot) {
            this.body = t;
            this.contextSnapshot = contextSnapshot;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ReactorScopeImpl implements ContextSnapshot.Scope {
        private final ContextRegistry contextRegistry;
        private final Map<Object, Object> previousValues;

        static /* synthetic */ void lambda$from$0() {
        }

        private ReactorScopeImpl(Map<Object, Object> map, ContextRegistry contextRegistry) {
            this.previousValues = map;
            this.contextRegistry = contextRegistry;
        }

        public void close() {
            for (ThreadLocalAccessor<?> threadLocalAccessor : this.contextRegistry.getThreadLocalAccessors()) {
                if (this.previousValues.containsKey(threadLocalAccessor.key())) {
                    resetThreadLocalValue(threadLocalAccessor, this.previousValues.get(threadLocalAccessor.key()));
                }
            }
        }

        private <V> void resetThreadLocalValue(ThreadLocalAccessor<?> threadLocalAccessor, @Nullable V v) {
            if (v != null) {
                threadLocalAccessor.restore(v);
            } else {
                threadLocalAccessor.reset();
            }
        }

        public static ContextSnapshot.Scope from(@Nullable Map<Object, Object> map, ContextRegistry contextRegistry) {
            return map != null ? new ReactorScopeImpl(map, contextRegistry) : new ContextSnapshot.Scope() { // from class: reactor.core.publisher.ContextPropagation$ReactorScopeImpl$$ExternalSyntheticLambda0
                public final void close() {
                    ContextPropagation.ReactorScopeImpl.lambda$from$0();
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ReactorScopeImpl100 implements ContextSnapshot.Scope {
        private final ContextRegistry contextRegistry;
        private final Map<Object, Object> previousValues;

        static /* synthetic */ void lambda$from$0() {
        }

        private ReactorScopeImpl100(Map<Object, Object> map, ContextRegistry contextRegistry) {
            this.previousValues = map;
            this.contextRegistry = contextRegistry;
        }

        public void close() {
            for (ThreadLocalAccessor<?> threadLocalAccessor : this.contextRegistry.getThreadLocalAccessors()) {
                if (this.previousValues.containsKey(threadLocalAccessor.key())) {
                    resetThreadLocalValue(threadLocalAccessor, this.previousValues.get(threadLocalAccessor.key()));
                }
            }
        }

        private <V> void resetThreadLocalValue(ThreadLocalAccessor<?> threadLocalAccessor, @Nullable V v) {
            if (v != null) {
                threadLocalAccessor.setValue(v);
            } else {
                threadLocalAccessor.reset();
            }
        }

        public static ContextSnapshot.Scope from(@Nullable Map<Object, Object> map, ContextRegistry contextRegistry) {
            return map != null ? new ReactorScopeImpl100(map, contextRegistry) : new ContextSnapshot.Scope() { // from class: reactor.core.publisher.ContextPropagation$ReactorScopeImpl100$$ExternalSyntheticLambda0
                public final void close() {
                    ContextPropagation.ReactorScopeImpl100.lambda$from$0();
                }
            };
        }
    }
}
