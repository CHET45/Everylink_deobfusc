package com.aivox.app.adapter;

import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.common.p003db.entity.ConversationEntity;
import com.aivox.common_ui.C1034R;
import com.blankj.utilcode.util.ColorUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/* JADX INFO: loaded from: classes.dex */
public class ConversationHistoryAdapter extends BaseQuickAdapter<ConversationEntity, BaseViewHolder> {
    public ConversationHistoryAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ConversationEntity conversationEntity) {
        baseViewHolder.setText(C0726R.id.tv_content, conversationEntity.getContent());
        baseViewHolder.setText(C0726R.id.tv_translation, conversationEntity.getTranslation());
        baseViewHolder.setBackgroundRes(C0726R.id.f113cl, conversationEntity.getMySide() ? C1034R.drawable.bg_conversation_left : C1034R.drawable.bg_conversation_right);
        baseViewHolder.setBackgroundColor(C0726R.id.line_content, ColorUtils.getColor(conversationEntity.getMySide() ? C0874R.color.line_divider_conversation_left : C0874R.color.line_divider_conversation_right));
    }
}
