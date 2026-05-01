package com.github.houbb.opencc4j.support.data.impl.p012jp;

import com.github.houbb.opencc4j.model.data.DataInfo;
import com.github.houbb.opencc4j.support.data.impl.AbstractData;
import com.github.houbb.opencc4j.support.data.impl.DataUtil;
import com.github.houbb.opencc4j.support.data.impl.p011hk.HkSTPhraseSelfData;

/* JADX INFO: loaded from: classes3.dex */
public class JpTSShinjitaiPhrasesSelfData extends AbstractData {
    private static final DataInfo DATA_INFO;

    static {
        synchronized (HkSTPhraseSelfData.class) {
            DataInfo dataInfo = new DataInfo();
            DATA_INFO = dataInfo;
            dataInfo.setDataMap(DataUtil.buildDataMap("/data/dictionary/JPShinjitaiPhrases.txt"));
            dataInfo.setName("日本新日文到标准繁体自身词组数据");
        }
    }

    @Override // com.github.houbb.opencc4j.support.data.Data
    public DataInfo data() {
        return DATA_INFO;
    }
}
