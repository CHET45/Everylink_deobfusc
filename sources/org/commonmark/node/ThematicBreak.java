package org.commonmark.node;

/* JADX INFO: loaded from: classes4.dex */
public class ThematicBreak extends Block {
    @Override // org.commonmark.node.Node
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
