package reactor.core.publisher;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes5.dex */
public interface Timed<T> extends Supplier<T> {
    Duration elapsed();

    Duration elapsedSinceSubscription();

    @Override // java.util.function.Supplier
    T get();

    Instant timestamp();
}
