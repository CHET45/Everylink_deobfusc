package reactor.util.retry;

import java.util.function.Predicate;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class RetryBackoffSpec$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ Predicate f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.test((Throwable) obj);
    }
}
