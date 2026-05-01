package com.aivox.base.img.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import com.aivox.base.C0874R;
import com.aivox.base.img.imageloader.GlideRoundedCornersTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class GlideImageLoader implements ImageLoaderWrapper {
    private RequestOptions initOptions(RequestOptions requestOptions) {
        return initOptions(C0874R.mipmap.icon_logo);
    }

    private RequestOptions initOptions(RequestOptions requestOptions, int i) {
        RequestOptions requestOptionsInitOptions = initOptions(C0874R.mipmap.icon_logo);
        requestOptionsInitOptions.optionalTransform(new GlideRoundedCornersTransform(i, GlideRoundedCornersTransform.CornerType.ALL));
        return requestOptionsInitOptions;
    }

    private RequestOptions initOptions(int i) {
        return initOptions((RequestOptions) null, i, i);
    }

    private RequestOptions initOptions(Drawable drawable) {
        return initOptions((RequestOptions) null, drawable, drawable);
    }

    private RequestOptions initOptions(RequestOptions requestOptions, int i, int i2) {
        if (requestOptions != null) {
            return requestOptions;
        }
        RequestOptions requestOptions2 = new RequestOptions();
        requestOptions2.error(i2).diskCacheStrategy(DiskCacheStrategy.ALL);
        return requestOptions2;
    }

    private RequestOptions initOptions(RequestOptions requestOptions, Drawable drawable, Drawable drawable2) {
        if (requestOptions != null) {
            return requestOptions;
        }
        RequestOptions requestOptions2 = new RequestOptions();
        requestOptions2.error(drawable2).diskCacheStrategy(DiskCacheStrategy.ALL);
        return requestOptions2;
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, File file, RequestOptions requestOptions) {
        Glide.with(imageView.getContext()).load(file).apply((BaseRequestOptions<?>) initOptions(requestOptions)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImagePath(ImageView imageView, String str, RequestOptions requestOptions) {
        Glide.with(imageView.getContext()).load("file://" + str).apply((BaseRequestOptions<?>) initOptions(requestOptions)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, String str, RequestOptions requestOptions) {
        Glide.with(imageView.getContext()).load(str).apply((BaseRequestOptions<?>) initOptions(requestOptions)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, String str) {
        Glide.with(imageView.getContext()).load(str).apply((BaseRequestOptions<?>) initOptions((RequestOptions) null)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, String str, int i) {
        Glide.with(imageView.getContext()).load(str).apply((BaseRequestOptions<?>) initOptions(i)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, String str, Drawable drawable) {
        Glide.with(imageView.getContext()).load(str).apply((BaseRequestOptions<?>) initOptions(drawable)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, int i, Drawable drawable) {
        Glide.with(imageView.getContext()).load(Integer.valueOf(i)).apply((BaseRequestOptions<?>) initOptions(drawable)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, String str, RequestOptions requestOptions, int i) {
        Glide.with(imageView.getContext()).load(str).apply((BaseRequestOptions<?>) initOptions(requestOptions, i)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, int i, RequestOptions requestOptions) {
        Glide.with(imageView.getContext()).load(Integer.valueOf(i)).apply((BaseRequestOptions<?>) initOptions(requestOptions)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, Uri uri, int i) {
        Glide.with(imageView.getContext()).load(uri).apply((BaseRequestOptions<?>) initOptions(i)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, int i, int i2) {
        if ((imageView.getContext() instanceof Activity) && ((Activity) imageView.getContext()).isDestroyed()) {
            return;
        }
        Glide.with(imageView.getContext()).load(Integer.valueOf(i)).apply((BaseRequestOptions<?>) initOptions(i2)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, Bitmap bitmap, RequestOptions requestOptions) {
        Glide.with(imageView.getContext()).load(bitmap).apply((BaseRequestOptions<?>) initOptions(requestOptions)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, int i, RequestOptions requestOptions, int i2) {
        Glide.with(imageView.getContext()).load(Integer.valueOf(i)).apply((BaseRequestOptions<?>) initOptions(requestOptions, i2)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void displayImage(ImageView imageView, Bitmap bitmap, RequestOptions requestOptions, int i) {
        Glide.with(imageView.getContext()).load(bitmap).apply((BaseRequestOptions<?>) initOptions(requestOptions)).into(imageView);
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void pauseRequests(Context context) {
        Log.i("tag", "pauseRequests");
        Glide.with(context).pauseRequests();
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void resumeRequests(Context context) {
        Log.i("tag", "resumeRequests");
        Glide.with(context).resumeRequests();
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override // com.aivox.base.img.imageloader.ImageLoaderWrapper
    public String getPhotoCacheDirSize(Context context) {
        Glide.get(context);
        return FileSizeUtil.getSize(Glide.getPhotoCacheDir(context));
    }
}
