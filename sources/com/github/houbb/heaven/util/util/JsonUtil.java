package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.p010io.FileUtil;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/* JADX INFO: loaded from: classes3.dex */
public final class JsonUtil {
    private JsonUtil() {
    }

    public static List<String> getIndexList(String str, int i) {
        if (StringUtil.isEmptyTrim(FileUtil.getFileContent(str)) || i <= 0) {
            return Collections.emptyList();
        }
        return getIndexList(str, CollectionUtil.fill(i));
    }

    public static List<String> getIndexList(String str, List<?> list) {
        String fileContent = FileUtil.getFileContent(str);
        if (StringUtil.isEmptyTrim(fileContent) || CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> listNewArrayList = Guavas.newArrayList(list.size());
        Stack stack = new Stack();
        List listNewArrayList2 = Guavas.newArrayList(list.size());
        for (int i = 0; i < fileContent.length(); i++) {
            char cCharAt = fileContent.charAt(i);
            if ('{' == cCharAt) {
                stack.push(Integer.valueOf(i));
            }
            if ('}' == cCharAt) {
                Integer num = (Integer) stack.pop();
                int length = fileContent.substring(0, num.intValue()).getBytes().length;
                listNewArrayList2.add(length + PunctuationConst.COMMA + (fileContent.substring(num.intValue(), i + 1).getBytes().length + length));
            }
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            listNewArrayList.add(getPrefix(list.get(i2)) + ((String) listNewArrayList2.get(i2)));
        }
        return listNewArrayList;
    }

    private static String getPrefix(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            return "";
        }
        String string = obj.toString();
        return StringUtil.isEmptyTrim(string) ? "" : string + " ";
    }
}
