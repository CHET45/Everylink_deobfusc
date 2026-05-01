package org.videolan.libvlc.interfaces;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

/* JADX INFO: loaded from: classes5.dex */
public interface IVLCVout {

    public interface Callback {
        void onSurfacesCreated(IVLCVout iVLCVout);

        void onSurfacesDestroyed(IVLCVout iVLCVout);
    }

    public interface OnNewVideoLayoutListener {
        void onNewVideoLayout(IVLCVout iVLCVout, int i, int i2, int i3, int i4, int i5, int i6);
    }

    void addCallback(Callback callback);

    boolean areViewsAttached();

    void attachViews();

    void attachViews(OnNewVideoLayoutListener onNewVideoLayoutListener);

    void detachViews();

    void removeCallback(Callback callback);

    void sendMouseEvent(int i, int i2, int i3, int i4);

    void setSubtitlesSurface(SurfaceTexture surfaceTexture);

    void setSubtitlesSurface(Surface surface, SurfaceHolder surfaceHolder);

    void setSubtitlesView(SurfaceView surfaceView);

    void setSubtitlesView(TextureView textureView);

    void setVideoSurface(SurfaceTexture surfaceTexture);

    void setVideoSurface(Surface surface, SurfaceHolder surfaceHolder);

    void setVideoView(SurfaceView surfaceView);

    void setVideoView(TextureView textureView);

    void setWindowSize(int i, int i2);
}
