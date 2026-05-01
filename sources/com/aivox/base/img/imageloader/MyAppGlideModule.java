package com.aivox.base.img.imageloader;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class MyAppGlideModule extends AppGlideModule {
    @Override // com.bumptech.glide.module.LibraryGlideModule, com.bumptech.glide.module.RegistersComponents
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.prepend(String.class, InputStream.class, new S3BucketModelLoaderFactory());
    }
}
