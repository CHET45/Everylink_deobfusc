package com.fasterxml.jackson.core.exc;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.RequestPayload;

/* JADX INFO: loaded from: classes3.dex */
public abstract class StreamReadException extends JsonProcessingException {
    static final long serialVersionUID = 2;
    protected transient JsonParser _processor;
    protected RequestPayload _requestPayload;

    public abstract StreamReadException withParser(JsonParser jsonParser);

    public abstract StreamReadException withRequestPayload(RequestPayload requestPayload);

    protected StreamReadException(String str) {
        this(null, str, null, null);
    }

    protected StreamReadException(JsonParser jsonParser, String str) {
        this(jsonParser, str, _currentLocation(jsonParser), null);
    }

    protected StreamReadException(JsonParser jsonParser, String str, Throwable th) {
        this(jsonParser, str, _currentLocation(jsonParser), th);
    }

    protected StreamReadException(JsonParser jsonParser, String str, JsonLocation jsonLocation) {
        this(jsonParser, str, jsonLocation, null);
    }

    protected StreamReadException(String str, JsonLocation jsonLocation, Throwable th) {
        this(null, str, jsonLocation, th);
    }

    protected StreamReadException(JsonParser jsonParser, String str, JsonLocation jsonLocation, Throwable th) {
        super(str, jsonLocation, th);
        this._processor = jsonParser;
    }

    @Override // com.fasterxml.jackson.core.JsonProcessingException, com.fasterxml.jackson.core.JacksonException
    public JsonParser getProcessor() {
        return this._processor;
    }

    public RequestPayload getRequestPayload() {
        return this._requestPayload;
    }

    public String getRequestPayloadAsString() {
        RequestPayload requestPayload = this._requestPayload;
        if (requestPayload != null) {
            return requestPayload.toString();
        }
        return null;
    }

    @Override // com.fasterxml.jackson.core.JsonProcessingException, java.lang.Throwable
    public String getMessage() {
        String message = super.getMessage();
        return this._requestPayload != null ? message + "\nRequest payload : " + this._requestPayload.toString() : message;
    }

    protected static JsonLocation _currentLocation(JsonParser jsonParser) {
        if (jsonParser == null) {
            return null;
        }
        return jsonParser.currentLocation();
    }
}
