package com.aivox.app.media;

import android.media.Image;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import com.aivox.app.media.VideoDecoderUtils;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class VideoDecoder extends BaseDecoder {
    private final String TAG;
    private Surface mSurface;
    private SurfaceView mSurfaceView;
    private String video_path;
    private VideoDecoderUtils video_utils;

    public VideoDecoder(String str, SurfaceView surfaceView) {
        super(str, true);
        this.TAG = "VideoDecoder";
        this.video_utils = null;
        this.mSurfaceView = surfaceView;
        this.video_path = str;
        VideoDecoderUtils videoDecoderUtils = new VideoDecoderUtils();
        this.video_utils = videoDecoderUtils;
        videoDecoderUtils.setOutDataType(1);
    }

    @Override // com.aivox.app.media.BaseDecoder
    boolean check() {
        Log.d("VideoDecoder", "check: ");
        return this.mSurfaceView != null;
    }

    @Override // com.aivox.app.media.BaseDecoder
    IExtractor initExtractor(String str) {
        Log.d("VideoDecoder", "initExtractor: ");
        return new VideoExtractor(this.video_path);
    }

    @Override // com.aivox.app.media.BaseDecoder
    void initSpecParams(MediaFormat mediaFormat) {
        this.mVideoWidth = mediaFormat.getInteger("width");
        this.mVideoHeight = mediaFormat.getInteger("height");
        this.mDuration = mediaFormat.getLong("durationUs") / 1000000;
        Log.d("VideoDecoder", "initSpecParams mVideoWidth: " + this.mVideoWidth + " mVideoHeight: " + this.mVideoHeight + " mDuration : " + this.mDuration);
        mediaFormat.setInteger("color-format", 2135033992);
    }

    @Override // com.aivox.app.media.BaseDecoder
    boolean initRender() {
        Log.d("VideoDecoder", "initRender: ");
        return true;
    }

    @Override // com.aivox.app.media.BaseDecoder
    boolean configCodec(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        Log.d("VideoDecoder", "configCodec:");
        if (this.mSurfaceView.getHolder().getSurface() != null) {
            Log.d("VideoDecoder", "configCodec: configure");
            this.mSurface = this.mSurfaceView.getHolder().getSurface();
            mediaCodec.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
            notifyDecode();
            return true;
        }
        Log.d("VideoDecoder", "configCodec: surface == null");
        return false;
    }

    @Override // com.aivox.app.media.BaseDecoder
    void render(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        Log.d("VideoDecoder", "render bufferInfo : ");
    }

    @Override // com.aivox.app.media.BaseDecoder
    void renderImage(Image image, int i, int i2) {
        Log.d("VideoDecoder", "renderImage width: " + i + " height : " + i2);
        Log.d("VideoDecoder", "renderImage format: " + image.getFormat());
        if (image != null) {
            this.video_utils.decodeFramesToImage(image, i, i2);
        } else {
            Log.d("VideoDecoder", "renderImage: image == null");
        }
    }

    @Override // com.aivox.app.media.BaseDecoder
    void doneDecode() {
        Log.d("VideoDecoder", "doneDecode: ");
    }

    public void setDataCallBack(VideoDecoderUtils.DataCallBack dataCallBack) {
        this.video_utils.setDataCallBack(dataCallBack);
    }
}
