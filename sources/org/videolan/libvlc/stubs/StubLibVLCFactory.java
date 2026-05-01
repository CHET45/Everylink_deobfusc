package org.videolan.libvlc.stubs;

import android.content.Context;
import java.util.List;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.interfaces.ILibVLCFactory;

/* JADX INFO: loaded from: classes5.dex */
public class StubLibVLCFactory implements ILibVLCFactory {
    @Override // org.videolan.libvlc.interfaces.ILibVLCFactory
    public ILibVLC getFromOptions(Context context, List<String> list) {
        return new StubLibVLC(context, list);
    }

    @Override // org.videolan.libvlc.interfaces.ILibVLCFactory
    public ILibVLC getFromContext(Context context) {
        return new StubLibVLC(context);
    }
}
