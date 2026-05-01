package com.github.houbb.opencc4j.core;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface ZhConvert {
    boolean containsChinese(String str);

    boolean containsSimple(String str);

    boolean containsTraditional(String str);

    @Deprecated
    List<String> doSeg(String str);

    boolean isChinese(char c);

    boolean isChinese(String str);

    boolean isSimple(char c);

    boolean isSimple(String str);

    boolean isTraditional(char c);

    boolean isTraditional(String str);

    List<String> simpleList(String str);

    String toSimple(String str);

    List<String> toSimple(char c);

    String toTraditional(String str);

    List<String> toTraditional(char c);

    List<String> traditionalList(String str);
}
