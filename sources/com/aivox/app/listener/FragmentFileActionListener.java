package com.aivox.app.listener;

import com.aivox.common.model.AudioInfoBean;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface FragmentFileActionListener {
    void audioSelectChanged(List<AudioInfoBean> list);

    void click2Record(int i);
}
