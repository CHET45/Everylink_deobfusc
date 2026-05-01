package org.commonmark.renderer;

import java.util.Set;
import org.commonmark.node.Node;

/* JADX INFO: loaded from: classes4.dex */
public interface NodeRenderer {
    Set<Class<? extends Node>> getNodeTypes();

    void render(Node node);
}
