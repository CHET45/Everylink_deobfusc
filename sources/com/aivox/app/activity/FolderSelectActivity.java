package com.aivox.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import androidx.databinding.DataBindingUtil;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ActivityFolderSelectBinding;
import com.aivox.app.databinding.ItemFolderSelectLayoutBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.http.HttpException;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.ToastUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.EventBean;
import com.aivox.common.model.FolderBean;
import com.aivox.common.util.AppUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import io.reactivex.functions.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class FolderSelectActivity extends BaseFragmentActivity {
    private int[] ids;
    private FolderListAdapter mAdapter;
    private ActivityFolderSelectBinding mBinding;
    private final List<FolderBean> mList = new ArrayList();
    private FolderBean mSelectedFolder;
    private boolean mShowMoveOut;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityFolderSelectBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_folder_select);
        this.mAdapter = new FolderListAdapter(this.context, C0726R.layout.item_folder_select_layout, this.mList);
        this.mBinding.lvFolder.setAdapter((ListAdapter) this.mAdapter);
        this.mBinding.lvFolder.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view2, int i, long j) {
                this.f$0.m2123lambda$initView$1$comaivoxappactivityFolderSelectActivity(adapterView, view2, i, j);
            }
        });
        this.mBinding.btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.activity.FolderSelectActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                FolderSelectActivity.this.mBinding.btnSave.startLoading();
                if (FolderSelectActivity.this.mSelectedFolder.getType().intValue() == -1) {
                    FolderSelectActivity.this.moveOutFromFolder();
                } else {
                    FolderSelectActivity folderSelectActivity = FolderSelectActivity.this;
                    folderSelectActivity.m144x96951750(folderSelectActivity.mSelectedFolder.getId());
                }
            }
        });
        initData();
    }

    /* JADX INFO: renamed from: lambda$initView$1$com-aivox-app-activity-FolderSelectActivity, reason: not valid java name */
    /* synthetic */ void m2123lambda$initView$1$comaivoxappactivityFolderSelectActivity(AdapterView adapterView, View view2, int i, long j) {
        this.mSelectedFolder = this.mList.get(i);
        this.mList.forEach(new Consumer() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m2122lambda$initView$0$comaivoxappactivityFolderSelectActivity((FolderBean) obj);
            }
        });
        this.mAdapter.notifyDataSetChanged();
        this.mBinding.btnSave.setEnabled(true);
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-FolderSelectActivity, reason: not valid java name */
    /* synthetic */ void m2122lambda$initView$0$comaivoxappactivityFolderSelectActivity(FolderBean folderBean) {
        folderBean.setSelected(folderBean.getId().equals(this.mSelectedFolder.getId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: moveIntoFolder, reason: merged with bridge method [inline-methods] */
    public void m144x96951750(final Integer num) {
        new AudioService(this.context).addToFolder(num.intValue(), this.ids).subscribe(new io.reactivex.functions.Consumer() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda6
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m143xfbf454cf(obj);
            }
        }, new io.reactivex.functions.Consumer() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m145x3135d9d1(num, (Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$moveIntoFolder$2$com-aivox-app-activity-FolderSelectActivity */
    /* synthetic */ void m143xfbf454cf(Object obj) throws Exception {
        this.mBinding.btnSave.stopLoading();
        EventBus.getDefault().post(new EventBean(501));
        ToastUtil.showShort(Integer.valueOf(C0874R.string.folder_move_in_success));
        finish();
    }

    /* JADX INFO: renamed from: lambda$moveIntoFolder$4$com-aivox-app-activity-FolderSelectActivity */
    /* synthetic */ void m145x3135d9d1(final Integer num, Throwable th) throws Exception {
        this.mBinding.btnSave.stopLoading();
        th.printStackTrace();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        } else {
            AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda8
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.m144x96951750(num);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveOutFromFolder() {
        new AudioService(this.context).moveOutFromFolder(Arrays.toString(this.ids).replace("[", "").replace("]", "").replace(", ", PunctuationConst.COMMA)).subscribe(new io.reactivex.functions.Consumer() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda3
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m146x77c8dee(obj);
            }
        }, new io.reactivex.functions.Consumer() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m147xa21d506f((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$moveOutFromFolder$5$com-aivox-app-activity-FolderSelectActivity */
    /* synthetic */ void m146x77c8dee(Object obj) throws Exception {
        this.mBinding.btnSave.stopLoading();
        EventBus.getDefault().post(new EventBean(501));
        ToastUtil.showShort(Integer.valueOf(C0874R.string.folder_move_out_success));
        finish();
    }

    /* JADX INFO: renamed from: lambda$moveOutFromFolder$6$com-aivox-app-activity-FolderSelectActivity */
    /* synthetic */ void m147xa21d506f(Throwable th) throws Exception {
        this.mBinding.btnSave.stopLoading();
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        } else {
            AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda5
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.moveOutFromFolder();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initData() {
        Bundle extras = getIntent().getExtras();
        this.ids = extras.getIntArray(Constant.KEY_IDS);
        this.mShowMoveOut = extras.getBoolean(Constant.KEY_SHOW_MOVE_OUT);
        DialogUtils.showLoadingDialog(this.context);
        new AudioService(this.context).getFolderList().doFinally(new Action() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Action
            public final void run() {
                DialogUtils.hideLoadingDialog();
            }
        }).subscribe(new io.reactivex.functions.Consumer() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2120lambda$initData$7$comaivoxappactivityFolderSelectActivity((List) obj);
            }
        }, new io.reactivex.functions.Consumer() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda11
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m2121lambda$initData$8$comaivoxappactivityFolderSelectActivity((Throwable) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initData$7$com-aivox-app-activity-FolderSelectActivity, reason: not valid java name */
    /* synthetic */ void m2120lambda$initData$7$comaivoxappactivityFolderSelectActivity(List list) throws Exception {
        this.mList.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            FolderBean folderBean = (FolderBean) it.next();
            if (folderBean.getType() != null && folderBean.getType().intValue() == 2) {
                this.mList.add(folderBean);
            }
        }
        if (this.mShowMoveOut) {
            FolderBean folderBean2 = new FolderBean();
            folderBean2.setId(-1);
            folderBean2.setType(-1);
            folderBean2.setName(getString(C0874R.string.folder_move_out));
            this.mList.add(folderBean2);
        }
        this.mAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: renamed from: lambda$initData$8$com-aivox-app-activity-FolderSelectActivity, reason: not valid java name */
    /* synthetic */ void m2121lambda$initData$8$comaivoxappactivityFolderSelectActivity(Throwable th) throws Exception {
        if (th instanceof HttpException) {
            AppUtils.checkHttpCode(this.context);
        } else {
            AppUtils.showError(this.context, th, new AppUtils.ResponseErrorCallback() { // from class: com.aivox.app.activity.FolderSelectActivity$$ExternalSyntheticLambda2
                @Override // com.aivox.common.util.AppUtils.ResponseErrorCallback
                public final void callback() {
                    this.f$0.initData();
                }
            });
        }
    }

    private static class FolderListAdapter extends ArrayAdapter<FolderBean> {
        private final Context mContext;
        private final int mLayoutRes;

        public FolderListAdapter(Context context, int i, List<FolderBean> list) {
            super(context, i, list);
            this.mContext = context;
            this.mLayoutRes = i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view2, ViewGroup viewGroup) {
            View root;
            ItemFolderSelectLayoutBinding itemFolderSelectLayoutBinding;
            FolderBean item = getItem(i);
            if (view2 == null) {
                itemFolderSelectLayoutBinding = (ItemFolderSelectLayoutBinding) DataBindingUtil.inflate(LayoutInflater.from(this.mContext), this.mLayoutRes, viewGroup, false);
                root = itemFolderSelectLayoutBinding.getRoot();
                root.setTag(itemFolderSelectLayoutBinding);
            } else {
                root = view2;
                itemFolderSelectLayoutBinding = (ItemFolderSelectLayoutBinding) view2.getTag();
            }
            itemFolderSelectLayoutBinding.setModel(item);
            return root;
        }
    }
}
