package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class ThrowableDeserializer extends BeanDeserializer {
    protected static final String PROP_NAME_LOCALIZED_MESSAGE = "localizedMessage";
    protected static final String PROP_NAME_MESSAGE = "message";
    protected static final String PROP_NAME_SUPPRESSED = "suppressed";
    private static final long serialVersionUID = 1;

    protected ThrowableDeserializer(BeanDeserializer beanDeserializer, NameTransformer nameTransformer) {
        super(beanDeserializer, nameTransformer);
        this._vanillaProcessing = false;
    }

    @Deprecated
    public ThrowableDeserializer(BeanDeserializer beanDeserializer) {
        this(beanDeserializer, null);
    }

    public static ThrowableDeserializer construct(DeserializationContext deserializationContext, BeanDeserializer beanDeserializer) {
        return new ThrowableDeserializer(beanDeserializer, null);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializer, com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer nameTransformer) {
        return getClass() != ThrowableDeserializer.class ? this : new ThrowableDeserializer(this, nameTransformer);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializer, com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public Object deserializeFromObject(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (this._propertyBasedCreator != null) {
            return _deserializeUsingPropertyBased(jsonParser, deserializationContext);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(deserializationContext, this._delegateDeserializer.deserialize(jsonParser, deserializationContext));
        }
        if (this._beanType.isAbstract()) {
            return deserializationContext.handleMissingInstantiator(handledType(), getValueInstantiator(), jsonParser, "abstract type (need to add/enable type information?)", new Object[0]);
        }
        boolean zCanCreateFromString = this._valueInstantiator.canCreateFromString();
        boolean zCanCreateUsingDefault = this._valueInstantiator.canCreateUsingDefault();
        if (!zCanCreateFromString && !zCanCreateUsingDefault) {
            return deserializationContext.handleMissingInstantiator(handledType(), getValueInstantiator(), jsonParser, "Throwable needs a default constructor, a single-String-arg constructor; or explicit @JsonCreator", new Object[0]);
        }
        int i = 0;
        Throwable th_instantiate = null;
        Object[] objArrCopyOf = null;
        Throwable[] thArr = null;
        while (!jsonParser.hasToken(JsonToken.END_OBJECT)) {
            String strCurrentName = jsonParser.currentName();
            SettableBeanProperty settableBeanPropertyFind = this._beanProperties.find(strCurrentName);
            jsonParser.nextToken();
            if (settableBeanPropertyFind != null) {
                if (!"cause".equals(settableBeanPropertyFind.getName()) || !jsonParser.hasToken(JsonToken.VALUE_NULL)) {
                    if (th_instantiate != null) {
                        settableBeanPropertyFind.deserializeAndSet(jsonParser, deserializationContext, th_instantiate);
                    } else {
                        if (objArrCopyOf == null) {
                            int size = this._beanProperties.size();
                            objArrCopyOf = new Object[size + size];
                        } else if (i == objArrCopyOf.length) {
                            objArrCopyOf = Arrays.copyOf(objArrCopyOf, i + 16);
                        }
                        int i2 = i + 1;
                        objArrCopyOf[i] = settableBeanPropertyFind;
                        i += 2;
                        objArrCopyOf[i2] = settableBeanPropertyFind.deserialize(jsonParser, deserializationContext);
                    }
                }
            } else if ("message".equalsIgnoreCase(strCurrentName)) {
                th_instantiate = _instantiate(deserializationContext, zCanCreateFromString, jsonParser.getValueAsString());
            } else if (this._ignorableProps != null && this._ignorableProps.contains(strCurrentName)) {
                jsonParser.skipChildren();
            } else if (PROP_NAME_SUPPRESSED.equalsIgnoreCase(strCurrentName)) {
                thArr = jsonParser.hasToken(JsonToken.VALUE_NULL) ? null : (Throwable[]) deserializationContext.findRootValueDeserializer(deserializationContext.constructType(Throwable[].class)).deserialize(jsonParser, deserializationContext);
            } else if (PROP_NAME_LOCALIZED_MESSAGE.equalsIgnoreCase(strCurrentName)) {
                jsonParser.skipChildren();
            } else if (this._anySetter != null) {
                if (th_instantiate == null) {
                    th_instantiate = _instantiate(deserializationContext, zCanCreateFromString, null);
                }
                this._anySetter.deserializeAndSet(jsonParser, deserializationContext, th_instantiate, strCurrentName);
            } else if ("message".equalsIgnoreCase(strCurrentName)) {
                jsonParser.skipChildren();
            } else {
                handleUnknownProperty(jsonParser, deserializationContext, th_instantiate, strCurrentName);
            }
            jsonParser.nextToken();
        }
        if (th_instantiate == null) {
            th_instantiate = _instantiate(deserializationContext, zCanCreateFromString, null);
        }
        if (objArrCopyOf != null) {
            for (int i3 = 0; i3 < i; i3 += 2) {
                ((SettableBeanProperty) objArrCopyOf[i3]).set(th_instantiate, objArrCopyOf[i3 + 1]);
            }
        }
        if (thArr != null) {
            for (Throwable th : thArr) {
                if (th != null) {
                    th_instantiate.addSuppressed(th);
                }
            }
        }
        return th_instantiate;
    }

    private Throwable _instantiate(DeserializationContext deserializationContext, boolean z, String str) throws IOException {
        if (!z) {
            return (Throwable) this._valueInstantiator.createUsingDefault(deserializationContext);
        }
        if (str != null) {
            return (Throwable) this._valueInstantiator.createFromString(deserializationContext, str);
        }
        return (Throwable) this._valueInstantiator.createFromString(deserializationContext, null);
    }
}
