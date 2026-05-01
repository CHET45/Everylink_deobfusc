package com.github.houbb.opencc4j.core.impl;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.opencc4j.core.ZhConvert;
import com.github.houbb.opencc4j.core.ZhConvertCore;
import com.github.houbb.opencc4j.core.ZhConvertCoreContext;
import com.github.houbb.opencc4j.support.chars.ZhChar;
import com.github.houbb.opencc4j.support.chars.ZhChars;
import com.github.houbb.opencc4j.support.convert.core.UnitConvert;
import com.github.houbb.opencc4j.support.convert.core.UnitConverts;
import com.github.houbb.opencc4j.support.datamap.IDataMap;
import com.github.houbb.opencc4j.support.datamap.impl.DataMaps;
import com.github.houbb.opencc4j.support.match.ZhMatch;
import com.github.houbb.opencc4j.support.match.ZhMatches;
import com.github.houbb.opencc4j.support.segment.Segment;
import com.github.houbb.opencc4j.support.segment.impl.Segments;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ZhConvertBootstrap implements ZhConvert {
    private ZhConvertCoreContext context;
    private Segment segment = Segments.defaults();
    private IDataMap dataMap = DataMaps.defaults();
    private ZhChar zhChar = ZhChars.defaults();
    private UnitConvert unitConvert = UnitConverts.defaults();
    private ZhConvertCore zhConvertCore = new ZhConvertCoreDefault();
    private ZhMatch isSimpleMatch = ZhMatches.simpleAll();
    private ZhMatch isTraditionalMatch = ZhMatches.traditionalAll();

    private ZhConvertBootstrap() {
    }

    public static ZhConvertBootstrap newInstance() {
        return new ZhConvertBootstrap();
    }

    @Deprecated
    public static ZhConvertBootstrap newInstance(Segment segment) {
        ZhConvertBootstrap zhConvertBootstrapNewInstance = newInstance();
        zhConvertBootstrapNewInstance.segment(segment);
        return zhConvertBootstrapNewInstance;
    }

    public ZhConvertBootstrap segment(Segment segment) {
        ArgUtil.notNull(segment, "segment");
        this.segment = segment;
        return this;
    }

    public ZhConvertBootstrap dataMap(IDataMap iDataMap) {
        ArgUtil.notNull(iDataMap, "dataMap");
        this.dataMap = iDataMap;
        return this;
    }

    public ZhConvertBootstrap zhChar(ZhChar zhChar) {
        ArgUtil.notNull(zhChar, "zhChar");
        this.zhChar = zhChar;
        return this;
    }

    public ZhConvertBootstrap unitConvert(UnitConvert unitConvert) {
        ArgUtil.notNull(unitConvert, "unitConvert");
        this.unitConvert = unitConvert;
        return this;
    }

    public ZhConvertBootstrap isSimpleMatch(ZhMatch zhMatch) {
        ArgUtil.notNull(zhMatch, "isSimpleMatch");
        this.isSimpleMatch = zhMatch;
        return this;
    }

    public ZhConvertBootstrap isTraditionalMatch(ZhMatch zhMatch) {
        ArgUtil.notNull(zhMatch, "isTraditionalMatch");
        this.isTraditionalMatch = zhMatch;
        return this;
    }

    public ZhConvertBootstrap init() {
        this.context = ZhConvertCoreContext.newInstance().zhChars(this.zhChar).segment(this.segment).unitConvert(this.unitConvert).dataMap(this.dataMap).isSimpleMatch(this.isSimpleMatch).isTraditionalMatch(this.isTraditionalMatch);
        return this;
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public String toSimple(String str) {
        return this.zhConvertCore.toSimple(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public String toTraditional(String str) {
        return this.zhConvertCore.toTraditional(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    @Deprecated
    public List<String> doSeg(String str) {
        if (StringUtil.isEmpty(str)) {
            return Guavas.newArrayList();
        }
        return this.segment.seg(str);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public List<String> simpleList(String str) {
        return this.zhConvertCore.simpleList(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public List<String> traditionalList(String str) {
        return this.zhConvertCore.traditionalList(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean isSimple(char c) {
        return this.zhConvertCore.isSimple(c, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean isSimple(String str) {
        return this.zhConvertCore.isSimple(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean containsSimple(String str) {
        return this.zhConvertCore.containsSimple(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean isTraditional(char c) {
        return this.zhConvertCore.isTraditional(c, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean isTraditional(String str) {
        return this.zhConvertCore.isTraditional(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean containsTraditional(String str) {
        return this.zhConvertCore.containsTraditional(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean isChinese(char c) {
        return this.zhConvertCore.isChinese(c, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean isChinese(String str) {
        return this.zhConvertCore.isChinese(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public boolean containsChinese(String str) {
        return this.zhConvertCore.containsChinese(str, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public List<String> toSimple(char c) {
        return this.zhConvertCore.toSimple(c, this.context);
    }

    @Override // com.github.houbb.opencc4j.core.ZhConvert
    public List<String> toTraditional(char c) {
        return this.zhConvertCore.toTraditional(c, this.context);
    }
}
