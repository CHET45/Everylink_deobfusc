package reactor.core.scheduler;

import reactor.core.scheduler.BoundedElasticScheduler;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class BoundedElasticScheduler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ BoundedElasticScheduler.BoundedServices f$0;

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.eviction();
    }
}
