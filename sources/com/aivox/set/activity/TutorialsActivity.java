package com.aivox.set.activity;

import android.view.View;
import androidx.databinding.DataBindingUtil;
import com.aivox.common.MyPageAdapter;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common_ui.C1034R;
import com.aivox.set.C1106R;
import com.aivox.set.databinding.ActivityTutorialsBinding;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TutorialsActivity extends BaseFragmentActivity {
    private ActivityTutorialsBinding mBinding;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityTutorialsBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_tutorials);
        final ArrayList arrayList = new ArrayList();
        this.mBinding.stvMain.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.TutorialsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2559lambda$initView$0$comaivoxsetactivityTutorialsActivity(arrayList, view2);
            }
        });
        this.mBinding.stvFiles.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.TutorialsActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2560lambda$initView$1$comaivoxsetactivityTutorialsActivity(arrayList, view2);
            }
        });
        this.mBinding.stvBilingual.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.TutorialsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2561lambda$initView$2$comaivoxsetactivityTutorialsActivity(arrayList, view2);
            }
        });
        this.mBinding.viewSkip.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.TutorialsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2562lambda$initView$3$comaivoxsetactivityTutorialsActivity(view2);
            }
        });
        this.mBinding.viewPrev.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.TutorialsActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2563lambda$initView$4$comaivoxsetactivityTutorialsActivity(view2);
            }
        });
        this.mBinding.viewNext.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.TutorialsActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2564lambda$initView$5$comaivoxsetactivityTutorialsActivity(arrayList, view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-set-activity-TutorialsActivity, reason: not valid java name */
    /* synthetic */ void m2559lambda$initView$0$comaivoxsetactivityTutorialsActivity(List list, View view2) {
        list.clear();
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_home_1));
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_home_2));
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_home_3));
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_home_4));
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_home_5));
        this.mBinding.vpGuide.setAdapter(new MyPageAdapter(this.context, list));
        this.mBinding.gpGuide.setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-set-activity-TutorialsActivity, reason: not valid java name */
    /* synthetic */ void m2560lambda$initView$1$comaivoxsetactivityTutorialsActivity(List list, View view2) {
        list.clear();
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_record_1));
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_record_2));
        this.mBinding.vpGuide.setAdapter(new MyPageAdapter(this.context, list));
        this.mBinding.gpGuide.setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-set-activity-TutorialsActivity, reason: not valid java name */
    /* synthetic */ void m2561lambda$initView$2$comaivoxsetactivityTutorialsActivity(List list, View view2) {
        list.clear();
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_chat_1));
        list.add(Integer.valueOf(C1034R.drawable.pic_use_guide_chat_2));
        this.mBinding.vpGuide.setAdapter(new MyPageAdapter(this.context, list));
        this.mBinding.gpGuide.setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-set-activity-TutorialsActivity, reason: not valid java name */
    /* synthetic */ void m2562lambda$initView$3$comaivoxsetactivityTutorialsActivity(View view2) {
        this.mBinding.gpGuide.setVisibility(8);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-set-activity-TutorialsActivity, reason: not valid java name */
    /* synthetic */ void m2563lambda$initView$4$comaivoxsetactivityTutorialsActivity(View view2) {
        if (this.mBinding.vpGuide.getCurrentItem() > 0) {
            this.mBinding.vpGuide.setCurrentItem(this.mBinding.vpGuide.getCurrentItem() - 1, true);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-set-activity-TutorialsActivity, reason: not valid java name */
    /* synthetic */ void m2564lambda$initView$5$comaivoxsetactivityTutorialsActivity(List list, View view2) {
        if (this.mBinding.vpGuide.getCurrentItem() == list.size() - 1) {
            this.mBinding.gpGuide.setVisibility(8);
        } else {
            this.mBinding.vpGuide.setCurrentItem(this.mBinding.vpGuide.getCurrentItem() + 1, true);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mBinding.gpGuide.getVisibility() == 0) {
            this.mBinding.gpGuide.setVisibility(8);
        } else {
            super.onBackPressed();
        }
    }
}
