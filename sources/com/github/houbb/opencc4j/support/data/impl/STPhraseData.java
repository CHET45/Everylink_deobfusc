package com.github.houbb.opencc4j.support.data.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.model.data.DataInfo;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class STPhraseData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        synchronized (STPhraseData.class) {
            DataInfo dataInfo = new DataInfo();
            DATA_INFO = dataInfo;
            dataInfo.setDataMap(DataUtil.buildDataMap("/data/dictionary/STPhrases.txt"));
            dataInfo.setName("中国大陆简体转繁体词组数据");
        }
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
