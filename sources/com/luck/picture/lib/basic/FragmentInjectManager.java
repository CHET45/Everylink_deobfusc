package com.luck.picture.lib.basic;

import android.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.luck.picture.lib.C2114R;
import com.luck.picture.lib.utils.ActivityCompatHelper;

/* JADX INFO: loaded from: classes3.dex */
public class FragmentInjectManager {
    public static void injectFragment(FragmentActivity fragmentActivity, String str, Fragment fragment) {
        if (ActivityCompatHelper.checkFragmentNonExits(fragmentActivity, str)) {
            fragmentActivity.getSupportFragmentManager().beginTransaction().add(C2114R.id.fragment_container, fragment, str).addToBackStack(str).commitAllowingStateLoss();
        }
    }

    public static void injectSystemRoomFragment(FragmentManager fragmentManager, String str, Fragment fragment) {
        fragmentManager.beginTransaction().add(R.id.content, fragment, str).addToBackStack(str).commitAllowingStateLoss();
    }
}
