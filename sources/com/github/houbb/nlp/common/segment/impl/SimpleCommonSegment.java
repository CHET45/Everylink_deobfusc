package com.github.houbb.nlp.common.segment.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.CharUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.nlp.common.util.CharUtils;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
public class SimpleCommonSegment extends AbstractCommonSegment {
    @Override // com.github.houbb.nlp.common.segment.impl.AbstractCommonSegment
    protected List<String> doSegment(String str) {
        List<String> listNewArrayList = Guavas.newArrayList();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (CharUtil.isChinese(c)) {
                sb.append(c);
                processOtherChars(sb2, listNewArrayList);
            } else {
                sb2.append(c);
                processChineseChars(sb, listNewArrayList);
            }
        }
        processChineseChars(sb, listNewArrayList);
        processOtherChars(sb2, listNewArrayList);
        return listNewArrayList;
    }

    protected List<String> getChineseSegments(String str) {
        return StringUtil.toCharStringList(str);
    }

    private void processChineseChars(StringBuilder sb, List<String> list) {
        if (sb.length() <= 0) {
            return;
        }
        list.addAll(getChineseSegments(sb.toString()));
        sb.setLength(0);
    }

    private void processOtherChars(StringBuilder sb, List<String> list) {
        int length = sb.length();
        if (length <= 0) {
            return;
        }
        if (1 == length) {
            list.add(sb.toString());
        } else {
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < sb.length(); i++) {
                char cCharAt = sb.charAt(i);
                if (CharUtil.isDigitOrLetter(cCharAt) || CharUtils.isLetterOrConnector(cCharAt)) {
                    sb2.append(cCharAt);
                } else {
                    if (sb2.length() > 0) {
                        list.add(sb2.toString());
                        sb2.setLength(0);
                    }
                    list.add(String.valueOf(cCharAt));
                }
            }
            if (sb2.length() > 0) {
                list.add(sb2.toString());
                sb2.setLength(0);
            }
        }
        sb.setLength(0);
    }
}
