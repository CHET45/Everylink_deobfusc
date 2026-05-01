package reactor.core.publisher;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeek;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
final class ParallelDoOnEach<T> extends ParallelFlux<T> implements Scannable {
    final Consumer<Context> onComplete;
    final BiConsumer<Context, ? super Throwable> onError;
    final BiConsumer<Context, ? super T> onNext;
    final ParallelFlux<T> source;

    ParallelDoOnEach(ParallelFlux<T> parallelFlux, @Nullable BiConsumer<Context, ? super T> biConsumer, @Nullable BiConsumer<Context, ? super Throwable> biConsumer2, @Nullable Consumer<Context> consumer) {
        this.source = ParallelFlux.from((ParallelFlux) parallelFlux);
        this.onNext = biConsumer;
        this.onError = biConsumer2;
        this.onComplete = consumer;
    }

    @Override // reactor.core.publisher.ParallelFlux
    public void subscribe(CoreSubscriber<? super T>[] coreSubscriberArr) {
        if (validate(coreSubscriberArr)) {
            int length = coreSubscriberArr.length;
            CoreSubscriber<? super T>[] coreSubscriberArr2 = new CoreSubscriber[length];
            boolean z = coreSubscriberArr[0] instanceof Fuseable.ConditionalSubscriber;
            for (int i = 0; i < length; i++) {
                CoreSubscriber<? super T> coreSubscriber = coreSubscriberArr[i];
                DoOnEachSignalPeek doOnEachSignalPeek = new DoOnEachSignalPeek(coreSubscriber.currentContext());
                if (z) {
                    coreSubscriberArr2[i] = new FluxPeekFuseable.PeekConditionalSubscriber((Fuseable.ConditionalSubscriber) coreSubscriber, doOnEachSignalPeek);
                } else {
                    coreSubscriberArr2[i] = new FluxPeek.PeekSubscriber(coreSubscriber, doOnEachSignalPeek);
                }
            }
            this.source.subscribe(coreSubscriberArr2);
        }
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override // reactor.core.publisher.ParallelFlux
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override // reactor.core.Scannable
    @Nullable
    public Object scanUnsafe(Scannable.Attr attr) {
        return attr == Scannable.Attr.PARENT ? this.source : attr == Scannable.Attr.PREFETCH ? Integer.valueOf(getPrefetch()) : attr == Scannable.Attr.RUN_STYLE ? Scannable.Attr.RunStyle.SYNC : attr == InternalProducerAttr.INSTANCE ? true : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DoOnEachSignalPeek implements SignalPeek<T> {
        Runnable onCompleteCall;
        Consumer<? super Throwable> onErrorCall;
        Consumer<? super T> onNextCall;

        @Override // reactor.core.publisher.SignalPeek
        public Runnable onAfterTerminateCall() {
            return null;
        }

        @Override // reactor.core.publisher.SignalPeek
        public Runnable onCancelCall() {
            return null;
        }

        @Override // reactor.core.publisher.SignalPeek
        public LongConsumer onRequestCall() {
            return null;
        }

        @Override // reactor.core.publisher.SignalPeek
        public Consumer<? super Subscription> onSubscribeCall() {
            return null;
        }

        @Override // reactor.core.Scannable
        public Object scanUnsafe(Scannable.Attr attr) {
            return null;
        }

        public DoOnEachSignalPeek(final Context context) {
            this.onNextCall = ParallelDoOnEach.this.onNext != null ? new Consumer() { // from class: reactor.core.publisher.ParallelDoOnEach$DoOnEachSignalPeek$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m1970x449fff01(context, obj);
                }
            } : null;
            this.onErrorCall = ParallelDoOnEach.this.onError != null ? new Consumer() { // from class: reactor.core.publisher.ParallelDoOnEach$DoOnEachSignalPeek$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m1971x4aa3ca60(context, (Throwable) obj);
                }
            } : null;
            this.onCompleteCall = ParallelDoOnEach.this.onComplete != null ? new Runnable() { // from class: reactor.core.publisher.ParallelDoOnEach$DoOnEachSignalPeek$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m1972x50a795bf(context);
                }
            } : null;
        }

        /* JADX INFO: renamed from: lambda$new$0$reactor-core-publisher-ParallelDoOnEach$DoOnEachSignalPeek */
        /* synthetic */ void m1970x449fff01(Context context, Object obj) {
            ParallelDoOnEach.this.onNext.accept(context, obj);
        }

        /* JADX INFO: renamed from: lambda$new$1$reactor-core-publisher-ParallelDoOnEach$DoOnEachSignalPeek */
        /* synthetic */ void m1971x4aa3ca60(Context context, Throwable th) {
            ParallelDoOnEach.this.onError.accept(context, th);
        }

        /* JADX INFO: renamed from: lambda$new$2$reactor-core-publisher-ParallelDoOnEach$DoOnEachSignalPeek */
        /* synthetic */ void m1972x50a795bf(Context context) {
            ParallelDoOnEach.this.onComplete.accept(context);
        }

        @Override // reactor.core.publisher.SignalPeek
        public Consumer<? super T> onNextCall() {
            return this.onNextCall;
        }

        @Override // reactor.core.publisher.SignalPeek
        public Consumer<? super Throwable> onErrorCall() {
            return this.onErrorCall;
        }

        @Override // reactor.core.publisher.SignalPeek
        public Runnable onCompleteCall() {
            return this.onCompleteCall;
        }
    }
}
