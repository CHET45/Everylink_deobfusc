package com.github.houbb.opencc4j.support.data.impl.p012jp;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.opencc4j.model.data.DataInfo;
import com.github.houbb.opencc4j.support.data.impl.AbstractData;
import com.github.houbb.opencc4j.support.data.impl.DataUtil;
import com.github.houbb.opencc4j.support.data.impl.p011hk.HkTSCharSelfData;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class JpTSShinjitaiCharSelfData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        synchronized (HkTSCharSelfData.class) {
            DataInfo dataInfo = new DataInfo();
            DATA_INFO = dataInfo;
            dataInfo.setDataMap(DataUtil.buildDataMap("/data/dictionary/JPShinjitaiCharacters.txt"));
            dataInfo.setName("新日文到标准繁体自身数据");
        }
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
