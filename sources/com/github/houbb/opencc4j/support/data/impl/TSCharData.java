package com.github.houbb.opencc4j.support.data.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.model.data.DataInfo;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class TSCharData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        synchronized (TSCharData.class) {
            DataInfo dataInfo = new DataInfo();
            DATA_INFO = dataInfo;
            dataInfo.setDataMap(DataUtil.buildDataMap("/data/dictionary/TSCharacters.txt"));
            dataInfo.setName("中国大陆繁体转简体字符数据");
        }
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
