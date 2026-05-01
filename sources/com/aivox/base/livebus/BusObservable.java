package com.aivox.base.livebus;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/* JADX INFO: loaded from: classes.dex */
public interface BusObservable<T> {
    BusObservable<T> alwaysBeActive(boolean z);

    BusObservable<T> autoClear(boolean z);

    void observe(LifecycleOwner lifecycleOwner, Observer<T> observer);

    void observeForever(Observer<T> observer);

    void observeSticky(LifecycleOwner lifecycleOwner, Observer<T> observer);

    void observeStickyForever(Observer<T> observer);

    void post(T t);

    void postDelay(LifecycleOwner lifecycleOwner, T t, long j);

    void postDelay(T t, long j);

    void postOrderly(T t);

    void removeObserver(Observer<T> observer);
}
