package org.videolan.libvlc;

import android.os.Handler;
import android.util.SparseArray;
import org.videolan.libvlc.interfaces.AbstractVLCEvent;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.interfaces.IMedia;
import org.videolan.libvlc.interfaces.IMediaList;

/* JADX INFO: loaded from: classes5.dex */
public class MediaList extends VLCObject<IMediaList.Event> implements IMediaList {
    private static final String TAG = "LibVLC/MediaList";
    private int mCount;
    private boolean mLocked;
    private final SparseArray<IMedia> mMediaArray;

    private native int nativeGetCount();

    private native void nativeLock();

    private native void nativeNewFromLibVlc(ILibVLC iLibVLC);

    private native void nativeNewFromMedia(IMedia iMedia);

    private native void nativeNewFromMediaDiscoverer(MediaDiscoverer mediaDiscoverer);

    private native void nativeRelease();

    private native void nativeUnlock();

    @Override // org.videolan.libvlc.VLCObject
    public /* bridge */ /* synthetic */ long getInstance() {
        return super.getInstance();
    }

    @Override // org.videolan.libvlc.VLCObject, org.videolan.libvlc.interfaces.IVLCObject
    public /* bridge */ /* synthetic */ ILibVLC getLibVLC() {
        return super.getLibVLC();
    }

    @Override // org.videolan.libvlc.VLCObject, org.videolan.libvlc.interfaces.IVLCObject
    public /* bridge */ /* synthetic */ boolean isReleased() {
        return super.isReleased();
    }

    private void init() {
        lock();
        this.mCount = nativeGetCount();
        for (int i = 0; i < this.mCount; i++) {
            this.mMediaArray.put(i, new Media(this, i));
        }
        unlock();
    }

    public MediaList(ILibVLC iLibVLC) {
        super(iLibVLC);
        this.mCount = 0;
        this.mMediaArray = new SparseArray<>();
        this.mLocked = false;
        nativeNewFromLibVlc(iLibVLC);
        init();
    }

    protected MediaList(MediaDiscoverer mediaDiscoverer) {
        super(mediaDiscoverer);
        this.mCount = 0;
        this.mMediaArray = new SparseArray<>();
        this.mLocked = false;
        nativeNewFromMediaDiscoverer(mediaDiscoverer);
        init();
    }

    protected MediaList(IMedia iMedia) {
        super(iMedia);
        this.mCount = 0;
        this.mMediaArray = new SparseArray<>();
        this.mLocked = false;
        nativeNewFromMedia(iMedia);
        init();
    }

    private synchronized IMedia insertMediaFromEvent(int i) {
        Media media;
        for (int i2 = this.mCount - 1; i2 >= i; i2--) {
            SparseArray<IMedia> sparseArray = this.mMediaArray;
            sparseArray.put(i2 + 1, sparseArray.valueAt(i2));
        }
        this.mCount++;
        media = new Media(this, i);
        this.mMediaArray.put(i, media);
        return media;
    }

    private synchronized IMedia removeMediaFromEvent(int i) {
        IMedia iMedia;
        this.mCount--;
        iMedia = this.mMediaArray.get(i);
        if (iMedia != null) {
            iMedia.release();
        }
        while (i < this.mCount) {
            SparseArray<IMedia> sparseArray = this.mMediaArray;
            int i2 = i + 1;
            sparseArray.put(i, sparseArray.valueAt(i2));
            i = i2;
        }
        return iMedia;
    }

    @Override // org.videolan.libvlc.interfaces.IMediaList
    public void setEventListener(IMediaList.EventListener eventListener, Handler handler) {
        super.setEventListener((AbstractVLCEvent.Listener) eventListener, handler);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.videolan.libvlc.VLCObject
    public synchronized IMediaList.Event onEventNative(int i, long j, long j2, float f, String str) {
        IMediaList.Event event;
        if (this.mLocked) {
            throw new IllegalStateException("already locked from event callback");
        }
        this.mLocked = true;
        event = null;
        if (i == 512) {
            int i2 = (int) j;
            if (i2 != -1) {
                event = new IMediaList.Event(i, insertMediaFromEvent(i2), true, i2);
            }
        } else if (i == 514) {
            int i3 = (int) j;
            if (i3 != -1) {
                event = new IMediaList.Event(i, removeMediaFromEvent(i3), false, i3);
            }
        } else if (i == 516) {
            event = new IMediaList.Event(i, null, false, -1);
        }
        this.mLocked = false;
        return event;
    }

    @Override // org.videolan.libvlc.interfaces.IMediaList
    public synchronized int getCount() {
        return this.mCount;
    }

    @Override // org.videolan.libvlc.interfaces.IMediaList
    public synchronized IMedia getMediaAt(int i) {
        IMedia iMedia;
        if (i >= 0) {
            if (i < getCount()) {
                iMedia = this.mMediaArray.get(i);
                iMedia.retain();
            }
        }
        throw new IndexOutOfBoundsException();
        return iMedia;
    }

    @Override // org.videolan.libvlc.VLCObject
    public void onReleaseNative() {
        for (int i = 0; i < this.mMediaArray.size(); i++) {
            IMedia iMedia = this.mMediaArray.get(i);
            if (iMedia != null) {
                iMedia.release();
            }
        }
        nativeRelease();
    }

    private synchronized void lock() {
        if (this.mLocked) {
            throw new IllegalStateException("already locked");
        }
        this.mLocked = true;
        nativeLock();
    }

    private synchronized void unlock() {
        if (!this.mLocked) {
            throw new IllegalStateException("not locked");
        }
        this.mLocked = false;
        nativeUnlock();
    }

    @Override // org.videolan.libvlc.interfaces.IMediaList
    public synchronized boolean isLocked() {
        return this.mLocked;
    }
}
