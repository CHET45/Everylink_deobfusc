package reactor.adapter;

import java.util.concurrent.Flow;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JdkFlowAdapter {
    public static <T> Flow.Publisher<T> publisherToFlowPublisher(Publisher<T> publisher) {
        return new PublisherAsFlowPublisher(publisher);
    }

    public static <T> Flux<T> flowPublisherToFlux(Flow.Publisher<T> publisher) {
        return new FlowPublisherAsFlux(publisher);
    }

    private static class FlowPublisherAsFlux<T> extends Flux<T> implements Scannable {
        private final Flow.Publisher<T> pub;

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return null;
        }

        private FlowPublisherAsFlux(Flow.Publisher<T> publisher) {
            this.pub = publisher;
        }

        @Override // reactor.core.publisher.Flux, reactor.core.CorePublisher
        public void subscribe(CoreSubscriber<? super T> coreSubscriber) {
            this.pub.subscribe(new SubscriberToRS(coreSubscriber));
        }
    }

    private static class PublisherAsFlowPublisher<T> implements Flow.Publisher<T> {
        private final Publisher<T> pub;

        private PublisherAsFlowPublisher(Publisher<T> publisher) {
            this.pub = publisher;
        }

        @Override // java.util.concurrent.Flow.Publisher
        public void subscribe(Flow.Subscriber<? super T> subscriber) {
            this.pub.subscribe(new FlowSubscriber(subscriber));
        }
    }

    private static class FlowSubscriber<T> implements CoreSubscriber<T>, Flow.Subscription {
        private final Flow.Subscriber<? super T> subscriber;
        Subscription subscription;

        public FlowSubscriber(Flow.Subscriber<? super T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override // reactor.core.CoreSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscriber.onSubscribe(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            this.subscriber.onNext(t);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            this.subscriber.onError(th);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            this.subscriber.onComplete();
        }

        @Override // java.util.concurrent.Flow.Subscription
        public void request(long j) {
            this.subscription.request(j);
        }

        @Override // java.util.concurrent.Flow.Subscription
        public void cancel() {
            this.subscription.cancel();
        }
    }

    private static class SubscriberToRS<T> implements Flow.Subscriber<T>, Subscription {

        /* JADX INFO: renamed from: s */
        private final Subscriber<? super T> f2075s;
        Flow.Subscription subscription;

        public SubscriberToRS(Subscriber<? super T> subscriber) {
            this.f2075s = subscriber;
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.f2075s.onSubscribe(this);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onNext(T t) {
            this.f2075s.onNext(t);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onError(Throwable th) {
            this.f2075s.onError(th);
        }

        @Override // java.util.concurrent.Flow.Subscriber
        public void onComplete() {
            this.f2075s.onComplete();
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            this.subscription.request(j);
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            this.subscription.cancel();
        }
    }

    JdkFlowAdapter() {
    }
}
