package com.lxj.easyadapter;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EasyAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(m1899bv = {1, 0, 3}, m1900d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J%\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\u0006H$¢\u0006\u0002\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u0006X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u0013"}, m1901d2 = {"Lcom/lxj/easyadapter/EasyAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/lxj/easyadapter/MultiItemTypeAdapter;", "data", "", "mLayoutId", "", "(Ljava/util/List;I)V", "getMLayoutId", "()I", "setMLayoutId", "(I)V", "bind", "", "holder", "Lcom/lxj/easyadapter/ViewHolder;", "t", "position", "(Lcom/lxj/easyadapter/ViewHolder;Ljava/lang/Object;I)V", "easy-adapter_release"}, m1902k = 1, m1903mv = {1, 1, 15})
public abstract class EasyAdapter<T> extends MultiItemTypeAdapter<T> {
    private int mLayoutId;

    protected abstract void bind(ViewHolder holder, T t, int position);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EasyAdapter(List<? extends T> data, int i) {
        super(data);
        Intrinsics.checkParameterIsNotNull(data, "data");
        this.mLayoutId = i;
        addItemDelegate(new ItemDelegate<T>() { // from class: com.lxj.easyadapter.EasyAdapter.1
            @Override // com.lxj.easyadapter.ItemDelegate
            public boolean isThisType(T item, int position) {
                return true;
            }

            @Override // com.lxj.easyadapter.ItemDelegate
            public void bind(ViewHolder holder, T t, int position) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                EasyAdapter.this.bind(holder, t, position);
            }

            @Override // com.lxj.easyadapter.ItemDelegate
            public int getLayoutId() {
                return EasyAdapter.this.getMLayoutId();
            }
        });
    }

    protected final int getMLayoutId() {
        return this.mLayoutId;
    }

    protected final void setMLayoutId(int i) {
        this.mLayoutId = i;
    }
}
