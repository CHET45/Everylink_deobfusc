package com.aivox.app.media;

import android.media.Image;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import com.aivox.base.util.BaseAppUtils;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseDecoder implements IDecoder {
    private boolean isVideoDecoder;
    private String mFilePath;
    private final String TAG = "BaseDecoder";
    private boolean mIsRunning = true;
    private Object mLock = new Object();
    private boolean mReadyForDecode = false;
    protected MediaCodec mCodec = null;
    protected IExtractor mExtractor = null;
    protected ByteBuffer mInputBuffers = null;
    protected ByteBuffer mOutputBuffers = null;
    protected Image mOutputImage = null;
    private DecodeState mState = DecodeState.STOP;
    protected long mDuration = 0;
    private long mEndPos = 0;
    private boolean mIsEOS = false;
    protected int mVideoWidth = 0;
    protected int mVideoHeight = 0;
    private long mStartTimeForSync = -1;
    private boolean mSyncRender = true;
    private MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();

    abstract boolean check();

    abstract boolean configCodec(MediaCodec mediaCodec, MediaFormat mediaFormat);

    abstract void doneDecode();

    @Override // com.aivox.app.media.IDecoder
    public int getRotationAngle() {
        return 0;
    }

    abstract IExtractor initExtractor(String str);

    abstract boolean initRender();

    abstract void initSpecParams(MediaFormat mediaFormat);

    abstract void render(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo);

    abstract void renderImage(Image image, int i, int i2);

    @Override // com.aivox.app.media.IDecoder
    public void setStateListener() {
    }

    public BaseDecoder(String str, boolean z) {
        this.mFilePath = str;
        this.isVideoDecoder = z;
    }

    @Override // com.aivox.app.media.IDecoder
    public void pause() {
        Log.d("BaseDecoder", "pause: ");
        this.mState = DecodeState.PAUSE;
        notifyDecode();
    }

    @Override // com.aivox.app.media.IDecoder
    public void goOn() {
        Log.d("BaseDecoder", "goOn: ");
        this.mState = DecodeState.DECODING;
        notifyDecode();
    }

    @Override // com.aivox.app.media.IDecoder
    public void stop() {
        Log.d("BaseDecoder", "stop: ");
        this.mState = DecodeState.STOP;
        this.mIsRunning = false;
        notifyDecode();
    }

    @Override // com.aivox.app.media.IDecoder
    public Boolean isDecoding() {
        return Boolean.valueOf(this.mState == DecodeState.DECODING);
    }

    @Override // com.aivox.app.media.IDecoder
    public Boolean isSeeking() {
        return Boolean.valueOf(this.mState == DecodeState.SEEKING);
    }

    @Override // com.aivox.app.media.IDecoder
    public Boolean isStop() {
        return Boolean.valueOf(this.mState == DecodeState.STOP);
    }

    @Override // com.aivox.app.media.IDecoder
    public Boolean isPause() {
        return Boolean.valueOf(this.mState == DecodeState.PAUSE);
    }

    @Override // com.aivox.app.media.IDecoder
    public int getWidth() {
        return this.mVideoWidth;
    }

    @Override // com.aivox.app.media.IDecoder
    public int getHeight() {
        return this.mVideoHeight;
    }

    @Override // com.aivox.app.media.IDecoder
    public long getDuration() {
        return this.mDuration;
    }

    @Override // com.aivox.app.media.IDecoder
    public MediaFormat getMediaFormat() {
        return this.mExtractor.getFormat();
    }

    @Override // com.aivox.app.media.IDecoder
    public int getTrack() {
        return this.mExtractor.getTrack();
    }

    @Override // com.aivox.app.media.IDecoder
    public String getFilePath() {
        return this.mFilePath;
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d("BaseDecoder", "run: BaseDecoder");
        if (this.mState == DecodeState.STOP) {
            this.mState = DecodeState.START;
        }
        if (!init()) {
            Log.d("BaseDecoder", "run: coec init fail, return");
            return;
        }
        while (this.mIsRunning) {
            if (this.mState != DecodeState.START && this.mState != DecodeState.DECODING && this.mState != DecodeState.SEEKING) {
                Log.d("BaseDecoder", "run waitDecode mState : " + this.mState);
                waitDecode();
                this.mStartTimeForSync = System.currentTimeMillis() - getCurTimeStamp();
            }
            if (!this.mIsRunning || this.mState == DecodeState.STOP) {
                this.mIsRunning = false;
                break;
            }
            if (this.mStartTimeForSync == -1) {
                this.mStartTimeForSync = System.currentTimeMillis();
            }
            if (!this.mIsEOS) {
                this.mIsEOS = pushBufferToDecoder();
            }
            int iPullBufferFromDecoder = pullBufferFromDecoder();
            if (iPullBufferFromDecoder >= 0) {
                if (this.mSyncRender && this.mState == DecodeState.DECODING) {
                    sleepRender();
                }
                if (this.isVideoDecoder) {
                    renderImage(this.mOutputImage, this.mVideoWidth, this.mVideoHeight);
                } else {
                    render(this.mOutputBuffers, this.mBufferInfo);
                }
                this.mCodec.releaseOutputBuffer(iPullBufferFromDecoder, true);
                if (this.mState == DecodeState.START) {
                    this.mState = DecodeState.PAUSE;
                }
            }
            if (this.mBufferInfo.flags == 4) {
                this.mState = DecodeState.FINISH;
            }
        }
        doneDecode();
    }

    public long getCurTimeStamp() {
        return this.mBufferInfo.presentationTimeUs / 1000;
    }

    private boolean init() {
        if (!check()) {
            Log.d("BaseDecoder", "init: check fail");
            return false;
        }
        IExtractor iExtractorInitExtractor = initExtractor(this.mFilePath);
        this.mExtractor = iExtractorInitExtractor;
        if (iExtractorInitExtractor == null || iExtractorInitExtractor.getFormat() == null) {
            Log.d("BaseDecoder", "init: check mExtractor fail");
            return false;
        }
        if (!initParams()) {
            Log.d("BaseDecoder", "init: initParams fail");
            return false;
        }
        if (!initCodec()) {
            Log.d("BaseDecoder", "init: initCodec fail");
            return false;
        }
        if (initRender()) {
            return true;
        }
        Log.d("BaseDecoder", "init: initRender fail");
        return false;
    }

    private boolean initParams() {
        try {
            MediaFormat format = this.mExtractor.getFormat();
            long j = format.getLong("durationUs") / 1000;
            this.mDuration = j;
            if (this.mEndPos == 0) {
                this.mEndPos = j;
            }
            initSpecParams(format);
            return true;
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return false;
        }
    }

    private boolean initCodec() {
        Log.d("BaseDecoder", "initCodec: ");
        try {
            String string = this.mExtractor.getFormat().getString("mime");
            Log.d("BaseDecoder", "initCodec codec type: " + string);
            MediaCodec mediaCodecCreateDecoderByType = MediaCodec.createDecoderByType(string);
            this.mCodec = mediaCodecCreateDecoderByType;
            if (!configCodec(mediaCodecCreateDecoderByType, this.mExtractor.getFormat())) {
                waitDecode();
            }
            this.mCodec.start();
            return true;
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            Log.d("BaseDecoder", "initCodec exception: " + e.getMessage());
            return false;
        }
    }

    private boolean pushBufferToDecoder() {
        int iDequeueInputBuffer = this.mCodec.dequeueInputBuffer(1000L);
        Log.d("BaseDecoder", "pushBufferToDecoder inputBufferIndex: " + iDequeueInputBuffer);
        if (iDequeueInputBuffer >= 0) {
            ByteBuffer inputBuffer = this.mCodec.getInputBuffer(iDequeueInputBuffer);
            this.mInputBuffers = inputBuffer;
            int buffer = this.mExtractor.readBuffer(inputBuffer);
            Log.d("BaseDecoder", "pushBufferToDecoder sampleSize: " + buffer);
            if (buffer < 0) {
                this.mCodec.queueInputBuffer(iDequeueInputBuffer, 0, 0, 0L, 4);
                return true;
            }
            this.mCodec.queueInputBuffer(iDequeueInputBuffer, 0, buffer, this.mExtractor.getCurrentTimestamp(), 0);
        }
        return false;
    }

    private int pullBufferFromDecoder() {
        int iDequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(this.mBufferInfo, 1000L);
        Log.d("BaseDecoder", "pullBufferFromDecoder index: " + iDequeueOutputBuffer);
        if (iDequeueOutputBuffer >= 0) {
            if (this.isVideoDecoder) {
                this.mOutputImage = this.mCodec.getOutputImage(iDequeueOutputBuffer);
            } else {
                this.mOutputBuffers = this.mCodec.getOutputBuffer(iDequeueOutputBuffer);
            }
        }
        return iDequeueOutputBuffer;
    }

    private void waitDecode() {
        Log.d("BaseDecoder", "waitDecode: ");
        try {
            synchronized (this.mLock) {
                this.mLock.wait();
            }
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    protected void notifyDecode() {
        synchronized (this.mLock) {
            this.mLock.notifyAll();
        }
    }

    private void sleepRender() {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.mStartTimeForSync;
        long curTimeStamp = getCurTimeStamp();
        Log.d("BaseDecoder", "sleepRender curTime: " + curTimeStamp + " passTime: " + jCurrentTimeMillis);
        if (curTimeStamp > jCurrentTimeMillis) {
            try {
                Thread.sleep(curTimeStamp - jCurrentTimeMillis);
            } catch (InterruptedException e) {
                BaseAppUtils.printErrorMsg(e);
            }
        }
    }
}
