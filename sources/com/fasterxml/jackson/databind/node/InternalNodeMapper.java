package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
final class InternalNodeMapper {
    private static final JsonMapper JSON_MAPPER;
    private static final ObjectReader NODE_READER;
    private static final ObjectWriter PRETTY_WRITER;
    private static final ObjectWriter STD_WRITER;

    InternalNodeMapper() {
    }

    static {
        JsonMapper jsonMapper = new JsonMapper();
        JSON_MAPPER = jsonMapper;
        STD_WRITER = jsonMapper.writer();
        PRETTY_WRITER = jsonMapper.writer().withDefaultPrettyPrinter();
        NODE_READER = jsonMapper.readerFor(JsonNode.class);
    }

    public static String nodeToString(BaseJsonNode baseJsonNode) {
        try {
            return STD_WRITER.writeValueAsString(_wrapper(baseJsonNode));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String nodeToPrettyString(BaseJsonNode baseJsonNode) {
        try {
            return PRETTY_WRITER.writeValueAsString(_wrapper(baseJsonNode));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] valueToBytes(Object obj) throws IOException {
        return JSON_MAPPER.writeValueAsBytes(obj);
    }

    public static JsonNode bytesToNode(byte[] bArr) throws IOException {
        return (JsonNode) NODE_READER.readValue(bArr);
    }

    private static JsonSerializable _wrapper(BaseJsonNode baseJsonNode) {
        return new WrapperForSerializer(baseJsonNode);
    }

    protected static class WrapperForSerializer extends JsonSerializable.Base {
        protected SerializerProvider _context;
        protected final BaseJsonNode _root;

        public WrapperForSerializer(BaseJsonNode baseJsonNode) {
            this._root = baseJsonNode;
        }

        @Override // com.fasterxml.jackson.databind.JsonSerializable
        public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            this._context = serializerProvider;
            _serializeNonRecursive(jsonGenerator, this._root);
        }

        @Override // com.fasterxml.jackson.databind.JsonSerializable
        public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
            serialize(jsonGenerator, serializerProvider);
        }

        protected void _serializeNonRecursive(JsonGenerator jsonGenerator, JsonNode jsonNode) throws IOException {
            if (jsonNode instanceof ObjectNode) {
                jsonGenerator.writeStartObject(this, jsonNode.size());
                _serializeNonRecursive(jsonGenerator, new IteratorStack(), jsonNode.fields());
            } else if (jsonNode instanceof ArrayNode) {
                jsonGenerator.writeStartArray(this, jsonNode.size());
                _serializeNonRecursive(jsonGenerator, new IteratorStack(), jsonNode.elements());
            } else {
                jsonNode.serialize(jsonGenerator, this._context);
            }
        }

        protected void _serializeNonRecursive(JsonGenerator jsonGenerator, IteratorStack iteratorStack, Iterator<?> it) throws IOException {
            JsonNode jsonNode;
            while (true) {
                if (it.hasNext()) {
                    Object next = it.next();
                    if (next instanceof Map.Entry) {
                        Map.Entry entry = (Map.Entry) next;
                        jsonGenerator.writeFieldName((String) entry.getKey());
                        jsonNode = (JsonNode) entry.getValue();
                    } else {
                        jsonNode = (JsonNode) next;
                    }
                    if (jsonNode instanceof ObjectNode) {
                        iteratorStack.push(it);
                        it = jsonNode.fields();
                        jsonGenerator.writeStartObject(jsonNode, jsonNode.size());
                    } else if (jsonNode instanceof ArrayNode) {
                        iteratorStack.push(it);
                        it = jsonNode.elements();
                        jsonGenerator.writeStartArray(jsonNode, jsonNode.size());
                    } else if (jsonNode instanceof POJONode) {
                        try {
                            jsonNode.serialize(jsonGenerator, this._context);
                        } catch (IOException | RuntimeException e) {
                            jsonGenerator.writeString(String.format("[ERROR: (%s) %s]", e.getClass().getName(), e.getMessage()));
                        }
                    } else {
                        jsonNode.serialize(jsonGenerator, this._context);
                    }
                } else {
                    if (jsonGenerator.getOutputContext().inArray()) {
                        jsonGenerator.writeEndArray();
                    } else {
                        jsonGenerator.writeEndObject();
                    }
                    it = iteratorStack.popOrNull();
                    if (it == null) {
                        return;
                    }
                }
            }
        }
    }

    static final class IteratorStack {
        private int _end;
        private Iterator<?>[] _stack;
        private int _top;

        public void push(Iterator<?> it) {
            int i = this._top;
            int i2 = this._end;
            if (i < i2) {
                Iterator<?>[] itArr = this._stack;
                this._top = i + 1;
                itArr[i] = it;
                return;
            }
            if (this._stack == null) {
                this._end = 10;
                this._stack = new Iterator[10];
            } else {
                int iMin = i2 + Math.min(4000, Math.max(20, i2 >> 1));
                this._end = iMin;
                this._stack = (Iterator[]) Arrays.copyOf(this._stack, iMin);
            }
            Iterator<?>[] itArr2 = this._stack;
            int i3 = this._top;
            this._top = i3 + 1;
            itArr2[i3] = it;
        }

        public Iterator<?> popOrNull() {
            int i = this._top;
            if (i == 0) {
                return null;
            }
            Iterator<?>[] itArr = this._stack;
            int i2 = i - 1;
            this._top = i2;
            return itArr[i2];
        }
    }
}
