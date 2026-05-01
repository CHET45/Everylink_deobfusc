package com.aivox.common_ui.update;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.EditText;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.microsoft.azure.storage.Constants;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class UpdateManager {
    private final Context mContext;
    private final String PACKAGE_NAME = "com.aivox.smalink";
    private final String versionName = BaseAppUtils.getVersionName();
    private final int versionCode = BaseAppUtils.getVersionCode();

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    public void simpleCheck(UpdateBean updateBean) {
        if (this.mContext == null) {
            return;
        }
        final boolean zCheckMax = updateBean.getMust().intValue() == 1;
        if (!zCheckMax) {
            zCheckMax = checkMax(updateBean.getMustVersion());
        }
        if (isUpdate(updateBean.getNewVersion())) {
            DialogUtils.showDialogWithBtnIds(this.mContext, Integer.valueOf(C0874R.string.found_a_new_version), Integer.valueOf(C0874R.string.new_version_msg), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common_ui.update.UpdateManager$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2515lambda$simpleCheck$0$comaivoxcommon_uiupdateUpdateManager(zCheckMax, context, dialogBuilder, dialog, i, i2, editText);
                }
            }, !zCheckMax, !zCheckMax, C0874R.string.cancel, C0874R.string.sure);
        }
    }

    /* JADX INFO: renamed from: lambda$simpleCheck$0$com-aivox-common_ui-update-UpdateManager, reason: not valid java name */
    /* synthetic */ void m2515lambda$simpleCheck$0$comaivoxcommon_uiupdateUpdateManager(boolean z, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        BaseAppUtils.startBrowser(this.mContext, Constant.GOOGLE_PLAY_URL);
        if (z) {
            System.exit(0);
        }
    }

    public boolean isUpdate(Object obj) {
        LogUtil.m338i("=isUpdate=" + this.versionCode);
        if (!checkMax(obj)) {
            return false;
        }
        LogUtil.m338i(Constants.TRUE);
        return true;
    }

    private boolean checkMax(Object obj) {
        if (!(obj instanceof String)) {
            return (obj instanceof Integer) && ((Integer) obj).intValue() > this.versionCode;
        }
        String str = (String) obj;
        if (BaseStringUtil.isEmpty(str)) {
            return false;
        }
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = this.versionName.split("\\.");
        for (int i = 0; i < strArrSplit.length; i++) {
            if (Integer.parseInt(strArrSplit[i]) > Integer.parseInt(strArrSplit2[i])) {
                LogUtil.m338i("s1:" + strArrSplit[i] + ";s2:" + strArrSplit2[i]);
                return true;
            }
            if (Integer.parseInt(strArrSplit[i]) < Integer.parseInt(strArrSplit2[i])) {
                return false;
            }
        }
        return false;
    }

    public boolean isConstraint(Object obj) {
        LogUtil.m338i("=isConstraint=");
        if (!checkMax(obj)) {
            return false;
        }
        LogUtil.m338i(Constants.TRUE);
        return true;
    }

    public int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.aivox.smalink", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            BaseAppUtils.printErrorMsg(e);
            return 0;
        }
    }

    public String getLanguage() {
        Locale languageLocale = MultiLanguageUtil.getInstance().getLanguageLocale();
        String language = languageLocale.getLanguage();
        if (!language.equalsIgnoreCase("zh") || languageLocale.getCountry().equalsIgnoreCase("cn")) {
            return (language.equalsIgnoreCase("zh") || language.equalsIgnoreCase("en") || language.equalsIgnoreCase("jp")) ? language : "zh";
        }
        return "tc";
    }
}
