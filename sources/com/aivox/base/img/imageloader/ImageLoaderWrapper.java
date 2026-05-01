package com.aivox.base.img.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public interface ImageLoaderWrapper {

    public static class DisplayOption {
        public int loadErrorResId;
        public int loadingResId;
    }

    void clearDiskCache(Context context);

    void clearMemory(Context context);

    void displayImage(ImageView imageView, int i, int i2);

    void displayImage(ImageView imageView, int i, Drawable drawable);

    void displayImage(ImageView imageView, int i, RequestOptions requestOptions);

    void displayImage(ImageView imageView, int i, RequestOptions requestOptions, int i2);

    void displayImage(ImageView imageView, Bitmap bitmap, RequestOptions requestOptions);

    void displayImage(ImageView imageView, Bitmap bitmap, RequestOptions requestOptions, int i);

    void displayImage(ImageView imageView, Uri uri, int i);

    void displayImage(ImageView imageView, File file, RequestOptions requestOptions);

    void displayImage(ImageView imageView, String str);

    void displayImage(ImageView imageView, String str, int i);

    void displayImage(ImageView imageView, String str, Drawable drawable);

    void displayImage(ImageView imageView, String str, RequestOptions requestOptions);

    void displayImage(ImageView imageView, String str, RequestOptions requestOptions, int i);

    void displayImagePath(ImageView imageView, String str, RequestOptions requestOptions);

    String getPhotoCacheDirSize(Context context);

    void pauseRequests(Context context);

    void resumeRequests(Context context);
}
