package com.aivox.app.util.wav;

/* JADX INFO: loaded from: classes.dex */
public class WavFileHeader {
    public static final int WAV_CHUNKSIZE_EXCLUDE_DATA = 36;
    public static final int WAV_CHUNKSIZE_OFFSET = 4;
    public static final int WAV_FILE_HEADER_SIZE = 44;
    public static final int WAV_SUB_CHUNKSIZE1_OFFSET = 16;
    public static final int WAV_SUB_CHUNKSIZE2_OFFSET = 40;
    public short mAudioFormat;
    public short mBitsPerSample;
    public short mBlockAlign;
    public int mByteRate;
    public String mChunkID;
    public int mChunkSize;
    public String mFormat;
    public short mNumChannel;
    public int mSampleRate;
    public String mSubChunk1ID;
    public int mSubChunk1Size;
    public String mSubChunk2ID;
    public int mSubChunk2Size;

    public WavFileHeader() {
        this.mChunkID = "RIFF";
        this.mChunkSize = 0;
        this.mFormat = "WAVE";
        this.mSubChunk1ID = "fmt ";
        this.mSubChunk1Size = 16;
        this.mAudioFormat = (short) 1;
        this.mNumChannel = (short) 1;
        this.mSampleRate = 16000;
        this.mByteRate = 32000;
        this.mBlockAlign = (short) 2;
        this.mBitsPerSample = (short) 16;
        this.mSubChunk2ID = "data";
        this.mSubChunk2Size = 0;
    }

    public WavFileHeader(int i, int i2, int i3) {
        this.mChunkID = "RIFF";
        this.mChunkSize = 0;
        this.mFormat = "WAVE";
        this.mSubChunk1ID = "fmt ";
        this.mSubChunk1Size = 16;
        this.mAudioFormat = (short) 1;
        this.mByteRate = 32000;
        this.mBlockAlign = (short) 2;
        this.mSubChunk2ID = "data";
        this.mSubChunk2Size = 0;
        this.mSampleRate = i;
        short s = (short) i3;
        this.mBitsPerSample = s;
        short s2 = (short) i2;
        this.mNumChannel = s2;
        this.mByteRate = ((i * s2) * s) / 8;
        this.mBlockAlign = (short) ((s2 * s) / 8);
    }

    public String toString() {
        return "WavFileHeader{mChunkID='" + this.mChunkID + "', mChunkSize=" + this.mChunkSize + ", mFormat='" + this.mFormat + "', mSubChunk1ID='" + this.mSubChunk1ID + "', mSubChunk1Size=" + this.mSubChunk1Size + ", mAudioFormat=" + ((int) this.mAudioFormat) + ", mNumChannel=" + ((int) this.mNumChannel) + ", mSampleRate=" + this.mSampleRate + ", mByteRate=" + this.mByteRate + ", mBlockAlign=" + ((int) this.mBlockAlign) + ", mBitsPerSample=" + ((int) this.mBitsPerSample) + ", mSubChunk2ID='" + this.mSubChunk2ID + "', mSubChunk2Size=" + this.mSubChunk2Size + '}';
    }
}
