package reactor.core.scheduler;

import java.util.Objects;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.function.Predicate;
import reactor.blockhound.BlockHound;
import reactor.blockhound.integration.BlockHoundIntegration;

/* JADX INFO: loaded from: classes5.dex */
public final class ReactorBlockHoundIntegration implements BlockHoundIntegration {
    static /* synthetic */ Predicate lambda$applyTo$0(Predicate predicate) {
        final Class<NonBlocking> cls = NonBlocking.class;
        Objects.requireNonNull(NonBlocking.class);
        return predicate.or(new Predicate() { // from class: reactor.core.scheduler.ReactorBlockHoundIntegration$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance((Thread) obj);
            }
        });
    }

    public void applyTo(BlockHound.Builder builder) {
        builder.nonBlockingThreadPredicate(new Function() { // from class: reactor.core.scheduler.ReactorBlockHoundIntegration$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ReactorBlockHoundIntegration.lambda$applyTo$0((Predicate) obj);
            }
        });
        builder.allowBlockingCallsInside(ScheduledThreadPoolExecutor.class.getName() + "$DelayedWorkQueue", "offer");
        builder.allowBlockingCallsInside(ScheduledThreadPoolExecutor.class.getName() + "$DelayedWorkQueue", "take");
        builder.allowBlockingCallsInside(BoundedElasticScheduler.class.getName() + "$BoundedScheduledExecutorService", "ensureQueueCapacity");
        builder.allowBlockingCallsInside(SchedulerTask.class.getName(), "dispose");
        builder.allowBlockingCallsInside(WorkerTask.class.getName(), "dispose");
        builder.allowBlockingCallsInside(ThreadPoolExecutor.class.getName(), "processWorkerExit");
        builder.allowBlockingCallsInside("reactor.core.publisher.ContextPropagation", "<clinit>");
        builder.allowBlockingCallsInside(FutureTask.class.getName(), "handlePossibleCancellationInterrupt");
    }
}
