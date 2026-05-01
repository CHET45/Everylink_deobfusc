package com.tencent.aai.model;

/* JADX INFO: loaded from: classes4.dex */
public class AudioRecognizeConfiguration {
    private int audioFlowSilenceTimeOut;
    private boolean isCompress;
    private int minVolumeCallbackTime;
    private boolean silentDetectTimeOut;
    private boolean silentDetectTimeOutAutoStop;
    private int sliceTime;

    public static class Builder {
        public int audioFlowSilenceTimeOut = 5000;
        public int minVolumeCallbackTime = 80;
        public int sliceTime = 40;
        public boolean silentDetectTimeOut = false;
        public boolean silentDetectTimeOutAutoStop = true;
        public boolean isCompress = true;

        public Builder audioFlowSilenceTimeOut(int i) {
            if (i < 2000) {
                this.audioFlowSilenceTimeOut = 2000;
            } else {
                this.audioFlowSilenceTimeOut = i;
            }
            return this;
        }

        public AudioRecognizeConfiguration build() {
            return new AudioRecognizeConfiguration(this.minVolumeCallbackTime, this.audioFlowSilenceTimeOut, this.sliceTime, this.silentDetectTimeOut, this.silentDetectTimeOutAutoStop, this.isCompress);
        }

        public Builder isCompress(boolean z) {
            this.isCompress = z;
            return this;
        }

        public Builder minVolumeCallbackTime(int i) {
            this.minVolumeCallbackTime = Math.max(i, 40);
            return this;
        }

        public Builder setSilentDetectTimeOut(boolean z) {
            this.silentDetectTimeOut = z;
            return this;
        }

        public Builder setSilentDetectTimeOutAutoStop(boolean z) {
            this.silentDetectTimeOutAutoStop = z;
            return this;
        }

        public Builder sliceTime(int i) {
            if (i < 40) {
                i = 40;
            }
            if (i > 5000) {
                i = 5000;
            }
            this.sliceTime = (i / 20) * 20;
            return this;
        }
    }

    private AudioRecognizeConfiguration(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        this.minVolumeCallbackTime = i;
        this.sliceTime = i3;
        this.silentDetectTimeOut = z;
        this.audioFlowSilenceTimeOut = i2;
        this.silentDetectTimeOutAutoStop = z2;
        this.isCompress = z3;
    }

    public int getAudioFlowSilenceTimeOut() {
        return this.audioFlowSilenceTimeOut;
    }

    public int getMinVolumeCallbackTime() {
        return this.minVolumeCallbackTime;
    }

    public boolean getSilentDetectTimeOut() {
        return this.silentDetectTimeOut;
    }

    public int getSliceTime() {
        return this.sliceTime;
    }

    public boolean isCompress() {
        return this.isCompress;
    }

    public boolean isSilentDetectTimeOutAutoStop() {
        return this.silentDetectTimeOutAutoStop;
    }

    public void setSilentDetectTimeOutAutoStop(boolean z) {
        this.silentDetectTimeOutAutoStop = z;
    }

    public void setSliceTime(int i) {
        if (i < 40) {
            i = 40;
        }
        if (i > 5000) {
            i = 5000;
        }
        this.sliceTime = (i / 20) * 20;
    }
}
