package org.videolan.libvlc.util;

import android.net.Uri;
import java.util.ArrayList;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.ILibVLC;

/* JADX INFO: loaded from: classes5.dex */
public class Dumper {
    private final ILibVLC mILibVLC;
    private final Listener mListener;
    private final MediaPlayer mMediaPlayer;

    public interface Listener {
        void onFinish(boolean z);

        void onProgress(float f);
    }

    public Dumper(Uri uri, String str, Listener listener) {
        if (uri == null || str == null || listener == null) {
            throw new IllegalArgumentException("arguments shouldn't be null");
        }
        this.mListener = listener;
        ArrayList arrayList = new ArrayList(8);
        arrayList.add("--demux");
        arrayList.add("dump2,none");
        arrayList.add("--demuxdump-file");
        arrayList.add(str);
        arrayList.add("--no-video");
        arrayList.add("--no-audio");
        arrayList.add("--no-spu");
        arrayList.add("-vv");
        LibVLC libVLC = new LibVLC(null, arrayList);
        this.mILibVLC = libVLC;
        Media media = new Media(libVLC, uri);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        this.mMediaPlayer = mediaPlayer;
        mediaPlayer.setEventListener(new MediaPlayer.EventListener() { // from class: org.videolan.libvlc.util.Dumper.1
            @Override // org.videolan.libvlc.interfaces.AbstractVLCEvent.Listener
            public void onEvent(MediaPlayer.Event event) {
                int i = event.type;
                if (i == 259) {
                    Dumper.this.mListener.onProgress(event.getBuffering());
                } else if (i == 265 || i == 266) {
                    Dumper.this.mListener.onFinish(event.type == 265);
                    Dumper.this.cancel();
                }
            }
        });
        media.release();
    }

    public void start() {
        this.mMediaPlayer.play();
    }

    public void cancel() {
        this.mMediaPlayer.stop();
        this.mMediaPlayer.release();
        this.mILibVLC.release();
    }
}
