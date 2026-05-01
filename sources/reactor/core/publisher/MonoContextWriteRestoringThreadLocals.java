package reactor.core.publisher;

import io.micrometer.context.ContextSnapshot;
import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class MonoContextWriteRestoringThreadLocals<T> extends MonoOperator<T, T> {
    final Function<Context, Context> doOnContext;

    MonoContextWriteRestoringThreadLocals(Mono<? extends T> mono, Function<Context, Context> function) {
        super(mono);
        this.doOnContext = (Function) Objects.requireNonNull(function, "doOnContext");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // reactor.core.publisher.Mono, reactor.core.CorePublisher
    public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
        Context contextApply = this.doOnContext.apply(coreSubscriber.currentContext());
        ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(contextApply);
        try {
            this.source.subscribe((CoreSubscriber<? super Object>) new ContextWriteRestoringThreadLocalsSubscriber(coreSubscriber, contextApply));
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

    @Override // reactor.core.publisher.MonoOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        if (attr == InternalProducerAttr.INSTANCE) {
            return true;
        }
        return super.scanUnsafe(attr);
    }

    static final class ContextWriteRestoringThreadLocalsSubscriber<T> implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context context;
        boolean done;

        /* JADX INFO: renamed from: s */
        Subscription f2249s;

        ContextWriteRestoringThreadLocalsSubscriber(CoreSubscriber<? super T> coreSubscriber, Context context) {
            this.actual = coreSubscriber;
            this.context = context;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            if (attr == Scannable.Attr.PARENT) {
                return this.f2249s;
            }
            if (attr == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(attr);
        }

        @Override // reactor.core.publisher.InnerOperator, reactor.core.CoreSubscriber
        public Context currentContext() {
            return this.context;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                if (Operators.validate(this.f2249s, subscription)) {
                    this.f2249s = subscription;
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
            this.done = true;
            ContextSnapshot.Scope threadLocals = ContextPropagation.setThreadLocals(this.actual.currentContext());
            try {
                this.actual.onNext(t);
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

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                Operators.onErrorDropped(th, this.context);
                return;
            }
            this.done = true;
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
            if (this.done) {
                return;
            }
            this.done = true;
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
                this.f2249s.request(j);
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
                this.f2249s.cancel();
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
    }
}
