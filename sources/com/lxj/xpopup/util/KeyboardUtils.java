package com.lxj.xpopup.util;

import android.R;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.lxj.xpopup.core.BasePopupView;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes4.dex */
public final class KeyboardUtils {
    private static ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    public static int sDecorViewInvisibleHeightPre;
    private static HashMap<View, OnSoftInputChangedListener> listenerMap = new HashMap<>();
    private static int sDecorViewDelta = 0;

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int i);
    }

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDecorViewInvisibleHeight(Window window) {
        View decorView = window.getDecorView();
        if (decorView == null) {
            return sDecorViewInvisibleHeightPre;
        }
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        int iAbs = Math.abs(decorView.getBottom() - rect.bottom);
        if (XPopupUtils.isNavBarVisible(window)) {
            iAbs -= XPopupUtils.getNavBarHeight();
        }
        if (iAbs <= XPopupUtils.getNavBarHeight() + XPopupUtils.getStatusBarHeight()) {
            sDecorViewDelta = iAbs;
            return 0;
        }
        return iAbs - sDecorViewDelta;
    }

    public static void registerSoftInputChangedListener(final Window window, BasePopupView basePopupView, OnSoftInputChangedListener onSoftInputChangedListener) {
        if ((window.getAttributes().flags & 512) != 0) {
            window.clearFlags(512);
        }
        FrameLayout frameLayout = (FrameLayout) window.findViewById(R.id.content);
        sDecorViewInvisibleHeightPre = getDecorViewInvisibleHeight(window);
        listenerMap.put(basePopupView, onSoftInputChangedListener);
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.lxj.xpopup.util.KeyboardUtils.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int decorViewInvisibleHeight = KeyboardUtils.getDecorViewInvisibleHeight(window);
                if (KeyboardUtils.sDecorViewInvisibleHeightPre != decorViewInvisibleHeight) {
                    Iterator it = KeyboardUtils.listenerMap.values().iterator();
                    while (it.hasNext()) {
                        ((OnSoftInputChangedListener) it.next()).onSoftInputChanged(decorViewInvisibleHeight);
                    }
                    KeyboardUtils.sDecorViewInvisibleHeightPre = decorViewInvisibleHeight;
                }
            }
        });
    }

    public static void removeLayoutChangeListener(View view2, BasePopupView basePopupView) {
        View viewFindViewById;
        onGlobalLayoutListener = null;
        listenerMap.remove(basePopupView);
        if (view2 == null || (viewFindViewById = view2.findViewById(R.id.content)) == null) {
            return;
        }
        viewFindViewById.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
    }

    public static void showSoftInput(View view2) {
        ((InputMethodManager) view2.getContext().getSystemService("input_method")).showSoftInput(view2, 2);
    }

    public static void hideSoftInput(View view2) {
        ((InputMethodManager) view2.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view2.getWindowToken(), 0);
    }

    public static void hideSoftInput(Window window) {
        View currentFocus = window.getCurrentFocus();
        if (currentFocus == null) {
            View decorView = window.getDecorView();
            View viewFindViewWithTag = decorView.findViewWithTag("keyboardTagView");
            if (viewFindViewWithTag == null) {
                viewFindViewWithTag = new EditText(window.getContext());
                viewFindViewWithTag.setTag("keyboardTagView");
                ((ViewGroup) decorView).addView(viewFindViewWithTag, 0, 0);
            }
            currentFocus = viewFindViewWithTag;
            currentFocus.requestFocus();
        }
        hideSoftInput(currentFocus);
    }
}
