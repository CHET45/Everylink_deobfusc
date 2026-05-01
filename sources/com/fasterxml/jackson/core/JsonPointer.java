package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.p008io.NumberInput;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class JsonPointer implements Serializable {
    protected static final JsonPointer EMPTY = new JsonPointer();
    public static final char ESC = '~';
    public static final String ESC_SLASH = "~1";
    public static final String ESC_TILDE = "~0";
    public static final char SEPARATOR = '/';
    private static final long serialVersionUID = 1;
    protected final String _asString;
    protected final int _asStringOffset;
    protected int _hashCode;
    protected volatile JsonPointer _head;
    protected final int _matchingElementIndex;
    protected final String _matchingPropertyName;
    protected final JsonPointer _nextSegment;

    protected JsonPointer() {
        this._nextSegment = null;
        this._matchingPropertyName = null;
        this._matchingElementIndex = -1;
        this._asString = "";
        this._asStringOffset = 0;
    }

    protected JsonPointer(String str, int i, String str2, JsonPointer jsonPointer) {
        this._asString = str;
        this._asStringOffset = i;
        this._nextSegment = jsonPointer;
        this._matchingPropertyName = str2;
        this._matchingElementIndex = _parseIndex(str2);
    }

    protected JsonPointer(String str, int i, String str2, int i2, JsonPointer jsonPointer) {
        this._asString = str;
        this._asStringOffset = i;
        this._nextSegment = jsonPointer;
        this._matchingPropertyName = str2;
        this._matchingElementIndex = i2;
    }

    public static JsonPointer compile(String str) throws IllegalArgumentException {
        if (str == null || str.length() == 0) {
            return EMPTY;
        }
        if (str.charAt(0) != '/') {
            throw new IllegalArgumentException("Invalid input: JSON Pointer expression must start with '/': \"" + str + PunctuationConst.DOUBLE_QUOTES);
        }
        return _parseTail(str);
    }

    public static JsonPointer valueOf(String str) {
        return compile(str);
    }

    public static JsonPointer empty() {
        return EMPTY;
    }

    public static JsonPointer forPath(JsonStreamContext jsonStreamContext, boolean z) {
        JsonPointer jsonPointer;
        PointerSegment pointerSegment;
        if (jsonStreamContext == null) {
            return EMPTY;
        }
        if (!jsonStreamContext.hasPathSegment() && (!z || !jsonStreamContext.inRoot() || !jsonStreamContext.hasCurrentIndex())) {
            jsonStreamContext = jsonStreamContext.getParent();
        }
        int length = 0;
        PointerSegment pointerSegment2 = null;
        while (jsonStreamContext != null) {
            if (jsonStreamContext.inObject()) {
                String currentName = jsonStreamContext.getCurrentName();
                if (currentName == null) {
                    currentName = "";
                }
                length += currentName.length() + 2;
                pointerSegment = new PointerSegment(pointerSegment2, currentName, -1);
            } else if (!jsonStreamContext.inArray() && !z) {
                jsonStreamContext = jsonStreamContext.getParent();
            } else {
                length += 6;
                pointerSegment = new PointerSegment(pointerSegment2, null, jsonStreamContext.getCurrentIndex());
            }
            pointerSegment2 = pointerSegment;
            jsonStreamContext = jsonStreamContext.getParent();
        }
        if (pointerSegment2 == null) {
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder(length);
        PointerSegment pointerSegment3 = null;
        PointerSegment pointerSegment4 = pointerSegment2;
        while (pointerSegment4 != null) {
            pointerSegment4.pathOffset = sb.length();
            sb.append('/');
            if (pointerSegment4.property != null) {
                _appendEscaped(sb, pointerSegment4.property);
            } else {
                sb.append(pointerSegment4.index);
            }
            PointerSegment pointerSegment5 = pointerSegment4;
            pointerSegment4 = pointerSegment4.next;
            pointerSegment3 = pointerSegment5;
        }
        String string = sb.toString();
        JsonPointer jsonPointer2 = EMPTY;
        while (pointerSegment3 != null) {
            if (pointerSegment3.property != null) {
                jsonPointer = new JsonPointer(string, pointerSegment3.pathOffset, pointerSegment3.property, jsonPointer2);
            } else {
                int i = pointerSegment3.index;
                jsonPointer = new JsonPointer(string, pointerSegment3.pathOffset, String.valueOf(i), i, jsonPointer2);
            }
            jsonPointer2 = jsonPointer;
            pointerSegment3 = pointerSegment3.prev;
        }
        return jsonPointer2;
    }

    private static void _appendEscaped(StringBuilder sb, String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '/') {
                sb.append(ESC_SLASH);
            } else if (cCharAt == '~') {
                sb.append(ESC_TILDE);
            } else {
                sb.append(cCharAt);
            }
        }
    }

    public int length() {
        return this._asString.length() - this._asStringOffset;
    }

    public boolean matches() {
        return this._nextSegment == null;
    }

    public String getMatchingProperty() {
        return this._matchingPropertyName;
    }

    public int getMatchingIndex() {
        return this._matchingElementIndex;
    }

    public boolean mayMatchProperty() {
        return this._matchingPropertyName != null;
    }

    public boolean mayMatchElement() {
        return this._matchingElementIndex >= 0;
    }

    public JsonPointer last() {
        if (this == EMPTY) {
            return null;
        }
        JsonPointer jsonPointer = this;
        while (true) {
            JsonPointer jsonPointer2 = jsonPointer._nextSegment;
            if (jsonPointer2 == EMPTY) {
                return jsonPointer;
            }
            jsonPointer = jsonPointer2;
        }
    }

    public JsonPointer append(JsonPointer jsonPointer) {
        JsonPointer jsonPointer2 = EMPTY;
        if (this == jsonPointer2) {
            return jsonPointer;
        }
        if (jsonPointer == jsonPointer2) {
            return this;
        }
        return compile(toString() + jsonPointer.toString());
    }

    public JsonPointer appendProperty(String str) {
        if (str == null) {
            return this;
        }
        StringBuilder sbAppend = toStringBuilder(str.length() + 2).append('/');
        _appendEscaped(sbAppend, str);
        return compile(sbAppend.toString());
    }

    public JsonPointer appendIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Negative index cannot be appended");
        }
        return compile(toStringBuilder(8).append('/').append(i).toString());
    }

    public boolean matchesProperty(String str) {
        return this._nextSegment != null && this._matchingPropertyName.equals(str);
    }

    public JsonPointer matchProperty(String str) {
        if (this._nextSegment == null || !this._matchingPropertyName.equals(str)) {
            return null;
        }
        return this._nextSegment;
    }

    public boolean matchesElement(int i) {
        return i == this._matchingElementIndex && i >= 0;
    }

    public JsonPointer matchElement(int i) {
        if (i != this._matchingElementIndex || i < 0) {
            return null;
        }
        return this._nextSegment;
    }

    public JsonPointer tail() {
        return this._nextSegment;
    }

    public JsonPointer head() {
        JsonPointer jsonPointer_constructHead = this._head;
        if (jsonPointer_constructHead == null) {
            if (this != EMPTY) {
                jsonPointer_constructHead = _constructHead();
            }
            this._head = jsonPointer_constructHead;
        }
        return jsonPointer_constructHead;
    }

    public String toString() {
        int i = this._asStringOffset;
        if (i <= 0) {
            return this._asString;
        }
        return this._asString.substring(i);
    }

    protected StringBuilder toStringBuilder(int i) {
        if (this._asStringOffset <= 0) {
            return new StringBuilder(this._asString);
        }
        int length = this._asString.length();
        StringBuilder sb = new StringBuilder((length - this._asStringOffset) + i);
        sb.append((CharSequence) this._asString, this._asStringOffset, length);
        return sb;
    }

    public int hashCode() {
        int iHashCode = this._hashCode;
        if (iHashCode == 0) {
            iHashCode = toString().hashCode();
            if (iHashCode == 0) {
                iHashCode = -1;
            }
            this._hashCode = iHashCode;
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof JsonPointer)) {
            return false;
        }
        JsonPointer jsonPointer = (JsonPointer) obj;
        return _compare(this._asString, this._asStringOffset, jsonPointer._asString, jsonPointer._asStringOffset);
    }

    private final boolean _compare(String str, int i, String str2, int i2) {
        int length = str.length();
        if (length - i != str2.length() - i2) {
            return false;
        }
        while (i < length) {
            int i3 = i + 1;
            int i4 = i2 + 1;
            if (str.charAt(i) != str2.charAt(i2)) {
                return false;
            }
            i = i3;
            i2 = i4;
        }
        return true;
    }

    private static final int _parseIndex(String str) {
        int length = str.length();
        if (length == 0 || length > 10) {
            return -1;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt <= '0') {
            return (length == 1 && cCharAt == '0') ? 0 : -1;
        }
        if (cCharAt > '9') {
            return -1;
        }
        for (int i = 1; i < length; i++) {
            char cCharAt2 = str.charAt(i);
            if (cCharAt2 > '9' || cCharAt2 < '0') {
                return -1;
            }
        }
        if (length == 10) {
            long j = Long.parseLong(str);
            if (j > 2147483647L) {
                return -1;
            }
            return (int) j;
        }
        return NumberInput.parseInt(str);
    }

    protected static JsonPointer _parseTail(String str) {
        int length = str.length();
        PointerParent pointerParent = null;
        int i = 1;
        int i2 = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '/') {
                PointerParent pointerParent2 = new PointerParent(pointerParent, i2, str.substring(i2 + 1, i));
                i2 = i;
                i++;
                pointerParent = pointerParent2;
            } else {
                i++;
                if (cCharAt == '~' && i < length) {
                    StringBuilder sb = new StringBuilder(32);
                    int i_extractEscapedSegment = _extractEscapedSegment(str, i2 + 1, i, sb);
                    String string = sb.toString();
                    if (i_extractEscapedSegment < 0) {
                        return _buildPath(str, i2, string, pointerParent);
                    }
                    PointerParent pointerParent3 = new PointerParent(pointerParent, i2, string);
                    i2 = i_extractEscapedSegment;
                    i = i_extractEscapedSegment + 1;
                    pointerParent = pointerParent3;
                }
            }
        }
        return _buildPath(str, i2, str.substring(i2 + 1), pointerParent);
    }

    private static JsonPointer _buildPath(String str, int i, String str2, PointerParent pointerParent) {
        JsonPointer jsonPointer = new JsonPointer(str, i, str2, EMPTY);
        while (pointerParent != null) {
            JsonPointer jsonPointer2 = new JsonPointer(str, pointerParent.fullPathOffset, pointerParent.segment, jsonPointer);
            pointerParent = pointerParent.parent;
            jsonPointer = jsonPointer2;
        }
        return jsonPointer;
    }

    protected static int _extractEscapedSegment(String str, int i, int i2, StringBuilder sb) {
        int length = str.length();
        int i3 = i2 - 1;
        if (i3 - i > 0) {
            sb.append((CharSequence) str, i, i3);
        }
        int i4 = i2 + 1;
        _appendEscape(sb, str.charAt(i2));
        while (i4 < length) {
            char cCharAt = str.charAt(i4);
            if (cCharAt == '/') {
                return i4;
            }
            int i5 = i4 + 1;
            if (cCharAt == '~' && i5 < length) {
                i4 += 2;
                _appendEscape(sb, str.charAt(i5));
            } else {
                sb.append(cCharAt);
                i4 = i5;
            }
        }
        return -1;
    }

    private static void _appendEscape(StringBuilder sb, char c) {
        if (c == '0') {
            c = '~';
        } else if (c == '1') {
            c = '/';
        } else {
            sb.append(ESC);
        }
        sb.append(c);
    }

    protected JsonPointer _constructHead() {
        JsonPointer jsonPointerLast = last();
        if (jsonPointerLast == this) {
            return EMPTY;
        }
        int length = jsonPointerLast.length();
        JsonPointer jsonPointer = this._nextSegment;
        String string = toString();
        return new JsonPointer(string.substring(0, string.length() - length), 0, this._matchingPropertyName, this._matchingElementIndex, jsonPointer._constructHead(length, jsonPointerLast));
    }

    protected JsonPointer _constructHead(int i, JsonPointer jsonPointer) {
        if (this == jsonPointer) {
            return EMPTY;
        }
        JsonPointer jsonPointer2 = this._nextSegment;
        String string = toString();
        return new JsonPointer(string.substring(0, string.length() - i), 0, this._matchingPropertyName, this._matchingElementIndex, jsonPointer2._constructHead(i, jsonPointer));
    }

    private static class PointerParent {
        public final int fullPathOffset;
        public final PointerParent parent;
        public final String segment;

        PointerParent(PointerParent pointerParent, int i, String str) {
            this.parent = pointerParent;
            this.fullPathOffset = i;
            this.segment = str;
        }
    }

    private static class PointerSegment {
        public final int index;
        public final PointerSegment next;
        public int pathOffset;
        public PointerSegment prev;
        public final String property;

        public PointerSegment(PointerSegment pointerSegment, String str, int i) {
            this.next = pointerSegment;
            this.property = str;
            this.index = i;
            if (pointerSegment != null) {
                pointerSegment.prev = this;
            }
        }
    }

    private Object writeReplace() {
        return new Serialization(toString());
    }

    static class Serialization implements Externalizable {
        private String _fullPath;

        public Serialization() {
        }

        Serialization(String str) {
            this._fullPath = str;
        }

        @Override // java.io.Externalizable
        public void writeExternal(ObjectOutput objectOutput) throws IOException {
            objectOutput.writeUTF(this._fullPath);
        }

        @Override // java.io.Externalizable
        public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            this._fullPath = objectInput.readUTF();
        }

        private Object readResolve() throws ObjectStreamException {
            return JsonPointer.compile(this._fullPath);
        }
    }
}
