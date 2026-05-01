package com.aivox.base.livebus;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.XLiveData;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LiveBusCore {
    private Map<Object, LiveEvent> mBus;

    public static LiveBusCore getInstance() {
        return Holder.DEFAULT_BUS;
    }

    private static class Holder {
        private static final LiveBusCore DEFAULT_BUS = new LiveBusCore();

        private Holder() {
        }
    }

    private LiveBusCore() {
        this.mBus = new HashMap();
    }

    public synchronized <T> BusObservable<T> with(Object obj, Class<T> cls) {
        if (!this.mBus.containsKey(obj)) {
            this.mBus.put(obj, new LiveEvent(obj));
        }
        return this.mBus.get(obj);
    }

    public synchronized void removeEvent(Object obj) {
        if (!this.mBus.containsKey(obj)) {
            this.mBus.remove(obj);
        }
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private class LiveEvent<T> implements BusObservable<T> {
        private final Object key;
        private final LiveEvent<T>.InternalLiveData<T> liveData = new InternalLiveData<>();
        private final Map<Observer, ObserverWrapper<T>> observerMap = new HashMap();
        private final Handler mainHandler = new Handler(Looper.getMainLooper());

        public LiveEvent(Object obj) {
            this.key = obj;
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void post(T t) {
            if (LiveBusCore.this.isMainThread()) {
                postInternal(t);
            } else {
                this.mainHandler.post(new PostValueTask(t));
            }
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void postDelay(T t, long j) {
            this.mainHandler.postDelayed(new PostValueTask(t), j);
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void postDelay(LifecycleOwner lifecycleOwner, T t, long j) {
            this.mainHandler.postDelayed(new PostLifeValueTask(t, lifecycleOwner), j);
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void postOrderly(T t) {
            this.mainHandler.post(new PostValueTask(t));
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void observe(final LifecycleOwner lifecycleOwner, final Observer<T> observer) {
            if (LiveBusCore.this.isMainThread()) {
                observeInternal(lifecycleOwner, observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.aivox.base.livebus.LiveBusCore.LiveEvent.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeInternal(lifecycleOwner, observer);
                    }
                });
            }
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void observeSticky(final LifecycleOwner lifecycleOwner, final Observer<T> observer) {
            if (LiveBusCore.this.isMainThread()) {
                observeStickyInternal(lifecycleOwner, observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.aivox.base.livebus.LiveBusCore.LiveEvent.2
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeStickyInternal(lifecycleOwner, observer);
                    }
                });
            }
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void observeForever(final Observer<T> observer) {
            if (LiveBusCore.this.isMainThread()) {
                observeForeverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.aivox.base.livebus.LiveBusCore.LiveEvent.3
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeForeverInternal(observer);
                    }
                });
            }
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void observeStickyForever(final Observer<T> observer) {
            if (LiveBusCore.this.isMainThread()) {
                observeStickyForeverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.aivox.base.livebus.LiveBusCore.LiveEvent.4
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.observeStickyForeverInternal(observer);
                    }
                });
            }
        }

        @Override // com.aivox.base.livebus.BusObservable
        public void removeObserver(final Observer<T> observer) {
            if (LiveBusCore.this.isMainThread()) {
                removeObserverInternal(observer);
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.aivox.base.livebus.LiveBusCore.LiveEvent.5
                    @Override // java.lang.Runnable
                    public void run() {
                        LiveEvent.this.removeObserverInternal(observer);
                    }
                });
            }
        }

        @Override // com.aivox.base.livebus.BusObservable
        public BusObservable<T> alwaysBeActive(boolean z) {
            ((InternalLiveData) this.liveData).observerAlwaysBeActive = z;
            return this;
        }

        @Override // com.aivox.base.livebus.BusObservable
        public BusObservable<T> autoClear(boolean z) {
            ((InternalLiveData) this.liveData).autoClear = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void postInternal(T t) {
            this.liveData.setValue(t);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void observeInternal(LifecycleOwner lifecycleOwner, Observer<T> observer) {
            ObserverWrapper observerWrapper = new ObserverWrapper(observer);
            observerWrapper.isRejectEvent = this.liveData.getVersion() > -1;
            this.liveData.observe(lifecycleOwner, observerWrapper);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void observeStickyInternal(LifecycleOwner lifecycleOwner, Observer<T> observer) {
            this.liveData.observe(lifecycleOwner, new ObserverWrapper(observer));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void observeForeverInternal(Observer<T> observer) {
            ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer);
            ((ObserverWrapper) observerWrapper).isRejectEvent = this.liveData.getVersion() > -1;
            this.observerMap.put(observer, observerWrapper);
            this.liveData.observeForever(observerWrapper);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void observeStickyForeverInternal(Observer<T> observer) {
            ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer);
            this.observerMap.put(observer, observerWrapper);
            this.liveData.observeForever(observerWrapper);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeObserverInternal(Observer<T> observer) {
            if (this.observerMap.containsKey(observer)) {
                observer = this.observerMap.remove(observer);
            }
            this.liveData.removeObserver(observer);
        }

        private class InternalLiveData<T> extends XLiveData<T> {
            private boolean autoClear;
            private boolean observerAlwaysBeActive;

            private InternalLiveData() {
                this.observerAlwaysBeActive = false;
                this.autoClear = true;
            }

            @Override // androidx.lifecycle.XLiveData
            protected Lifecycle.State observerActiveLevel() {
                return this.observerAlwaysBeActive ? Lifecycle.State.CREATED : Lifecycle.State.STARTED;
            }

            @Override // androidx.lifecycle.LiveData
            public void removeObserver(Observer<? super T> observer) {
                super.removeObserver(observer);
                if (!this.autoClear || LiveEvent.this.liveData.hasObservers()) {
                    return;
                }
                LiveBusCore.getInstance().mBus.remove(LiveEvent.this.key);
            }
        }

        private class PostValueTask implements Runnable {
            private Object newValue;

            public PostValueTask(Object obj) {
                this.newValue = obj;
            }

            @Override // java.lang.Runnable
            public void run() {
                LiveEvent.this.postInternal(this.newValue);
            }
        }

        private class PostLifeValueTask implements Runnable {
            private Object newValue;
            private LifecycleOwner owner;

            public PostLifeValueTask(Object obj, LifecycleOwner lifecycleOwner) {
                this.newValue = obj;
                this.owner = lifecycleOwner;
            }

            @Override // java.lang.Runnable
            public void run() {
                LifecycleOwner lifecycleOwner = this.owner;
                if (lifecycleOwner == null || !lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    return;
                }
                LiveEvent.this.postInternal(this.newValue);
            }
        }
    }

    private class ObserverWrapper<T> implements Observer<T> {
        private boolean isRejectEvent;
        private Observer<T> observer;

        private ObserverWrapper(Observer<T> observer) {
            this.isRejectEvent = false;
            this.observer = observer;
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(T t) {
            if (this.isRejectEvent) {
                this.isRejectEvent = false;
            } else {
                this.observer.onChanged(t);
            }
        }
    }
}
