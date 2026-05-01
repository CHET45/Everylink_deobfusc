package com.github.houbb.opencc4j.support.data.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.model.data.DataInfo;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class TwSTCharData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        DataInfo dataInfo = new DataInfo();
        DATA_INFO = dataInfo;
        dataInfo.setDataMap(DataUtil.merge(DataUtil.buildDataMap("/data/dictionary/STCharacters.txt"), DataUtil.buildDataMap("/data/dictionary/TWVariants.txt")));
        dataInfo.setName("中国台湾简体转繁体字符数据");
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
