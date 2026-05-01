package com.aivox.app.util.agent;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public interface GlassDelegate {

    public interface ImageCallback {
        void onFailed();

        void onImageCaptured(Bitmap bitmap);
    }

    public interface LocationCallback {
        void onLocationResult(double d, double d2);
    }

    void glassCalendarEvent(String str, Long l);

    void glassDeviceControl(int i);

    void glassGetLocation(LocationCallback locationCallback);

    void glassImageRecognition(ImageCallback imageCallback);

    void glassTakePhoto();
}
