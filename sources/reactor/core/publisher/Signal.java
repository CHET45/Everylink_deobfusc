package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
public interface Signal<T> extends Supplier<T>, Consumer<Subscriber<? super T>> {
    @Override // java.util.function.Supplier
    @Nullable
    T get();

    ContextView getContextView();

    @Nullable
    Subscription getSubscription();

    @Nullable
    Throwable getThrowable();

    SignalType getType();

    static <T> Signal<T> complete() {
        return ImmutableSignal.onComplete();
    }

    static <T> Signal<T> complete(Context context) {
        if (context.isEmpty()) {
            return ImmutableSignal.onComplete();
        }
        return new ImmutableSignal(context, SignalType.ON_COMPLETE, null, null, null);
    }

    static <T> Signal<T> error(Throwable th) {
        return error(th, Context.empty());
    }

    static <T> Signal<T> error(Throwable th, Context context) {
        return new ImmutableSignal(context, SignalType.ON_ERROR, null, th, null);
    }

    static <T> Signal<T> next(T t) {
        return next(t, Context.empty());
    }

    static <T> Signal<T> next(T t, Context context) {
        return new ImmutableSignal(context, SignalType.ON_NEXT, t, null, null);
    }

    static <T> Signal<T> subscribe(Subscription subscription) {
        return subscribe(subscription, Context.empty());
    }

    static <T> Signal<T> subscribe(Subscription subscription, Context context) {
        return new ImmutableSignal(context, SignalType.ON_SUBSCRIBE, null, null, subscription);
    }

    static boolean isComplete(Object obj) {
        return obj == ImmutableSignal.onComplete() || ((obj instanceof Signal) && ((Signal) obj).getType() == SignalType.ON_COMPLETE);
    }

    static boolean isError(Object obj) {
        return (obj instanceof Signal) && ((Signal) obj).getType() == SignalType.ON_ERROR;
    }

    default boolean hasValue() {
        return isOnNext() && get() != null;
    }

    default boolean hasError() {
        return isOnError() && getThrowable() != null;
    }

    default boolean isOnError() {
        return getType() == SignalType.ON_ERROR;
    }

    default boolean isOnComplete() {
        return getType() == SignalType.ON_COMPLETE;
    }

    default boolean isOnSubscribe() {
        return getType() == SignalType.ON_SUBSCRIBE;
    }

    default boolean isOnNext() {
        return getType() == SignalType.ON_NEXT;
    }

    @Override // java.util.function.Consumer
    default void accept(Subscriber<? super T> subscriber) {
        if (isOnNext()) {
            subscriber.onNext(get());
            return;
        }
        if (isOnComplete()) {
            subscriber.onComplete();
        } else if (isOnError()) {
            subscriber.onError(getThrowable());
        } else if (isOnSubscribe()) {
            subscriber.onSubscribe(getSubscription());
        }
    }
}
