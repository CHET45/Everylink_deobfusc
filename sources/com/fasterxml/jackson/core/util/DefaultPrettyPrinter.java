package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.p008io.SerializedString;
import com.fasterxml.jackson.core.util.Separators;
import java.io.IOException;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultPrettyPrinter implements PrettyPrinter, Instantiatable<DefaultPrettyPrinter>, Serializable {

    @Deprecated
    public static final SerializedString DEFAULT_ROOT_VALUE_SEPARATOR = new SerializedString(" ");
    private static final long serialVersionUID = 1;
    protected String _arrayEmptySeparator;
    protected Indenter _arrayIndenter;
    protected String _arrayValueSeparator;
    protected transient int _nesting;
    protected String _objectEmptySeparator;
    protected String _objectEntrySeparator;
    protected String _objectFieldValueSeparatorWithSpaces;
    protected Indenter _objectIndenter;
    protected SerializableString _rootSeparator;
    protected Separators _separators;

    @Deprecated
    protected boolean _spacesInObjectEntries;

    public interface Indenter {
        boolean isInline();

        void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException;
    }

    public static class NopIndenter implements Indenter, Serializable {
        public static final NopIndenter instance = new NopIndenter();

        @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
        public boolean isInline() {
            return true;
        }

        @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
        public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
        }
    }

    public DefaultPrettyPrinter() {
        this(DEFAULT_SEPARATORS);
    }

    @Deprecated
    public DefaultPrettyPrinter(String str) {
        this(str == null ? null : new SerializedString(str));
    }

    @Deprecated
    public DefaultPrettyPrinter(SerializableString serializableString) {
        this(DEFAULT_SEPARATORS.withRootSeparator(serializableString.getValue()));
    }

    @Deprecated
    public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter, SerializableString serializableString) {
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._arrayIndenter = defaultPrettyPrinter._arrayIndenter;
        this._objectIndenter = defaultPrettyPrinter._objectIndenter;
        this._spacesInObjectEntries = defaultPrettyPrinter._spacesInObjectEntries;
        this._nesting = defaultPrettyPrinter._nesting;
        this._separators = defaultPrettyPrinter._separators;
        this._objectFieldValueSeparatorWithSpaces = defaultPrettyPrinter._objectFieldValueSeparatorWithSpaces;
        this._objectEntrySeparator = defaultPrettyPrinter._objectEntrySeparator;
        this._objectEmptySeparator = defaultPrettyPrinter._objectEmptySeparator;
        this._arrayValueSeparator = defaultPrettyPrinter._arrayValueSeparator;
        this._arrayEmptySeparator = defaultPrettyPrinter._arrayEmptySeparator;
        this._rootSeparator = serializableString;
    }

    public DefaultPrettyPrinter(Separators separators) {
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._separators = separators;
        this._rootSeparator = separators.getRootSeparator() == null ? null : new SerializedString(separators.getRootSeparator());
        this._objectFieldValueSeparatorWithSpaces = separators.getObjectFieldValueSpacing().apply(separators.getObjectFieldValueSeparator());
        this._objectEntrySeparator = separators.getObjectEntrySpacing().apply(separators.getObjectEntrySeparator());
        this._objectEmptySeparator = separators.getObjectEmptySeparator();
        this._arrayValueSeparator = separators.getArrayValueSpacing().apply(separators.getArrayValueSeparator());
        this._arrayEmptySeparator = separators.getArrayEmptySeparator();
    }

    public DefaultPrettyPrinter(DefaultPrettyPrinter defaultPrettyPrinter) {
        this._arrayIndenter = FixedSpaceIndenter.instance;
        this._objectIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
        this._spacesInObjectEntries = true;
        this._rootSeparator = defaultPrettyPrinter._rootSeparator;
        this._arrayIndenter = defaultPrettyPrinter._arrayIndenter;
        this._objectIndenter = defaultPrettyPrinter._objectIndenter;
        this._spacesInObjectEntries = defaultPrettyPrinter._spacesInObjectEntries;
        this._nesting = defaultPrettyPrinter._nesting;
        this._separators = defaultPrettyPrinter._separators;
        this._objectFieldValueSeparatorWithSpaces = defaultPrettyPrinter._objectFieldValueSeparatorWithSpaces;
        this._objectEntrySeparator = defaultPrettyPrinter._objectEntrySeparator;
        this._objectEmptySeparator = defaultPrettyPrinter._objectEmptySeparator;
        this._arrayValueSeparator = defaultPrettyPrinter._arrayValueSeparator;
        this._arrayEmptySeparator = defaultPrettyPrinter._arrayEmptySeparator;
    }

    @Deprecated
    public DefaultPrettyPrinter withRootSeparator(SerializableString serializableString) {
        SerializableString serializableString2 = this._rootSeparator;
        if (serializableString2 == serializableString || (serializableString != null && serializableString.equals(serializableString2))) {
            return this;
        }
        return new DefaultPrettyPrinter(this).withSeparators(this._separators.withRootSeparator(serializableString == null ? null : serializableString.getValue()));
    }

    @Deprecated
    public DefaultPrettyPrinter withRootSeparator(String str) {
        return withRootSeparator(str == null ? null : new SerializedString(str));
    }

    public void indentArraysWith(Indenter indenter) {
        if (indenter == null) {
            indenter = NopIndenter.instance;
        }
        this._arrayIndenter = indenter;
    }

    public void indentObjectsWith(Indenter indenter) {
        if (indenter == null) {
            indenter = NopIndenter.instance;
        }
        this._objectIndenter = indenter;
    }

    public DefaultPrettyPrinter withArrayIndenter(Indenter indenter) {
        if (indenter == null) {
            indenter = NopIndenter.instance;
        }
        if (this._arrayIndenter == indenter) {
            return this;
        }
        DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
        defaultPrettyPrinter._arrayIndenter = indenter;
        return defaultPrettyPrinter;
    }

    public DefaultPrettyPrinter withObjectIndenter(Indenter indenter) {
        if (indenter == null) {
            indenter = NopIndenter.instance;
        }
        if (this._objectIndenter == indenter) {
            return this;
        }
        DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
        defaultPrettyPrinter._objectIndenter = indenter;
        return defaultPrettyPrinter;
    }

    @Deprecated
    public DefaultPrettyPrinter withSpacesInObjectEntries() {
        return _withSpaces(true);
    }

    @Deprecated
    public DefaultPrettyPrinter withoutSpacesInObjectEntries() {
        return _withSpaces(false);
    }

    protected DefaultPrettyPrinter _withSpaces(boolean z) {
        if (this._spacesInObjectEntries == z) {
            return this;
        }
        DefaultPrettyPrinter defaultPrettyPrinterWithSeparators = withSeparators(this._separators.withObjectFieldValueSpacing(z ? Separators.Spacing.BOTH : Separators.Spacing.NONE));
        defaultPrettyPrinterWithSeparators._spacesInObjectEntries = z;
        return defaultPrettyPrinterWithSeparators;
    }

    public DefaultPrettyPrinter withSeparators(Separators separators) {
        DefaultPrettyPrinter defaultPrettyPrinter = new DefaultPrettyPrinter(this);
        defaultPrettyPrinter._separators = separators;
        defaultPrettyPrinter._rootSeparator = separators.getRootSeparator() == null ? null : new SerializedString(separators.getRootSeparator());
        defaultPrettyPrinter._objectFieldValueSeparatorWithSpaces = separators.getObjectFieldValueSpacing().apply(separators.getObjectFieldValueSeparator());
        defaultPrettyPrinter._objectEntrySeparator = separators.getObjectEntrySpacing().apply(separators.getObjectEntrySeparator());
        defaultPrettyPrinter._objectEmptySeparator = separators.getObjectEmptySeparator();
        defaultPrettyPrinter._arrayValueSeparator = separators.getArrayValueSpacing().apply(separators.getArrayValueSeparator());
        defaultPrettyPrinter._arrayEmptySeparator = separators.getArrayEmptySeparator();
        return defaultPrettyPrinter;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.fasterxml.jackson.core.util.Instantiatable
    public DefaultPrettyPrinter createInstance() {
        if (getClass() != DefaultPrettyPrinter.class) {
            throw new IllegalStateException("Failed `createInstance()`: " + getClass().getName() + " does not override method; it has to");
        }
        return new DefaultPrettyPrinter(this);
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException {
        SerializableString serializableString = this._rootSeparator;
        if (serializableString != null) {
            jsonGenerator.writeRaw(serializableString);
        }
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeStartObject(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw('{');
        if (this._objectIndenter.isInline()) {
            return;
        }
        this._nesting++;
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException {
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._objectFieldValueSeparatorWithSpaces);
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._objectEntrySeparator);
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeEndObject(JsonGenerator jsonGenerator, int i) throws IOException {
        if (!this._objectIndenter.isInline()) {
            this._nesting--;
        }
        if (i > 0) {
            this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(this._objectEmptySeparator);
        }
        jsonGenerator.writeRaw('}');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeStartArray(JsonGenerator jsonGenerator) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            this._nesting++;
        }
        jsonGenerator.writeRaw('[');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException {
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeRaw(this._arrayValueSeparator);
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeEndArray(JsonGenerator jsonGenerator, int i) throws IOException {
        if (!this._arrayIndenter.isInline()) {
            this._nesting--;
        }
        if (i > 0) {
            this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(this._arrayEmptySeparator);
        }
        jsonGenerator.writeRaw(']');
    }

    public static class FixedSpaceIndenter extends NopIndenter {
        public static final FixedSpaceIndenter instance = new FixedSpaceIndenter();

        @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.NopIndenter, com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
        public boolean isInline() {
            return true;
        }

        @Override // com.fasterxml.jackson.core.util.DefaultPrettyPrinter.NopIndenter, com.fasterxml.jackson.core.util.DefaultPrettyPrinter.Indenter
        public void writeIndentation(JsonGenerator jsonGenerator, int i) throws IOException {
            jsonGenerator.writeRaw(' ');
        }
    }
}
