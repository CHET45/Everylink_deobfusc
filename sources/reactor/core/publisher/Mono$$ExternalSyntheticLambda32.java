package reactor.core.publisher;

import java.util.function.Predicate;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class Mono$$ExternalSyntheticLambda32 implements Predicate {
    public final /* synthetic */ Class f$0;

    public /* synthetic */ Mono$$ExternalSyntheticLambda32(Class cls) {
        this.f$0 = cls;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.isInstance((Throwable) obj);
    }
}
