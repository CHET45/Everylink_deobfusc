package reactor.core.publisher;

import java.util.Objects;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class FluxOperator<I, O> extends Flux<O> implements Scannable {
    protected final Flux<? extends I> source;

    protected FluxOperator(Flux<? extends I> flux) {
        this.source = (Flux) Objects.requireNonNull(flux);
    }

    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.PARENT ? this.source : attr == InternalProducerAttr.INSTANCE ? false : null;
    }
}
