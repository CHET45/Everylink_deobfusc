package com.aivox.app.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.AudioListAdapter;
import com.aivox.app.databinding.ActivitySearchAndMoveBinding;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioNewAllListBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.MultipleRecordItem;
import com.aivox.common.util.AppUtils;
import com.aivox.common.view.CustomLoadMoreView;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class SearchAndMoveActivity extends BaseFragmentActivity {
    private AudioListAdapter mAdapter;
    private ActivitySearchAndMoveBinding mBinding;
    private boolean mCanMoveInFolder;
    private int mTagId;
    private final List<MultipleRecordItem> mList = new ArrayList();
    private final List<AudioInfoBean> mSelectedData = new ArrayList();
    private int mPage = 1;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivitySearchAndMoveBinding activitySearchAndMoveBinding = (ActivitySearchAndMoveBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_search_and_move);
        this.mBinding = activitySearchAndMoveBinding;
        activitySearchAndMoveBinding.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2245lambda$initView$0$comaivoxappactivitySearchAndMoveActivity(view2);
            }
        });
        this.mBinding.ivApply.setOnClickListener(new ViewOnClickListenerC08031());
        this.mBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda1
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return this.f$0.m2246lambda$initView$1$comaivoxappactivitySearchAndMoveActivity(textView, i, keyEvent);
            }
        });
        this.mAdapter = new AudioListAdapter(this.context, this.mList, false);
        this.mBinding.rvList.setLayoutManager(new LinearLayoutManager(this.context));
        this.mAdapter.bindToRecyclerView(this.mBinding.rvList);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mCanMoveInFolder = extras.getBoolean(Constant.KEY_FLAG);
            this.mTagId = extras.getInt(Constant.KEY_IDS);
        }
        if (this.mCanMoveInFolder) {
            this.mBinding.tvTitle.setText(C0874R.string.folder_move_in);
            SwipedSelectItemTouchCallBack swipedSelectItemTouchCallBack = new SwipedSelectItemTouchCallBack();
            swipedSelectItemTouchCallBack.setOnItemTouchListener(this.mAdapter);
            new ItemTouchHelper(swipedSelectItemTouchCallBack).attachToRecyclerView(this.mBinding.rvList);
            this.mAdapter.setNumChangeListener(new AudioListAdapter.OnSelectNumChangedListener() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda2
                @Override // com.aivox.app.adapter.AudioListAdapter.OnSelectNumChangedListener
                public final void onAudioSelectedChanged(List list, boolean z) {
                    this.f$0.m2247lambda$initView$2$comaivoxappactivitySearchAndMoveActivity(list, z);
                }
            });
            this.mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
                public final boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                    return this.f$0.m2248lambda$initView$3$comaivoxappactivitySearchAndMoveActivity(baseQuickAdapter, view2, i);
                }
            });
        }
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2249lambda$initView$4$comaivoxappactivitySearchAndMoveActivity(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setLoadMoreView(new CustomLoadMoreView());
        this.mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public final void onLoadMoreRequested() {
                this.f$0.m2250lambda$initView$5$comaivoxappactivitySearchAndMoveActivity();
            }
        }, this.mBinding.rvList);
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.SearchAndMoveActivity$1 */
    class ViewOnClickListenerC08031 implements View.OnClickListener {
        ViewOnClickListenerC08031() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            DialogUtils.showLoadingDialog(SearchAndMoveActivity.this.context);
            new AudioService(SearchAndMoveActivity.this.context).addToFolder(SearchAndMoveActivity.this.mTagId, SearchAndMoveActivity.this.mSelectedData.stream().mapToInt(new ToIntFunction() { // from class: com.aivox.app.activity.SearchAndMoveActivity$1$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((AudioInfoBean) obj).getId();
                }
            }).toArray()).subscribe(new Consumer() { // from class: com.aivox.app.activity.SearchAndMoveActivity$1$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2251lambda$onClick$0$comaivoxappactivitySearchAndMoveActivity$1(obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.SearchAndMoveActivity$1$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2252lambda$onClick$1$comaivoxappactivitySearchAndMoveActivity$1((Throwable) obj);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onClick$0$com-aivox-app-activity-SearchAndMoveActivity$1, reason: not valid java name */
        /* synthetic */ void m2251lambda$onClick$0$comaivoxappactivitySearchAndMoveActivity$1(Object obj) throws Exception {
            DialogUtils.hideLoadingDialog();
            ToastUtil.showShort(Integer.valueOf(C0874R.string.folder_move_in_success));
            EventBus.getDefault().post(new EventBean(50));
            SearchAndMoveActivity.this.finish();
        }

        /* JADX INFO: renamed from: lambda$onClick$1$com-aivox-app-activity-SearchAndMoveActivity$1, reason: not valid java name */
        /* synthetic */ void m2252lambda$onClick$1$comaivoxappactivitySearchAndMoveActivity$1(Throwable th) throws Exception {
            DialogUtils.hideLoadingDialog();
            AppUtils.checkHttpCode(SearchAndMoveActivity.this.context);
        }
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-SearchAndMoveActivity, reason: not valid java name */
    /* synthetic */ void m2245lambda$initView$0$comaivoxappactivitySearchAndMoveActivity(View view2) {
        finish();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-SearchAndMoveActivity, reason: not valid java name */
    /* synthetic */ boolean m2246lambda$initView$1$comaivoxappactivitySearchAndMoveActivity(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 3) {
            return false;
        }
        DialogUtils.showLoadingDialog(this.context);
        KeyboardUtils.hideSoftInput(this);
        this.mPage = 1;
        m237x49990e95(this.mBinding.etSearch.getEditableText().toString());
        return true;
    }

    /* JADX INFO: renamed from: lambda$initView$2$com-aivox-app-activity-SearchAndMoveActivity, reason: not valid java name */
    /* synthetic */ void m2247lambda$initView$2$comaivoxappactivitySearchAndMoveActivity(List list, boolean z) {
        this.mSelectedData.clear();
        this.mSelectedData.addAll(list);
        this.mBinding.ivApply.setVisibility(list.isEmpty() ? 8 : 0);
    }

    /* JADX INFO: renamed from: lambda$initView$3$com-aivox-app-activity-SearchAndMoveActivity, reason: not valid java name */
    /* synthetic */ boolean m2248lambda$initView$3$comaivoxappactivitySearchAndMoveActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        this.mAdapter.onSwipeSelected(i);
        return true;
    }

    /* JADX INFO: renamed from: lambda$initView$4$com-aivox-app-activity-SearchAndMoveActivity, reason: not valid java name */
    /* synthetic */ void m2249lambda$initView$4$comaivoxappactivitySearchAndMoveActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.mAdapter.isAudioSelectMode() || !AntiShake.check(this)) {
            KeyboardUtils.hideSoftInput(view2);
            int id = view2.getId();
            AudioInfoBean audioInfo = this.mList.get(i).getAudioInfo();
            if (id == C0726R.id.cl_content) {
                if (this.mAdapter.isAudioSelectMode() || this.mCanMoveInFolder) {
                    this.mAdapter.onSwipeSelected(i);
                    return;
                }
                int isTrans = audioInfo.getIsTrans();
                if (audioInfo.getState() == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD.type) {
                    DialogUtils.showDialogWithDefBtnAndSingleListener(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.uploading_and_wait), null, false, true);
                    return;
                }
                if (isTrans == MyEnum.TRANS_STATE.TRANSCRIBED.type) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.AUDIO_ID, audioInfo.getId());
                    bundle.putString(Constant.LOCAL_AUDIO_URL, audioInfo.getLocalPath());
                    bundle.putString(Constant.LOCAL_AUDIO_NAME, audioInfo.getTitle());
                    bundle.putInt(Constant.LOCAL_AUDIO_DURATION, audioInfo.getFileTime());
                    bundle.putBoolean(Constant.IS_LOCAL_AUDIO, false);
                    ARouterUtils.startWithActivity(this, RecordAction.RECORD_INFO, bundle);
                    return;
                }
                if (isTrans == MyEnum.TRANS_STATE.ON_TRANS.type) {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.trans_wait_time));
                } else {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.trans_notice));
                }
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initView$5$com-aivox-app-activity-SearchAndMoveActivity, reason: not valid java name */
    /* synthetic */ void m2250lambda$initView$5$comaivoxappactivitySearchAndMoveActivity() {
        this.mPage++;
        m237x49990e95(this.mBinding.etSearch.getEditableText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: getAudioList, reason: merged with bridge method [inline-methods] */
    public void m237x49990e95(final String str) {
        new AudioService(this.context).getAllAudioList(this.mPage, 20, str, 0).doFinally(new Action() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Action
            public final void run() {
                DialogUtils.hideLoadingDialog();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m236x902180f6(str, (AudioNewAllListBean.DataBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m238x3109c34(str, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAudioList$6$com-aivox-app-activity-SearchAndMoveActivity */
    /* synthetic */ void m236x902180f6(String str, AudioNewAllListBean.DataBean dataBean) throws Exception {
        if (this.mPage == 1) {
            this.mAdapter.setEnableLoadMore(true);
            this.mList.clear();
        }
        for (AudioInfoBean audioInfoBean : dataBean.getDatas()) {
            audioInfoBean.setKeywords(str);
            this.mList.add(new MultipleRecordItem(audioInfoBean));
        }
        this.mAdapter.setNewData(this.mList);
        boolean z = this.mPage * 20 >= dataBean.getTotal();
        this.mAdapter.loadMoreComplete();
        this.mAdapter.setEnableLoadMore(!z);
    }

    /* JADX INFO: renamed from: lambda$getAudioList$8$com-aivox-app-activity-SearchAndMoveActivity */
    /* synthetic */ void m238x3109c34(final String str, Throwable th) throws Exception {
        AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.SearchAndMoveActivity$$ExternalSyntheticLambda6
            @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
            public final void callback() {
                this.f$0.m237x49990e95(str);
            }
        });
    }
}
