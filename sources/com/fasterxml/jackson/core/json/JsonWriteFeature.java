package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.JsonGenerator;

/* JADX INFO: loaded from: classes3.dex */
public enum JsonWriteFeature implements FormatFeature {
    QUOTE_FIELD_NAMES(true, JsonGenerator.Feature.QUOTE_FIELD_NAMES),
    WRITE_NAN_AS_STRINGS(true, JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS),
    WRITE_NUMBERS_AS_STRINGS(false, JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS),
    ESCAPE_NON_ASCII(false, JsonGenerator.Feature.ESCAPE_NON_ASCII),
    WRITE_HEX_UPPER_CASE(true, JsonGenerator.Feature.WRITE_HEX_UPPER_CASE),
    ESCAPE_FORWARD_SLASHES(false, JsonGenerator.Feature.ESCAPE_FORWARD_SLASHES),
    COMBINE_UNICODE_SURROGATES_IN_UTF8(false, JsonGenerator.Feature.COMBINE_UNICODE_SURROGATES_IN_UTF8);

    private final boolean _defaultState;
    private final JsonGenerator.Feature _mappedFeature;
    private final int _mask = 1 << ordinal();

    public static int collectDefaults() {
        int mask = 0;
        for (JsonWriteFeature jsonWriteFeature : values()) {
            if (jsonWriteFeature.enabledByDefault()) {
                mask |= jsonWriteFeature.getMask();
            }
        }
        return mask;
    }

    JsonWriteFeature(boolean z, JsonGenerator.Feature feature) {
        this._defaultState = z;
        this._mappedFeature = feature;
    }

    @Override // com.fasterxml.jackson.core.FormatFeature, com.fasterxml.jackson.core.util.JacksonFeature
    public boolean enabledByDefault() {
        return this._defaultState;
    }

    @Override // com.fasterxml.jackson.core.FormatFeature, com.fasterxml.jackson.core.util.JacksonFeature
    public int getMask() {
        return this._mask;
    }

    @Override // com.fasterxml.jackson.core.FormatFeature, com.fasterxml.jackson.core.util.JacksonFeature
    public boolean enabledIn(int i) {
        return (i & this._mask) != 0;
    }

    public JsonGenerator.Feature mappedFeature() {
        return this._mappedFeature;
    }
}
