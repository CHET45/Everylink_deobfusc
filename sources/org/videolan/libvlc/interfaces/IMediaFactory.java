package org.videolan.libvlc.interfaces;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import java.io.FileDescriptor;

/* JADX INFO: loaded from: classes5.dex */
public interface IMediaFactory extends IComponentFactory {
    public static final String factoryId = "org.videolan.libvlc.interfaces.IMediaFactory";

    IMedia getFromAssetFileDescriptor(ILibVLC iLibVLC, AssetFileDescriptor assetFileDescriptor);

    IMedia getFromFileDescriptor(ILibVLC iLibVLC, FileDescriptor fileDescriptor);

    IMedia getFromLocalPath(ILibVLC iLibVLC, String str);

    IMedia getFromUri(ILibVLC iLibVLC, Uri uri);
}
