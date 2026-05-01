package reactor.core.publisher;

import androidx.concurrent.futures.C0162xc40028dd;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Scannable;
import reactor.core.publisher.FluxCreate;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
final class MonoCreate<T> extends Mono<T> implements SourceProducer<T> {
    final Consumer<MonoSink<T>> callback;
    static final Disposable TERMINATED = OperatorDisposables.DISPOSED;
    static final Disposable CANCELLED = Disposables.disposed();

    MonoCreate(Consumer<MonoSink<T>> consumer) {
        this.callback = consumer;
    }

    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        CoreSubscriber coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled = Operators.restoreContextOnSubscriberIfAutoCPEnabled(this, coreSubscriber);
        DefaultMonoSink defaultMonoSink = new DefaultMonoSink(coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled);
        coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.onSubscribe(defaultMonoSink);
        try {
            this.callback.accept(defaultMonoSink);
        } catch (Throwable th) {
            defaultMonoSink.error(Operators.onOperatorError(th, coreSubscriberRestoreContextOnSubscriberIfAutoCPEnabled.currentContext()));
        }
    }

    @Override // reactor.core.publisher.SourceProducer, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        if (attr == InternalProducerAttr.INSTANCE) {
            return true;
        }
        return super.scanUnsafe(attr);
    }

    static final class DefaultMonoSink<T> extends AtomicBoolean implements MonoSink<T>, InnerProducer<T> {
        static final int HAS_REQUEST_HAS_VALUE = 3;
        static final int HAS_REQUEST_NO_VALUE = 2;
        static final int NO_REQUEST_HAS_VALUE = 1;
        final CoreSubscriber<? super T> actual;
        volatile Disposable disposable;
        volatile LongConsumer requestConsumer;
        volatile int state;
        T value;
        static final AtomicReferenceFieldUpdater<DefaultMonoSink, Disposable> DISPOSABLE = AtomicReferenceFieldUpdater.newUpdater(DefaultMonoSink.class, Disposable.class, "disposable");
        static final AtomicIntegerFieldUpdater<DefaultMonoSink> STATE = AtomicIntegerFieldUpdater.newUpdater(DefaultMonoSink.class, "state");
        static final AtomicReferenceFieldUpdater<DefaultMonoSink, LongConsumer> REQUEST_CONSUMER = AtomicReferenceFieldUpdater.newUpdater(DefaultMonoSink.class, LongConsumer.class, "requestConsumer");

        DefaultMonoSink(CoreSubscriber<? super T> coreSubscriber) {
            this.actual = coreSubscriber;
        }

        @Override // reactor.core.publisher.MonoSink
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.publisher.MonoSink
        public ContextView contextView() {
            return this.actual.currentContext();
        }

        @Override // reactor.core.publisher.InnerProducer, reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.TERMINATED) {
                return Boolean.valueOf(this.state == 3 || this.state == 1 || this.disposable == MonoCreate.TERMINATED);
            }
            if (attr == Scannable.Attr.CANCELLED) {
                return Boolean.valueOf(this.disposable == MonoCreate.CANCELLED);
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.MonoSink
        public void success() {
            if (isDisposed() || STATE.getAndSet(this, 3) == 3) {
                return;
            }
            try {
                this.actual.onComplete();
            } finally {
                disposeResource(false);
            }
        }

        @Override // reactor.core.publisher.MonoSink
        public void success(@Nullable T t) {
            int i;
            if (t == null) {
                success();
                return;
            }
            Disposable disposable = this.disposable;
            if (disposable == MonoCreate.CANCELLED) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (disposable == MonoCreate.TERMINATED) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            do {
                i = this.state;
                if (i == 3 || i == 1) {
                    Operators.onNextDropped(t, this.actual.currentContext());
                    return;
                }
                if (i == 2) {
                    if (STATE.compareAndSet(this, i, 3)) {
                        try {
                            this.actual.onNext(t);
                            this.actual.onComplete();
                        } finally {
                            try {
                            } finally {
                            }
                        }
                        return;
                    }
                    Operators.onNextDropped(t, this.actual.currentContext());
                    return;
                }
                this.value = t;
            } while (!STATE.compareAndSet(this, i, 1));
        }

        @Override // reactor.core.publisher.MonoSink
        public void error(Throwable th) {
            if (isDisposed()) {
                Operators.onOperatorError(th, this.actual.currentContext());
            } else {
                if (STATE.getAndSet(this, 3) != 3) {
                    try {
                        this.actual.onError(th);
                        return;
                    } finally {
                        disposeResource(false);
                    }
                }
                Operators.onOperatorError(th, this.actual.currentContext());
            }
        }

        @Override // reactor.core.publisher.MonoSink
        public MonoSink<T> onRequest(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer, "onRequest");
            if (!C0162xc40028dd.m5m(REQUEST_CONSUMER, this, null, longConsumer)) {
                throw new IllegalStateException("A consumer has already been assigned to consume requests");
            }
            int i = this.state;
            if (i == 2 || i == 3) {
                longConsumer.accept(Long.MAX_VALUE);
            }
            return this;
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // reactor.core.publisher.MonoSink
        public MonoSink<T> onCancel(Disposable disposable) {
            Objects.requireNonNull(disposable, "onCancel");
            if (!C0162xc40028dd.m5m(DISPOSABLE, this, null, new FluxCreate.SinkDisposable(null, disposable))) {
                Disposable disposable2 = this.disposable;
                if (disposable2 == MonoCreate.CANCELLED) {
                    disposable.dispose();
                } else if (disposable2 instanceof FluxCreate.SinkDisposable) {
                    FluxCreate.SinkDisposable sinkDisposable = (FluxCreate.SinkDisposable) disposable2;
                    if (sinkDisposable.onCancel == null) {
                        sinkDisposable.onCancel = disposable;
                    } else {
                        disposable.dispose();
                    }
                }
            }
            return this;
        }

        @Override // reactor.core.publisher.MonoSink
        public MonoSink<T> onDispose(Disposable disposable) {
            Objects.requireNonNull(disposable, "onDispose");
            if (!C0162xc40028dd.m5m(DISPOSABLE, this, null, new FluxCreate.SinkDisposable(disposable, null))) {
                Disposable disposable2 = this.disposable;
                if (isDisposed()) {
                    disposable.dispose();
                } else if (disposable2 instanceof FluxCreate.SinkDisposable) {
                    FluxCreate.SinkDisposable sinkDisposable = (FluxCreate.SinkDisposable) disposable2;
                    if (sinkDisposable.disposable == null) {
                        sinkDisposable.disposable = disposable;
                    } else {
                        disposable.dispose();
                    }
                }
            }
            return this;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            int i;
            if (Operators.validate(j)) {
                LongConsumer longConsumer = this.requestConsumer;
                if (longConsumer != null) {
                    longConsumer.accept(j);
                }
                do {
                    i = this.state;
                    if (i == 2 || i == 3) {
                        return;
                    }
                    if (i == 1) {
                        if (STATE.compareAndSet(this, i, 3)) {
                            try {
                                this.actual.onNext(this.value);
                                this.actual.onComplete();
                                return;
                            } finally {
                                disposeResource(false);
                            }
                        }
                        return;
                    }
                } while (!STATE.compareAndSet(this, i, 2));
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            if (STATE.getAndSet(this, 3) != 3) {
                T t = this.value;
                this.value = null;
                Operators.onDiscard(t, this.actual.currentContext());
                disposeResource(true);
            }
        }

        void disposeResource(boolean z) {
            Disposable andSet;
            Disposable disposable = z ? MonoCreate.CANCELLED : MonoCreate.TERMINATED;
            Disposable disposable2 = this.disposable;
            if (disposable2 == MonoCreate.TERMINATED || disposable2 == MonoCreate.CANCELLED || (andSet = DISPOSABLE.getAndSet(this, disposable)) == null || andSet == MonoCreate.TERMINATED || andSet == MonoCreate.CANCELLED) {
                return;
            }
            if (z && (andSet instanceof FluxCreate.SinkDisposable)) {
                ((FluxCreate.SinkDisposable) andSet).cancel();
            }
            andSet.dispose();
        }

        @Override // java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "MonoSink";
        }

        boolean isDisposed() {
            Disposable disposable = this.disposable;
            return disposable == MonoCreate.CANCELLED || disposable == MonoCreate.TERMINATED;
        }
    }
}
