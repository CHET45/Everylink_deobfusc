package com.aivox.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.NoticeListAdapter;
import com.aivox.app.databinding.ActivityNoticeListBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.CommonNoticeBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.NoticeBean;
import com.aivox.common.util.AppUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.function.Predicate;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class NoticeListActivity extends BaseFragmentActivity {
    private NoticeListAdapter mAdapter;
    private ActivityNoticeListBinding mBinding;
    private Disposable mDis;
    private boolean mIsSysList = true;
    private boolean mMsgRecorded;
    private NoticeBean mNoticeBean;
    private boolean mSysRecorded;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityNoticeListBinding activityNoticeListBinding = (ActivityNoticeListBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_notice_list);
        this.mBinding = activityNoticeListBinding;
        activityNoticeListBinding.rvNotification.setLayoutManager(new LinearLayoutManager(this.context));
        NoticeListAdapter noticeListAdapter = new NoticeListAdapter(C0726R.layout.item_notice_list);
        this.mAdapter = noticeListAdapter;
        noticeListAdapter.bindToRecyclerView(this.mBinding.rvNotification);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda8
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2167lambda$initView$0$comaivoxappactivityNoticeListActivity(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda9
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2174lambda$initView$7$comaivoxappactivityNoticeListActivity(baseQuickAdapter, view2, i);
            }
        });
        this.mBinding.tvNoticeSys.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2175lambda$initView$8$comaivoxappactivityNoticeListActivity(view2);
            }
        });
        this.mBinding.tvNoticeMsg.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2176lambda$initView$9$comaivoxappactivityNoticeListActivity(view2);
            }
        });
        getNoticeData();
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2167lambda$initView$0$comaivoxappactivityNoticeListActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        CommonNoticeBean commonNoticeBean = (CommonNoticeBean) baseQuickAdapter.getData().get(i);
        if (BaseStringUtil.isNotEmpty(commonNoticeBean.getUrl())) {
            BaseAppUtils.startActivityForWeb(this, commonNoticeBean.getUrl(), ARouterUtils.getClass(MainAction.WEB));
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_TITLE, commonNoticeBean.getTitle());
        bundle.putString("data", commonNoticeBean.getContent());
        ARouterUtils.startWithActivity(this, MainAction.NOTICE_DETAIL, bundle);
    }

    /* JADX INFO: renamed from: lambda$initView$7$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2174lambda$initView$7$comaivoxappactivityNoticeListActivity(BaseQuickAdapter baseQuickAdapter, View view2, final int i) {
        if (view2.getId() == C0726R.id.iv_save) {
            DialogUtils.showLoadingDialog(this.context, "", false);
            new AudioService(this.context).saveAppShare(this.mAdapter.getItem(i).getSign(), this.mAdapter.getItem(i).getId().intValue()).doFinally(new NoticeListActivity$$ExternalSyntheticLambda0()).subscribe(new Consumer() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2169lambda$initView$2$comaivoxappactivityNoticeListActivity(i, obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda13
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2170lambda$initView$3$comaivoxappactivityNoticeListActivity((Throwable) obj);
                }
            });
        } else if (view2.getId() == C0726R.id.iv_delete) {
            DialogUtils.showDeleteDialog(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.audio_delete), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda1
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2173lambda$initView$6$comaivoxappactivityNoticeListActivity(i, context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2169lambda$initView$2$comaivoxappactivityNoticeListActivity(final int i, Object obj) throws Exception {
        this.mNoticeBean.getMessageList().getRecords().removeIf(new Predicate() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return this.f$0.m2168lambda$initView$1$comaivoxappactivityNoticeListActivity(i, (NoticeBean.MessageList.Records) obj2);
            }
        });
        changeList(false);
        DialogUtils.showDialogWithBtnIds(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.share_save_success), null, null, false, true, C0874R.string.cancel, C0874R.string.done);
        EventBus.getDefault().post(new EventBean(50));
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ boolean m2168lambda$initView$1$comaivoxappactivityNoticeListActivity(int i, NoticeBean.MessageList.Records records) {
        return records.getId().equals(this.mAdapter.getItem(i).getId());
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2170lambda$initView$3$comaivoxappactivityNoticeListActivity(Throwable th) throws Exception {
        AppUtils.checkHttpCode(this.context);
    }

    /* JADX INFO: renamed from: lambda$initView$6$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2173lambda$initView$6$comaivoxappactivityNoticeListActivity(final int i, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        DialogUtils.showLoadingDialog(context);
        new AudioService(context).deleteNotify(this.mAdapter.getItem(i).getId().intValue()).doFinally(new NoticeListActivity$$ExternalSyntheticLambda0()).subscribe(new Consumer() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2172lambda$initView$5$comaivoxappactivityNoticeListActivity(i, obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2172lambda$initView$5$comaivoxappactivityNoticeListActivity(final int i, Object obj) throws Exception {
        this.mNoticeBean.getMessageList().getRecords().removeIf(new Predicate() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return this.f$0.m2171lambda$initView$4$comaivoxappactivityNoticeListActivity(i, (NoticeBean.MessageList.Records) obj2);
            }
        });
        changeList(false);
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ boolean m2171lambda$initView$4$comaivoxappactivityNoticeListActivity(int i, NoticeBean.MessageList.Records records) {
        return records.getId().equals(this.mAdapter.getItem(i).getId());
    }

    /* JADX INFO: renamed from: lambda$initView$8$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2175lambda$initView$8$comaivoxappactivityNoticeListActivity(View view2) {
        if (this.mIsSysList) {
            return;
        }
        changeList(true);
    }

    /* JADX INFO: renamed from: lambda$initView$9$com-aivox-app-activity-NoticeListActivity, reason: not valid java name */
    /* synthetic */ void m2176lambda$initView$9$comaivoxappactivityNoticeListActivity(View view2) {
        if (this.mIsSysList) {
            changeList(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getNoticeData() {
        this.mBinding.llEmpty.setVisibility(4);
        DialogUtils.showLoadingDialog(this.context);
        this.mDis = new AudioService(this.context).getAllNewNotice().doFinally(new NoticeListActivity$$ExternalSyntheticLambda0()).subscribe(new Consumer() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m164x583a7e99((NoticeBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m165x3d7bed5a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getNoticeData$10$com-aivox-app-activity-NoticeListActivity */
    /* synthetic */ void m164x583a7e99(NoticeBean noticeBean) throws Exception {
        this.mNoticeBean = noticeBean;
        changeList(this.mIsSysList);
    }

    /* JADX INFO: renamed from: lambda$getNoticeData$11$com-aivox-app-activity-NoticeListActivity */
    /* synthetic */ void m165x3d7bed5a(Throwable th) throws Exception {
        this.mBinding.llEmpty.setVisibility(0);
        AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.NoticeListActivity$$ExternalSyntheticLambda6
            @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
            public final void callback() {
                this.f$0.getNoticeData();
            }
        });
    }

    private void changeList(boolean z) {
        if (this.mNoticeBean == null) {
            return;
        }
        this.mIsSysList = z;
        this.mBinding.llEmpty.setVisibility(4);
        ArrayList arrayList = new ArrayList();
        if (this.mIsSysList) {
            this.mBinding.tvNoticeSys.setTextColor(ColorUtils.getColor(C0874R.color.txt_color_primary));
            this.mBinding.tvNoticeMsg.setTextColor(ColorUtils.getColor(C0874R.color.txt_color_tertiary));
            if (CollectionUtils.isEmpty(this.mNoticeBean.getSysNotifyList())) {
                this.mBinding.llEmpty.setVisibility(0);
            } else {
                if (!this.mSysRecorded) {
                    SPUtil.putWithUid(SPUtil.LATEST_SYS_NOTICE_ID, this.mNoticeBean.getSysNotifyList().get(0).getId());
                    this.mSysRecorded = true;
                }
                for (NoticeBean.SysNotifyList sysNotifyList : this.mNoticeBean.getSysNotifyList()) {
                    CommonNoticeBean commonNoticeBean = new CommonNoticeBean();
                    commonNoticeBean.setId(sysNotifyList.getId());
                    commonNoticeBean.setContent(sysNotifyList.getContent());
                    commonNoticeBean.setCreatedAt(sysNotifyList.getCreatedAt());
                    commonNoticeBean.setTitle(sysNotifyList.getTitle());
                    commonNoticeBean.setType(sysNotifyList.getType());
                    commonNoticeBean.setUrl(sysNotifyList.getUrl());
                    arrayList.add(commonNoticeBean);
                }
            }
        } else {
            this.mBinding.tvNoticeSys.setTextColor(ColorUtils.getColor(C0874R.color.txt_color_tertiary));
            this.mBinding.tvNoticeMsg.setTextColor(ColorUtils.getColor(C0874R.color.txt_color_primary));
            if (CollectionUtils.isEmpty(this.mNoticeBean.getMessageList().getRecords())) {
                this.mBinding.llEmpty.setVisibility(0);
            } else {
                if (!this.mMsgRecorded) {
                    SPUtil.putWithUid(SPUtil.LATEST_MSG_NOTICE_ID, this.mNoticeBean.getMessageList().getRecords().get(0).getId());
                    this.mMsgRecorded = true;
                }
                for (NoticeBean.MessageList.Records records : this.mNoticeBean.getMessageList().getRecords()) {
                    CommonNoticeBean commonNoticeBean2 = new CommonNoticeBean();
                    commonNoticeBean2.setId(records.getId());
                    commonNoticeBean2.setContent(records.getContent());
                    commonNoticeBean2.setCreatedAt(records.getCreatedAt());
                    commonNoticeBean2.setTitle(records.getTitle());
                    commonNoticeBean2.setType(records.getType());
                    commonNoticeBean2.setUrl(records.getUrl());
                    commonNoticeBean2.setSign(records.getSign());
                    commonNoticeBean2.setEffective(records.isEffective());
                    arrayList.add(commonNoticeBean2);
                }
            }
        }
        this.mAdapter.setNewData(arrayList);
    }

    @Override // com.aivox.common.base.BaseFragmentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mDis.dispose();
    }
}
