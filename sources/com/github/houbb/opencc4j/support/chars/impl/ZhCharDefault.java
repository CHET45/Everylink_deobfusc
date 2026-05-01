package com.github.houbb.opencc4j.support.chars.impl;

import com.github.houbb.opencc4j.support.chars.ZhChar;
import com.github.houbb.opencc4j.util.InnerCharUtils;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ZhCharDefault implements ZhChar {
    @Override // com.github.houbb.opencc4j.support.chars.ZhChar
    public List<String> chars(String str) {
        return InnerCharUtils.toCharList(str);
    }
}
