package org.videolan.libvlc.stubs;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import java.io.FileDescriptor;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.interfaces.IMedia;
import org.videolan.libvlc.interfaces.IMediaList;

/* JADX INFO: loaded from: classes5.dex */
public class StubMedia extends StubVLCObject<IMedia.Event> implements IMedia {
    private ILibVLC mILibVLC;
    private int mType;
    private Uri mUri;

    @Override // org.videolan.libvlc.interfaces.IMedia
    public void addOption(String str) {
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public void addSlave(IMedia.Slave slave) {
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public void clearSlaves() {
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public long getDuration() {
        return 0L;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public int getState() {
        return 0;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public IMedia.Stats getStats() {
        return null;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public IMedia.Track getTrack(int i) {
        return null;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public int getTrackCount() {
        return 0;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public boolean isParsed() {
        return false;
    }

    @Override // org.videolan.libvlc.stubs.StubVLCObject, org.videolan.libvlc.interfaces.IVLCObject
    public boolean isReleased() {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public boolean parse() {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public boolean parse(int i) {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public boolean parseAsync() {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public boolean parseAsync(int i) {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public boolean parseAsync(int i, int i2) {
        return false;
    }

    @Override // org.videolan.libvlc.stubs.StubVLCObject, org.videolan.libvlc.interfaces.IVLCObject
    public void release() {
    }

    @Override // org.videolan.libvlc.stubs.StubVLCObject, org.videolan.libvlc.interfaces.IVLCObject
    public boolean retain() {
        return false;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public void setDefaultMediaPlayerOptions() {
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public void setEventListener(IMedia.EventListener eventListener) {
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public void setHWDecoderEnabled(boolean z, boolean z2) {
    }

    public StubMedia(ILibVLC iLibVLC, String str) {
        this(iLibVLC, Uri.parse(str));
    }

    public StubMedia(ILibVLC iLibVLC, Uri uri) {
        this.mType = 0;
        this.mUri = uri;
        this.mILibVLC = iLibVLC;
    }

    public StubMedia(ILibVLC iLibVLC, FileDescriptor fileDescriptor) {
        this.mType = 0;
        this.mILibVLC = iLibVLC;
    }

    public StubMedia(ILibVLC iLibVLC, AssetFileDescriptor assetFileDescriptor) {
        this.mType = 0;
        this.mILibVLC = iLibVLC;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public IMediaList subItems() {
        return new StubMediaList();
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public int getType() {
        return this.mType;
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public String getMeta(int i) {
        Uri uri = this.mUri;
        if (uri == null) {
            return null;
        }
        if (i == 0) {
            return getTitle();
        }
        if (i != 10) {
            return null;
        }
        return uri.getPath();
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public String getMeta(int i, boolean z) {
        return getMeta(i);
    }

    private String getTitle() {
        if ("file".equals(this.mUri.getScheme())) {
            return this.mUri.getLastPathSegment();
        }
        return this.mUri.getPath();
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public IMedia.Slave[] getSlaves() {
        return new IMedia.Slave[0];
    }

    @Override // org.videolan.libvlc.interfaces.IMedia
    public Uri getUri() {
        return this.mUri;
    }

    @Override // org.videolan.libvlc.stubs.StubVLCObject, org.videolan.libvlc.interfaces.IVLCObject
    public ILibVLC getLibVLC() {
        return this.mILibVLC;
    }

    public void setType(int i) {
        this.mType = i;
    }
}
