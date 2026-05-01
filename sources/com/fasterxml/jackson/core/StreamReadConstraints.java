package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.exc.StreamConstraintsException;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class StreamReadConstraints implements Serializable {
    private static StreamReadConstraints DEFAULT = new StreamReadConstraints(1000, -1, 1000, 20000000, 50000);
    public static final int DEFAULT_MAX_DEPTH = 1000;
    public static final long DEFAULT_MAX_DOC_LEN = -1;
    public static final int DEFAULT_MAX_NAME_LEN = 50000;
    public static final int DEFAULT_MAX_NUM_LEN = 1000;
    public static final int DEFAULT_MAX_STRING_LEN = 20000000;
    public static final long DEFAULT_MAX_TOKEN_COUNT = -1;
    private static final int MAX_BIGINT_SCALE_MAGNITUDE = 100000;
    private static final long serialVersionUID = 1;
    protected final long _maxDocLen;
    protected final int _maxNameLen;
    protected final int _maxNestingDepth;
    protected final int _maxNumLen;
    protected final int _maxStringLen;
    protected final long _maxTokenCount;

    public static void overrideDefaultStreamReadConstraints(StreamReadConstraints streamReadConstraints) {
        if (streamReadConstraints == null) {
            DEFAULT = new StreamReadConstraints(1000, -1L, 1000, 20000000);
        } else {
            DEFAULT = streamReadConstraints;
        }
    }

    public static final class Builder {
        private long maxDocLen;
        private int maxNameLen;
        private int maxNestingDepth;
        private int maxNumLen;
        private int maxStringLen;
        private long maxTokenCount;

        public Builder maxNestingDepth(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cannot set maxNestingDepth to a negative value");
            }
            this.maxNestingDepth = i;
            return this;
        }

        public Builder maxDocumentLength(long j) {
            if (j <= 0) {
                j = -1;
            }
            this.maxDocLen = j;
            return this;
        }

        public Builder maxTokenCount(long j) {
            if (j <= 0) {
                j = -1;
            }
            this.maxTokenCount = j;
            return this;
        }

        public Builder maxNumberLength(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cannot set maxNumberLength to a negative value");
            }
            this.maxNumLen = i;
            return this;
        }

        public Builder maxStringLength(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cannot set maxStringLen to a negative value");
            }
            this.maxStringLen = i;
            return this;
        }

        public Builder maxNameLength(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cannot set maxNameLen to a negative value");
            }
            this.maxNameLen = i;
            return this;
        }

        Builder() {
            this(1000, -1L, -1L, 1000, 20000000, 50000);
        }

        Builder(int i, long j, long j2, int i2, int i3, int i4) {
            this.maxNestingDepth = i;
            this.maxDocLen = j;
            this.maxTokenCount = j2;
            this.maxNumLen = i2;
            this.maxStringLen = i3;
            this.maxNameLen = i4;
        }

        Builder(StreamReadConstraints streamReadConstraints) {
            this.maxNestingDepth = streamReadConstraints._maxNestingDepth;
            this.maxDocLen = streamReadConstraints._maxDocLen;
            this.maxTokenCount = streamReadConstraints._maxTokenCount;
            this.maxNumLen = streamReadConstraints._maxNumLen;
            this.maxStringLen = streamReadConstraints._maxStringLen;
            this.maxNameLen = streamReadConstraints._maxNameLen;
        }

        public StreamReadConstraints build() {
            return new StreamReadConstraints(this.maxNestingDepth, this.maxDocLen, this.maxNumLen, this.maxStringLen, this.maxNameLen, this.maxTokenCount);
        }
    }

    @Deprecated
    protected StreamReadConstraints(int i, long j, int i2, int i3) {
        this(i, j, i2, i3, 50000, -1L);
    }

    @Deprecated
    protected StreamReadConstraints(int i, long j, int i2, int i3, int i4) {
        this(i, j, i2, i3, i4, -1L);
    }

    protected StreamReadConstraints(int i, long j, int i2, int i3, int i4, long j2) {
        this._maxNestingDepth = i;
        this._maxDocLen = j;
        this._maxNumLen = i2;
        this._maxStringLen = i3;
        this._maxNameLen = i4;
        this._maxTokenCount = j2;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static StreamReadConstraints defaults() {
        return DEFAULT;
    }

    public Builder rebuild() {
        return new Builder(this);
    }

    public int getMaxNestingDepth() {
        return this._maxNestingDepth;
    }

    public long getMaxDocumentLength() {
        return this._maxDocLen;
    }

    public boolean hasMaxDocumentLength() {
        return this._maxDocLen > 0;
    }

    public long getMaxTokenCount() {
        return this._maxTokenCount;
    }

    public boolean hasMaxTokenCount() {
        return this._maxTokenCount > 0;
    }

    public int getMaxNumberLength() {
        return this._maxNumLen;
    }

    public int getMaxStringLength() {
        return this._maxStringLen;
    }

    public int getMaxNameLength() {
        return this._maxNameLen;
    }

    public void validateNestingDepth(int i) throws StreamConstraintsException {
        if (i > this._maxNestingDepth) {
            throw _constructException("Document nesting depth (%d) exceeds the maximum allowed (%d, from %s)", Integer.valueOf(i), Integer.valueOf(this._maxNestingDepth), _constrainRef("getMaxNestingDepth"));
        }
    }

    public void validateDocumentLength(long j) throws StreamConstraintsException {
        long j2 = this._maxDocLen;
        if (j > j2 && j2 > 0) {
            throw _constructException("Document length (%d) exceeds the maximum allowed (%d, from %s)", Long.valueOf(j), Long.valueOf(this._maxDocLen), _constrainRef("getMaxDocumentLength"));
        }
    }

    public void validateTokenCount(long j) throws StreamConstraintsException {
        if (j > this._maxTokenCount) {
            throw _constructException("Token count (%d) exceeds the maximum allowed (%d, from %s)", Long.valueOf(j), Long.valueOf(this._maxTokenCount), _constrainRef("getMaxTokenCount"));
        }
    }

    public void validateFPLength(int i) throws StreamConstraintsException {
        if (i > this._maxNumLen) {
            throw _constructException("Number value length (%d) exceeds the maximum allowed (%d, from %s)", Integer.valueOf(i), Integer.valueOf(this._maxNumLen), _constrainRef("getMaxNumberLength"));
        }
    }

    public void validateIntegerLength(int i) throws StreamConstraintsException {
        if (i > this._maxNumLen) {
            throw _constructException("Number value length (%d) exceeds the maximum allowed (%d, from %s)", Integer.valueOf(i), Integer.valueOf(this._maxNumLen), _constrainRef("getMaxNumberLength"));
        }
    }

    public void validateStringLength(int i) throws StreamConstraintsException {
        if (i > this._maxStringLen) {
            throw _constructException("String value length (%d) exceeds the maximum allowed (%d, from %s)", Integer.valueOf(i), Integer.valueOf(this._maxStringLen), _constrainRef("getMaxStringLength"));
        }
    }

    public void validateNameLength(int i) throws StreamConstraintsException {
        if (i > this._maxNameLen) {
            throw _constructException("Name length (%d) exceeds the maximum allowed (%d, from %s)", Integer.valueOf(i), Integer.valueOf(this._maxNameLen), _constrainRef("getMaxNameLength"));
        }
    }

    public void validateBigIntegerScale(int i) throws StreamConstraintsException {
        if (Math.abs(i) > MAX_BIGINT_SCALE_MAGNITUDE) {
            throw _constructException("BigDecimal scale (%d) magnitude exceeds the maximum allowed (%d)", Integer.valueOf(i), Integer.valueOf(MAX_BIGINT_SCALE_MAGNITUDE));
        }
    }

    protected StreamConstraintsException _constructException(String str, Object... objArr) throws StreamConstraintsException {
        throw new StreamConstraintsException(String.format(str, objArr));
    }

    protected String _constrainRef(String str) {
        return "`StreamReadConstraints." + str + "()`";
    }
}
