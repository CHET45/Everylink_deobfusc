package com.aivox.set.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import com.aivox.base.C0874R;
import com.aivox.base.app.AppManager;
import com.aivox.base.common.Constant;
import com.aivox.base.permission.PermissionCallback;
import com.aivox.base.permission.PermissionUtils;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.router.action.SetAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.FileUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.RegexUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.SaveLogHelper;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.UserService;
import com.aivox.common.http.oss.CommonUploadManager;
import com.aivox.common.http.oss.OnUploadListener;
import com.aivox.common.model.IssuesTagBean;
import com.aivox.common.model.UploadFileBean;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.util.GlideEngine;
import com.aivox.common_ui.C1034R;
import com.aivox.set.C1106R;
import com.aivox.set.adapter.FileSelectAdapter;
import com.aivox.set.databinding.ActivityContactUsBinding;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

/* JADX INFO: loaded from: classes.dex */
public class ContactUsActivity extends BaseFragmentActivity {
    private FileSelectAdapter mAdapter;
    private ActivityContactUsBinding mBinding;
    private String mContactEmail;
    private LayoutInflater mInflater;
    private UserInfo mUserInfo;
    private final ArrayList<Integer> mTagList = new ArrayList<>();
    private final ArrayList<UploadFileBean> mSelectFiles = new ArrayList<>();
    private final ArrayList<String> mPathNeedUpload = new ArrayList<>();
    private int mCurStep = 1;
    private int mUploadFileNum = 0;

    static /* synthetic */ int access$308(ContactUsActivity contactUsActivity) {
        int i = contactUsActivity.mUploadFileNum;
        contactUsActivity.mUploadFileNum = i + 1;
        return i;
    }

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityContactUsBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_contact_us);
        this.mInflater = LayoutInflater.from(this.context);
        this.mUserInfo = new SQLiteDataBaseManager(this).getUserInfo();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mCurStep = extras.getInt(Constant.KEY_INDEX, 1);
            this.mContactEmail = extras.getString("email");
            ArrayList<Integer> integerArrayList = extras.getIntegerArrayList(Constant.KEY_IDS);
            if (CollectionUtils.isNotEmpty(integerArrayList)) {
                this.mTagList.addAll(integerArrayList);
            }
        }
        KeyboardUtils.registerSoftInputChangedListener(getWindow(), new KeyboardUtils.OnSoftInputChangedListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda0
            @Override // com.blankj.utilcode.util.KeyboardUtils.OnSoftInputChangedListener
            public final void onSoftInputChanged(int i) {
                this.f$0.m2528lambda$initView$1$comaivoxsetactivityContactUsActivity(i);
            }
        });
        int i = this.mCurStep;
        if (i == 1) {
            this.mBinding.llStep1.setVisibility(0);
            final String str = (String) SPUtil.get(SPUtil.LOGIN_ACCOUNT, "");
            this.mBinding.tvAccount.setText(str);
            this.mBinding.etEmail.addTextChangedListener(new TextWatcher() { // from class: com.aivox.set.activity.ContactUsActivity.1
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    int i2;
                    int i3;
                    boolean zIsEmail = RegexUtil.isEmail(editable.toString());
                    boolean zEndsWith = editable.toString().endsWith("privaterelay.appleid.com");
                    ContactUsActivity.this.mBinding.btnNext.setEnabled(zIsEmail && !zEndsWith);
                    ContactUsActivity.this.mBinding.ivFormatNotice.setVisibility(zIsEmail ? 8 : 0);
                    ContactUsActivity.this.mBinding.tvWarning.setVisibility((!zIsEmail || zEndsWith) ? 0 : 8);
                    if (!zIsEmail) {
                        ContactUsActivity.this.mBinding.tvWarning.setText(C0874R.string.account_email_set_notice);
                    }
                    if (zEndsWith) {
                        ContactUsActivity.this.mBinding.tvWarning.setText(C0874R.string.account_email_private_notice);
                    }
                    ContactUsActivity.this.mBinding.cbEmail.setChecked(editable.toString().equals(ContactUsActivity.this.mBinding.tvAccount.getText()));
                    EditText editText = ContactUsActivity.this.mBinding.etEmail;
                    Resources resources = ContactUsActivity.this.getResources();
                    if (editable.toString().equals(ContactUsActivity.this.mBinding.tvAccount.getText())) {
                        i2 = C0874R.color.txt_color_highlight;
                    } else {
                        i2 = C0874R.color.txt_color_primary;
                    }
                    editText.setTextColor(resources.getColor(i2));
                    LinearLayout linearLayout = ContactUsActivity.this.mBinding.llEmail;
                    if (!zIsEmail || zEndsWith) {
                        i3 = C1034R.drawable.bg_gary_input_notice_c20;
                    } else {
                        i3 = C1034R.drawable.bg_gary_input_focused_c20;
                    }
                    linearLayout.setBackgroundResource(i3);
                    String string = ContactUsActivity.this.mBinding.tvEmail.getText().toString();
                    if (!zIsEmail || zEndsWith) {
                        if (string.endsWith("*")) {
                            return;
                        }
                        ContactUsActivity.this.mBinding.tvEmail.setText(string + "*");
                    } else if (string.endsWith("*")) {
                        ContactUsActivity.this.mBinding.tvEmail.setText(string.substring(0, string.length() - 1));
                    }
                }
            });
            this.mBinding.etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda18
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    this.f$0.m2537lambda$initView$2$comaivoxsetactivityContactUsActivity(view2, z);
                }
            });
            this.mBinding.llQuickSet.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda19
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2538lambda$initView$3$comaivoxsetactivityContactUsActivity(str, view2);
                }
            });
            this.mBinding.btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2539lambda$initView$4$comaivoxsetactivityContactUsActivity(view2);
                }
            });
            return;
        }
        if (i == 2) {
            this.mBinding.llStep2.setVisibility(0);
            this.mBinding.titleView.setRightIconVisible(0);
            this.mBinding.titleView.setRightIconListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2540lambda$initView$5$comaivoxsetactivityContactUsActivity(view2);
                }
            });
            this.mBinding.tvProgress.setText("66%");
            this.mBinding.progress.setProgress(66);
            this.mBinding.tvStepMsg.setText(C0874R.string.contact_us_step_2);
            new UserService(this.context).getIssuesTagList().doFinally(new Action() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda3
                @Override // io.reactivex.functions.Action
                public final void run() throws Exception {
                    this.f$0.m2541lambda$initView$6$comaivoxsetactivityContactUsActivity();
                }
            }).subscribe(new Consumer() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2542lambda$initView$7$comaivoxsetactivityContactUsActivity((List) obj);
                }
            }, new Consumer() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2543lambda$initView$8$comaivoxsetactivityContactUsActivity((Throwable) obj);
                }
            });
            this.mBinding.tagFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda6
                @Override // com.zhy.view.flowlayout.TagFlowLayout.OnTagClickListener
                public final boolean onTagClick(View view2, int i2, FlowLayout flowLayout) {
                    return this.f$0.m2544lambda$initView$9$comaivoxsetactivityContactUsActivity(view2, i2, flowLayout);
                }
            });
            this.mBinding.tagFlow.setOnSelectListener(new TagFlowLayout.OnSelectListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda7
                @Override // com.zhy.view.flowlayout.TagFlowLayout.OnSelectListener
                public final void onSelected(Set set) {
                    this.f$0.m2529lambda$initView$10$comaivoxsetactivityContactUsActivity(set);
                }
            });
            this.mBinding.btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2530lambda$initView$11$comaivoxsetactivityContactUsActivity(view2);
                }
            });
            return;
        }
        if (i != 3) {
            if (i != 4) {
                return;
            }
            this.mBinding.titleView.hideBackBtn();
            this.mBinding.tvProgress.setVisibility(8);
            this.mBinding.progress.setVisibility(8);
            this.mBinding.ivSubmitSuccess.setVisibility(0);
            this.mBinding.tvSubmitSuccess.setVisibility(0);
            this.mBinding.tvStepMsg.setText(C0874R.string.contact_us_step_4);
            this.mBinding.btnNext.setEnabled(true);
            this.mBinding.btnNext.setText(getString(C0874R.string.finish));
            this.mBinding.btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda17
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m2536lambda$initView$17$comaivoxsetactivityContactUsActivity(view2);
                }
            });
            return;
        }
        this.mBinding.svStep3.setVisibility(0);
        this.mBinding.titleView.setRightIconVisible(0);
        this.mBinding.titleView.setRightIconListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2531lambda$initView$12$comaivoxsetactivityContactUsActivity(view2);
            }
        });
        this.mBinding.progress.setProgress(100);
        this.mBinding.tvProgress.setText("100%");
        this.mBinding.tvStepMsg.setText(C0874R.string.contact_us_step_3);
        this.mBinding.tvWarning.setText(C0874R.string.contact_us_msg_empty);
        this.mBinding.btnNext.setText(getString(C0874R.string.account_submit));
        this.mBinding.etMsg.addTextChangedListener(new TextWatcher() { // from class: com.aivox.set.activity.ContactUsActivity.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                ContactUsActivity.this.mBinding.tvTextCount.setText(editable.length() + "/300");
                int length = editable.length();
                ContactUsActivity.this.mBinding.llMsg.setBackgroundResource(length > 0 ? C1034R.drawable.bg_gary_input_focused_c20 : C1034R.drawable.bg_gary_input_notice_c20);
                ContactUsActivity.this.mBinding.ivMsgEmpty.setVisibility(length == 0 ? 0 : 8);
                ContactUsActivity.this.mBinding.tvWarning.setVisibility(length == 0 ? 0 : 8);
                ContactUsActivity.this.mBinding.btnNext.setEnabled(length > 0);
                String string = ContactUsActivity.this.mBinding.tvMsg.getText().toString();
                if (length == 0) {
                    if (!string.endsWith("*")) {
                        ContactUsActivity.this.mBinding.tvMsg.setText(string + "*");
                    }
                } else if (string.endsWith("*")) {
                    ContactUsActivity.this.mBinding.tvMsg.setText(string.substring(0, string.length() - 1));
                }
                ContactUsActivity.this.updateBtnStatus();
            }
        });
        this.mBinding.etMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view2, boolean z) {
                this.f$0.m2532lambda$initView$13$comaivoxsetactivityContactUsActivity(view2, z);
            }
        });
        this.mBinding.llSubmit.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2533lambda$initView$14$comaivoxsetactivityContactUsActivity(view2);
            }
        });
        this.mBinding.btnNext.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2534lambda$initView$15$comaivoxsetactivityContactUsActivity(view2);
            }
        });
        this.mSelectFiles.add(new UploadFileBean(0, ""));
        updateFileNum();
        this.mBinding.rvFiles.setLayoutManager(new GridLayoutManager(this.context, 5));
        FileSelectAdapter fileSelectAdapter = new FileSelectAdapter(C1106R.layout.file_item_layout);
        this.mAdapter = fileSelectAdapter;
        fileSelectAdapter.bindToRecyclerView(this.mBinding.rvFiles);
        this.mAdapter.setNewData(this.mSelectFiles);
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda16
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i2) {
                this.f$0.m2535lambda$initView$16$comaivoxsetactivityContactUsActivity(baseQuickAdapter, view2, i2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2528lambda$initView$1$comaivoxsetactivityContactUsActivity(int i) {
        if (i > 400) {
            this.mBinding.btnNext.setVisibility(8);
        } else {
            this.mBinding.btnNext.postDelayed(new Runnable() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m2527lambda$initView$0$comaivoxsetactivityContactUsActivity();
                }
            }, 200L);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2527lambda$initView$0$comaivoxsetactivityContactUsActivity() {
        this.mBinding.btnNext.setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2537lambda$initView$2$comaivoxsetactivityContactUsActivity(View view2, boolean z) {
        int i;
        LinearLayout linearLayout = this.mBinding.llEmail;
        if (z) {
            if (RegexUtil.isEmail(this.mBinding.etEmail.getText().toString()) || BaseStringUtil.isEmpty(this.mBinding.etEmail.getText().toString())) {
                i = C1034R.drawable.bg_gary_input_focused_c20;
            } else {
                i = C1034R.drawable.bg_gary_input_notice_c20;
            }
        } else {
            i = C1034R.drawable.bg_gary_input_c20;
        }
        linearLayout.setBackgroundResource(i);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2538lambda$initView$3$comaivoxsetactivityContactUsActivity(String str, View view2) {
        this.mBinding.etEmail.setText(str);
        this.mBinding.etEmail.setSelection(this.mBinding.etEmail.getText().length());
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2539lambda$initView$4$comaivoxsetactivityContactUsActivity(View view2) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_INDEX, 2);
        bundle.putString("email", this.mBinding.etEmail.getText().toString());
        ARouterUtils.startWithContext(this.context, SetAction.SET_CONTACT_US, bundle);
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2540lambda$initView$5$comaivoxsetactivityContactUsActivity(View view2) {
        backToHome();
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2541lambda$initView$6$comaivoxsetactivityContactUsActivity() throws Exception {
        this.mBinding.tagFlow.setAdapter(new TagAdapter<Integer>(this.mTagList) { // from class: com.aivox.set.activity.ContactUsActivity.2
            @Override // com.zhy.view.flowlayout.TagAdapter
            public View getView(FlowLayout flowLayout, int i, Integer num) {
                TextView textView = (TextView) ContactUsActivity.this.mInflater.inflate(C1106R.layout.tag_item_layout, (ViewGroup) ContactUsActivity.this.mBinding.tagFlow, false);
                textView.setText(ContactUsActivity.this.getTagNameById(num.intValue()));
                return textView;
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2542lambda$initView$7$comaivoxsetactivityContactUsActivity(List list) throws Exception {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mTagList.add(((IssuesTagBean) it.next()).getCode());
        }
    }

    /* JADX INFO: renamed from: lambda$initView$8$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2543lambda$initView$8$comaivoxsetactivityContactUsActivity(Throwable th) throws Exception {
        this.mTagList.add(1);
        this.mTagList.add(2);
        this.mTagList.add(3);
        this.mTagList.add(4);
        this.mTagList.add(5);
        this.mTagList.add(6);
        this.mTagList.add(99);
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ boolean m2544lambda$initView$9$comaivoxsetactivityContactUsActivity(View view2, int i, FlowLayout flowLayout) {
        if (this.mBinding.tagFlow.getSelectedList().size() != 2) {
            return false;
        }
        ToastUtil.showShort(Integer.valueOf(C0874R.string.contact_us_tag_limit));
        return false;
    }

    /* JADX INFO: renamed from: lambda$initView$10$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2529lambda$initView$10$comaivoxsetactivityContactUsActivity(Set set) {
        int size = set.size();
        this.mBinding.btnNext.setEnabled(size > 0);
        String string = this.mBinding.tvTags.getText().toString();
        if (size == 0) {
            if (string.endsWith("*")) {
                return;
            }
            this.mBinding.tvTags.setText(string + "*");
        } else if (string.endsWith("*")) {
            this.mBinding.tvTags.setText(string.substring(0, string.length() - 1));
        }
    }

    /* JADX INFO: renamed from: lambda$initView$11$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2530lambda$initView$11$comaivoxsetactivityContactUsActivity(View view2) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Iterator<Integer> it = this.mBinding.tagFlow.getSelectedList().iterator();
        while (it.hasNext()) {
            arrayList.add(this.mTagList.get(it.next().intValue()));
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_INDEX, 3);
        bundle.putString("email", this.mContactEmail);
        bundle.putIntegerArrayList(Constant.KEY_IDS, arrayList);
        ARouterUtils.startWithContext(this.context, SetAction.SET_CONTACT_US, bundle);
    }

    /* JADX INFO: renamed from: lambda$initView$12$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2531lambda$initView$12$comaivoxsetactivityContactUsActivity(View view2) {
        backToHome();
    }

    /* JADX INFO: renamed from: lambda$initView$13$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2532lambda$initView$13$comaivoxsetactivityContactUsActivity(View view2, boolean z) {
        this.mBinding.tvTextCount.setVisibility(z ? 0 : 8);
        this.mBinding.llMsg.setBackgroundResource(z ? C1034R.drawable.bg_gary_input_focused_c20 : C1034R.drawable.bg_gary_input_c20);
    }

    /* JADX INFO: renamed from: lambda$initView$14$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2533lambda$initView$14$comaivoxsetactivityContactUsActivity(View view2) {
        this.mBinding.cbSubmit.setChecked(!this.mBinding.cbSubmit.isChecked());
        updateBtnStatus();
    }

    /* JADX INFO: renamed from: lambda$initView$15$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2534lambda$initView$15$comaivoxsetactivityContactUsActivity(View view2) {
        upload2OSS();
    }

    /* JADX INFO: renamed from: lambda$initView$16$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2535lambda$initView$16$comaivoxsetactivityContactUsActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (view2.getId() == C1106R.id.iv_cover) {
            if (this.mAdapter.getData().get(i).getType() == 0) {
                PermissionUtils.getIns(this, new C11104()).request("android.permission.READ_MEDIA_VIDEO", "android.permission.READ_MEDIA_AUDIO", "android.permission.READ_MEDIA_IMAGES");
            }
        } else if (view2.getId() == C1106R.id.iv_delete) {
            this.mPathNeedUpload.remove(this.mSelectFiles.get(i).getLocalPath());
            this.mSelectFiles.remove(i);
            this.mAdapter.notifyItemRemoved(i);
            updateFileNum();
        }
    }

    /* JADX INFO: renamed from: com.aivox.set.activity.ContactUsActivity$4 */
    class C11104 implements PermissionCallback {
        C11104() {
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void granted(boolean z) {
            PictureSelector.create((AppCompatActivity) ContactUsActivity.this).openGallery(SelectMimeType.ofAll()).setMaxSelectNum(1).setMinSelectNum(1).setImageSpanCount(4).isPreviewImage(true).isDisplayCamera(true).setSelectMaxFileSize(31457280L).setImageEngine(GlideEngine.createGlideEngine()).setCompressEngine(new CompressFileEngine() { // from class: com.aivox.set.activity.ContactUsActivity$4$$ExternalSyntheticLambda0
                @Override // com.luck.picture.lib.engine.CompressFileEngine
                public final void onStartCompress(Context context, ArrayList arrayList, OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
                    this.f$0.m2545lambda$granted$0$comaivoxsetactivityContactUsActivity$4(context, arrayList, onKeyValueResultCallbackListener);
                }
            }).setSelectionMode(1).isSelectZoomAnim(true).forResult(188);
        }

        /* JADX INFO: renamed from: lambda$granted$0$com-aivox-set-activity-ContactUsActivity$4, reason: not valid java name */
        /* synthetic */ void m2545lambda$granted$0$comaivoxsetactivityContactUsActivity$4(Context context, ArrayList arrayList, final OnKeyValueResultCallbackListener onKeyValueResultCallbackListener) {
            Luban.with(context).load(arrayList).ignoreBy(50).setCompressListener(new OnNewCompressListener() { // from class: com.aivox.set.activity.ContactUsActivity.4.1
                @Override // top.zibin.luban.OnNewCompressListener
                public void onStart() {
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onSuccess(String str, File file) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, file.getAbsolutePath());
                    }
                }

                @Override // top.zibin.luban.OnNewCompressListener
                public void onError(String str, Throwable th) {
                    OnKeyValueResultCallbackListener onKeyValueResultCallbackListener2 = onKeyValueResultCallbackListener;
                    if (onKeyValueResultCallbackListener2 != null) {
                        onKeyValueResultCallbackListener2.onCallback(str, null);
                    }
                }
            }).launch();
        }

        @Override // com.aivox.base.permission.PermissionCallback
        public void requestError(Throwable th) {
            LogUtil.m336e("permission.e:" + th.getLocalizedMessage());
            ToastUtil.showShort(Integer.valueOf(C0874R.string.permission_denied));
            BaseAppUtils.openSettingView(ContactUsActivity.this.context);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$17$com-aivox-set-activity-ContactUsActivity, reason: not valid java name */
    /* synthetic */ void m2536lambda$initView$17$comaivoxsetactivityContactUsActivity(View view2) {
        backToHome();
    }

    private void backToHome() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_INDEX, 2);
        ARouterUtils.startWithActivity(this, MainAction.MAIN, bundle);
        AppManager.getAppManager().finishAllActivityExcept(ARouterUtils.getClass(MainAction.MAIN));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBtnStatus() {
        this.mBinding.btnNext.setEnabled(this.mBinding.cbSubmit.isChecked() && BaseStringUtil.isNotEmpty(this.mBinding.etMsg.getText().toString().trim()));
    }

    private void updateFileNum() {
        if (this.mSelectFiles.size() == 11) {
            this.mSelectFiles.remove(0);
            this.mAdapter.notifyItemRemoved(0);
            this.mBinding.tvFiles.setText(String.format(getString(C0874R.string.contact_us_files_num), Integer.valueOf(this.mSelectFiles.size())));
        } else {
            if (this.mSelectFiles.get(0).getType() != 0) {
                this.mSelectFiles.add(0, new UploadFileBean(0, ""));
                this.mAdapter.notifyItemInserted(0);
            }
            this.mBinding.tvFiles.setText(String.format(getString(C0874R.string.contact_us_files_num), Integer.valueOf(this.mSelectFiles.size() - 1)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadAndAskForAssistance() {
        DialogUtils.showLoadingDialog(this.context);
        final File file = new File(FileUtils.getLogFilePath(this.context), SaveLogHelper.getIns().getFileName());
        String str = (String) SPUtil.get(SPUtil.USER_ID, "");
        if (FileUtils.isFileExist(file.getPath())) {
            CommonUploadManager.getInstance().startUpload(this.context, 0, file.getPath(), str, DateUtil.getCurDate(DateUtil.YYYYMMDD), new OnUploadListener() { // from class: com.aivox.set.activity.ContactUsActivity.5
                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onProgress(int i, long j, long j2, int i2) {
                    LogUtil.m338i("上传中:" + i + "  " + i2 + "% " + j + "/" + j2);
                }

                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onSuccess(int i, String str2, String str3, long j) {
                    ContactUsActivity.this.askForAssistance(file, str3);
                }

                @Override // com.aivox.common.http.oss.OnUploadListener
                public void onFailure(int i) {
                    LogUtil.m338i("上传失败：" + i);
                }
            }, Constant.TYPE_LOG);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void askForAssistance(final File file, String str) {
        ArrayList arrayList = new ArrayList();
        for (UploadFileBean uploadFileBean : this.mSelectFiles) {
            if (uploadFileBean.getType() != 0) {
                arrayList.add(uploadFileBean.getUrl());
            }
        }
        new UserService(this.context).userFeedback(this.mContactEmail, this.mBinding.etMsg.getText().toString().trim(), this.mTagList, arrayList, str).subscribe(new Consumer() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m365xbff6c07a(file, obj);
            }
        }, new Consumer() { // from class: com.aivox.set.activity.ContactUsActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                DialogUtils.hideLoadingDialog();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$askForAssistance$18$com-aivox-set-activity-ContactUsActivity */
    /* synthetic */ void m365xbff6c07a(File file, Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        FileUtils.deleteFile(file.getPath());
        SaveLogHelper.getIns().init(FileUtils.getLogFilePath(this.context));
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_INDEX, 4);
        ARouterUtils.startWithContext(this.context, SetAction.SET_CONTACT_US, bundle);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mCurStep != 4) {
            super.onBackPressed();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 188) {
            ArrayList<LocalMedia> arrayListObtainSelectorList = PictureSelector.obtainSelectorList(intent);
            for (LocalMedia localMedia : arrayListObtainSelectorList) {
                String availablePath = localMedia.getAvailablePath();
                String realPath = localMedia.getRealPath();
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                LogUtil.m338i("path:" + availablePath);
                LogUtil.m338i("file size:" + FileUtils.getFileSizeKb(availablePath));
                LogUtil.m338i("ori path:" + realPath);
                LogUtil.m338i("ori size:" + FileUtils.getFileSizeKb(realPath));
                LogUtil.m338i("type:" + localMedia.getMimeType());
                if (isImage(localMedia.getMimeType())) {
                    this.mSelectFiles.add(new UploadFileBean(1, availablePath));
                    this.mPathNeedUpload.add(availablePath);
                } else {
                    this.mSelectFiles.add(new UploadFileBean(2, realPath));
                    this.mPathNeedUpload.add(realPath);
                }
            }
            updateFileNum();
            this.mAdapter.notifyItemRangeChanged(this.mSelectFiles.size() - arrayListObtainSelectorList.size(), this.mSelectFiles.size());
        }
    }

    private void upload2OSS() {
        if (this.mPathNeedUpload.isEmpty()) {
            uploadAndAskForAssistance();
            return;
        }
        DialogUtils.showLoadingDialog(this.context, "", false);
        for (String str : this.mPathNeedUpload) {
            if (FileUtils.isFileExist(str)) {
                CommonUploadManager.getInstance().startUpload(this.context, 0, str, this.mUserInfo.getUuid(), DateUtil.getCurDate(DateUtil.YYYYMMDD), new C11126(str), Constant.TYPE_LOG);
            } else {
                LogUtil.m338i("文件不存在");
            }
        }
    }

    /* JADX INFO: renamed from: com.aivox.set.activity.ContactUsActivity$6 */
    class C11126 implements OnUploadListener {
        final /* synthetic */ String val$localPath;

        C11126(String str) {
            this.val$localPath = str;
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onProgress(int i, long j, long j2, int i2) {
            LogUtil.m338i("上传中:" + i + "  " + i2 + "% " + j + "/" + j2);
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onSuccess(int i, String str, String str2, long j) {
            LogUtil.m338i("上传成功:" + i + "  " + str + "   " + str2);
            ContactUsActivity.access$308(ContactUsActivity.this);
            for (UploadFileBean uploadFileBean : ContactUsActivity.this.mSelectFiles) {
                if (uploadFileBean.getLocalPath().equals(this.val$localPath)) {
                    uploadFileBean.setUrl(str2);
                }
            }
            if (ContactUsActivity.this.mUploadFileNum == ContactUsActivity.this.mPathNeedUpload.size()) {
                ContactUsActivity.this.runOnUiThread(new Runnable() { // from class: com.aivox.set.activity.ContactUsActivity$6$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2547lambda$onSuccess$1$comaivoxsetactivityContactUsActivity$6();
                    }
                });
            }
        }

        /* JADX INFO: renamed from: lambda$onSuccess$1$com-aivox-set-activity-ContactUsActivity$6, reason: not valid java name */
        /* synthetic */ void m2547lambda$onSuccess$1$comaivoxsetactivityContactUsActivity$6() {
            DialogUtils.hideLoadingDialog();
            DialogUtils.showDialogWithDefBtn(ContactUsActivity.this.context, "", Integer.valueOf(C0874R.string.contact_us_summit_notice), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.set.activity.ContactUsActivity$6$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2546lambda$onSuccess$0$comaivoxsetactivityContactUsActivity$6(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, true, true);
        }

        /* JADX INFO: renamed from: lambda$onSuccess$0$com-aivox-set-activity-ContactUsActivity$6, reason: not valid java name */
        /* synthetic */ void m2546lambda$onSuccess$0$comaivoxsetactivityContactUsActivity$6(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
            ContactUsActivity.this.uploadAndAskForAssistance();
        }

        @Override // com.aivox.common.http.oss.OnUploadListener
        public void onFailure(int i) {
            LogUtil.m338i("上传失败：" + i);
        }
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        KeyboardUtils.unregisterSoftInputChangedListener(getWindow());
    }

    private boolean isImage(String str) {
        List listAsList = Arrays.asList("image/jpeg", PictureMimeType.PNG_Q, "image/gif", "image/bmp", "image/tiff", "image/webp");
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        return listAsList.contains(str.toLowerCase());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTagNameById(int i) {
        if (i != 99) {
            switch (i) {
                case 1:
                    return C0874R.string.contact_us_tag_1;
                case 2:
                    return C0874R.string.contact_us_tag_2;
                case 3:
                    return C0874R.string.contact_us_tag_3;
                case 4:
                    return C0874R.string.contact_us_tag_4;
                case 5:
                    return C0874R.string.contact_us_tag_5;
                case 6:
                    return C0874R.string.contact_us_tag_6;
                default:
                    return 0;
            }
        }
        return C0874R.string.contact_us_tag_7;
    }
}
