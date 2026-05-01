package top.zibin.luban.p042io;

/* JADX INFO: loaded from: classes5.dex */
public final class ByteArrayAdapter implements ArrayAdapterInterface<byte[]> {
    private static final String TAG = "ByteArrayPool";

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public int getElementSizeInBytes() {
        return 1;
    }

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public String getTag() {
        return TAG;
    }

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public int getArrayLength(byte[] bArr) {
        return bArr.length;
    }

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public byte[] newArray(int i) {
        return new byte[i];
    }
}
