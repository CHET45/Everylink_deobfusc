package com.app.hubert.guide.lifecycle;

import android.app.Fragment;
import com.app.hubert.guide.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class ListenerFragment extends Fragment {
    FragmentLifecycle mFragmentLifecycle;

    public void setFragmentLifecycle(FragmentLifecycle fragmentLifecycle) {
        this.mFragmentLifecycle = fragmentLifecycle;
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        LogUtil.m369d("onStart: ");
        this.mFragmentLifecycle.onStart();
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.mFragmentLifecycle.onStop();
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this.mFragmentLifecycle.onDestroyView();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LogUtil.m369d("onDestroy: ");
        this.mFragmentLifecycle.onDestroy();
    }
}
