package com.aivox.base.img.app;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import com.aivox.base.C0874R;
import com.aivox.base.img.image.CustomRequestListener;
import com.aivox.base.img.image.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class ImageLoaderManager {
    private ImageLoaderManager() {
    }

    public static ImageLoaderManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static ImageLoaderManager instance = new ImageLoaderManager();

        private SingletonHolder() {
        }
    }

    public void displayImageForNotification(Context context, RemoteViews remoteViews, int i, Notification notification, int i2, String str) {
        displayImageForTarget(context, initNotificationTarget(context, i, remoteViews, notification, i2), str);
    }

    public void displayImageForView(ImageView imageView, String str) {
        displayImageForView(imageView, str, null, 0, 0);
    }

    public void displayImageForView(ImageView imageView, String str, int i, int i2) {
        displayImageForView(imageView, str, null, i, i2);
    }

    public void displayImageForView(ImageView imageView, String str, CustomRequestListener customRequestListener, int i, int i2) {
        Glide.with(imageView.getContext()).asBitmap().load(str).apply((BaseRequestOptions<?>) initCommonRequestOption(i, i2)).transition(BitmapTransitionOptions.withCrossFade()).listener(customRequestListener).into(imageView);
    }

    public void displayImageForCircle(final ImageView imageView, String str) {
        Glide.with(imageView.getContext()).asBitmap().load(str).apply((BaseRequestOptions<?>) initCommonRequestOption()).into(new BitmapImageViewTarget(imageView) { // from class: com.aivox.base.img.app.ImageLoaderManager.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bumptech.glide.request.target.BitmapImageViewTarget, com.bumptech.glide.request.target.ImageViewTarget
            public void setResource(Bitmap bitmap) {
                RoundedBitmapDrawable roundedBitmapDrawableCreate = RoundedBitmapDrawableFactory.create(imageView.getResources(), bitmap);
                roundedBitmapDrawableCreate.setCircular(true);
                imageView.setImageDrawable(roundedBitmapDrawableCreate);
            }
        });
    }

    public void displayImageForViewGroup(final ViewGroup viewGroup, String str) {
        Glide.with(viewGroup.getContext()).asBitmap().load(str).apply((BaseRequestOptions<?>) initCommonRequestOption()).into(new SimpleTarget<Bitmap>() { // from class: com.aivox.base.img.app.ImageLoaderManager.2
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(final Bitmap bitmap, Transition<? super Bitmap> transition) {
                Observable.just(bitmap).map(new Function<Bitmap, Drawable>() { // from class: com.aivox.base.img.app.ImageLoaderManager.2.2
                    @Override // io.reactivex.functions.Function
                    public Drawable apply(Bitmap bitmap2) {
                        return new BitmapDrawable(Utils.doBlur(bitmap, 100, true));
                    }
                }).subscribeOn(Schedulers.m1898io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Drawable>() { // from class: com.aivox.base.img.app.ImageLoaderManager.2.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Drawable drawable) throws Exception {
                        viewGroup.setBackground(drawable);
                    }
                });
            }
        });
    }

    private void displayImageForTarget(Context context, Target target, String str) {
        displayImageForTarget(context, target, str, null);
    }

    private void displayImageForTarget(Context context, Target target, String str, CustomRequestListener customRequestListener) {
        Glide.with(context).asBitmap().load(str).apply((BaseRequestOptions<?>) initCommonRequestOption()).transition(BitmapTransitionOptions.withCrossFade()).listener(customRequestListener).into(target);
    }

    private NotificationTarget initNotificationTarget(Context context, int i, RemoteViews remoteViews, Notification notification, int i2) {
        return new NotificationTarget(context, i, remoteViews, notification, i2);
    }

    private RequestOptions initCommonRequestOption() {
        return initCommonRequestOption(C0874R.mipmap.b4y, C0874R.mipmap.b4y);
    }

    private RequestOptions initCommonRequestOption(int i, int i2) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(i).error(i2).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(false).priority(Priority.NORMAL);
        return requestOptions;
    }
}
