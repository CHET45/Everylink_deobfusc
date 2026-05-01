package com.github.houbb.opencc4j.support.data.impl.p011hk;

import com.github.houbb.opencc4j.model.data.DataInfo;
import com.github.houbb.opencc4j.support.data.impl.AbstractData;
import com.github.houbb.opencc4j.support.data.impl.DataUtil;

/* JADX INFO: loaded from: classes3.dex */
public class HkSTCharSelfData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        DataInfo dataInfo = new DataInfo();
        DATA_INFO = dataInfo;
        dataInfo.setDataMap(DataUtil.buildDataMap("/data/dictionary/HKVariants.txt"));
        dataInfo.setName("中国香港简体转繁体字符自身数据");
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
