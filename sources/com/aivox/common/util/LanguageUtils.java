package com.aivox.common.util;

import android.content.Context;
import com.aivox.base.common.MyEnum;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.common.http.UserService;
import com.aivox.common.view.LanguageSelectView;

/* JADX INFO: loaded from: classes.dex */
public class LanguageUtils {
    public static final int TYPE_BILINGUAL_FROM = 3;
    public static final int TYPE_BILINGUAL_TO = 4;
    public static final int TYPE_TRANSCRIBE = 2;
    public static final int TYPE_TRANSLATE_FROM = 0;
    public static final int TYPE_TRANSLATE_TO = 1;

    public static void showLangSelectView(Context context, int i, boolean z, LanguageSelectView.LangSelectListener langSelectListener) {
        DialogUtils.showBottomSheetDialog(context, new LanguageSelectView(context, i, z, langSelectListener));
    }

    public static void showLangSelectView(Context context, boolean z, LanguageSelectView.LangSelectListener langSelectListener) {
        DialogUtils.showBottomSheetDialog(context, new LanguageSelectView(context, z, langSelectListener));
    }

    public static void setDefaultLang(Context context, int i, int i2, int i3) {
        setDefaultLangToLocal(i, i2, i3);
        int mainTransType = BaseAppUtils.getMainTransType(i);
        if (mainTransType == 1) {
            new UserService(context).setAsrLanguage(i2).subscribe();
        } else {
            if (mainTransType != 2) {
                return;
            }
            new UserService(context).setTranslateLanguage(i2, i3).subscribe();
        }
    }

    public static void setDefaultLangToLocal(int i, int i2, int i3) {
        int mainTransType = BaseAppUtils.getMainTransType(i);
        if (mainTransType == 1) {
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_TRANSCRIBE, Integer.valueOf(i2));
            return;
        }
        if (mainTransType == 2) {
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_FROM, Integer.valueOf(i2));
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_TO, Integer.valueOf(i3));
        } else {
            if (mainTransType != 3) {
                return;
            }
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_BILINGUAL_FROM, Integer.valueOf(i2));
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_BILINGUAL_TO, Integer.valueOf(i3));
        }
    }

    public static int getDefaultLangFromLocal(int i) {
        int i2 = MyEnum.TRANSLATE_LANGUAGE.NONE.type;
        if (i == 0) {
            return ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_FROM, Integer.valueOf(MyEnum.TRANSLATE_LANGUAGE.NONE.type))).intValue();
        }
        if (i == 1) {
            return ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_TO, Integer.valueOf(MyEnum.TRANSLATE_LANGUAGE.NONE.type))).intValue();
        }
        if (i == 2) {
            return ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_TRANSCRIBE, Integer.valueOf(MyEnum.TRANSLATE_LANGUAGE.NONE.type))).intValue();
        }
        if (i != 3) {
            return i != 4 ? i2 : ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_BILINGUAL_TO, Integer.valueOf(MyEnum.TRANSLATE_LANGUAGE.EN.type))).intValue();
        }
        return ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_BILINGUAL_FROM, Integer.valueOf(MyEnum.TRANSLATE_LANGUAGE.ZH.type))).intValue();
    }
}
