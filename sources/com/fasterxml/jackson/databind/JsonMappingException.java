package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class JsonMappingException extends DatabindException {
    static final int MAX_REFS_TO_LIST = 1000;
    private static final long serialVersionUID = 3;
    protected LinkedList<Reference> _path;
    protected transient Closeable _processor;

    public static class Reference implements Serializable {
        private static final long serialVersionUID = 2;
        protected String _desc;
        protected String _fieldName;
        protected transient Object _from;
        protected int _index;

        protected Reference() {
            this._index = -1;
        }

        public Reference(Object obj) {
            this._index = -1;
            this._from = obj;
        }

        public Reference(Object obj, String str) {
            this._index = -1;
            this._from = obj;
            if (str == null) {
                throw new NullPointerException("Cannot pass null fieldName");
            }
            this._fieldName = str;
        }

        public Reference(Object obj, int i) {
            this._from = obj;
            this._index = i;
        }

        void setFieldName(String str) {
            this._fieldName = str;
        }

        void setIndex(int i) {
            this._index = i;
        }

        void setDescription(String str) {
            this._desc = str;
        }

        @JsonIgnore
        public Object getFrom() {
            return this._from;
        }

        public String getFieldName() {
            return this._fieldName;
        }

        public int getIndex() {
            return this._index;
        }

        public String getDescription() {
            if (this._desc == null) {
                StringBuilder sb = new StringBuilder();
                Object obj = this._from;
                if (obj == null) {
                    sb.append("UNKNOWN");
                } else {
                    Class<?> componentType = obj instanceof Class ? (Class) obj : obj.getClass();
                    int i = 0;
                    while (componentType.isArray()) {
                        componentType = componentType.getComponentType();
                        i++;
                    }
                    sb.append(componentType.getName());
                    while (true) {
                        i--;
                        if (i < 0) {
                            break;
                        }
                        sb.append("[]");
                    }
                }
                sb.append('[');
                if (this._fieldName != null) {
                    sb.append('\"');
                    sb.append(this._fieldName);
                    sb.append('\"');
                } else {
                    int i2 = this._index;
                    if (i2 >= 0) {
                        sb.append(i2);
                    } else {
                        sb.append('?');
                    }
                }
                sb.append(']');
                this._desc = sb.toString();
            }
            return this._desc;
        }

        public String toString() {
            return getDescription();
        }

        Object writeReplace() {
            getDescription();
            return this;
        }
    }

    @Deprecated
    public JsonMappingException(String str) {
        super(str);
    }

    @Deprecated
    public JsonMappingException(String str, Throwable th) {
        super(str, th);
    }

    @Deprecated
    public JsonMappingException(String str, JsonLocation jsonLocation) {
        super(str, jsonLocation);
    }

    @Deprecated
    public JsonMappingException(String str, JsonLocation jsonLocation, Throwable th) {
        super(str, jsonLocation, th);
    }

    public JsonMappingException(Closeable closeable, String str) {
        super(str);
        this._processor = closeable;
        if (closeable instanceof JsonParser) {
            this._location = ((JsonParser) closeable).currentTokenLocation();
        }
    }

    public JsonMappingException(Closeable closeable, String str, Throwable th) {
        super(str, th);
        this._processor = closeable;
        if (th instanceof JacksonException) {
            this._location = ((JacksonException) th).getLocation();
        } else if (closeable instanceof JsonParser) {
            this._location = ((JsonParser) closeable).currentTokenLocation();
        }
    }

    public JsonMappingException(Closeable closeable, String str, JsonLocation jsonLocation) {
        super(str, jsonLocation);
        this._processor = closeable;
    }

    public static JsonMappingException from(JsonParser jsonParser, String str) {
        return new JsonMappingException(jsonParser, str);
    }

    public static JsonMappingException from(JsonParser jsonParser, String str, Throwable th) {
        return new JsonMappingException(jsonParser, str, th);
    }

    public static JsonMappingException from(JsonGenerator jsonGenerator, String str) {
        return new JsonMappingException(jsonGenerator, str, (Throwable) null);
    }

    public static JsonMappingException from(JsonGenerator jsonGenerator, String str, Throwable th) {
        return new JsonMappingException(jsonGenerator, str, th);
    }

    public static JsonMappingException from(DeserializationContext deserializationContext, String str) {
        return new JsonMappingException(_parser(deserializationContext), str);
    }

    public static JsonMappingException from(DeserializationContext deserializationContext, String str, Throwable th) {
        return new JsonMappingException(_parser(deserializationContext), str, th);
    }

    private static JsonParser _parser(DeserializationContext deserializationContext) {
        if (deserializationContext == null) {
            return null;
        }
        return deserializationContext.getParser();
    }

    public static JsonMappingException from(SerializerProvider serializerProvider, String str) {
        return new JsonMappingException(_generator(serializerProvider), str);
    }

    public static JsonMappingException from(SerializerProvider serializerProvider, String str, Throwable th) {
        return new JsonMappingException(_generator(serializerProvider), str, th);
    }

    private static JsonGenerator _generator(SerializerProvider serializerProvider) {
        if (serializerProvider == null) {
            return null;
        }
        return serializerProvider.getGenerator();
    }

    public static JsonMappingException fromUnexpectedIOE(IOException iOException) {
        return new JsonMappingException((Closeable) null, String.format("Unexpected IOException (of type %s): %s", iOException.getClass().getName(), ClassUtil.exceptionMessage(iOException)));
    }

    public static JsonMappingException wrapWithPath(Throwable th, Object obj, String str) {
        return wrapWithPath(th, new Reference(obj, str));
    }

    public static JsonMappingException wrapWithPath(Throwable th, Object obj, int i) {
        return wrapWithPath(th, new Reference(obj, i));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.fasterxml.jackson.databind.JsonMappingException wrapWithPath(java.lang.Throwable r3, com.fasterxml.jackson.databind.JsonMappingException.Reference r4) {
        /*
            boolean r0 = r3 instanceof com.fasterxml.jackson.databind.JsonMappingException
            if (r0 == 0) goto L7
            com.fasterxml.jackson.databind.JsonMappingException r3 = (com.fasterxml.jackson.databind.JsonMappingException) r3
            goto L49
        L7:
            java.lang.String r0 = com.fasterxml.jackson.databind.util.ClassUtil.exceptionMessage(r3)
            if (r0 == 0) goto L13
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L30
        L13:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "(was "
            r0.<init>(r1)
            java.lang.Class r1 = r3.getClass()
            java.lang.String r1 = r1.getName()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = ")"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
        L30:
            boolean r1 = r3 instanceof com.fasterxml.jackson.core.JacksonException
            if (r1 == 0) goto L42
            r1 = r3
            com.fasterxml.jackson.core.JacksonException r1 = (com.fasterxml.jackson.core.JacksonException) r1
            java.lang.Object r1 = r1.getProcessor()
            boolean r2 = r1 instanceof java.io.Closeable
            if (r2 == 0) goto L42
            java.io.Closeable r1 = (java.io.Closeable) r1
            goto L43
        L42:
            r1 = 0
        L43:
            com.fasterxml.jackson.databind.JsonMappingException r2 = new com.fasterxml.jackson.databind.JsonMappingException
            r2.<init>(r1, r0, r3)
            r3 = r2
        L49:
            r3.prependPath(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.JsonMappingException.wrapWithPath(java.lang.Throwable, com.fasterxml.jackson.databind.JsonMappingException$Reference):com.fasterxml.jackson.databind.JsonMappingException");
    }

    public JsonMappingException withCause(Throwable th) {
        initCause(th);
        return this;
    }

    public List<Reference> getPath() {
        LinkedList<Reference> linkedList = this._path;
        if (linkedList == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(linkedList);
    }

    public String getPathReference() {
        return getPathReference(new StringBuilder()).toString();
    }

    public StringBuilder getPathReference(StringBuilder sb) {
        _appendPathDesc(sb);
        return sb;
    }

    @Override // com.fasterxml.jackson.databind.DatabindException
    public void prependPath(Object obj, String str) {
        prependPath(new Reference(obj, str));
    }

    @Override // com.fasterxml.jackson.databind.DatabindException
    public void prependPath(Object obj, int i) {
        prependPath(new Reference(obj, i));
    }

    public void prependPath(Reference reference) {
        if (this._path == null) {
            this._path = new LinkedList<>();
        }
        if (this._path.size() < 1000) {
            this._path.addFirst(reference);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonProcessingException, com.fasterxml.jackson.core.JacksonException
    @JsonIgnore
    public Object getProcessor() {
        return this._processor;
    }

    @Override // java.lang.Throwable
    public String getLocalizedMessage() {
        return _buildMessage();
    }

    @Override // com.fasterxml.jackson.core.JsonProcessingException, java.lang.Throwable
    public String getMessage() {
        return _buildMessage();
    }

    protected String _buildMessage() {
        String message = super.getMessage();
        if (this._path == null) {
            return message;
        }
        return _appendReferenceChain(message == null ? new StringBuilder() : new StringBuilder(message)).toString();
    }

    @Override // com.fasterxml.jackson.core.JsonProcessingException, java.lang.Throwable
    public String toString() {
        return getClass().getName() + ": " + getMessage();
    }

    protected StringBuilder _appendReferenceChain(StringBuilder sb) {
        sb.append(" (through reference chain: ");
        StringBuilder pathReference = getPathReference(sb);
        pathReference.append(')');
        return pathReference;
    }

    protected void _appendPathDesc(StringBuilder sb) {
        LinkedList<Reference> linkedList = this._path;
        if (linkedList == null) {
            return;
        }
        Iterator<Reference> it = linkedList.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) {
                sb.append("->");
            }
        }
    }
}
