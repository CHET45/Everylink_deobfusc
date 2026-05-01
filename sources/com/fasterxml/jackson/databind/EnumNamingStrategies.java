package com.fasterxml.jackson.databind;

import com.github.houbb.heaven.constant.PunctuationConst;

/* JADX INFO: loaded from: classes3.dex */
public class EnumNamingStrategies {
    private EnumNamingStrategies() {
    }

    public static class CamelCaseStrategy implements EnumNamingStrategy {
        public static final CamelCaseStrategy INSTANCE = new CamelCaseStrategy();

        @Override // com.fasterxml.jackson.databind.EnumNamingStrategy
        public String convertEnumToExternalName(String str) {
            StringBuilder sb = null;
            if (str == null) {
                return null;
            }
            int length = 0;
            int iIndexIn = -1;
            do {
                iIndexIn = indexIn(str, iIndexIn + 1);
                if (iIndexIn != -1) {
                    if (length == 0) {
                        sb = new StringBuilder(str.length() + (PunctuationConst.UNDERLINE.length() * 4));
                        sb.append(toLowerCase(str.substring(length, iIndexIn)));
                    } else {
                        sb.append(normalizeWord(str.substring(length, iIndexIn)));
                    }
                    length = PunctuationConst.UNDERLINE.length() + iIndexIn;
                }
            } while (iIndexIn != -1);
            if (length == 0) {
                return toLowerCase(str);
            }
            sb.append(normalizeWord(str.substring(length)));
            return sb.toString();
        }

        private static int indexIn(CharSequence charSequence, int i) {
            int length = charSequence.length();
            while (i < length) {
                if ('_' == charSequence.charAt(i)) {
                    return i;
                }
                i++;
            }
            return -1;
        }

        private static String normalizeWord(String str) {
            int length = str.length();
            return length == 0 ? str : new StringBuilder(length).append(charToUpperCaseIfLower(str.charAt(0))).append(toLowerCase(str.substring(1))).toString();
        }

        private static String toLowerCase(String str) {
            int length = str.length();
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(charToLowerCaseIfUpper(str.charAt(i)));
            }
            return sb.toString();
        }

        private static char charToUpperCaseIfLower(char c) {
            return Character.isLowerCase(c) ? Character.toUpperCase(c) : c;
        }

        private static char charToLowerCaseIfUpper(char c) {
            return Character.isUpperCase(c) ? Character.toLowerCase(c) : c;
        }
    }
}
