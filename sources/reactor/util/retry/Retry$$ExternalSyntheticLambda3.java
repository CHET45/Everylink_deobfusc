package reactor.util.retry;

import java.util.function.Supplier;
import reactor.core.scheduler.Schedulers;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class Retry$$ExternalSyntheticLambda3 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Schedulers.parallel();
    }
}
