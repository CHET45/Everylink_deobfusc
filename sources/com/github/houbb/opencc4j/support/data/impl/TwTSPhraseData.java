package com.github.houbb.opencc4j.support.data.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.model.data.DataInfo;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class TwTSPhraseData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        synchronized (TwTSPhraseData.class) {
            DataInfo dataInfo = new DataInfo();
            DATA_INFO = dataInfo;
            dataInfo.setDataMap(DataUtil.merge(DataUtil.buildDataMap("/data/dictionary/TSPhrases.txt"), DataUtil.buildDataMapReverse("/data/dictionary/TWPhrases.txt")));
            dataInfo.setName("中国台湾繁体转简体词组数据");
        }
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
