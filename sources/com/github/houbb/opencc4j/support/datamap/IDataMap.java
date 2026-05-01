package com.github.houbb.opencc4j.support.datamap;

import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public interface IDataMap {
    Set<String> sChars();

    Map<String, List<String>> stChar();

    Map<String, List<String>> stPhrase();

    Set<String> tChars();

    Map<String, List<String>> tsChar();

    Map<String, List<String>> tsPhrase();
}
