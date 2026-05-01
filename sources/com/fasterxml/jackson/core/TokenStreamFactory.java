package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.p008io.DataOutputAsStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TokenStreamFactory implements Versioned, Serializable {
    private static final long serialVersionUID = 2;

    public abstract boolean canHandleBinaryNatively();

    public abstract boolean canParseAsync();

    public abstract boolean canUseSchema(FormatSchema formatSchema);

    public abstract JsonGenerator createGenerator(DataOutput dataOutput) throws IOException;

    public abstract JsonGenerator createGenerator(DataOutput dataOutput, JsonEncoding jsonEncoding) throws IOException;

    public abstract JsonGenerator createGenerator(File file, JsonEncoding jsonEncoding) throws IOException;

    public abstract JsonGenerator createGenerator(OutputStream outputStream) throws IOException;

    public abstract JsonGenerator createGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException;

    public abstract JsonGenerator createGenerator(Writer writer) throws IOException;

    public abstract JsonParser createNonBlockingByteArrayParser() throws IOException;

    public abstract JsonParser createNonBlockingByteBufferParser() throws IOException;

    public abstract JsonParser createParser(DataInput dataInput) throws IOException;

    public abstract JsonParser createParser(File file) throws IOException;

    public abstract JsonParser createParser(InputStream inputStream) throws IOException;

    public abstract JsonParser createParser(Reader reader) throws IOException;

    public abstract JsonParser createParser(String str) throws IOException;

    public abstract JsonParser createParser(URL url) throws IOException;

    public abstract JsonParser createParser(byte[] bArr) throws IOException;

    public abstract JsonParser createParser(byte[] bArr, int i, int i2) throws IOException;

    public abstract JsonParser createParser(char[] cArr) throws IOException;

    public abstract JsonParser createParser(char[] cArr, int i, int i2) throws IOException;

    public abstract int getFactoryFeatures();

    public abstract int getFormatGeneratorFeatures();

    public abstract String getFormatName();

    public abstract int getFormatParserFeatures();

    public abstract Class<? extends FormatFeature> getFormatReadFeatureType();

    public abstract Class<? extends FormatFeature> getFormatWriteFeatureType();

    public abstract int getGeneratorFeatures();

    public abstract int getParserFeatures();

    public abstract boolean isEnabled(JsonFactory.Feature feature);

    public abstract boolean isEnabled(JsonGenerator.Feature feature);

    public abstract boolean isEnabled(JsonParser.Feature feature);

    public abstract boolean isEnabled(StreamReadFeature streamReadFeature);

    public abstract boolean isEnabled(StreamWriteFeature streamWriteFeature);

    public abstract boolean requiresPropertyOrdering();

    public abstract StreamReadConstraints streamReadConstraints();

    public abstract StreamWriteConstraints streamWriteConstraints();

    protected OutputStream _createDataOutputWrapper(DataOutput dataOutput) {
        return new DataOutputAsStream(dataOutput);
    }

    protected InputStream _optimizedStreamFromURL(URL url) throws IOException {
        String host;
        if ("file".equals(url.getProtocol()) && (((host = url.getHost()) == null || host.length() == 0) && url.getPath().indexOf(37) < 0)) {
            return new FileInputStream(url.getPath());
        }
        return url.openStream();
    }

    protected InputStream _fileInputStream(File file) throws IOException {
        return new FileInputStream(file);
    }

    protected OutputStream _fileOutputStream(File file) throws IOException {
        return new FileOutputStream(file);
    }

    protected void _checkRangeBoundsForByteArray(byte[] bArr, int i, int i2) throws IllegalArgumentException {
        if (bArr == null) {
            _reportRangeError("Invalid `byte[]` argument: `null`");
        }
        int length = bArr.length;
        int i3 = i + i2;
        if (((length - i3) | i | i2 | i3) < 0) {
            _reportRangeError(String.format("Invalid 'offset' (%d) and/or 'len' (%d) arguments for `byte[]` of length %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(length)));
        }
    }

    protected void _checkRangeBoundsForCharArray(char[] cArr, int i, int i2) throws IOException {
        if (cArr == null) {
            _reportRangeError("Invalid `char[]` argument: `null`");
        }
        int length = cArr.length;
        int i3 = i + i2;
        if (((length - i3) | i | i2 | i3) < 0) {
            _reportRangeError(String.format("Invalid 'offset' (%d) and/or 'len' (%d) arguments for `char[]` of length %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(length)));
        }
    }

    protected <T> T _reportRangeError(String str) throws IllegalArgumentException {
        throw new IllegalArgumentException(str);
    }
}
