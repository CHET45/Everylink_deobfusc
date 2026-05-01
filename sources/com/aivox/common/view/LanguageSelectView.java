package com.aivox.common.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.base.C0874R;
import com.aivox.base.common.MyEnum;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common.databinding.LanguageSelectViewLayoutBinding;
import com.aivox.common.model.DataHandle;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.LangItemView;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes.dex */
public class LanguageSelectView extends BaseDialogViewWrapper {
    private LanguageSelectViewLayoutBinding mBinding;
    private Context mContext;
    private LanguageAdapter mFromAdapter;
    private final List<MyEnum.TRANSLATE_LANGUAGE> mFromRecentList;
    private boolean mIsFrom;
    private int mLangFromPos;
    private int mLangFromRecentPos;
    private int mLangToPos;
    private int mLangToRecentPos;
    private final List<MyEnum.TRANSLATE_LANGUAGE> mList;
    private LanguageAdapter mToAdapter;
    private final List<MyEnum.TRANSLATE_LANGUAGE> mToRecentList;
    private int mType;

    public interface LangSelectListener {
        void onLangSelected(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2);
    }

    public LanguageSelectView(Context context, int i, boolean z, LangSelectListener langSelectListener) {
        super(context);
        this.mList = new ArrayList();
        this.mFromRecentList = new ArrayList();
        this.mToRecentList = new ArrayList();
        this.mLangFromPos = -1;
        this.mLangToPos = -1;
        this.mLangFromRecentPos = -1;
        this.mLangToRecentPos = -1;
        initView(context, i, z, langSelectListener);
    }

    public LanguageSelectView(Context context, boolean z, LangSelectListener langSelectListener) {
        super(context);
        this.mList = new ArrayList();
        this.mFromRecentList = new ArrayList();
        this.mToRecentList = new ArrayList();
        this.mLangFromPos = -1;
        this.mLangToPos = -1;
        this.mLangFromRecentPos = -1;
        this.mLangToRecentPos = -1;
        this.mIsFrom = z;
        initView(context, 3, false, langSelectListener);
    }

    private void initView(final Context context, int i, boolean z, final LangSelectListener langSelectListener) {
        this.mBinding = LanguageSelectViewLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mType = i;
        this.mContext = context;
        int iIntValue = MyEnum.TRANSLATE_LANGUAGE.NONE.type;
        int iIntValue2 = MyEnum.TRANSLATE_LANGUAGE.NONE.type;
        int iIntValue3 = MyEnum.TRANSLATE_LANGUAGE.NONE.type;
        int iIntValue4 = MyEnum.TRANSLATE_LANGUAGE.NONE.type;
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> arrayList2 = new ArrayList<>();
        if (BaseAppUtils.getMainTransType(this.mType) == 1) {
            iIntValue = ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_TRANSCRIBE_TEMP, -1)).intValue();
            arrayList = getRecentLang(false);
        }
        if (BaseAppUtils.getMainTransType(this.mType) == 2) {
            iIntValue2 = ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_FROM_TEMP, -1)).intValue();
            iIntValue3 = ((Integer) SPUtil.getWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_TO_TEMP, -1)).intValue();
            arrayList = getRecentLang(true);
            arrayList2 = getRecentLang(false);
        }
        if (BaseAppUtils.getMainTransType(this.mType) == 3) {
            iIntValue4 = ((Integer) SPUtil.getWithUid(this.mIsFrom ? SPUtil.PRESET_LANGUAGE_BILINGUAL_FROM : SPUtil.PRESET_LANGUAGE_BILINGUAL_TO, -1)).intValue();
            arrayList = getRecentLang(this.mIsFrom);
        }
        if (CollectionUtils.isNotEmpty(arrayList)) {
            this.mBinding.tvLanguageRecent.setVisibility(0);
            this.mBinding.llLangRecentFrom.setVisibility(0);
            this.mBinding.livFromRecent1.setVisibility(0);
            this.mBinding.livFromRecent1.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList.get(0).intValue()).textRes);
            this.mBinding.livFromRecent1.setPro(MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList.get(0).intValue()).type);
            if (!z && this.mBinding.livFromRecent1.isPro()) {
                this.mBinding.livFromRecent1.setVisibility(8);
            }
            if (arrayList.size() > 1) {
                this.mBinding.livFromRecent2.setVisibility(0);
                this.mBinding.livFromRecent2.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList.get(1).intValue()).textRes);
                this.mBinding.livFromRecent2.setPro(MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList.get(1).intValue()).type);
                if (!z && this.mBinding.livFromRecent2.isPro()) {
                    this.mBinding.livFromRecent2.setVisibility(8);
                }
            } else {
                this.mBinding.livFromRecent2.setVisibility(8);
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                MyEnum.TRANSLATE_LANGUAGE language = MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList.get(i2).intValue());
                if (language != null) {
                    this.mFromRecentList.add(language);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(arrayList2)) {
            this.mBinding.livToRecent1.setVisibility(0);
            this.mBinding.livToRecent1.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList2.get(0).intValue()).textRes);
            if (arrayList2.size() > 1) {
                this.mBinding.livToRecent2.setVisibility(0);
                this.mBinding.livToRecent2.setText(MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList2.get(1).intValue()).textRes);
            } else {
                this.mBinding.livToRecent2.setVisibility(8);
            }
            for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                MyEnum.TRANSLATE_LANGUAGE language2 = MyEnum.TRANSLATE_LANGUAGE.getLanguage(arrayList2.get(i3).intValue());
                if (language2 != null) {
                    this.mToRecentList.add(language2);
                }
            }
        }
        this.mBinding.dtvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2477lambda$initView$0$comaivoxcommonviewLanguageSelectView(view2);
            }
        });
        if (BaseAppUtils.getMainTransType(this.mType) == 2) {
            this.mBinding.clLangSwitch.setVisibility(0);
        }
        this.mList.addAll((Collection) Arrays.stream(MyEnum.TRANSLATE_LANGUAGE.values()).filter(new Predicate() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LanguageSelectView.lambda$initView$1((MyEnum.TRANSLATE_LANGUAGE) obj);
            }
        }).collect(Collectors.toList()));
        this.mFromAdapter = new LanguageAdapter(this.mContext, this.mList);
        this.mToAdapter = new LanguageAdapter(this.mContext, this.mList);
        this.mBinding.rvLangFrom.setAdapter(this.mFromAdapter);
        this.mBinding.rvLangTo.setAdapter(this.mToAdapter);
        this.mBinding.rvLangFrom.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mBinding.rvLangTo.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mBinding.tvLangL.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2479lambda$initView$2$comaivoxcommonviewLanguageSelectView(view2);
            }
        });
        this.mBinding.tvLangR.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2480lambda$initView$3$comaivoxcommonviewLanguageSelectView(view2);
            }
        });
        this.mBinding.livFromRecent1.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2481lambda$initView$4$comaivoxcommonviewLanguageSelectView(view2);
            }
        });
        this.mBinding.livFromRecent2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2482lambda$initView$5$comaivoxcommonviewLanguageSelectView(view2);
            }
        });
        this.mBinding.livToRecent1.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2483lambda$initView$6$comaivoxcommonviewLanguageSelectView(view2);
            }
        });
        this.mBinding.livToRecent2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2484lambda$initView$7$comaivoxcommonviewLanguageSelectView(view2);
            }
        });
        this.mFromAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda1
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i4) {
                this.f$0.m2485lambda$initView$8$comaivoxcommonviewLanguageSelectView(view2, i4);
            }
        });
        this.mToAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda2
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i4) {
                this.f$0.m2486lambda$initView$9$comaivoxcommonviewLanguageSelectView(view2, i4);
            }
        });
        this.mBinding.btnContinue.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2478lambda$initView$11$comaivoxcommonviewLanguageSelectView(context, langSelectListener, view2);
            }
        });
        if (BaseAppUtils.getMainTransType(this.mType) == 1 && iIntValue != -1) {
            MyEnum.TRANSLATE_LANGUAGE language3 = MyEnum.TRANSLATE_LANGUAGE.getLanguage(iIntValue);
            if (this.mFromRecentList.contains(language3)) {
                if (!z && language3.type >= 10000) {
                    this.mLangFromRecentPos = -1;
                } else {
                    int iIndexOf = this.mFromRecentList.indexOf(language3);
                    this.mLangFromRecentPos = iIndexOf;
                    onRecentLangClicked(true, iIndexOf);
                }
            } else {
                int iIndexOf2 = this.mList.indexOf(language3);
                this.mLangFromPos = iIndexOf2;
                this.mFromAdapter.updateSelectPosition(iIndexOf2);
                this.mBinding.tvLangL.setText(this.mList.get(this.mLangFromPos).textRes);
            }
        } else if (BaseAppUtils.getMainTransType(this.mType) == 2 && iIntValue2 != -1) {
            MyEnum.TRANSLATE_LANGUAGE language4 = MyEnum.TRANSLATE_LANGUAGE.getLanguage(iIntValue2);
            if (this.mFromRecentList.contains(language4)) {
                int iIndexOf3 = this.mFromRecentList.indexOf(language4);
                this.mLangFromRecentPos = iIndexOf3;
                onRecentLangClicked(true, iIndexOf3);
            } else {
                int iIndexOf4 = this.mList.indexOf(language4);
                this.mLangFromPos = iIndexOf4;
                this.mFromAdapter.updateSelectPosition(iIndexOf4);
                this.mBinding.tvLangL.setText(this.mList.get(this.mLangFromPos).textRes);
            }
        } else if (BaseAppUtils.getMainTransType(this.mType) == 3 && iIntValue4 != -1) {
            MyEnum.TRANSLATE_LANGUAGE language5 = MyEnum.TRANSLATE_LANGUAGE.getLanguage(iIntValue4);
            if (this.mFromRecentList.contains(language5)) {
                int iIndexOf5 = this.mFromRecentList.indexOf(language5);
                this.mLangFromRecentPos = iIndexOf5;
                onRecentLangClicked(true, iIndexOf5);
            } else {
                int iIndexOf6 = this.mList.indexOf(language5);
                this.mLangFromPos = iIndexOf6;
                this.mFromAdapter.updateSelectPosition(iIndexOf6);
                this.mBinding.tvLangL.setText(this.mList.get(this.mLangFromPos).textRes);
            }
        } else {
            this.mLangFromPos = 1;
            this.mFromAdapter.updateSelectPosition(1);
            this.mBinding.tvLangL.setText(this.mList.get(this.mLangFromPos).textRes);
        }
        int i4 = this.mLangFromRecentPos;
        updateToLangList(i4 == -1 ? this.mLangFromPos : this.mList.indexOf(this.mFromRecentList.get(i4)));
        if (BaseAppUtils.getMainTransType(this.mType) != 2 || iIntValue3 == -1) {
            return;
        }
        MyEnum.TRANSLATE_LANGUAGE language6 = MyEnum.TRANSLATE_LANGUAGE.getLanguage(iIntValue3);
        if (this.mToRecentList.contains(language6)) {
            int iIndexOf7 = this.mToRecentList.indexOf(language6);
            this.mLangToRecentPos = iIndexOf7;
            onRecentLangClicked(false, iIndexOf7);
        } else {
            int iIndexOf8 = this.mList.indexOf(MyEnum.TRANSLATE_LANGUAGE.getLanguage(iIntValue3));
            this.mLangToPos = iIndexOf8;
            this.mToAdapter.updateSelectPosition(iIndexOf8);
            this.mBinding.tvLangR.setText(this.mList.get(this.mLangToPos).textRes);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2477lambda$initView$0$comaivoxcommonviewLanguageSelectView(View view2) {
        this.mDialog.dismiss();
    }

    static /* synthetic */ boolean lambda$initView$1(MyEnum.TRANSLATE_LANGUAGE translate_language) {
        return (translate_language.equals(MyEnum.TRANSLATE_LANGUAGE.NONE) || translate_language.equals(MyEnum.TRANSLATE_LANGUAGE.EN_LOCAL)) ? false : true;
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2479lambda$initView$2$comaivoxcommonviewLanguageSelectView(View view2) {
        updateLanguageUI(true);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2480lambda$initView$3$comaivoxcommonviewLanguageSelectView(View view2) {
        updateLanguageUI(false);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2481lambda$initView$4$comaivoxcommonviewLanguageSelectView(View view2) {
        onRecentLangClicked(true, 0);
        updateToLangList(this.mList.indexOf(this.mFromRecentList.get(0)));
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2482lambda$initView$5$comaivoxcommonviewLanguageSelectView(View view2) {
        onRecentLangClicked(true, 1);
        updateToLangList(this.mList.indexOf(this.mFromRecentList.get(1)));
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2483lambda$initView$6$comaivoxcommonviewLanguageSelectView(View view2) {
        if (this.mBinding.livToRecent1.isEnable()) {
            onRecentLangClicked(false, 0);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2484lambda$initView$7$comaivoxcommonviewLanguageSelectView(View view2) {
        if (this.mBinding.livToRecent2.isEnable()) {
            onRecentLangClicked(false, 1);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$8$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2485lambda$initView$8$comaivoxcommonviewLanguageSelectView(View view2, int i) {
        clearRecentLangStatus(true);
        this.mLangFromPos = i;
        updateToLangList(i);
        this.mBinding.tvLangL.setText(this.mList.get(i).textRes);
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2486lambda$initView$9$comaivoxcommonviewLanguageSelectView(View view2, int i) {
        clearRecentLangStatus(false);
        this.mLangToPos = i;
        this.mBinding.tvLangR.setText(this.mList.get(i).textRes);
    }

    /* JADX INFO: renamed from: lambda$initView$11$com-aivox-common-view-LanguageSelectView, reason: not valid java name */
    /* synthetic */ void m2478lambda$initView$11$comaivoxcommonviewLanguageSelectView(Context context, LangSelectListener langSelectListener, View view2) {
        MyEnum.TRANSLATE_LANGUAGE translate_language = MyEnum.TRANSLATE_LANGUAGE.NONE;
        int i = this.mLangFromRecentPos;
        if (i == -1 && this.mLangFromPos == -1) {
            return;
        }
        MyEnum.TRANSLATE_LANGUAGE translate_language2 = i == -1 ? this.mList.get(this.mLangFromPos) : this.mFromRecentList.get(i);
        if (translate_language2.type >= 10000 && !DataHandle.getIns().isVip()) {
            DialogUtils.showDialogWithDefBtn(context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.usage_limit_exceeded), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.common.view.LanguageSelectView$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context2, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    AppUtils.jumpToPurchase((FragmentActivity) context2, false);
                }
            }, true, true);
            return;
        }
        if (BaseAppUtils.getMainTransType(this.mType) == 2) {
            int i2 = this.mLangToRecentPos;
            translate_language = i2 == -1 ? this.mList.get(this.mLangToPos) : this.mToRecentList.get(i2);
        }
        if (BaseAppUtils.getMainTransType(this.mType) == 1) {
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_TRANSCRIBE_TEMP, Integer.valueOf(translate_language2.type));
            saveRecentLanguages(translate_language2.type, false);
        }
        if (BaseAppUtils.getMainTransType(this.mType) == 2) {
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_FROM_TEMP, Integer.valueOf(translate_language2.type));
            SPUtil.putWithUid(SPUtil.PRESET_LANGUAGE_TRANSLATE_TO_TEMP, Integer.valueOf(translate_language.type));
            saveRecentLanguages(translate_language2.type, true);
            saveRecentLanguages(translate_language.type, false);
        }
        if (BaseAppUtils.getMainTransType(this.mType) == 3) {
            SPUtil.putWithUid(this.mIsFrom ? SPUtil.PRESET_LANGUAGE_BILINGUAL_FROM : SPUtil.PRESET_LANGUAGE_BILINGUAL_TO, Integer.valueOf(translate_language2.type));
            saveRecentLanguages(translate_language2.type, this.mIsFrom);
        }
        LogUtils.m499e(translate_language2.desc, translate_language.desc);
        langSelectListener.onLangSelected(translate_language2, translate_language);
        this.mDialog.dismiss();
    }

    private void updateLanguageUI(boolean z) {
        this.mBinding.llLangRecentFrom.setVisibility((z && CollectionUtils.isNotEmpty(this.mFromRecentList)) ? 0 : 8);
        this.mBinding.llLangRecentTo.setVisibility((z || !CollectionUtils.isNotEmpty(this.mToRecentList)) ? 8 : 0);
        this.mBinding.rvLangFrom.setVisibility(z ? 0 : 8);
        this.mBinding.rvLangTo.setVisibility(z ? 8 : 0);
        this.mBinding.tvLangL.setTextColor(this.mContext.getColor(z ? C0874R.color.txt_color_secondary : C0874R.color.txt_color_primary));
        this.mBinding.tvLangL.setBackgroundResource(z ? C1034R.drawable.bg_lang_selected : C1034R.drawable.bg_lang_unselected);
        this.mBinding.tvLangR.setTextColor(this.mContext.getColor(z ? C0874R.color.txt_color_primary : C0874R.color.txt_color_secondary));
        this.mBinding.tvLangR.setBackgroundResource(z ? C1034R.drawable.bg_lang_unselected : C1034R.drawable.bg_lang_selected);
    }

    private void onRecentLangClicked(boolean z, int i) {
        if (z) {
            this.mLangFromRecentPos = i;
            this.mBinding.livFromRecent1.setChecked(this.mLangFromRecentPos == 0);
            this.mBinding.livFromRecent2.setChecked(this.mLangFromRecentPos == 1);
            this.mLangFromPos = -1;
            this.mFromAdapter.updateSelectPosition(-1);
            this.mBinding.tvLangL.setText(this.mFromRecentList.get(this.mLangFromRecentPos).textRes);
            return;
        }
        this.mLangToRecentPos = i;
        this.mBinding.livToRecent1.setChecked(this.mLangToRecentPos == 0);
        this.mBinding.livToRecent2.setChecked(this.mLangToRecentPos == 1);
        this.mLangToPos = -1;
        this.mToAdapter.updateSelectPosition(-1);
        this.mBinding.tvLangR.setText(this.mToRecentList.get(this.mLangToRecentPos).textRes);
    }

    private void clearRecentLangStatus(boolean z) {
        if (z) {
            this.mLangFromRecentPos = -1;
            this.mBinding.livFromRecent1.setChecked(false);
            this.mBinding.livFromRecent2.setChecked(false);
        } else {
            this.mLangToRecentPos = -1;
            this.mBinding.livToRecent1.setChecked(false);
            this.mBinding.livToRecent2.setChecked(false);
        }
    }

    private void updateToLangList(int i) {
        if (BaseAppUtils.getMainTransType(this.mType) != 2) {
            return;
        }
        clearRecentLangStatus(false);
        this.mToAdapter.updateUnableName(this.mList.get(i).name);
        int i2 = i == 0 ? 1 : 0;
        this.mLangToPos = i2;
        this.mToAdapter.updateSelectPosition(i2);
        this.mBinding.tvLangR.setText(this.mContext.getString(this.mList.get(this.mLangToPos).textRes));
        this.mBinding.livToRecent1.setEnable(true);
        this.mBinding.livToRecent2.setEnable(true);
        if (i < 0) {
            i = 0;
        } else if (i >= this.mList.size()) {
            i = this.mList.size() - 1;
        }
        for (int i3 = 0; i3 < this.mToRecentList.size(); i3++) {
            if (this.mToRecentList.get(i3) == this.mList.get(i)) {
                if (i3 == 0) {
                    this.mBinding.livToRecent1.setEnable(false);
                } else {
                    this.mBinding.livToRecent2.setEnable(false);
                }
            }
        }
    }

    public List<Integer> getRecentLang(boolean z) {
        String str;
        String str2;
        String str3;
        boolean z2 = BaseAppUtils.getMainTransType(this.mType) == 2;
        if (BaseAppUtils.getMainTransType(this.mType) == 3) {
            if (z) {
                str3 = SPUtil.RECENT_LANGUAGE_BILINGUAL_FROM;
            } else {
                str3 = SPUtil.RECENT_LANGUAGE_BILINGUAL_TO;
            }
            str2 = (String) SPUtil.getWithUid(str3, "[]");
        } else {
            if (!z2) {
                str = SPUtil.RECENT_LANGUAGE_TRANSCRIBE;
            } else if (z) {
                str = SPUtil.RECENT_LANGUAGE_FROM;
            } else {
                str = SPUtil.RECENT_LANGUAGE_TO;
            }
            str2 = (String) SPUtil.getWithUid(str, "[]");
        }
        return (List) new Gson().fromJson(str2, new TypeToken<List<Integer>>() { // from class: com.aivox.common.view.LanguageSelectView.1
        }.getType());
    }

    public void saveRecentLanguages(int i, boolean z) {
        String str;
        String str2;
        boolean z2 = BaseAppUtils.getMainTransType(this.mType) == 2;
        boolean z3 = BaseAppUtils.getMainTransType(this.mType) == 3;
        List<Integer> recentLang = getRecentLang(z);
        if (recentLang.contains(Integer.valueOf(i))) {
            return;
        }
        if (recentLang.size() == 2) {
            recentLang.remove(0);
        }
        recentLang.add(Integer.valueOf(i));
        String json = new Gson().toJson(recentLang);
        if (z3) {
            if (z) {
                str2 = SPUtil.RECENT_LANGUAGE_BILINGUAL_FROM;
            } else {
                str2 = SPUtil.RECENT_LANGUAGE_BILINGUAL_TO;
            }
            SPUtil.putWithUid(str2, json);
            return;
        }
        if (!z2) {
            str = SPUtil.RECENT_LANGUAGE_TRANSCRIBE;
        } else if (z) {
            str = SPUtil.RECENT_LANGUAGE_FROM;
        } else {
            str = SPUtil.RECENT_LANGUAGE_TO;
        }
        SPUtil.putWithUid(str, json);
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LanguageAdapter extends RecyclerView.Adapter<LanguageViewHolder> {
        private final Context context;
        private int curIndex = -1;
        private final List<MyEnum.TRANSLATE_LANGUAGE> languageList;
        private OnItemClickListener onItemClickListener;
        private String unableName;

        public LanguageAdapter(Context context, List<MyEnum.TRANSLATE_LANGUAGE> list) {
            this.context = context;
            this.languageList = list;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public LanguageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new LanguageViewHolder(LayoutInflater.from(this.context).inflate(C1034R.layout.item_lang_select_layout, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(final LanguageViewHolder languageViewHolder, final int i) {
            final MyEnum.TRANSLATE_LANGUAGE translate_language = this.languageList.get(i);
            languageViewHolder.langItemView.setText(translate_language.textRes);
            languageViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.LanguageSelectView$LanguageAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m361x2bbf9062(translate_language, languageViewHolder, i, view2);
                }
            });
            languageViewHolder.langItemView.setChecked(i == this.curIndex);
            languageViewHolder.langItemView.setEnable(!Objects.equals(this.unableName, translate_language.name));
            languageViewHolder.langItemView.setPro(this.languageList.get(i).type);
        }

        /* JADX INFO: renamed from: lambda$onBindViewHolder$0$com-aivox-common-view-LanguageSelectView$LanguageAdapter */
        /* synthetic */ void m361x2bbf9062(MyEnum.TRANSLATE_LANGUAGE translate_language, LanguageViewHolder languageViewHolder, int i, View view2) {
            if (this.onItemClickListener == null || Objects.equals(this.unableName, translate_language.name)) {
                return;
            }
            this.onItemClickListener.onItemClick(languageViewHolder.langItemView, i);
            this.curIndex = i;
            notifyDataSetChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.languageList.size();
        }

        public void updateSelectPosition(int i) {
            this.curIndex = i;
            notifyDataSetChanged();
        }

        public void updateUnableName(String str) {
            this.unableName = str;
            notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class LanguageViewHolder extends RecyclerView.ViewHolder {
            private final LangItemView langItemView;

            public LanguageViewHolder(View view2) {
                super(view2);
                this.langItemView = (LangItemView) view2.findViewById(C1034R.id.liv_item);
            }
        }
    }
}
