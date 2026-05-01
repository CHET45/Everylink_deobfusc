package com.app.hubert.guide.listener;

import com.app.hubert.guide.core.Controller;

/* JADX INFO: loaded from: classes.dex */
public interface OnGuideChangedListener {
    void onRemoved(Controller controller);

    void onShowed(Controller controller);
}
