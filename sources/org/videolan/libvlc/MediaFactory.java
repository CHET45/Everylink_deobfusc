package org.videolan.libvlc;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import java.io.FileDescriptor;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.interfaces.IMedia;
import org.videolan.libvlc.interfaces.IMediaFactory;

/* JADX INFO: loaded from: classes5.dex */
public class MediaFactory implements IMediaFactory {
    @Override // org.videolan.libvlc.interfaces.IMediaFactory
    public IMedia getFromLocalPath(ILibVLC iLibVLC, String str) {
        return new Media(iLibVLC, str);
    }

    @Override // org.videolan.libvlc.interfaces.IMediaFactory
    public IMedia getFromUri(ILibVLC iLibVLC, Uri uri) {
        return new Media(iLibVLC, uri);
    }

    @Override // org.videolan.libvlc.interfaces.IMediaFactory
    public IMedia getFromFileDescriptor(ILibVLC iLibVLC, FileDescriptor fileDescriptor) {
        return new Media(iLibVLC, fileDescriptor);
    }

    @Override // org.videolan.libvlc.interfaces.IMediaFactory
    public IMedia getFromAssetFileDescriptor(ILibVLC iLibVLC, AssetFileDescriptor assetFileDescriptor) {
        return new Media(iLibVLC, assetFileDescriptor);
    }
}
