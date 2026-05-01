package com.zyyoona7.popup;

import android.content.Context;
import android.view.View;

/* JADX INFO: loaded from: classes4.dex */
public class EasyPopup extends BasePopup<EasyPopup> {
    private OnViewListener mOnViewListener;

    public interface OnViewListener {
        void initViews(View view2, EasyPopup easyPopup);
    }

    @Override // com.zyyoona7.popup.BasePopup
    protected void initAttributes() {
    }

    public static EasyPopup create() {
        return new EasyPopup();
    }

    public static EasyPopup create(Context context) {
        return new EasyPopup(context);
    }

    public EasyPopup() {
    }

    public EasyPopup(Context context) {
        setContext(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zyyoona7.popup.BasePopup
    public void initViews(View view2, EasyPopup easyPopup) {
        OnViewListener onViewListener = this.mOnViewListener;
        if (onViewListener != null) {
            onViewListener.initViews(view2, easyPopup);
        }
    }

    public EasyPopup setOnViewListener(OnViewListener onViewListener) {
        this.mOnViewListener = onViewListener;
        return this;
    }
}
