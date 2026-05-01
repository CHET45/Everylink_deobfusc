package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.core.p008io.NumberInput;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import com.fasterxml.jackson.datatype.jsr310.util.DurationUnitConverter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.Duration;
import java.util.Objects;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: classes3.dex */
public class DurationDeserializer extends JSR310DeserializerBase<Duration> implements ContextualDeserializer {
    public static final DurationDeserializer INSTANCE = new DurationDeserializer();
    private static final long serialVersionUID = 1;
    protected final DurationUnitConverter _durationUnitConverter;
    protected final Boolean _readTimestampsAsNanosOverride;

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return super.deserializeWithType(jsonParser, deserializationContext, typeDeserializer);
    }

    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase, com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public /* bridge */ /* synthetic */ LogicalType logicalType() {
        return super.logicalType();
    }

    public DurationDeserializer() {
        super(Duration.class);
        this._durationUnitConverter = null;
        this._readTimestampsAsNanosOverride = null;
    }

    protected DurationDeserializer(DurationDeserializer durationDeserializer, Boolean bool) {
        super(durationDeserializer, bool);
        this._durationUnitConverter = durationDeserializer._durationUnitConverter;
        this._readTimestampsAsNanosOverride = durationDeserializer._readTimestampsAsNanosOverride;
    }

    protected DurationDeserializer(DurationDeserializer durationDeserializer, DurationUnitConverter durationUnitConverter) {
        super(durationDeserializer, Boolean.valueOf(durationDeserializer._isLenient));
        this._durationUnitConverter = durationUnitConverter;
        this._readTimestampsAsNanosOverride = durationDeserializer._readTimestampsAsNanosOverride;
    }

    protected DurationDeserializer(DurationDeserializer durationDeserializer, Boolean bool, DurationUnitConverter durationUnitConverter, Boolean bool2) {
        super(durationDeserializer, bool);
        this._durationUnitConverter = durationUnitConverter;
        this._readTimestampsAsNanosOverride = bool2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase
    public JSR310DeserializerBase<Duration> withLeniency(Boolean bool) {
        return new DurationDeserializer(this, bool);
    }

    protected DurationDeserializer withConverter(DurationUnitConverter durationUnitConverter) {
        return new DurationDeserializer(this, durationUnitConverter);
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        JsonFormat.Value valueFindFormatOverrides = findFormatOverrides(deserializationContext, beanProperty, handledType());
        boolean zBooleanValue = this._isLenient;
        DurationUnitConverter durationUnitConverter = this._durationUnitConverter;
        Boolean feature = this._readTimestampsAsNanosOverride;
        if (valueFindFormatOverrides != null) {
            if (valueFindFormatOverrides.hasLenient()) {
                zBooleanValue = valueFindFormatOverrides.getLenient().booleanValue();
            }
            if (valueFindFormatOverrides.hasPattern()) {
                String pattern = valueFindFormatOverrides.getPattern();
                DurationUnitConverter durationUnitConverterFrom = DurationUnitConverter.from(pattern);
                if (durationUnitConverterFrom == null) {
                    deserializationContext.reportBadDefinition(getValueType(deserializationContext), String.format("Bad 'pattern' definition (\"%s\") for `Duration`: expected one of [%s]", pattern, DurationUnitConverter.descForAllowed()));
                }
                durationUnitConverter = durationUnitConverterFrom;
            }
            feature = valueFindFormatOverrides.getFeature(JsonFormat.Feature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
        }
        return (zBooleanValue == this._isLenient && Objects.equals(durationUnitConverter, this._durationUnitConverter) && Objects.equals(feature, this._readTimestampsAsNanosOverride)) ? this : new DurationDeserializer(this, Boolean.valueOf(zBooleanValue), durationUnitConverter, feature);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Duration deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int iCurrentTokenId = jsonParser.currentTokenId();
        if (iCurrentTokenId == 1) {
            return _fromString(jsonParser, deserializationContext, deserializationContext.extractScalarFromObject(jsonParser, this, handledType()));
        }
        if (iCurrentTokenId == 3) {
            return _deserializeFromArray(jsonParser, deserializationContext);
        }
        if (iCurrentTokenId == 12) {
            return (Duration) jsonParser.getEmbeddedObject();
        }
        if (iCurrentTokenId == 6) {
            return _fromString(jsonParser, deserializationContext, jsonParser.getText());
        }
        if (iCurrentTokenId != 7) {
            return iCurrentTokenId != 8 ? (Duration) _handleUnexpectedToken(deserializationContext, jsonParser, JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT) : (Duration) DecimalUtils.extractSecondsAndNanos(jsonParser.getDecimalValue(), new BiFunction() { // from class: com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return Duration.ofSeconds(((Long) obj).longValue(), ((Integer) obj2).intValue());
                }
            });
        }
        return _fromTimestamp(deserializationContext, jsonParser.getLongValue());
    }

    protected Duration _fromString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return _fromEmptyString(jsonParser, deserializationContext, strTrim);
        }
        if (deserializationContext.isEnabled(StreamReadCapability.UNTYPED_SCALARS) && _isValidTimestampString(strTrim)) {
            return _fromTimestamp(deserializationContext, NumberInput.parseLong(strTrim));
        }
        try {
            return Duration.parse(strTrim);
        } catch (DateTimeException e) {
            return (Duration) _handleDateTimeException(deserializationContext, e, strTrim);
        }
    }

    protected Duration _fromTimestamp(DeserializationContext deserializationContext, long j) {
        DurationUnitConverter durationUnitConverter = this._durationUnitConverter;
        if (durationUnitConverter != null) {
            return durationUnitConverter.convert(j);
        }
        if (shouldReadTimestampsAsNanoseconds(deserializationContext)) {
            return Duration.ofSeconds(j);
        }
        return Duration.ofMillis(j);
    }

    protected boolean shouldReadTimestampsAsNanoseconds(DeserializationContext deserializationContext) {
        Boolean bool = this._readTimestampsAsNanosOverride;
        return bool != null ? bool.booleanValue() : deserializationContext.isEnabled(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }
}
