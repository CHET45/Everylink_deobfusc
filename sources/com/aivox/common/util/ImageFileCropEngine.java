package com.aivox.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.aivox.base.C0874R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.engine.CropFileEngine;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ImageFileCropEngine implements CropFileEngine {
    @Override // com.luck.picture.lib.engine.CropFileEngine
    public void onStartCrop(Fragment fragment, Uri uri, Uri uri2, ArrayList<String> arrayList, int i) {
        UCrop.Options options = new UCrop.Options();
        options.withAspectRatio(1.0f, 1.0f);
        options.setCircleDimmedLayer(true);
        options.setHideBottomControls(true);
        options.setStatusBarColor(fragment.getContext().getColor(C0874R.color.bg_color_secondary));
        options.setToolbarColor(fragment.getContext().getColor(C0874R.color.bg_color_secondary));
        options.setToolbarWidgetColor(fragment.getContext().getColor(C0874R.color.txt_color_primary));
        UCrop uCropM1882of = UCrop.m1882of(uri, uri2, arrayList);
        uCropM1882of.withOptions(options);
        uCropM1882of.setImageEngine(new UCropImageEngine() { // from class: com.aivox.common.util.ImageFileCropEngine.1
            @Override // com.yalantis.ucrop.UCropImageEngine
            public void loadImage(Context context, String str, ImageView imageView) {
                Glide.with(context).load(str).override(180, 180).into(imageView);
            }

            @Override // com.yalantis.ucrop.UCropImageEngine
            public void loadImage(Context context, Uri uri3, int i2, int i3, final UCropImageEngine.OnCallbackListener<Bitmap> onCallbackListener) {
                Glide.with(context).asBitmap().load(uri3).override(i2, i3).into(new CustomTarget<Bitmap>() { // from class: com.aivox.common.util.ImageFileCropEngine.1.1
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                        onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        UCropImageEngine.OnCallbackListener onCallbackListener2 = onCallbackListener;
                        if (onCallbackListener2 != null) {
                            onCallbackListener2.onCall(bitmap);
                        }
                    }

                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(Drawable drawable) {
                        UCropImageEngine.OnCallbackListener onCallbackListener2 = onCallbackListener;
                        if (onCallbackListener2 != null) {
                            onCallbackListener2.onCall(null);
                        }
                    }
                });
            }
        });
        uCropM1882of.start(fragment.requireActivity(), fragment, i);
    }
}
