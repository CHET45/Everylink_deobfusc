package com.aivox.app.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.activity.RecordInfoActivity;
import com.aivox.app.adapter.AiChatAdapter;
import com.aivox.app.adapter.TranscribeAdapter;
import com.aivox.app.databinding.FragmentRecordTranscribeBinding;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.base.C0874R;
import com.aivox.base.common.BaseDataHandle;
import com.aivox.base.common.Constant;
import com.aivox.base.databinding.OnItemClickListener;
import com.aivox.base.databinding.OnItemLongClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DateUtil;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.http.Api;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AiChatBean;
import com.aivox.common.model.AudioContentBean;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.Model;
import com.aivox.common.model.Transcribe;
import com.aivox.common.model.TranscribeListBean;
import com.aivox.common.p003db.entity.LocalFileEntity;
import com.aivox.common.p003db.maneger.LocalFileDbManager;
import com.aivox.common.util.AppUtils;
import com.aivox.common.util.NetworkImageSaver;
import com.aivox.common_ui.BottomEditDialogView;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.noties.markwon.Markwon;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class RecordTranscribeFragment extends BaseBindingFragment {
    private int clickIndex;
    private CountDownTimer countDownTimer;
    private boolean isReClick;
    private int lastIndex;
    private int listMode;
    private AiChatAdapter mAiChatAdapter;
    private boolean mAiSummaryShowing;
    private int mAudioBg;
    private int mAudioId;
    private AudioInfoBean mAudioInfoBean;
    private FragmentRecordTranscribeBinding mBinding;
    private LinearLayoutManager mChatLayoutManager;
    private Disposable mDis;
    private boolean mIsLoading;
    private LinearLayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    private boolean mLoadMoreError;
    private LocalFileDbManager mLocalFileDbManager;
    private boolean mReTranscribing;
    private TranscribeAdapter searchAdapter;
    private CountDownTimer seekbarTimer;
    private int trans;
    private TranscribeAdapter transcribeAdapter;
    private final List<Transcribe> transcribeList = new ArrayList();
    private final List<Transcribe> searchList = new ArrayList();
    private final List<AiChatBean.Records> mAiChatList = new ArrayList();
    private final List<Integer> mReomvePosList = new ArrayList();
    private String mTitle = "";
    private int curSpeakIndex = -1;
    private int mPage = 1;
    private boolean mAppBarNeedCollapse = true;
    private boolean mIsLocal = true;
    private boolean autoScroll = true;
    private String mKeyword = "";

    public interface OnFragmentInteractionListener {
        void doSummaryEdit(AiChatBean.Records records);

        void loadSuccessTranscribe(List<Transcribe> list, boolean z, boolean z2);

        void quitSearchMode();

        void refreshAutoScroll();

        void refreshBar(int i);

        void regenerateSummary(AiChatBean.Records records);

        void showItemPop(Transcribe transcribe, int i, View view2);

        void showSummaryLayout(boolean z);

        void stopAudioWhenScroll();

        void switchContent(int i);

        void transClick(int i, boolean z);

        void transClickBGED(int i, int i2, boolean z, boolean z2, boolean z3);

        void updateCurId(int i);
    }

    static /* synthetic */ void lambda$initBindingAndViews$4(View view2) {
    }

    static /* synthetic */ void lambda$initBindingAndViews$5(View view2) {
    }

    public void onGenerationStarted() {
        this.mBinding.viewCoverSwitch.setVisibility(0);
    }

    public void onGenerationInterrupt() {
        this.mAiChatList.get(r0.size() - 1).setStatus(0);
        this.mAiChatList.get(r0.size() - 1).setAnswer(getString(C0874R.string.try_again));
        this.mAiChatAdapter.notifyItemChanged(this.mAiChatList.size() - 1);
        this.mBinding.viewCoverSwitch.setVisibility(8);
        scrollAiChatToBottom();
    }

    public void onGenerationLimit() {
        this.mAiChatAdapter.notifyItemRemoved(this.mAiChatList.size() - 1);
        this.mAiChatList.remove(r0.size() - 1);
        this.mBinding.viewCoverSwitch.setVisibility(8);
        scrollAiChatToBottom();
    }

    public void onAiGenerateFinish(int i, String str) {
        List<AiChatBean.Records> list = this.mAiChatList;
        list.get(list.size() - 1).setId(Integer.valueOf(i));
        List<AiChatBean.Records> list2 = this.mAiChatList;
        list2.get(list2.size() - 1).setStatus(1);
        List<AiChatBean.Records> list3 = this.mAiChatList;
        list3.get(list3.size() - 1).setAnswer(str);
        this.mAiChatAdapter.notifyItemChanged(this.mAiChatList.size() - 1);
        this.mBinding.viewCoverSwitch.setVisibility(8);
        scrollAiChatToBottom();
        EventBean eventBean = new EventBean(79);
        eventBean.setA(this.mAudioId);
        EventBus.getDefault().post(eventBean);
    }

    public void updateChatContent(int i, String str) {
        for (int i2 = 0; i2 < this.mAiChatList.size(); i2++) {
            if (this.mAiChatList.get(i2).getId().intValue() == i) {
                this.mAiChatList.get(i2).setAnswer(str);
                this.mAiChatAdapter.notifyItemChanged(i2);
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void switchAiChatListMode(int r6) {
        /*
            r5 = this;
            java.util.List<com.aivox.common.model.AiChatBean$Records> r0 = r5.mAiChatList
            boolean r0 = r0.isEmpty()
            r1 = 0
            r2 = 8
            if (r0 == 0) goto L1a
            com.aivox.app.databinding.FragmentRecordTranscribeBinding r0 = r5.mBinding
            android.view.View r0 = r0.viewCoverSwitch
            r0.setVisibility(r2)
            com.aivox.app.databinding.FragmentRecordTranscribeBinding r0 = r5.mBinding
            android.view.View r0 = r0.viewCoverTitle
            r0.setVisibility(r2)
            goto L52
        L1a:
            r0 = 1
            if (r6 == r0) goto L44
            java.util.List<com.aivox.common.model.AiChatBean$Records> r3 = r5.mAiChatList
            int r4 = r3.size()
            int r4 = r4 - r0
            java.lang.Object r3 = r3.get(r4)
            com.aivox.common.model.AiChatBean$Records r3 = (com.aivox.common.model.AiChatBean.Records) r3
            java.lang.Integer r3 = r3.getStatus()
            int r3 = r3.intValue()
            if (r3 == r0) goto L35
            goto L44
        L35:
            com.aivox.app.databinding.FragmentRecordTranscribeBinding r0 = r5.mBinding
            android.view.View r0 = r0.viewCoverSwitch
            r0.setVisibility(r2)
            com.aivox.app.databinding.FragmentRecordTranscribeBinding r0 = r5.mBinding
            android.view.View r0 = r0.viewCoverTitle
            r0.setVisibility(r2)
            goto L52
        L44:
            com.aivox.app.databinding.FragmentRecordTranscribeBinding r0 = r5.mBinding
            android.view.View r0 = r0.viewCoverSwitch
            r0.setVisibility(r1)
            com.aivox.app.databinding.FragmentRecordTranscribeBinding r0 = r5.mBinding
            android.view.View r0 = r0.viewCoverTitle
            r0.setVisibility(r1)
        L52:
            com.aivox.app.adapter.AiChatAdapter r0 = r5.mAiChatAdapter
            r0.switchMode(r6, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.app.fragment.RecordTranscribeFragment.switchAiChatListMode(int):void");
    }

    public List<Integer> getSelectedChatId() {
        this.mReomvePosList.clear();
        for (int i = 0; i < this.mAiChatList.size(); i++) {
            if (this.mAiChatAdapter.getSelectIdList().contains(this.mAiChatList.get(i).getId())) {
                this.mReomvePosList.add(Integer.valueOf(i));
            }
        }
        return this.mAiChatAdapter.getSelectIdList();
    }

    public void removeSelectChatId() {
        this.mReomvePosList.sort(Collections.reverseOrder());
        Iterator<Integer> it = this.mReomvePosList.iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            this.mAiChatList.remove(iIntValue);
            this.mAiChatAdapter.notifyItemRemoved(iIntValue);
        }
        this.mBinding.rvChatList.setVisibility(this.mAiChatList.isEmpty() ? 4 : 0);
    }

    public static RecordTranscribeFragment getInstance(AudioInfoBean audioInfoBean, int i, boolean z) {
        RecordTranscribeFragment recordTranscribeFragment = new RecordTranscribeFragment();
        recordTranscribeFragment.mAudioInfoBean = audioInfoBean;
        recordTranscribeFragment.mAudioId = audioInfoBean.getId();
        recordTranscribeFragment.trans = audioInfoBean.getIsTrans();
        recordTranscribeFragment.mAudioBg = i;
        recordTranscribeFragment.mIsLocal = z;
        return recordTranscribeFragment;
    }

    public void setInteractionListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        this.mListener = onFragmentInteractionListener;
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mBinding = FragmentRecordTranscribeBinding.inflate(layoutInflater, viewGroup, false);
        this.mLocalFileDbManager = LocalFileDbManager.getInstance(AppApplication.getIns().getDaoSession());
        Markwon markwonCreate = Markwon.create(this.mContext);
        this.mLayoutManager = new LinearLayoutManager(this.mContext);
        this.mChatLayoutManager = new LinearLayoutManager(this.mContext, 1, false);
        this.mBinding.rvTransList.setLayoutManager(this.mLayoutManager);
        this.mBinding.rvChatList.setLayoutManager(this.mChatLayoutManager);
        this.mBinding.rvSearchList.setLayoutManager(new LinearLayoutManager(this.mContext));
        setTransShow();
        this.mBinding.btnToTranscribe.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m305x5a85dd1c(view2);
            }
        });
        this.transcribeAdapter = new TranscribeAdapter(this.mActivity, C0726R.layout.item_transcribe, false);
        this.searchAdapter = new TranscribeAdapter(this.mActivity, C0726R.layout.item_transcribe);
        this.mAiChatAdapter = new AiChatAdapter(C0726R.layout.item_ai_chat, markwonCreate);
        this.mBinding.rvTransList.setAdapter(this.transcribeAdapter);
        this.mBinding.rvSearchList.setAdapter(this.searchAdapter);
        this.mBinding.rvChatList.setAdapter(this.mAiChatAdapter);
        this.searchAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda16
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i) {
                this.f$0.m306x5a0f771d(view2, i);
            }
        });
        AudioInfoBean audioInfoBean = this.mAudioInfoBean;
        if (audioInfoBean != null && audioInfoBean.getAudioInfo() != null) {
            setAvatarStyle(this.mAudioInfoBean.getAudioInfo().getSpeakerAvatarStyle().intValue());
        }
        SwipedSelectItemTouchCallBack swipedSelectItemTouchCallBack = new SwipedSelectItemTouchCallBack();
        swipedSelectItemTouchCallBack.setOnItemTouchListener(this.transcribeAdapter);
        new ItemTouchHelper(swipedSelectItemTouchCallBack).attachToRecyclerView(this.mBinding.rvTransList);
        this.mBinding.rvTransList.getItemAnimator().setChangeDuration(0L);
        this.transcribeAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda17
            @Override // com.aivox.base.databinding.OnItemClickListener
            public final void onItemClick(View view2, int i) {
                this.f$0.m307x5999111e(view2, i);
            }
        });
        this.transcribeAdapter.setOnItemLongClickListener(new OnItemLongClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda18
            @Override // com.aivox.base.databinding.OnItemLongClickListener
            public final void onItemLongClick(View view2, int i) {
                this.f$0.m308x5922ab1f(view2, i);
            }
        });
        this.mBinding.rvTransList.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    RecordTranscribeFragment.this.startBarRefresh();
                } else if (i == 1) {
                    RecordTranscribeFragment.this.startTimeCount();
                    RecordTranscribeFragment.this.mListener.refreshBar(8);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (RecordTranscribeFragment.this.mLayoutManager.findLastVisibleItemPosition() < RecordTranscribeFragment.this.mLayoutManager.getItemCount() - 5 || RecordTranscribeFragment.this.mIsLoading || !RecordTranscribeFragment.this.mLoadMoreError) {
                    return;
                }
                RecordTranscribeFragment.this.loadMoreTrans();
            }
        });
        this.mBinding.tvRecordTitle.setOnClickListener(new ViewOnClickListenerC08282());
        this.mBinding.viewCoverTitle.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RecordTranscribeFragment.lambda$initBindingAndViews$4(view2);
            }
        });
        this.mBinding.viewCoverSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RecordTranscribeFragment.lambda$initBindingAndViews$5(view2);
            }
        });
        this.mBinding.tvSwitchContent.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m309x57bf7922(view2);
            }
        });
        this.mBinding.tvSwitchSummary.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m310x57491323(view2);
            }
        });
        this.mAiChatAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda3
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m311x56d2ad24(baseQuickAdapter, view2, i);
            }
        });
        if (this.trans == 1) {
            reqTranscribeResult();
        }
        getAiSummary();
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m305x5a85dd1c(View view2) {
        if (getActivity() instanceof RecordInfoActivity) {
            ((RecordInfoActivity) getActivity()).toShowTransView();
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$1$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m306x5a0f771d(View view2, int i) {
        int id = this.searchList.get(i).getId();
        int i2 = 0;
        while (true) {
            if (i2 >= this.transcribeList.size()) {
                i2 = 0;
                break;
            } else if (this.transcribeList.get(i2).getId() == id) {
                break;
            } else {
                i2++;
            }
        }
        this.mBinding.rvTransList.scrollToPosition(i2);
        this.mBinding.rvTransList.scrollBy(0, 500);
        this.mListener.quitSearchMode();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$2$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m307x5999111e(View view2, int i) {
        if (AntiShake.check(view2, 5000)) {
            return;
        }
        if (view2.getId() == C0726R.id.rcv_imgs) {
            OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
            if (onFragmentInteractionListener != null) {
                onFragmentInteractionListener.updateCurId(this.transcribeList.get(i).getId());
                return;
            }
            return;
        }
        OnFragmentInteractionListener onFragmentInteractionListener2 = this.mListener;
        if (onFragmentInteractionListener2 != null) {
            onFragmentInteractionListener2.transClickBGED(this.transcribeList.get(i).getId(), Integer.parseInt(this.transcribeList.get(i).getBeginAt()), this.transcribeList.get(i).getCurSpeakIndex() == i, this.isReClick, false);
            if (this.transcribeList.get(i).getCurSpeakIndex() == i) {
                this.isReClick = !this.isReClick;
            } else {
                this.isReClick = false;
            }
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$3$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m308x5922ab1f(View view2, int i) {
        if (this.mListener == null || BaseStringUtil.isEmpty(this.transcribeList.get(i).getOnebest())) {
            return;
        }
        this.clickIndex = i;
        setAutoScroll(false);
        this.transcribeAdapter.notifyItemChanged(this.clickIndex);
        this.mListener.showItemPop(this.transcribeList.get(i), i, view2);
    }

    /* JADX INFO: renamed from: com.aivox.app.fragment.RecordTranscribeFragment$2 */
    class ViewOnClickListenerC08282 implements View.OnClickListener {
        ViewOnClickListenerC08282() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(RecordTranscribeFragment.this.mContext, new BottomEditDialogView.OnBtnClickListener() { // from class: com.aivox.app.fragment.RecordTranscribeFragment.2.1
                @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
                public void onLeftBtnClick() {
                }

                @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
                public void onSaveBtnClick(String str) {
                    if (!TextUtils.isEmpty(str)) {
                        RecordTranscribeFragment.this.mTitle = str;
                        RecordTranscribeFragment.this.mBinding.tvRecordTitle.setText(RecordTranscribeFragment.this.mTitle);
                        RecordTranscribeFragment.this.m318xf733d109(RecordTranscribeFragment.this.mAudioId);
                        return;
                    }
                    ToastUtil.showShort(Integer.valueOf(C0874R.string.title_can_not_be_empty));
                }
            });
            bottomEditDialogView.setDialogContent(RecordTranscribeFragment.this.getString(C0874R.string.record_info_dialog_file_rename), RecordTranscribeFragment.this.getString(C0874R.string.record_info_dialog_file_name), RecordTranscribeFragment.this.getString(C0874R.string.record_info_dialog_file_name_hint), RecordTranscribeFragment.this.mTitle, "");
            DialogUtils.showBottomSheetDialog(RecordTranscribeFragment.this.mContext, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
            bottomEditDialogView.postDelayed(new Runnable() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyboardUtils.showSoftInput();
                }
            }, 200L);
        }
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$6$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m309x57bf7922(View view2) {
        showAiSummary(false);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$7$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m310x57491323(View view2) {
        showAiSummary(true);
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$8$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m311x56d2ad24(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        if (this.mAiChatAdapter.getMode() == 1) {
            this.mAiChatAdapter.selectItem(i);
            return;
        }
        if (view2.getId() == C0726R.id.iv_ai_answer_edit) {
            if (this.mAiChatList.get(i).getStatus().intValue() == 0) {
                ToastUtil.showShort(Integer.valueOf(C0874R.string.try_again));
                return;
            } else {
                this.mListener.doSummaryEdit(this.mAiChatList.get(i));
                return;
            }
        }
        if (view2.getId() == C0726R.id.iv_ai_answer_copy) {
            BaseStringUtil.putTextIntoClip(this.mContext, this.mAiChatList.get(i).getAnswer());
            return;
        }
        if (view2.getId() == C0726R.id.iv_ai_answer_regenerate) {
            List<AiChatBean.Records> list = this.mAiChatList;
            if (list.get(list.size() - 1).getStatus().intValue() != -1) {
                List<AiChatBean.Records> list2 = this.mAiChatList;
                if (list2.get(list2.size() - 1).getStatus().intValue() != 3) {
                    this.mListener.regenerateSummary(this.mAiChatList.get(i));
                    return;
                }
                return;
            }
            return;
        }
        if (view2.getId() == C0726R.id.iv_fullscreen) {
            Transcribe transcribe = new Transcribe();
            Transcribe.TagImgBean tagImgBean = new Transcribe.TagImgBean(0, this.mAiChatList.get(i).getAnswer());
            ArrayList arrayList = new ArrayList();
            arrayList.add(tagImgBean);
            transcribe.setImageList(arrayList);
            Bundle bundle = new Bundle();
            bundle.putSerializable("transcribe", transcribe);
            bundle.putBoolean("showDelete", false);
            bundle.putInt("pos", 0);
            ARouterUtils.startWithContext(this.mContext, MainAction.PHOTO_BROWSE, bundle);
            return;
        }
        if (view2.getId() == C0726R.id.iv_ai_mind_map_download) {
            NetworkImageSaver.saveImageFromNetwork(this.mAiChatList.get(i).getAnswer(), false);
        } else if (view2.getId() == C0726R.id.iv_ai_mind_map_send) {
            NetworkImageSaver.saveImageFromNetwork(this.mAiChatList.get(i).getAnswer(), true);
        }
    }

    public String getAiSummaryContent() {
        return this.mAiChatList.isEmpty() ? "" : this.mAiChatList.get(0).getAnswer();
    }

    public int getLatestAiChatId() {
        if (this.mAiChatList.isEmpty()) {
            return 0;
        }
        return this.mAiChatList.get(r0.size() - 1).getId().intValue();
    }

    public boolean transIsEmpty() {
        return this.transcribeList.isEmpty();
    }

    public void getAiSummary() {
        new AudioService(this.mContext).getAiChatList(this.mAudioId, 1, 20).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m304x7ec1fa1b((AiChatBean) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getAiSummary$9$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m304x7ec1fa1b(AiChatBean aiChatBean) throws Exception {
        DialogUtils.hideLoadingDialog();
        this.mAiChatList.addAll(aiChatBean.getRecords());
        this.mAiChatAdapter.setNewData(this.mAiChatList);
        this.mBinding.rvChatList.setVisibility(this.mAiChatList.isEmpty() ? 4 : 0);
        scrollAiChatToBottom();
        if (CollectionUtils.isNotEmpty(this.mAiChatList)) {
            if (this.mAiChatList.get(r3.size() - 1).getStatus().intValue() == 3) {
                EventBus.getDefault().post(new EventBean(83));
            }
        }
    }

    public void refreshListAfterChangeImgTag(int i, Transcribe.TagImgBean tagImgBean, boolean z) {
        for (int i2 = 0; i2 < this.transcribeList.size(); i2++) {
            if (this.transcribeList.get(i2).getId() == i) {
                if (z) {
                    this.transcribeList.get(i2).getImageList().add(tagImgBean);
                } else {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= this.transcribeList.get(i2).getImageList().size()) {
                            break;
                        }
                        if (this.transcribeList.get(i2).getImageList().get(i3).getId() == tagImgBean.getId()) {
                            this.transcribeList.get(i2).getImageList().remove(i3);
                            break;
                        }
                        i3++;
                    }
                }
                this.transcribeAdapter.notifyItemChanged(i2);
            }
        }
    }

    public void refreshListAfterChangeImgTagById(int i, int i2) {
        for (int i3 = 0; i3 < this.transcribeList.size(); i3++) {
            if (this.transcribeList.get(i3).getId() == i) {
                int i4 = 0;
                while (true) {
                    if (i4 >= this.transcribeList.get(i3).getImageList().size()) {
                        break;
                    }
                    if (this.transcribeList.get(i3).getImageList().get(i4).getId() == i2) {
                        this.transcribeList.get(i3).getImageList().remove(i4);
                        break;
                    }
                    i4++;
                }
                if (this.transcribeList.get(i3).getImageList().isEmpty() && BaseStringUtil.isEmpty(this.transcribeList.get(i3).getOnebest())) {
                    m315xe1b5ae6(i3);
                } else {
                    this.transcribeAdapter.notifyItemChanged(i3);
                }
            }
        }
    }

    private void showAiSummary(boolean z) {
        this.mAiSummaryShowing = z;
        this.mBinding.tvSwitchContent.setBackgroundResource(z ? 0 : C1034R.drawable.bg_switch_select);
        this.mBinding.tvSwitchSummary.setBackgroundResource(z ? C1034R.drawable.bg_switch_select : 0);
        this.mBinding.tvSwitchContent.setTextColor(this.mContext.getColor(z ? C0874R.color.txt_color_primary : C0874R.color.txt_color_secondary));
        this.mBinding.tvSwitchSummary.setTextColor(this.mContext.getColor(z ? C0874R.color.txt_color_secondary : C0874R.color.txt_color_primary));
        this.mBinding.rlChatLayout.setVisibility(z ? 0 : 8);
        this.mBinding.rvTransList.setVisibility(z ? 8 : 0);
        this.mListener.switchContent(z ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: reqEditRecord, reason: merged with bridge method [inline-methods] */
    public void m318xf733d109(final int i) {
        if (this.mAudioInfoBean == null) {
            return;
        }
        new AudioService(this.mContext).editRecordInfo(i, this.mTitle).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m317xf7aa3708((AudioInfoBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda5
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m319xf6bd6b0a(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$10$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m317xf7aa3708(AudioInfoBean audioInfoBean) throws Exception {
        ToastUtil.showShort(Integer.valueOf(C0874R.string.modifyuserinfo_success));
        this.mAudioInfoBean = audioInfoBean;
        if (this.mIsLocal) {
            this.mLocalFileDbManager.insertOrReplace(new LocalFileEntity(audioInfoBean));
        }
        EventBus.getDefault().post(new EventBean(50));
    }

    /* JADX INFO: renamed from: lambda$reqEditRecord$12$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m319xf6bd6b0a(final int i, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            AppUtils.showError(this.mContext, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda12
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m318xf733d109(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimeCount() {
        this.autoScroll = false;
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(5000L, 500L) { // from class: com.aivox.app.fragment.RecordTranscribeFragment.3
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (RecordTranscribeFragment.this.mListener != null) {
                    RecordTranscribeFragment.this.mListener.refreshAutoScroll();
                }
            }
        };
        this.countDownTimer = countDownTimer2;
        countDownTimer2.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBarRefresh() {
        this.mListener.refreshBar(8);
        CountDownTimer countDownTimer = this.seekbarTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        CountDownTimer countDownTimer2 = new CountDownTimer(500L, 500L) { // from class: com.aivox.app.fragment.RecordTranscribeFragment.4
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (RecordTranscribeFragment.this.mListener != null) {
                    RecordTranscribeFragment.this.mListener.refreshBar(0);
                }
            }
        };
        this.seekbarTimer = countDownTimer2;
        countDownTimer2.start();
    }

    public void setAutoScroll(boolean z) {
        this.autoScroll = z;
        if (z) {
            int size = this.transcribeList.size();
            int i = this.clickIndex;
            if (size > i) {
                this.transcribeAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override // com.aivox.common.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void setTrans(int i) {
        this.trans = i;
        setTransShow();
        if (i == 1) {
            reqTranscribeResult();
        }
    }

    public void setAudioInfo(AudioInfoBean audioInfoBean) {
        this.mAudioInfoBean = audioInfoBean;
        if (BaseStringUtil.isEmpty(audioInfoBean.getSite())) {
            this.mBinding.tvRecordLocation.setVisibility(4);
        } else {
            this.mBinding.tvRecordLocation.setText(this.mAudioInfoBean.getSite());
            this.mBinding.tvRecordLocation.setVisibility(0);
        }
    }

    public void setCurTime(long j) {
        if (this.transcribeList.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.transcribeList.size(); i++) {
            long j2 = Long.parseLong(this.transcribeList.get(i).getBeginAt());
            long j3 = Long.parseLong(this.transcribeList.get(i).getEndAt());
            if (j >= j2 && j <= j3) {
                this.curSpeakIndex = i;
            }
        }
        int i2 = this.lastIndex;
        int i3 = this.curSpeakIndex;
        if (i2 != i3 || i3 == 0) {
            Iterator<Transcribe> it = this.transcribeList.iterator();
            while (it.hasNext()) {
                it.next().setCurSpeakIndex(this.curSpeakIndex);
            }
            TranscribeAdapter transcribeAdapter = this.transcribeAdapter;
            if (transcribeAdapter != null && this.listMode != 2) {
                transcribeAdapter.notifyDataSetChanged();
            }
            FragmentRecordTranscribeBinding fragmentRecordTranscribeBinding = this.mBinding;
            if (fragmentRecordTranscribeBinding != null && this.autoScroll && this.curSpeakIndex != -1 && this.listMode != 2) {
                fragmentRecordTranscribeBinding.rvTransList.scrollToPosition(this.curSpeakIndex);
                this.mBinding.appbar.setExpanded(false, true);
            }
            this.lastIndex = this.curSpeakIndex;
        }
    }

    public void batchUpdate(List<Transcribe> list) {
        for (int i = 0; i < this.transcribeList.size(); i++) {
            for (Transcribe transcribe : list) {
                if (transcribe.getId() == this.transcribeList.get(i).getId()) {
                    this.transcribeList.set(i, transcribe);
                    this.transcribeAdapter.notifyItemChanged(i);
                }
            }
        }
    }

    public void batchDelete(String str) {
        for (Transcribe transcribe : new ArrayList(this.transcribeList)) {
            if (str.contains(String.valueOf(transcribe.getId()))) {
                this.transcribeAdapter.notifyItemRemoved(this.transcribeList.indexOf(transcribe));
                this.transcribeList.remove(transcribe);
            }
        }
        OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.loadSuccessTranscribe(this.transcribeList, false, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reqTranscribeResult() {
        this.mPage = 1;
        this.mIsLoading = false;
        new AudioService(this.mActivity).getTransResultByPage(this.mAudioId, this.mPage).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m320x1c9c34f4((TranscribeListBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m321x1c25cef5((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqTranscribeResult$13$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m320x1c9c34f4(TranscribeListBean transcribeListBean) throws Exception {
        DialogUtils.hideLoadingDialog();
        this.transcribeList.clear();
        List<Transcribe> records = transcribeListBean.getRecords();
        if (records == null) {
            records = new ArrayList<>();
        }
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setCurrentStartTime(DateUtil.getDateFromTimestamp(DateUtil.getTimestampFromDate(DateUtil.utc2Local(this.mAudioInfoBean.getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.YYYY_MM_DD_HH_MM_SS) + Long.parseLong(records.get(i).getBeginAt()), DateUtil.HH_MM_SS));
        }
        this.transcribeList.addAll(records);
        this.transcribeAdapter.setmDate(this.transcribeList);
        this.mTitle = this.mAudioInfoBean.getTitle();
        this.mBinding.clAudioTitle.setVisibility(0);
        if (BaseStringUtil.isEmpty(this.mTitle)) {
            this.mBinding.tvRecordTitle.setText(this.mAudioInfoBean.getStartTime().replace(ExifInterface.GPS_DIRECTION_TRUE, " "));
        } else {
            this.mBinding.tvRecordTitle.setText(this.mTitle);
        }
        this.mBinding.tvRecordDate.setText(DateUtil.getDateForDetail(this.mAudioInfoBean.getStartTime()) + " • ");
        this.mBinding.llSwitch.setVisibility(0);
        if (BaseStringUtil.isEmpty(this.mAudioInfoBean.getSite())) {
            this.mBinding.tvRecordTime.setText(DateUtil.numberToTime(this.mAudioInfoBean.getFileTime()));
            this.mBinding.tvRecordLocation.setVisibility(4);
        } else {
            this.mBinding.tvRecordTime.setText(DateUtil.numberToTime(this.mAudioInfoBean.getFileTime()) + " • ");
            this.mBinding.tvRecordLocation.setText(this.mAudioInfoBean.getSite());
            this.mBinding.tvRecordLocation.setVisibility(0);
        }
        this.transcribeAdapter.setOnCheckChangeListener((TranscribeAdapter.OnCheckChangeListener) getActivity());
        if (this.mAudioBg != -1) {
            for (int i2 = 0; i2 < this.transcribeList.size(); i2++) {
                if (this.mBinding != null && this.mAudioBg == Integer.parseInt(this.transcribeList.get(i2).getBeginAt())) {
                    this.mBinding.rvTransList.scrollToPosition(i2);
                    return;
                }
            }
        }
        OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.loadSuccessTranscribe(this.transcribeList, true, false);
        }
        loadMoreTrans();
    }

    /* JADX INFO: renamed from: lambda$reqTranscribeResult$14$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m321x1c25cef5(Throwable th) throws Exception {
        if (BaseDataHandle.getIns().getCode() == Constant.SeverErrorCode.THE_AUDIO_TRANSCRIBED_INEXISTENCE.code) {
            ToastUtil.showTextToast(this.mActivity, BaseDataHandle.getIns().getMsg());
        } else if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda0
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.reqTranscribeResult();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMoreTrans() {
        if (this.mReTranscribing) {
            return;
        }
        this.mIsLoading = true;
        this.mPage++;
        this.mDis = new AudioService(this.mActivity).getTransResultByPage(this.mAudioId, this.mPage).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m312xc1af9cb9((TranscribeListBean) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda13
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m313xc13936ba((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$loadMoreTrans$15$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m312xc1af9cb9(TranscribeListBean transcribeListBean) throws Exception {
        this.mIsLoading = false;
        this.mLoadMoreError = false;
        if (CollectionUtils.isEmpty(transcribeListBean.getRecords())) {
            OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
            if (onFragmentInteractionListener != null) {
                onFragmentInteractionListener.loadSuccessTranscribe(this.transcribeList, false, true);
                return;
            }
            return;
        }
        for (int i = 0; i < transcribeListBean.getRecords().size(); i++) {
            transcribeListBean.getRecords().get(i).setCurrentStartTime(DateUtil.getDateFromTimestamp(DateUtil.getTimestampFromDate(DateUtil.utc2Local(this.mAudioInfoBean.getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.YYYY_MM_DD_HH_MM_SS) + Long.parseLong(transcribeListBean.getRecords().get(i).getBeginAt()), DateUtil.HH_MM_SS));
        }
        this.transcribeList.addAll(transcribeListBean.getRecords());
        this.transcribeAdapter.setmDate(this.transcribeList);
        this.searchList.clear();
        for (Transcribe transcribe : this.transcribeList) {
            if ((transcribe.getOnebest() + transcribe.getTranslate()).contains(this.mKeyword) && BaseStringUtil.isNotEmpty(this.mKeyword)) {
                this.searchList.add(transcribe);
            }
        }
        this.searchAdapter.setmDate(this.searchList);
        OnFragmentInteractionListener onFragmentInteractionListener2 = this.mListener;
        if (onFragmentInteractionListener2 != null) {
            onFragmentInteractionListener2.loadSuccessTranscribe(this.transcribeList, false, false);
        }
        this.mIsLoading = false;
        loadMoreTrans();
    }

    /* JADX INFO: renamed from: lambda$loadMoreTrans$16$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m313xc13936ba(Throwable th) throws Exception {
        LogUtil.m338i("thr:" + th.getLocalizedMessage());
        DialogUtils.hideLoadingDialog();
        this.mPage--;
        this.mIsLoading = false;
        this.mLoadMoreError = true;
    }

    public void setSummaryContent(AiChatBean.Records records) {
        if (this.mBinding == null) {
            return;
        }
        this.mAiChatList.add(records);
        if (this.mBinding.rvChatList.getVisibility() == 4) {
            this.mBinding.rvChatList.setVisibility(0);
        }
        this.mAiChatAdapter.notifyItemChanged(r3.getItemCount() - 1);
        if (this.mAppBarNeedCollapse) {
            this.mAppBarNeedCollapse = false;
            this.mBinding.appbar.setExpanded(false);
        }
        scrollAiChatToBottom();
    }

    private void scrollAiChatToBottom() {
        this.mChatLayoutManager.scrollToPositionWithOffset(this.mAiChatAdapter.getItemCount() - 1, -10000);
    }

    public void setReTranscribingState(boolean z) {
        this.mReTranscribing = z;
    }

    public void refreshKeywords(String str) {
        this.mKeyword = str;
        if (this.transcribeList.size() > 0) {
            Iterator<Transcribe> it = this.transcribeList.iterator();
            while (it.hasNext()) {
                it.next().setKeywords(str);
            }
            this.transcribeAdapter.notifyDataSetChanged();
            this.searchList.clear();
            for (Transcribe transcribe : this.transcribeList) {
                if ((transcribe.getOnebest() + transcribe.getTranslate()).contains(str) && BaseStringUtil.isNotEmpty(str)) {
                    this.searchList.add(transcribe);
                }
            }
            this.searchAdapter.setmDate(this.searchList);
        }
    }

    /* JADX INFO: renamed from: reqDelTxt, reason: merged with bridge method [inline-methods] */
    public void m315xe1b5ae6(final int i) {
        new Api(this.mActivity).deleteVideoTxt(this.transcribeList.get(i).getId()).subscribe(new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m314xe91c0e5(i, (Model) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m316xda4f4e7(i, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reqDelTxt$17$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m314xe91c0e5(int i, Model model) throws Exception {
        this.transcribeAdapter.remove(i, i);
        OnFragmentInteractionListener onFragmentInteractionListener = this.mListener;
        if (onFragmentInteractionListener != null) {
            onFragmentInteractionListener.loadSuccessTranscribe(this.transcribeList, true, true);
        }
    }

    /* JADX INFO: renamed from: lambda$reqDelTxt$19$com-aivox-app-fragment-RecordTranscribeFragment */
    /* synthetic */ void m316xda4f4e7(final int i, Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mActivity);
        } else {
            AppUtils.showError(this.mActivity, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.RecordTranscribeFragment$$ExternalSyntheticLambda14
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m315xe1b5ae6(i);
                }
            });
        }
    }

    public void setTransShow() {
        FragmentRecordTranscribeBinding fragmentRecordTranscribeBinding = this.mBinding;
        if (fragmentRecordTranscribeBinding == null) {
            return;
        }
        try {
            if (this.trans == 0) {
                fragmentRecordTranscribeBinding.btnToTranscribe.setVisibility(0);
                this.mBinding.rvTransList.setVisibility(8);
            } else {
                fragmentRecordTranscribeBinding.btnToTranscribe.setVisibility(8);
                this.mBinding.rvTransList.setVisibility(0);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void changeMode(int i) {
        if (i == 3) {
            this.mBinding.rvSearchList.setVisibility(0);
            return;
        }
        this.mBinding.rvSearchList.setVisibility(8);
        this.listMode = i;
        if (!this.mAiSummaryShowing) {
            this.transcribeAdapter.changeMode(i);
        } else if (i == 2) {
            this.mListener.showSummaryLayout(false);
        } else {
            this.mListener.showSummaryLayout(true);
        }
        this.mBinding.viewCoverTitle.setVisibility(this.listMode == 0 ? 8 : 0);
        this.mBinding.viewCoverSwitch.setVisibility(this.listMode == 0 ? 8 : 0);
    }

    public void setAvatarStyle(int i) {
        this.transcribeAdapter.setAvatarStyle(i);
        this.transcribeAdapter.notifyDataSetChanged();
    }

    public void selectFirst() {
        if (this.transcribeList.size() > 0) {
            this.transcribeAdapter.onSwipeSelected(0);
        }
    }

    public String getIds(int i) {
        return this.transcribeAdapter.getPickIds(i);
    }

    public String getTexts() {
        return this.transcribeAdapter.getPickTexts();
    }

    public List<AudioContentBean.DataBean> getEditData() {
        return this.transcribeAdapter.getEditData();
    }

    public void updateListData() {
        this.transcribeAdapter.notifyDataSetChanged();
    }

    public void updateListSummaryData(ArrayList<Integer> arrayList, ArrayList<String> arrayList2) {
        HashMap map = new HashMap();
        for (int i = 0; i < arrayList.size(); i++) {
            map.put(arrayList.get(i), arrayList2.get(i));
        }
        for (Transcribe transcribe : this.transcribeList) {
            if (transcribe.getAudioMark() != null) {
                Integer id = transcribe.getAudioMark().getId();
                if (map.containsKey(id)) {
                    transcribe.getAudioMark().setContent((String) map.get(id));
                } else {
                    transcribe.setAudioMark(null);
                }
            }
        }
        this.transcribeAdapter.notifyDataSetChanged();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
    }

    @Override // com.aivox.common.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        if (this.mListener != null) {
            this.mListener = null;
        }
    }

    @Override // com.aivox.common.base.BaseBindingFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Disposable disposable = this.mDis;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
