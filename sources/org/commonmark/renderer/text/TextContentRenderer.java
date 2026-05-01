package org.commonmark.renderer.text;

import java.util.ArrayList;
import java.util.List;
import org.commonmark.Extension;
import org.commonmark.internal.renderer.NodeRendererMap;
import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.Renderer;

/* JADX INFO: loaded from: classes4.dex */
public class TextContentRenderer implements Renderer {
    private final List<TextContentNodeRendererFactory> nodeRendererFactories;
    private final boolean stripNewlines;

    public interface TextContentRendererExtension extends Extension {
        void extend(Builder builder);
    }

    private TextContentRenderer(Builder builder) {
        this.stripNewlines = builder.stripNewlines;
        ArrayList arrayList = new ArrayList(builder.nodeRendererFactories.size() + 1);
        this.nodeRendererFactories = arrayList;
        arrayList.addAll(builder.nodeRendererFactories);
        arrayList.add(new TextContentNodeRendererFactory() { // from class: org.commonmark.renderer.text.TextContentRenderer.1
            @Override // org.commonmark.renderer.text.TextContentNodeRendererFactory
            public NodeRenderer create(TextContentNodeRendererContext textContentNodeRendererContext) {
                return new CoreTextContentNodeRenderer(textContentNodeRendererContext);
            }
        });
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override // org.commonmark.renderer.Renderer
    public void render(Node node, Appendable appendable) {
        new RendererContext(new TextContentWriter(appendable)).render(node);
    }

    @Override // org.commonmark.renderer.Renderer
    public String render(Node node) {
        StringBuilder sb = new StringBuilder();
        render(node, sb);
        return sb.toString();
    }

    public static class Builder {
        private boolean stripNewlines = false;
        private List<TextContentNodeRendererFactory> nodeRendererFactories = new ArrayList();

        public TextContentRenderer build() {
            return new TextContentRenderer(this);
        }

        public Builder stripNewlines(boolean z) {
            this.stripNewlines = z;
            return this;
        }

        public Builder nodeRendererFactory(TextContentNodeRendererFactory textContentNodeRendererFactory) {
            this.nodeRendererFactories.add(textContentNodeRendererFactory);
            return this;
        }

        public Builder extensions(Iterable<? extends Extension> iterable) {
            for (Extension extension : iterable) {
                if (extension instanceof TextContentRendererExtension) {
                    ((TextContentRendererExtension) extension).extend(this);
                }
            }
            return this;
        }
    }

    private class RendererContext implements TextContentNodeRendererContext {
        private final NodeRendererMap nodeRendererMap;
        private final TextContentWriter textContentWriter;

        private RendererContext(TextContentWriter textContentWriter) {
            this.nodeRendererMap = new NodeRendererMap();
            this.textContentWriter = textContentWriter;
            for (int size = TextContentRenderer.this.nodeRendererFactories.size() - 1; size >= 0; size--) {
                this.nodeRendererMap.add(((TextContentNodeRendererFactory) TextContentRenderer.this.nodeRendererFactories.get(size)).create(this));
            }
        }

        @Override // org.commonmark.renderer.text.TextContentNodeRendererContext
        public boolean stripNewlines() {
            return TextContentRenderer.this.stripNewlines;
        }

        @Override // org.commonmark.renderer.text.TextContentNodeRendererContext
        public TextContentWriter getWriter() {
            return this.textContentWriter;
        }

        @Override // org.commonmark.renderer.text.TextContentNodeRendererContext
        public void render(Node node) {
            this.nodeRendererMap.render(node);
        }
    }
}
