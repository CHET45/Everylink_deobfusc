package reactor.core.publisher;

import java.io.Serializable;
import java.util.Objects;
import org.reactivestreams.Subscription;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

/* JADX INFO: loaded from: classes5.dex */
final class ImmutableSignal<T> implements Signal<T>, Serializable {
    private static final Signal<?> ON_COMPLETE = new ImmutableSignal(Context.empty(), SignalType.ON_COMPLETE, null, null, null);
    private static final long serialVersionUID = -2004454746525418508L;
    private final transient ContextView contextView;
    private final transient Subscription subscription;
    private final Throwable throwable;
    private final SignalType type;
    private final T value;

    ImmutableSignal(ContextView contextView, SignalType signalType, @Nullable T t, @Nullable Throwable th, @Nullable Subscription subscription) {
        this.contextView = contextView;
        this.value = t;
        this.subscription = subscription;
        this.throwable = th;
        this.type = signalType;
    }

    @Override // reactor.core.publisher.Signal
    @Nullable
    public Throwable getThrowable() {
        return this.throwable;
    }

    @Override // reactor.core.publisher.Signal
    @Nullable
    public Subscription getSubscription() {
        return this.subscription;
    }

    @Override // reactor.core.publisher.Signal, java.util.function.Supplier
    @Nullable
    public T get() {
        return this.value;
    }

    @Override // reactor.core.publisher.Signal
    public SignalType getType() {
        return this.type;
    }

    @Override // reactor.core.publisher.Signal
    public ContextView getContextView() {
        return this.contextView;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof Signal)) {
            Signal signal = (Signal) obj;
            if (getType() != signal.getType()) {
                return false;
            }
            if (isOnComplete()) {
                return true;
            }
            if (isOnSubscribe()) {
                return Objects.equals(getSubscription(), signal.getSubscription());
            }
            if (isOnError()) {
                return Objects.equals(getThrowable(), signal.getThrowable());
            }
            if (isOnNext()) {
                return Objects.equals(get(), signal.get());
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = getType().hashCode();
        if (isOnError()) {
            return (iHashCode * 31) + (getThrowable() != null ? getThrowable().hashCode() : 0);
        }
        if (isOnNext()) {
            return (iHashCode * 31) + (get() != null ? get().hashCode() : 0);
        }
        if (isOnSubscribe()) {
            return (iHashCode * 31) + (getSubscription() != null ? getSubscription().hashCode() : 0);
        }
        return iHashCode;
    }

    /* JADX INFO: renamed from: reactor.core.publisher.ImmutableSignal$1 */
    static /* synthetic */ class C51501 {
        static final /* synthetic */ int[] $SwitchMap$reactor$core$publisher$SignalType;

        static {
            int[] iArr = new int[SignalType.values().length];
            $SwitchMap$reactor$core$publisher$SignalType = iArr;
            try {
                iArr[SignalType.ON_SUBSCRIBE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$reactor$core$publisher$SignalType[SignalType.ON_NEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$reactor$core$publisher$SignalType[SignalType.ON_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$reactor$core$publisher$SignalType[SignalType.ON_COMPLETE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public String toString() {
        int i = C51501.$SwitchMap$reactor$core$publisher$SignalType[getType().ordinal()];
        if (i == 1) {
            return String.format("onSubscribe(%s)", getSubscription());
        }
        if (i == 2) {
            return String.format("onNext(%s)", get());
        }
        if (i == 3) {
            return String.format("onError(%s)", getThrowable());
        }
        if (i == 4) {
            return "onComplete()";
        }
        return String.format("Signal type=%s", getType());
    }

    static <U> Signal<U> onComplete() {
        return (Signal<U>) ON_COMPLETE;
    }
}
