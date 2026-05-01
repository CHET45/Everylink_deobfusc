package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonPointer;

/* JADX INFO: loaded from: classes3.dex */
public class JsonPointerBasedFilter extends TokenFilter {
    protected final boolean _includeAllElements;
    protected final JsonPointer _pathToMatch;

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter filterStartArray() {
        return this;
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter filterStartObject() {
        return this;
    }

    public JsonPointerBasedFilter(String str) {
        this(JsonPointer.compile(str), false);
    }

    public JsonPointerBasedFilter(JsonPointer jsonPointer) {
        this(jsonPointer, false);
    }

    public JsonPointerBasedFilter(JsonPointer jsonPointer, boolean z) {
        this._pathToMatch = jsonPointer;
        this._includeAllElements = z;
    }

    protected JsonPointerBasedFilter construct(JsonPointer jsonPointer, boolean z) {
        return new JsonPointerBasedFilter(jsonPointer, z);
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter includeElement(int i) {
        JsonPointer jsonPointerMatchElement;
        if (this._includeAllElements && !this._pathToMatch.mayMatchElement()) {
            jsonPointerMatchElement = this._pathToMatch.tail();
        } else {
            jsonPointerMatchElement = this._pathToMatch.matchElement(i);
        }
        if (jsonPointerMatchElement == null) {
            return null;
        }
        if (jsonPointerMatchElement.matches()) {
            return TokenFilter.INCLUDE_ALL;
        }
        return construct(jsonPointerMatchElement, this._includeAllElements);
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public TokenFilter includeProperty(String str) {
        JsonPointer jsonPointerMatchProperty = this._pathToMatch.matchProperty(str);
        if (jsonPointerMatchProperty == null) {
            return null;
        }
        if (jsonPointerMatchProperty.matches()) {
            return TokenFilter.INCLUDE_ALL;
        }
        return construct(jsonPointerMatchProperty, this._includeAllElements);
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    protected boolean _includeScalar() {
        return this._pathToMatch.matches();
    }

    @Override // com.fasterxml.jackson.core.filter.TokenFilter
    public String toString() {
        return "[JsonPointerFilter at: " + this._pathToMatch + "]";
    }
}
