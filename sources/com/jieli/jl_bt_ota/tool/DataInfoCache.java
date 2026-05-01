package com.jieli.jl_bt_ota.tool;

import com.jieli.jl_bt_ota.model.DataInfo;
import com.jieli.jl_bt_ota.model.base.BasePacket;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class DataInfoCache extends ArrayList<DataInfo> {
    public DataInfo getDataInfo(BasePacket basePacket) {
        int size = size();
        int i = 0;
        while (i < size) {
            DataInfo dataInfo = get(i);
            i++;
            DataInfo dataInfo2 = dataInfo;
            if (dataInfo2.getBasePacket().getOpCode() == basePacket.getOpCode() && dataInfo2.getBasePacket().getOpCodeSn() == basePacket.getOpCodeSn()) {
                return dataInfo2;
            }
        }
        return null;
    }
}
