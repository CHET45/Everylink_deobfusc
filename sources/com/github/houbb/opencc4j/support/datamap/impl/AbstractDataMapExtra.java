package com.github.houbb.opencc4j.support.datamap.impl;

import com.github.houbb.heaven.util.util.MapUtil;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractDataMapExtra extends AbstractDataMap {
    private final IDataMap baseDataMap;
    private volatile Map<String, List<String>> stChar;
    private volatile Map<String, List<String>> stPhrase;
    private volatile Map<String, List<String>> tsChar;
    private volatile Map<String, List<String>> tsPhrase;

    protected abstract Map<String, List<String>> stCharExtra();

    protected abstract Map<String, List<String>> stPhraseExtra();

    protected abstract Map<String, List<String>> tsCharExtra();

    protected abstract Map<String, List<String>> tsPhraseExtra();

    public AbstractDataMapExtra(IDataMap iDataMap) {
        this.baseDataMap = iDataMap;
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public synchronized Map<String, List<String>> tsPhrase() {
        if (this.tsPhrase != null) {
            return this.tsPhrase;
        }
        this.tsPhrase = buildAllMap(this.baseDataMap.tsPhrase(), tsPhraseExtra());
        return this.tsPhrase;
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public synchronized Map<String, List<String>> tsChar() {
        if (this.tsChar != null) {
            return this.tsChar;
        }
        this.tsChar = buildAllMap(this.baseDataMap.tsChar(), tsCharExtra());
        return this.tsChar;
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public synchronized Map<String, List<String>> stPhrase() {
        if (this.stPhrase != null) {
            return this.stPhrase;
        }
        this.stPhrase = buildAllMap(this.baseDataMap.stPhrase(), stPhraseExtra());
        return this.stPhrase;
    }

    @Override // com.github.houbb.opencc4j.support.datamap.IDataMap
    public synchronized Map<String, List<String>> stChar() {
        if (this.stChar != null) {
            return this.stChar;
        }
        this.stChar = buildAllMap(this.baseDataMap.stChar(), stCharExtra());
        return this.stChar;
    }

    protected Map<String, List<String>> buildAllMap(Map<String, List<String>> map, Map<String, List<String>> map2) {
        if (MapUtil.isEmpty(map2)) {
            return map;
        }
        HashMap map3 = new HashMap();
        if (MapUtil.isNotEmpty(map)) {
            map3.putAll(map);
        }
        map3.putAll(map2);
        return map3;
    }
}
