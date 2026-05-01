package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayDeserializer;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.MethodProperty;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.IgnorePropertiesUtil;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public class BeanDeserializer extends BeanDeserializerBase implements Serializable {
    private static final long serialVersionUID = 1;
    private volatile transient NameTransformer _currentlyTransforming;
    protected transient Exception _nullFromCreator;

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public /* bridge */ /* synthetic */ BeanDeserializerBase withByNameInclusion(Set set, Set set2) {
        return withByNameInclusion((Set<String>) set, (Set<String>) set2);
    }

    @Deprecated
    public BeanDeserializer(BeanDeserializerBuilder beanDeserializerBuilder, BeanDescription beanDescription, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean z, boolean z2) {
        super(beanDeserializerBuilder, beanDescription, beanPropertyMap, map, hashSet, z, null, z2);
    }

    public BeanDeserializer(BeanDeserializerBuilder beanDeserializerBuilder, BeanDescription beanDescription, BeanPropertyMap beanPropertyMap, Map<String, SettableBeanProperty> map, HashSet<String> hashSet, boolean z, Set<String> set, boolean z2) {
        super(beanDeserializerBuilder, beanDescription, beanPropertyMap, map, hashSet, z, set, z2);
    }

    protected BeanDeserializer(BeanDeserializerBase beanDeserializerBase) {
        super(beanDeserializerBase, beanDeserializerBase._ignoreAllUnknown);
    }

    protected BeanDeserializer(BeanDeserializerBase beanDeserializerBase, boolean z) {
        super(beanDeserializerBase, z);
    }

    protected BeanDeserializer(BeanDeserializerBase beanDeserializerBase, NameTransformer nameTransformer) {
        super(beanDeserializerBase, nameTransformer);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, ObjectIdReader objectIdReader) {
        super(beanDeserializerBase, objectIdReader);
    }

    @Deprecated
    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, Set<String> set) {
        super(beanDeserializerBase, set);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, Set<String> set, Set<String> set2) {
        super(beanDeserializerBase, set, set2);
    }

    public BeanDeserializer(BeanDeserializerBase beanDeserializerBase, BeanPropertyMap beanPropertyMap) {
        super(beanDeserializerBase, beanPropertyMap);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        if (getClass() != BeanDeserializer.class || this._currentlyTransforming == nameTransformer) {
            return this;
        }
        this._currentlyTransforming = nameTransformer;
        try {
            return new BeanDeserializer(this, nameTransformer);
        } finally {
            this._currentlyTransforming = null;
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializer withObjectIdReader(ObjectIdReader objectIdReader) {
        return new BeanDeserializer(this, objectIdReader);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializer withByNameInclusion(Set<String> set, Set<String> set2) {
        return new BeanDeserializer(this, set, set2);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializerBase withIgnoreAllUnknown(boolean z) {
        return new BeanDeserializer(this, z);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializerBase withBeanProperties(BeanPropertyMap beanPropertyMap) {
        return new BeanDeserializer(this, beanPropertyMap);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    protected BeanDeserializerBase asArrayDeserializer() {
        return new BeanAsArrayDeserializer(this, this._beanProperties.getPropertiesInInsertionOrder());
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.isExpectedStartObjectToken()) {
            if (this._vanillaProcessing) {
                return vanillaDeserialize(jsonParser, deserializationContext, jsonParser.nextToken());
            }
            jsonParser.nextToken();
            if (this._objectIdReader != null) {
                return deserializeWithObjectId(jsonParser, deserializationContext);
            }
            return deserializeFromObject(jsonParser, deserializationContext);
        }
        return _deserializeOther(jsonParser, deserializationContext, jsonParser.currentToken());
    }

    protected final Object _deserializeOther(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken jsonToken) throws IOException {
        if (jsonToken != null) {
            switch (C14311.$SwitchMap$com$fasterxml$jackson$core$JsonToken[jsonToken.ordinal()]) {
                case 1:
                    return deserializeFromString(jsonParser, deserializationContext);
                case 2:
                    return deserializeFromNumber(jsonParser, deserializationContext);
                case 3:
                    return deserializeFromDouble(jsonParser, deserializationContext);
                case 4:
                    return deserializeFromEmbedded(jsonParser, deserializationContext);
                case 5:
                case 6:
                    return deserializeFromBoolean(jsonParser, deserializationContext);
                case 7:
                    return deserializeFromNull(jsonParser, deserializationContext);
                case 8:
                    return _deserializeFromArray(jsonParser, deserializationContext);
                case 9:
                case 10:
                    if (this._vanillaProcessing) {
                        return vanillaDeserialize(jsonParser, deserializationContext, jsonToken);
                    }
                    if (this._objectIdReader != null) {
                        return deserializeWithObjectId(jsonParser, deserializationContext);
                    }
                    return deserializeFromObject(jsonParser, deserializationContext);
            }
        }
        return deserializationContext.handleUnexpectedToken(getValueType(deserializationContext), jsonParser);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        String strCurrentName;
        Class<?> activeView;
        jsonParser.assignCurrentValue(obj);
        if (this._injectables != null) {
            injectValues(deserializationContext, obj);
        }
        if (this._unwrappedPropertyHandler != null) {
            return deserializeWithUnwrapped(jsonParser, deserializationContext, obj);
        }
        if (this._externalTypeIdHandler != null) {
            return deserializeWithExternalTypeId(jsonParser, deserializationContext, obj);
        }
        if (jsonParser.isExpectedStartObjectToken()) {
            strCurrentName = jsonParser.nextFieldName();
            if (strCurrentName == null) {
                return obj;
            }
        } else {
            if (jsonParser.hasTokenId(5)) {
                strCurrentName = jsonParser.currentName();
            }
            return obj;
        }
        if (this._needViewProcesing && (activeView = deserializationContext.getActiveView()) != null) {
            return deserializeWithView(jsonParser, deserializationContext, obj, activeView);
        }
        do {
            jsonParser.nextToken();
            SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
            if (settableBeanPropertyFind != null) {
                try {
                    settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, obj);
                } catch (Exception e) {
                    wrapAndThrow(e, obj, strCurrentName, deserializationContext);
                }
            } else {
                handleUnknownVanilla(jsonParser, deserializationContext, obj, strCurrentName);
            }
            strCurrentName = jsonParser.nextFieldName();
        } while (strCurrentName != null);
        return obj;
    }

    private final Object vanillaDeserialize(JsonParser jsonParser, DeserializationContext deserializationContext, JsonToken jsonToken) throws IOException {
        Object objCreateUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        if (jsonParser.hasTokenId(5)) {
            jsonParser.assignCurrentValue(objCreateUsingDefault);
            String strCurrentName = jsonParser.currentName();
            do {
                jsonParser.nextToken();
                SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
                if (settableBeanPropertyFind != null) {
                    try {
                        settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, objCreateUsingDefault);
                    } catch (Exception e) {
                        wrapAndThrow(e, objCreateUsingDefault, strCurrentName, deserializationContext);
                    }
                } else {
                    handleUnknownVanilla(jsonParser, deserializationContext, objCreateUsingDefault, strCurrentName);
                }
                strCurrentName = jsonParser.nextFieldName();
            } while (strCurrentName != null);
        }
        return objCreateUsingDefault;
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Class<?> activeView;
        if (this._objectIdReader != null && this._objectIdReader.maySerializeAsObject() && jsonParser.hasTokenId(5) && this._objectIdReader.isValidReferencePropertyName(jsonParser.currentName(), jsonParser)) {
            return deserializeFromObjectId(jsonParser, deserializationContext);
        }
        if (this._nonStandardCreation) {
            if (this._unwrappedPropertyHandler != null) {
                return deserializeWithUnwrapped(jsonParser, deserializationContext);
            }
            if (this._externalTypeIdHandler != null) {
                return deserializeWithExternalTypeId(jsonParser, deserializationContext);
            }
            return deserializeFromObjectUsingNonDefault(jsonParser, deserializationContext);
        }
        Object objCreateUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        jsonParser.assignCurrentValue(objCreateUsingDefault);
        if (jsonParser.canReadObjectId()) {
            Object objectId = jsonParser.getObjectId();
            if (objectId != null) {
                _handleTypedObjectId(jsonParser, deserializationContext, objCreateUsingDefault, objectId);
            }
        } else if (this._objectIdReader != null && jsonParser.hasTokenId(2) && deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS)) {
            deserializationContext.reportUnresolvedObjectId(this._objectIdReader, objCreateUsingDefault);
        }
        if (this._injectables != null) {
            injectValues(deserializationContext, objCreateUsingDefault);
        }
        if (this._needViewProcesing && (activeView = deserializationContext.getActiveView()) != null) {
            return deserializeWithView(jsonParser, deserializationContext, objCreateUsingDefault, activeView);
        }
        if (jsonParser.hasTokenId(5)) {
            String strCurrentName = jsonParser.currentName();
            do {
                jsonParser.nextToken();
                SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
                if (settableBeanPropertyFind != null) {
                    try {
                        settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, objCreateUsingDefault);
                    } catch (Exception e) {
                        wrapAndThrow(e, objCreateUsingDefault, strCurrentName, deserializationContext);
                    }
                } else {
                    handleUnknownVanilla(jsonParser, deserializationContext, objCreateUsingDefault, strCurrentName);
                }
                strCurrentName = jsonParser.nextFieldName();
            } while (strCurrentName != null);
        }
        return objCreateUsingDefault;
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    protected Object _deserializeUsingPropertyBased(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        PropertyValueBuffer propertyValueBufferStartBuilding;
        Object objWrapInstantiationProblem;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        if (this._anySetter != null) {
            propertyValueBufferStartBuilding = propertyBasedCreator.startBuildingWithAnySetter(jsonParser, deserializationContext, this._objectIdReader, this._anySetter);
        } else {
            propertyValueBufferStartBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        }
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        ArrayList arrayList = null;
        TokenBuffer tokenBufferBufferForInputBuffering = null;
        while (jsonTokenCurrentToken == JsonToken.FIELD_NAME) {
            String strCurrentName = jsonParser.currentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanPropertyFindCreatorProperty = propertyBasedCreator.findCreatorProperty(strCurrentName);
            if (!propertyValueBufferStartBuilding.readIdProperty(strCurrentName) || settableBeanPropertyFindCreatorProperty != null) {
                if (settableBeanPropertyFindCreatorProperty != null) {
                    if (activeView != null && !settableBeanPropertyFindCreatorProperty.visibleInView(activeView)) {
                        jsonParser.skipChildren();
                    } else if (propertyValueBufferStartBuilding.assignParameter(settableBeanPropertyFindCreatorProperty, _deserializeWithErrorWrapping(jsonParser, deserializationContext, settableBeanPropertyFindCreatorProperty))) {
                        jsonParser.nextToken();
                        try {
                            objWrapInstantiationProblem = propertyBasedCreator.build(deserializationContext, propertyValueBufferStartBuilding);
                        } catch (Exception e) {
                            objWrapInstantiationProblem = wrapInstantiationProblem(e, deserializationContext);
                        }
                        Object objHandleUnknownProperties = objWrapInstantiationProblem;
                        if (objHandleUnknownProperties == null) {
                            return deserializationContext.handleInstantiationProblem(handledType(), null, _creatorReturnedNullException());
                        }
                        jsonParser.assignCurrentValue(objHandleUnknownProperties);
                        if (objHandleUnknownProperties.getClass() != this._beanType.getRawClass()) {
                            return handlePolymorphic(jsonParser, deserializationContext, jsonParser.streamReadConstraints(), objHandleUnknownProperties, tokenBufferBufferForInputBuffering);
                        }
                        if (tokenBufferBufferForInputBuffering != null) {
                            objHandleUnknownProperties = handleUnknownProperties(deserializationContext, objHandleUnknownProperties, tokenBufferBufferForInputBuffering);
                        }
                        return deserialize(jsonParser, deserializationContext, objHandleUnknownProperties);
                    }
                } else {
                    SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
                    if (settableBeanPropertyFind != null && (!this._beanType.isRecordType() || (settableBeanPropertyFind instanceof MethodProperty))) {
                        try {
                            propertyValueBufferStartBuilding.bufferProperty(settableBeanPropertyFind, _deserializeWithErrorWrapping(jsonParser, deserializationContext, settableBeanPropertyFind));
                        } catch (UnresolvedForwardReference e2) {
                            BeanReferring beanReferringHandleUnresolvedReference = handleUnresolvedReference(deserializationContext, settableBeanPropertyFind, propertyValueBufferStartBuilding, e2);
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(beanReferringHandleUnresolvedReference);
                        }
                    } else if (IgnorePropertiesUtil.shouldIgnore(strCurrentName, this._ignorableProps, this._includableProps)) {
                        handleIgnoredProperty(jsonParser, deserializationContext, handledType(), strCurrentName);
                    } else if (this._anySetter != null) {
                        try {
                            if (this._anySetter.isFieldType() || this._anySetter.isSetterType()) {
                                propertyValueBufferStartBuilding.bufferAnyProperty(this._anySetter, strCurrentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                            } else {
                                propertyValueBufferStartBuilding.bufferAnyParameterProperty(this._anySetter, strCurrentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                            }
                        } catch (Exception e3) {
                            wrapAndThrow(e3, this._beanType.getRawClass(), strCurrentName, deserializationContext);
                        }
                    } else if (this._ignoreAllUnknown) {
                        jsonParser.skipChildren();
                    } else {
                        if (tokenBufferBufferForInputBuffering == null) {
                            tokenBufferBufferForInputBuffering = deserializationContext.bufferForInputBuffering(jsonParser);
                        }
                        tokenBufferBufferForInputBuffering.writeFieldName(strCurrentName);
                        tokenBufferBufferForInputBuffering.copyCurrentStructure(jsonParser);
                    }
                }
            }
            jsonTokenCurrentToken = jsonParser.nextToken();
        }
        try {
            Object objBuild = propertyBasedCreator.build(deserializationContext, propertyValueBufferStartBuilding);
            if (this._injectables != null) {
                injectValues(deserializationContext, objBuild);
            }
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ((BeanReferring) it.next()).setBean(objBuild);
                }
            }
            if (tokenBufferBufferForInputBuffering == null) {
                return objBuild;
            }
            if (objBuild.getClass() != this._beanType.getRawClass()) {
                return handlePolymorphic(null, deserializationContext, jsonParser.streamReadConstraints(), objBuild, tokenBufferBufferForInputBuffering);
            }
            return handleUnknownProperties(deserializationContext, objBuild, tokenBufferBufferForInputBuffering);
        } catch (Exception e4) {
            return wrapInstantiationProblem(e4, deserializationContext);
        }
    }

    private BeanReferring handleUnresolvedReference(DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty, PropertyValueBuffer propertyValueBuffer, UnresolvedForwardReference unresolvedForwardReference) throws JsonMappingException {
        BeanReferring beanReferring = new BeanReferring(deserializationContext, unresolvedForwardReference, settableBeanProperty.getType(), propertyValueBuffer, settableBeanProperty);
        unresolvedForwardReference.getRoid().appendReferring(beanReferring);
        return beanReferring;
    }

    protected final Object _deserializeWithErrorWrapping(JsonParser jsonParser, DeserializationContext deserializationContext, SettableBeanProperty settableBeanProperty) throws IOException {
        try {
            return settableBeanProperty.deserialize(jsonParser, deserializationContext);
        } catch (Exception e) {
            return wrapAndThrow(e, this._beanType.getRawClass(), settableBeanProperty.getName(), deserializationContext);
        }
    }

    protected Object deserializeFromNull(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.requiresCustomCodec()) {
            TokenBuffer tokenBufferBufferForInputBuffering = deserializationContext.bufferForInputBuffering(jsonParser);
            tokenBufferBufferForInputBuffering.writeEndObject();
            JsonParser jsonParserAsParser = tokenBufferBufferForInputBuffering.asParser(jsonParser);
            jsonParserAsParser.nextToken();
            Object objVanillaDeserialize = this._vanillaProcessing ? vanillaDeserialize(jsonParserAsParser, deserializationContext, JsonToken.END_OBJECT) : deserializeFromObject(jsonParserAsParser, deserializationContext);
            jsonParserAsParser.close();
            return objVanillaDeserialize;
        }
        return deserializationContext.handleUnexpectedToken(getValueType(deserializationContext), jsonParser);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer
    protected Object _deserializeFromArray(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonDeserializer<Object> jsonDeserializer = this._arrayDelegateDeserializer;
        if (jsonDeserializer != null || (jsonDeserializer = this._delegateDeserializer) != null) {
            Object objCreateUsingArrayDelegate = this._valueInstantiator.createUsingArrayDelegate(deserializationContext, jsonDeserializer.deserialize(jsonParser, deserializationContext));
            if (this._injectables != null) {
                injectValues(deserializationContext, objCreateUsingArrayDelegate);
            }
            return objCreateUsingArrayDelegate;
        }
        CoercionAction coercionAction_findCoercionFromEmptyArray = _findCoercionFromEmptyArray(deserializationContext);
        boolean zIsEnabled = deserializationContext.isEnabled(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
        if (zIsEnabled || coercionAction_findCoercionFromEmptyArray != CoercionAction.Fail) {
            JsonToken jsonTokenNextToken = jsonParser.nextToken();
            if (jsonTokenNextToken == JsonToken.END_ARRAY) {
                int i = C14311.$SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction[coercionAction_findCoercionFromEmptyArray.ordinal()];
                if (i == 1) {
                    return getEmptyValue(deserializationContext);
                }
                if (i == 2 || i == 3) {
                    return getNullValue(deserializationContext);
                }
                return deserializationContext.handleUnexpectedToken(getValueType(deserializationContext), JsonToken.START_ARRAY, jsonParser, (String) null, new Object[0]);
            }
            if (zIsEnabled) {
                if (jsonTokenNextToken == JsonToken.START_ARRAY) {
                    JavaType valueType = getValueType(deserializationContext);
                    return deserializationContext.handleUnexpectedToken(valueType, JsonToken.START_ARRAY, jsonParser, "Cannot deserialize value of type %s from deeply-nested Array: only single wrapper allowed with `%s`", ClassUtil.getTypeDescription(valueType), "DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS");
                }
                Object objDeserialize = deserialize(jsonParser, deserializationContext);
                if (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    handleMissingEndArrayForSingle(jsonParser, deserializationContext);
                }
                return objDeserialize;
            }
        }
        return deserializationContext.handleUnexpectedToken(getValueType(deserializationContext), jsonParser);
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.databind.deser.BeanDeserializer$1 */
    static /* synthetic */ class C14311 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$core$JsonToken;
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction;

        static {
            int[] iArr = new int[CoercionAction.values().length];
            $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction = iArr;
            try {
                iArr[CoercionAction.AsEmpty.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction[CoercionAction.AsNull.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$cfg$CoercionAction[CoercionAction.TryConvert.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[JsonToken.values().length];
            $SwitchMap$com$fasterxml$jackson$core$JsonToken = iArr2;
            try {
                iArr2[JsonToken.VALUE_STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_INT.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NUMBER_FLOAT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_EMBEDDED_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_TRUE.ordinal()] = 5;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_FALSE.ordinal()] = 6;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.VALUE_NULL.ordinal()] = 7;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.START_ARRAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.FIELD_NAME.ordinal()] = 9;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$core$JsonToken[JsonToken.END_OBJECT.ordinal()] = 10;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    protected final Object deserializeWithView(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, Class<?> cls) throws IOException {
        if (jsonParser.hasTokenId(5)) {
            String strCurrentName = jsonParser.currentName();
            do {
                jsonParser.nextToken();
                SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
                if (settableBeanPropertyFind != null) {
                    if (!settableBeanPropertyFind.visibleInView(cls)) {
                        if (deserializationContext.isEnabled(DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES)) {
                            deserializationContext.reportInputMismatch(handledType(), String.format("Input mismatch while deserializing %s. Property '%s' is not part of current active view '%s' (disable 'DeserializationFeature.FAIL_ON_UNEXPECTED_VIEW_PROPERTIES' to allow)", ClassUtil.nameOf(handledType()), settableBeanPropertyFind.getName(), cls.getName()), new Object[0]);
                        }
                        jsonParser.skipChildren();
                    } else {
                        try {
                            settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, obj);
                        } catch (Exception e) {
                            wrapAndThrow(e, obj, strCurrentName, deserializationContext);
                        }
                    }
                } else {
                    handleUnknownVanilla(jsonParser, deserializationContext, obj, strCurrentName);
                }
                strCurrentName = jsonParser.nextFieldName();
            } while (strCurrentName != null);
        }
        return obj;
    }

    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._propertyBasedCreator != null) {
            return deserializeUsingPropertyBasedWithUnwrapped(jsonParser, deserializationContext);
        }
        TokenBuffer tokenBufferBufferForInputBuffering = deserializationContext.bufferForInputBuffering(jsonParser);
        tokenBufferBufferForInputBuffering.writeStartObject();
        Object objCreateUsingDefault = this._valueInstantiator.createUsingDefault(deserializationContext);
        jsonParser.assignCurrentValue(objCreateUsingDefault);
        if (this._injectables != null) {
            injectValues(deserializationContext, objCreateUsingDefault);
        }
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        String strCurrentName = jsonParser.hasTokenId(5) ? jsonParser.currentName() : null;
        while (strCurrentName != null) {
            jsonParser.nextToken();
            SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
            if (settableBeanPropertyFind != null) {
                if (activeView != null && !settableBeanPropertyFind.visibleInView(activeView)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, objCreateUsingDefault);
                    } catch (Exception e) {
                        wrapAndThrow(e, objCreateUsingDefault, strCurrentName, deserializationContext);
                    }
                }
            } else if (IgnorePropertiesUtil.shouldIgnore(strCurrentName, this._ignorableProps, this._includableProps)) {
                handleIgnoredProperty(jsonParser, deserializationContext, objCreateUsingDefault, strCurrentName);
            } else if (this._anySetter == null) {
                tokenBufferBufferForInputBuffering.writeFieldName(strCurrentName);
                tokenBufferBufferForInputBuffering.copyCurrentStructure(jsonParser);
            } else {
                TokenBuffer tokenBufferBufferAsCopyOfValue = deserializationContext.bufferAsCopyOfValue(jsonParser);
                tokenBufferBufferForInputBuffering.writeFieldName(strCurrentName);
                tokenBufferBufferForInputBuffering.append(tokenBufferBufferAsCopyOfValue);
                try {
                    this._anySetter.deserializeAndSet(tokenBufferBufferAsCopyOfValue.asParserOnFirstToken(), deserializationContext, objCreateUsingDefault, strCurrentName);
                } catch (Exception e2) {
                    wrapAndThrow(e2, objCreateUsingDefault, strCurrentName, deserializationContext);
                }
            }
            strCurrentName = jsonParser.nextFieldName();
        }
        tokenBufferBufferForInputBuffering.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, objCreateUsingDefault, tokenBufferBufferForInputBuffering);
        return objCreateUsingDefault;
    }

    protected Object deserializeWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        if (jsonTokenCurrentToken == JsonToken.START_OBJECT) {
            jsonTokenCurrentToken = jsonParser.nextToken();
        }
        TokenBuffer tokenBufferBufferForInputBuffering = deserializationContext.bufferForInputBuffering(jsonParser);
        tokenBufferBufferForInputBuffering.writeStartObject();
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        while (jsonTokenCurrentToken == JsonToken.FIELD_NAME) {
            String strCurrentName = jsonParser.currentName();
            SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
            jsonParser.nextToken();
            if (settableBeanPropertyFind != null) {
                if (activeView != null && !settableBeanPropertyFind.visibleInView(activeView)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, obj);
                    } catch (Exception e) {
                        wrapAndThrow(e, obj, strCurrentName, deserializationContext);
                    }
                }
            } else if (IgnorePropertiesUtil.shouldIgnore(strCurrentName, this._ignorableProps, this._includableProps)) {
                handleIgnoredProperty(jsonParser, deserializationContext, obj, strCurrentName);
            } else if (this._anySetter == null) {
                tokenBufferBufferForInputBuffering.writeFieldName(strCurrentName);
                tokenBufferBufferForInputBuffering.copyCurrentStructure(jsonParser);
            } else {
                TokenBuffer tokenBufferBufferAsCopyOfValue = deserializationContext.bufferAsCopyOfValue(jsonParser);
                tokenBufferBufferForInputBuffering.writeFieldName(strCurrentName);
                tokenBufferBufferForInputBuffering.append(tokenBufferBufferAsCopyOfValue);
                try {
                    this._anySetter.deserializeAndSet(tokenBufferBufferAsCopyOfValue.asParserOnFirstToken(), deserializationContext, obj, strCurrentName);
                } catch (Exception e2) {
                    wrapAndThrow(e2, obj, strCurrentName, deserializationContext);
                }
            }
            jsonTokenCurrentToken = jsonParser.nextToken();
        }
        tokenBufferBufferForInputBuffering.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, obj, tokenBufferBufferForInputBuffering);
        return obj;
    }

    protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object objWrapInstantiationProblem;
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBufferStartBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        TokenBuffer tokenBufferBufferForInputBuffering = deserializationContext.bufferForInputBuffering(jsonParser);
        tokenBufferBufferForInputBuffering.writeStartObject();
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        while (jsonTokenCurrentToken == JsonToken.FIELD_NAME) {
            String strCurrentName = jsonParser.currentName();
            jsonParser.nextToken();
            SettableBeanProperty settableBeanPropertyFindCreatorProperty = propertyBasedCreator.findCreatorProperty(strCurrentName);
            if (!propertyValueBufferStartBuilding.readIdProperty(strCurrentName) || settableBeanPropertyFindCreatorProperty != null) {
                if (settableBeanPropertyFindCreatorProperty != null) {
                    if (propertyValueBufferStartBuilding.assignParameter(settableBeanPropertyFindCreatorProperty, _deserializeWithErrorWrapping(jsonParser, deserializationContext, settableBeanPropertyFindCreatorProperty))) {
                        JsonToken jsonTokenNextToken = jsonParser.nextToken();
                        try {
                            objWrapInstantiationProblem = propertyBasedCreator.build(deserializationContext, propertyValueBufferStartBuilding);
                        } catch (Exception e) {
                            objWrapInstantiationProblem = wrapInstantiationProblem(e, deserializationContext);
                        }
                        jsonParser.assignCurrentValue(objWrapInstantiationProblem);
                        while (jsonTokenNextToken == JsonToken.FIELD_NAME) {
                            tokenBufferBufferForInputBuffering.copyCurrentStructure(jsonParser);
                            jsonTokenNextToken = jsonParser.nextToken();
                        }
                        if (jsonTokenNextToken != JsonToken.END_OBJECT) {
                            deserializationContext.reportWrongTokenException(this, JsonToken.END_OBJECT, "Attempted to unwrap '%s' value", handledType().getName());
                        }
                        tokenBufferBufferForInputBuffering.writeEndObject();
                        if (objWrapInstantiationProblem.getClass() != this._beanType.getRawClass()) {
                            return deserializationContext.reportInputMismatch(settableBeanPropertyFindCreatorProperty, "Cannot create polymorphic instances with unwrapped values", new Object[0]);
                        }
                        return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, objWrapInstantiationProblem, tokenBufferBufferForInputBuffering);
                    }
                } else {
                    SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
                    if (settableBeanPropertyFind != null) {
                        propertyValueBufferStartBuilding.bufferProperty(settableBeanPropertyFind, _deserializeWithErrorWrapping(jsonParser, deserializationContext, settableBeanPropertyFind));
                    } else if (IgnorePropertiesUtil.shouldIgnore(strCurrentName, this._ignorableProps, this._includableProps)) {
                        handleIgnoredProperty(jsonParser, deserializationContext, handledType(), strCurrentName);
                    } else if (this._anySetter == null) {
                        tokenBufferBufferForInputBuffering.writeFieldName(strCurrentName);
                        tokenBufferBufferForInputBuffering.copyCurrentStructure(jsonParser);
                    } else {
                        TokenBuffer tokenBufferBufferAsCopyOfValue = deserializationContext.bufferAsCopyOfValue(jsonParser);
                        tokenBufferBufferForInputBuffering.writeFieldName(strCurrentName);
                        tokenBufferBufferForInputBuffering.append(tokenBufferBufferAsCopyOfValue);
                        try {
                            propertyValueBufferStartBuilding.bufferAnyProperty(this._anySetter, strCurrentName, this._anySetter.deserialize(tokenBufferBufferAsCopyOfValue.asParserOnFirstToken(), deserializationContext));
                        } catch (Exception e2) {
                            wrapAndThrow(e2, this._beanType.getRawClass(), strCurrentName, deserializationContext);
                        }
                    }
                }
            }
            jsonTokenCurrentToken = jsonParser.nextToken();
        }
        try {
            return this._unwrappedPropertyHandler.processUnwrapped(jsonParser, deserializationContext, propertyBasedCreator.build(deserializationContext, propertyValueBufferStartBuilding), tokenBufferBufferForInputBuffering);
        } catch (Exception e3) {
            return wrapInstantiationProblem(e3, deserializationContext);
        }
    }

    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._propertyBasedCreator != null) {
            return deserializeUsingPropertyBasedWithExternalTypeId(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        return deserializeWithExternalTypeId(jsonParser, deserializationContext, this._valueInstantiator.createUsingDefault(deserializationContext));
    }

    protected Object deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj) throws IOException {
        return _deserializeWithExternalTypeId(jsonParser, deserializationContext, obj, this._externalTypeIdHandler.start());
    }

    protected Object _deserializeWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext, Object obj, ExternalTypeHandler externalTypeHandler) throws IOException {
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        while (jsonTokenCurrentToken == JsonToken.FIELD_NAME) {
            String strCurrentName = jsonParser.currentName();
            JsonToken jsonTokenNextToken = jsonParser.nextToken();
            SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
            if (settableBeanPropertyFind != null) {
                if (jsonTokenNextToken.isScalarValue()) {
                    externalTypeHandler.handleTypePropertyValue(jsonParser, deserializationContext, strCurrentName, obj);
                }
                if (activeView != null && !settableBeanPropertyFind.visibleInView(activeView)) {
                    jsonParser.skipChildren();
                } else {
                    try {
                        settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, obj);
                    } catch (Exception e) {
                        wrapAndThrow(e, obj, strCurrentName, deserializationContext);
                    }
                }
            } else if (IgnorePropertiesUtil.shouldIgnore(strCurrentName, this._ignorableProps, this._includableProps)) {
                handleIgnoredProperty(jsonParser, deserializationContext, obj, strCurrentName);
            } else if (!externalTypeHandler.handlePropertyValue(jsonParser, deserializationContext, strCurrentName, obj)) {
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jsonParser, deserializationContext, obj, strCurrentName);
                    } catch (Exception e2) {
                        wrapAndThrow(e2, obj, strCurrentName, deserializationContext);
                    }
                } else {
                    handleUnknownProperty(jsonParser, deserializationContext, obj, strCurrentName);
                }
            }
            jsonTokenCurrentToken = jsonParser.nextToken();
        }
        return externalTypeHandler.complete(jsonParser, deserializationContext, obj);
    }

    protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ExternalTypeHandler externalTypeHandlerStart = this._externalTypeIdHandler.start();
        PropertyBasedCreator propertyBasedCreator = this._propertyBasedCreator;
        PropertyValueBuffer propertyValueBufferStartBuilding = propertyBasedCreator.startBuilding(jsonParser, deserializationContext, this._objectIdReader);
        Class<?> activeView = this._needViewProcesing ? deserializationContext.getActiveView() : null;
        JsonToken jsonTokenCurrentToken = jsonParser.currentToken();
        while (jsonTokenCurrentToken == JsonToken.FIELD_NAME) {
            String strCurrentName = jsonParser.currentName();
            JsonToken jsonTokenNextToken = jsonParser.nextToken();
            SettableBeanProperty settableBeanPropertyFindCreatorProperty = propertyBasedCreator.findCreatorProperty(strCurrentName);
            if (!propertyValueBufferStartBuilding.readIdProperty(strCurrentName) || settableBeanPropertyFindCreatorProperty != null) {
                if (settableBeanPropertyFindCreatorProperty != null) {
                    if (!externalTypeHandlerStart.handlePropertyValue(jsonParser, deserializationContext, strCurrentName, null) && propertyValueBufferStartBuilding.assignParameter(settableBeanPropertyFindCreatorProperty, _deserializeWithErrorWrapping(jsonParser, deserializationContext, settableBeanPropertyFindCreatorProperty))) {
                        jsonParser.nextToken();
                        try {
                            Object objBuild = propertyBasedCreator.build(deserializationContext, propertyValueBufferStartBuilding);
                            if (objBuild.getClass() != this._beanType.getRawClass()) {
                                return deserializationContext.reportBadDefinition(this._beanType, String.format("Cannot create polymorphic instances with external type ids (%s -> %s)", this._beanType, objBuild.getClass()));
                            }
                            return _deserializeWithExternalTypeId(jsonParser, deserializationContext, objBuild, externalTypeHandlerStart);
                        } catch (Exception e) {
                            wrapAndThrow(e, this._beanType.getRawClass(), strCurrentName, deserializationContext);
                        }
                    }
                } else {
                    SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
                    if (settableBeanPropertyFind != null) {
                        if (jsonTokenNextToken.isScalarValue()) {
                            externalTypeHandlerStart.handleTypePropertyValue(jsonParser, deserializationContext, strCurrentName, null);
                        }
                        if (activeView != null && !settableBeanPropertyFind.visibleInView(activeView)) {
                            jsonParser.skipChildren();
                        } else {
                            propertyValueBufferStartBuilding.bufferProperty(settableBeanPropertyFind, settableBeanPropertyFind.deserialize(jsonParser, deserializationContext));
                        }
                    } else if (!externalTypeHandlerStart.handlePropertyValue(jsonParser, deserializationContext, strCurrentName, null)) {
                        if (IgnorePropertiesUtil.shouldIgnore(strCurrentName, this._ignorableProps, this._includableProps)) {
                            handleIgnoredProperty(jsonParser, deserializationContext, handledType(), strCurrentName);
                        } else if (this._anySetter != null) {
                            propertyValueBufferStartBuilding.bufferAnyProperty(this._anySetter, strCurrentName, this._anySetter.deserialize(jsonParser, deserializationContext));
                        } else {
                            handleUnknownProperty(jsonParser, deserializationContext, this._valueClass, strCurrentName);
                        }
                    }
                }
            }
            jsonTokenCurrentToken = jsonParser.nextToken();
        }
        try {
            return externalTypeHandlerStart.complete(jsonParser, deserializationContext, propertyValueBufferStartBuilding, propertyBasedCreator);
        } catch (Exception e2) {
            return wrapInstantiationProblem(e2, deserializationContext);
        }
    }

    protected Exception _creatorReturnedNullException() {
        if (this._nullFromCreator == null) {
            this._nullFromCreator = new NullPointerException("JSON Creator returned null");
        }
        return this._nullFromCreator;
    }

    static class BeanReferring extends ReadableObjectId.Referring {
        private Object _bean;
        private final DeserializationContext _context;
        private final SettableBeanProperty _prop;

        BeanReferring(DeserializationContext deserializationContext, UnresolvedForwardReference unresolvedForwardReference, JavaType javaType, PropertyValueBuffer propertyValueBuffer, SettableBeanProperty settableBeanProperty) {
            super(unresolvedForwardReference, javaType);
            this._context = deserializationContext;
            this._prop = settableBeanProperty;
        }

        public void setBean(Object obj) {
            this._bean = obj;
        }

        @Override // com.fasterxml.jackson.databind.deser.impl.ReadableObjectId.Referring
        public void handleResolvedForwardReference(Object obj, Object obj2) throws IOException {
            if (this._bean == null) {
                DeserializationContext deserializationContext = this._context;
                SettableBeanProperty settableBeanProperty = this._prop;
                deserializationContext.reportInputMismatch(settableBeanProperty, "Cannot resolve ObjectId forward reference using property '%s' (of type %s): Bean not yet resolved", settableBeanProperty.getName(), this._prop.getDeclaringClass().getName());
            }
            this._prop.set(this._bean, obj2);
        }
    }
}
