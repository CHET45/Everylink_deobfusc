package com.aivox.base.img.imageloader;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class S3BucketModelLoaderFactory implements ModelLoaderFactory<String, InputStream> {
    @Override // com.bumptech.glide.load.model.ModelLoaderFactory
    public void teardown() {
    }

    @Override // com.bumptech.glide.load.model.ModelLoaderFactory
    public ModelLoader<String, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
        return new S3BucketModelLoader();
    }
}
