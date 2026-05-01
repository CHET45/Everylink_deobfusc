package org.videolan.libvlc;

import android.os.Handler;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import org.videolan.C5096R;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.IMedia;
import org.videolan.libvlc.interfaces.IVLCVout;
import org.videolan.libvlc.util.DisplayManager;
import org.videolan.libvlc.util.VLCVideoLayout;

/* JADX INFO: loaded from: classes5.dex */
class VideoHelper implements IVLCVout.OnNewVideoLayoutListener {
    private static final String TAG = "LibVLC/VideoHelper";
    private DisplayManager mDisplayManager;
    private MediaPlayer mMediaPlayer;
    private FrameLayout mVideoSurfaceFrame;
    private MediaPlayer.ScaleType mCurrentScaleType = MediaPlayer.ScaleType.SURFACE_BEST_FIT;
    private int mVideoHeight = 0;
    private int mVideoWidth = 0;
    private int mVideoVisibleHeight = 0;
    private int mVideoVisibleWidth = 0;
    private int mVideoSarNum = 0;
    private int mVideoSarDen = 0;
    private SurfaceView mVideoSurface = null;
    private SurfaceView mSubtitlesSurface = null;
    private TextureView mVideoTexture = null;
    private final Handler mHandler = new Handler();
    private View.OnLayoutChangeListener mOnLayoutChangeListener = null;

    VideoHelper(MediaPlayer mediaPlayer, VLCVideoLayout vLCVideoLayout, DisplayManager displayManager, boolean z, boolean z2) {
        init(mediaPlayer, vLCVideoLayout, displayManager, z, !z2);
    }

    private void init(MediaPlayer mediaPlayer, VLCVideoLayout vLCVideoLayout, DisplayManager displayManager, boolean z, boolean z2) {
        this.mMediaPlayer = mediaPlayer;
        this.mDisplayManager = displayManager;
        if (displayManager == null || displayManager.isPrimary()) {
            FrameLayout frameLayout = (FrameLayout) vLCVideoLayout.findViewById(C5096R.id.player_surface_frame);
            this.mVideoSurfaceFrame = frameLayout;
            if (z2) {
                ViewStub viewStub = (ViewStub) frameLayout.findViewById(C5096R.id.surface_stub);
                this.mVideoSurface = (SurfaceView) (viewStub != null ? viewStub.inflate() : this.mVideoSurfaceFrame.findViewById(C5096R.id.surface_video));
                if (z) {
                    ViewStub viewStub2 = (ViewStub) this.mVideoSurfaceFrame.findViewById(C5096R.id.subtitles_surface_stub);
                    SurfaceView surfaceView = (SurfaceView) (viewStub2 != null ? viewStub2.inflate() : this.mVideoSurfaceFrame.findViewById(C5096R.id.surface_subtitles));
                    this.mSubtitlesSurface = surfaceView;
                    surfaceView.setZOrderMediaOverlay(true);
                    this.mSubtitlesSurface.getHolder().setFormat(-3);
                    return;
                }
                return;
            }
            ViewStub viewStub3 = (ViewStub) frameLayout.findViewById(C5096R.id.texture_stub);
            this.mVideoTexture = (TextureView) (viewStub3 != null ? viewStub3.inflate() : this.mVideoSurfaceFrame.findViewById(C5096R.id.texture_video));
            return;
        }
        if (this.mDisplayManager.getPresentation() != null) {
            this.mVideoSurfaceFrame = this.mDisplayManager.getPresentation().getSurfaceFrame();
            this.mVideoSurface = this.mDisplayManager.getPresentation().getSurfaceView();
            this.mSubtitlesSurface = this.mDisplayManager.getPresentation().getSubtitlesSurfaceView();
        }
    }

    void release() {
        if (this.mMediaPlayer.getVLCVout().areViewsAttached()) {
            detachViews();
        }
        this.mMediaPlayer = null;
        this.mVideoSurfaceFrame = null;
        this.mHandler.removeCallbacks(null);
        this.mVideoSurface = null;
        this.mSubtitlesSurface = null;
        this.mVideoTexture = null;
    }

    void attachViews() {
        if (this.mVideoSurface == null && this.mVideoTexture == null) {
            return;
        }
        IVLCVout vLCVout = this.mMediaPlayer.getVLCVout();
        SurfaceView surfaceView = this.mVideoSurface;
        if (surfaceView != null) {
            vLCVout.setVideoView(surfaceView);
            SurfaceView surfaceView2 = this.mSubtitlesSurface;
            if (surfaceView2 != null) {
                vLCVout.setSubtitlesView(surfaceView2);
            }
        } else {
            TextureView textureView = this.mVideoTexture;
            if (textureView == null) {
                return;
            } else {
                vLCVout.setVideoView(textureView);
            }
        }
        vLCVout.attachViews(this);
        if (this.mOnLayoutChangeListener == null) {
            this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: org.videolan.libvlc.VideoHelper.1
                private final Runnable runnable = new Runnable() { // from class: org.videolan.libvlc.VideoHelper.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (VideoHelper.this.mVideoSurfaceFrame == null || VideoHelper.this.mOnLayoutChangeListener == null) {
                            return;
                        }
                        VideoHelper.this.updateVideoSurfaces();
                    }
                };

                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
                        return;
                    }
                    VideoHelper.this.mHandler.removeCallbacks(this.runnable);
                    VideoHelper.this.mHandler.post(this.runnable);
                }
            };
        }
        this.mVideoSurfaceFrame.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mMediaPlayer.setVideoTrackEnabled(true);
    }

    void detachViews() {
        FrameLayout frameLayout;
        View.OnLayoutChangeListener onLayoutChangeListener = this.mOnLayoutChangeListener;
        if (onLayoutChangeListener != null && (frameLayout = this.mVideoSurfaceFrame) != null) {
            frameLayout.removeOnLayoutChangeListener(onLayoutChangeListener);
            this.mOnLayoutChangeListener = null;
        }
        this.mMediaPlayer.setVideoTrackEnabled(false);
        this.mMediaPlayer.getVLCVout().detachViews();
    }

    private void changeMediaPlayerLayout(int i, int i2) {
        if (this.mMediaPlayer.isReleased()) {
        }
        switch (C51132.$SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[this.mCurrentScaleType.ordinal()]) {
            case 1:
                this.mMediaPlayer.setAspectRatio(null);
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 2:
            case 3:
                IMedia.VideoTrack currentVideoTrack = this.mMediaPlayer.getCurrentVideoTrack();
                if (currentVideoTrack != null) {
                    boolean z = currentVideoTrack.orientation == 5 || currentVideoTrack.orientation == 6;
                    if (this.mCurrentScaleType == MediaPlayer.ScaleType.SURFACE_FIT_SCREEN) {
                        int i3 = currentVideoTrack.width;
                        int i4 = currentVideoTrack.height;
                        if (z) {
                            i4 = i3;
                            i3 = i4;
                        }
                        if (currentVideoTrack.sarNum != currentVideoTrack.sarDen) {
                            i3 = (i3 * currentVideoTrack.sarNum) / currentVideoTrack.sarDen;
                        }
                        float f = i3;
                        float f2 = i4;
                        float f3 = i;
                        float f4 = i2;
                        this.mMediaPlayer.setScale(f3 / f4 >= f / f2 ? f3 / f : f4 / f2);
                        this.mMediaPlayer.setAspectRatio(null);
                    } else {
                        this.mMediaPlayer.setScale(0.0f);
                        this.mMediaPlayer.setAspectRatio(!z ? "" + i + ":" + i2 : "" + i2 + ":" + i);
                    }
                    break;
                }
                break;
            case 4:
                this.mMediaPlayer.setAspectRatio("16:9");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 5:
                this.mMediaPlayer.setAspectRatio("16:10");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 6:
                this.mMediaPlayer.setAspectRatio("2:1");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 7:
                this.mMediaPlayer.setAspectRatio("2.21:1");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 8:
                this.mMediaPlayer.setAspectRatio("2.35:1");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 9:
                this.mMediaPlayer.setAspectRatio("2.39:1");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 10:
                this.mMediaPlayer.setAspectRatio("5:4");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 11:
                this.mMediaPlayer.setAspectRatio("4:3");
                this.mMediaPlayer.setScale(0.0f);
                break;
            case 12:
                this.mMediaPlayer.setAspectRatio(null);
                this.mMediaPlayer.setScale(1.0f);
                break;
        }
    }

    /* JADX INFO: renamed from: org.videolan.libvlc.VideoHelper$2 */
    static /* synthetic */ class C51132 {
        static final /* synthetic */ int[] $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType;

        static {
            int[] iArr = new int[MediaPlayer.ScaleType.values().length];
            $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType = iArr;
            try {
                iArr[MediaPlayer.ScaleType.SURFACE_BEST_FIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_FIT_SCREEN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_FILL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_16_9.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_16_10.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_2_1.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_221_1.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_235_1.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_239_1.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_5_4.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_4_3.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$videolan$libvlc$MediaPlayer$ScaleType[MediaPlayer.ScaleType.SURFACE_ORIGINAL.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0153 A[PHI: r1
  0x0153: PHI (r1v33 double) = (r1v18 double), (r1v18 double), (r1v41 double) binds: [B:86:0x0151, B:83:0x014c, B:79:0x0142] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0156 A[PHI: r1
  0x0156: PHI (r1v34 double) = (r1v18 double), (r1v18 double), (r1v41 double) binds: [B:86:0x0151, B:83:0x014c, B:79:0x0142] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void updateVideoSurfaces() {
        /*
            Method dump skipped, instruction units count: 435
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.videolan.libvlc.VideoHelper.updateVideoSurfaces():void");
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout.OnNewVideoLayoutListener
    public void onNewVideoLayout(IVLCVout iVLCVout, int i, int i2, int i3, int i4, int i5, int i6) {
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
        this.mVideoVisibleWidth = i3;
        this.mVideoVisibleHeight = i4;
        this.mVideoSarNum = i5;
        this.mVideoSarDen = i6;
        updateVideoSurfaces();
    }

    void setVideoScale(MediaPlayer.ScaleType scaleType) {
        this.mCurrentScaleType = scaleType;
        updateVideoSurfaces();
    }

    MediaPlayer.ScaleType getVideoScale() {
        return this.mCurrentScaleType;
    }
}
