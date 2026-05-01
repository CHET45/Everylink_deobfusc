package com.github.houbb.opencc4j.core;

import com.github.houbb.opencc4j.support.chars.ZhChar;
import com.github.houbb.opencc4j.support.convert.core.UnitConvert;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import com.github.houbb.opencc4j.support.match.ZhMatch;
import com.github.houbb.opencc4j.support.segment.Segment;

/* JADX INFO: loaded from: classes3.dex */
public class ZhConvertCoreContext {
    private IDataMap dataMap;
    private ZhMatch isSimpleMatch;
    private ZhMatch isTraditionalMatch;
    private Segment segment;
    private UnitConvert unitConvert;
    private ZhChar zhChars;

    public static ZhConvertCoreContext newInstance() {
        return new ZhConvertCoreContext();
    }

    public Segment segment() {
        return this.segment;
    }

    public ZhConvertCoreContext segment(Segment segment) {
        this.segment = segment;
        return this;
    }

    public IDataMap dataMap() {
        return this.dataMap;
    }

    public ZhConvertCoreContext dataMap(IDataMap iDataMap) {
        this.dataMap = iDataMap;
        return this;
    }

    public UnitConvert unitConvert() {
        return this.unitConvert;
    }

    public ZhConvertCoreContext unitConvert(UnitConvert unitConvert) {
        this.unitConvert = unitConvert;
        return this;
    }

    public ZhChar zhChars() {
        return this.zhChars;
    }

    public ZhConvertCoreContext zhChars(ZhChar zhChar) {
        this.zhChars = zhChar;
        return this;
    }

    public ZhMatch isSimpleMatch() {
        return this.isSimpleMatch;
    }

    public ZhConvertCoreContext isSimpleMatch(ZhMatch zhMatch) {
        this.isSimpleMatch = zhMatch;
        return this;
    }

    public ZhMatch isTraditionalMatch() {
        return this.isTraditionalMatch;
    }

    public ZhConvertCoreContext isTraditionalMatch(ZhMatch zhMatch) {
        this.isTraditionalMatch = zhMatch;
        return this;
    }
}
