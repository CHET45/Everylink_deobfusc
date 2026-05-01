package reactor.core.publisher;

import java.util.Objects;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class MonoOperator<I, O> extends Mono<O> implements Scannable {
    protected final Mono<? extends I> source;

    protected MonoOperator(Mono<? extends I> mono) {
        this.source = (Mono) Objects.requireNonNull(mono);
    }

    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        if (attr == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        return attr == Scannable.Attr.PARENT ? this.source : attr == InternalProducerAttr.INSTANCE ? false : null;
    }
}
