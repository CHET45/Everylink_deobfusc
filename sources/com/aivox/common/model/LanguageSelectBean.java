package com.aivox.common.model;

import com.aivox.base.common.MyEnum;

/* JADX INFO: loaded from: classes.dex */
public class LanguageSelectBean {
    private boolean enable;
    private MyEnum.TRANSLATE_LANGUAGE language;
    private boolean selected;

    public LanguageSelectBean(MyEnum.TRANSLATE_LANGUAGE translate_language) {
        this(translate_language, true, false);
    }

    public LanguageSelectBean(MyEnum.TRANSLATE_LANGUAGE translate_language, boolean z, boolean z2) {
        this.language = translate_language;
        this.enable = z;
        this.selected = z2;
    }

    public MyEnum.TRANSLATE_LANGUAGE getLanguage() {
        return this.language;
    }

    public void setLanguage(MyEnum.TRANSLATE_LANGUAGE translate_language) {
        this.language = translate_language;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean z) {
        this.enable = z;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }
}
