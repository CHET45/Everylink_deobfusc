package com.aivox.app.media;

import android.media.AudioTrack;
import android.media.Image;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import com.aivox.base.util.BaseAppUtils;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class AudioDecoder extends BaseDecoder {
    private int OUT_SAMPLE_RATE;
    private final String TAG;
    public AudioExtractor audioExtractor;
    private AudioTrack mAudioTrack;
    private int mChannels;
    private int mPCMEncodeBit;
    private int mSampleRate;

    @Override // com.aivox.app.media.BaseDecoder
    void renderImage(Image image, int i, int i2) {
    }

    public AudioDecoder(String str) {
        super(str, false);
        this.TAG = "AudioDecoder";
        this.mSampleRate = -1;
        this.OUT_SAMPLE_RATE = 44100;
        this.mChannels = -1;
        this.mPCMEncodeBit = 2;
        this.mAudioTrack = null;
    }

    @Override // com.aivox.app.media.BaseDecoder
    boolean check() {
        Log.d("AudioDecoder", "check: ");
        return true;
    }

    @Override // com.aivox.app.media.BaseDecoder
    IExtractor initExtractor(String str) {
        Log.d("AudioDecoder", "initExtractor: ");
        if (this.audioExtractor == null) {
            this.audioExtractor = new AudioExtractor(str);
        }
        return this.audioExtractor;
    }

    public AudioExtractor getAudioExtractor() {
        return this.audioExtractor;
    }

    public void setAudioExtractor(AudioExtractor audioExtractor) {
        this.audioExtractor = audioExtractor;
    }

    @Override // com.aivox.app.media.BaseDecoder
    void initSpecParams(MediaFormat mediaFormat) {
        try {
            this.mChannels = mediaFormat.getInteger("channel-count");
            if (mediaFormat.containsKey("pcm-encoding")) {
                Log.d("AudioDecoder", "initSpecParams containsKey: KEY_PCM_ENCODING");
                this.mPCMEncodeBit = mediaFormat.getInteger("pcm-encoding");
            } else {
                Log.d("AudioDecoder", "initSpecParams not containsKey: KEY_PCM_ENCODING");
                this.mPCMEncodeBit = 2;
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            Log.d("AudioDecoder", "initSpecParams Exception : " + e.getMessage());
        }
        Log.d("AudioDecoder", "initSpecParams mChannels: " + this.mChannels + " mPCMEncodeBit: " + this.mPCMEncodeBit);
    }

    @Override // com.aivox.app.media.BaseDecoder
    boolean initRender() {
        Log.d("AudioDecoder", "initRender mChannels: " + this.mChannels);
        int i = this.mChannels == 1 ? 4 : 12;
        int minBufferSize = AudioTrack.getMinBufferSize(this.mSampleRate, i, this.mPCMEncodeBit);
        Log.d("AudioDecoder", "initRender minBufferSize: " + minBufferSize + " mSampleRate: " + this.mSampleRate + " mPCMEncodeBit: " + this.mPCMEncodeBit + " mChannels: " + this.mChannels);
        AudioTrack audioTrack = new AudioTrack(3, this.OUT_SAMPLE_RATE, i, this.mPCMEncodeBit, minBufferSize, 1);
        this.mAudioTrack = audioTrack;
        audioTrack.play();
        return true;
    }

    @Override // com.aivox.app.media.BaseDecoder
    boolean configCodec(MediaCodec mediaCodec, MediaFormat mediaFormat) {
        Log.d("AudioDecoder", "configCodec: ");
        mediaCodec.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 0);
        this.mSampleRate = mediaCodec.getOutputFormat().getInteger("sample-rate");
        Log.d("AudioDecoder", "configCodec sample rate: " + this.mSampleRate);
        return true;
    }

    @Override // com.aivox.app.media.BaseDecoder
    void render(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        this.mAudioTrack.write(byteBuffer, bufferInfo.size, 0);
    }

    @Override // com.aivox.app.media.BaseDecoder
    void doneDecode() {
        Log.d("AudioDecoder", "doneDecode:");
        this.mAudioTrack.stop();
        this.mAudioTrack.release();
    }
}
