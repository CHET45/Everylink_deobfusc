package com.aivox.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.NoteMarkAdapter;
import com.aivox.app.databinding.ActivityNoteMarkListBinding;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.DialogBuilder;
import com.aivox.base.util.DialogUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.http.AudioService;
import com.aivox.common.model.AudioMarkBean;
import com.aivox.common_ui.BottomEditDialogView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NoteMarkListActivity extends BaseFragmentActivity {
    private NoteMarkAdapter mAdapter;
    private int mAudioId;
    private ActivityNoteMarkListBinding mBinding;

    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        this.mBinding = (ActivityNoteMarkListBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_note_mark_list);
        this.mAudioId = getIntent().getExtras().getInt(Constant.KEY_IDS);
        this.mBinding.titleView.setOnBackListener(new View.OnClickListener() { // from class: com.aivox.app.activity.NoteMarkListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                NoteMarkListActivity.this.onBackPressed();
            }
        });
        this.mAdapter = new NoteMarkAdapter(C0726R.layout.item_note_mark_layout);
        this.mBinding.rvMarks.setLayoutManager(new LinearLayoutManager(this.context));
        this.mAdapter.bindToRecyclerView(this.mBinding.rvMarks);
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.aivox.app.activity.NoteMarkListActivity$$ExternalSyntheticLambda0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                this.f$0.m2163lambda$initView$0$comaivoxappactivityNoteMarkListActivity(baseQuickAdapter, view2, i);
            }
        });
        DialogUtils.showLoadingDialog(this.context);
        Observable<List<AudioMarkBean>> observableDoFinally = new AudioService(this.context).getNoteMarkList(this.mAudioId).doFinally(new NoteMarkListActivity$$ExternalSyntheticLambda1());
        final NoteMarkAdapter noteMarkAdapter = this.mAdapter;
        Objects.requireNonNull(noteMarkAdapter);
        observableDoFinally.subscribe(new Consumer() { // from class: com.aivox.app.activity.NoteMarkListActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                noteMarkAdapter.setNewData((List) obj);
            }
        }, new NoteMarkListActivity$$ExternalSyntheticLambda3());
    }

    /* JADX INFO: renamed from: lambda$initView$0$com-aivox-app-activity-NoteMarkListActivity, reason: not valid java name */
    /* synthetic */ void m2163lambda$initView$0$comaivoxappactivityNoteMarkListActivity(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
        int id = view2.getId();
        if (id == C0726R.id.iv_mark_delete) {
            showDeleteDialog(i);
        } else if (id == C0726R.id.tv_mark_content) {
            BottomEditDialogView bottomEditDialogView = new BottomEditDialogView(this.context, 3, 1000, true, new C07762(i));
            bottomEditDialogView.setDialogContent(getString(C0874R.string.dialog_edit_notes_title), getString(C0874R.string.dialog_edit_notes_msg), getString(C0874R.string.dialog_edit_notes_hint), this.mAdapter.getData().get(i).getContent(), getString(C0874R.string.delete));
            DialogUtils.showBottomSheetDialog(this.context, bottomEditDialogView, C0874R.style.BottomSheetDialogWithEdit);
        }
    }

    /* JADX INFO: renamed from: com.aivox.app.activity.NoteMarkListActivity$2 */
    class C07762 implements BottomEditDialogView.OnBtnClickListener {
        final /* synthetic */ int val$position;

        C07762(int i) {
            this.val$position = i;
        }

        @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
        public void onLeftBtnClick() {
            NoteMarkListActivity.this.showDeleteDialog(this.val$position);
        }

        @Override // com.aivox.common_ui.BottomEditDialogView.OnBtnClickListener
        public void onSaveBtnClick(final String str) {
            LogUtil.m336e(str);
            if (BaseStringUtil.isEmpty(str.trim())) {
                return;
            }
            DialogUtils.showLoadingDialog(NoteMarkListActivity.this.context);
            Observable<Object> observableDoFinally = new AudioService(NoteMarkListActivity.this.context).editNoteMark(NoteMarkListActivity.this.mAudioId, NoteMarkListActivity.this.mAdapter.getData().get(this.val$position).getId().intValue(), str).doFinally(new Action() { // from class: com.aivox.app.activity.NoteMarkListActivity$2$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Action
                public final void run() {
                    DialogUtils.hideLoadingDialog();
                }
            });
            final int i = this.val$position;
            observableDoFinally.subscribe(new Consumer() { // from class: com.aivox.app.activity.NoteMarkListActivity$2$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.m163x5e6f7677(i, str, obj);
                }
            }, new Consumer() { // from class: com.aivox.app.activity.NoteMarkListActivity$2$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    ((Throwable) obj).printStackTrace();
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onSaveBtnClick$0$com-aivox-app-activity-NoteMarkListActivity$2 */
        /* synthetic */ void m163x5e6f7677(int i, String str, Object obj) throws Exception {
            NoteMarkListActivity.this.mAdapter.getData().get(i).setContent(str);
            NoteMarkListActivity.this.mAdapter.notifyItemChanged(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteDialog(final int i) {
        DialogUtils.showDeleteDialog(this.context, Integer.valueOf(C0874R.string.reminder), Integer.valueOf(C0874R.string.note_mark_delete_notice), new DialogBuilder.DialogButtonClickListener() { // from class: com.aivox.app.activity.NoteMarkListActivity$$ExternalSyntheticLambda5
            @Override // com.aivox.base.util.DialogBuilder.DialogButtonClickListener
            public final void onButtonClick(Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
                this.f$0.m162x6fd78db3(i, context, dialogBuilder, dialog, i2, i3, editText);
            }
        }, null, true, true, C0874R.string.delete, C0874R.string.cancel, 0);
    }

    /* JADX INFO: renamed from: lambda$showDeleteDialog$2$com-aivox-app-activity-NoteMarkListActivity */
    /* synthetic */ void m162x6fd78db3(final int i, Context context, DialogBuilder dialogBuilder, Dialog dialog, int i2, int i3, EditText editText) {
        DialogUtils.showLoadingDialog(context);
        new AudioService(context).deleteNoteMark(Arrays.asList(this.mAdapter.getData().get(i).getId())).doFinally(new NoteMarkListActivity$$ExternalSyntheticLambda1()).subscribe(new Consumer() { // from class: com.aivox.app.activity.NoteMarkListActivity$$ExternalSyntheticLambda4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.m161xd536cb32(i, obj);
            }
        }, new NoteMarkListActivity$$ExternalSyntheticLambda3());
    }

    /* JADX INFO: renamed from: lambda$showDeleteDialog$1$com-aivox-app-activity-NoteMarkListActivity */
    /* synthetic */ void m161xd536cb32(int i, Object obj) throws Exception {
        this.mAdapter.getData().remove(i);
        this.mAdapter.notifyItemRemoved(i);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        List<AudioMarkBean> data = this.mAdapter.getData();
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (AudioMarkBean audioMarkBean : data) {
            arrayList.add(audioMarkBean.getId());
            arrayList2.add(audioMarkBean.getContent());
        }
        Intent intent = new Intent();
        intent.putIntegerArrayListExtra(Constant.KEY_IDS, arrayList);
        intent.putStringArrayListExtra("data", arrayList2);
        setResult(-1, intent);
        finish();
    }
}
