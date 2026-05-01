package org.videolan.libvlc.stubs;

import android.os.Handler;
import org.videolan.libvlc.interfaces.IMedia;
import org.videolan.libvlc.interfaces.IMediaList;

/* JADX INFO: loaded from: classes5.dex */
public class StubMediaList extends StubVLCObject<IMediaList.Event> implements IMediaList {
    @Override // org.videolan.libvlc.interfaces.IMediaList
    public int getCount() {
        return 0;
    }

    @Override // org.videolan.libvlc.interfaces.IMediaList
    public IMedia getMediaAt(int i) {
        return null;
    }

    @Override // org.videolan.libvlc.interfaces.IMediaList
    public boolean isLocked() {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IMediaList
    public void setEventListener(IMediaList.EventListener eventListener, Handler handler) {
    }
}
