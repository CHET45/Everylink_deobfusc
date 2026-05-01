package com.aivox.base.exts;

import android.content.Context;
import android.text.Editable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.aivox.base.common.Constant;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EditTextExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0083\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u00022%\b\u0006\u0010\u0003\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00010\u00042d\b\u0006\u0010\t\u001a^\u0012\u0015\u0012\u0013\u0018\u00010\u000b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00010\n2d\b\u0006\u0010\u0010\u001a^\u0012\u0015\u0012\u0013\u0018\u00010\u000b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00010\nH\u0086\bø\u0001\u0000\u001a7\u0010\u0012\u001a\u00020\u0001*\u00020\u00022%\b\u0006\u0010\u0003\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0086\bø\u0001\u0000\u001av\u0010\u0013\u001a\u00020\u0001*\u00020\u00022d\b\u0006\u0010\t\u001a^\u0012\u0015\u0012\u0013\u0018\u00010\u000b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00010\nH\u0086\bø\u0001\u0000\u001a\u0012\u0010\u0014\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016\u001a\n\u0010\u0017\u001a\u00020\u0001*\u00020\u0002\u001av\u0010\u0018\u001a\u00020\u0001*\u00020\u00022d\b\u0006\u0010\u0010\u001a^\u0012\u0015\u0012\u0013\u0018\u00010\u000b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00010\nH\u0086\bø\u0001\u0000\u001a\u0012\u0010\u0019\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\f\u001a\u0012\u0010\u001b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016\u001a\u0012\u0010\u001c\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001d"}, m1901d2 = {"addTextChanged", "", "Landroid/widget/EditText;", "afterChanged", "Lkotlin/Function1;", "Landroid/text/Editable;", "Lkotlin/ParameterName;", "name", "s", "beforeChanged", "Lkotlin/Function4;", "", "", "start", "count", "after", "onChanged", "before", "afterTextChanged", "beforeTextChanged", "hideSoftInput", "context", "Landroid/content/Context;", "moveSelectionToEnd", "onTextChanged", "setSelectionSafely", Constant.KEY_INDEX, "showSoftInput", "triggleSoftInput", "base_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class EditTextExtsKt {
    public static /* synthetic */ void addTextChanged$default(EditText editText, Function1 afterChanged, Function4 beforeChanged, Function4 onChanged, int i, Object obj) {
        if ((i & 1) != 0) {
            afterChanged = new Function1<Editable, Unit>() { // from class: com.aivox.base.exts.EditTextExtsKt.addTextChanged.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Editable editable) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Editable editable) {
                    invoke2(editable);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 2) != 0) {
            beforeChanged = new Function4<CharSequence, Integer, Integer, Integer, Unit>() { // from class: com.aivox.base.exts.EditTextExtsKt.addTextChanged.2
                public final void invoke(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(CharSequence charSequence, Integer num, Integer num2, Integer num3) {
                    invoke(charSequence, num.intValue(), num2.intValue(), num3.intValue());
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 4) != 0) {
            onChanged = new Function4<CharSequence, Integer, Integer, Integer, Unit>() { // from class: com.aivox.base.exts.EditTextExtsKt.addTextChanged.3
                public final void invoke(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(CharSequence charSequence, Integer num, Integer num2, Integer num3) {
                    invoke(charSequence, num.intValue(), num2.intValue(), num3.intValue());
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(afterChanged, "afterChanged");
        Intrinsics.checkNotNullParameter(beforeChanged, "beforeChanged");
        Intrinsics.checkNotNullParameter(onChanged, "onChanged");
        editText.addTextChangedListener(new EditTextExtsKt$addTextChanged$listener$1(afterChanged, beforeChanged, onChanged));
    }

    public static final void addTextChanged(EditText editText, Function1<? super Editable, Unit> afterChanged, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> beforeChanged, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> onChanged) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(afterChanged, "afterChanged");
        Intrinsics.checkNotNullParameter(beforeChanged, "beforeChanged");
        Intrinsics.checkNotNullParameter(onChanged, "onChanged");
        editText.addTextChangedListener(new EditTextExtsKt$addTextChanged$listener$1(afterChanged, beforeChanged, onChanged));
    }

    public static /* synthetic */ void afterTextChanged$default(EditText editText, Function1 afterChanged, int i, Object obj) {
        if ((i & 1) != 0) {
            afterChanged = new Function1<Editable, Unit>() { // from class: com.aivox.base.exts.EditTextExtsKt.afterTextChanged.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Editable editable) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Editable editable) {
                    invoke2(editable);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(afterChanged, "afterChanged");
        editText.addTextChangedListener(new C0879x503d4a86(afterChanged));
    }

    public static /* synthetic */ void beforeTextChanged$default(EditText editText, Function4 beforeChanged, int i, Object obj) {
        if ((i & 1) != 0) {
            beforeChanged = new Function4<CharSequence, Integer, Integer, Integer, Unit>() { // from class: com.aivox.base.exts.EditTextExtsKt.beforeTextChanged.1
                public final void invoke(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(CharSequence charSequence, Integer num, Integer num2, Integer num3) {
                    invoke(charSequence, num.intValue(), num2.intValue(), num3.intValue());
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(beforeChanged, "beforeChanged");
        editText.addTextChangedListener(new C0881x847695df(beforeChanged));
    }

    public static /* synthetic */ void onTextChanged$default(EditText editText, Function4 onChanged, int i, Object obj) {
        if ((i & 1) != 0) {
            onChanged = new Function4<CharSequence, Integer, Integer, Integer, Unit>() { // from class: com.aivox.base.exts.EditTextExtsKt.onTextChanged.1
                public final void invoke(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(CharSequence charSequence, Integer num, Integer num2, Integer num3) {
                    invoke(charSequence, num.intValue(), num2.intValue(), num3.intValue());
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(onChanged, "onChanged");
        editText.addTextChangedListener(new EditTextExtsKt$onTextChanged$$inlined$addTextChanged$default$1(onChanged));
    }

    public static final void showSoftInput(EditText editText, Context context) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).showSoftInputFromInputMethod(editText.getWindowToken(), 0);
    }

    public static final void hideSoftInput(EditText editText, Context context) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static final void triggleSoftInput(EditText editText, Context context) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        ((InputMethodManager) systemService).toggleSoftInputFromWindow(editText.getWindowToken(), 0, 2);
    }

    public static final void setSelectionSafely(EditText editText, int i) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        try {
            editText.setSelection(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void moveSelectionToEnd(EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        int length = editText.getText().length();
        if (length > 0) {
            editText.setSelection(length);
        }
    }

    public static final void afterTextChanged(EditText editText, Function1<? super Editable, Unit> afterChanged) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(afterChanged, "afterChanged");
        editText.addTextChangedListener(new C0879x503d4a86(afterChanged));
    }

    public static final void beforeTextChanged(EditText editText, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> beforeChanged) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(beforeChanged, "beforeChanged");
        editText.addTextChangedListener(new C0881x847695df(beforeChanged));
    }

    public static final void onTextChanged(EditText editText, Function4<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, Unit> onChanged) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        Intrinsics.checkNotNullParameter(onChanged, "onChanged");
        editText.addTextChangedListener(new EditTextExtsKt$onTextChanged$$inlined$addTextChanged$default$1(onChanged));
    }
}
