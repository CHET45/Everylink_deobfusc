package com.aivox.set.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.base.C0874R;
import com.aivox.base.http.HttpException;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.UserService;
import com.aivox.common.model.AllTransTimeBean;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.C1034R;
import com.aivox.set.C1106R;
import com.aivox.set.databinding.ActivityTimeDetailBinding;
import com.blankj.utilcode.util.CollectionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TimeDetailActivity extends BaseFragmentActivity {
    private TimeAdapter mAdapter;
    private ActivityTimeDetailBinding mBinding;
    private final List<AllTransTimeBean.RecordTimeList> mList = new ArrayList();

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityTimeDetailBinding activityTimeDetailBinding = (ActivityTimeDetailBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_time_detail);
        this.mBinding = activityTimeDetailBinding;
        activityTimeDetailBinding.rvTime.setLayoutManager(new LinearLayoutManager(this.context));
        TimeAdapter timeAdapter = new TimeAdapter(C1034R.layout.remain_time_item_view_layout);
        this.mAdapter = timeAdapter;
        timeAdapter.bindToRecyclerView(this.mBinding.rvTime);
        getData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getData() {
        DialogUtils.showLoadingDialog(this.context);
        new UserService(this.context).getAllRecordTimeList().doFinally(new Action() { // from class: com.aivox.set.activity.TimeDetailActivity$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Action
            public final void run() {
                DialogUtils.hideLoadingDialog();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.set.activity.TimeDetailActivity$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2557lambda$getData$0$comaivoxsetactivityTimeDetailActivity((AllTransTimeBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.set.activity.TimeDetailActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2558lambda$getData$1$comaivoxsetactivityTimeDetailActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getData$0$com-aivox-set-activity-TimeDetailActivity, reason: not valid java name */
    /* synthetic */ void m2557lambda$getData$0$comaivoxsetactivityTimeDetailActivity(AllTransTimeBean allTransTimeBean) throws Exception {
        List<AllTransTimeBean.RecordTimeList> recordTimeList = allTransTimeBean.getRecordTimeList();
        List<AllTransTimeBean.RecordTimeList> usedRecordTimeList = allTransTimeBean.getUsedRecordTimeList();
        if (CollectionUtils.isNotEmpty(recordTimeList)) {
            long j = 0;
            long j2 = 0;
            long j3 = 0;
            for (AllTransTimeBean.RecordTimeList recordTimeList2 : recordTimeList) {
                recordTimeList2.setExpired(false);
                long jIntValue = recordTimeList2.getTotal().intValue() - recordTimeList2.getUsed().intValue();
                int iIntValue = recordTimeList2.getSource().intValue();
                if (iIntValue == 2 || iIntValue == 3) {
                    j3 += jIntValue;
                } else if (iIntValue != 8 && iIntValue != 9) {
                    j2 += jIntValue;
                } else if (DateUtil.isTimePassed(recordTimeList2.getBeginAt())) {
                    j = jIntValue;
                }
            }
            this.mBinding.rtvTime.setRemainingTime(Long.parseLong(SPUtil.get(SPUtil.LEFT_CURRENCY_TIME, 0) + ""), j, j2, j3);
            this.mBinding.rtvTime.setVisibility(0);
            this.mList.addAll(recordTimeList);
        }
        if (CollectionUtils.isNotEmpty(usedRecordTimeList)) {
            this.mList.addAll(usedRecordTimeList);
        }
        this.mAdapter.setNewData(this.mList);
    }

    /* JADX INFO: renamed from: lambda$getData$1$com-aivox-set-activity-TimeDetailActivity, reason: not valid java name */
    /* synthetic */ void m2558lambda$getData$1$comaivoxsetactivityTimeDetailActivity(Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        } else {
            AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.set.activity.TimeDetailActivity$$ExternalSyntheticLambda3
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.getData();
                }
            });
        }
    }

    private static class TimeAdapter extends BaseQuickAdapter<AllTransTimeBean.RecordTimeList, BaseViewHolder> {
        public TimeAdapter(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, AllTransTimeBean.RecordTimeList recordTimeList) {
            String string;
            int iIntValue = recordTimeList.getSource().intValue();
            String expireAt = recordTimeList.getExpireAt();
            baseViewHolder.setAlpha(C1034R.id.cl_content, recordTimeList.isExpired() ? 0.3f : 1.0f);
            baseViewHolder.setText(C1034R.id.tv_time_remain, BaseStringUtil.getHourStr(recordTimeList.getTotal().intValue() - recordTimeList.getUsed().intValue(), "") + " " + this.mContext.getString(C0874R.string.hours_left));
            baseViewHolder.setText(C1034R.id.tv_time_type, getTextBySource(iIntValue));
            baseViewHolder.setBackgroundRes(C1034R.id.view_label, recordTimeList.isExpired() ? C1034R.drawable.ic_time_label_expired : getLabelBySource(iIntValue));
            int i = C1034R.id.tv_time_expired;
            if (iIntValue == 8) {
                string = this.mContext.getString(C0874R.string.valid_from, DateUtil.getDateForTimeDetail(recordTimeList.getBeginAt(), true), DateUtil.getDateForTimeDetail(expireAt, false));
            } else if (iIntValue == 9) {
                string = this.mContext.getString(C0874R.string.updated_after, DateUtil.getDateForTimeDetail(expireAt, false));
            } else {
                string = this.mContext.getString(C0874R.string.valid_through, DateUtil.getDateForTimeDetail(expireAt, false));
            }
            baseViewHolder.setText(i, string);
            baseViewHolder.setGone(C1034R.id.tv_time_expired, iIntValue != 11);
        }

        private int getTextBySource(int i) {
            if (i == 2 || i == 3) {
                return C0874R.string.extra_purchased;
            }
            if (i == 8 || i == 9) {
                return C0874R.string.vip_pro;
            }
            if (i == 11) {
                return C0874R.string.free_trial;
            }
            return C0874R.string.other_way;
        }

        private int getLabelBySource(int i) {
            if (i == 2 || i == 3) {
                return C1034R.drawable.ic_time_label_purchase;
            }
            if (i == 8 || i == 9) {
                return C1034R.drawable.ic_time_label_vip;
            }
            return C1034R.drawable.ic_time_label_gift;
        }
    }
}
