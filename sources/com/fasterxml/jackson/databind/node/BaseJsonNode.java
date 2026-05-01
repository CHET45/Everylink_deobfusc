package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.exc.StreamConstraintsException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.util.ExceptionUtil;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseJsonNode extends JsonNode implements Serializable {
    private static final long serialVersionUID = 1;

    protected ArrayNode _withArray(JsonPointer jsonPointer, JsonPointer jsonPointer2, JsonNode.OverwriteMode overwriteMode, boolean z) {
        return null;
    }

    protected ObjectNode _withObject(JsonPointer jsonPointer, JsonPointer jsonPointer2, JsonNode.OverwriteMode overwriteMode, boolean z) {
        return null;
    }

    public abstract JsonToken asToken();

    public abstract int hashCode();

    @Override // com.fasterxml.jackson.core.TreeNode
    public JsonParser.NumberType numberType() {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public abstract void serialize(JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public abstract void serializeWithType(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException;

    Object writeReplace() {
        return NodeSerialization.from(this);
    }

    protected BaseJsonNode() {
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public final JsonNode findPath(String str) {
        JsonNode jsonNodeFindValue = findValue(str);
        return jsonNodeFindValue == null ? MissingNode.getInstance() : jsonNodeFindValue;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode required(String str) {
        return (JsonNode) _reportRequiredViolation("Node of type `%s` has no fields", getClass().getSimpleName());
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public JsonNode required(int i) {
        return (JsonNode) _reportRequiredViolation("Node of type `%s` has no indexed values", getClass().getSimpleName());
    }

    @Override // com.fasterxml.jackson.core.TreeNode
    public JsonParser traverse() {
        return new TreeTraversingParser(this);
    }

    @Override // com.fasterxml.jackson.core.TreeNode
    public JsonParser traverse(ObjectCodec objectCodec) {
        return new TreeTraversingParser(this, objectCodec);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ObjectNode withObject(JsonPointer jsonPointer, JsonNode.OverwriteMode overwriteMode, boolean z) {
        if (jsonPointer.matches()) {
            if (this instanceof ObjectNode) {
                return (ObjectNode) this;
            }
            _reportWrongNodeType("Can only call `withObject()` with empty JSON Pointer on `ObjectNode`, not `%s`", getClass().getName());
        }
        ObjectNode objectNode_withObject = _withObject(jsonPointer, jsonPointer, overwriteMode, z);
        if (objectNode_withObject == null) {
            _reportWrongNodeType("Cannot replace context node (of type `%s`) using `withObject()` with  JSON Pointer '%s'", getClass().getName(), jsonPointer);
        }
        return objectNode_withObject;
    }

    protected void _withXxxVerifyReplace(JsonPointer jsonPointer, JsonPointer jsonPointer2, JsonNode.OverwriteMode overwriteMode, boolean z, JsonNode jsonNode) {
        if (_withXxxMayReplace(jsonNode, overwriteMode)) {
            return;
        }
        _reportWrongNodeType("Cannot replace `JsonNode` of type `%s` for property \"%s\" in JSON Pointer \"%s\" (mode `OverwriteMode.%s`)", jsonNode.getClass().getName(), jsonPointer2.getMatchingProperty(), jsonPointer, overwriteMode);
    }

    /* JADX INFO: renamed from: com.fasterxml.jackson.databind.node.BaseJsonNode$1 */
    static /* synthetic */ class C14571 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$databind$JsonNode$OverwriteMode;

        static {
            int[] iArr = new int[JsonNode.OverwriteMode.values().length];
            $SwitchMap$com$fasterxml$jackson$databind$JsonNode$OverwriteMode = iArr;
            try {
                iArr[JsonNode.OverwriteMode.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$JsonNode$OverwriteMode[JsonNode.OverwriteMode.NULLS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$JsonNode$OverwriteMode[JsonNode.OverwriteMode.SCALARS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$databind$JsonNode$OverwriteMode[JsonNode.OverwriteMode.ALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    protected boolean _withXxxMayReplace(JsonNode jsonNode, JsonNode.OverwriteMode overwriteMode) {
        int i = C14571.$SwitchMap$com$fasterxml$jackson$databind$JsonNode$OverwriteMode[overwriteMode.ordinal()];
        if (i == 1) {
            return false;
        }
        if (i == 2) {
            return jsonNode.isNull();
        }
        if (i != 3) {
            return true;
        }
        return !jsonNode.isContainerNode();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public ArrayNode withArray(JsonPointer jsonPointer, JsonNode.OverwriteMode overwriteMode, boolean z) {
        if (jsonPointer.matches()) {
            if (this instanceof ArrayNode) {
                return (ArrayNode) this;
            }
            _reportWrongNodeType("Can only call `withArray()` with empty JSON Pointer on `ArrayNode`, not `%s`", getClass().getName());
        }
        ArrayNode arrayNode_withArray = _withArray(jsonPointer, jsonPointer, overwriteMode, z);
        if (arrayNode_withArray == null) {
            _reportWrongNodeType("Cannot replace context node (of type `%s`) using `withArray()` with  JSON Pointer '%s'", getClass().getName(), jsonPointer);
        }
        return arrayNode_withArray;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        return InternalNodeMapper.nodeToString(this);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String toPrettyString() {
        return InternalNodeMapper.nodeToPrettyString(this);
    }

    protected <T> T _reportWrongNodeType(String str, Object... objArr) {
        throw new UnsupportedOperationException(String.format(str, objArr));
    }

    protected <T> T _reportWrongNodeOperation(String str, Object... objArr) {
        throw new UnsupportedOperationException(String.format(str, objArr));
    }

    protected JsonPointer _jsonPointerIfValid(String str) {
        if (str.isEmpty() || str.charAt(0) == '/') {
            return JsonPointer.compile(str);
        }
        return null;
    }

    protected BigInteger _bigIntFromBigDec(BigDecimal bigDecimal) throws Throwable {
        try {
            StreamReadConstraints.defaults().validateBigIntegerScale(bigDecimal.scale());
        } catch (StreamConstraintsException e) {
            ExceptionUtil.throwSneaky(e);
        }
        return bigDecimal.toBigInteger();
    }
}
