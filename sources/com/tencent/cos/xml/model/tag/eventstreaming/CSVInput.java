package com.tencent.cos.xml.model.tag.eventstreaming;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class CSVInput implements Serializable, Cloneable {
    private Boolean allowQuotedRecordDelimiter;
    private String comments;
    private String fieldDelimiter;
    private String fileHeaderInfo;
    private String quoteCharacter;
    private String quoteEscapeCharacter;
    private String recordDelimiter;

    public CSVInput(String str, String str2, String str3, String str4, Boolean bool, String str5, String str6) {
        this.recordDelimiter = str;
        this.fieldDelimiter = str2;
        this.quoteCharacter = str3;
        this.quoteEscapeCharacter = str4;
        this.allowQuotedRecordDelimiter = bool;
        this.fileHeaderInfo = str5;
        this.comments = str6;
    }

    public String getFileHeaderInfo() {
        return this.fileHeaderInfo;
    }

    public void setFileHeaderInfo(String str) {
        this.fileHeaderInfo = str;
    }

    public CSVInput withFileHeaderInfo(String str) {
        setFileHeaderInfo(str);
        return this;
    }

    public void setFileHeaderInfo(FileHeaderInfo fileHeaderInfo) {
        setFileHeaderInfo(fileHeaderInfo == null ? null : fileHeaderInfo.toString());
    }

    public CSVInput withFileHeaderInfo(FileHeaderInfo fileHeaderInfo) {
        setFileHeaderInfo(fileHeaderInfo);
        return this;
    }

    public Character getComments() {
        return stringToChar(this.comments);
    }

    public String getCommentsAsString() {
        return this.comments;
    }

    public void setComments(String str) {
        validateNotEmpty(str, "comments");
        this.comments = str;
    }

    public CSVInput withComments(String str) {
        setComments(str);
        return this;
    }

    public void setComments(Character ch) {
        setComments(charToString(ch));
    }

    public CSVInput withComments(Character ch) {
        setComments(ch);
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

    public CSVInput withQuoteEscapeCharacter(String str) {
        setQuoteEscapeCharacter(str);
        return this;
    }

    public void setQuoteEscapeCharacter(Character ch) {
        setQuoteEscapeCharacter(charToString(ch));
    }

    public CSVInput withQuoteEscapeCharacter(Character ch) {
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

    public CSVInput withRecordDelimiter(String str) {
        setRecordDelimiter(str);
        return this;
    }

    public void setRecordDelimiter(Character ch) {
        setRecordDelimiter(charToString(ch));
    }

    public CSVInput withRecordDelimiter(Character ch) {
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

    public CSVInput withFieldDelimiter(String str) {
        setFieldDelimiter(str);
        return this;
    }

    public void setFieldDelimiter(Character ch) {
        setFieldDelimiter(charToString(ch));
    }

    public CSVInput withFieldDelimiter(Character ch) {
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

    public CSVInput withQuoteCharacter(String str) {
        setQuoteCharacter(str);
        return this;
    }

    public void setQuoteCharacter(Character ch) {
        setQuoteCharacter(charToString(ch));
    }

    public CSVInput withQuoteCharacter(Character ch) {
        setQuoteCharacter(ch);
        return this;
    }

    public Boolean getAllowQuotedRecordDelimiter() {
        return this.allowQuotedRecordDelimiter;
    }

    public void setAllowQuotedRecordDelimiter(Boolean bool) {
        this.allowQuotedRecordDelimiter = bool;
    }

    public CSVInput withAllowQuotedRecordDelimiter(Boolean bool) {
        setAllowQuotedRecordDelimiter(bool);
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CSVInput)) {
            return false;
        }
        CSVInput cSVInput = (CSVInput) obj;
        if ((cSVInput.getFileHeaderInfo() == null) ^ (getFileHeaderInfo() == null)) {
            return false;
        }
        if (cSVInput.getFileHeaderInfo() != null && !cSVInput.getFileHeaderInfo().equals(getFileHeaderInfo())) {
            return false;
        }
        if ((cSVInput.getQuoteEscapeCharacterAsString() == null) ^ (getQuoteEscapeCharacterAsString() == null)) {
            return false;
        }
        if (cSVInput.getQuoteEscapeCharacterAsString() != null && !cSVInput.getQuoteEscapeCharacterAsString().equals(getQuoteEscapeCharacterAsString())) {
            return false;
        }
        if ((cSVInput.getCommentsAsString() == null) ^ (getCommentsAsString() == null)) {
            return false;
        }
        if (cSVInput.getCommentsAsString() != null && !cSVInput.getCommentsAsString().equals(getCommentsAsString())) {
            return false;
        }
        if ((cSVInput.getRecordDelimiterAsString() == null) ^ (getRecordDelimiterAsString() == null)) {
            return false;
        }
        if (cSVInput.getRecordDelimiterAsString() != null && !cSVInput.getRecordDelimiterAsString().equals(getRecordDelimiterAsString())) {
            return false;
        }
        if ((cSVInput.getFieldDelimiterAsString() == null) ^ (getFieldDelimiterAsString() == null)) {
            return false;
        }
        if (cSVInput.getFieldDelimiterAsString() != null && !cSVInput.getFieldDelimiterAsString().equals(getFieldDelimiterAsString())) {
            return false;
        }
        if ((cSVInput.getQuoteCharacterAsString() == null) ^ (getQuoteCharacterAsString() == null)) {
            return false;
        }
        if (cSVInput.getQuoteCharacterAsString() == null || cSVInput.getQuoteCharacterAsString().equals(getQuoteCharacterAsString())) {
            return cSVInput.getAllowQuotedRecordDelimiter() == null || cSVInput.getAllowQuotedRecordDelimiter().equals(getAllowQuotedRecordDelimiter());
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((getFileHeaderInfo() == null ? 0 : getFileHeaderInfo().hashCode()) + 31) * 31) + (getCommentsAsString() == null ? 0 : getCommentsAsString().hashCode())) * 31) + (getQuoteEscapeCharacterAsString() == null ? 0 : getQuoteEscapeCharacterAsString().hashCode())) * 31) + (getRecordDelimiterAsString() == null ? 0 : getRecordDelimiterAsString().hashCode())) * 31) + (getFieldDelimiterAsString() == null ? 0 : getFieldDelimiterAsString().hashCode())) * 31) + (getQuoteCharacterAsString() != null ? getQuoteCharacterAsString().hashCode() : 0)) * 31) + (getAllowQuotedRecordDelimiter() != null ? getAllowQuotedRecordDelimiter().hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getFileHeaderInfo() != null) {
            sb.append("FileHeaderInfo: ").append(getFileHeaderInfo()).append(PunctuationConst.COMMA);
        }
        if (getCommentsAsString() != null) {
            sb.append("Comments: ").append(getCommentsAsString()).append(PunctuationConst.COMMA);
        }
        if (getQuoteEscapeCharacterAsString() != null) {
            sb.append("QuoteEscapeCharacter: ").append(getQuoteEscapeCharacterAsString()).append(PunctuationConst.COMMA);
        }
        if (getRecordDelimiterAsString() != null) {
            sb.append("RecordDelimiter: ").append(getRecordDelimiterAsString()).append(PunctuationConst.COMMA);
        }
        if (getFieldDelimiterAsString() != null) {
            sb.append("FieldDelimiter: ").append(getFieldDelimiterAsString()).append(PunctuationConst.COMMA);
        }
        if (getQuoteCharacterAsString() != null) {
            sb.append("QuoteCharacter: ").append(getQuoteCharacterAsString());
        }
        if (getAllowQuotedRecordDelimiter() != null) {
            sb.append("AllowQuotedRecordDelimiter: ").append(getAllowQuotedRecordDelimiter());
        }
        sb.append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
        return sb.toString();
    }

    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public CSVInput m2696clone() {
        try {
            return (CSVInput) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() even though we're Cloneable!", e);
        }
    }
}
