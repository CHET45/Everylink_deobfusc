package com.github.houbb.opencc4j.support.convert.context;

import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface UnitConvertContext {
    Map<String, List<String>> getCharData();

    Map<String, List<String>> getPhraseData();

    String getUnit();
}
