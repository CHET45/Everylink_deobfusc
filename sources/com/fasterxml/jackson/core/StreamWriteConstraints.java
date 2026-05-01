package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.exc.StreamConstraintsException;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class StreamWriteConstraints implements Serializable {
    private static StreamWriteConstraints DEFAULT = new StreamWriteConstraints(1000);
    public static final int DEFAULT_MAX_DEPTH = 1000;
    private static final long serialVersionUID = 1;
    protected final int _maxNestingDepth;

    public static void overrideDefaultStreamWriteConstraints(StreamWriteConstraints streamWriteConstraints) {
        if (streamWriteConstraints == null) {
            DEFAULT = new StreamWriteConstraints(1000);
        } else {
            DEFAULT = streamWriteConstraints;
        }
    }

    public static final class Builder {
        private int maxNestingDepth;

        public Builder maxNestingDepth(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Cannot set maxNestingDepth to a negative value");
            }
            this.maxNestingDepth = i;
            return this;
        }

        Builder() {
            this(1000);
        }

        Builder(int i) {
            this.maxNestingDepth = i;
        }

        Builder(StreamWriteConstraints streamWriteConstraints) {
            this.maxNestingDepth = streamWriteConstraints._maxNestingDepth;
        }

        public StreamWriteConstraints build() {
            return new StreamWriteConstraints(this.maxNestingDepth);
        }
    }

    protected StreamWriteConstraints(int i) {
        this._maxNestingDepth = i;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static StreamWriteConstraints defaults() {
        return DEFAULT;
    }

    public Builder rebuild() {
        return new Builder(this);
    }

    public int getMaxNestingDepth() {
        return this._maxNestingDepth;
    }

    public void validateNestingDepth(int i) throws StreamConstraintsException {
        if (i > this._maxNestingDepth) {
            throw _constructException("Document nesting depth (%d) exceeds the maximum allowed (%d, from %s)", Integer.valueOf(i), Integer.valueOf(this._maxNestingDepth), _constrainRef("getMaxNestingDepth"));
        }
    }

    protected StreamConstraintsException _constructException(String str, Object... objArr) throws StreamConstraintsException {
        throw new StreamConstraintsException(String.format(str, objArr));
    }

    protected String _constrainRef(String str) {
        return "`StreamWriteConstraints." + str + "()`";
    }
}
