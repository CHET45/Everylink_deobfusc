package com.aivox.common_ui;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aivox.base.C0874R;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common_ui.databinding.BottomEditDialogLayoutBinding;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BottomEditDialogView extends BaseDialogViewWrapper {
    public static final int TYPE_COMMON = 1;
    public static final int TYPE_SPEAKER_EDIT = 2;
    private final BottomEditDialogLayoutBinding mBinding;
    private final Context mContext;
    private int mDialogType;
    private ExtraInteractionListener mExtraListener;
    private String mOriContent;
    private final List<SpeakerStyleBean> mSpeakerStyleList;

    public interface ExtraInteractionListener {
        void onDataChange(Object obj);
    }

    public interface OnBtnClickListener {
        void onLeftBtnClick();

        void onSaveBtnClick(String str);
    }

    public BottomEditDialogView(Context context, OnBtnClickListener onBtnClickListener) {
        this(context, 1, 25, false, onBtnClickListener);
    }

    public BottomEditDialogView(Context context, int i, int i2, boolean z, final OnBtnClickListener onBtnClickListener) {
        super(context);
        this.mSpeakerStyleList = new ArrayList();
        this.mDialogType = 1;
        this.mContext = context;
        BottomEditDialogLayoutBinding bottomEditDialogLayoutBindingInflate = BottomEditDialogLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        this.mBinding = bottomEditDialogLayoutBindingInflate;
        bottomEditDialogLayoutBindingInflate.dtvTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.BottomEditDialogView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2490lambda$new$0$comaivoxcommon_uiBottomEditDialogView(view2);
            }
        });
        bottomEditDialogLayoutBindingInflate.btnLeft.setVisibility(z ? 0 : 8);
        bottomEditDialogLayoutBindingInflate.btnLeft.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.BottomEditDialogView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2491lambda$new$1$comaivoxcommon_uiBottomEditDialogView(onBtnClickListener, view2);
            }
        });
        bottomEditDialogLayoutBindingInflate.btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.BottomEditDialogView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2492lambda$new$2$comaivoxcommon_uiBottomEditDialogView(onBtnClickListener, view2);
            }
        });
        bottomEditDialogLayoutBindingInflate.ivContentClear.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.BottomEditDialogView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2493lambda$new$3$comaivoxcommon_uiBottomEditDialogView(view2);
            }
        });
        bottomEditDialogLayoutBindingInflate.etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i2)});
        bottomEditDialogLayoutBindingInflate.etContent.setMaxLines(i);
        bottomEditDialogLayoutBindingInflate.etContent.setImeOptions(i <= 1 ? 6 : 0);
        KeyboardUtils.showSoftInput(bottomEditDialogLayoutBindingInflate.etContent);
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common_ui-BottomEditDialogView, reason: not valid java name */
    /* synthetic */ void m2490lambda$new$0$comaivoxcommon_uiBottomEditDialogView(View view2) {
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common_ui-BottomEditDialogView, reason: not valid java name */
    /* synthetic */ void m2491lambda$new$1$comaivoxcommon_uiBottomEditDialogView(OnBtnClickListener onBtnClickListener, View view2) {
        this.mDialog.dismiss();
        onBtnClickListener.onLeftBtnClick();
    }

    /* JADX INFO: renamed from: lambda$new$2$com-aivox-common_ui-BottomEditDialogView, reason: not valid java name */
    /* synthetic */ void m2492lambda$new$2$comaivoxcommon_uiBottomEditDialogView(OnBtnClickListener onBtnClickListener, View view2) {
        KeyboardUtils.hideSoftInput(this.mBinding.etContent);
        this.mDialog.dismiss();
        String strTrim = this.mBinding.etContent.getText().toString().trim();
        if (BaseStringUtil.isEmpty(strTrim)) {
            strTrim = this.mOriContent;
        }
        onBtnClickListener.onSaveBtnClick(strTrim);
        if (this.mDialogType == 2) {
            int avatarStyle = 0;
            for (SpeakerStyleBean speakerStyleBean : this.mSpeakerStyleList) {
                if (speakerStyleBean.isSelected()) {
                    avatarStyle = speakerStyleBean.getAvatarStyle();
                }
            }
            ExtraInteractionListener extraInteractionListener = this.mExtraListener;
            if (extraInteractionListener != null) {
                extraInteractionListener.onDataChange(Integer.valueOf(avatarStyle));
            }
        }
    }

    /* JADX INFO: renamed from: lambda$new$3$com-aivox-common_ui-BottomEditDialogView, reason: not valid java name */
    /* synthetic */ void m2493lambda$new$3$comaivoxcommon_uiBottomEditDialogView(View view2) {
        this.mBinding.etContent.setText("");
    }

    public void setDialogContent(String str, String str2, String str3, String str4, String str5) {
        this.mOriContent = str4;
        this.mBinding.dtvTitle.setTitle(str);
        this.mBinding.dtivItemTitle.setText(str2);
        this.mBinding.etContent.setHint(str3);
        this.mBinding.etContent.setText(str4);
        this.mBinding.etContent.setSelection(str4.length());
        if (BaseStringUtil.isNotEmpty(str5)) {
            this.mBinding.btnLeft.setText(str5);
        }
    }

    public void showTopBtn(final View.OnClickListener onClickListener) {
        this.mBinding.ivTop.setVisibility(0);
        this.mBinding.ivTop.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.BottomEditDialogView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2494lambda$showTopBtn$4$comaivoxcommon_uiBottomEditDialogView(onClickListener, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showTopBtn$4$com-aivox-common_ui-BottomEditDialogView, reason: not valid java name */
    /* synthetic */ void m2494lambda$showTopBtn$4$comaivoxcommon_uiBottomEditDialogView(View.OnClickListener onClickListener, View view2) {
        this.mBinding.ivTop.setVisibility(8);
        onClickListener.onClick(view2);
    }

    public void setDialogType(int i, int i2, ExtraInteractionListener extraInteractionListener) {
        this.mExtraListener = extraInteractionListener;
        this.mDialogType = i;
        if (i != 2) {
            return;
        }
        int[] iArr = {C0874R.string.avatar_style_default, C0874R.string.avatar_style_illustration, C0874R.string.avatar_style_animal};
        this.mBinding.dtivItemType.setVisibility(0);
        this.mBinding.hsvTypeItem.setVisibility(0);
        this.mBinding.dtivItemType.setText(this.mContext.getString(C0874R.string.title_avatars));
        int i3 = 0;
        while (true) {
            boolean z = true;
            if (i3 < 3) {
                SpeakerStyleBean speakerStyleBean = new SpeakerStyleBean();
                if (i3 != i2 - 1) {
                    z = false;
                }
                speakerStyleBean.setSelected(z);
                int i4 = i3 + 1;
                speakerStyleBean.setAvatarStyle(i4);
                speakerStyleBean.setStyleNameRes(iArr[i3]);
                this.mSpeakerStyleList.add(speakerStyleBean);
                i3 = i4;
            } else {
                onAvatarTypeChanged(i2 - 1);
                return;
            }
        }
    }

    private void onAvatarTypeChanged(int i) {
        this.mBinding.llTypeItem.removeAllViews();
        int i2 = 0;
        while (i2 < this.mSpeakerStyleList.size()) {
            SpeakerStyleBean speakerStyleBean = this.mSpeakerStyleList.get(i2);
            speakerStyleBean.setSelected(i == i2);
            TextView textView = new TextView(this.mContext);
            textView.setText(speakerStyleBean.getStyleNameRes());
            textView.setPadding(SizeUtils.dp2px(16.0f), SizeUtils.dp2px(10.0f), SizeUtils.dp2px(16.0f), SizeUtils.dp2px(10.0f));
            textView.setTag(Integer.valueOf(i2));
            textView.setTextColor(this.mContext.getColor(speakerStyleBean.selected ? C0874R.color.txt_color_secondary : C0874R.color.txt_color_tertiary));
            textView.setBackgroundResource(speakerStyleBean.selected ? C1034R.drawable.bg_tab_selected : C1034R.drawable.bg_tab_unselected);
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.BottomEditDialogView$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m362xa290ecd7(view2);
                }
            });
            this.mBinding.llTypeItem.addView(textView);
            ((LinearLayout.LayoutParams) textView.getLayoutParams()).setMarginEnd(SizeUtils.dp2px(8.0f));
            i2++;
        }
    }

    /* JADX INFO: renamed from: lambda$onAvatarTypeChanged$5$com-aivox-common_ui-BottomEditDialogView */
    /* synthetic */ void m362xa290ecd7(View view2) {
        onAvatarTypeChanged(((Integer) view2.getTag()).intValue());
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    private static class SpeakerStyleBean {
        private int avatarStyle;
        private boolean selected;
        private int styleNameRes;

        public boolean isSelected() {
            return this.selected;
        }

        public void setSelected(boolean z) {
            this.selected = z;
        }

        public int getAvatarStyle() {
            return this.avatarStyle;
        }

        public void setAvatarStyle(int i) {
            this.avatarStyle = i;
        }

        public int getStyleNameRes() {
            return this.styleNameRes;
        }

        public void setStyleNameRes(int i) {
            this.styleNameRes = i;
        }
    }
}
