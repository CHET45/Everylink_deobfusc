package com.lxj.xpopup.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.photoview.PhotoView;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public interface XPopupImageLoader {
    File getImageFile(Context context, Object obj);

    View loadImage(int i, Object obj, ImageViewerPopupView imageViewerPopupView, PhotoView photoView, ProgressBar progressBar);

    void loadSnapshot(Object obj, PhotoView photoView);
}
