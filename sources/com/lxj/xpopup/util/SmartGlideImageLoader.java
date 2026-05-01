package com.lxj.xpopup.util;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.photoview.OnMatrixChangedListener;
import com.lxj.xpopup.photoview.PhotoView;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class SmartGlideImageLoader implements XPopupImageLoader {
    private int errImg;
    private boolean mBigImage;

    public SmartGlideImageLoader() {
    }

    public SmartGlideImageLoader(int i) {
        this.errImg = i;
    }

    public SmartGlideImageLoader(boolean z, int i) {
        this(i);
        this.mBigImage = z;
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public View loadImage(int i, Object obj, ImageViewerPopupView imageViewerPopupView, PhotoView photoView, final ProgressBar progressBar) {
        progressBar.setVisibility(0);
        final View viewBuildBigImageView = this.mBigImage ? buildBigImageView(imageViewerPopupView, progressBar, i) : buildPhotoView(imageViewerPopupView, photoView, i);
        final Context context = viewBuildBigImageView.getContext();
        if (photoView.getDrawable() != null && ((Integer) photoView.getTag()).intValue() == i) {
            if (viewBuildBigImageView instanceof PhotoView) {
                try {
                    ((PhotoView) viewBuildBigImageView).setImageDrawable(photoView.getDrawable().getConstantState().newDrawable());
                } catch (Exception unused) {
                }
            } else {
                ((SubsamplingScaleImageView) viewBuildBigImageView).setImage(ImageSource.bitmap(XPopupUtils.view2Bitmap(photoView)));
            }
        }
        Glide.with(viewBuildBigImageView).downloadOnly().load(obj).into(new ImageDownloadTarget() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.1
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable drawable) {
                super.onLoadFailed(drawable);
                progressBar.setVisibility(8);
                View view2 = viewBuildBigImageView;
                if (view2 instanceof PhotoView) {
                    ((PhotoView) view2).setImageResource(SmartGlideImageLoader.this.errImg);
                    ((PhotoView) viewBuildBigImageView).setZoomable(false);
                } else {
                    ((SubsamplingScaleImageView) view2).setImage(ImageSource.resource(SmartGlideImageLoader.this.errImg));
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onResourceReady(File file, Transition<? super File> transition) {
                super.onResourceReady(file, transition);
                int windowWidth = XPopupUtils.getWindowWidth(context) * 2;
                int screenHeight = XPopupUtils.getScreenHeight(context) * 2;
                int[] imageSize = XPopupUtils.getImageSize(file);
                int rotateDegree = XPopupUtils.getRotateDegree(file.getAbsolutePath());
                View view2 = viewBuildBigImageView;
                if (view2 instanceof PhotoView) {
                    progressBar.setVisibility(8);
                    ((PhotoView) viewBuildBigImageView).setZoomable(true);
                    if (imageSize[0] <= windowWidth && imageSize[1] <= screenHeight) {
                        Glide.with(viewBuildBigImageView).load(file).transform(new Rotate(rotateDegree)).apply((BaseRequestOptions<?>) new RequestOptions().error(SmartGlideImageLoader.this.errImg).override(imageSize[0], imageSize[1])).into((PhotoView) viewBuildBigImageView);
                        return;
                    } else {
                        ((PhotoView) viewBuildBigImageView).setImageBitmap(XPopupUtils.rotate(XPopupUtils.getBitmap(file, windowWidth, screenHeight), rotateDegree, imageSize[0] / 2.0f, imageSize[1] / 2.0f));
                        return;
                    }
                }
                SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) view2;
                if ((imageSize[1] * 1.0f) / imageSize[0] > (XPopupUtils.getScreenHeight(context) * 1.0f) / XPopupUtils.getWindowWidth(context)) {
                    subsamplingScaleImageView.setMinimumScaleType(4);
                } else {
                    subsamplingScaleImageView.setMinimumScaleType(1);
                }
                subsamplingScaleImageView.setMaxScale(10.0f);
                subsamplingScaleImageView.setDoubleTapZoomScale(3.0f);
                subsamplingScaleImageView.setOnImageEventListener(new SSIVListener(subsamplingScaleImageView, progressBar, SmartGlideImageLoader.this.errImg));
                subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(file)).dimensions(imageSize[0], imageSize[1]), ImageSource.cachedBitmap(XPopupUtils.getBitmap(file, XPopupUtils.getWindowWidth(context), XPopupUtils.getScreenHeight(context))));
                subsamplingScaleImageView.setScaleAndCenter(0.0f, new PointF(0.0f, 0.0f));
            }
        });
        return viewBuildBigImageView;
    }

    private SubsamplingScaleImageView buildBigImageView(final ImageViewerPopupView imageViewerPopupView, ProgressBar progressBar, final int i) {
        SubsamplingScaleImageView subsamplingScaleImageView = new SubsamplingScaleImageView(imageViewerPopupView.getContext());
        subsamplingScaleImageView.setOrientation(-1);
        subsamplingScaleImageView.setOnStateChangedListener(new SubsamplingScaleImageView.DefaultOnStateChangedListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.2
            @Override // com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.DefaultOnStateChangedListener, com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnStateChangedListener
            public void onCenterChanged(PointF pointF, int i2) {
                super.onCenterChanged(pointF, i2);
            }
        });
        subsamplingScaleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                imageViewerPopupView.dismiss();
            }
        });
        if (imageViewerPopupView.longPressListener != null) {
            subsamplingScaleImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.4
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    imageViewerPopupView.longPressListener.onLongPressed(imageViewerPopupView, i);
                    return false;
                }
            });
        }
        return subsamplingScaleImageView;
    }

    private PhotoView buildPhotoView(final ImageViewerPopupView imageViewerPopupView, final PhotoView photoView, final int i) {
        final PhotoView photoView2 = new PhotoView(imageViewerPopupView.getContext());
        photoView2.setZoomable(false);
        photoView2.setOnMatrixChangeListener(new OnMatrixChangedListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.5
            @Override // com.lxj.xpopup.photoview.OnMatrixChangedListener
            public void onMatrixChanged(RectF rectF) {
                if (photoView != null) {
                    Matrix matrix = new Matrix();
                    photoView2.getSuppMatrix(matrix);
                    photoView.setSuppMatrix(matrix);
                }
            }
        });
        photoView2.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                imageViewerPopupView.dismiss();
            }
        });
        if (imageViewerPopupView.longPressListener != null) {
            photoView2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.7
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    imageViewerPopupView.longPressListener.onLongPressed(imageViewerPopupView, i);
                    return false;
                }
            });
        }
        return photoView2;
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public void loadSnapshot(Object obj, final PhotoView photoView) {
        Glide.with(photoView).downloadOnly().load(obj).into(new ImageDownloadTarget() { // from class: com.lxj.xpopup.util.SmartGlideImageLoader.8
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(Drawable drawable) {
                super.onLoadFailed(drawable);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.lxj.xpopup.util.ImageDownloadTarget, com.bumptech.glide.request.target.Target
            public void onResourceReady(File file, Transition<? super File> transition) {
                super.onResourceReady(file, transition);
                int rotateDegree = XPopupUtils.getRotateDegree(file.getAbsolutePath());
                int windowWidth = XPopupUtils.getWindowWidth(photoView.getContext());
                int screenHeight = XPopupUtils.getScreenHeight(photoView.getContext());
                int[] imageSize = XPopupUtils.getImageSize(file);
                if (imageSize[0] > windowWidth || imageSize[1] > screenHeight) {
                    photoView.setImageBitmap(XPopupUtils.rotate(XPopupUtils.getBitmap(file, windowWidth, screenHeight), rotateDegree, imageSize[0] / 2.0f, imageSize[1] / 2.0f));
                } else {
                    Glide.with(photoView).load(file).apply((BaseRequestOptions<?>) new RequestOptions().override(imageSize[0], imageSize[1])).into(photoView);
                }
            }
        });
    }

    @Override // com.lxj.xpopup.interfaces.XPopupImageLoader
    public File getImageFile(Context context, Object obj) {
        try {
            return Glide.with(context).downloadOnly().load(obj).submit().get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
