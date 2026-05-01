package reactor.core.publisher;

import java.util.function.Supplier;
import reactor.core.CoreSubscriber;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class FluxHandle$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ CoreSubscriber f$0;

    @Override // java.util.function.Supplier
    public final Object get() {
        return this.f$0.currentContext();
    }
}
