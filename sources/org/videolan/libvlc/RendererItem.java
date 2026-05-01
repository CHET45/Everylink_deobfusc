package org.videolan.libvlc;

import org.videolan.libvlc.interfaces.AbstractVLCEvent;
import org.videolan.libvlc.interfaces.ILibVLC;

/* JADX INFO: loaded from: classes5.dex */
public class RendererItem extends VLCObject<Event> {
    public static final int LIBVLC_RENDERER_CAN_AUDIO = 1;
    public static final int LIBVLC_RENDERER_CAN_VIDEO = 2;
    public final String displayName;
    final int flags;
    final String iconUrl;
    public final String name;
    private final long ref;
    public final String type;

    private native void nativeReleaseItem();

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

    RendererItem(String str, String str2, String str3, int i, long j) {
        int iLastIndexOf = str.lastIndexOf(45);
        this.name = str;
        this.displayName = iLastIndexOf != -1 ? str.replace('-', ' ') : str;
        this.type = str2;
        this.iconUrl = str3;
        this.flags = i;
        this.ref = j;
    }

    public boolean equals(Object obj) {
        return (obj instanceof RendererItem) && this.ref == ((RendererItem) obj).ref;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.videolan.libvlc.VLCObject
    public Event onEventNative(int i, long j, long j2, float f, String str) {
        return new Event(i);
    }

    @Override // org.videolan.libvlc.VLCObject
    protected void onReleaseNative() {
        nativeReleaseItem();
    }

    public static class Event extends AbstractVLCEvent {
        protected Event(int i) {
            super(i);
        }
    }
}
