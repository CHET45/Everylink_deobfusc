package reactor.core.publisher;

/* JADX INFO: loaded from: classes5.dex */
public enum SignalType {
    SUBSCRIBE,
    REQUEST,
    CANCEL,
    ON_SUBSCRIBE,
    ON_NEXT,
    ON_ERROR,
    ON_COMPLETE,
    AFTER_TERMINATE,
    CURRENT_CONTEXT,
    ON_CONTEXT;

    @Override // java.lang.Enum
    public String toString() {
        switch (ordinal()) {
            case 1:
                return "request";
            case 2:
                return "cancel";
            case 3:
                return "onSubscribe";
            case 4:
                return "onNext";
            case 5:
                return "onError";
            case 6:
                return "onComplete";
            case 7:
                return "afterTerminate";
            case 8:
                return "currentContext";
            case 9:
                return "onContextUpdate";
            default:
                return "subscribe";
        }
    }
}
