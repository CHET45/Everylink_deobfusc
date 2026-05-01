package com.aivox.base.img.imageloader;

/* JADX INFO: loaded from: classes.dex */
public class ImageLoaderFactory {
    private static ImageLoaderWrapper sInstance;

    private ImageLoaderFactory() {
    }

    public static ImageLoaderWrapper getLoader() {
        if (sInstance == null) {
            synchronized (ImageLoaderFactory.class) {
                if (sInstance == null) {
                    sInstance = new GlideImageLoader();
                }
            }
        }
        return sInstance;
    }
}
