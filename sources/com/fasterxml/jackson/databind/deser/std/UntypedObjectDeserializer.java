package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadCapability;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.LogicalType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
@JacksonStdImpl
public class UntypedObjectDeserializer extends StdDeserializer<Object> implements ResolvableDeserializer, ContextualDeserializer {
    protected static final Object[] NO_OBJECTS = new Object[0];
    private static final long serialVersionUID = 1;
    protected JsonDeserializer<Object> _listDeserializer;
    protected JavaType _listType;
    protected JsonDeserializer<Object> _mapDeserializer;
    protected JavaType _mapType;
    protected final boolean _nonMerging;
    protected JsonDeserializer<Object> _numberDeserializer;
    protected JsonDeserializer<Object> _stringDeserializer;

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Boolean supportsUpdate(DeserializationConfig deserializationConfig) {
        return null;
    }

    @Deprecated
    public UntypedObjectDeserializer() {
        this((JavaType) null, (JavaType) null);
    }

    public UntypedObjectDeserializer(JavaType javaType, JavaType javaType2) {
        super((Class<?>) Object.class);
        this._listType = javaType;
        this._mapType = javaType2;
        this._nonMerging = false;
    }

    public UntypedObjectDeserializer(UntypedObjectDeserializer untypedObjectDeserializer, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, JsonDeserializer<?> jsonDeserializer3, JsonDeserializer<?> jsonDeserializer4) {
        super((Class<?>) Object.class);
        this._mapDeserializer = jsonDeserializer;
        this._listDeserializer = jsonDeserializer2;
        this._stringDeserializer = jsonDeserializer3;
        this._numberDeserializer = jsonDeserializer4;
        this._listType = untypedObjectDeserializer._listType;
        this._mapType = untypedObjectDeserializer._mapType;
        this._nonMerging = untypedObjectDeserializer._nonMerging;
    }

    protected UntypedObjectDeserializer(UntypedObjectDeserializer untypedObjectDeserializer, boolean z) {
        super((Class<?>) Object.class);
        this._mapDeserializer = untypedObjectDeserializer._mapDeserializer;
        this._listDeserializer = untypedObjectDeserializer._listDeserializer;
        this._stringDeserializer = untypedObjectDeserializer._stringDeserializer;
        this._numberDeserializer = untypedObjectDeserializer._numberDeserializer;
        this._listType = untypedObjectDeserializer._listType;
        this._mapType = untypedObjectDeserializer._mapType;
        this._nonMerging = z;
    }

    @Override // com.fasterxml.jackson.databind.deser.ResolvableDeserializer
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        JavaType javaTypeConstructType = deserializationContext.constructType(Object.class);
        JavaType javaTypeConstructType2 = deserializationContext.constructType(String.class);
        TypeFactory typeFactory = deserializationContext.getTypeFactory();
        JavaType javaType = this._listType;
        if (javaType == null) {
            this._listDeserializer = _clearIfStdImpl(_findCustomDeser(deserializationContext, typeFactory.constructCollectionType(List.class, javaTypeConstructType)));
        } else {
            this._listDeserializer = _findCustomDeser(deserializationContext, javaType);
        }
        JavaType javaType2 = this._mapType;
        if (javaType2 == null) {
            this._mapDeserializer = _clearIfStdImpl(_findCustomDeser(deserializationContext, typeFactory.constructMapType(Map.class, javaTypeConstructType2, javaTypeConstructType)));
        } else {
            this._mapDeserializer = _findCustomDeser(deserializationContext, javaType2);
        }
        this._stringDeserializer = _clearIfStdImpl(_findCustomDeser(deserializationContext, javaTypeConstructType2));
        this._numberDeserializer = _clearIfStdImpl(_findCustomDeser(deserializationContext, typeFactory.constructType(Number.class)));
        JavaType javaTypeUnknownType = TypeFactory.unknownType();
        this._mapDeserializer = deserializationContext.handleSecondaryContextualization(this._mapDeserializer, null, javaTypeUnknownType);
        this._listDeserializer = deserializationContext.handleSecondaryContextualization(this._listDeserializer, null, javaTypeUnknownType);
        this._stringDeserializer = deserializationContext.handleSecondaryContextualization(this._stringDeserializer, null, javaTypeUnknownType);
        this._numberDeserializer = deserializationContext.handleSecondaryContextualization(this._numberDeserializer, null, javaTypeUnknownType);
    }

    protected JsonDeserializer<Object> _findCustomDeser(DeserializationContext deserializationContext, JavaType javaType) throws JsonMappingException {
        return deserializationContext.findNonContextualValueDeserializer(javaType);
    }

    protected JsonDeserializer<Object> _clearIfStdImpl(JsonDeserializer<Object> jsonDeserializer) {
        if (ClassUtil.isJacksonStdImpl(jsonDeserializer)) {
            return null;
        }
        return jsonDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        boolean z = beanProperty == null && Boolean.FALSE.equals(deserializationContext.getConfig().getDefaultMergeable(Object.class));
        if (this._stringDeserializer == null && this._numberDeserializer == null && this._mapDeserializer == null && this._listDeserializer == null && getClass() == UntypedObjectDeserializer.class) {
            return UntypedObjectDeserializerNR.instance(z);
        }
        return z != this._nonMerging ? new UntypedObjectDeserializer(this, z) : this;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public LogicalType logicalType() {
        return LogicalType.Untyped;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        switch (jsonParser.currentTokenId()) {
            case 1:
            case 2:
            case 5:
                JsonDeserializer<Object> jsonDeserializer = this._mapDeserializer;
                if (jsonDeserializer != null) {
                    return jsonDeserializer.deserialize(jsonParser, deserializationContext);
                }
                return mapObject(jsonParser, deserializationContext);
            case 3:
                if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                    return mapArrayToArray(jsonParser, deserializationContext);
                }
                JsonDeserializer<Object> jsonDeserializer2 = this._listDeserializer;
                if (jsonDeserializer2 != null) {
                    return jsonDeserializer2.deserialize(jsonParser, deserializationContext);
                }
                return mapArray(jsonParser, deserializationContext);
            case 4:
            default:
                return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
            case 6:
                JsonDeserializer<Object> jsonDeserializer3 = this._stringDeserializer;
                if (jsonDeserializer3 != null) {
                    return jsonDeserializer3.deserialize(jsonParser, deserializationContext);
                }
                return jsonParser.getText();
            case 7:
                JsonDeserializer<Object> jsonDeserializer4 = this._numberDeserializer;
                if (jsonDeserializer4 != null) {
                    return jsonDeserializer4.deserialize(jsonParser, deserializationContext);
                }
                if (deserializationContext.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
                    return _coerceIntegral(jsonParser, deserializationContext);
                }
                return jsonParser.getNumberValue();
            case 8:
                JsonDeserializer<Object> jsonDeserializer5 = this._numberDeserializer;
                if (jsonDeserializer5 != null) {
                    return jsonDeserializer5.deserialize(jsonParser, deserializationContext);
                }
                return _deserializeFP(jsonParser, deserializationContext);
            case 9:
                return Boolean.TRUE;
            case 10:
                return Boolean.FALSE;
            case 11:
                return null;
            case 12:
                return jsonParser.getEmbeddedObject();
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        int iCurrentTokenId = jsonParser.currentTokenId();
        if (iCurrentTokenId != 1 && iCurrentTokenId != 3) {
            switch (iCurrentTokenId) {
                case 5:
                    break;
                case 6:
                    JsonDeserializer<Object> jsonDeserializer = this._stringDeserializer;
                    if (jsonDeserializer != null) {
                        return jsonDeserializer.deserialize(jsonParser, deserializationContext);
                    }
                    return jsonParser.getText();
                case 7:
                    JsonDeserializer<Object> jsonDeserializer2 = this._numberDeserializer;
                    if (jsonDeserializer2 != null) {
                        return jsonDeserializer2.deserialize(jsonParser, deserializationContext);
                    }
                    if (deserializationContext.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
                        return _coerceIntegral(jsonParser, deserializationContext);
                    }
                    return jsonParser.getNumberValue();
                case 8:
                    JsonDeserializer<Object> jsonDeserializer3 = this._numberDeserializer;
                    if (jsonDeserializer3 != null) {
                        return jsonDeserializer3.deserialize(jsonParser, deserializationContext);
                    }
                    return _deserializeFP(jsonParser, deserializationContext);
                case 9:
                    return Boolean.TRUE;
                case 10:
                    return Boolean.FALSE;
                case 11:
                    return null;
                case 12:
                    return jsonParser.getEmbeddedObject();
                default:
                    return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
            }
        }
        return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        if (this._nonMerging) {
            return deserialize(jsonParser, deserializationContext);
        }
        switch (jsonParser.currentTokenId()) {
            case 1:
            case 2:
            case 5:
                JsonDeserializer<Object> jsonDeserializer = this._mapDeserializer;
                if (jsonDeserializer == null) {
                    if (!(obj instanceof Map)) {
                    }
                }
                break;
            case 3:
                JsonDeserializer<Object> jsonDeserializer2 = this._listDeserializer;
                if (jsonDeserializer2 == null) {
                    if (!(obj instanceof Collection)) {
                        if (!deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                        }
                    }
                }
                break;
            case 6:
                JsonDeserializer<Object> jsonDeserializer3 = this._stringDeserializer;
                if (jsonDeserializer3 == null) {
                }
                break;
            case 7:
                JsonDeserializer<Object> jsonDeserializer4 = this._numberDeserializer;
                if (jsonDeserializer4 == null) {
                    if (!deserializationContext.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
                    }
                }
                break;
            case 8:
                JsonDeserializer<Object> jsonDeserializer5 = this._numberDeserializer;
                if (jsonDeserializer5 == null) {
                }
                break;
        }
        return deserialize(jsonParser, deserializationContext);
    }

    protected Object mapArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        int i = 2;
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return new ArrayList(2);
        }
        Object objDeserialize = deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(objDeserialize);
            return arrayList;
        }
        Object objDeserialize2 = deserialize(jsonParser, deserializationContext);
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            ArrayList arrayList2 = new ArrayList(2);
            arrayList2.add(objDeserialize);
            arrayList2.add(objDeserialize2);
            return arrayList2;
        }
        ObjectBuffer objectBufferLeaseObjectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] objArrResetAndStart = objectBufferLeaseObjectBuffer.resetAndStart();
        objArrResetAndStart[0] = objDeserialize;
        objArrResetAndStart[1] = objDeserialize2;
        int i2 = 2;
        while (true) {
            Object objDeserialize3 = deserialize(jsonParser, deserializationContext);
            i++;
            if (i2 >= objArrResetAndStart.length) {
                objArrResetAndStart = objectBufferLeaseObjectBuffer.appendCompletedChunk(objArrResetAndStart);
                i2 = 0;
            }
            int i3 = i2 + 1;
            objArrResetAndStart[i2] = objDeserialize3;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                ArrayList arrayList3 = new ArrayList(i);
                objectBufferLeaseObjectBuffer.completeAndClearBuffer(objArrResetAndStart, i3, arrayList3);
                deserializationContext.returnObjectBuffer(objectBufferLeaseObjectBuffer);
                return arrayList3;
            }
            i2 = i3;
        }
    }

    protected Object mapArray(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<Object> collection) throws IOException {
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            collection.add(deserialize(jsonParser, deserializationContext));
        }
        return collection;
    }

    protected Object mapObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String strCurrentName;
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        if (jsonTokenCurrentToken == JsonToken.START_OBJECT) {
            strCurrentName = jsonParser.nextFieldName();
        } else if (jsonTokenCurrentToken == JsonToken.FIELD_NAME) {
            strCurrentName = jsonParser.currentName();
        } else {
            if (jsonTokenCurrentToken != JsonToken.END_OBJECT) {
                return deserializationContext.handleUnexpectedToken(handledType(), jsonParser);
            }
            strCurrentName = null;
        }
        String str = strCurrentName;
        if (str == null) {
            return new LinkedHashMap(2);
        }
        jsonParser.nextToken();
        Object objDeserialize = deserialize(jsonParser, deserializationContext);
        String strNextFieldName = jsonParser.nextFieldName();
        if (strNextFieldName == null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap(2);
            linkedHashMap.put(str, objDeserialize);
            return linkedHashMap;
        }
        jsonParser.nextToken();
        Object objDeserialize2 = deserialize(jsonParser, deserializationContext);
        String strNextFieldName2 = jsonParser.nextFieldName();
        if (strNextFieldName2 == null) {
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(4);
            linkedHashMap2.put(str, objDeserialize);
            return linkedHashMap2.put(strNextFieldName, objDeserialize2) != null ? _mapObjectWithDups(jsonParser, deserializationContext, linkedHashMap2, str, objDeserialize, objDeserialize2, strNextFieldName2) : linkedHashMap2;
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        linkedHashMap3.put(str, objDeserialize);
        if (linkedHashMap3.put(strNextFieldName, objDeserialize2) != null) {
            return _mapObjectWithDups(jsonParser, deserializationContext, linkedHashMap3, str, objDeserialize, objDeserialize2, strNextFieldName2);
        }
        do {
            jsonParser.nextToken();
            Object objDeserialize3 = deserialize(jsonParser, deserializationContext);
            Object objPut = linkedHashMap3.put(strNextFieldName2, objDeserialize3);
            if (objPut != null) {
                return _mapObjectWithDups(jsonParser, deserializationContext, linkedHashMap3, strNextFieldName2, objPut, objDeserialize3, jsonParser.nextFieldName());
            }
            strNextFieldName2 = jsonParser.nextFieldName();
        } while (strNextFieldName2 != null);
        return linkedHashMap3;
    }

    protected Object _mapObjectWithDups(JsonParser jsonParser, DeserializationContext deserializationContext, Map<String, Object> map, String str, Object obj, Object obj2, String str2) throws IOException {
        boolean zIsEnabled = deserializationContext.isEnabled(StreamReadCapability.DUPLICATE_PROPERTIES);
        if (zIsEnabled) {
            _squashDups(map, str, obj, obj2);
        }
        while (str2 != null) {
            jsonParser.nextToken();
            Object objDeserialize = deserialize(jsonParser, deserializationContext);
            Object objPut = map.put(str2, objDeserialize);
            if (objPut != null && zIsEnabled) {
                _squashDups(map, str, objPut, objDeserialize);
            }
            str2 = jsonParser.nextFieldName();
        }
        return map;
    }

    private void _squashDups(Map<String, Object> map, String str, Object obj, Object obj2) {
        if (obj instanceof List) {
            ((List) obj).add(obj2);
            map.put(str, obj);
        } else {
            ArrayList arrayList = new ArrayList();
            arrayList.add(obj);
            arrayList.add(obj2);
            map.put(str, arrayList);
        }
    }

    protected Object[] mapArrayToArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
            return NO_OBJECTS;
        }
        ObjectBuffer objectBufferLeaseObjectBuffer = deserializationContext.leaseObjectBuffer();
        Object[] objArrResetAndStart = objectBufferLeaseObjectBuffer.resetAndStart();
        int i = 0;
        while (true) {
            Object objDeserialize = deserialize(jsonParser, deserializationContext);
            if (i >= objArrResetAndStart.length) {
                objArrResetAndStart = objectBufferLeaseObjectBuffer.appendCompletedChunk(objArrResetAndStart);
                i = 0;
            }
            int i2 = i + 1;
            objArrResetAndStart[i] = objDeserialize;
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                Object[] objArrCompleteAndClearBuffer = objectBufferLeaseObjectBuffer.completeAndClearBuffer(objArrResetAndStart, i2);
                deserializationContext.returnObjectBuffer(objectBufferLeaseObjectBuffer);
                return objArrCompleteAndClearBuffer;
            }
            i = i2;
        }
    }

    protected Object mapObject(JsonParser jsonParser, DeserializationContext deserializationContext, Map<Object, Object> map) throws IOException {
        Object objDeserialize;
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        if (jsonTokenCurrentToken == JsonToken.START_OBJECT) {
            jsonTokenCurrentToken = jsonParser.nextToken();
        }
        if (jsonTokenCurrentToken == JsonToken.END_OBJECT) {
            return map;
        }
        String strCurrentName = jsonParser.currentName();
        do {
            jsonParser.nextToken();
            Object obj = map.get(strCurrentName);
            if (obj != null) {
                objDeserialize = deserialize(jsonParser, deserializationContext, obj);
            } else {
                objDeserialize = deserialize(jsonParser, deserializationContext);
            }
            if (objDeserialize != obj) {
                map.put(strCurrentName, objDeserialize);
            }
            strCurrentName = jsonParser.nextFieldName();
        } while (strCurrentName != null);
        return map;
    }

    protected Object _deserializeFP(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonParser.NumberTypeFP numberTypeFP = jsonParser.getNumberTypeFP();
        if (numberTypeFP == JsonParser.NumberTypeFP.BIG_DECIMAL) {
            return jsonParser.getDecimalValue();
        }
        if (!jsonParser.isNaN() && deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
            return jsonParser.getDecimalValue();
        }
        if (numberTypeFP == JsonParser.NumberTypeFP.FLOAT32) {
            return Float.valueOf(jsonParser.getFloatValue());
        }
        return Double.valueOf(jsonParser.getDoubleValue());
    }

    @Deprecated
    @JacksonStdImpl
    public static class Vanilla extends StdDeserializer<Object> {
        private static final long serialVersionUID = 1;
        public static final Vanilla std = new Vanilla();
        protected final boolean _nonMerging;

        public Vanilla() {
            this(false);
        }

        protected Vanilla(boolean z) {
            super((Class<?>) Object.class);
            this._nonMerging = z;
        }

        public static Vanilla instance(boolean z) {
            if (z) {
                return new Vanilla(true);
            }
            return std;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public LogicalType logicalType() {
            return LogicalType.Untyped;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Boolean supportsUpdate(DeserializationConfig deserializationConfig) {
            if (this._nonMerging) {
                return Boolean.FALSE;
            }
            return null;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            switch (jsonParser.currentTokenId()) {
                case 1:
                    if (jsonParser.nextToken() == JsonToken.END_OBJECT) {
                        return new LinkedHashMap(2);
                    }
                    break;
                case 2:
                    return new LinkedHashMap(2);
                case 3:
                    if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                        if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                            return UntypedObjectDeserializer.NO_OBJECTS;
                        }
                        return new ArrayList(2);
                    }
                    if (deserializationContext.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
                        return mapArrayToArray(jsonParser, deserializationContext);
                    }
                    return mapArray(jsonParser, deserializationContext);
                case 4:
                default:
                    return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
                case 5:
                    break;
                case 6:
                    return jsonParser.getText();
                case 7:
                    if (deserializationContext.hasSomeOfFeatures(F_MASK_INT_COERCIONS)) {
                        return _coerceIntegral(jsonParser, deserializationContext);
                    }
                    return jsonParser.getNumberValue();
                case 8:
                    return _deserializeFP(jsonParser, deserializationContext);
                case 9:
                    return Boolean.TRUE;
                case 10:
                    return Boolean.FALSE;
                case 11:
                    return null;
                case 12:
                    return jsonParser.getEmbeddedObject();
            }
            return mapObject(jsonParser, deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
            int iCurrentTokenId = jsonParser.currentTokenId();
            if (iCurrentTokenId != 1 && iCurrentTokenId != 3) {
                switch (iCurrentTokenId) {
                    case 5:
                        break;
                    case 6:
                        return jsonParser.getText();
                    case 7:
                        if (deserializationContext.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                            return jsonParser.getBigIntegerValue();
                        }
                        return jsonParser.getNumberValue();
                    case 8:
                        return _deserializeFP(jsonParser, deserializationContext);
                    case 9:
                        return Boolean.TRUE;
                    case 10:
                        return Boolean.FALSE;
                    case 11:
                        return null;
                    case 12:
                        return jsonParser.getEmbeddedObject();
                    default:
                        return deserializationContext.handleUnexpectedToken(Object.class, jsonParser);
                }
            }
            return typeDeserializer.deserializeTypedFromAny(jsonParser, deserializationContext);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
            Object objDeserialize;
            if (this._nonMerging) {
                return deserialize(jsonParser, deserializationContext);
            }
            int iCurrentTokenId = jsonParser.currentTokenId();
            if (iCurrentTokenId != 1) {
                if (iCurrentTokenId != 2) {
                    if (iCurrentTokenId == 3) {
                        if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                            return obj;
                        }
                        if (obj instanceof Collection) {
                            Collection collection = (Collection) obj;
                            do {
                                collection.add(deserialize(jsonParser, deserializationContext));
                            } while (jsonParser.nextToken() != JsonToken.END_ARRAY);
                        }
                        return deserialize(jsonParser, deserializationContext);
                    }
                    if (iCurrentTokenId != 4) {
                        if (iCurrentTokenId == 5) {
                        }
                        return deserialize(jsonParser, deserializationContext);
                    }
                }
                return obj;
            }
            if (jsonParser.nextToken() == JsonToken.END_OBJECT) {
                return obj;
            }
            if (obj instanceof Map) {
                Map map = (Map) obj;
                String strCurrentName = jsonParser.currentName();
                do {
                    jsonParser.nextToken();
                    Object obj2 = map.get(strCurrentName);
                    if (obj2 != null) {
                        objDeserialize = deserialize(jsonParser, deserializationContext, obj2);
                    } else {
                        objDeserialize = deserialize(jsonParser, deserializationContext);
                    }
                    if (objDeserialize != obj2) {
                        map.put(strCurrentName, objDeserialize);
                    }
                    strCurrentName = jsonParser.nextFieldName();
                } while (strCurrentName != null);
                return obj;
            }
            return deserialize(jsonParser, deserializationContext);
        }

        protected Object _deserializeFP(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonParser.NumberTypeFP numberTypeFP = jsonParser.getNumberTypeFP();
            if (numberTypeFP == JsonParser.NumberTypeFP.BIG_DECIMAL) {
                return jsonParser.getDecimalValue();
            }
            if (!jsonParser.isNaN() && deserializationContext.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                return jsonParser.getDecimalValue();
            }
            if (numberTypeFP == JsonParser.NumberTypeFP.FLOAT32) {
                return Float.valueOf(jsonParser.getFloatValue());
            }
            return Double.valueOf(jsonParser.getDoubleValue());
        }

        protected Object mapArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Object objDeserialize = deserialize(jsonParser, deserializationContext);
            if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                ArrayList arrayList = new ArrayList(2);
                arrayList.add(objDeserialize);
                return arrayList;
            }
            ObjectBuffer objectBufferLeaseObjectBuffer = deserializationContext.leaseObjectBuffer();
            Object[] objArrResetAndStart = objectBufferLeaseObjectBuffer.resetAndStart();
            objArrResetAndStart[0] = objDeserialize;
            int i = 1;
            int i2 = 1;
            while (true) {
                Object objDeserialize2 = deserialize(jsonParser, deserializationContext);
                i++;
                if (i2 >= objArrResetAndStart.length) {
                    objArrResetAndStart = objectBufferLeaseObjectBuffer.appendCompletedChunk(objArrResetAndStart);
                    i2 = 0;
                }
                int i3 = i2 + 1;
                objArrResetAndStart[i2] = objDeserialize2;
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    ArrayList arrayList2 = new ArrayList(i);
                    objectBufferLeaseObjectBuffer.completeAndClearBuffer(objArrResetAndStart, i3, arrayList2);
                    deserializationContext.returnObjectBuffer(objectBufferLeaseObjectBuffer);
                    return arrayList2;
                }
                i2 = i3;
            }
        }

        protected Object[] mapArrayToArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ObjectBuffer objectBufferLeaseObjectBuffer = deserializationContext.leaseObjectBuffer();
            Object[] objArrResetAndStart = objectBufferLeaseObjectBuffer.resetAndStart();
            int i = 0;
            while (true) {
                Object objDeserialize = deserialize(jsonParser, deserializationContext);
                if (i >= objArrResetAndStart.length) {
                    objArrResetAndStart = objectBufferLeaseObjectBuffer.appendCompletedChunk(objArrResetAndStart);
                    i = 0;
                }
                int i2 = i + 1;
                objArrResetAndStart[i] = objDeserialize;
                if (jsonParser.nextToken() == JsonToken.END_ARRAY) {
                    Object[] objArrCompleteAndClearBuffer = objectBufferLeaseObjectBuffer.completeAndClearBuffer(objArrResetAndStart, i2);
                    deserializationContext.returnObjectBuffer(objectBufferLeaseObjectBuffer);
                    return objArrCompleteAndClearBuffer;
                }
                i = i2;
            }
        }

        protected Object mapObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String strCurrentName = jsonParser.currentName();
            jsonParser.nextToken();
            Object objDeserialize = deserialize(jsonParser, deserializationContext);
            String strNextFieldName = jsonParser.nextFieldName();
            if (strNextFieldName == null) {
                LinkedHashMap linkedHashMap = new LinkedHashMap(2);
                linkedHashMap.put(strCurrentName, objDeserialize);
                return linkedHashMap;
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            linkedHashMap2.put(strCurrentName, objDeserialize);
            String strNextFieldName2 = strNextFieldName;
            do {
                jsonParser.nextToken();
                Object objDeserialize2 = deserialize(jsonParser, deserializationContext);
                Object objPut = linkedHashMap2.put(strNextFieldName2, objDeserialize2);
                if (objPut != null) {
                    return _mapObjectWithDups(jsonParser, deserializationContext, linkedHashMap2, strNextFieldName2, objPut, objDeserialize2, jsonParser.nextFieldName());
                }
                strNextFieldName2 = jsonParser.nextFieldName();
            } while (strNextFieldName2 != null);
            return linkedHashMap2;
        }

        protected Object _mapObjectWithDups(JsonParser jsonParser, DeserializationContext deserializationContext, Map<String, Object> map, String str, Object obj, Object obj2, String str2) throws IOException {
            boolean zIsEnabled = deserializationContext.isEnabled(StreamReadCapability.DUPLICATE_PROPERTIES);
            if (zIsEnabled) {
                _squashDups(map, str, obj, obj2);
            }
            while (str2 != null) {
                jsonParser.nextToken();
                Object objDeserialize = deserialize(jsonParser, deserializationContext);
                Object objPut = map.put(str2, objDeserialize);
                if (objPut != null && zIsEnabled) {
                    _squashDups(map, str2, objPut, objDeserialize);
                }
                str2 = jsonParser.nextFieldName();
            }
            return map;
        }

        private void _squashDups(Map<String, Object> map, String str, Object obj, Object obj2) {
            if (obj instanceof List) {
                ((List) obj).add(obj2);
                map.put(str, obj);
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add(obj);
                arrayList.add(obj2);
                map.put(str, arrayList);
            }
        }
    }
}
