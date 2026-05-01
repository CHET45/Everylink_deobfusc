package com.aivox.app.listener;

import com.aivox.common.model.AudioInfoBean;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface OnFragmentInteractionListener {
    void audioSelectChanged(List<AudioInfoBean> list, boolean z, boolean z2, boolean z3, int i);

    void createOrRenameFolder(String str, int i);

    void deleteFolder(int i);
}
