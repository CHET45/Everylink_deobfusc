package com.github.houbb.opencc4j.util;

import com.github.houbb.opencc4j.core.impl.ZhConvertBootstrap;
import com.github.houbb.opencc4j.support.datamap.impl.DataMaps;
import com.github.houbb.opencc4j.support.segment.impl.Segments;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class ZhHkConverterUtil {
    private static final ZhConvertBootstrap DEFAULT_BOOTSTRAP = ZhConvertBootstrap.newInstance().segment(Segments.hkFastForward()).dataMap(DataMaps.hongKong()).init();
    private static final ZhConvertBootstrap HK_SELF_BS = ZhConvertBootstrap.newInstance().segment(Segments.hkSelfFastForward()).dataMap(DataMaps.hongKongSelf()).init();

    private ZhHkConverterUtil() {
    }

    public static boolean isChinese(char c) {
        return DEFAULT_BOOTSTRAP.isChinese(c);
    }

    public static boolean isChinese(String str) {
        return DEFAULT_BOOTSTRAP.isChinese(str);
    }

    public static boolean containsChinese(String str) {
        return DEFAULT_BOOTSTRAP.containsChinese(str);
    }

    public static boolean isSimple(char c) {
        return DEFAULT_BOOTSTRAP.isSimple(c);
    }

    public static boolean isSimple(String str) {
        return DEFAULT_BOOTSTRAP.isSimple(str);
    }

    public static boolean containsSimple(String str) {
        return DEFAULT_BOOTSTRAP.containsSimple(str);
    }

    public static boolean isTraditional(char c) {
        return DEFAULT_BOOTSTRAP.isTraditional(c);
    }

    public static boolean isTraditional(String str) {
        return DEFAULT_BOOTSTRAP.isTraditional(str);
    }

    public static boolean containsTraditional(String str) {
        return DEFAULT_BOOTSTRAP.containsTraditional(str);
    }

    public static List<String> simpleList(String str) {
        return DEFAULT_BOOTSTRAP.simpleList(str);
    }

    public static List<String> traditionalList(String str) {
        return DEFAULT_BOOTSTRAP.traditionalList(str);
    }

    public static String toSimple(String str) {
        return ZhConverterUtil.toSimple(HK_SELF_BS.toSimple(str));
    }

    public static String toTraditional(String str) {
        return HK_SELF_BS.toTraditional(ZhConverterUtil.toTraditional(str));
    }

    public static List<String> toTraditional(char c) {
        return DEFAULT_BOOTSTRAP.toTraditional(c);
    }

    public static List<String> toSimple(char c) {
        return DEFAULT_BOOTSTRAP.toSimple(c);
    }
}
