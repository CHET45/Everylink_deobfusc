package top.zibin.luban.p042io;

/* JADX INFO: loaded from: classes5.dex */
interface ArrayAdapterInterface<T> {
    int getArrayLength(T t);

    int getElementSizeInBytes();

    String getTag();

    T newArray(int i);
}
