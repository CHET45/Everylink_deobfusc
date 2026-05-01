package com.aivox.base.img.imageloader;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public final class S3BucketModelLoader implements ModelLoader<String, InputStream> {
    @Override // com.bumptech.glide.load.model.ModelLoader
    public ModelLoader.LoadData<InputStream> buildLoadData(String str, int i, int i2, Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(str), new S3BucketDataFetcher(str));
    }

    @Override // com.bumptech.glide.load.model.ModelLoader
    public boolean handles(String str) {
        return str.startsWith("https://litok.s3.amazonaws.com/");
    }
}
