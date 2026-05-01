package reactor.core.publisher;

import java.util.function.Supplier;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class Flux$$ExternalSyntheticLambda20 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return ContextPropagation.contextCaptureToEmpty();
    }
}
