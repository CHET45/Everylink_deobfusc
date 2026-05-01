package com.github.houbb.opencc4j.support.convert.context.impl;

import com.github.houbb.opencc4j.support.convert.context.UnitConvertContext;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultUnitConvertContext implements UnitConvertContext {
    private Map<String, List<String>> charData;
    private Map<String, List<String>> phraseData;
    private String unit;

    @Override // com.github.houbb.opencc4j.support.convert.context.UnitConvertContext
    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    @Override // com.github.houbb.opencc4j.support.convert.context.UnitConvertContext
    public Map<String, List<String>> getCharData() {
        return this.charData;
    }

    public void setCharData(Map<String, List<String>> map) {
        this.charData = map;
    }

    @Override // com.github.houbb.opencc4j.support.convert.context.UnitConvertContext
    public Map<String, List<String>> getPhraseData() {
        return this.phraseData;
    }

    public void setPhraseData(Map<String, List<String>> map) {
        this.phraseData = map;
    }
}
