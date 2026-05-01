package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.cfg.CacheProvider;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.microsoft.azure.storage.Constants;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DefaultDeserializationContext extends DeserializationContext implements Serializable {
    private static final long serialVersionUID = 1;
    private List<ObjectIdResolver> _objectIdResolvers;
    protected transient LinkedHashMap<ObjectIdGenerator.IdKey, ReadableObjectId> _objectIds;

    public abstract DefaultDeserializationContext createDummyInstance(DeserializationConfig deserializationConfig);

    public abstract DefaultDeserializationContext createInstance(DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues);

    public abstract DefaultDeserializationContext with(DeserializerFactory deserializerFactory);

    public abstract DefaultDeserializationContext withCaches(CacheProvider cacheProvider);

    protected DefaultDeserializationContext(DeserializerFactory deserializerFactory, DeserializerCache deserializerCache) {
        super(deserializerFactory, deserializerCache);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
        super(defaultDeserializationContext, deserializationConfig, jsonParser, injectableValues);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, DeserializationConfig deserializationConfig) {
        super(defaultDeserializationContext, deserializationConfig);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, DeserializerFactory deserializerFactory) {
        super(defaultDeserializationContext, deserializerFactory);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext) {
        super(defaultDeserializationContext);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, CacheProvider cacheProvider) {
        super(defaultDeserializationContext, new DeserializerCache(cacheProvider.forDeserializerCache(defaultDeserializationContext._config)));
    }

    public DefaultDeserializationContext copy() {
        throw new IllegalStateException("DefaultDeserializationContext sub-class not overriding copy()");
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public ReadableObjectId findObjectId(Object obj, ObjectIdGenerator<?> objectIdGenerator, ObjectIdResolver objectIdResolver) {
        ObjectIdResolver objectIdResolverNewForDeserialization = null;
        if (obj == null) {
            return null;
        }
        ObjectIdGenerator.IdKey idKeyKey = objectIdGenerator.key(obj);
        LinkedHashMap<ObjectIdGenerator.IdKey, ReadableObjectId> linkedHashMap = this._objectIds;
        if (linkedHashMap == null) {
            this._objectIds = new LinkedHashMap<>();
        } else {
            ReadableObjectId readableObjectId = linkedHashMap.get(idKeyKey);
            if (readableObjectId != null) {
                return readableObjectId;
            }
        }
        List<ObjectIdResolver> list = this._objectIdResolvers;
        if (list == null) {
            this._objectIdResolvers = new ArrayList(8);
        } else {
            Iterator<ObjectIdResolver> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ObjectIdResolver next = it.next();
                if (next.canUseFor(objectIdResolver)) {
                    objectIdResolverNewForDeserialization = next;
                    break;
                }
            }
        }
        if (objectIdResolverNewForDeserialization == null) {
            objectIdResolverNewForDeserialization = objectIdResolver.newForDeserialization(this);
            this._objectIdResolvers.add(objectIdResolverNewForDeserialization);
        }
        ReadableObjectId readableObjectIdCreateReadableObjectId = createReadableObjectId(idKeyKey);
        readableObjectIdCreateReadableObjectId.setResolver(objectIdResolverNewForDeserialization);
        this._objectIds.put(idKeyKey, readableObjectIdCreateReadableObjectId);
        return readableObjectIdCreateReadableObjectId;
    }

    protected ReadableObjectId createReadableObjectId(ObjectIdGenerator.IdKey idKey) {
        return new ReadableObjectId(idKey);
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public void checkUnresolvedObjectId() throws UnresolvedForwardReference {
        if (this._objectIds != null && isEnabled(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS)) {
            Iterator<Map.Entry<ObjectIdGenerator.IdKey, ReadableObjectId>> it = this._objectIds.entrySet().iterator();
            UnresolvedForwardReference unresolvedForwardReferenceWithStackTrace = null;
            while (it.hasNext()) {
                ReadableObjectId value = it.next().getValue();
                if (value.hasReferringProperties() && !tryToResolveUnresolvedObjectId(value)) {
                    if (unresolvedForwardReferenceWithStackTrace == null) {
                        unresolvedForwardReferenceWithStackTrace = new UnresolvedForwardReference(getParser(), "Unresolved forward references for: ").withStackTrace();
                    }
                    Object obj = value.getKey().key;
                    Iterator<ReadableObjectId.Referring> itReferringProperties = value.referringProperties();
                    while (itReferringProperties.hasNext()) {
                        ReadableObjectId.Referring next = itReferringProperties.next();
                        unresolvedForwardReferenceWithStackTrace.addUnresolvedId(obj, next.getBeanType(), next.getLocation());
                    }
                }
            }
            if (unresolvedForwardReferenceWithStackTrace != null) {
                throw unresolvedForwardReferenceWithStackTrace;
            }
        }
    }

    protected boolean tryToResolveUnresolvedObjectId(ReadableObjectId readableObjectId) {
        return readableObjectId.tryToResolveUnresolved(this);
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public JsonDeserializer<Object> deserializerInstance(Annotated annotated, Object obj) throws JsonMappingException {
        JsonDeserializer<?> jsonDeserializer;
        if (obj == null) {
            return null;
        }
        if (obj instanceof JsonDeserializer) {
            jsonDeserializer = (JsonDeserializer) obj;
        } else {
            if (!(obj instanceof Class)) {
                throw new IllegalStateException("AnnotationIntrospector returned deserializer definition of type " + obj.getClass().getName() + "; expected type JsonDeserializer or Class<JsonDeserializer> instead");
            }
            Class<?> cls = (Class) obj;
            if (cls == JsonDeserializer.None.class || ClassUtil.isBogusClass(cls)) {
                return null;
            }
            if (!JsonDeserializer.class.isAssignableFrom(cls)) {
                throw new IllegalStateException("AnnotationIntrospector returned Class " + cls.getName() + "; expected Class<JsonDeserializer>");
            }
            HandlerInstantiator handlerInstantiator = this._config.getHandlerInstantiator();
            JsonDeserializer<?> jsonDeserializerDeserializerInstance = handlerInstantiator != null ? handlerInstantiator.deserializerInstance(this._config, annotated, cls) : null;
            jsonDeserializer = jsonDeserializerDeserializerInstance == null ? (JsonDeserializer) ClassUtil.createInstance(cls, this._config.canOverrideAccessModifiers()) : jsonDeserializerDeserializerInstance;
        }
        if (jsonDeserializer instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer) jsonDeserializer).resolve(this);
        }
        return jsonDeserializer;
    }

    @Override // com.fasterxml.jackson.databind.DeserializationContext
    public final KeyDeserializer keyDeserializerInstance(Annotated annotated, Object obj) throws JsonMappingException {
        KeyDeserializer keyDeserializer;
        if (obj == null) {
            return null;
        }
        if (obj instanceof KeyDeserializer) {
            keyDeserializer = (KeyDeserializer) obj;
        } else {
            if (!(obj instanceof Class)) {
                throw new IllegalStateException("AnnotationIntrospector returned key deserializer definition of type " + obj.getClass().getName() + "; expected type KeyDeserializer or Class<KeyDeserializer> instead");
            }
            Class<?> cls = (Class) obj;
            if (cls == KeyDeserializer.None.class || ClassUtil.isBogusClass(cls)) {
                return null;
            }
            if (!KeyDeserializer.class.isAssignableFrom(cls)) {
                throw new IllegalStateException("AnnotationIntrospector returned Class " + cls.getName() + "; expected Class<KeyDeserializer>");
            }
            HandlerInstantiator handlerInstantiator = this._config.getHandlerInstantiator();
            KeyDeserializer keyDeserializerKeyDeserializerInstance = handlerInstantiator != null ? handlerInstantiator.keyDeserializerInstance(this._config, annotated, cls) : null;
            keyDeserializer = keyDeserializerKeyDeserializerInstance == null ? (KeyDeserializer) ClassUtil.createInstance(cls, this._config.canOverrideAccessModifiers()) : keyDeserializerKeyDeserializerInstance;
        }
        if (keyDeserializer instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer) keyDeserializer).resolve(this);
        }
        return keyDeserializer;
    }

    public Object readRootValue(JsonParser jsonParser, JavaType javaType, JsonDeserializer<Object> jsonDeserializer, Object obj) throws IOException {
        if (this._config.useRootWrapping()) {
            return _unwrapAndDeserialize(jsonParser, javaType, jsonDeserializer, obj);
        }
        if (obj == null) {
            return jsonDeserializer.deserialize(jsonParser, this);
        }
        return jsonDeserializer.deserialize(jsonParser, this, obj);
    }

    protected Object _unwrapAndDeserialize(JsonParser jsonParser, JavaType javaType, JsonDeserializer<Object> jsonDeserializer, Object obj) throws IOException {
        Object objDeserialize;
        String simpleName = this._config.findRootName(javaType).getSimpleName();
        if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
            reportWrongTokenException(javaType, JsonToken.START_OBJECT, "Current token not START_OBJECT (needed to unwrap root name %s), but %s", ClassUtil.name(simpleName), jsonParser.currentToken());
        }
        if (jsonParser.nextToken() != JsonToken.FIELD_NAME) {
            reportWrongTokenException(javaType, JsonToken.FIELD_NAME, "Current token not FIELD_NAME (to contain expected root name %s), but %s", ClassUtil.name(simpleName), jsonParser.currentToken());
        }
        String strCurrentName = jsonParser.currentName();
        if (!simpleName.equals(strCurrentName)) {
            reportPropertyInputMismatch(javaType, strCurrentName, "Root name (%s) does not match expected (%s) for type %s", ClassUtil.name(strCurrentName), ClassUtil.name(simpleName), ClassUtil.getTypeDescription(javaType));
        }
        jsonParser.nextToken();
        if (obj == null) {
            objDeserialize = jsonDeserializer.deserialize(jsonParser, this);
        } else {
            objDeserialize = jsonDeserializer.deserialize(jsonParser, this, obj);
        }
        if (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            reportWrongTokenException(javaType, JsonToken.END_OBJECT, "Current token not END_OBJECT (to match wrapper object with root name %s), but %s", ClassUtil.name(simpleName), jsonParser.currentToken());
        }
        return objDeserialize;
    }

    public static final class Impl extends DefaultDeserializationContext {
        private static final long serialVersionUID = 1;

        public Impl(DeserializerFactory deserializerFactory) {
            super(deserializerFactory, new DeserializerCache());
        }

        private Impl(Impl impl, DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
            super(impl, deserializationConfig, jsonParser, injectableValues);
        }

        private Impl(Impl impl) {
            super(impl);
        }

        private Impl(Impl impl, DeserializerFactory deserializerFactory) {
            super(impl, deserializerFactory);
        }

        private Impl(Impl impl, DeserializationConfig deserializationConfig) {
            super(impl, deserializationConfig);
        }

        private Impl(Impl impl, CacheProvider cacheProvider) {
            super(impl, cacheProvider);
        }

        @Override // com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
        public DefaultDeserializationContext copy() {
            ClassUtil.verifyMustOverride(Impl.class, this, Constants.QueryConstants.COPY);
            return new Impl(this);
        }

        @Override // com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
        public DefaultDeserializationContext createInstance(DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
            return new Impl(this, deserializationConfig, jsonParser, injectableValues);
        }

        @Override // com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
        public DefaultDeserializationContext createDummyInstance(DeserializationConfig deserializationConfig) {
            return new Impl(this, deserializationConfig);
        }

        @Override // com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
        public DefaultDeserializationContext with(DeserializerFactory deserializerFactory) {
            return new Impl(this, deserializerFactory);
        }

        @Override // com.fasterxml.jackson.databind.deser.DefaultDeserializationContext
        public DefaultDeserializationContext withCaches(CacheProvider cacheProvider) {
            return new Impl(this, cacheProvider);
        }
    }
}
