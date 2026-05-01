package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

/* JADX INFO: loaded from: classes5.dex */
interface SignalPeek<T> extends Scannable {
    @Nullable
    default Consumer<? super T> onAfterNextCall() {
        return null;
    }

    @Nullable
    Runnable onAfterTerminateCall();

    @Nullable
    Runnable onCancelCall();

    @Nullable
    Runnable onCompleteCall();

    @Nullable
    default Consumer<? super Context> onCurrentContextCall() {
        return null;
    }

    @Nullable
    Consumer<? super Throwable> onErrorCall();

    @Nullable
    Consumer<? super T> onNextCall();

    @Nullable
    LongConsumer onRequestCall();

    @Nullable
    Consumer<? super Subscription> onSubscribeCall();
}
