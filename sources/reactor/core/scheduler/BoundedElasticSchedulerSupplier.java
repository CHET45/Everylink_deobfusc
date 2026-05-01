package reactor.core.scheduler;

import java.util.function.Supplier;
import reactor.util.Logger;
import reactor.util.Loggers;

/* JADX INFO: loaded from: classes5.dex */
class BoundedElasticSchedulerSupplier implements Supplier<Scheduler> {
    static final Logger logger = Loggers.getLogger((Class<?>) BoundedElasticSchedulerSupplier.class);

    BoundedElasticSchedulerSupplier() {
    }

    @Override // java.util.function.Supplier
    public Scheduler get() {
        if (Schedulers.DEFAULT_BOUNDED_ELASTIC_ON_VIRTUAL_THREADS) {
            logger.warn("Virtual Threads support is not available on the given JVM. Falling back to default BoundedElastic setup");
        }
        return Schedulers.newBoundedElastic(Schedulers.DEFAULT_BOUNDED_ELASTIC_SIZE, Schedulers.DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, "boundedElastic", 60, true);
    }
}
