package org.commonmark.node;

/* JADX INFO: loaded from: classes4.dex */
public class Code extends Node {
    private String literal;

    public Code() {
    }

    public Code(String str) {
        this.literal = str;
    }

    @Override // org.commonmark.node.Node
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getLiteral() {
        return this.literal;
    }

    public void setLiteral(String str) {
        this.literal = str;
    }
}
