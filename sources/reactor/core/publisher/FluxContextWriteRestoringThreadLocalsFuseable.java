package reactor.core.publisher;

import io.micrometer.context.ContextSnapshot;
import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxContextWriteRestoringThreadLocalsFuseable<T> extends FluxOperator<T, T> implements Fuseable {
    final Function<Context, Context> doOnContext;

    FluxContextWriteRestoringThreadLocalsFuseable(Flux<? extends T> flux, Function<Context, Context> function) {
        super(flux);
        this.doOnContext = (Function) Objects.requireNonNull(function, "doOnContext");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Context contextApply = this.doOnContext.apply(coreSubscriber.currentContext());
        ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(contextApply);
        try {
            this.source.subscribe((CoreSubscriber<? super Object>) new FuseableContextWriteRestoringThreadLocalsSubscriber(coreSubscriber, contextApply));
            if (threadLocals != null) {
                threadLocals.close();
            }
        } catch (Throwable th) {
            if (threadLocals != null) {
                try {
                    threadLocals.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        if (attr == InternalProducerAttr.INSTANCE) {
            return true;
        }
        return super.scanUnsafe(attr);
    }

    static class FuseableContextWriteRestoringThreadLocalsSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        final Context context;

        /* JADX INFO: renamed from: s */
        Subscription f2106s;

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            return 0;
        }

        FuseableContextWriteRestoringThreadLocalsSubscriber(CoreSubscriber<? super T> coreSubscriber, Context context) {
            this.actual = coreSubscriber;
            this.context = context;
            if (coreSubscriber instanceof Fuseable.ConditionalSubscriber) {
                this.actualConditional = (Fuseable.ConditionalSubscriber) coreSubscriber;
            } else {
                this.actualConditional = null;
            }
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2106s;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.context;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                if (Operators.validate(this.f2106s, subscription)) {
                    this.f2106s = subscription;
                    this.actual.onSubscribe(this);
                }
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                this.actual.onNext(t);
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actualConditional;
                if (conditionalSubscriber != null) {
                    boolean zTryOnNext = conditionalSubscriber.tryOnNext(t);
                    if (threadLocals != null) {
                        threadLocals.close();
                    }
                    return zTryOnNext;
                }
                this.actual.onNext(t);
                if (threadLocals == null) {
                    return true;
                }
                threadLocals.close();
                return true;
            } catch (Throwable th) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                this.actual.onError(th);
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
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                this.actual.onComplete();
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.context);
            try {
                this.f2106s.request(j);
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.context);
            try {
                this.f2106s.cancel();
                if (threadLocals != null) {
                    threadLocals.close();
                }
            } catch (Throwable th) {
                if (threadLocals != null) {
                    try {
                        threadLocals.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        @Override // java.util.Queue
        public T poll() {
            throw new UnsupportedOperationException("Nope");
        }

        @Override // java.util.Collection
        public int size() {
            throw new UnsupportedOperationException("Nope");
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            throw new UnsupportedOperationException("Nope");
        }

        @Override // java.util.Collection
        public void clear() {
            throw new UnsupportedOperationException("Nope");
        }
    }
}
