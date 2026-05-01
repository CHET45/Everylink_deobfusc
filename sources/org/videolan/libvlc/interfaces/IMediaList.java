package org.videolan.libvlc.interfaces;

import android.os.Handler;
import org.videolan.libvlc.interfaces.AbstractVLCEvent;

/* JADX INFO: loaded from: classes5.dex */
public interface IMediaList extends IVLCObject<Event> {

    public interface EventListener extends AbstractVLCEvent.Listener<Event> {
    }

    int getCount();

    IMedia getMediaAt(int i);

    boolean isLocked();

    void setEventListener(EventListener eventListener, Handler handler);

    public static class Event extends AbstractVLCEvent {
        public static final int EndReached = 516;
        public static final int ItemAdded = 512;
        public static final int ItemDeleted = 514;
        public final int index;
        public final IMedia media;
        private final boolean retain;

        public Event(int i, IMedia iMedia, boolean z, int i2) {
            super(i);
            if (z && (iMedia == null || !iMedia.retain())) {
                throw new IllegalStateException("invalid media reference");
            }
            this.media = iMedia;
            this.retain = z;
            this.index = i2;
        }

        @Override // org.videolan.libvlc.interfaces.AbstractVLCEvent
        public void release() {
            if (this.retain) {
                this.media.release();
            }
        }
    }
}
