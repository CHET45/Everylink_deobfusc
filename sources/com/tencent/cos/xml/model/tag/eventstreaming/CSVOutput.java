package com.tencent.cos.xml.model.tag.eventstreaming;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class CSVOutput implements Serializable, Cloneable {
    private String fieldDelimiter;
    private String quoteCharacter;
    private String quoteEscapeCharacter;
    private String quoteFields;
    private String recordDelimiter;

    public CSVOutput(String str, String str2, String str3, String str4, String str5) {
        this.quoteFields = str;
        this.recordDelimiter = str2;
        this.fieldDelimiter = str3;
        this.quoteCharacter = str4;
        this.quoteEscapeCharacter = str5;
    }

    public String getQuoteFields() {
        return this.quoteFields;
    }

    public void setQuoteFields(String str) {
        this.quoteFields = str;
    }

    public CSVOutput withQuoteFields(String str) {
        setQuoteFields(str);
        return this;
    }

    public void setQuoteFields(QuoteFields quoteFields) {
        setQuoteFields(quoteFields == null ? null : quoteFields.toString());
    }

    public CSVOutput withQuoteFields(QuoteFields quoteFields) {
        setQuoteFields(quoteFields);
        return this;
    }

    public Character getQuoteEscapeCharacter() {
        return stringToChar(this.quoteEscapeCharacter);
    }

    public String getQuoteEscapeCharacterAsString() {
        return this.quoteEscapeCharacter;
    }

    public void setQuoteEscapeCharacter(String str) {
        validateNotEmpty(str, "quoteEscapeCharacter");
        this.quoteEscapeCharacter = str;
    }

    public CSVOutput withQuoteEscapeCharacter(String str) {
        setQuoteEscapeCharacter(str);
        return this;
    }

    public void setQuoteEscapeCharacter(Character ch) {
        setQuoteEscapeCharacter(charToString(ch));
    }

    public CSVOutput withQuoteEscapeCharacter(Character ch) {
        setQuoteEscapeCharacter(ch);
        return this;
    }

    public Character getRecordDelimiter() {
        return stringToChar(this.recordDelimiter);
    }

    public String getRecordDelimiterAsString() {
        return this.recordDelimiter;
    }

    public void setRecordDelimiter(String str) {
        validateNotEmpty(str, "recordDelimiter");
        this.recordDelimiter = str;
    }

    public CSVOutput withRecordDelimiter(String str) {
        setRecordDelimiter(str);
        return this;
    }

    public void setRecordDelimiter(Character ch) {
        setRecordDelimiter(charToString(ch));
    }

    public CSVOutput withRecordDelimiter(Character ch) {
        setRecordDelimiter(ch);
        return this;
    }

    public Character getFieldDelimiter() {
        return stringToChar(this.fieldDelimiter);
    }

    public String getFieldDelimiterAsString() {
        return this.fieldDelimiter;
    }

    public void setFieldDelimiter(String str) {
        validateNotEmpty(str, "fieldDelimiter");
        this.fieldDelimiter = str;
    }

    public CSVOutput withFieldDelimiter(String str) {
        setFieldDelimiter(str);
        return this;
    }

    public void setFieldDelimiter(Character ch) {
        setFieldDelimiter(charToString(ch));
    }

    public CSVOutput withFieldDelimiter(Character ch) {
        setFieldDelimiter(ch);
        return this;
    }

    public Character getQuoteCharacter() {
        return stringToChar(this.quoteCharacter);
    }

    public String getQuoteCharacterAsString() {
        return this.quoteCharacter;
    }

    public void setQuoteCharacter(String str) {
        validateNotEmpty(str, "quoteCharacter");
        this.quoteCharacter = str;
    }

    public CSVOutput withQuoteCharacter(String str) {
        setQuoteCharacter(str);
        return this;
    }

    public void setQuoteCharacter(Character ch) {
        setQuoteCharacter(charToString(ch));
    }

    public CSVOutput withQuoteCharacter(Character ch) {
        setQuoteCharacter(ch);
        return this;
    }

    private String charToString(Character ch) {
        if (ch == null) {
            return null;
        }
        return ch.toString();
    }

    private Character stringToChar(String str) {
        if (str == null) {
            return null;
        }
        return Character.valueOf(str.charAt(0));
    }

    private void validateNotEmpty(String str, String str2) {
        if ("".equals(str)) {
            throw new IllegalArgumentException(str2 + " must not be empty-string.");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getQuoteFields() != null) {
            sb.append("QuoteFields: ").append(getQuoteFields()).append(PunctuationConst.COMMA);
        }
        if (getQuoteEscapeCharacter() != null) {
            sb.append("QuoteEscapeCharacter: ").append(getQuoteEscapeCharacterAsString()).append(PunctuationConst.COMMA);
        }
        if (getRecordDelimiter() != null) {
            sb.append("RecordDelimiter: ").append(getRecordDelimiterAsString()).append(PunctuationConst.COMMA);
        }
        if (getFieldDelimiter() != null) {
            sb.append("FieldDelimiter: ").append(getFieldDelimiterAsString()).append(PunctuationConst.COMMA);
        }
        if (getQuoteCharacter() != null) {
            sb.append("QuoteCharacter: ").append(getQuoteCharacterAsString());
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CSVOutput)) {
            return false;
        }
        CSVOutput cSVOutput = (CSVOutput) obj;
        if ((cSVOutput.getQuoteEscapeCharacterAsString() == null) ^ (getQuoteEscapeCharacterAsString() == null)) {
            return false;
        }
        if (cSVOutput.getQuoteEscapeCharacterAsString() != null && !cSVOutput.getQuoteEscapeCharacterAsString().equals(getQuoteEscapeCharacterAsString())) {
            return false;
        }
        if ((cSVOutput.getQuoteFields() == null) ^ (getQuoteFields() == null)) {
            return false;
        }
        if (cSVOutput.getQuoteFields() != null && !cSVOutput.getQuoteFields().equals(getQuoteFields())) {
            return false;
        }
        if ((cSVOutput.getRecordDelimiterAsString() == null) ^ (getRecordDelimiterAsString() == null)) {
            return false;
        }
        if (cSVOutput.getRecordDelimiterAsString() != null && !cSVOutput.getRecordDelimiterAsString().equals(getRecordDelimiterAsString())) {
            return false;
        }
        if ((cSVOutput.getFieldDelimiterAsString() == null) ^ (getFieldDelimiterAsString() == null)) {
            return false;
        }
        if (cSVOutput.getFieldDelimiterAsString() != null && !cSVOutput.getFieldDelimiterAsString().equals(getFieldDelimiterAsString())) {
            return false;
        }
        if ((cSVOutput.getQuoteCharacterAsString() == null) ^ (getQuoteCharacterAsString() == null)) {
            return false;
        }
        return cSVOutput.getQuoteCharacterAsString() == null || cSVOutput.getQuoteCharacterAsString().equals(getQuoteCharacterAsString());
    }

    public int hashCode() {
        return (((((((((getQuoteFields() == null ? 0 : getQuoteFields().hashCode()) + 31) * 31) + (getQuoteEscapeCharacterAsString() == null ? 0 : getQuoteEscapeCharacterAsString().hashCode())) * 31) + (getRecordDelimiterAsString() == null ? 0 : getRecordDelimiterAsString().hashCode())) * 31) + (getFieldDelimiterAsString() == null ? 0 : getFieldDelimiterAsString().hashCode())) * 31) + (getQuoteCharacterAsString() != null ? getQuoteCharacterAsString().hashCode() : 0);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() even though we're Cloneable!", e);
        }
    }
}
