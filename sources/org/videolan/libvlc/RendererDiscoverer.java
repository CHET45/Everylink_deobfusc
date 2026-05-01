package org.videolan.libvlc;

import androidx.collection.LongSparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.videolan.libvlc.interfaces.AbstractVLCEvent;
import org.videolan.libvlc.interfaces.ILibVLC;

/* JADX INFO: loaded from: classes5.dex */
public class RendererDiscoverer extends VLCObject<Event> {
    private static final String TAG = "LibVLC/RendererDiscoverer";
    private final LongSparseArray<RendererItem> index;
    final List<RendererItem> mRenderers;

    public interface EventListener extends AbstractVLCEvent.Listener<Event> {
    }

    private static native Description[] nativeList(ILibVLC iLibVLC);

    private native void nativeNew(ILibVLC iLibVLC, String str);

    private native RendererItem nativeNewItem(long j);

    private native void nativeRelease();

    private native boolean nativeStart();

    private native void nativeStop();

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

    public static class Event extends AbstractVLCEvent {
        public static final int ItemAdded = 1282;
        public static final int ItemDeleted = 1283;
        private final RendererItem item;

        protected Event(int i, long j, RendererItem rendererItem) {
            super(i, j);
            this.item = rendererItem;
            rendererItem.retain();
        }

        public RendererItem getItem() {
            return this.item;
        }

        @Override // org.videolan.libvlc.interfaces.AbstractVLCEvent
        public void release() {
            this.item.release();
            super.release();
        }
    }

    private static RendererItem createItemFromNative(String str, String str2, String str3, int i, long j) {
        return new RendererItem(str, str2, str3, i, j);
    }

    public RendererDiscoverer(ILibVLC iLibVLC, String str) {
        super(iLibVLC);
        this.mRenderers = new ArrayList();
        this.index = new LongSparseArray<>();
        nativeNew(iLibVLC, str);
    }

    public boolean start() {
        if (isReleased()) {
            throw new IllegalStateException("MediaDiscoverer is released");
        }
        return nativeStart();
    }

    public void stop() {
        if (isReleased()) {
            throw new IllegalStateException("MediaDiscoverer is released");
        }
        setEventListener((EventListener) null);
        nativeStop();
        release();
    }

    public void setEventListener(EventListener eventListener) {
        super.setEventListener((AbstractVLCEvent.Listener) eventListener);
    }

    public static Description[] list(ILibVLC iLibVLC) {
        return nativeList(iLibVLC);
    }

    public static class Description {
        final String longName;
        public final String name;

        private Description(String str, String str2) {
            this.name = str;
            this.longName = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.videolan.libvlc.VLCObject
    public Event onEventNative(int i, long j, long j2, float f, String str) {
        if (i == 1282) {
            return new Event(i, j, insertItemFromEvent(j));
        }
        if (i != 1283) {
            return null;
        }
        return new Event(i, j, removeItemFromEvent(j));
    }

    private synchronized RendererItem insertItemFromEvent(long j) {
        RendererItem rendererItemNativeNewItem;
        rendererItemNativeNewItem = nativeNewItem(j);
        this.index.put(j, rendererItemNativeNewItem);
        this.mRenderers.add(rendererItemNativeNewItem);
        return rendererItemNativeNewItem;
    }

    private synchronized RendererItem removeItemFromEvent(long j) {
        RendererItem rendererItem;
        rendererItem = this.index.get(j);
        if (rendererItem != null) {
            this.index.remove(j);
            this.mRenderers.remove(rendererItem);
            rendererItem.release();
        }
        return rendererItem;
    }

    @Override // org.videolan.libvlc.VLCObject
    protected void onReleaseNative() {
        Iterator<RendererItem> it = this.mRenderers.iterator();
        while (it.hasNext()) {
            it.next().release();
        }
        this.mRenderers.clear();
        nativeRelease();
    }

    private static Description createDescriptionFromNative(String str, String str2) {
        return new Description(str, str2);
    }
}
