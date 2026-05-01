package com.github.houbb.opencc4j.core;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface ZhConvertCore {
    boolean containsChinese(String str, ZhConvertCoreContext zhConvertCoreContext);

    boolean containsSimple(String str, ZhConvertCoreContext zhConvertCoreContext);

    boolean containsTraditional(String str, ZhConvertCoreContext zhConvertCoreContext);

    boolean isChinese(char c, ZhConvertCoreContext zhConvertCoreContext);

    boolean isChinese(String str, ZhConvertCoreContext zhConvertCoreContext);

    boolean isSimple(char c, ZhConvertCoreContext zhConvertCoreContext);

    boolean isSimple(String str, ZhConvertCoreContext zhConvertCoreContext);

    boolean isTraditional(char c, ZhConvertCoreContext zhConvertCoreContext);

    boolean isTraditional(String str, ZhConvertCoreContext zhConvertCoreContext);

    List<String> simpleList(String str, ZhConvertCoreContext zhConvertCoreContext);

    String toSimple(String str, ZhConvertCoreContext zhConvertCoreContext);

    List<String> toSimple(char c, ZhConvertCoreContext zhConvertCoreContext);

    String toTraditional(String str, ZhConvertCoreContext zhConvertCoreContext);

    List<String> toTraditional(char c, ZhConvertCoreContext zhConvertCoreContext);

    List<String> traditionalList(String str, ZhConvertCoreContext zhConvertCoreContext);
}
