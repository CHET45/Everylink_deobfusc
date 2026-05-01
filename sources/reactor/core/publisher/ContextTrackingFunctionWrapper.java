package reactor.core.publisher;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.FluxContextWrite;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
class ContextTrackingFunctionWrapper<T, V> implements Function<Publisher<T>, CorePublisher<V>> {
    static final String CONTEXT_MARKER_PREFIX = "reactor.core.context.marker.";
    final String marker;
    final Function<? super Publisher<T>, ? extends Publisher<V>> transformer;

    ContextTrackingFunctionWrapper(Function<? super Publisher<T>, ? extends Publisher<V>> function) {
        this(function, function.toString());
    }

    ContextTrackingFunctionWrapper(Function<? super Publisher<T>, ? extends Publisher<V>> function, String str) {
        this.transformer = function;
        this.marker = str;
    }

    @Override // java.util.function.Function
    public CorePublisher<V> apply(Publisher<T> publisher) {
        final String str = CONTEXT_MARKER_PREFIX + System.identityHashCode(publisher);
        final Publisher<V> publisherApply = this.transformer.apply((Publisher) Operators.liftPublisher(new BiFunction() { // from class: reactor.core.publisher.ContextTrackingFunctionWrapper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return this.f$0.m1956xcdeca802(str, (Publisher) obj, (CoreSubscriber) obj2);
            }
        }).apply(publisher));
        return new CorePublisher<V>() { // from class: reactor.core.publisher.ContextTrackingFunctionWrapper.1
            @Override // reactor.core.CorePublisher
            public void subscribe(CoreSubscriber<? super V> coreSubscriber) {
                FluxContextWrite.ContextWriteSubscriber contextWriteSubscriber = new FluxContextWrite.ContextWriteSubscriber(coreSubscriber, coreSubscriber.currentContext().put(str, true));
                Publisher publisher2 = publisherApply;
                if (publisher2 instanceof CorePublisher) {
                    ((CorePublisher) publisher2).subscribe((CoreSubscriber) contextWriteSubscriber);
                } else {
                    publisher2.subscribe(contextWriteSubscriber);
                }
            }

            @Override // org.reactivestreams.Publisher
            public void subscribe(Subscriber<? super V> subscriber) {
                subscribe((CoreSubscriber) Operators.toCoreSubscriber(subscriber));
            }
        };
    }

    /* JADX INFO: renamed from: lambda$apply$0$reactor-core-publisher-ContextTrackingFunctionWrapper */
    /* synthetic */ CoreSubscriber m1956xcdeca802(String str, Publisher publisher, CoreSubscriber coreSubscriber) {
        Context contextCurrentContext = coreSubscriber.currentContext();
        if (!contextCurrentContext.hasKey(str)) {
            throw new IllegalStateException("Context loss after applying " + this.marker);
        }
        return new FluxContextWrite.ContextWriteSubscriber(coreSubscriber, contextCurrentContext.delete(str));
    }
}
