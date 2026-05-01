package com.aivox.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.FragmentMainFileBinding;
import com.aivox.app.fragment.RecordListCloudFragment;
import com.aivox.app.listener.OnFragmentInteractionListener;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.databinding.OnViewClickListener;
import com.aivox.base.http.HttpException;
import com.aivox.base.router.ARouterUtils;
import com.aivox.base.router.action.MainAction;
import com.aivox.base.router.action.RecordAction;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.DataHandle;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.FolderBean;
import com.aivox.common.util.AppUtils;
import com.aivox.common_ui.C1034R;
import com.aivox.common_ui.antishake.AntiShake;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MainFileFragment extends BaseBindingFragment implements OnViewClickListener {
    private RecordListCloudFragment cloudFragment;
    private FragmentMainFileBinding mBinding;
    private int mCurPosition;
    private OnFragmentInteractionListener mListener;
    private TabAdapter mTabAdapter;
    private final ArrayList<FolderBean> mTitles = new ArrayList<>();

    public static MainFileFragment newInstance() {
        return new MainFileFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        FragmentMainFileBinding fragmentMainFileBindingInflate = FragmentMainFileBinding.inflate(layoutInflater, viewGroup, false);
        this.mBinding = fragmentMainFileBindingInflate;
        fragmentMainFileBindingInflate.setClickListener(this);
        FolderBean folderBean = new FolderBean();
        folderBean.setId(Integer.valueOf(Constant.FOLDER_ID_ALL));
        folderBean.setType(-1);
        folderBean.setName(getString(C0874R.string.folder_name_all));
        folderBean.setSelected(true);
        this.mTitles.add(folderBean);
        this.mTabAdapter = new TabAdapter(C0726R.layout.item_folder_tab_layout, this.mTitles);
        this.mBinding.rvTabs.setAdapter(this.mTabAdapter);
        this.mBinding.rvTabs.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        this.mTabAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m273x548c1cb(baseQuickAdapter, view2, i);
            }
        });
        FragmentManager childFragmentManager = getChildFragmentManager();
        if (childFragmentManager.findFragmentById(this.mBinding.flContainer.getId()) == null) {
            this.cloudFragment = RecordListCloudFragment.newInstance();
            FragmentTransaction fragmentTransactionBeginTransaction = childFragmentManager.beginTransaction();
            fragmentTransactionBeginTransaction.add(this.mBinding.flContainer.getId(), this.cloudFragment);
            fragmentTransactionBeginTransaction.commit();
        }
        getFolderData();
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-app-fragment-MainFileFragment */
    /* synthetic */ void m273x548c1cb(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        switchTag(i);
    }

    private void showSortPopup() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(C0726R.layout.sort_popup_layout, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(viewInflate, -2, -2);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        TextView textView = (TextView) viewInflate.findViewById(C0726R.id.tv_to_old);
        TextView textView2 = (TextView) viewInflate.findViewById(C0726R.id.tv_to_now);
        textView.setTextColor(ColorUtils.getColor(this.cloudFragment.getSortType() == 0 ? C0874R.color.txt_color_highlight : C0874R.color.txt_color_primary));
        textView2.setTextColor(ColorUtils.getColor(this.cloudFragment.getSortType() == 0 ? C0874R.color.txt_color_primary : C0874R.color.txt_color_highlight));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2339lambda$showSortPopup$1$comaivoxappfragmentMainFileFragment(popupWindow, view2);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2340lambda$showSortPopup$2$comaivoxappfragmentMainFileFragment(popupWindow, view2);
            }
        });
        popupWindow.showAsDropDown(this.mBinding.llSearch, 0, 0, GravityCompat.END);
    }

    /* JADX INFO: renamed from: lambda$showSortPopup$1$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2339lambda$showSortPopup$1$comaivoxappfragmentMainFileFragment(PopupWindow popupWindow, View view2) {
        this.cloudFragment.initData(0);
        popupWindow.dismiss();
    }

    /* JADX INFO: renamed from: lambda$showSortPopup$2$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2340lambda$showSortPopup$2$comaivoxappfragmentMainFileFragment(PopupWindow popupWindow, View view2) {
        this.cloudFragment.initData(1);
        popupWindow.dismiss();
    }

    private void switchTag(int i) {
        if (this.mTitles.get(i).getId().intValue() == Constant.FOLDER_ID_RECYCLE_BIN && !DataHandle.getIns().isVip()) {
            DialogUtils.showDialogWithBtnIds(this.mActivity, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.recycle_bin_notice), null, new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda5
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                    this.f$0.m2341lambda$switchTag$3$comaivoxappfragmentMainFileFragment(context, dialogBuilder, dialog, i2, i3, editText);
                }
            }, true, true, C0874R.string.know_and_continue, C0874R.string.join_vip);
            return;
        }
        this.mCurPosition = i;
        FolderBean folderBean = this.mTitles.get(i);
        this.mBinding.tvFolderCur.setText(i == 0 ? getString(C0874R.string.home_bottom_files) : this.mTitles.get(i).getName());
        this.cloudFragment.switchTag(this.mTitles.get(i).getId().intValue());
        if (folderBean.getId().intValue() != Constant.FOLDER_ID_RECYCLE_BIN) {
            this.mBinding.btnDeleteAll.setVisibility(8);
        }
        this.mBinding.ivImportFile.setVisibility(folderBean.getType().intValue() != 2 ? 0 : 8);
        this.mBinding.ivCreateFolder.setVisibility(folderBean.getType().intValue() != 2 ? 0 : 8);
        this.mBinding.ivDeleteFolder.setVisibility(folderBean.getType().intValue() == 2 ? 0 : 8);
        this.mBinding.ivEditFolder.setVisibility(folderBean.getType().intValue() == 2 ? 0 : 8);
        this.mBinding.ivMoveFolder.setVisibility(folderBean.getType().intValue() == 2 ? 0 : 8);
        Iterator<FolderBean> it = this.mTitles.iterator();
        while (it.hasNext()) {
            it.next().setSelected(false);
        }
        this.mTitles.get(i).setSelected(true);
        this.mTabAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: renamed from: lambda$switchTag$3$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2341lambda$switchTag$3$comaivoxappfragmentMainFileFragment(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        AppUtils.jumpToPurchase(getActivity(), false);
    }

    public void onFolderNameChanged(String str, int i) {
        Iterator<FolderBean> it = this.mTitles.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            FolderBean next = it.next();
            if (next.getId().intValue() == i) {
                next.setName(str);
                break;
            }
        }
        this.mTabAdapter.notifyDataSetChanged();
        this.cloudFragment.initData();
        this.mBinding.tvFolderCur.setText(str);
    }

    private void getFolderData() {
        new AudioService(this.mContext).getFolderList().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2336lambda$getFolderData$4$comaivoxappfragmentMainFileFragment((List) obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((Throwable) obj).printStackTrace();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$getFolderData$4$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2336lambda$getFolderData$4$comaivoxappfragmentMainFileFragment(List list) throws Exception {
        this.mTitles.clear();
        this.mTitles.addAll(list);
        this.mTitles.get(0).setSelected(true);
        this.mTabAdapter.setNewData(this.mTitles);
        this.mBinding.rvTabs.scrollToPosition(0);
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view2, Bundle bundle) {
        super.onViewCreated(view2, bundle);
        RecordListCloudFragment recordListCloudFragment = this.cloudFragment;
        if (recordListCloudFragment != null) {
            recordListCloudFragment.setInteractionListener(this.mListener, new RecordListCloudFragment.SelectNumChangListener() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda4
                @Override // com.aivox.app.fragment.RecordListCloudFragment.SelectNumChangListener
                public final void selectNumChang(int i) {
                    this.f$0.m2338lambda$onViewCreated$6$comaivoxappfragmentMainFileFragment(i);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$onViewCreated$6$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2338lambda$onViewCreated$6$comaivoxappfragmentMainFileFragment(int i) {
        String name;
        if (this.mTitles.get(this.mCurPosition).getId().intValue() == Constant.FOLDER_ID_RECYCLE_BIN) {
            TextView textView = this.mBinding.tvFolderCur;
            if (i > 0) {
                name = String.format("%s (%d/%d)", getString(C0874R.string.audio_selected), Integer.valueOf(i), Integer.valueOf(this.cloudFragment.getItemNum()));
            } else {
                name = this.mTitles.get(this.mCurPosition).getName();
            }
            textView.setText(name);
        }
    }

    private void toImportLocalFile() {
        ARouterUtils.startWithContext(this.mContext, RecordAction.RECORD_IMPORT);
    }

    public void notifyList() {
        RecordListCloudFragment recordListCloudFragment = this.cloudFragment;
        if (recordListCloudFragment != null) {
            recordListCloudFragment.notifyList();
        }
    }

    public void notifyUploadItem(AudioInfoBean audioInfoBean) {
        RecordListCloudFragment recordListCloudFragment = this.cloudFragment;
        if (recordListCloudFragment != null) {
            recordListCloudFragment.notifyItemChanged(audioInfoBean);
        }
    }

    public RecordListCloudFragment getCloudFragment() {
        return this.cloudFragment;
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void onEventMainThread(EventBean eventBean) {
        RecordListCloudFragment recordListCloudFragment;
        super.onEventMainThread(eventBean);
        LogUtil.m338i("RECORDLIST_Main_event:" + eventBean.getFrom());
        int from = eventBean.getFrom();
        if (from == 33) {
            DataHandle.getIns().setRtcDone(true);
            return;
        }
        if (from == 50) {
            notifyList();
            return;
        }
        if (from == 503) {
            this.mBinding.btnDeleteAll.setVisibility(eventBean.getA() <= 0 ? 8 : 0);
            return;
        }
        if (from == 500) {
            getFolderData();
            switchTag(0);
        } else if (from == 501 && (recordListCloudFragment = this.cloudFragment) != null) {
            recordListCloudFragment.initData();
        }
    }

    public void setInteractionListener(OnFragmentInteractionListener onFragmentInteractionListener) {
        this.mListener = onFragmentInteractionListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDeleteAll() {
        DialogUtils.showLoadingDialog(this.mContext, "", false);
        new AudioService(this.mContext).clearRecycleBin().subscribe(new Consumer() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2334lambda$doDeleteAll$7$comaivoxappfragmentMainFileFragment(obj);
            }
        }, new Consumer() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2335lambda$doDeleteAll$8$comaivoxappfragmentMainFileFragment((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doDeleteAll$7$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2334lambda$doDeleteAll$7$comaivoxappfragmentMainFileFragment(Object obj) throws Exception {
        DialogUtils.hideLoadingDialog();
        ToastUtil.showTextToast(this.mContext, getString(C0874R.string.audio_delete_success));
        this.cloudFragment.initData();
    }

    /* JADX INFO: renamed from: lambda$doDeleteAll$8$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2335lambda$doDeleteAll$8$comaivoxappfragmentMainFileFragment(Throwable th) throws Exception {
        DialogUtils.hideLoadingDialog();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.mContext);
        } else {
            AppUtils.showError(this.mContext, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda9
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.doDeleteAll();
                }
            });
        }
    }

    private void showFolderCreateDialog(String str) {
        this.mListener.createOrRenameFolder(str, this.mTitles.get(this.mCurPosition).getId().intValue());
    }

    public void quitSelectMode() {
        RecordListCloudFragment recordListCloudFragment = this.cloudFragment;
        if (recordListCloudFragment != null) {
            recordListCloudFragment.quitSelectMode();
        }
    }

    @Override // com.aivox.base.databinding.OnViewClickListener
    public void onViewClick(View view2) {
        if (AntiShake.check(view2)) {
            return;
        }
        KeyboardUtils.hideSoftInput(view2);
        int id = view2.getId();
        if (id == C0726R.id.iv_import_file) {
            toImportLocalFile();
            return;
        }
        if (id == C0726R.id.iv_create_folder) {
            showFolderCreateDialog("");
            return;
        }
        if (id == C0726R.id.iv_edit_folder) {
            showFolderCreateDialog(this.mTitles.get(this.mCurPosition).getName());
            return;
        }
        if (id == C0726R.id.iv_delete_folder) {
            this.mListener.deleteFolder(this.mTitles.get(this.mCurPosition).getId().intValue());
            return;
        }
        if (id == C0726R.id.iv_move_folder) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.KEY_FLAG, true);
            bundle.putInt(Constant.KEY_IDS, this.mTitles.get(this.mCurPosition).getId().intValue());
            ARouterUtils.startWithContext(this.mContext, MainAction.SEARCH, bundle);
            return;
        }
        if (id == C0726R.id.ll_search) {
            ARouterUtils.startWithContext(this.mContext, MainAction.SEARCH);
        } else if (id == C0726R.id.iv_sort) {
            showSortPopup();
        } else if (id == C0726R.id.btn_delete_all) {
            DialogUtils.showDeleteDialog(this.mContext, Integer.valueOf(C0874R.string.delete_all_the_files), Integer.valueOf(C0874R.string.audio_delete_remind_warning), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.fragment.MainFileFragment$$ExternalSyntheticLambda0
                @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
                public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
                    this.f$0.m2337lambda$onViewClick$9$comaivoxappfragmentMainFileFragment(context, dialogBuilder, dialog, i, i2, editText);
                }
            }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
        }
    }

    /* JADX INFO: renamed from: lambda$onViewClick$9$com-aivox-app-fragment-MainFileFragment, reason: not valid java name */
    /* synthetic */ void m2337lambda$onViewClick$9$comaivoxappfragmentMainFileFragment(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i, int i2, EditText editText) {
        doDeleteAll();
    }

    private static class TabAdapter extends BaseQuickAdapter<FolderBean, BaseViewHolder> {
        public TabAdapter(int i, List<FolderBean> list) {
            super(i, list);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, FolderBean folderBean) {
            int color = Color.parseColor(BaseStringUtil.isEmpty(folderBean.getColourText()) ? "#FFFFFF" : folderBean.getColourText());
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            gradientDrawable.setCornerRadius(SizeUtils.dp2px(99.0f));
            gradientDrawable.setColor(color);
            GradientDrawable gradientDrawable2 = new GradientDrawable();
            gradientDrawable2.setShape(0);
            gradientDrawable2.setCornerRadius(SizeUtils.dp2px(99.0f));
            gradientDrawable2.setStroke(SizeUtils.dp2px(1.0f), color);
            if (folderBean.getType().intValue() == Constant.FOLDER_TYPE_CUSTOM) {
                if (folderBean.isSelected()) {
                    baseViewHolder.setTextColor(C0726R.id.tv_folder_name, ColorUtils.getColor(C0874R.color.txt_color_secondary));
                    baseViewHolder.getView(C0726R.id.cl_content).setBackgroundDrawable(gradientDrawable);
                } else {
                    baseViewHolder.setTextColor(C0726R.id.tv_folder_name, color);
                    baseViewHolder.getView(C0726R.id.cl_content).setBackgroundDrawable(gradientDrawable2);
                }
            } else {
                baseViewHolder.setTextColor(C0726R.id.tv_folder_name, ColorUtils.getColor(folderBean.isSelected() ? C0874R.color.txt_color_secondary : C0874R.color.txt_color_tertiary));
                baseViewHolder.setBackgroundRes(C0726R.id.cl_content, folderBean.isSelected() ? C1034R.drawable.bg_tab_selected : C1034R.drawable.bg_tab_unselected);
            }
            baseViewHolder.setText(C0726R.id.tv_folder_name, folderBean.getName());
        }
    }
}
