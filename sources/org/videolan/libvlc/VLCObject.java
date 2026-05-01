package org.videolan.libvlc;

import android.os.Handler;
import android.os.Looper;
import org.videolan.libvlc.interfaces.AbstractVLCEvent;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.interfaces.IVLCObject;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: loaded from: classes5.dex */
public abstract class VLCObject<T extends AbstractVLCEvent> implements IVLCObject<T> {
    private AbstractVLCEvent.Listener<T> mEventListener;
    private Handler mHandler;
    final ILibVLC mILibVLC;
    private long mInstance;
    private int mNativeRefCount;

    private native void nativeDetachEvents();

    public native long getInstance();

    protected abstract T onEventNative(int i, long j, long j2, float f, String str);

    protected abstract void onReleaseNative();

    protected VLCObject(ILibVLC iLibVLC) {
        this.mEventListener = null;
        this.mHandler = null;
        this.mNativeRefCount = 1;
        this.mInstance = 0L;
        this.mILibVLC = iLibVLC;
    }

    protected VLCObject(IVLCObject iVLCObject) {
        this.mEventListener = null;
        this.mHandler = null;
        this.mNativeRefCount = 1;
        this.mInstance = 0L;
        this.mILibVLC = iVLCObject.getLibVLC();
    }

    protected VLCObject() {
        this.mEventListener = null;
        this.mHandler = null;
        this.mNativeRefCount = 1;
        this.mInstance = 0L;
        this.mILibVLC = null;
    }

    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public synchronized boolean isReleased() {
        return this.mNativeRefCount == 0;
    }

    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public final synchronized boolean retain() {
        int i = this.mNativeRefCount;
        if (i <= 0) {
            return false;
        }
        this.mNativeRefCount = i + 1;
        return true;
    }

    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public final void release() {
        int i;
        synchronized (this) {
            int i2 = this.mNativeRefCount;
            if (i2 == 0) {
                return;
            }
            if (i2 > 0) {
                i = i2 - 1;
                this.mNativeRefCount = i;
            } else {
                i = -1;
            }
            if (i == 0) {
                setEventListener(null);
            }
            if (i == 0) {
                nativeDetachEvents();
                synchronized (this) {
                    onReleaseNative();
                }
            }
        }
    }

    protected synchronized void finalize() {
        if (!isReleased()) {
            throw new AssertionError("VLCObject (" + getClass().getName() + ") finalized but not natively released (" + this.mNativeRefCount + " refs)");
        }
    }

    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public ILibVLC getLibVLC() {
        return this.mILibVLC;
    }

    protected synchronized void setEventListener(AbstractVLCEvent.Listener<T> listener) {
        setEventListener(listener, null);
    }

    protected synchronized void setEventListener(AbstractVLCEvent.Listener<T> listener, Handler handler) {
        Handler handler2 = this.mHandler;
        if (handler2 != null) {
            handler2.removeCallbacksAndMessages(null);
        }
        this.mEventListener = listener;
        if (listener == null) {
            this.mHandler = null;
        } else if (this.mHandler == null) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            this.mHandler = handler;
        }
    }

    private synchronized void dispatchEventFromNative(int i, long j, long j2, float f, String str) {
        Handler handler;
        if (isReleased()) {
            return;
        }
        AbstractVLCEvent abstractVLCEventOnEventNative = onEventNative(i, j, j2, f, str);
        if (abstractVLCEventOnEventNative != null && this.mEventListener != null && (handler = this.mHandler) != null) {
            handler.post(new Runnable(this.mEventListener, abstractVLCEventOnEventNative) { // from class: org.videolan.libvlc.VLCObject.1EventRunnable
                private final T event;
                private final AbstractVLCEvent.Listener<T> listener;

                {
                    this.listener = listener;
                    this.event = abstractVLCEventOnEventNative;
                }

                @Override // java.lang.Runnable
                public void run() {
                    this.listener.onEvent(this.event);
                    this.event.release();
                }
            });
        }
    }
}
