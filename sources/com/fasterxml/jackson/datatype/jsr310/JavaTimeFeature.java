package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.core.util.JacksonFeature;

/* JADX INFO: loaded from: classes3.dex */
public enum JavaTimeFeature implements JacksonFeature {
    NORMALIZE_DESERIALIZED_ZONE_ID(true),
    ALWAYS_ALLOW_STRINGIFIED_DATE_TIMESTAMPS(false),
    ONE_BASED_MONTHS(false);

    private final boolean _defaultState;
    private final int _mask = 1 << ordinal();

    JavaTimeFeature(boolean z) {
        this._defaultState = z;
    }

    @Override // com.fasterxml.jackson.core.util.JacksonFeature
    public boolean enabledByDefault() {
        return this._defaultState;
    }

    @Override // com.fasterxml.jackson.core.util.JacksonFeature
    public boolean enabledIn(int i) {
        return (i & this._mask) != 0;
    }

    @Override // com.fasterxml.jackson.core.util.JacksonFeature
    public int getMask() {
        return this._mask;
    }
}
