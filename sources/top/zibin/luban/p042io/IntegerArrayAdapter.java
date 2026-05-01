package top.zibin.luban.p042io;

/* JADX INFO: loaded from: classes5.dex */
public final class IntegerArrayAdapter implements ArrayAdapterInterface<int[]> {
    private static final String TAG = "IntegerArrayPool";

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public int getElementSizeInBytes() {
        return 4;
    }

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public String getTag() {
        return TAG;
    }

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public int getArrayLength(int[] iArr) {
        return iArr.length;
    }

    @Override // top.zibin.luban.p042io.ArrayAdapterInterface
    public int[] newArray(int i) {
        return new int[i];
    }
}
