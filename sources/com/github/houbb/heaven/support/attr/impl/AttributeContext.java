package com.github.houbb.heaven.support.attr.impl;

import com.github.houbb.heaven.support.attr.IAttributeContext;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.util.Optional;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes3.dex */
public class AttributeContext implements IAttributeContext {
    private Map<String, Object> context;

    public AttributeContext() {
        this.context = new ConcurrentHashMap();
    }

    public AttributeContext(Map<String, Object> map) {
        this.context = new ConcurrentHashMap(map);
    }

    protected AttributeContext putAttrMap(Map<String, ?> map) {
        ArgUtil.notNull(map, "map");
        this.context.putAll(map);
        return this;
    }

    protected Set<Map.Entry<String, Object>> entrySet() {
        return this.context.entrySet();
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public AttributeContext putAttr(String str, Object obj) {
        this.context.put(str, obj);
        return this;
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Object getAttr(String str) {
        return this.context.get(str);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Optional<Object> getAttrOptional(String str) {
        return Optional.ofNullable(getAttr(str));
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public String getAttrString(String str) {
        return ObjectUtil.objectToString(getAttr(str));
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Boolean getAttrBoolean(String str) {
        return (Boolean) getAttrOptional(str).getCastOrNull(Boolean.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Character getAttrCharacter(String str) {
        return (Character) getAttrOptional(str).getCastOrNull(Character.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Byte getAttrByte(String str) {
        return (Byte) getAttrOptional(str).getCastOrNull(Byte.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Short getAttrShort(String str) {
        return (Short) getAttrOptional(str).getCastOrNull(Short.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Integer getAttrInteger(String str) {
        return (Integer) getAttrOptional(str).getCastOrNull(Integer.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Float getAttrFloat(String str) {
        return (Float) getAttrOptional(str).getCastOrNull(Float.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Double getAttrDouble(String str) {
        return (Double) getAttrOptional(str).getCastOrNull(Double.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Long getAttrLong(String str) {
        return (Long) getAttrOptional(str).getCastOrNull(Long.class);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public IAttributeContext removeAttr(String str) {
        this.context.remove(str);
        return this;
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public boolean containsKey(String str) {
        return this.context.containsKey(str);
    }

    @Override // com.github.houbb.heaven.support.attr.IAttributeContext
    public Set<String> keySet() {
        return this.context.keySet();
    }
}
