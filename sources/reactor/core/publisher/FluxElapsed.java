package reactor.core.publisher;

import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/* JADX INFO: loaded from: classes5.dex */
final class FluxElapsed<T> extends InternalFluxOperator<T, Tuple2<Long, T>> implements Fuseable {
    final Scheduler scheduler;

    FluxElapsed(Flux<T> flux, Scheduler scheduler) {
        super(flux);
        this.scheduler = scheduler;
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.OptimizableOperator
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Tuple2<Long, T>> coreSubscriber) {
        return new ElapsedSubscriber(coreSubscriber, this.scheduler);
    }

    @Override // reactor.core.publisher.InternalFluxOperator, reactor.core.publisher.FluxOperator, reactor.core.Scannable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
    }

    static final class ElapsedSubscriber<T> implements InnerOperator<T, Tuple2<Long, T>>, Fuseable.QueueSubscription<Tuple2<Long, T>> {
        final CoreSubscriber<? super Tuple2<Long, T>> actual;
        long lastTime;

        /* JADX INFO: renamed from: qs */
        Fuseable.QueueSubscription<T> f2122qs;

        /* JADX INFO: renamed from: s */
        Subscription f2123s;
        final Scheduler scheduler;

        ElapsedSubscriber(CoreSubscriber<? super Tuple2<Long, T>> coreSubscriber, Scheduler scheduler) {
            this.actual = coreSubscriber;
            this.scheduler = scheduler;
        }

        @Override // reactor.core.Scannable
        @Nullable
        public Object scanUnsafe(Scannable.Attr attr) {
            return attr == Scannable.Attr.PARENT ? this.f2123s : attr == Scannable.Attr.RUN_ON ? this.scheduler : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : super.scanUnsafe(attr);
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (Operators.validate(this.f2123s, subscription)) {
                this.lastTime = this.scheduler.now(TimeUnit.MILLISECONDS);
                this.f2123s = subscription;
                this.actual.onSubscribe(this);
            }
        }

        @Override // reactor.core.publisher.InnerProducer
        public CoreSubscriber<? super Tuple2<Long, T>> actual() {
            return this.actual;
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            if (t == null) {
                this.actual.onNext(null);
            } else {
                this.actual.onNext(snapshot(t));
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.f2123s.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.f2123s.cancel();
        }

        @Override // reactor.core.Fuseable.QueueSubscription
        public int requestFusion(int i) {
            Fuseable.QueueSubscription<T> queueSubscriptionM1969as = Operators.m1969as(this.f2123s);
            if (queueSubscriptionM1969as == null) {
                return 0;
            }
            this.f2122qs = queueSubscriptionM1969as;
            return queueSubscriptionM1969as.requestFusion(i);
        }

        Tuple2<Long, T> snapshot(T t) {
            long jNow = this.scheduler.now(TimeUnit.MILLISECONDS);
            long j = this.lastTime;
            this.lastTime = jNow;
            return Tuples.m1988of(Long.valueOf(jNow - j), t);
        }

        @Override // java.util.Queue
        @Nullable
        public Tuple2<Long, T> poll() {
            T tPoll = this.f2122qs.poll();
            if (tPoll != null) {
                return snapshot(tPoll);
            }
            return null;
        }

        @Override // java.util.Collection
        public int size() {
            return this.f2122qs.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.f2122qs.isEmpty();
        }

        @Override // java.util.Collection
        public void clear() {
            this.f2122qs.clear();
        }
    }
}
