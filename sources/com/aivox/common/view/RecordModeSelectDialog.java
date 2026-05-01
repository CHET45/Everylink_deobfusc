package com.aivox.common.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.base.view.dialog.BaseDialogViewWrapper;
import com.aivox.common.C0958R;
import com.aivox.common.databinding.RecordModeSelectDialogLayoutBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecordModeSelectDialog extends BaseDialogViewWrapper {
    private final RecordModeAdapter mAdapter;
    private RecordModeSelectDialogLayoutBinding mBinding;
    private final List<RecordModeBean> mList;

    public interface ItemInteractionListener {
        void onSelected(int i);
    }

    public RecordModeSelectDialog(Context context) {
        this(context, null, null);
    }

    public RecordModeSelectDialog(Context context, List<RecordModeBean> list, final ItemInteractionListener itemInteractionListener) {
        super(context);
        ArrayList arrayList = new ArrayList();
        this.mList = arrayList;
        this.mBinding = RecordModeSelectDialogLayoutBinding.inflate(LayoutInflater.from(context), this, true);
        arrayList.addAll(list);
        RecordModeAdapter recordModeAdapter = new RecordModeAdapter(C0958R.layout.item_record_mode_select, arrayList);
        this.mAdapter = recordModeAdapter;
        this.mBinding.rvMark.setLayoutManager(new LinearLayoutManager(context));
        this.mBinding.rvMark.setAdapter(recordModeAdapter);
        this.mBinding.dtvTitle.setViewClickListener(new View.OnClickListener() { // from class: com.aivox.common.view.RecordModeSelectDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2487lambda$new$0$comaivoxcommonviewRecordModeSelectDialog(view2);
            }
        });
        recordModeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.common.view.RecordModeSelectDialog$$ExternalSyntheticLambda1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2488lambda$new$1$comaivoxcommonviewRecordModeSelectDialog(itemInteractionListener, baseQuickAdapter, view2, i);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$com-aivox-common-view-RecordModeSelectDialog, reason: not valid java name */
    /* synthetic */ void m2487lambda$new$0$comaivoxcommonviewRecordModeSelectDialog(View view2) {
        this.mDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$new$1$com-aivox-common-view-RecordModeSelectDialog, reason: not valid java name */
    /* synthetic */ void m2488lambda$new$1$comaivoxcommonviewRecordModeSelectDialog(ItemInteractionListener itemInteractionListener, BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        RecordModeBean recordModeBean = (RecordModeBean) baseQuickAdapter.getItem(i);
        if (recordModeBean != null && view2.getId() == C0958R.id.cl_mode) {
            if (itemInteractionListener != null) {
                itemInteractionListener.onSelected(recordModeBean.getMode());
            }
            this.mDialog.dismiss();
        }
    }

    @Override // com.aivox.base.view.dialog.IDialogVIew
    public void setDialog(Dialog dialog) {
        this.mDialog = dialog;
    }

    public static class RecordModeAdapter extends BaseQuickAdapter<RecordModeBean, BaseViewHolder> {
        private int curId;

        public int getCurId() {
            return this.curId;
        }

        public void setCurId(int i) {
            this.curId = i;
        }

        public RecordModeAdapter(int i, List<RecordModeBean> list) {
            super(i, list);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, RecordModeBean recordModeBean) {
            baseViewHolder.addOnClickListener(C0958R.id.cl_mode);
            baseViewHolder.setText(C0958R.id.tv_mode_name, recordModeBean.getModeName());
            baseViewHolder.setText(C0958R.id.tv_mode_desc, recordModeBean.getModeDesc());
            baseViewHolder.setImageResource(C0958R.id.iv_mode, recordModeBean.getImgRes());
        }
    }

    public static class RecordModeBean implements Serializable {
        private int imgRes;
        private int mode;
        private String modeDesc;
        private String modeName;

        public RecordModeBean(int i, int i2, String str, String str2) {
            this.mode = i;
            this.imgRes = i2;
            this.modeName = str;
            this.modeDesc = str2;
        }

        public int getMode() {
            return this.mode;
        }

        public void setMode(int i) {
            this.mode = i;
        }

        public int getImgRes() {
            return this.imgRes;
        }

        public void setImgRes(int i) {
            this.imgRes = i;
        }

        public String getModeName() {
            return this.modeName;
        }

        public void setModeName(String str) {
            this.modeName = str;
        }

        public String getModeDesc() {
            return this.modeDesc;
        }

        public void setModeDesc(String str) {
            this.modeDesc = str;
        }
    }
}
