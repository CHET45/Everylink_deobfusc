package com.aivox.base.exts;

import android.text.Editable;
import android.text.TextWatcher;
import com.alibaba.fastjson.asm.Opcodes;
import kotlin.Metadata;
import kotlin.jvm.functions.Function4;

/* JADX INFO: compiled from: EditTextExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J*\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0016J*\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016¨\u0006\u000e¸\u0006\u0000"}, m1901d2 = {"com/aivox/base/exts/EditTextExtsKt$addTextChanged$listener$1", "Landroid/text/TextWatcher;", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "", "count", "after", "onTextChanged", "before", "base_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = Opcodes.ARETURN)
public final class EditTextExtsKt$onTextChanged$$inlined$addTextChanged$default$1 implements TextWatcher {
    final /* synthetic */ Function4 $onChanged;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public EditTextExtsKt$onTextChanged$$inlined$addTextChanged$default$1(Function4 function4) {
        this.$onChanged = function4;
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.$onChanged.invoke(s, Integer.valueOf(start), Integer.valueOf(before), Integer.valueOf(count));
    }
}
