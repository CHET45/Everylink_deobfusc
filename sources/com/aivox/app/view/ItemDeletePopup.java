package com.aivox.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.aivox.app.C0726R;
import com.aivox.common.C0958R;

/* JADX INFO: loaded from: classes.dex */
public class ItemDeletePopup {
    private final Context mContext;
    private final OnDeleteListener mListener;
    private PopupWindow mPopupWindow;

    public interface OnDeleteListener {
        void delete();
    }

    public static ItemDeletePopup create(Context context, OnDeleteListener onDeleteListener) {
        return new ItemDeletePopup(context, onDeleteListener);
    }

    private ItemDeletePopup(Context context, OnDeleteListener onDeleteListener) {
        this.mContext = context;
        this.mListener = onDeleteListener;
        initViews();
    }

    protected void initViews() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(C0726R.layout.ai_chat_delete_pop, (ViewGroup) null);
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -2);
        this.mPopupWindow = popupWindow;
        popupWindow.setAnimationStyle(C0958R.style.LeftTopPopAnim);
        viewInflate.findViewById(C0726R.id.item_delete).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.ItemDeletePopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2427lambda$initViews$0$comaivoxappviewItemDeletePopup(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initViews$0$com-aivox-app-view-ItemDeletePopup, reason: not valid java name */
    /* synthetic */ void m2427lambda$initViews$0$comaivoxappviewItemDeletePopup(View view2) {
        OnDeleteListener onDeleteListener = this.mListener;
        if (onDeleteListener != null) {
            onDeleteListener.delete();
        }
    }

    public void show(View view2, int i) {
        this.mPopupWindow.dismiss();
        if (this.mPopupWindow.isShowing()) {
            return;
        }
        this.mPopupWindow.showAtLocation(view2, 0, 0, i);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mPopupWindow.setOnDismissListener(onDismissListener);
    }

    public boolean isShowing() {
        return this.mPopupWindow.isShowing();
    }

    public void dismiss() {
        this.mPopupWindow.dismiss();
    }
}
