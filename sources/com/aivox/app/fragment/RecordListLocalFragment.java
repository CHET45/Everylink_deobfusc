package com.aivox.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.AudioListAdapter;
import com.aivox.app.databinding.FragmentLocalRecordBinding;
import com.aivox.app.listener.OnFragmentInteractionListener;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioInfo;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.MultipleRecordItem;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.houbb.heaven.constant.PunctuationConst;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.greenrobot.greendao.query.WhereCondition;

/* JADX INFO: loaded from: classes.dex */
public class RecordListLocalFragment extends BaseBindingFragment {
    private AudioListAdapter mAdapter;
    private FragmentLocalRecordBinding mBinding;
    private String mKeyWord;
    private OnFragmentInteractionListener mListener;
    private LocalFileDbManager mLocalFileDbManager;
    private String mUid;
    private int mLocalPage = 0;
    private final List<MultipleRecordItem> mList = new ArrayList();

    static /* synthetic */ void lambda$initBindingAndViews$1(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
    }

    public static RecordListLocalFragment newInstance() {
        return new RecordListLocalFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUid = (String) SPUtil.get(SPUtil.USER_ID, "0");
        this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (((Boolean) SPUtil.get(SPUtil.HAS_BREAK_SAVE_AUDIO, false)).booleanValue()) {
            getData(true);
        }
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mBinding = FragmentLocalRecordBinding.inflate(layoutInflater, viewGroup, false);
        this.mAdapter = new AudioListAdapter(this.mContext, this.mList, true);
        SwipedSelectItemTouchCallBack swipedSelectItemTouchCallBack = new SwipedSelectItemTouchCallBack();
        swipedSelectItemTouchCallBack.setOnItemTouchListener(this.mAdapter);
        new ItemTouchHelper(swipedSelectItemTouchCallBack).attachToRecyclerView(this.mBinding.rvAudio);
        this.mAdapter.setNumChangeListener(new AudioListAdapter.OnSelectNumChangedListener() { // from class: com.aivox.app.fragment.RecordListLocalFragment.1
            @Override // com.aivox.app.adapter.AudioListAdapter.OnSelectNumChangedListener
            public void onAudioSelectedChanged(List<AudioInfoBean> list, boolean z) {
                RecordListLocalFragment.this.mListener.audioSelectChanged(list, z, false, false, -1);
            }
        });
        this.mAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
            public final boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return this.f$0.m300x39a30246(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m302xe702e7e3(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda7
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public final void onLoadMoreRequested() {
                this.f$0.loadMore();
            }
        }, this.mBinding.rvAudio);
        this.mBinding.rvAudio.setLayoutManager(new LinearLayoutManager(this.mContext));
        ((SimpleItemAnimator) Objects.requireNonNull(this.mBinding.rvAudio.getItemAnimator())).setSupportsChangeAnimations(false);
        this.mBinding.rvAudio.setAdapter(this.mAdapter);
        this.mBinding.srvAudio.setEnabled(true);
        this.mBinding.srvAudio.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda8
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.m303x20cd89c2();
            }
        });
        this.mBinding.srvAudio.autoRefresh();
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-app-fragment-RecordListLocalFragment */
    /* synthetic */ boolean m300x39a30246(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        this.mAdapter.onSwipeSelected(i);
        return true;
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$3$com-aivox-app-fragment-RecordListLocalFragment */
    /* synthetic */ void m302xe702e7e3(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.mAdapter.isAudioSelectMode() || !AntiShake.check(this)) {
            KeyboardUtils.hideSoftInput(view2);
            int id = view2.getId();
            AudioInfoBean audioInfo = this.mList.get(i).getAudioInfo();
            if (id == C0726R.id.cl_1) {
                if (this.mAdapter.isAudioSelectMode()) {
                    this.mAdapter.onSwipeSelected(i);
                    return;
                }
                final int isTrans = audioInfo.getIsTrans();
                if (audioInfo.getState() == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD.type) {
                    DialogUtils.showDialogWithDefBtnAndSingleListener(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.uploading_and_wait), null, false, true);
                    return;
                }
                if (isTrans == MyEnum.TRANS_STATE.TRANSCRIBED.type) {
                    click2RecordInfo(i);
                } else if (isTrans == MyEnum.TRANS_STATE.ON_TRANS.type) {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.trans_wait_time));
                } else {
                    LogUtil.m334d("click_state:" + audioInfo.getState());
                    DialogUtils.showDialogWithBtnIds(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.read_after_transcribe), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda3
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                            RecordListLocalFragment.lambda$initBindingAndViews$1(context, dialogBuilder, dialog, i2, i3, editText);
                        }
                    }, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda4
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                            this.f$0.m301xad384604(isTrans, context, dialogBuilder, dialog, i2, i3, editText);
                        }
                    }, true, true, C0874R.string.tips_cancel, C0874R.string.file_word);
                }
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$2$com-aivox-app-fragment-RecordListLocalFragment */
    /* synthetic */ void m301xad384604(int i, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        if (this.mListener == null || i == MyEnum.TRANS_STATE.NOT_TRANS.type) {
            return;
        }
        int i4 = MyEnum.TRANS_STATE.TRANS_FAIL.type;
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
    }

    public void setInteractionListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        this.mListener = onFragmentInteractionListener;
    }

    /* JADX INFO: renamed from: initData, reason: merged with bridge method [inline-methods] */
    public void m303x20cd89c2() {
        getData(true);
    }

    public void initDataWithKeyWord(String str) {
        this.mKeyWord = str;
        getData(true);
    }

    private void getData(final boolean z) {
        WhereCondition whereConditionLike;
        if (this.mAdapter.isAudioSelectMode()) {
            this.mBinding.srvAudio.setRefreshing(false);
            return;
        }
        if (this.mLocalFileDbManager == null || this.mUid == null) {
            this.mUid = (String) SPUtil.get(SPUtil.USER_ID, "0");
            this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        }
        if (z) {
            this.mLocalPage = 0;
        }
        try {
            LocalFileDbManager localFileDbManager = this.mLocalFileDbManager;
            int i = this.mLocalPage;
            WhereCondition whereConditionM1944eq = LocalFileEntityDao.Properties.Uid.m1944eq(this.mUid);
            WhereCondition[] whereConditionArr = new WhereCondition[1];
            if (TextUtils.isEmpty(this.mKeyWord)) {
                whereConditionLike = LocalFileEntityDao.Properties.Uid.m1944eq(this.mUid);
            } else {
                whereConditionLike = LocalFileEntityDao.Properties.Title.like(PunctuationConst.PERCENT + this.mKeyWord + PunctuationConst.PERCENT);
            }
            whereConditionArr[0] = whereConditionLike;
            final int i2 = 20;
            final List<LocalFileEntity> listQueryLocalListByPage = localFileDbManager.queryLocalListByPage(i, 20, whereConditionM1944eq, whereConditionArr);
            if (listQueryLocalListByPage.isEmpty()) {
                if (this.mLocalPage == 0) {
                    showEmpty();
                }
                this.mBinding.srvAudio.setRefreshing(false);
                this.mAdapter.setEnableLoadMore(false);
                this.mAdapter.loadMoreComplete();
                return;
            }
            hideEmpty();
            ArrayList arrayList = new ArrayList();
            Iterator<LocalFileEntity> it = listQueryLocalListByPage.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(it.next().getVid()));
            }
            if (arrayList.isEmpty()) {
                if (this.mLocalPage == 0) {
                    showEmpty();
                }
                this.mBinding.srvAudio.setRefreshing(false);
                this.mAdapter.setEnableLoadMore(false);
                this.mAdapter.loadMoreComplete();
                return;
            }
            new AudioService(this.mContext).getLocalRecordList(arrayList).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2370lambda$getData$5$comaivoxappfragmentRecordListLocalFragment(z, listQueryLocalListByPage, i2, (List) obj);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m2372lambda$getData$7$comaivoxappfragmentRecordListLocalFragment((Throwable) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            BaseAppUtils.printErrorMsg(e);
        }
    }

    /* JADX INFO: renamed from: lambda$getData$5$com-aivox-app-fragment-RecordListLocalFragment, reason: not valid java name */
    /* synthetic */ void m2370lambda$getData$5$comaivoxappfragmentRecordListLocalFragment(boolean z, List list, int i, List list2) throws Exception {
        if (z) {
            this.mList.clear();
        }
        Iterator it = list2.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            AudioInfoBean audioInfoBean = (AudioInfoBean) it.next();
            audioInfoBean.setKeywords(this.mKeyWord);
            this.mList.add(new MultipleRecordItem(audioInfoBean));
            AudioInfo audioInfo = audioInfoBean.getAudioInfo();
            if (audioInfo != null) {
                this.mLocalFileDbManager.updateAllStatus(!BaseStringUtil.isEmpty(audioInfo.getAudioUrl()) ? 1 : 0, audioInfoBean.getIsTrans(), false, LocalFileEntityDao.Properties.Uid.m1944eq(this.mUid), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(audioInfoBean.getId())));
                this.mLocalFileDbManager.updateTitle(audioInfoBean.getTitle(), LocalFileEntityDao.Properties.Uid.m1944eq(this.mUid), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(audioInfoBean.getId())));
            }
        }
        this.mAdapter.setEnableLoadMore(list.size() == i);
        this.mAdapter.notifyDataSetChanged();
        this.mBinding.srvAudio.setRefreshing(false);
        this.mAdapter.loadMoreComplete();
        if (this.mList.isEmpty()) {
            showEmpty();
        } else {
            hideEmpty();
        }
    }

    /* JADX INFO: renamed from: lambda$getData$7$com-aivox-app-fragment-RecordListLocalFragment, reason: not valid java name */
    /* synthetic */ void m2372lambda$getData$7$comaivoxappfragmentRecordListLocalFragment(Throwable th) throws Exception {
        this.mBinding.srvAudio.setRefreshing(false);
        this.mAdapter.loadMoreComplete();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.RecordListLocalFragment$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m2371lambda$getData$6$comaivoxappfragmentRecordListLocalFragment();
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$getData$6$com-aivox-app-fragment-RecordListLocalFragment, reason: not valid java name */
    /* synthetic */ void m2371lambda$getData$6$comaivoxappfragmentRecordListLocalFragment() {
        getData(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMore() {
        this.mLocalPage++;
        getData(false);
    }

    private void showEmpty() {
        this.mBinding.rvAudio.setVisibility(8);
        this.mBinding.llEmpty.setVisibility(0);
    }

    private void hideEmpty() {
        this.mBinding.rvAudio.setVisibility(0);
        this.mBinding.llEmpty.setVisibility(8);
    }

    public void notifyItemChanged(AudioInfoBean audioInfoBean) {
        for (int i = 0; i < this.mList.size(); i++) {
            if (this.mList.get(i).getAudioInfo().getId() == audioInfoBean.getId()) {
                this.mList.get(i).setAudioInfo(audioInfoBean);
                this.mAdapter.notifyItemChanged(i);
            }
        }
    }

    private void click2RecordInfo(int i) {
        Bundle bundle = new Bundle();
        if (this.mList.size() > i) {
            AudioInfoBean audioInfo = this.mList.get(i).getAudioInfo();
            bundle.putInt(Constant.AUDIO_ID, audioInfo.getId());
            bundle.putString(Constant.LOCAL_AUDIO_URL, audioInfo.getLocalPath());
            bundle.putString(Constant.LOCAL_AUDIO_NAME, audioInfo.getTitle());
            bundle.putInt(Constant.LOCAL_AUDIO_DURATION, audioInfo.getFileTime());
            bundle.putBoolean(Constant.IS_LOCAL_AUDIO, true);
            ARouterUtils.startWithActivity(this.mActivity, RecordAction.RECORD_INFO, bundle);
        }
    }
}
