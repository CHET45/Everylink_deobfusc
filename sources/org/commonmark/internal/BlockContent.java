package org.commonmark.internal;

/* JADX INFO: loaded from: classes4.dex */
class BlockContent {
    private int lineCount;

    /* JADX INFO: renamed from: sb */
    private final StringBuilder f2032sb;

    public BlockContent() {
        this.lineCount = 0;
        this.f2032sb = new StringBuilder();
    }

    public BlockContent(String str) {
        this.lineCount = 0;
        this.f2032sb = new StringBuilder(str);
    }

    public void add(CharSequence charSequence) {
        if (this.lineCount != 0) {
            this.f2032sb.append('\n');
        }
        this.f2032sb.append(charSequence);
        this.lineCount++;
    }

    public String getString() {
        return this.f2032sb.toString();
    }
}
