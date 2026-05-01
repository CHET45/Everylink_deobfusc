package com.aivox.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.AudioListAdapter;
import com.aivox.app.databinding.FragmentCloudRecordBinding;
import com.aivox.app.listener.OnFragmentInteractionListener;
import com.aivox.app.test.trans.AudioTransModel;
import com.aivox.app.test.trans.AudioUploadModel;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.common.MyEnum;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AiTitleBean;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioNewAllListBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.MultipleRecordItem;
import com.aivox.common.model.UserInfo;
import com.aivox.common.p003db.LocalFileEntityDao;
import com.aivox.common.p003db.SQLiteDataBaseManager;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.LanguageUtils;
import com.aivox.common.util.StringUtil;
import com.aivox.common.view.CustomLoadMoreView;
import com.aivox.common.view.LanguageSelectView;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class RecordListCloudFragment extends BaseBindingFragment {
    private boolean isLoadAll;
    private AudioListAdapter mAudioListAdapter;
    private FragmentCloudRecordBinding mBinding;
    private SelectNumChangListener mChaneListener;
    private OnFragmentInteractionListener mListener;
    private LocalFileDbManager mLocalFileDbManager;
    private UserInfo userInfo;
    private final List<MultipleRecordItem> mList = new ArrayList();
    private int mCurrentTapListPos = 0;
    private int mPage = 1;
    private int mSort = 0;
    private int mFolderTagId = Constant.FOLDER_ID_ALL;
    private boolean isFirst = true;
    private boolean mNeedRefresh = false;
    private CompositeDisposable mDis = new CompositeDisposable();
    Map<Integer, Long> transIdProgress = new HashMap();

    public interface SelectNumChangListener {
        void selectNumChang(int i);
    }

    static /* synthetic */ void lambda$doFavorite$10(Object obj) throws Exception {
    }

    static /* synthetic */ void lambda$doFavorite$7(Object obj) throws Exception {
    }

    public static RecordListCloudFragment newInstance() {
        return new RecordListCloudFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mBinding = FragmentCloudRecordBinding.inflate(layoutInflater, viewGroup, false);
        SQLiteDataBaseManager sQLiteDataBaseManager = new SQLiteDataBaseManager(this.mActivity);
        this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        this.userInfo = sQLiteDataBaseManager.getUserInfo();
        this.mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda9
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public final void onRefresh() {
                this.f$0.initData();
            }
        });
        initAdapter();
        initData();
        return this.mBinding.getRoot();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (((Boolean) SPUtil.get(SPUtil.HAS_BREAK_SAVE_AUDIO, false)).booleanValue()) {
            notifyList();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (z && this.mNeedRefresh) {
            notifyList();
        }
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(EventBean eventBean) {
        super.onEventMainThread(eventBean);
        int from = eventBean.getFrom();
        int i = 0;
        if (from == 79) {
            while (i < this.mList.size()) {
                if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                    this.mList.get(i).getAudioInfo().setExistAiSummary(true);
                    this.mAudioListAdapter.notifyItemChanged(i);
                    return;
                }
                i++;
            }
            return;
        }
        if (from == 80) {
            int a = eventBean.getA();
            AiTitleBean aiTitleBean = (AiTitleBean) eventBean.getT();
            while (i < this.mList.size()) {
                if (this.mList.get(i).getAudioInfo().getId() == a) {
                    this.mList.get(i).getAudioInfo().setTitleStatus(aiTitleBean.getStatus().intValue());
                    this.mList.get(i).getAudioInfo().setTitle(aiTitleBean.getTitle());
                    this.mAudioListAdapter.notifyItemChanged(i);
                    return;
                }
                i++;
            }
            return;
        }
        switch (from) {
            case 200:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_START");
                while (i < this.mList.size()) {
                    if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                        this.mList.get(i).getAudioInfo().setState(MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type);
                        this.mAudioListAdapter.notifyItemChanged(i);
                    } else {
                        i++;
                    }
                    break;
                }
                break;
            case Constant.EVENT.AUDIO_UPLOAD_PROGRESS /* 201 */:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_PROGRESS " + eventBean.getB());
                while (i < this.mList.size()) {
                    if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                        if (eventBean.getB() != 100) {
                            this.mList.get(i).getAudioInfo().setState(MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type);
                        }
                        this.mList.get(i).getAudioInfo().setProgress(eventBean.getB());
                    } else {
                        i++;
                    }
                    break;
                }
                break;
            case Constant.EVENT.AUDIO_UPLOAD_COMPLETE /* 202 */:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_COMPLETE");
                for (int i2 = 0; i2 < this.mList.size(); i2++) {
                    if (this.mList.get(i2).getAudioInfo().getId() == eventBean.getA()) {
                        AudioInfoBean audioInfo = this.mList.get(i2).getAudioInfo();
                        audioInfo.setState(MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_SUCCESS.type);
                        audioInfo.getAudioInfo().setAudioUrl(eventBean.getS1());
                        this.mAudioListAdapter.notifyItemChanged(i2);
                        LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession()).updateAudioUrl(eventBean.getS1(), LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(audioInfo.getId())));
                        if (eventBean.isTrue()) {
                            AudioTransModel.getInstance().startTrans(this.mContext, audioInfo.getId(), audioInfo.getSource());
                        }
                    }
                    break;
                }
                break;
            case Constant.EVENT.AUDIO_UPLOAD_FAILED /* 203 */:
                LogUtil.m337e("TEMP", "AUDIO_UPLOAD_FAILED");
                while (true) {
                    if (i < this.mList.size()) {
                        if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                            this.mList.get(i).getAudioInfo().setState(MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD_FAIL.type);
                            this.mAudioListAdapter.notifyItemChanged(i);
                        } else {
                            i++;
                        }
                    }
                }
                ToastUtil.showLong(eventBean.getS1());
                break;
            default:
                switch (from) {
                    case Constant.EVENT.AUDIO_TRANS_START /* 210 */:
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_START");
                        while (i < this.mList.size()) {
                            if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                                this.mList.get(i).getAudioInfo().setIsTrans(MyEnum.TRANS_STATE.ON_TRANS.type);
                                this.mAudioListAdapter.notifyItemChanged(i);
                            } else {
                                i++;
                            }
                            break;
                        }
                        break;
                    case Constant.EVENT.AUDIO_TRANS_PROGRESS /* 211 */:
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_PROGRESS " + eventBean.getB());
                        break;
                    case 212:
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_COMPLETE");
                        for (int i3 = 0; i3 < this.mList.size(); i3++) {
                            if (this.mList.get(i3).getAudioInfo().getId() == eventBean.getA()) {
                                this.mList.get(i3).getAudioInfo().setIsTrans(MyEnum.TRANS_STATE.TRANSCRIBED.type);
                                this.mAudioListAdapter.notifyItemChanged(i3);
                                LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession()).updateTransStatus(1, LocalFileEntityDao.Properties.Uid.m1944eq(this.userInfo.getUuid()), LocalFileEntityDao.Properties.Vid.m1944eq(Integer.valueOf(eventBean.getA())));
                            }
                            break;
                        }
                        break;
                    case Constant.EVENT.AUDIO_TRANS_FAILED /* 213 */:
                        LogUtil.m337e("TEMP", "AUDIO_TRANS_FAILED");
                        while (true) {
                            if (i < this.mList.size()) {
                                if (this.mList.get(i).getAudioInfo().getId() == eventBean.getA()) {
                                    this.mList.get(i).getAudioInfo().setIsTrans(MyEnum.TRANS_STATE.TRANS_FAIL.type);
                                    this.mAudioListAdapter.notifyItemChanged(i);
                                } else {
                                    i++;
                                }
                            }
                        }
                        ToastUtil.showLong(eventBean.getS1());
                        break;
                }
                break;
        }
    }

    public void setInteractionListener(OnFragmentInteractionListener onFragmentInteractionListener, SelectNumChangListener selectNumChangListener) {
        this.mListener = onFragmentInteractionListener;
        this.mChaneListener = selectNumChangListener;
    }

    private void initAdapter() {
        this.mAudioListAdapter = new AudioListAdapter(this.mContext, this.mList, false);
        this.mBinding.audioRv.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.mAudioListAdapter.bindToRecyclerView(this.mBinding.audioRv);
        this.mAudioListAdapter.setEmptyView(C0874R.layout.include_null_view);
        SwipedSelectItemTouchCallBack swipedSelectItemTouchCallBack = new SwipedSelectItemTouchCallBack();
        swipedSelectItemTouchCallBack.setOnItemTouchListener(this.mAudioListAdapter);
        new ItemTouchHelper(swipedSelectItemTouchCallBack).attachToRecyclerView(this.mBinding.audioRv);
        this.mAudioListAdapter.setNumChangeListener(new AudioListAdapter.OnSelectNumChangedListener() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda3
            @Override // com.aivox.app.adapter.AudioListAdapter.OnSelectNumChangedListener
            public final void onAudioSelectedChanged(List list, boolean z) {
                this.f$0.m297x6efdd99d(list, z);
            }
        });
        this.mAudioListAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda4
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildLongClickListener
            public final boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                return this.f$0.m298xa8c87b7c(baseQuickAdapter, view2, i);
            }
        });
        this.mAudioListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda5
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m299x56286119(baseQuickAdapter, view2, i);
            }
        });
        this.mAudioListAdapter.setEnableLoadMore(true);
        this.mAudioListAdapter.setLoadMoreView(new CustomLoadMoreView());
        this.mAudioListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
            public final void onLoadMoreRequested() {
                this.f$0.getAudioListData();
            }
        }, this.mBinding.audioRv);
    }

    /* JADX INFO: renamed from: lambda$initAdapter$0$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m297x6efdd99d(List list, boolean z) {
        this.mListener.audioSelectChanged(list, z, (this.mFolderTagId == Constant.FOLDER_ID_ALL || this.mFolderTagId == Constant.FOLDER_ID_FAVORITE) ? false : true, this.mFolderTagId == Constant.FOLDER_ID_SHARE, this.mFolderTagId);
        this.mChaneListener.selectNumChang(list.size());
    }

    /* JADX INFO: renamed from: lambda$initAdapter$1$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ boolean m298xa8c87b7c(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        this.mAudioListAdapter.onSwipeSelected(i);
        return true;
    }

    /* JADX INFO: renamed from: lambda$initAdapter$4$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m299x56286119(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.mAudioListAdapter.isAudioSelectMode() || !AntiShake.check(this)) {
            KeyboardUtils.hideSoftInput(view2);
            int id = view2.getId();
            if (baseQuickAdapter.getItemViewType(i) != 1) {
                return;
            }
            this.mCurrentTapListPos = i;
            final AudioInfoBean audioInfo = this.mList.get(i).getAudioInfo();
            if (id == C0726R.id.iv_favorite) {
                switchFavoriteStatus(false);
                return;
            }
            if (id == C0726R.id.cl_content) {
                if (this.mAudioListAdapter.isAudioSelectMode() || this.mFolderTagId == Constant.FOLDER_ID_RECYCLE_BIN) {
                    this.mAudioListAdapter.onSwipeSelected(i);
                    return;
                }
                final int isTrans = audioInfo.getIsTrans();
                if (audioInfo.getState() == MyEnum.AUDIO_UPLOAD_STATE.SYNC2CLOUD.type) {
                    DialogUtils.showDialogWithDefBtnAndSingleListener(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.uploading_and_wait), null, false, true);
                    return;
                }
                if (audioInfo.getId() == DataHandle.getIns().getBreakAudioId()) {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.break_audio_resuming));
                    return;
                }
                if (isTrans == MyEnum.TRANS_STATE.TRANSCRIBED.type) {
                    click2RecordInfo(i);
                    return;
                }
                if (isTrans == MyEnum.TRANS_STATE.ON_TRANS.type || audioInfo.getState() == MyEnum.AUDIO_UPLOAD_STATE.UPLOADING.type) {
                    ToastUtil.showLong(Integer.valueOf(C0874R.string.trans_wait_time));
                    return;
                }
                LogUtil.m334d("click_state:" + audioInfo.getState());
                if (AppUtils.fileTransTimeCheck(getActivity(), audioInfo, DataHandle.getIns().isVip())) {
                    DialogUtils.showDialogWithBtnIds(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.read_after_transcribe), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda1
                        @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                        public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                            RecordListCloudFragment.lambda$initAdapter$3(isTrans, audioInfo, context, dialogBuilder, dialog, i2, i3, editText);
                        }
                    }, true, true, C0874R.string.tips_cancel, C0874R.string.file_word);
                }
            }
        }
    }

    static /* synthetic */ void lambda$initAdapter$3(int i, final AudioInfoBean audioInfoBean, final Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        if (i == MyEnum.TRANS_STATE.NOT_TRANS.type || i == MyEnum.TRANS_STATE.TRANS_FAIL.type) {
            int defaultLangFromLocal = LanguageUtils.getDefaultLangFromLocal(2);
            if (defaultLangFromLocal == MyEnum.TRANSLATE_LANGUAGE.NONE.type) {
                LanguageUtils.showLangSelectView(context, 1, false, new LanguageSelectView.LangSelectListener() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda14
                    @Override // com.aivox.common.view.LanguageSelectView.LangSelectListener
                    public final void onLangSelected(MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
                        RecordListCloudFragment.lambda$initAdapter$2(audioInfoBean, context, translate_language, translate_language2);
                    }
                });
                return;
            }
            audioInfoBean.setSource(defaultLangFromLocal);
            if (audioInfoBean.isLocalAudio()) {
                AudioUploadModel.getInstance().checkAndUpload(context, audioInfoBean, true, true);
            } else {
                AudioTransModel.getInstance().startTrans(context, audioInfoBean.getId(), audioInfoBean.getSource());
            }
        }
    }

    static /* synthetic */ void lambda$initAdapter$2(AudioInfoBean audioInfoBean, Context context, MyEnum.TRANSLATE_LANGUAGE translate_language, MyEnum.TRANSLATE_LANGUAGE translate_language2) {
        if (translate_language == MyEnum.TRANSLATE_LANGUAGE.YUE_HK) {
            translate_language = MyEnum.TRANSLATE_LANGUAGE.YUE;
        }
        audioInfoBean.setSource(translate_language.type);
        if (audioInfoBean.isLocalAudio()) {
            AudioUploadModel.getInstance().checkAndUpload(context, audioInfoBean, true, true);
        } else {
            AudioTransModel.getInstance().startTrans(context, audioInfoBean.getId(), audioInfoBean.getSource());
        }
    }

    private void switchFavoriteStatus(boolean z) {
        AudioInfoBean audioInfo = this.mList.get(this.mCurrentTapListPos).getAudioInfo();
        boolean zAnyMatch = audioInfo.getTagGroupList().stream().anyMatch(new Predicate() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return RecordListCloudFragment.lambda$switchFavoriteStatus$5((AudioInfoBean.TagGroupList) obj);
            }
        });
        if (zAnyMatch) {
            audioInfo.getTagGroupList().removeIf(new Predicate() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return RecordListCloudFragment.lambda$switchFavoriteStatus$6((AudioInfoBean.TagGroupList) obj);
                }
            });
        } else {
            AudioInfoBean.TagGroupList tagGroupList = new AudioInfoBean.TagGroupList();
            tagGroupList.setType(Integer.valueOf(Constant.FOLDER_TYPE_FAVORITE));
            audioInfo.getTagGroupList().add(tagGroupList);
        }
        if (!z) {
            doFavorite(zAnyMatch, audioInfo.getId());
        }
        this.mAudioListAdapter.notifyItemChanged(this.mCurrentTapListPos);
    }

    static /* synthetic */ boolean lambda$switchFavoriteStatus$5(AudioInfoBean.TagGroupList tagGroupList) {
        return tagGroupList.getType().intValue() == Constant.FOLDER_TYPE_FAVORITE;
    }

    static /* synthetic */ boolean lambda$switchFavoriteStatus$6(AudioInfoBean.TagGroupList tagGroupList) {
        return tagGroupList.getType().intValue() == Constant.FOLDER_TYPE_FAVORITE;
    }

    private void doFavorite(boolean z, final int i) {
        int[] iArr = {i};
        if (z) {
            new AudioService(this.mContext).moveOutFromFavorite(String.valueOf(i)).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    RecordListCloudFragment.lambda$doFavorite$7(obj);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda10
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m293xf31bb132(i, (Throwable) obj);
                }
            });
        } else {
            new AudioService(this.mContext).addToFolder(Constant.FOLDER_ID_FAVORITE, iArr).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda11
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    RecordListCloudFragment.lambda$doFavorite$10(obj);
                }
            }, new Consumer() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda12
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m291x532b18a(i, (Throwable) obj);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$doFavorite$9$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m293xf31bb132(final int i, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            AppUtils.showError(this.mContext, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda13
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m292xb9510f53(i);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$doFavorite$8$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m292xb9510f53(int i) {
        doFavorite(true, i);
    }

    /* JADX INFO: renamed from: lambda$doFavorite$12$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m291x532b18a(final int i, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            AppUtils.showError(this.mContext, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda2
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m290xcb680fab(i);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$doFavorite$11$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m290xcb680fab(int i) {
        doFavorite(false, i);
    }

    private void click2RecordInfo(int i) {
        Bundle bundle = new Bundle();
        if (this.mList.size() > i) {
            AudioInfoBean audioInfo = this.mList.get(i).getAudioInfo();
            bundle.putInt(Constant.AUDIO_ID, audioInfo.getId());
            bundle.putString(Constant.LOCAL_AUDIO_URL, audioInfo.getLocalPath());
            bundle.putString(Constant.LOCAL_AUDIO_NAME, audioInfo.getTitle());
            bundle.putInt(Constant.LOCAL_AUDIO_DURATION, audioInfo.getFileTime());
            bundle.putBoolean(Constant.IS_LOCAL_AUDIO, false);
            ARouterUtils.startWithActivity(this.mActivity, RecordAction.RECORD_INFO, bundle);
        }
    }

    public void initData() {
        initData(this.mSort);
    }

    public void initData(int i) {
        this.mSort = i;
        this.mBinding.refreshLayout.setRefreshing(true);
        this.mPage = 1;
        this.isLoadAll = false;
        getAudioListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAudioListData() {
        Observable<AudioNewAllListBean.DataBean> personalAudioList;
        if (this.mFolderTagId == Constant.FOLDER_ID_ALL) {
            personalAudioList = new AudioService(this.mActivity).getAllAudioList(this.mPage, 20, "", this.mSort);
        } else if (this.mFolderTagId == Constant.FOLDER_ID_FAVORITE) {
            personalAudioList = new AudioService(this.mActivity).getAsteriskAudioList(this.mPage, 20, this.mSort);
        } else if (this.mFolderTagId == Constant.FOLDER_ID_RECYCLE_BIN) {
            personalAudioList = new AudioService(this.mActivity).getRecycleAudioList(this.mPage, 20, this.mSort);
        } else {
            personalAudioList = new AudioService(this.mActivity).getPersonalAudioList(this.mFolderTagId, this.mPage, 20, this.mSort);
        }
        this.mDis.add(personalAudioList.doFinally(new Action() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda16
            @Override // io.reactivex.functions.Action
            public final void run() throws Exception {
                this.f$0.m294xe2deb3ca();
            }
        }).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda17
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m295x1ca955a9((AudioNewAllListBean.DataBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda18
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m296x5673f788((Throwable) obj);
            }
        }));
    }

    /* JADX INFO: renamed from: lambda$getAudioListData$13$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m294xe2deb3ca() throws Exception {
        this.mBinding.refreshLayout.setRefreshing(false);
    }

    /* JADX INFO: renamed from: lambda$getAudioListData$14$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m295x1ca955a9(AudioNewAllListBean.DataBean dataBean) throws Exception {
        if (this.mPage == 1) {
            this.mAudioListAdapter.quitAudioSelectMode();
            this.mList.clear();
            if (this.mFolderTagId == Constant.FOLDER_ID_RECYCLE_BIN) {
                this.mListener.audioSelectChanged(new ArrayList(), false, this.mFolderTagId != Constant.FOLDER_ID_ALL, this.mFolderTagId == Constant.FOLDER_ID_SHARE, this.mFolderTagId);
            }
        }
        this.isLoadAll = this.mPage * 20 >= dataBean.getTotal();
        this.mAudioListAdapter.loadMoreComplete();
        this.mAudioListAdapter.setEnableLoadMore(!this.isLoadAll);
        if (this.isLoadAll && !dataBean.getDatas().isEmpty()) {
            dataBean.getDatas().get(dataBean.getDatas().size() - 1).setTheLast(true);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(dataBean.getDatas());
        StringUtil.updateFileTime(this.mLocalFileDbManager, this.userInfo.getUuid(), arrayList);
        StringUtil.updateFileLocalPath(this.mLocalFileDbManager, this.userInfo.getUuid(), arrayList);
        toSort(arrayList);
        if (this.mFolderTagId == Constant.FOLDER_ID_RECYCLE_BIN) {
            EventBus.getDefault().post(new EventBean(503, this.mList.size()));
        }
        if (this.mPage == 1) {
            this.mAudioListAdapter.setNewData(this.mList);
            this.mAudioListAdapter.disableLoadMoreIfNotFullPage();
        } else {
            this.mAudioListAdapter.notifyItemRangeChanged(this.mList.size() - 1, arrayList.size());
        }
        this.isFirst = false;
        this.mPage++;
        DialogUtils.hideLoadingDialog();
    }

    /* JADX INFO: renamed from: lambda$getAudioListData$15$com-aivox-app-fragment-RecordListCloudFragment */
    /* synthetic */ void m296x5673f788(Throwable th) throws Exception {
        int i = this.mPage;
        if (i > 1) {
            this.mPage = i - 1;
        }
        this.mAudioListAdapter.loadMoreComplete();
        this.mAudioListAdapter.setEnableLoadMore(false);
        LogUtil.m338i("thr1:" + th.getLocalizedMessage());
        LogUtil.m338i("RecordListFragment:" + th.getLocalizedMessage());
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.RecordListCloudFragment$$ExternalSyntheticLambda15
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.getAudioListData();
                }
            });
        }
    }

    public void switchTag(int i) {
        this.mFolderTagId = i;
        this.mList.clear();
        this.mDis.clear();
        this.mAudioListAdapter.notifyDataSetChanged();
        initData();
    }

    public void notifyList() {
        markNeedRefresh(false);
        initData();
    }

    public void markNeedRefresh(boolean z) {
        this.mNeedRefresh = z;
    }

    public void notifyItemChanged(AudioInfoBean audioInfoBean) {
        for (int i = 0; i < this.mList.size(); i++) {
            if (this.mList.get(i).getAudioInfo() != null && this.mList.get(i).getAudioInfo().getId() == audioInfoBean.getId()) {
                if (audioInfoBean.getTitle().isEmpty()) {
                    audioInfoBean.setTitle(this.mList.get(i).getAudioInfo().getTitle());
                    audioInfoBean.setTitleStatus(this.mList.get(i).getAudioInfo().getTitleStatus());
                }
                this.mList.get(i).setAudioInfo(audioInfoBean);
                LogUtil.m334d("notifyItemChanged" + audioInfoBean.getTitle() + audioInfoBean.getProgress() + audioInfoBean.getState());
                this.mAudioListAdapter.notifyItemChanged(i);
            }
        }
    }

    private void toSort(List<AudioInfoBean> list) {
        if (!this.transIdProgress.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (this.transIdProgress.containsKey(Integer.valueOf(list.get(i).getId()))) {
                    list.get(i).setIsTrans(MyEnum.TRANS_STATE.ON_TRANS.type);
                }
            }
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.mList.add(new MultipleRecordItem(list.get(i2)));
            if (list.get(i2).getIsTrans() == MyEnum.TRANS_STATE.ON_TRANS.type && this.isFirst) {
                AudioTransModel.getInstance().continueTrans(this.mContext, list.get(i2).getId());
            }
        }
    }

    public int getSortType() {
        return this.mSort;
    }

    public void quitSelectMode() {
        this.mAudioListAdapter.quitAudioSelectMode();
    }

    public int getItemNum() {
        return this.mAudioListAdapter.getData().size();
    }
}
