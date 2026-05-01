package com.github.houbb.heaven.support.attr;

import com.github.houbb.heaven.util.util.Optional;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public interface IAttributeContext {
    boolean containsKey(String str);

    Object getAttr(String str);

    Boolean getAttrBoolean(String str);

    Byte getAttrByte(String str);

    Character getAttrCharacter(String str);

    Double getAttrDouble(String str);

    Float getAttrFloat(String str);

    Integer getAttrInteger(String str);

    Long getAttrLong(String str);

    Optional<Object> getAttrOptional(String str);

    Short getAttrShort(String str);

    String getAttrString(String str);

    Set<String> keySet();

    IAttributeContext putAttr(String str, Object obj);

    IAttributeContext removeAttr(String str);
}
