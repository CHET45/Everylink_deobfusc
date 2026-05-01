package org.commonmark.renderer.text;

import com.github.houbb.heaven.constant.CharConst;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.commonmark.internal.renderer.text.BulletListHolder;
import org.commonmark.internal.renderer.text.ListHolder;
import org.commonmark.internal.renderer.text.OrderedListHolder;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BlockQuote;
import org.commonmark.node.BulletList;
import org.commonmark.node.Code;
import org.commonmark.node.Document;
import org.commonmark.node.Emphasis;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.HardLineBreak;
import org.commonmark.node.Heading;
import org.commonmark.node.HtmlBlock;
import org.commonmark.node.HtmlInline;
import org.commonmark.node.Image;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.node.SoftLineBreak;
import org.commonmark.node.StrongEmphasis;
import org.commonmark.node.Text;
import org.commonmark.node.ThematicBreak;
import org.commonmark.renderer.NodeRenderer;

/* JADX INFO: loaded from: classes4.dex */
public class CoreTextContentNodeRenderer extends AbstractVisitor implements NodeRenderer {
    protected final TextContentNodeRendererContext context;
    private ListHolder listHolder;
    private final TextContentWriter textContent;

    public CoreTextContentNodeRenderer(TextContentNodeRendererContext textContentNodeRendererContext) {
        this.context = textContentNodeRendererContext;
        this.textContent = textContentNodeRendererContext.getWriter();
    }

    @Override // org.commonmark.renderer.NodeRenderer
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet(Arrays.asList(Document.class, Heading.class, Paragraph.class, BlockQuote.class, BulletList.class, FencedCodeBlock.class, HtmlBlock.class, ThematicBreak.class, IndentedCodeBlock.class, Link.class, ListItem.class, OrderedList.class, Image.class, Emphasis.class, StrongEmphasis.class, Text.class, Code.class, HtmlInline.class, SoftLineBreak.class, HardLineBreak.class));
    }

    @Override // org.commonmark.renderer.NodeRenderer
    public void render(Node node) {
        node.accept(this);
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(Document document) {
        visitChildren(document);
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(BlockQuote blockQuote) {
        this.textContent.write((char) 171);
        visitChildren(blockQuote);
        this.textContent.write((char) 187);
        writeEndOfLineIfNeeded(blockQuote, null);
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(BulletList bulletList) {
        if (this.listHolder != null) {
            writeEndOfLine();
        }
        this.listHolder = new BulletListHolder(this.listHolder, bulletList);
        visitChildren(bulletList);
        writeEndOfLineIfNeeded(bulletList, null);
        if (this.listHolder.getParent() != null) {
            this.listHolder = this.listHolder.getParent();
        } else {
            this.listHolder = null;
        }
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(Code code) {
        this.textContent.write('\"');
        this.textContent.write(code.getLiteral());
        this.textContent.write('\"');
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(FencedCodeBlock fencedCodeBlock) {
        if (this.context.stripNewlines()) {
            this.textContent.writeStripped(fencedCodeBlock.getLiteral());
            writeEndOfLineIfNeeded(fencedCodeBlock, null);
        } else {
            this.textContent.write(fencedCodeBlock.getLiteral());
        }
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(HardLineBreak hardLineBreak) {
        writeEndOfLineIfNeeded(hardLineBreak, null);
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(Heading heading) {
        visitChildren(heading);
        writeEndOfLineIfNeeded(heading, Character.valueOf(CharConst.COLON));
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(ThematicBreak thematicBreak) {
        if (!this.context.stripNewlines()) {
            this.textContent.write("***");
        }
        writeEndOfLineIfNeeded(thematicBreak, null);
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(HtmlInline htmlInline) {
        writeText(htmlInline.getLiteral());
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(HtmlBlock htmlBlock) {
        writeText(htmlBlock.getLiteral());
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(Image image) {
        writeLink(image, image.getTitle(), image.getDestination());
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        if (this.context.stripNewlines()) {
            this.textContent.writeStripped(indentedCodeBlock.getLiteral());
            writeEndOfLineIfNeeded(indentedCodeBlock, null);
        } else {
            this.textContent.write(indentedCodeBlock.getLiteral());
        }
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(Link link) {
        writeLink(link, link.getTitle(), link.getDestination());
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(ListItem listItem) {
        ListHolder listHolder = this.listHolder;
        if (listHolder != null && (listHolder instanceof OrderedListHolder)) {
            OrderedListHolder orderedListHolder = (OrderedListHolder) listHolder;
            this.textContent.write((this.context.stripNewlines() ? "" : orderedListHolder.getIndent()) + orderedListHolder.getCounter() + orderedListHolder.getDelimiter() + " ");
            visitChildren(listItem);
            writeEndOfLineIfNeeded(listItem, null);
            orderedListHolder.increaseCounter();
            return;
        }
        if (listHolder == null || !(listHolder instanceof BulletListHolder)) {
            return;
        }
        BulletListHolder bulletListHolder = (BulletListHolder) listHolder;
        if (!this.context.stripNewlines()) {
            this.textContent.write(bulletListHolder.getIndent() + bulletListHolder.getMarker() + " ");
        }
        visitChildren(listItem);
        writeEndOfLineIfNeeded(listItem, null);
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(OrderedList orderedList) {
        if (this.listHolder != null) {
            writeEndOfLine();
        }
        this.listHolder = new OrderedListHolder(this.listHolder, orderedList);
        visitChildren(orderedList);
        writeEndOfLineIfNeeded(orderedList, null);
        if (this.listHolder.getParent() != null) {
            this.listHolder = this.listHolder.getParent();
        } else {
            this.listHolder = null;
        }
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(Paragraph paragraph) {
        visitChildren(paragraph);
        if (paragraph.getParent() == null || (paragraph.getParent() instanceof Document)) {
            writeEndOfLineIfNeeded(paragraph, null);
        }
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(SoftLineBreak softLineBreak) {
        writeEndOfLineIfNeeded(softLineBreak, null);
    }

    @Override // org.commonmark.node.AbstractVisitor, org.commonmark.node.Visitor
    public void visit(Text text) {
        writeText(text.getLiteral());
    }

    @Override // org.commonmark.node.AbstractVisitor
    protected void visitChildren(Node node) {
        Node firstChild = node.getFirstChild();
        while (firstChild != null) {
            Node next = firstChild.getNext();
            this.context.render(firstChild);
            firstChild = next;
        }
    }

    private void writeText(String str) {
        if (this.context.stripNewlines()) {
            this.textContent.writeStripped(str);
        } else {
            this.textContent.write(str);
        }
    }

    private void writeLink(Node node, String str, String str2) {
        boolean z = node.getFirstChild() != null;
        boolean z2 = (str == null || str.equals(str2)) ? false : true;
        boolean z3 = (str2 == null || str2.equals("")) ? false : true;
        if (z) {
            this.textContent.write('\"');
            visitChildren(node);
            this.textContent.write('\"');
            if (z2 || z3) {
                this.textContent.whitespace();
                this.textContent.write('(');
            }
        }
        if (z2) {
            this.textContent.write(str);
            if (z3) {
                this.textContent.colon();
                this.textContent.whitespace();
            }
        }
        if (z3) {
            this.textContent.write(str2);
        }
        if (z) {
            if (z2 || z3) {
                this.textContent.write(')');
            }
        }
    }

    private void writeEndOfLineIfNeeded(Node node, Character ch) {
        if (this.context.stripNewlines()) {
            if (ch != null) {
                this.textContent.write(ch.charValue());
            }
            if (node.getNext() != null) {
                this.textContent.whitespace();
                return;
            }
            return;
        }
        if (node.getNext() != null) {
            this.textContent.line();
        }
    }

    private void writeEndOfLine() {
        if (this.context.stripNewlines()) {
            this.textContent.whitespace();
        } else {
            this.textContent.line();
        }
    }
}
