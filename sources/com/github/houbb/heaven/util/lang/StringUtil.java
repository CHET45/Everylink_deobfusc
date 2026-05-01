package com.github.houbb.heaven.util.lang;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.response.exception.CommonRuntimeException;
import com.github.houbb.heaven.support.condition.ICondition;
import com.github.houbb.heaven.support.handler.IHandler;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.heaven.util.lang.reflect.ClassTypeUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.heaven.util.util.SystemUtil;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public final class StringUtil {
    public static final String BLANK = " ";
    private static final Pattern BLANK_PATTERN = Pattern.compile("\\s*|\t|\r|\n");
    public static final String EMPTY = "";
    public static final String EMPTY_JSON = "{}";
    public static final String LETTERS_LOWER = "abcdefghijklmnopqrstuvwsxyz";
    public static final String LETTERS_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWSXYZ";
    public static final String NEW_LINE = "";

    @Deprecated
    private static char getPreChar(char c, char c2) {
        if ('\\' == c && '\\' == c2) {
            return ' ';
        }
        return c2;
    }

    private StringUtil() {
    }

    public static boolean isNotReturnLine(String str) {
        return !isReturnLine(str);
    }

    public static boolean isReturnLine(String str) {
        return isEmpty(str) || isEmpty(str.trim()) || "".equals(str);
    }

    public static boolean isUpperCase(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerCase(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsUppercase(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsLowercase(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLetter(String str) {
        return isCharsCondition(str, new ICondition<Character>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.1
            @Override // com.github.houbb.heaven.support.condition.ICondition
            public boolean condition(Character ch) {
                return Character.isLowerCase(ch.charValue()) || Character.isUpperCase(ch.charValue());
            }
        });
    }

    public static boolean isDigit(String str) {
        return isCharsCondition(str, new ICondition<Character>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.2
            @Override // com.github.houbb.heaven.support.condition.ICondition
            public boolean condition(Character ch) {
                return Character.isDigit(ch.charValue());
            }
        });
    }

    public static boolean isDigitOrLetter(String str) {
        return isCharsCondition(str, new ICondition<Character>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.3
            @Override // com.github.houbb.heaven.support.condition.ICondition
            public boolean condition(Character ch) {
                return CharUtil.isDigitOrLetter(ch.charValue());
            }
        });
    }

    public static boolean isNumber(String str) {
        return isCharsCondition(str, new ICondition<Character>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.4
            @Override // com.github.houbb.heaven.support.condition.ICondition
            public boolean condition(Character ch) {
                return CharUtil.isNumber(ch.charValue());
            }
        });
    }

    private static boolean isCharsCondition(String str, ICondition<Character> iCondition) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!iCondition.condition(Character.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isEmptyTrim(String str) {
        if (isEmpty(str)) {
            return true;
        }
        return isEmpty(trim(str));
    }

    public static boolean isNotEmptyTrim(String str) {
        return !isEmptyTrim(str);
    }

    public static boolean isEmptyJson(String str) {
        if (isEmptyTrim(str)) {
            return true;
        }
        return EMPTY_JSON.equals(str.trim());
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String[] splitByAnyBlank(String str) {
        if (isEmpty(str)) {
            return new String[0];
        }
        return str.split("\\s+|\u0013");
    }

    public static String trimAnyBlank(String str) {
        return isEmpty(str) ? str : str.trim().replaceAll("\\s+|\u0013", "");
    }

    public static String replaceAnyBlank(String str, String str2) {
        return isEmpty(str) ? str : BLANK_PATTERN.matcher(str).replaceAll(str2).replaceAll("\\u00A0", str2);
    }

    public static String replaceAnyBlank(String str) {
        return replaceAnyBlank(str, "");
    }

    public static String trimAnyPunctionAndSymbol(String str) {
        return isEmpty(str) ? str : str.trim().replaceAll("\\p{P}|\\p{S}", "");
    }

    public static String getCamelCaseString(String str, boolean z) {
        StringBuilder sb = new StringBuilder();
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == ' ' || cCharAt == '&' || cCharAt == '-' || cCharAt == '/' || cCharAt == '@' || cCharAt == '_' || cCharAt == '#' || cCharAt == '$') {
                if (sb.length() > 0) {
                    z2 = true;
                }
            } else if (z2) {
                sb.append(Character.toUpperCase(cCharAt));
                z2 = false;
            } else {
                sb.append(Character.toLowerCase(cCharAt));
            }
        }
        if (z) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        return sb.toString();
    }

    public static String firstToLowerCase(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String firstToUpperCase(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String defaultEmpty(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static String join(Object... objArr) {
        return join(objArr, PunctuationConst.COMMA);
    }

    public static String join(Object[] objArr, String str) {
        return join(objArr, str, 0, ArrayUtil.getEndIndex(-1, objArr));
    }

    public static String join(String str, Object... objArr) {
        return join(objArr, str);
    }

    public static String join(Object[] objArr, String str, int i, int i2) {
        if (objArr == null) {
            return null;
        }
        if (str == null) {
            str = "";
        }
        int i3 = i2 - i;
        if (i3 < 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(i3 * 16);
        for (int i4 = i; i4 <= i2; i4++) {
            if (i4 > i) {
                sb.append(str);
            }
            Object obj = objArr[i4];
            if (obj != null) {
                sb.append(obj);
            }
        }
        return sb.toString();
    }

    public static <E> String join(Collection<E> collection, String str, int i, int i2) {
        if (CollectionUtil.isEmpty(collection)) {
            return "";
        }
        String strNullToDefault = nullToDefault(str, "");
        StringBuilder sb = new StringBuilder();
        Iterator<E> it = collection.iterator();
        for (int i3 = 0; i3 < i; i3++) {
            it.next();
        }
        sb.append(it.next().toString());
        while (i < i2) {
            sb.append(strNullToDefault).append(it.next().toString());
            i++;
        }
        return sb.toString();
    }

    public static <E> String join(Collection<E> collection, String str) {
        return join(collection, str, 0, CollectionUtil.getEndIndex(-1, collection));
    }

    public static <E> String join(Collection<E> collection) {
        return join(collection, PunctuationConst.COMMA);
    }

    public static String camelToUnderline(String str) {
        if (isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_');
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String underlineToCamel(String str) {
        if (isEmpty(str)) {
            return "";
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '_') {
                i++;
                if (i < length) {
                    sb.append(Character.toUpperCase(str.charAt(i)));
                }
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        return sb.toString();
    }

    public static String repeat(String str, int i) {
        if (isEmpty(str) || i <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String buildString(Object obj, String str, int i) {
        String strSubstring;
        String strSubstring2;
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        String string = obj.toString();
        int length = string.length();
        if (length >= i) {
            strSubstring = string.substring(0, i);
        } else {
            strSubstring = string.substring(0, length);
        }
        int length2 = (length - strSubstring.length()) - str.length();
        if (length2 <= 0) {
            strSubstring2 = "";
        } else {
            strSubstring2 = string.substring(length - length2);
        }
        return strSubstring + str + strSubstring2;
    }

    public static String trim(String str) {
        return isEmpty(str) ? str : str.trim();
    }

    public static String nullToDefault(CharSequence charSequence, String str) {
        return charSequence == null ? str : charSequence.toString();
    }

    public static String fill(String str, char c, int i, boolean z) {
        int length = str.length();
        if (length > i) {
            return str;
        }
        String strRepeat = repeat(String.valueOf(c), i - length);
        return z ? strRepeat.concat(str) : str.concat(strRepeat);
    }

    public static String objectToString(Object obj, String str) {
        if (ObjectUtil.isNull(obj)) {
            return str;
        }
        if (ClassTypeUtil.isArray(obj.getClass())) {
            return Arrays.toString((Object[]) obj);
        }
        return obj.toString();
    }

    public static String objectToString(Object obj) {
        return objectToString(obj, null);
    }

    @Deprecated
    public static String times(String str, int i) {
        if (isEmpty(str) || i <= 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String capitalFirst(String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() <= 1) {
            return str.toUpperCase();
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static List<String> splitStrictly(String str, char c, int i) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        if (i <= 0) {
            return Collections.singletonList(str);
        }
        String strRepeat = CharUtil.repeat(c, i);
        String strRepeat2 = CharUtil.repeat(c, i + 1);
        List<Integer> indexList = getIndexList(str, strRepeat);
        return subStringList(str, CollectionUtil.difference(indexList, getSerialFilterList(indexList, getIndexList(str, strRepeat2), i)), i);
    }

    private static List<Integer> getSerialFilterList(List<Integer> list, List<Integer> list2, int i) {
        List<Integer> listNewArrayList = Guavas.newArrayList();
        listNewArrayList.addAll(list2);
        for (Integer num : list2) {
            int i2 = 1;
            for (int iIndexOf = list.indexOf(num) + 1; iIndexOf < list.size(); iIndexOf++) {
                Integer num2 = list.get(iIndexOf);
                int iIntValue = (i * i2) + num.intValue();
                if (num2.equals(Integer.valueOf(iIntValue))) {
                    listNewArrayList.add(Integer.valueOf(iIntValue));
                    i2++;
                }
            }
        }
        return listNewArrayList;
    }

    public static List<String> subStringList(String str, Collection<Integer> collection, int i) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        if (CollectionUtil.isEmpty(collection)) {
            return Collections.singletonList(str);
        }
        List<String> listNewArrayList = Guavas.newArrayList(collection.size());
        Iterator<Integer> it = collection.iterator();
        int iIntValue = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Integer next = it.next();
            if (iIntValue > str.length() - 1) {
                listNewArrayList.add("");
                break;
            }
            listNewArrayList.add(str.substring(iIntValue, next.intValue()));
            iIntValue = next.intValue() + i;
        }
        if (iIntValue < str.length()) {
            listNewArrayList.add(str.substring(iIntValue));
        }
        return listNewArrayList;
    }

    public static List<Integer> getIndexList(String str, String str2) {
        int iIndexOf;
        if (isEmpty(str) || isEmpty(str2)) {
            return Collections.emptyList();
        }
        List<Integer> listNewArrayList = Guavas.newArrayList();
        int length = 0;
        while (length < str.length() && (iIndexOf = str.indexOf(str2, length)) >= 0) {
            listNewArrayList.add(Integer.valueOf(iIndexOf));
            length = iIndexOf + str2.length();
        }
        return listNewArrayList;
    }

    public static List<Integer> getIndexList(String str, char c, boolean z) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        List<Integer> listNewArrayList = Guavas.newArrayList();
        char[] charArray = str.toCharArray();
        char preChar = ' ';
        boolean z2 = false;
        for (int i = 0; i < charArray.length; i++) {
            char c2 = charArray[i];
            preChar = getPreChar(preChar, c2);
            if ('\\' != preChar && '\"' == c2) {
                z2 = !z2;
            }
            if (c2 == c) {
                if (!z) {
                    listNewArrayList.add(Integer.valueOf(i));
                } else if (!z2) {
                    listNewArrayList.add(Integer.valueOf(i));
                }
            }
        }
        return listNewArrayList;
    }

    public static List<String> splitByIndexes(String str, List<Integer> list) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        if (CollectionUtil.isEmpty(list)) {
            return Collections.singletonList(str);
        }
        List<String> listNewArrayList = Guavas.newArrayList(list.size() + 1);
        Iterator<Integer> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            if (iIntValue > i) {
                listNewArrayList.add(str.substring(i, iIntValue));
            }
            i = iIntValue + 1;
        }
        int iIntValue2 = list.get(list.size() - 1).intValue() + 1;
        if (iIntValue2 < str.length()) {
            listNewArrayList.add(str.substring(iIntValue2));
        }
        return listNewArrayList;
    }

    public static byte[] stringToBytes(String str) {
        if (ObjectUtil.isNull(str)) {
            return null;
        }
        return str.getBytes();
    }

    public static String bytesToString(byte[] bArr) {
        if (ArrayPrimitiveUtil.isEmpty(bArr)) {
            return null;
        }
        return new String(bArr);
    }

    public static String[] splitToStringArray(String str, String str2) {
        if (isEmpty(str)) {
            return null;
        }
        return str.split(str2);
    }

    public static String[] splitToStringArray(String str) {
        return splitToStringArray(str, PunctuationConst.COMMA);
    }

    public static String join(byte[] bArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(bArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(bArr, (IHandler) new IHandler<Byte, Byte>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.5
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Byte handle(Byte b) {
                return b;
            }
        }), getSplitter(strArr));
    }

    public static String join(char[] cArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(cArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(cArr, (IHandler) new IHandler<Character, Character>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.6
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Character handle(Character ch) {
                return ch;
            }
        }), getSplitter(strArr));
    }

    public static String join(short[] sArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(sArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(sArr, (IHandler) new IHandler<Short, Short>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.7
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Short handle(Short sh) {
                return sh;
            }
        }), getSplitter(strArr));
    }

    public static String join(long[] jArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(jArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(jArr, (IHandler) new IHandler<Long, Long>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.8
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Long handle(Long l) {
                return l;
            }
        }), getSplitter(strArr));
    }

    public static String join(float[] fArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(fArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(fArr, (IHandler) new IHandler<Float, Float>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.9
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Float handle(Float f) {
                return f;
            }
        }), getSplitter(strArr));
    }

    public static String join(double[] dArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(dArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(dArr, new IHandler<Double, Double>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.10
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Double handle(Double d) {
                return d;
            }
        }), getSplitter(strArr));
    }

    public static String join(boolean[] zArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(zArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(zArr, new IHandler<Boolean, Boolean>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.11
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Boolean handle(Boolean bool) {
                return bool;
            }
        }), getSplitter(strArr));
    }

    public static String join(int[] iArr, String... strArr) {
        if (ArrayPrimitiveUtil.isEmpty(iArr)) {
            return "";
        }
        return join(ArrayPrimitiveUtil.toList(iArr, (IHandler) new IHandler<Integer, Integer>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.12
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Integer handle(Integer num) {
                return num;
            }
        }), getSplitter(strArr));
    }

    private static String getSplitter(String... strArr) {
        if (ArrayUtil.isEmpty(strArr)) {
            return PunctuationConst.COMMA;
        }
        return strArr[0];
    }

    public static List<String> splitToList(String str, String str2) {
        ArgUtil.notEmpty(str2, "splitter");
        if (isEmpty(str)) {
            return Guavas.newArrayList();
        }
        return ArrayUtil.toList(str.split(str2));
    }

    public static List<String> splitToList(String str, char c) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        return splitToList(str.toCharArray(), c);
    }

    public static List<String> splitToList(char[] cArr, char c) {
        if (ArrayPrimitiveUtil.isEmpty(cArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (char c2 : cArr) {
            if (c == c2) {
                arrayList.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c2);
            }
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public static List<String> splitToList(String str) {
        return splitToList(str, PunctuationConst.COMMA);
    }

    public static Character[] toCharacterArray(String str) {
        char[] charArray = str.toCharArray();
        Character[] chArr = new Character[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            chArr[i] = Character.valueOf(charArray[i]);
        }
        return chArr;
    }

    public static List<Character> toCharacterList(String str) {
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList(charArray.length);
        for (char c : charArray) {
            arrayList.add(Character.valueOf(c));
        }
        return arrayList;
    }

    public static List<String> toCharStringList(String str) {
        if (isEmpty(str)) {
            return Guavas.newArrayList();
        }
        return ArrayPrimitiveUtil.toList(str.toCharArray(), (IHandler) new IHandler<Character, String>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.13
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public String handle(Character ch) {
                return String.valueOf(ch);
            }
        });
    }

    public static Set<Character> toCharSet(String str) {
        if (isEmpty(str)) {
            return Guavas.newHashSet();
        }
        char[] charArray = str.toCharArray();
        HashSet hashSet = new HashSet();
        for (char c : charArray) {
            hashSet.add(Character.valueOf(c));
        }
        return hashSet;
    }

    public static String toHalfWidth(String str) {
        return characterHandler(str, new IHandler<Character, Character>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.14
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Character handle(Character ch) {
                return Character.valueOf(CharUtil.toHalfWidth(ch.charValue()));
            }
        });
    }

    public static String toFullWidth(String str) {
        return characterHandler(str, new IHandler<Character, Character>() { // from class: com.github.houbb.heaven.util.lang.StringUtil.15
            @Override // com.github.houbb.heaven.support.handler.IHandler
            public Character handle(Character ch) {
                return Character.valueOf(CharUtil.toFullWidth(ch.charValue()));
            }
        });
    }

    private static String characterHandler(String str, IHandler<Character, Character> iHandler) {
        if (isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        char[] cArr = new char[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            cArr[i] = iHandler.handle(Character.valueOf(charArray[i])).charValue();
        }
        return new String(cArr);
    }

    public static String trimNotChinese(String str) {
        if (isEmptyTrim(str)) {
            return "";
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            Character chValueOf = Character.valueOf(c);
            if (CharUtil.isChinese(chValueOf.charValue())) {
                sb.append(chValueOf);
            }
        }
        return sb.toString();
    }

    public static String valueOf(Object obj) {
        if (ObjectUtil.isNull(obj)) {
            return null;
        }
        return String.valueOf(obj);
    }

    public static String leftPadding(String str, int i, char c) {
        ArgUtil.notNull(str, "original");
        int length = str.length();
        if (length >= i) {
            return str;
        }
        StringBuilder sb = new StringBuilder(i);
        while (length < i) {
            sb.append(c);
            length++;
        }
        sb.append(str);
        return sb.toString();
    }

    public static String leftPadding(String str, int i) {
        return leftPadding(str, i, '0');
    }

    public static Character getFirstChar(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Character.valueOf(str.charAt(0));
    }

    public static String emptyToNull(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static Boolean toBool(String str) {
        return Boolean.valueOf("YES".equalsIgnoreCase(str) || "TRUE".equalsIgnoreCase(str) || "1".equalsIgnoreCase(str));
    }

    public static Character toChar(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Character.valueOf(str.charAt(0));
    }

    public static Byte toByte(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Byte.valueOf(str);
    }

    public static Short toShort(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Short.valueOf(str);
    }

    public static Integer toInt(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Integer.valueOf(str);
    }

    public static Long toLong(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Long.valueOf(str);
    }

    public static Float toFloat(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Float.valueOf(str);
    }

    public static Double toDouble(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static BigInteger toBigInteger(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return new BigInteger(str);
    }

    public static BigDecimal toBigDecimal(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return new BigDecimal(str);
    }

    public static Date toDate(String str, String str2) {
        if (isEmpty(str)) {
            return null;
        }
        return DateUtil.getFormatDate(str, str2);
    }

    public static Date toDate(String str) {
        return toDate(str, DateUtil.PURE_DATE_FORMAT);
    }

    public static String toString(Date date, String str) {
        return DateUtil.getDateFormat(date, str);
    }

    public static String toString(Date date) {
        return toString(date, DateUtil.PURE_DATE_FORMAT);
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static String toString(byte[] bArr, String str) {
        try {
            return new String(bArr, str);
        } catch (UnsupportedEncodingException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static String toString(byte[] bArr) {
        return toString(bArr, "UTF-8");
    }

    public static byte[] getBytes(String str, String str2) {
        try {
            return str.getBytes(str2);
        } catch (UnsupportedEncodingException e) {
            throw new CommonRuntimeException(e);
        }
    }

    public static byte[] getBytes(String str) {
        return getBytes(str, "UTF-8");
    }

    public static boolean isEnglish(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!CharUtil.isEnglish(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isChinese(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!CharUtil.isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    public static String packageToPath(String str) {
        return isEmpty(str) ? str : str.replaceAll("\\.", "/");
    }

    public static String subString(String str, int i, int i2) {
        if (isEmpty(str)) {
            return str;
        }
        if (i2 <= 0) {
            return null;
        }
        int length = i2 + i;
        if (length > str.length()) {
            length = str.length();
        }
        return str.substring(i, length);
    }

    public static List<String> contentToLines(String str) {
        if (str == null) {
            return null;
        }
        return ArrayUtil.toList(str.split("\\r?\\n"));
    }

    public static String linesToContent(List<String> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append(list.get(i)).append(SystemUtil.getLineSeparator());
        }
        sb.append(list.get(list.size() - 1));
        return sb.toString();
    }

    public static List<String> splitByLength(String str, int i) {
        if (isEmpty(str)) {
            return Collections.emptyList();
        }
        int length = str.length();
        int i2 = length / i;
        if (length % i != 0) {
            i2++;
        }
        ArrayList arrayList = new ArrayList(i2);
        int i3 = 0;
        while (i3 < i2) {
            int i4 = i3 * i;
            i3++;
            int i5 = i3 * i;
            if (i5 > length) {
                i5 = length;
            }
            arrayList.add(str.substring(i4, i5));
        }
        return arrayList;
    }

    public static List<String> getAllSubStrList(String str, int i) {
        ArgUtil.positive(i, "stepLen");
        if (isEmpty(str) || i > str.length()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        while (i < length) {
            for (int i2 = 0; i2 <= length - i; i2++) {
                arrayList.add(str.substring(i2, i2 + i));
            }
            i++;
        }
        return arrayList;
    }

    public static String replaceEmoji(String str, String str2) {
        return isEmpty(str) ? str : str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", str2);
    }

    public static String replaceEmoji(String str) {
        return replaceEmoji(str, "");
    }

    public static Set<Character> getCharSet(String str) {
        if (isEmpty(str)) {
            return Collections.emptySet();
        }
        char[] charArray = str.toCharArray();
        HashSet hashSet = new HashSet(charArray.length);
        for (char c : charArray) {
            hashSet.add(Character.valueOf(c));
        }
        return hashSet;
    }

    public static String subWithBytes(String str, int i, String str2) {
        if (isEmpty(str)) {
            return str;
        }
        try {
            byte[] bytes = str.getBytes(str2);
            return bytes.length <= i ? str : new String(bytes, 0, i, str2);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String subWithBytes(String str, int i) {
        return subWithBytes(str, i, "UTF-8");
    }
}
