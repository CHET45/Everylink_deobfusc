package org.videolan.libvlc.stubs;

import org.videolan.libvlc.interfaces.AbstractVLCEvent;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.interfaces.IVLCObject;

/* JADX INFO: loaded from: classes5.dex */
public class StubVLCObject<T extends AbstractVLCEvent> implements IVLCObject<T> {
    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public ILibVLC getLibVLC() {
        return null;
    }

    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public boolean isReleased() {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public void release() {
    }

    @Override // org.videolan.libvlc.interfaces.IVLCObject
    public boolean retain() {
        return false;
    }
}
