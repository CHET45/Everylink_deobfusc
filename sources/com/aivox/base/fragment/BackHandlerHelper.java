package com.aivox.base.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BackHandlerHelper {
    public static boolean handleBackPress(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        for (int size = fragments.size() - 1; size >= 0; size--) {
            if (isFragmentBackHandled(fragments.get(size))) {
                return true;
            }
        }
        if (fragmentManager.getBackStackEntryCount() <= 0) {
            return false;
        }
        fragmentManager.popBackStack();
        return true;
    }

    public static boolean handleBackPress(Fragment fragment) {
        return handleBackPress(fragment.getChildFragmentManager());
    }

    public static boolean handleBackPress(FragmentActivity fragmentActivity) {
        return handleBackPress(fragmentActivity.getSupportFragmentManager());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isFragmentBackHandled(Fragment fragment) {
        return fragment != 0 && fragment.isVisible() && fragment.getUserVisibleHint() && (fragment instanceof FragmentBackHandler) && ((FragmentBackHandler) fragment).onBackPressed();
    }
}
