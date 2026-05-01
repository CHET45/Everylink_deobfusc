package com.aivox.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import com.aivox.base.util.LogUtil;
import com.google.android.material.C1585R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment {
    private static final String TAG = "BaseBottomSheetFragment";
    public FragmentActivity mActivity;
    public Context mContext;

    public abstract View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup);

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mContext = activity;
        return initBindingAndViews(layoutInflater, viewGroup);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        LogUtil.m337e(TAG, "onStart: ");
        super.onStart();
        FrameLayout frameLayout = (FrameLayout) ((BottomSheetDialog) getDialog()).getDelegate().findViewById(C1585R.id.design_bottom_sheet);
        if (frameLayout != null) {
            frameLayout.setBackground(null);
            ((CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams()).height = getPeekHeight();
            BottomSheetBehavior.from(frameLayout).setPeekHeight(getPeekHeight());
        }
    }

    protected int getPeekHeight() {
        int i = getResources().getDisplayMetrics().heightPixels;
        return i - (i / 20);
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        LogUtil.m335d(TAG, "onDetach");
        this.mActivity = null;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.m335d(TAG, "onAttach");
        if (context instanceof Activity) {
            this.mActivity = (FragmentActivity) context;
        }
    }
}
