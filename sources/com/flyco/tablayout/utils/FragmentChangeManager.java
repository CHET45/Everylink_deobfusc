package com.flyco.tablayout.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class FragmentChangeManager {
    private int mContainerViewId;
    private int mCurrentTab;
    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments;

    public FragmentChangeManager(FragmentManager fragmentManager, int i, ArrayList<Fragment> arrayList) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = i;
        this.mFragments = arrayList;
        initFragments();
    }

    private void initFragments() {
        for (Fragment fragment : this.mFragments) {
            this.mFragmentManager.beginTransaction().add(this.mContainerViewId, fragment).hide(fragment).commit();
        }
        setFragments(0);
    }

    public void setFragments(int i) {
        for (int i2 = 0; i2 < this.mFragments.size(); i2++) {
            FragmentTransaction fragmentTransactionBeginTransaction = this.mFragmentManager.beginTransaction();
            Fragment fragment = this.mFragments.get(i2);
            if (i2 == i) {
                fragmentTransactionBeginTransaction.show(fragment);
            } else {
                fragmentTransactionBeginTransaction.hide(fragment);
            }
            fragmentTransactionBeginTransaction.commit();
        }
        this.mCurrentTab = i;
    }

    public int getCurrentTab() {
        return this.mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return this.mFragments.get(this.mCurrentTab);
    }
}
