package com.github.piasy.audioprocessor;

/* JADX INFO: loaded from: classes3.dex */
public class AudioProcessor {
    private final int mBufferSize;
    private final float[] mFloatInput;
    private final float[] mFloatOutput;
    private final byte[] mOutBuffer;

    private static native void process(float f, byte[] bArr, byte[] bArr2, int i, int i2, float[] fArr, float[] fArr2);

    static {
        System.loadLibrary("audio-processor");
    }

    public AudioProcessor(int i) {
        this.mBufferSize = i;
        this.mOutBuffer = new byte[i];
        this.mFloatInput = new float[i / 2];
        this.mFloatOutput = new float[i / 2];
    }

    public synchronized byte[] process(float f, byte[] bArr, int i) {
        process(f, bArr, this.mOutBuffer, this.mBufferSize, i, this.mFloatInput, this.mFloatOutput);
        return this.mOutBuffer;
    }
}
