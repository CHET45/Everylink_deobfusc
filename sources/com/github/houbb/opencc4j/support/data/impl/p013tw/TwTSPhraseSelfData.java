package com.github.houbb.opencc4j.support.data.impl.p013tw;

import com.github.houbb.opencc4j.model.data.DataInfo;
import com.github.houbb.opencc4j.support.data.impl.AbstractData;
import com.github.houbb.opencc4j.support.data.impl.DataUtil;

/* JADX INFO: loaded from: classes3.dex */
public class TwTSPhraseSelfData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        synchronized (TwTSPhraseSelfData.class) {
            DataInfo dataInfo = new DataInfo();
            DATA_INFO = dataInfo;
            dataInfo.setDataMap(DataUtil.buildDataMapReverse("/data/dictionary/TWPhrases.txt"));
            dataInfo.setName("中国台湾繁体转简体自身词组数据");
        }
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
