package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.util.RequestPayload;

/* JADX INFO: loaded from: classes3.dex */
public class JsonParseException extends StreamReadException {
    private static final long serialVersionUID = 2;

    public JsonParseException(String str) {
        this(null, str, null, null);
    }

    public JsonParseException(JsonParser jsonParser, String str) {
        this(jsonParser, str, _currentLocation(jsonParser), null);
    }

    public JsonParseException(JsonParser jsonParser, String str, Throwable th) {
        this(jsonParser, str, _currentLocation(jsonParser), th);
    }

    public JsonParseException(JsonParser jsonParser, String str, JsonLocation jsonLocation) {
        this(jsonParser, str, jsonLocation, null);
    }

    public JsonParseException(JsonParser jsonParser, String str, JsonLocation jsonLocation, Throwable th) {
        super(jsonParser, str, jsonLocation, th);
    }

    @Deprecated
    public JsonParseException(String str, JsonLocation jsonLocation) {
        this(null, str, jsonLocation, null);
    }

    @Deprecated
    public JsonParseException(String str, JsonLocation jsonLocation, Throwable th) {
        this(null, str, jsonLocation, th);
    }

    @Override // com.fasterxml.jackson.core.exc.StreamReadException
    public JsonParseException withParser(JsonParser jsonParser) {
        this._processor = jsonParser;
        return this;
    }

    @Override // com.fasterxml.jackson.core.exc.StreamReadException
    public JsonParseException withRequestPayload(RequestPayload requestPayload) {
        this._requestPayload = requestPayload;
        return this;
    }

    @Override // com.fasterxml.jackson.core.exc.StreamReadException, com.fasterxml.jackson.core.JsonProcessingException, com.fasterxml.jackson.core.JacksonException
    public JsonParser getProcessor() {
        return super.getProcessor();
    }

    @Override // com.fasterxml.jackson.core.exc.StreamReadException
    public RequestPayload getRequestPayload() {
        return super.getRequestPayload();
    }

    @Override // com.fasterxml.jackson.core.exc.StreamReadException
    public String getRequestPayloadAsString() {
        return super.getRequestPayloadAsString();
    }

    @Override // com.fasterxml.jackson.core.exc.StreamReadException, com.fasterxml.jackson.core.JsonProcessingException, java.lang.Throwable
    public String getMessage() {
        return super.getMessage();
    }
}
