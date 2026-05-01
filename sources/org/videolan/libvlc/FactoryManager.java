package org.videolan.libvlc;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import org.videolan.libvlc.interfaces.IComponentFactory;
import org.videolan.libvlc.interfaces.ILibVLCFactory;
import org.videolan.libvlc.interfaces.IMediaFactory;

/* JADX INFO: loaded from: classes5.dex */
public class FactoryManager {
    private static Map<String, IComponentFactory> factories = new HashMap();

    public static void registerFactory(String str, IComponentFactory iComponentFactory) {
        factories.put(str, iComponentFactory);
    }

    public static IComponentFactory getFactory(String str) {
        IComponentFactory iComponentFactory = factories.get(str);
        if (iComponentFactory != null) {
            return iComponentFactory;
        }
        Log.e("FactoryManager", "Factory doesn't exist. Falling back to hard coded one");
        if (str.equals(IMediaFactory.factoryId)) {
            registerFactory(IMediaFactory.factoryId, new MediaFactory());
        }
        if (str.equals(ILibVLCFactory.factoryId)) {
            registerFactory(ILibVLCFactory.factoryId, new LibVLCFactory());
        }
        return factories.get(str);
    }
}
