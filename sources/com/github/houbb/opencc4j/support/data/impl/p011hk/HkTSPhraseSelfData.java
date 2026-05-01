package com.github.houbb.opencc4j.support.data.impl.p011hk;

import com.github.houbb.opencc4j.model.data.DataInfo;
import com.github.houbb.opencc4j.support.data.impl.AbstractData;
import com.github.houbb.opencc4j.support.data.impl.DataUtil;

/* JADX INFO: loaded from: classes3.dex */
public class HkTSPhraseSelfData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        synchronized (HkTSPhraseSelfData.class) {
            DataInfo dataInfo = new DataInfo();
            DATA_INFO = dataInfo;
            dataInfo.setDataMap(DataUtil.buildDataMapReverse("/data/dictionary/HKVariantsRevPhrases.txt"));
            dataInfo.setName("中国香港繁体转简体自身词组数据");
        }
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
