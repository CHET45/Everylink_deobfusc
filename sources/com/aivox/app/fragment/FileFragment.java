package com.aivox.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.HomeAudioListAdapter;
import com.aivox.app.databinding.FragmentFileBinding;
import com.aivox.app.listener.FragmentFileActionListener;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioNewAllListBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common_ui.antishake.AntiShake;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class FileFragment extends BaseBindingFragment {
    private AudioService audioService;
    private HomeAudioListAdapter mAdapter;
    private FragmentFileBinding mBinding;
    private FragmentFileActionListener mListener;
    private final CompositeDisposable mDis = new CompositeDisposable();
    private final List<AudioInfoBean> mList = new ArrayList();
    private int mPage = 1;

    public static FileFragment newInstance() {
        return new FileFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mBinding = FragmentFileBinding.inflate(layoutInflater, viewGroup, false);
        this.audioService = new AudioService(this.mActivity);
        this.mBinding.rvAudioList.setLayoutManager(new LinearLayoutManager(this.mContext));
        HomeAudioListAdapter homeAudioListAdapter = new HomeAudioListAdapter(C0726R.layout.item_audio_layout);
        this.mAdapter = homeAudioListAdapter;
        homeAudioListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2265lambda$initBindingAndViews$0$comaivoxappfragmentFileFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
            public final boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return this.f$0.m2266lambda$initBindingAndViews$1$comaivoxappfragmentFileFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setNumChangeListener(new HomeAudioListAdapter.OnSelectNumChangedListener() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda2
            @Override // com.aivox.app.adapter.HomeAudioListAdapter.OnSelectNumChangedListener
            public final void onAudioSelectedChanged(List list) {
                this.f$0.m2267lambda$initBindingAndViews$2$comaivoxappfragmentFileFragment(list);
            }
        });
        this.mAdapter.bindToRecyclerView(this.mBinding.rvAudioList);
        this.mAdapter.setEmptyView(C0726R.layout.home_empty_layout);
        this.mBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda3
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m2268lambda$initBindingAndViews$3$comaivoxappfragmentFileFragment();
            }
        });
        this.mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public final void onLoadMoreRequested() {
                this.f$0.m2269lambda$initBindingAndViews$4$comaivoxappfragmentFileFragment();
            }
        }, this.mBinding.rvAudioList);
        getAudioList(true);
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ void m2265lambda$initBindingAndViews$0$comaivoxappfragmentFileFragment(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.mAdapter.isAudioSelectMode() || !AntiShake.check(this)) {
            if (this.mAdapter.isAudioSelectMode()) {
                this.mAdapter.onItemSelected(i);
                return;
            }
            AudioInfoBean audioInfoBean = this.mList.get(i);
            if (audioInfoBean.getId() == DataHandle.getIns().getBreakAudioId()) {
                ToastUtil.showLong(Integer.valueOf(C0874R.string.break_audio_resuming));
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.AUDIO_ID, audioInfoBean.getId());
            bundle.putString(Constant.LOCAL_AUDIO_URL, audioInfoBean.getLocalPath());
            bundle.putString(Constant.LOCAL_AUDIO_NAME, audioInfoBean.getTitle());
            bundle.putInt(Constant.LOCAL_AUDIO_DURATION, audioInfoBean.getFileTime());
            bundle.putBoolean(Constant.IS_LOCAL_AUDIO, false);
            ARouterUtils.startWithActivity(this.mActivity, RecordAction.RECORD_INFO, bundle);
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$1$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ boolean m2266lambda$initBindingAndViews$1$comaivoxappfragmentFileFragment(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        this.mAdapter.onItemSelected(i);
        return true;
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$2$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ void m2267lambda$initBindingAndViews$2$comaivoxappfragmentFileFragment(List list) {
        FragmentFileActionListener fragmentFileActionListener = this.mListener;
        if (fragmentFileActionListener != null) {
            fragmentFileActionListener.audioSelectChanged(list);
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$3$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ void m2268lambda$initBindingAndViews$3$comaivoxappfragmentFileFragment() {
        getAudioList(true);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$4$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ void m2269lambda$initBindingAndViews$4$comaivoxappfragmentFileFragment() {
        getAudioList(false);
    }

    public void setInteractionListener(FragmentFileActionListener fragmentFileActionListener) {
        this.mListener = fragmentFileActionListener;
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        if (eventBean.getFrom() != 50) {
            return;
        }
        getAudioList(true);
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
    }

    private void getAudioList(final boolean z) {
        if (z) {
            this.mPage = 1;
        } else {
            this.mPage++;
        }
        if (this.mDis.size() > 0) {
            this.mDis.clear();
        }
        this.mDis.add(this.audioService.getAllAudioList(this.mPage, 20, "", 0).doFinally(new Action() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m2262lambda$getAudioList$5$comaivoxappfragmentFileFragment();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2263lambda$getAudioList$6$comaivoxappfragmentFileFragment(z, (AudioNewAllListBean.DataBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.FileFragment$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2264lambda$getAudioList$7$comaivoxappfragmentFileFragment((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getAudioList$5$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ void m2262lambda$getAudioList$5$comaivoxappfragmentFileFragment() throws Exception {
        if (this.mBinding.refreshView.isRefreshing()) {
            this.mBinding.refreshView.setRefreshing(false);
        }
    }

    /* JADX INFO: renamed from: lambda$getAudioList$6$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ void m2263lambda$getAudioList$6$comaivoxappfragmentFileFragment(boolean z, AudioNewAllListBean.DataBean dataBean) throws Exception {
        if (z) {
            this.mList.clear();
        }
        if (dataBean.getDatas().isEmpty()) {
            this.mAdapter.setEnableLoadMore(false);
        } else {
            this.mList.addAll(dataBean.getDatas());
            this.mAdapter.setEnableLoadMore(this.mList.size() < dataBean.getTotal());
        }
        if (this.mPage == 1) {
            this.mAdapter.setNewData(this.mList);
        } else {
            this.mAdapter.notifyItemRangeChanged(this.mList.size() - dataBean.getDatas().size(), dataBean.getDatas().size());
        }
        this.mAdapter.loadMoreComplete();
    }

    /* JADX INFO: renamed from: lambda$getAudioList$7$com-aivox-app-fragment-FileFragment, reason: not valid java name */
    /* synthetic */ void m2264lambda$getAudioList$7$comaivoxappfragmentFileFragment(Throwable th) throws Exception {
        this.mPage--;
        th.printStackTrace();
    }

    public void quitSelectMode() {
        this.mAdapter.quitAudioSelectMode();
    }

    public void notifyItemChanged(AudioInfoBean audioInfoBean) {
        for (int i = 0; i < this.mList.size(); i++) {
            if (this.mList.get(i) != null && this.mList.get(i).getId() == audioInfoBean.getId()) {
                if (audioInfoBean.getTitle().isEmpty()) {
                    audioInfoBean.setTitle(this.mList.get(i).getTitle());
                }
                this.mList.set(i, audioInfoBean);
                LogUtil.m334d("notifyItemChanged" + audioInfoBean.getTitle() + audioInfoBean.getProgress() + audioInfoBean.getState());
                this.mAdapter.notifyItemChanged(i);
            }
        }
    }
}
