package com.aivox.app.adapter;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;
import com.aivox.app.C0726R;
import com.aivox.app.util.SwipedSelectItemTouchCallBack;
import com.aivox.base.common.Constant;
import com.aivox.base.img.imageloader.ImageLoaderFactory;
import com.aivox.base.util.BaseStringUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.common.model.AiChatBean;
import com.aivox.common.model.EventBean;
import com.aivox.common_ui.C1034R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import io.noties.markwon.Markwon;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class AiChatAdapter extends BaseQuickAdapter<AiChatBean.Records, BaseViewHolder> implements SwipedSelectItemTouchCallBack.OnItemSwipeListener {
    public static final int MODE_NORMAL = 0;
    public static final int MODE_SELECT = 1;
    private AnimationDrawable mAnim;
    private int mCurMode;
    private final Markwon mMarkDown;
    private final List<Integer> mSelectIdList;

    public AiChatAdapter(int i, Markwon markwon) {
        super(i);
        this.mSelectIdList = new ArrayList();
        this.mCurMode = 0;
        this.mMarkDown = markwon;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AiChatBean.Records records) {
        this.mAnim = (AnimationDrawable) ((ImageView) baseViewHolder.getView(C0726R.id.iv_loading)).getDrawable();
        baseViewHolder.setText(C0726R.id.tv_ai_question, records.getQuestion());
        if (records.getType().intValue() == Constant.AI_TYPE_MIND) {
            baseViewHolder.setGone(C0726R.id.iv_ai_mind_map_download, true);
            baseViewHolder.setGone(C0726R.id.iv_ai_mind_map_send, true);
            baseViewHolder.setGone(C0726R.id.iv_ai_answer_edit, false);
            baseViewHolder.setGone(C0726R.id.iv_ai_answer_copy, false);
            if (records.getStatus().intValue() == 1 || records.getStatus().intValue() == 2) {
                baseViewHolder.setGone(C0726R.id.cv_mind_map, true);
                baseViewHolder.setGone(C0726R.id.tv_ai_answer_content, false);
                if (records.getAnswer().startsWith("http")) {
                    baseViewHolder.setGone(C0726R.id.ll_ai_mind_error, false);
                    ImageLoaderFactory.getLoader().displayImage((ImageView) baseViewHolder.getView(C0726R.id.iv_ai_mind_map), records.getAnswer());
                } else {
                    baseViewHolder.setGone(C0726R.id.ll_ai_mind_error, true);
                }
                baseViewHolder.setVisible(C0726R.id.ll_ai_answer_function, true);
                baseViewHolder.setVisible(C0726R.id.view_line, true);
                baseViewHolder.setGone(C0726R.id.ll_ai_loading, false);
                this.mAnim.stop();
            } else {
                baseViewHolder.setGone(C0726R.id.cv_mind_map, false);
                baseViewHolder.setGone(C0726R.id.ll_ai_loading, true);
                baseViewHolder.setGone(C0726R.id.tv_ai_answer_content, false);
                baseViewHolder.setGone(C0726R.id.ll_ai_answer_function, false);
                baseViewHolder.setGone(C0726R.id.view_line, false);
                this.mAnim.start();
            }
        } else {
            baseViewHolder.setGone(C0726R.id.iv_ai_mind_map_download, false);
            baseViewHolder.setGone(C0726R.id.iv_ai_mind_map_send, false);
            baseViewHolder.setGone(C0726R.id.iv_ai_answer_edit, false);
            baseViewHolder.setGone(C0726R.id.iv_ai_answer_copy, true);
            baseViewHolder.setGone(C0726R.id.cv_mind_map, false);
            if (BaseStringUtil.isEmpty(records.getAnswer()) || records.getStatus().intValue() == 3) {
                baseViewHolder.setGone(C0726R.id.ll_ai_loading, true);
                baseViewHolder.setGone(C0726R.id.tv_ai_answer_content, false);
                baseViewHolder.setGone(C0726R.id.ll_ai_answer_function, false);
                baseViewHolder.setGone(C0726R.id.view_line, false);
                this.mAnim.start();
            } else {
                baseViewHolder.setGone(C0726R.id.ll_ai_loading, false);
                baseViewHolder.setGone(C0726R.id.tv_ai_answer_content, true);
                baseViewHolder.setVisible(C0726R.id.ll_ai_answer_function, records.getStatus().intValue() == 1 || records.getStatus().intValue() == 2);
                baseViewHolder.setVisible(C0726R.id.view_line, records.getStatus().intValue() == 1 || records.getStatus().intValue() == 2);
                this.mAnim.stop();
            }
            this.mMarkDown.setMarkdown((TextView) baseViewHolder.getView(C0726R.id.tv_ai_answer_content), records.getAnswer());
        }
        baseViewHolder.addOnClickListener(C0726R.id.iv_ai_mind_map_download);
        baseViewHolder.addOnClickListener(C0726R.id.iv_ai_mind_map_send);
        baseViewHolder.addOnClickListener(C0726R.id.iv_ai_answer_edit);
        baseViewHolder.addOnClickListener(C0726R.id.iv_ai_answer_copy);
        baseViewHolder.addOnClickListener(C0726R.id.iv_ai_answer_regenerate);
        baseViewHolder.addOnClickListener(C0726R.id.iv_fullscreen);
        baseViewHolder.addOnClickListener(C0726R.id.cl_ai_content);
        baseViewHolder.addOnClickListener(C0726R.id.tv_ai_answer_content);
        baseViewHolder.addOnLongClickListener(C0726R.id.cl_ai_content);
        baseViewHolder.addOnLongClickListener(C0726R.id.tv_ai_answer_content);
        baseViewHolder.setGone(C0726R.id.cb_select, this.mCurMode == 1);
        baseViewHolder.setGone(C0726R.id.ll_ai_answer_function, this.mCurMode == 0 && records.getStatus().intValue() > -1);
        baseViewHolder.setGone(C0726R.id.view_line, this.mCurMode == 0 && records.getStatus().intValue() > -1);
        if (this.mSelectIdList.contains(records.getId())) {
            baseViewHolder.setBackgroundRes(C0726R.id.cl_ai_content, C1034R.drawable.bg_purple_c14);
            baseViewHolder.setBackgroundRes(C0726R.id.tv_ai_question, C1034R.drawable.bg_ai_chat_right_selected);
            baseViewHolder.setChecked(C0726R.id.cb_select, true);
        } else {
            baseViewHolder.setBackgroundRes(C0726R.id.cl_ai_content, 0);
            baseViewHolder.setBackgroundRes(C0726R.id.tv_ai_question, C1034R.drawable.bg_ai_chat_right);
            baseViewHolder.setChecked(C0726R.id.cb_select, false);
        }
    }

    @Override // com.aivox.app.util.SwipedSelectItemTouchCallBack.OnItemSwipeListener
    public void onSwipeSelected(int i) {
        switchMode(1, true);
        selectItem(i);
    }

    public void switchMode(int i, boolean z) {
        LogUtil.m336e("switchMode:" + i + ",bySwipeOrPress:" + z);
        this.mCurMode = i;
        if (i == 0) {
            this.mSelectIdList.clear();
        }
        notifyItemRangeChanged(0, this.mData.size());
        if (z) {
            EventBus.getDefault().post(new EventBean(82, i));
        }
    }

    public void selectItem(int i) {
        if (((AiChatBean.Records) this.mData.get(i)).getStatus().intValue() == -1 || ((AiChatBean.Records) this.mData.get(i)).getStatus().intValue() == 3) {
            return;
        }
        if (this.mSelectIdList.contains(((AiChatBean.Records) this.mData.get(i)).getId())) {
            this.mSelectIdList.remove(((AiChatBean.Records) this.mData.get(i)).getId());
        } else {
            this.mSelectIdList.add(((AiChatBean.Records) this.mData.get(i)).getId());
        }
        notifyItemChanged(i);
    }

    public int getMode() {
        return this.mCurMode;
    }

    public List<Integer> getSelectIdList() {
        return this.mSelectIdList;
    }
}
