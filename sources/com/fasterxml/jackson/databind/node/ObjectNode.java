package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.JsonNodeFeature;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.RawValue;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes3.dex */
public class ObjectNode extends ContainerNode<ObjectNode> implements Serializable {
    private static final long serialVersionUID = 1;
    protected final Map<String, JsonNode> _children;

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode get(int i) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public final boolean isObject() {
        return true;
    }

    public ObjectNode(JsonNodeFactory jsonNodeFactory) {
        super(jsonNodeFactory);
        this._children = new LinkedHashMap();
    }

    public ObjectNode(JsonNodeFactory jsonNodeFactory, Map<String, JsonNode> map) {
        super(jsonNodeFactory);
        this._children = (Map) Objects.requireNonNull(map, "Must not pass `null` for 'children' argument");
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    protected JsonNode _at(JsonPointer jsonPointer) {
        return get(jsonPointer.getMatchingProperty());
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode deepCopy() {
        ObjectNode objectNode = objectNode();
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            objectNode._children.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return objectNode;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    @Deprecated
    public ObjectNode with(String str) {
        JsonPointer jsonPointer_jsonPointerIfValid = _jsonPointerIfValid(str);
        if (jsonPointer_jsonPointerIfValid != null) {
            return withObject(jsonPointer_jsonPointerIfValid);
        }
        JsonNode jsonNode = this._children.get(str);
        if (jsonNode != null) {
            if (jsonNode instanceof ObjectNode) {
                return (ObjectNode) jsonNode;
            }
            throw new UnsupportedOperationException("Property '" + str + "' has value that is not of type `ObjectNode` (but `" + jsonNode.getClass().getName() + "`)");
        }
        ObjectNode objectNode = objectNode();
        this._children.put(str, objectNode);
        return objectNode;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode withObject(String str) {
        JsonPointer jsonPointer_jsonPointerIfValid = _jsonPointerIfValid(str);
        if (jsonPointer_jsonPointerIfValid != null) {
            return withObject(jsonPointer_jsonPointerIfValid);
        }
        return withObjectProperty(str);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode withObjectProperty(String str) {
        JsonNode jsonNode = this._children.get(str);
        if (jsonNode == null || jsonNode.isNull()) {
            return putObject(str);
        }
        if (jsonNode.isObject()) {
            return (ObjectNode) jsonNode;
        }
        return (ObjectNode) _reportWrongNodeType("Cannot replace `JsonNode` of type `%s` with `ObjectNode` for property \"%s\" (default mode `OverwriteMode.%s`)", jsonNode.getClass().getName(), str, JsonNode.OverwriteMode.NULLS);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ArrayNode withArray(String str) {
        JsonPointer jsonPointer_jsonPointerIfValid = _jsonPointerIfValid(str);
        if (jsonPointer_jsonPointerIfValid != null) {
            return withArray(jsonPointer_jsonPointerIfValid);
        }
        JsonNode jsonNode = this._children.get(str);
        if (jsonNode != null) {
            if (jsonNode instanceof ArrayNode) {
                return (ArrayNode) jsonNode;
            }
            throw new UnsupportedOperationException("Property '" + str + "' has value that is not of type `ArrayNode` (but `" + jsonNode.getClass().getName() + "`)");
        }
        ArrayNode arrayNode = arrayNode();
        this._children.put(str, arrayNode);
        return arrayNode;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ArrayNode withArrayProperty(String str) {
        JsonNode jsonNode = this._children.get(str);
        if (jsonNode == null || jsonNode.isNull()) {
            return putArray(str);
        }
        if (jsonNode.isArray()) {
            return (ArrayNode) jsonNode;
        }
        return (ArrayNode) _reportWrongNodeType("Cannot replace `JsonNode` of type `%s` with `ArrayNode` for property \"%s\" with (default mode `OverwriteMode.%s`)", jsonNode.getClass().getName(), str, JsonNode.OverwriteMode.NULLS);
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode
    protected ObjectNode _withObject(JsonPointer jsonPointer, JsonPointer jsonPointer2, JsonNode.OverwriteMode overwriteMode, boolean z) {
        if (jsonPointer2.matches()) {
            return this;
        }
        JsonNode jsonNode_at = _at(jsonPointer2);
        if (jsonNode_at != null && (jsonNode_at instanceof BaseJsonNode)) {
            ObjectNode objectNode_withObject = ((BaseJsonNode) jsonNode_at)._withObject(jsonPointer, jsonPointer2.tail(), overwriteMode, z);
            if (objectNode_withObject != null) {
                return objectNode_withObject;
            }
            _withXxxVerifyReplace(jsonPointer, jsonPointer2, overwriteMode, z, jsonNode_at);
        }
        return _withObjectAddTailProperty(jsonPointer2, z);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode
    protected ArrayNode _withArray(JsonPointer jsonPointer, JsonPointer jsonPointer2, JsonNode.OverwriteMode overwriteMode, boolean z) {
        if (jsonPointer2.matches()) {
            return null;
        }
        JsonNode jsonNode_at = _at(jsonPointer2);
        if (jsonNode_at != null && (jsonNode_at instanceof BaseJsonNode)) {
            ArrayNode arrayNode_withArray = ((BaseJsonNode) jsonNode_at)._withArray(jsonPointer, jsonPointer2.tail(), overwriteMode, z);
            if (arrayNode_withArray != null) {
                return arrayNode_withArray;
            }
            _withXxxVerifyReplace(jsonPointer, jsonPointer2, overwriteMode, z, jsonNode_at);
        }
        return _withArrayAddTailProperty(jsonPointer2, z);
    }

    protected ObjectNode _withObjectAddTailProperty(JsonPointer jsonPointer, boolean z) {
        String matchingProperty = jsonPointer.getMatchingProperty();
        JsonPointer jsonPointerTail = jsonPointer.tail();
        if (jsonPointerTail.matches()) {
            return putObject(matchingProperty);
        }
        if (z && jsonPointerTail.mayMatchElement()) {
            return putArray(matchingProperty)._withObjectAddTailElement(jsonPointerTail, z);
        }
        return putObject(matchingProperty)._withObjectAddTailProperty(jsonPointerTail, z);
    }

    protected ArrayNode _withArrayAddTailProperty(JsonPointer jsonPointer, boolean z) {
        String matchingProperty = jsonPointer.getMatchingProperty();
        JsonPointer jsonPointerTail = jsonPointer.tail();
        if (jsonPointerTail.matches()) {
            return putArray(matchingProperty);
        }
        if (z && jsonPointerTail.mayMatchElement()) {
            return putArray(matchingProperty)._withArrayAddTailElement(jsonPointerTail, z);
        }
        return putObject(matchingProperty)._withArrayAddTailProperty(jsonPointerTail, z);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable.Base
    public boolean isEmpty(SerializerProvider serializerProvider) {
        return this._children.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNodeType getNodeType() {
        return JsonNodeType.OBJECT;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.START_OBJECT;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public int size() {
        return this._children.size();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isEmpty() {
        return this._children.isEmpty();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<JsonNode> elements() {
        return this._children.values().iterator();
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode get(String str) {
        return this._children.get(str);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public Iterator<String> fieldNames() {
        return this._children.keySet().iterator();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode path(int i) {
        return MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonNode path(String str) {
        JsonNode jsonNode = this._children.get(str);
        return jsonNode != null ? jsonNode : MissingNode.getInstance();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode
    public JsonNode required(String str) {
        JsonNode jsonNode = this._children.get(str);
        return jsonNode != null ? jsonNode : (JsonNode) _reportRequiredViolation("No value for property '%s' of `ObjectNode`", str);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Iterator<Map.Entry<String, JsonNode>> fields() {
        return this._children.entrySet().iterator();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public Set<Map.Entry<String, JsonNode>> properties() {
        return this._children.entrySet();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Comparator<JsonNode> comparator, JsonNode jsonNode) {
        if (!(jsonNode instanceof ObjectNode)) {
            return false;
        }
        Map<String, JsonNode> map = this._children;
        Map<String, JsonNode> map2 = ((ObjectNode) jsonNode)._children;
        if (map2.size() != map.size()) {
            return false;
        }
        for (Map.Entry<String, JsonNode> entry : map.entrySet()) {
            JsonNode jsonNode2 = map2.get(entry.getKey());
            if (jsonNode2 == null || !entry.getValue().equals(comparator, jsonNode2)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode findValue(String str) {
        JsonNode jsonNode = this._children.get(str);
        if (jsonNode != null) {
            return jsonNode;
        }
        Iterator<JsonNode> it = this._children.values().iterator();
        while (it.hasNext()) {
            JsonNode jsonNodeFindValue = it.next().findValue(str);
            if (jsonNodeFindValue != null) {
                return jsonNodeFindValue;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findValues(String str, List<JsonNode> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(entry.getValue());
            } else {
                list = entry.getValue().findValues(str, list);
            }
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<String> findValuesAsText(String str, List<String> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(entry.getValue().asText());
            } else {
                list = entry.getValue().findValuesAsText(str, list);
            }
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode findParent(String str) {
        if (this._children.get(str) != null) {
            return this;
        }
        Iterator<JsonNode> it = this._children.values().iterator();
        while (it.hasNext()) {
            JsonNode jsonNodeFindParent = it.next().findParent(str);
            if (jsonNodeFindParent != null) {
                return (ObjectNode) jsonNodeFindParent;
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public List<JsonNode> findParents(String str, List<JsonNode> list) {
        for (Map.Entry<String, JsonNode> entry : this._children.entrySet()) {
            if (str.equals(entry.getKey())) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(this);
            } else {
                list = entry.getValue().findParents(str, list);
            }
        }
        return list;
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (serializerProvider != null) {
            boolean zIsEnabled = serializerProvider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
            boolean z = !zIsEnabled;
            boolean zIsEnabled2 = serializerProvider.isEnabled(JsonNodeFeature.WRITE_NULL_PROPERTIES);
            boolean z2 = !zIsEnabled2;
            if (!zIsEnabled || !zIsEnabled2) {
                jsonGenerator.writeStartObject(this);
                serializeFilteredContents(jsonGenerator, serializerProvider, z, z2);
                jsonGenerator.writeEndObject();
                return;
            }
        }
        jsonGenerator.writeStartObject(this);
        for (Map.Entry<String, JsonNode> entry : _contentsToSerialize(serializerProvider).entrySet()) {
            JsonNode value = entry.getValue();
            jsonGenerator.writeFieldName(entry.getKey());
            value.serialize(jsonGenerator, serializerProvider);
        }
        jsonGenerator.writeEndObject();
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {
        boolean z;
        boolean z2;
        if (serializerProvider != null) {
            z = !serializerProvider.isEnabled(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
            z2 = !serializerProvider.isEnabled(JsonNodeFeature.WRITE_NULL_PROPERTIES);
        } else {
            z = false;
            z2 = false;
        }
        WritableTypeId writableTypeIdWriteTypePrefix = typeSerializer.writeTypePrefix(jsonGenerator, typeSerializer.typeId(this, JsonToken.START_OBJECT));
        if (z || z2) {
            serializeFilteredContents(jsonGenerator, serializerProvider, z, z2);
        } else {
            for (Map.Entry<String, JsonNode> entry : _contentsToSerialize(serializerProvider).entrySet()) {
                JsonNode value = entry.getValue();
                jsonGenerator.writeFieldName(entry.getKey());
                value.serialize(jsonGenerator, serializerProvider);
            }
        }
        typeSerializer.writeTypeSuffix(jsonGenerator, writableTypeIdWriteTypePrefix);
    }

    protected void serializeFilteredContents(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, boolean z, boolean z2) throws IOException {
        for (Map.Entry<String, JsonNode> entry : _contentsToSerialize(serializerProvider).entrySet()) {
            JsonNode value = entry.getValue();
            if (!z || !value.isArray() || !value.isEmpty(serializerProvider)) {
                if (!z2 || !value.isNull()) {
                    jsonGenerator.writeFieldName(entry.getKey());
                    value.serialize(jsonGenerator, serializerProvider);
                }
            }
        }
    }

    protected Map<String, JsonNode> _contentsToSerialize(SerializerProvider serializerProvider) {
        if (serializerProvider.isEnabled(JsonNodeFeature.WRITE_PROPERTIES_SORTED) && !this._children.isEmpty() && !(this._children instanceof TreeMap)) {
            return new TreeMap(this._children);
        }
        return this._children;
    }

    public <T extends JsonNode> T set(String str, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        this._children.put(str, jsonNode);
        return this;
    }

    public <T extends JsonNode> T setAll(Map<String, ? extends JsonNode> map) {
        for (Map.Entry<String, ? extends JsonNode> entry : map.entrySet()) {
            JsonNode value = entry.getValue();
            if (value == null) {
                value = nullNode();
            }
            this._children.put(entry.getKey(), value);
        }
        return this;
    }

    public <T extends JsonNode> T setAll(ObjectNode objectNode) {
        this._children.putAll(objectNode._children);
        return this;
    }

    public JsonNode replace(String str, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        return this._children.put(str, jsonNode);
    }

    public <T extends JsonNode> T without(String str) {
        this._children.remove(str);
        return this;
    }

    public <T extends JsonNode> T without(Collection<String> collection) {
        this._children.keySet().removeAll(collection);
        return this;
    }

    @Deprecated
    public JsonNode put(String str, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        return this._children.put(str, jsonNode);
    }

    public JsonNode putIfAbsent(String str, JsonNode jsonNode) {
        if (jsonNode == null) {
            jsonNode = nullNode();
        }
        return this._children.putIfAbsent(str, jsonNode);
    }

    public JsonNode remove(String str) {
        return this._children.remove(str);
    }

    public ObjectNode remove(Collection<String> collection) {
        this._children.keySet().removeAll(collection);
        return this;
    }

    @Override // com.fasterxml.jackson.databind.node.ContainerNode
    public ObjectNode removeAll() {
        this._children.clear();
        return this;
    }

    @Deprecated
    public JsonNode putAll(Map<String, ? extends JsonNode> map) {
        return setAll(map);
    }

    @Deprecated
    public JsonNode putAll(ObjectNode objectNode) {
        return setAll(objectNode);
    }

    public ObjectNode retain(Collection<String> collection) {
        this._children.keySet().retainAll(collection);
        return this;
    }

    public ObjectNode retain(String... strArr) {
        return retain(Arrays.asList(strArr));
    }

    public ArrayNode putArray(String str) {
        ArrayNode arrayNode = arrayNode();
        _put(str, arrayNode);
        return arrayNode;
    }

    public ObjectNode putObject(String str) {
        ObjectNode objectNode = objectNode();
        _put(str, objectNode);
        return objectNode;
    }

    public ObjectNode putPOJO(String str, Object obj) {
        return _put(str, pojoNode(obj));
    }

    public ObjectNode putRawValue(String str, RawValue rawValue) {
        return _put(str, rawValueNode(rawValue));
    }

    public ObjectNode putNull(String str) {
        this._children.put(str, nullNode());
        return this;
    }

    public ObjectNode put(String str, short s) {
        return _put(str, numberNode(s));
    }

    public ObjectNode put(String str, Short sh) {
        JsonNode jsonNodeNumberNode;
        if (sh == null) {
            jsonNodeNumberNode = nullNode();
        } else {
            jsonNodeNumberNode = numberNode(sh.shortValue());
        }
        return _put(str, jsonNodeNumberNode);
    }

    public ObjectNode put(String str, int i) {
        return _put(str, numberNode(i));
    }

    public ObjectNode put(String str, Integer num) {
        JsonNode jsonNodeNumberNode;
        if (num == null) {
            jsonNodeNumberNode = nullNode();
        } else {
            jsonNodeNumberNode = numberNode(num.intValue());
        }
        return _put(str, jsonNodeNumberNode);
    }

    public ObjectNode put(String str, long j) {
        return _put(str, numberNode(j));
    }

    public ObjectNode put(String str, Long l) {
        JsonNode jsonNodeNumberNode;
        if (l == null) {
            jsonNodeNumberNode = nullNode();
        } else {
            jsonNodeNumberNode = numberNode(l.longValue());
        }
        return _put(str, jsonNodeNumberNode);
    }

    public ObjectNode put(String str, float f) {
        return _put(str, numberNode(f));
    }

    public ObjectNode put(String str, Float f) {
        JsonNode jsonNodeNumberNode;
        if (f == null) {
            jsonNodeNumberNode = nullNode();
        } else {
            jsonNodeNumberNode = numberNode(f.floatValue());
        }
        return _put(str, jsonNodeNumberNode);
    }

    public ObjectNode put(String str, double d) {
        return _put(str, numberNode(d));
    }

    public ObjectNode put(String str, Double d) {
        JsonNode jsonNodeNumberNode;
        if (d == null) {
            jsonNodeNumberNode = nullNode();
        } else {
            jsonNodeNumberNode = numberNode(d.doubleValue());
        }
        return _put(str, jsonNodeNumberNode);
    }

    public ObjectNode put(String str, BigDecimal bigDecimal) {
        ValueNode valueNodeNumberNode;
        if (bigDecimal == null) {
            valueNodeNumberNode = nullNode();
        } else {
            valueNodeNumberNode = numberNode(bigDecimal);
        }
        return _put(str, valueNodeNumberNode);
    }

    public ObjectNode put(String str, BigInteger bigInteger) {
        ValueNode valueNodeNumberNode;
        if (bigInteger == null) {
            valueNodeNumberNode = nullNode();
        } else {
            valueNodeNumberNode = numberNode(bigInteger);
        }
        return _put(str, valueNodeNumberNode);
    }

    public ObjectNode put(String str, String str2) {
        JsonNode jsonNodeTextNode;
        if (str2 == null) {
            jsonNodeTextNode = nullNode();
        } else {
            jsonNodeTextNode = textNode(str2);
        }
        return _put(str, jsonNodeTextNode);
    }

    public ObjectNode put(String str, boolean z) {
        return _put(str, booleanNode(z));
    }

    public ObjectNode put(String str, Boolean bool) {
        JsonNode jsonNodeBooleanNode;
        if (bool == null) {
            jsonNodeBooleanNode = nullNode();
        } else {
            jsonNodeBooleanNode = booleanNode(bool.booleanValue());
        }
        return _put(str, jsonNodeBooleanNode);
    }

    public ObjectNode put(String str, byte[] bArr) {
        JsonNode jsonNodeBinaryNode;
        if (bArr == null) {
            jsonNodeBinaryNode = nullNode();
        } else {
            jsonNodeBinaryNode = binaryNode(bArr);
        }
        return _put(str, jsonNodeBinaryNode);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && (obj instanceof ObjectNode)) {
            return _childrenEqual((ObjectNode) obj);
        }
        return false;
    }

    protected boolean _childrenEqual(ObjectNode objectNode) {
        return this._children.equals(objectNode._children);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode
    public int hashCode() {
        return this._children.hashCode();
    }

    protected ObjectNode _put(String str, JsonNode jsonNode) {
        this._children.put(str, jsonNode);
        return this;
    }
}
