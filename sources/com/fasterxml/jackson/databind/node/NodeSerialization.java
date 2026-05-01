package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
class NodeSerialization implements Serializable, Externalizable {
    protected static final int LONGEST_EAGER_ALLOC = 100000;
    private static final long serialVersionUID = 1;
    public byte[] json;

    public NodeSerialization() {
    }

    public NodeSerialization(byte[] bArr) {
        this.json = bArr;
    }

    protected Object readResolve() {
        try {
            return InternalNodeMapper.bytesToNode(this.json);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to JDK deserialize `JsonNode` value: " + e.getMessage(), e);
        }
    }

    public static NodeSerialization from(Object obj) {
        try {
            return new NodeSerialization(InternalNodeMapper.valueToBytes(obj));
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to JDK serialize `" + obj.getClass().getSimpleName() + "` value: " + e.getMessage(), e);
        }
    }

    @Override // java.io.Externalizable
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeInt(this.json.length);
        objectOutput.write(this.json);
    }

    @Override // java.io.Externalizable
    public void readExternal(ObjectInput objectInput) throws IOException {
        this.json = _read(objectInput, objectInput.readInt());
    }

    private byte[] _read(ObjectInput objectInput, int i) throws IOException {
        if (i <= LONGEST_EAGER_ALLOC) {
            byte[] bArr = new byte[i];
            objectInput.readFully(bArr, 0, i);
            return bArr;
        }
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(LONGEST_EAGER_ALLOC);
        try {
            byte[] bArrResetAndGetFirstSegment = byteArrayBuilder.resetAndGetFirstSegment();
            while (true) {
                int i2 = 0;
                do {
                    int iMin = Math.min(bArrResetAndGetFirstSegment.length - i2, i);
                    objectInput.readFully(bArrResetAndGetFirstSegment, 0, iMin);
                    i -= iMin;
                    i2 += iMin;
                    if (i == 0) {
                        byte[] bArrCompleteAndCoalesce = byteArrayBuilder.completeAndCoalesce(i2);
                        byteArrayBuilder.close();
                        return bArrCompleteAndCoalesce;
                    }
                } while (i2 != bArrResetAndGetFirstSegment.length);
                bArrResetAndGetFirstSegment = byteArrayBuilder.finishCurrentSegment();
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayBuilder.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }
}
