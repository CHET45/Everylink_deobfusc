package com.aivox.set.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aivox.base.C0874R;
import com.aivox.base.http.HttpException;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.PurchaseHistoryBean;
import com.aivox.common.util.AppUtils;
import com.aivox.set.C1106R;
import com.aivox.set.adapter.PurchaseHistoryAdapter;
import com.aivox.set.databinding.ActivityPurchaseHistoryBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.functions.Consumer;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public class PurchaseHistoryActivity extends BaseFragmentActivity {
    private PurchaseHistoryAdapter mAdapter;
    private ActivityPurchaseHistoryBinding mBinding;
    private boolean mIsLoadAll;
    private int mPage = 1;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityPurchaseHistoryBinding) DataBindingUtil.setContentView(this, C1106R.layout.activity_purchase_history);
        this.mAdapter = new PurchaseHistoryAdapter(C1106R.layout.item_purchase_history);
        this.mBinding.rvList.setLayoutManager(new LinearLayoutManager(this.context));
        this.mAdapter.bindToRecyclerView(this.mBinding.rvList);
        this.mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.aivox.set.activity.PurchaseHistoryActivity$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public final void onLoadMoreRequested() {
                this.f$0.m2553lambda$initView$0$comaivoxsetactivityPurchaseHistoryActivity();
            }
        }, this.mBinding.rvList);
        this.mAdapter.setEmptyView(C0874R.layout.include_null_view);
        this.mBinding.refreshView.setRefreshing(true);
        this.mBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.aivox.set.activity.PurchaseHistoryActivity$$ExternalSyntheticLambda1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m2554lambda$initView$1$comaivoxsetactivityPurchaseHistoryActivity();
            }
        });
        m2553lambda$initView$0$comaivoxsetactivityPurchaseHistoryActivity();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-set-activity-PurchaseHistoryActivity, reason: not valid java name */
    /* synthetic */ void m2554lambda$initView$1$comaivoxsetactivityPurchaseHistoryActivity() {
        this.mPage = 1;
        m2553lambda$initView$0$comaivoxsetactivityPurchaseHistoryActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getData, reason: merged with bridge method [inline-methods] */
    public void m2553lambda$initView$0$comaivoxsetactivityPurchaseHistoryActivity() {
        new AudioService(this).getPurchaseHistoryList(this.mPage, 20).subscribe(new Consumer() { // from class: com.aivox.set.activity.PurchaseHistoryActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2551lambda$getData$2$comaivoxsetactivityPurchaseHistoryActivity((PurchaseHistoryBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.set.activity.PurchaseHistoryActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2552lambda$getData$3$comaivoxsetactivityPurchaseHistoryActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getData$2$com-aivox-set-activity-PurchaseHistoryActivity, reason: not valid java name */
    /* synthetic */ void m2551lambda$getData$2$comaivoxsetactivityPurchaseHistoryActivity(PurchaseHistoryBean purchaseHistoryBean) throws Exception {
        this.mBinding.refreshView.setRefreshing(false);
        if (this.mPage == 1) {
            this.mAdapter.setNewData(purchaseHistoryBean.getRecords());
        } else {
            this.mAdapter.addData((Collection) purchaseHistoryBean.getRecords());
        }
        boolean z = this.mPage * 20 >= purchaseHistoryBean.getTotal();
        this.mIsLoadAll = z;
        this.mAdapter.setEnableLoadMore(!z);
        this.mAdapter.loadMoreComplete();
        this.mPage++;
    }

    /* JADX INFO: renamed from: lambda$getData$3$com-aivox-set-activity-PurchaseHistoryActivity, reason: not valid java name */
    /* synthetic */ void m2552lambda$getData$3$comaivoxsetactivityPurchaseHistoryActivity(Throwable th) throws Exception {
        this.mBinding.refreshView.setRefreshing(false);
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this);
        } else {
            AppUtils.showError(this, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.set.activity.PurchaseHistoryActivity$$ExternalSyntheticLambda2
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2553lambda$initView$0$comaivoxsetactivityPurchaseHistoryActivity();
                }
            });
        }
    }
}
