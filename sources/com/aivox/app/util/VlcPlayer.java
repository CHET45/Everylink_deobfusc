package com.aivox.app.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.aivox.base.util.DialogUtils;
import java.util.ArrayList;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.AbstractVLCEvent;

/* JADX INFO: loaded from: classes.dex */
public class VlcPlayer {
    private boolean isFirstFrameRendered = false;
    private final LibVLC libVLC;
    private final MediaPlayer mediaPlayer;

    public VlcPlayer(Context context, SurfaceView surfaceView) {
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.aivox.app.util.VlcPlayer.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (VlcPlayer.this.mediaPlayer != null) {
                    VlcPlayer.this.mediaPlayer.getVLCVout().setVideoSurface(surfaceHolder.getSurface(), surfaceHolder);
                    VlcPlayer.this.mediaPlayer.getVLCVout().attachViews();
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
                if (VlcPlayer.this.mediaPlayer != null) {
                    VlcPlayer.this.mediaPlayer.getVLCVout().setWindowSize(i2, i3);
                }
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (VlcPlayer.this.mediaPlayer == null || !VlcPlayer.this.mediaPlayer.getVLCVout().areViewsAttached()) {
                    return;
                }
                VlcPlayer.this.mediaPlayer.getVLCVout().detachViews();
            }
        });
        ArrayList arrayList = new ArrayList();
        arrayList.add("--avcodec-hw=any");
        arrayList.add("--network-caching=1000");
        arrayList.add("--no-audio");
        LibVLC libVLC = new LibVLC(context, arrayList);
        this.libVLC = libVLC;
        MediaPlayer mediaPlayer = new MediaPlayer(libVLC);
        this.mediaPlayer = mediaPlayer;
        mediaPlayer.setScale(0.0f);
        mediaPlayer.setEventListener(new MediaPlayer.EventListener() { // from class: com.aivox.app.util.VlcPlayer$$ExternalSyntheticLambda0
            @Override // org.videolan.libvlc.interfaces.AbstractVLCEvent.Listener
            public final void onEvent(AbstractVLCEvent abstractVLCEvent) {
                this.f$0.m2395lambda$new$0$comaivoxapputilVlcPlayer((MediaPlayer.Event) abstractVLCEvent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-app-util-VlcPlayer, reason: not valid java name */
    /* synthetic */ void m2395lambda$new$0$comaivoxapputilVlcPlayer(MediaPlayer.Event event) {
        if (event.type != 267 || this.isFirstFrameRendered) {
            return;
        }
        DialogUtils.hideLoadingDialog();
        this.isFirstFrameRendered = true;
    }

    public void play(String str, String str2) {
        Media media = new Media(this.libVLC, Uri.parse(str2));
        if (!TextUtils.isEmpty(str)) {
            media.addOption(":source-iface=" + str);
        }
        this.mediaPlayer.setMedia(media);
        media.release();
        this.mediaPlayer.play();
    }

    public void pause() {
        this.mediaPlayer.pause();
    }

    public void stop() {
        this.mediaPlayer.stop();
    }

    public void release() {
        this.mediaPlayer.release();
        this.libVLC.release();
    }

    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }
}
