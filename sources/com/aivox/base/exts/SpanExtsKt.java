package com.aivox.base.exts;

import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.ContextCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SpanExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u001a\"\u0010\b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0007\u001a$\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u000b\u001a\u00020\u0007¨\u0006\f"}, m1901d2 = {"setSpanTextColor", "", "Landroid/widget/TextView;", "fullStr", "", TypedValues.AttributesType.S_TARGET, TypedValues.Custom.S_COLOR, "", "setSpanTextSize", "textSize", "setSpanTextStyle", "style", "base_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class SpanExtsKt {
    public static final void setSpanTextSize(TextView textView, String fullStr, String target, int i) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(fullStr, "fullStr");
        Intrinsics.checkNotNullParameter(target, "target");
        String str = fullStr;
        SpannableString spannableString = new SpannableString(str);
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, target, 0, false, 6, (Object) null);
        if (iIndexOf$default >= 0) {
            spannableString.setSpan(new AbsoluteSizeSpan(i, true), iIndexOf$default, target.length() + iIndexOf$default, 17);
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static final void setSpanTextColor(TextView textView, String fullStr, String target, int i) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(fullStr, "fullStr");
        Intrinsics.checkNotNullParameter(target, "target");
        String str = fullStr;
        SpannableString spannableString = new SpannableString(str);
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, target, 0, false, 6, (Object) null);
        if (iIndexOf$default >= 0) {
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), i)), iIndexOf$default, target.length() + iIndexOf$default, 17);
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static /* synthetic */ void setSpanTextStyle$default(TextView textView, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 1;
        }
        setSpanTextStyle(textView, str, str2, i);
    }

    public static final void setSpanTextStyle(TextView textView, String fullStr, String target, int i) {
        Intrinsics.checkNotNullParameter(textView, "<this>");
        Intrinsics.checkNotNullParameter(fullStr, "fullStr");
        Intrinsics.checkNotNullParameter(target, "target");
        String str = fullStr;
        SpannableString spannableString = new SpannableString(str);
        int iIndexOf$default = StringsKt.indexOf$default((CharSequence) str, target, 0, false, 6, (Object) null);
        if (iIndexOf$default >= 0) {
            spannableString.setSpan(new StyleSpan(i), iIndexOf$default, target.length() + iIndexOf$default, 17);
        }
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
