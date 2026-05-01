package org.minidns.record;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public abstract class Data {
    private byte[] bytes;
    private transient Integer hashCodeCache;

    public abstract Record.TYPE getType();

    protected abstract void serialize(DataOutputStream dataOutputStream) throws IOException;

    private void setBytes() {
        if (this.bytes != null) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            serialize(new DataOutputStream(byteArrayOutputStream));
            this.bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public final int length() {
        setBytes();
        return this.bytes.length;
    }

    public final void toOutputStream(OutputStream outputStream) throws IOException {
        toOutputStream(new DataOutputStream(outputStream));
    }

    public final void toOutputStream(DataOutputStream dataOutputStream) throws IOException {
        setBytes();
        dataOutputStream.write(this.bytes);
    }

    public final byte[] toByteArray() {
        setBytes();
        return (byte[]) this.bytes.clone();
    }

    public final int hashCode() {
        if (this.hashCodeCache == null) {
            setBytes();
            this.hashCodeCache = Integer.valueOf(Arrays.hashCode(this.bytes));
        }
        return this.hashCodeCache.intValue();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Data)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        Data data = (Data) obj;
        data.setBytes();
        setBytes();
        return Arrays.equals(this.bytes, data.bytes);
    }
}
