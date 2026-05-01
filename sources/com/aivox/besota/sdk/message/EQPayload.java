package com.aivox.besota.sdk.message;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class EQPayload implements Serializable {
    private float bandCount;
    private float calibration;
    private LinkedList<EQIDParam> iirParams;
    private int index = -1;
    private float leftGain;
    private float rightGain;
    private int sampleRate;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public float getCalibration() {
        return this.calibration;
    }

    public void setCalibration(float f) {
        this.calibration = f;
    }

    public int getSampleRate() {
        return this.sampleRate;
    }

    public void setSampleRate(int i) {
        this.sampleRate = i;
    }

    public float getLeftGain() {
        return this.leftGain;
    }

    public void setLeftGain(float f) {
        this.leftGain = f;
    }

    public float getRightGain() {
        return this.rightGain;
    }

    public void setRightGain(float f) {
        this.rightGain = f;
    }

    public float getBandCount() {
        return this.bandCount;
    }

    public void setBandCount(float f) {
        this.bandCount = f;
    }

    public LinkedList<EQIDParam> getIirParams() {
        return this.iirParams;
    }

    public void setIirParams(LinkedList<EQIDParam> linkedList) {
        this.iirParams = linkedList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EQPayload eQPayload = (EQPayload) obj;
        return Float.compare(eQPayload.calibration, this.calibration) == 0 && this.sampleRate == eQPayload.sampleRate && Float.compare(eQPayload.leftGain, this.leftGain) == 0 && Float.compare(eQPayload.rightGain, this.rightGain) == 0 && Objects.equals(this.iirParams, eQPayload.iirParams);
    }

    public int hashCode() {
        return Objects.hash(Float.valueOf(this.calibration), Integer.valueOf(this.sampleRate), Float.valueOf(this.leftGain), Float.valueOf(this.rightGain), this.iirParams);
    }

    public static class EQIDParam implements Serializable {
        int bandType;
        float frequency;
        float gainValue;
        float qValue;

        public int getBandType() {
            return this.bandType;
        }

        public void setBandType(int i) {
            this.bandType = i;
        }

        public float getGainValue() {
            return this.gainValue;
        }

        public void setGainValue(float f) {
            this.gainValue = f;
        }

        public float getFrequency() {
            return this.frequency;
        }

        public void setFrequency(float f) {
            this.frequency = f;
        }

        public float getQValue() {
            return this.qValue;
        }

        public void setQValue(float f) {
            this.qValue = f;
        }

        public String toString() {
            return "\ntype=" + this.bandType + ",gain=" + this.gainValue + ",freq=" + this.frequency + ",qValue=" + this.qValue;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            EQIDParam eQIDParam = (EQIDParam) obj;
            return this.bandType == eQIDParam.bandType && Float.compare(eQIDParam.gainValue, this.gainValue) == 0 && Float.compare(eQIDParam.frequency, this.frequency) == 0 && Float.compare(eQIDParam.qValue, this.qValue) == 0;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.bandType), Float.valueOf(this.gainValue), Float.valueOf(this.frequency), Float.valueOf(this.qValue));
        }
    }

    public String toString() {
        String string;
        LinkedList<EQIDParam> linkedList = this.iirParams;
        if (linkedList == null) {
            string = "";
        } else {
            string = linkedList.toString();
        }
        return "\nindex=" + this.index + "\ncalibration=" + this.calibration + "\nsampleRate=" + this.sampleRate + "\nleftGain=" + this.leftGain + "\nrightGain=" + this.rightGain + "\nbandCount=" + this.bandCount + "\n" + string;
    }
}
