package com.aivox.base.livebus;

/* JADX INFO: loaded from: classes.dex */
public final class LiveBus {
    public static <T> BusObservable<T> get(Object obj, Class<T> cls) {
        return LiveBusCore.getInstance().with(obj, cls);
    }

    public static <T> BusObservable<T> get(Class<T> cls) {
        return LiveBusCore.getInstance().with(cls.getName(), cls);
    }

    public static BusObservable<Object> get(Object obj) {
        return LiveBusCore.getInstance().with(obj, Object.class);
    }

    public static void remove(Object obj) {
        LiveBusCore.getInstance().removeEvent(obj);
    }
}
