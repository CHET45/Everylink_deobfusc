package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.PropertyValue;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.IOException;
import java.util.BitSet;

/* JADX INFO: loaded from: classes3.dex */
public class PropertyValueBuffer {
    protected PropertyValue _anyParamBuffered;
    protected final SettableAnyProperty _anyParamSetter;
    protected PropertyValue _buffered;
    protected final DeserializationContext _context;
    protected final Object[] _creatorParameters;
    protected Object _idValue;
    protected final ObjectIdReader _objectIdReader;
    protected int _paramsNeeded;
    protected int _paramsSeen;
    protected final BitSet _paramsSeenBig;
    protected final JsonParser _parser;

    public PropertyValueBuffer(JsonParser jsonParser, DeserializationContext deserializationContext, int i, ObjectIdReader objectIdReader, SettableAnyProperty settableAnyProperty) {
        this._parser = jsonParser;
        this._context = deserializationContext;
        this._paramsNeeded = i;
        this._objectIdReader = objectIdReader;
        this._creatorParameters = new Object[i];
        if (i < 32) {
            this._paramsSeenBig = null;
        } else {
            this._paramsSeenBig = new BitSet();
        }
        if (settableAnyProperty == null || settableAnyProperty.getParameterIndex() < 0) {
            this._anyParamSetter = null;
        } else {
            this._anyParamSetter = settableAnyProperty;
        }
    }

    @Deprecated
    public PropertyValueBuffer(JsonParser jsonParser, DeserializationContext deserializationContext, int i, ObjectIdReader objectIdReader) {
        this(jsonParser, deserializationContext, i, objectIdReader, null);
    }

    public final boolean hasParameter(SettableBeanProperty settableBeanProperty) {
        int creatorIndex = settableBeanProperty.getCreatorIndex();
        BitSet bitSet = this._paramsSeenBig;
        if (bitSet == null) {
            if (((this._paramsSeen >> creatorIndex) & 1) == 1) {
                return true;
            }
        } else if (bitSet.get(creatorIndex)) {
            return true;
        }
        SettableAnyProperty settableAnyProperty = this._anyParamSetter;
        return settableAnyProperty != null && creatorIndex == settableAnyProperty.getParameterIndex();
    }

    public Object getParameter(SettableBeanProperty settableBeanProperty) throws DatabindException {
        Object obj_createAndSetAnySetterValue;
        if (hasParameter(settableBeanProperty)) {
            obj_createAndSetAnySetterValue = this._creatorParameters[settableBeanProperty.getCreatorIndex()];
        } else {
            Object[] objArr = this._creatorParameters;
            int creatorIndex = settableBeanProperty.getCreatorIndex();
            Object obj_findMissing = _findMissing(settableBeanProperty);
            objArr[creatorIndex] = obj_findMissing;
            obj_createAndSetAnySetterValue = obj_findMissing;
        }
        if (obj_createAndSetAnySetterValue == null && this._anyParamSetter != null && settableBeanProperty.getCreatorIndex() == this._anyParamSetter.getParameterIndex()) {
            obj_createAndSetAnySetterValue = _createAndSetAnySetterValue();
        }
        return (obj_createAndSetAnySetterValue == null && this._context.isEnabled(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)) ? this._context.reportInputMismatch(settableBeanProperty, "Null value for creator property '%s' (index %d); `DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES` enabled", settableBeanProperty.getName(), Integer.valueOf(settableBeanProperty.getCreatorIndex())) : obj_createAndSetAnySetterValue;
    }

    public Object[] getParameters(SettableBeanProperty[] settableBeanPropertyArr) throws IOException {
        if (this._paramsNeeded > 0) {
            if (this._paramsSeenBig == null) {
                int i = this._paramsSeen;
                int length = this._creatorParameters.length;
                int i2 = 0;
                while (i2 < length) {
                    if ((i & 1) == 0) {
                        this._creatorParameters[i2] = _findMissing(settableBeanPropertyArr[i2]);
                    }
                    i2++;
                    i >>= 1;
                }
            } else {
                int length2 = this._creatorParameters.length;
                int i3 = 0;
                while (true) {
                    int iNextClearBit = this._paramsSeenBig.nextClearBit(i3);
                    if (iNextClearBit >= length2) {
                        break;
                    }
                    this._creatorParameters[iNextClearBit] = _findMissing(settableBeanPropertyArr[iNextClearBit]);
                    i3 = iNextClearBit + 1;
                }
            }
        }
        SettableAnyProperty settableAnyProperty = this._anyParamSetter;
        if (settableAnyProperty != null) {
            this._creatorParameters[settableAnyProperty.getParameterIndex()] = _createAndSetAnySetterValue();
        }
        if (this._context.isEnabled(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES)) {
            for (int i4 = 0; i4 < settableBeanPropertyArr.length; i4++) {
                if (this._creatorParameters[i4] == null) {
                    SettableBeanProperty settableBeanProperty = settableBeanPropertyArr[i4];
                    this._context.reportInputMismatch(settableBeanProperty, "Null value for creator property '%s' (index %d); `DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES` enabled", settableBeanProperty.getName(), Integer.valueOf(settableBeanPropertyArr[i4].getCreatorIndex()));
                }
            }
        }
        return this._creatorParameters;
    }

    private Object _createAndSetAnySetterValue() throws JsonMappingException {
        Object objCreateParameterObject = this._anyParamSetter.createParameterObject();
        for (PropertyValue propertyValue = this._anyParamBuffered; propertyValue != null; propertyValue = propertyValue.next) {
            try {
                propertyValue.setValue(objCreateParameterObject);
            } catch (JsonMappingException e) {
                throw e;
            } catch (IOException e2) {
                throw JsonMappingException.fromUnexpectedIOE(e2);
            }
        }
        return objCreateParameterObject;
    }

    protected Object _findMissing(SettableBeanProperty settableBeanProperty) throws DatabindException {
        if (this._anyParamSetter != null && settableBeanProperty.getCreatorIndex() == this._anyParamSetter.getParameterIndex() && this._anyParamBuffered != null) {
            return null;
        }
        if (settableBeanProperty.getInjectableValueId() != null) {
            return this._context.findInjectableValue(settableBeanProperty.getInjectableValueId(), settableBeanProperty, null);
        }
        if (settableBeanProperty.isRequired()) {
            this._context.reportInputMismatch(settableBeanProperty, "Missing required creator property '%s' (index %d)", settableBeanProperty.getName(), Integer.valueOf(settableBeanProperty.getCreatorIndex()));
        }
        if (this._context.isEnabled(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)) {
            this._context.reportInputMismatch(settableBeanProperty, "Missing creator property '%s' (index %d); `DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES` enabled", settableBeanProperty.getName(), Integer.valueOf(settableBeanProperty.getCreatorIndex()));
        }
        try {
            Object absentValue = settableBeanProperty.getNullValueProvider().getAbsentValue(this._context);
            return absentValue != null ? absentValue : settableBeanProperty.getValueDeserializer().getAbsentValue(this._context);
        } catch (DatabindException e) {
            AnnotatedMember member = settableBeanProperty.getMember();
            if (member != null) {
                e.prependPath(member.getDeclaringClass(), settableBeanProperty.getName());
            }
            throw e;
        }
    }

    public boolean readIdProperty(String str) throws IOException {
        ObjectIdReader objectIdReader = this._objectIdReader;
        if (objectIdReader == null || !str.equals(objectIdReader.propertyName.getSimpleName())) {
            return false;
        }
        this._idValue = this._objectIdReader.readObjectReference(this._parser, this._context);
        return true;
    }

    public Object handleIdValue(DeserializationContext deserializationContext, Object obj) throws IOException {
        ObjectIdReader objectIdReader = this._objectIdReader;
        if (objectIdReader != null) {
            Object obj2 = this._idValue;
            if (obj2 != null) {
                deserializationContext.findObjectId(obj2, objectIdReader.generator, this._objectIdReader.resolver).bindItem(obj);
                SettableBeanProperty settableBeanProperty = this._objectIdReader.idProperty;
                if (settableBeanProperty != null) {
                    return settableBeanProperty.setAndReturn(obj, this._idValue);
                }
            } else {
                deserializationContext.reportUnresolvedObjectId(objectIdReader, obj);
            }
        }
        return obj;
    }

    protected PropertyValue buffered() {
        return this._buffered;
    }

    public boolean isComplete() {
        return this._paramsNeeded <= 0;
    }

    public boolean assignParameter(SettableBeanProperty settableBeanProperty, Object obj) {
        int creatorIndex = settableBeanProperty.getCreatorIndex();
        this._creatorParameters[creatorIndex] = obj;
        BitSet bitSet = this._paramsSeenBig;
        if (bitSet == null) {
            int i = this._paramsSeen;
            int i2 = (1 << creatorIndex) | i;
            if (i != i2) {
                this._paramsSeen = i2;
                int i3 = this._paramsNeeded - 1;
                this._paramsNeeded = i3;
                if (i3 <= 0) {
                    return this._objectIdReader == null || this._idValue != null;
                }
            }
        } else if (!bitSet.get(creatorIndex)) {
            this._paramsSeenBig.set(creatorIndex);
            this._paramsNeeded--;
        }
        return false;
    }

    public void bufferProperty(SettableBeanProperty settableBeanProperty, Object obj) {
        this._buffered = new PropertyValue.Regular(this._buffered, obj, settableBeanProperty);
    }

    public void bufferAnyProperty(SettableAnyProperty settableAnyProperty, String str, Object obj) {
        this._buffered = new PropertyValue.Any(this._buffered, obj, settableAnyProperty, str);
    }

    public void bufferMapProperty(Object obj, Object obj2) {
        this._buffered = new PropertyValue.Map(this._buffered, obj2, obj);
    }

    public void bufferAnyParameterProperty(SettableAnyProperty settableAnyProperty, String str, Object obj) {
        this._anyParamBuffered = new PropertyValue.AnyParameter(this._anyParamBuffered, obj, settableAnyProperty, str);
    }
}
