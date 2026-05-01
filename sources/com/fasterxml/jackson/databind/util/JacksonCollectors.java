package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JacksonCollectors {
    public static Collector<JsonNode, ArrayNode, ArrayNode> toArrayNode() {
        return toArrayNode(JsonNodeFactory.instance);
    }

    public static Collector<JsonNode, ArrayNode, ArrayNode> toArrayNode(final JsonNodeCreator jsonNodeCreator) {
        jsonNodeCreator.getClass();
        return Collector.of(new Supplier() { // from class: com.fasterxml.jackson.databind.util.JacksonCollectors$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return jsonNodeCreator.arrayNode();
            }
        }, new BiConsumer() { // from class: com.fasterxml.jackson.databind.util.JacksonCollectors$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((ArrayNode) obj).add((JsonNode) obj2);
            }
        }, new BinaryOperator() { // from class: com.fasterxml.jackson.databind.util.JacksonCollectors$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((ArrayNode) obj).addAll((ArrayNode) obj2);
            }
        }, new Collector.Characteristics[0]);
    }
}
