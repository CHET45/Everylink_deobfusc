package org.commonmark.renderer.html;

import java.util.Map;
import org.commonmark.node.Node;

/* JADX INFO: loaded from: classes4.dex */
public interface AttributeProvider {
    void setAttributes(Node node, String str, Map<String, String> map);
}
