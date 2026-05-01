package com.fasterxml.jackson.core;

import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class ErrorReportConfiguration implements Serializable {
    private static ErrorReportConfiguration DEFAULT = new ErrorReportConfiguration(256, 500);
    public static final int DEFAULT_MAX_ERROR_TOKEN_LENGTH = 256;
    public static final int DEFAULT_MAX_RAW_CONTENT_LENGTH = 500;
    private static final long serialVersionUID = 1;
    protected final int _maxErrorTokenLength;
    protected final int _maxRawContentLength;

    public static void overrideDefaultErrorReportConfiguration(ErrorReportConfiguration errorReportConfiguration) {
        if (errorReportConfiguration == null) {
            DEFAULT = new ErrorReportConfiguration(256, 500);
        } else {
            DEFAULT = errorReportConfiguration;
        }
    }

    public static final class Builder {
        private int maxErrorTokenLength;
        private int maxRawContentLength;

        public Builder maxErrorTokenLength(int i) {
            ErrorReportConfiguration.validateMaxErrorTokenLength(i);
            this.maxErrorTokenLength = i;
            return this;
        }

        public Builder maxRawContentLength(int i) {
            ErrorReportConfiguration.validateMaxRawContentLength(i);
            this.maxRawContentLength = i;
            return this;
        }

        Builder() {
            this(256, 500);
        }

        Builder(int i, int i2) {
            this.maxErrorTokenLength = i;
            this.maxRawContentLength = i2;
        }

        Builder(ErrorReportConfiguration errorReportConfiguration) {
            this.maxErrorTokenLength = errorReportConfiguration._maxErrorTokenLength;
            this.maxRawContentLength = errorReportConfiguration._maxRawContentLength;
        }

        public ErrorReportConfiguration build() {
            return new ErrorReportConfiguration(this.maxErrorTokenLength, this.maxRawContentLength);
        }
    }

    protected ErrorReportConfiguration(int i, int i2) {
        this._maxErrorTokenLength = i;
        this._maxRawContentLength = i2;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ErrorReportConfiguration defaults() {
        return DEFAULT;
    }

    public Builder rebuild() {
        return new Builder(this);
    }

    public int getMaxErrorTokenLength() {
        return this._maxErrorTokenLength;
    }

    public int getMaxRawContentLength() {
        return this._maxRawContentLength;
    }

    static void validateMaxErrorTokenLength(int i) throws IllegalArgumentException {
        if (i < 0) {
            throw new IllegalArgumentException(String.format("Value of maxErrorTokenLength (%d) cannot be negative", Integer.valueOf(i)));
        }
    }

    static void validateMaxRawContentLength(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(String.format("Value of maxRawContentLength (%d) cannot be negative", Integer.valueOf(i)));
        }
    }
}
