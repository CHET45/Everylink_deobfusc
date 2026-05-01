package org.commonmark.internal.renderer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;

/* JADX INFO: loaded from: classes4.dex */
public class NodeRendererMap {
    private final Map<Class<? extends Node>, NodeRenderer> renderers = new HashMap(32);

    public void add(NodeRenderer nodeRenderer) {
        Iterator<Class<? extends Node>> it = nodeRenderer.getNodeTypes().iterator();
        while (it.hasNext()) {
            this.renderers.put(it.next(), nodeRenderer);
        }
    }

    public void render(Node node) {
        NodeRenderer nodeRenderer = this.renderers.get(node.getClass());
        if (nodeRenderer != null) {
            nodeRenderer.render(node);
        }
    }
}
