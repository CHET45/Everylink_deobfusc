package com.aivox.set.generated.callback;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class OnClickListener implements View.OnClickListener {
    final Listener mListener;
    final int mSourceId;

    public interface Listener {
        void _internalCallbackOnClick(int i, View view2);
    }

    public OnClickListener(Listener listener, int i) {
        this.mListener = listener;
        this.mSourceId = i;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        this.mListener._internalCallbackOnClick(this.mSourceId, view2);
    }
}
