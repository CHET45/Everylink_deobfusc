package org.commonmark.renderer.html;

import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.commonmark.internal.util.Escaping;

/* JADX INFO: loaded from: classes4.dex */
public class HtmlWriter {
    private static final Map<String, String> NO_ATTRIBUTES = Collections.emptyMap();
    private final Appendable buffer;
    private char lastChar = 0;

    public HtmlWriter(Appendable appendable) {
        if (appendable == null) {
            throw new NullPointerException("out must not be null");
        }
        this.buffer = appendable;
    }

    public void raw(String str) {
        append(str);
    }

    public void text(String str) {
        append(Escaping.escapeHtml(str));
    }

    public void tag(String str) {
        tag(str, NO_ATTRIBUTES);
    }

    public void tag(String str, Map<String, String> map) {
        tag(str, map, false);
    }

    public void tag(String str, Map<String, String> map, boolean z) {
        append("<");
        append(str);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                append(" ");
                append(Escaping.escapeHtml(entry.getKey()));
                append("=\"");
                append(Escaping.escapeHtml(entry.getValue()));
                append(PunctuationConst.DOUBLE_QUOTES);
            }
        }
        if (z) {
            append(" /");
        }
        append(">");
    }

    public void line() {
        char c = this.lastChar;
        if (c == 0 || c == '\n') {
            return;
        }
        append("\n");
    }

    protected void append(String str) {
        try {
            this.buffer.append(str);
            int length = str.length();
            if (length != 0) {
                this.lastChar = str.charAt(length - 1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
