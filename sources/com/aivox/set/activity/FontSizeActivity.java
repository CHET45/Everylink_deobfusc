package com.aivox.set.activity;

import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.model.EventBean;
import com.aivox.common_ui.GearSeekBar;
import com.aivox.set.C1106R;
import com.aivox.set.databinding.ActivityFontSizeBinding;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class FontSizeActivity extends BaseFragmentActivity {
    public static final float DEFAULT_FONT_SIZE = 17.0f;
    public static final float MAX_FONT_SIZE = 22.0f;
    public static final float MID_FONT_SIZE = 20.0f;
    public static final float MIN_FONT_SIZE = 13.0f;
    private ActivityFontSizeBinding mBinding;
    private float mFontSize;

    private float getFontSizeSpByGear(int i) {
        if (i == 0) {
            return 13.0f;
        }
        if (i != 2) {
            return i != 3 ? 17.0f : 22.0f;
        }
        return 20.0f;
    }

    private int getGearByFontSize(float f) {
        if (f == 13.0f) {
            return 0;
        }
        if (f == 20.0f) {
            return 2;
        }
        return f == 22.0f ? 3 : 1;
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityFontSizeBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_font_size);
        this.mFontSize = ((Float) SPUtil.get(SPUtil.AUDIO_FONT_SIZE, Float.valueOf(17.0f))).floatValue();
        this.mBinding.gsbFontSize.setGear(getGearByFontSize(this.mFontSize), false);
        this.mBinding.gsbFontSize.setOnGearChangedListener(new GearSeekBar.OnGearChangedListener() { // from class: com.aivox.set.activity.FontSizeActivity$$ExternalSyntheticLambda0
            @Override // com.aivox.common_ui.GearSeekBar.OnGearChangedListener
            public final void onGearChanged(int i) {
                this.f$0.m2548lambda$initView$0$comaivoxsetactivityFontSizeActivity(i);
            }
        });
        this.mBinding.btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.FontSizeActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2549lambda$initView$1$comaivoxsetactivityFontSizeActivity(view2);
            }
        });
        refreshSize(this.mFontSize);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-set-activity-FontSizeActivity, reason: not valid java name */
    /* synthetic */ void m2548lambda$initView$0$comaivoxsetactivityFontSizeActivity(int i) {
        float fontSizeSpByGear = getFontSizeSpByGear(i);
        this.mFontSize = fontSizeSpByGear;
        refreshSize(fontSizeSpByGear);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-set-activity-FontSizeActivity, reason: not valid java name */
    /* synthetic */ void m2549lambda$initView$1$comaivoxsetactivityFontSizeActivity(View view2) {
        SPUtil.put(SPUtil.AUDIO_FONT_SIZE, Float.valueOf(this.mFontSize));
        EventBus.getDefault().post(new EventBean(84));
        finish();
    }

    private void refreshSize(float f) {
        this.mBinding.tvContent1.setTextSize(f);
        this.mBinding.tvContent2.setTextSize(f);
        this.mBinding.tvContent3.setTextSize(f);
    }
}
