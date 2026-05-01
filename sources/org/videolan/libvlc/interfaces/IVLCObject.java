package org.videolan.libvlc.interfaces;

import org.videolan.libvlc.interfaces.AbstractVLCEvent;

/* JADX INFO: loaded from: classes5.dex */
public interface IVLCObject<T extends AbstractVLCEvent> {
    ILibVLC getLibVLC();

    boolean isReleased();

    void release();

    boolean retain();
}
