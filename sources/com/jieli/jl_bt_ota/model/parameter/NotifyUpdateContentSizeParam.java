package com.jieli.jl_bt_ota.model.parameter;

import com.jieli.jl_bt_ota.model.base.BaseParameter;
import com.jieli.jl_bt_ota.util.CHexConver;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyUpdateContentSizeParam extends BaseParameter {
    private int contentSize;
    private int currentProgress = 0;

    public NotifyUpdateContentSizeParam(int i) {
        this.contentSize = i;
    }

    public int getContentSize() {
        return this.contentSize;
    }

    public int getCurrentProgress() {
        return this.currentProgress;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter, com.jieli.jl_bt_ota.interfaces.command.IParamBase
    public byte[] getParamData() {
        if (this.currentProgress <= 0) {
            return CHexConver.intToBigBytes(this.contentSize);
        }
        byte[] bArr = new byte[8];
        byte[] bArrIntToBigBytes = CHexConver.intToBigBytes(this.contentSize);
        byte[] bArrIntToBigBytes2 = CHexConver.intToBigBytes(this.currentProgress);
        System.arraycopy(bArrIntToBigBytes, 0, bArr, 0, bArrIntToBigBytes.length);
        System.arraycopy(bArrIntToBigBytes2, 0, bArr, bArrIntToBigBytes.length, bArrIntToBigBytes2.length);
        return bArr;
    }

    public NotifyUpdateContentSizeParam setContentSize(int i) {
        this.contentSize = i;
        return this;
    }

    public NotifyUpdateContentSizeParam setCurrentProgress(int i) {
        this.currentProgress = i;
        return this;
    }

    @Override // com.jieli.jl_bt_ota.model.base.BaseParameter
    public String toString() {
        return "NotifyUpdateContentSizeParam{contentSize=" + this.contentSize + ", currentProgress=" + this.currentProgress + "} " + super.toString();
    }
}
