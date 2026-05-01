package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.p008io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
abstract class JSR310DeserializerBase<T> extends StdScalarDeserializer<T> {
    private static final long serialVersionUID = 1;
    protected final boolean _isLenient;

    protected abstract JSR310DeserializerBase<T> withLeniency(Boolean bool);

    protected JSR310DeserializerBase(Class<T> cls) {
        super((Class<?>) cls);
        this._isLenient = true;
    }

    protected JSR310DeserializerBase(Class<T> cls, Boolean bool) {
        super((Class<?>) cls);
        this._isLenient = !Boolean.FALSE.equals(bool);
    }

    protected JSR310DeserializerBase(JSR310DeserializerBase<T> jSR310DeserializerBase) {
        super(jSR310DeserializerBase);
        this._isLenient = jSR310DeserializerBase._isLenient;
    }

    protected JSR310DeserializerBase(JSR310DeserializerBase<T> jSR310DeserializerBase, Boolean bool) {
        super(jSR310DeserializerBase);
        this._isLenient = !Boolean.FALSE.equals(bool);
    }

    protected boolean isLenient() {
        return this._isLenient;
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.datatype.jsr310.deser.JSR310DeserializerBase$1 */
    static /* synthetic */ class C14821 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction;

        static {
            int[] iArr = new int[CoercionAction.values().length];
            $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction = iArr;
            try {
                iArr[CoercionAction.AsEmpty.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction[CoercionAction.TryConvert.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction[CoercionAction.AsNull.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected T _fromEmptyString(JsonParser jsonParser, DeserializationContext deserializationContext, String str) throws IOException {
        if (C14821.$SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction[_checkFromStringCoercion(deserializationContext, str).ordinal()] == 1) {
            return (T) getEmptyValue(deserializationContext);
        }
        if (this._isLenient) {
            return null;
        }
        return _failForNotLenient(jsonParser, deserializationContext, JsonToken.VALUE_STRING);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public LogicalType logicalType() {
        return LogicalType.DateTime;
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    protected boolean _isValidTimestampString(String str) {
        if (_isIntNumber(str)) {
            return NumberInput.inLongRange(str, str.charAt(0) == '-');
        }
        return false;
    }

    protected <BOGUS> BOGUS _reportWrongToken(DeserializationContext deserializationContext, JsonToken jsonToken, String str) throws IOException {
        deserializationContext.reportWrongTokenException(this, jsonToken, "Expected %s for '%s' of %s value", jsonToken.name(), str, handledType().getName());
        return null;
    }

    protected <BOGUS> BOGUS _reportWrongToken(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken... jsonTokenArr) throws IOException {
        return (BOGUS) deserializationContext.reportInputMismatch(handledType(), "Unexpected token (%s), expected one of %s for %s value", jsonParser.getCurrentToken(), Arrays.asList(jsonTokenArr).toString(), handledType().getName());
    }

    protected <R> R _handleDateTimeException(DeserializationContext deserializationContext, DateTimeException dateTimeException, String str) throws JsonMappingException {
        try {
            return (R) deserializationContext.handleWeirdStringValue(handledType(), str, "Failed to deserialize %s: (%s) %s", handledType().getName(), dateTimeException.getClass().getName(), dateTimeException.getMessage());
        } catch (JsonMappingException e) {
            e.initCause(dateTimeException);
            throw e;
        } catch (IOException e2) {
            if (e2.getCause() == null) {
                e2.initCause(dateTimeException);
            }
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    protected <R> R _handleUnexpectedToken(DeserializationContext deserializationContext, JsonParser jsonParser, String str, Object... objArr) throws JsonMappingException {
        try {
            return (R) deserializationContext.handleUnexpectedToken(handledType(), jsonParser.getCurrentToken(), jsonParser, str, objArr);
        } catch (JsonMappingException e) {
            throw e;
        } catch (IOException e2) {
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    protected <R> R _handleUnexpectedToken(DeserializationContext deserializationContext, JsonParser jsonParser, JsonToken... jsonTokenArr) throws JsonMappingException {
        return (R) _handleUnexpectedToken(deserializationContext, jsonParser, "Unexpected token (%s), expected one of %s for %s value", jsonParser.currentToken(), Arrays.asList(jsonTokenArr), handledType().getName());
    }

    protected T _failForNotLenient(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken jsonToken) throws IOException {
        return (T) deserializationContext.handleUnexpectedToken(handledType(), jsonToken, jsonParser, "Cannot deserialize instance of %s out of %s token: not allowed because 'strict' mode set for property or type (enable 'lenient' handling to allow)", ClassUtil.nameOf(handledType()), jsonParser.currentToken());
    }

    protected DateTimeException _peelDTE(DateTimeException dateTimeException) {
        while (true) {
            Throwable cause = dateTimeException.getCause();
            if (cause == null || !(cause instanceof DateTimeException)) {
                break;
            }
            dateTimeException = (DateTimeException) cause;
        }
        return dateTimeException;
    }
}
