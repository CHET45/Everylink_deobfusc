package org.videolan.libvlc;

import android.content.Context;
import java.util.List;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.interfaces.ILibVLCFactory;

/* JADX INFO: loaded from: classes5.dex */
public class LibVLCFactory implements ILibVLCFactory {
    static {
        FactoryManager.registerFactory(ILibVLCFactory.factoryId, new LibVLCFactory());
    }

    @Override // org.videolan.libvlc.interfaces.ILibVLCFactory
    public ILibVLC getFromOptions(Context context, List<String> list) {
        return new LibVLC(context, list);
    }

    @Override // org.videolan.libvlc.interfaces.ILibVLCFactory
    public ILibVLC getFromContext(Context context) {
        return new LibVLC(context, null);
    }
}
