package com.aivox.base.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;
import android.util.Log;
import com.aivox.base.C0874R;
import com.aivox.base.util.LogUtil;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class MultiLanguageUtil {
    public static final String SAVE_LANGUAGE = "save_language";
    private static final String TAG = "MultiLanguageUtil";
    private static MultiLanguageUtil instance;
    private Context mContext;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil(context);
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        MultiLanguageUtil multiLanguageUtil = instance;
        if (multiLanguageUtil != null) {
            return multiLanguageUtil;
        }
        throw new IllegalStateException("You must be init MultiLanguageUtil first");
    }

    private MultiLanguageUtil(Context context) {
        this.mContext = context;
    }

    public void setConfiguration() {
        Locale languageLocale = getLanguageLocale();
        LogUtil.m338i("setConfiguration__" + languageLocale.getLanguage() + PunctuationConst.UNDERLINE + languageLocale.getCountry());
        Configuration configuration = this.mContext.getResources().getConfiguration();
        configuration.setLocale(languageLocale);
        Resources resources = this.mContext.getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public Locale getLanguageLocale() {
        int i = CommSharedUtil.getInstance(this.mContext).getInt(SAVE_LANGUAGE, 0);
        if (i == 0) {
            return getSysLocale();
        }
        if (i == 1) {
            return Locale.ENGLISH;
        }
        if (i == 2) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        if (i == 3) {
            return Locale.TRADITIONAL_CHINESE;
        }
        if (i == 4) {
            return Locale.JAPAN;
        }
        getSystemLanguage(getSysLocale());
        return Locale.ENGLISH;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + PunctuationConst.UNDERLINE + locale.getCountry();
    }

    public Locale getSysLocale() {
        return LocaleList.getDefault().get(0);
    }

    public String getLanguage() {
        Locale languageLocale = getInstance().getLanguageLocale();
        String language = languageLocale.getLanguage();
        LogUtil.m336e("LANGUAGE:" + language);
        if (language.equalsIgnoreCase("zh") && !languageLocale.getCountry().equalsIgnoreCase("cn")) {
            return "zh-Hant";
        }
        if (language.equalsIgnoreCase("en")) {
            return "en";
        }
        if (language.equalsIgnoreCase("ja")) {
            return "ja";
        }
        if (language.equalsIgnoreCase("ko")) {
            return "ko";
        }
        if (language.equalsIgnoreCase("vi")) {
            return "vi";
        }
        if (language.equalsIgnoreCase("th")) {
            return "th";
        }
        return "zh-Hans";
    }

    public void updateLanguage(int i) {
        CommSharedUtil.getInstance(this.mContext).putInt(SAVE_LANGUAGE, i);
        getInstance().setConfiguration();
        EventBus.getDefault().post(new OnChangeLanguageEvent(i));
    }

    public String getLanguageName(Context context) {
        int i = CommSharedUtil.getInstance(context).getInt(SAVE_LANGUAGE, 0);
        if (i == 1) {
            return this.mContext.getString(C0874R.string.language_english);
        }
        if (i == 2) {
            return this.mContext.getString(C0874R.string.language_simplified_chinese);
        }
        if (i == 3) {
            return this.mContext.getString(C0874R.string.language_traditional_chinese);
        }
        if (i == 4) {
            return this.mContext.getString(C0874R.string.language_japanese);
        }
        return this.mContext.getString(C0874R.string.language_system);
    }

    public int getLanguageType() {
        int i = CommSharedUtil.getInstance(this.mContext).getInt(SAVE_LANGUAGE, 2);
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 3;
        }
        if (i == 0) {
            return 0;
        }
        if (i == 4) {
            return 4;
        }
        Log.e(TAG, "getLanguageType" + i);
        return i;
    }

    public static Context attachBaseContext(Context context) {
        return createConfigurationResources(context);
    }

    private static Context createConfigurationResources(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(getInstance().getLanguageLocale());
        return context.createConfigurationContext(configuration);
    }
}
