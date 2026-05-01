package com.aivox.base.util;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.github.houbb.heaven.constant.MethodConst;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LiveDataBus {
    private final Map<String, BusMutableLiveData<Object>> bus;

    private LiveDataBus() {
        this.bus = new HashMap();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();

        private SingletonHolder() {
        }
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    public <T> MutableLiveData<T> with(String str, Class<T> cls) {
        if (!this.bus.containsKey(str)) {
            this.bus.put(str, new BusMutableLiveData<>());
        }
        return this.bus.get(str);
    }

    public MutableLiveData<Object> with(String str) {
        return with(str, Object.class);
    }

    private static class ObserverWrapper<T> implements Observer<T> {
        private Observer<T> observer;

        public ObserverWrapper(Observer<T> observer) {
            this.observer = observer;
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(T t) {
            if (this.observer == null || isCallOnObserve()) {
                return;
            }
            this.observer.onChanged(t);
        }

        private boolean isCallOnObserve() {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if ("android.arch.lifecycle.LiveData".equals(stackTraceElement.getClassName()) && "observeForever".equals(stackTraceElement.getMethodName())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static class BusMutableLiveData<T> extends MutableLiveData<T> {
        private Map<Observer, Observer> observerMap;

        private BusMutableLiveData() {
            this.observerMap = new HashMap();
        }

        @Override // androidx.lifecycle.LiveData
        public void observe(LifecycleOwner lifecycleOwner, Observer<? super T> observer) {
            super.observe(lifecycleOwner, observer);
            try {
                hook(observer);
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
            }
        }

        @Override // androidx.lifecycle.LiveData
        public void observeForever(Observer<? super T> observer) {
            if (!this.observerMap.containsKey(observer)) {
                this.observerMap.put(observer, new ObserverWrapper(observer));
            }
            super.observeForever(this.observerMap.get(observer));
        }

        @Override // androidx.lifecycle.LiveData
        public void removeObserver(Observer<? super T> observer) {
            if (this.observerMap.containsKey(observer)) {
                observer = this.observerMap.remove(observer);
            }
            super.removeObserver(observer);
        }

        private void hook(Observer<? super T> observer) throws Exception {
            Field declaredField = LiveData.class.getDeclaredField("mObservers");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this);
            Method declaredMethod = obj.getClass().getDeclaredMethod(MethodConst.GET_PREFIX, Object.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(obj, observer);
            Object value = objInvoke instanceof Map.Entry ? ((Map.Entry) objInvoke).getValue() : null;
            if (value == null) {
                throw new NullPointerException("Wrapper can not be bull!");
            }
            Field declaredField2 = value.getClass().getSuperclass().getDeclaredField("mLastVersion");
            declaredField2.setAccessible(true);
            Field declaredField3 = LiveData.class.getDeclaredField("mVersion");
            declaredField3.setAccessible(true);
            declaredField2.set(value, declaredField3.get(this));
        }
    }
}
