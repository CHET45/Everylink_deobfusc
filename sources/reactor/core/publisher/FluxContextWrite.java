package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class FluxContextWrite<T> extends InternalFluxOperator<T, T> implements Fuseable {
    final Function<Context, Context> doOnContext;

    FluxContextWrite(Flux<? extends T> flux, Function<Context, Context> function) {
        super(flux);
        this.doOnContext = (Function) Objects.requireNonNull(function, "doOnContext");
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> coreSubscriber) {
        return new ContextWriteSubscriber(coreSubscriber, this.doOnContext.apply(coreSubscriber.currentContext()));
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ContextWriteSubscriber<T> implements Fuseable.ConditionalSubscriber<T>, InnerOperator<T, T>, Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        final Context context;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2103qs;

        /* JADX INFO: renamed from: s */
        Subscription f2104s;

        ContextWriteSubscriber(CoreSubscriber<? super T> coreSubscriber, Context context) {
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
                return this.f2104s;
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
            if (Operators.validate(this.f2104s, subscription)) {
                this.f2104s = subscription;
                if (subscription instanceof Fuseable.QueueSubscription) {
                    this.f2103qs = (Fuseable.QueueSubscription) subscription;
                }
                this.actual.onSubscribe(this);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override // reactor.core.Fuseable.ConditionalSubscriber
        public boolean tryOnNext(T t) {
            Fuseable.ConditionalSubscriber<? super T> conditionalSubscriber = this.actualConditional;
            if (conditionalSubscriber != null) {
                return conditionalSubscriber.tryOnNext(t);
            }
            this.actual.onNext(t);
            return true;
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2104s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2104s.cancel();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2103qs;
            if (queueSubscription == null) {
                return 0;
            }
            return queueSubscription.requestFusion(i);
        }

        @Override // java.util.Queue
        @Nullable
        public T poll() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2103qs;
            if (queueSubscription != null) {
                return queueSubscription.poll();
            }
            return null;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2103qs;
            return queueSubscription == null || queueSubscription.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2103qs;
            if (queueSubscription != null) {
                queueSubscription.clear();
            }
        }

        @Override // java.util.Collection
        public int size() {
            Fuseable.QueueSubscription<T> queueSubscription = this.f2103qs;
            if (queueSubscription != null) {
                return queueSubscription.size();
            }
            return 0;
        }
    }
}
