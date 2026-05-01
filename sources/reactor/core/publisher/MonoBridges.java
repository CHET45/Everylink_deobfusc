package reactor.core.publisher;

import java.util.function.Function;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes5.dex */
final class MonoBridges {
    MonoBridges() {
    }

    static <R> Mono<R> zip(Function<? super Object[], ? extends R> function, Mono<?>[] monoArr) {
        return Mono.zip(function, monoArr);
    }

    static Mono<Void> when(Publisher<?>[] publisherArr) {
        return Mono.when(publisherArr);
    }
}
