package com.aivox.base.util;

import android.content.Context;
import android.text.InputFilter;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/* JADX INFO: loaded from: classes.dex */
public class LayoutUtil {
    public static Button initFooterView() {
        Button button = new Button(BaseAppUtils.getContext());
        button.setText("点击获取更多");
        button.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        button.setBackgroundColor(0);
        button.setPadding(0, 25, 0, 25);
        button.setGravity(17);
        button.setTextSize(16.0f);
        return button;
    }

    public static TextView initHeadview() {
        TextView textView = new TextView(BaseAppUtils.getContext());
        textView.setText("--------以上是历史消息--------");
        textView.setTextSize(16.0f);
        textView.setPadding(0, 10, 0, 10);
        textView.setTextColor(-7829368);
        textView.setGravity(17);
        return textView;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        int measuredHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View view2 = adapter.getView(i, null, listView);
            view2.measure(0, 0);
            measuredHeight += view2.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = measuredHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
    }

    public static void isBtnCanClick(View view2, boolean z) {
        if (z) {
            view2.requestFocus();
        }
        view2.setClickable(z);
    }

    public static void isBtnCanClick(boolean z, View... viewArr) {
        for (View view2 : viewArr) {
            isBtnCanClick(view2, z);
        }
    }

    public static boolean isFilled(View... viewArr) {
        for (View view2 : viewArr) {
            if (((view2 instanceof EditText) && BaseStringUtil.isEmpty(((EditText) view2).getText().toString().trim())) || ((view2 instanceof TextView) && BaseStringUtil.isEmpty(((TextView) view2).getText().toString().trim()))) {
                return false;
            }
        }
        return true;
    }

    public static void isEtHasFocus(boolean z, View... viewArr) {
        for (View view2 : viewArr) {
            isEtHasFocus(z, view2);
        }
    }

    public static void isEtHasFocus(boolean z, View view2) {
        view2.setFocusable(z);
        if (z) {
            view2.setFocusableInTouchMode(true);
            view2.requestFocus();
            view2.findFocus();
        }
    }

    public static void setEtLengthLimit(EditText editText, int i) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
    }

    public static void viewsGone(int i, View... viewArr) {
        for (View view2 : viewArr) {
            if (view2 != null) {
                view2.setVisibility(i);
            }
        }
    }

    public static void setBottomSheetDialogBgColor(Context context, View view2) {
        try {
            ((ViewGroup) view2.getParent()).setBackground(null);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
        }
    }

    public static int getLineMaxNumber(String str, TextPaint textPaint, float f) {
        if (str == null || "".equals(str)) {
            return 0;
        }
        return (int) (f / (textPaint.measureText(str) / str.length()));
    }

    public static int getLineMaxNumber(String str, TextPaint textPaint, int i) {
        if (str == null || "".equals(str)) {
            return 0;
        }
        return new StaticLayout(str, textPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false).getLineEnd(0);
    }

    public static void setMargins(View view2, int i, int i2, int i3, int i4) {
        if (view2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) view2.getLayoutParams()).setMargins(i, i2, i3, i4);
            view2.requestLayout();
        }
    }

    public static void fitSystemInsets(View view2, final boolean z) {
        if (view2 == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view2, new OnApplyWindowInsetsListener() { // from class: com.aivox.base.util.LayoutUtil$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view3, WindowInsetsCompat windowInsetsCompat) {
                return LayoutUtil.lambda$fitSystemInsets$0(z, view3, windowInsetsCompat);
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat lambda$fitSystemInsets$0(boolean z, View view2, WindowInsetsCompat windowInsetsCompat) {
        if (z) {
            view2.setPadding(view2.getPaddingLeft(), windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).f2322top, view2.getPaddingRight(), view2.getPaddingBottom());
        } else {
            view2.setPadding(view2.getPaddingLeft(), view2.getPaddingTop(), view2.getPaddingRight(), Math.max(windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom, windowInsetsCompat.getInsets(WindowInsetsCompat.Type.ime()).bottom));
        }
        return windowInsetsCompat;
    }

    public static void fitSystemInsets(View view2) {
        if (view2 == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view2, new OnApplyWindowInsetsListener() { // from class: com.aivox.base.util.LayoutUtil$$ExternalSyntheticLambda1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view3, WindowInsetsCompat windowInsetsCompat) {
                return LayoutUtil.lambda$fitSystemInsets$1(view3, windowInsetsCompat);
            }
        });
    }

    static /* synthetic */ WindowInsetsCompat lambda$fitSystemInsets$1(View view2, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        view2.setPadding(view2.getPaddingLeft(), insets.f2322top, view2.getPaddingRight(), Math.max(insets.bottom, windowInsetsCompat.getInsets(WindowInsetsCompat.Type.ime()).bottom));
        return windowInsetsCompat;
    }
}
