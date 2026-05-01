package com.aivox.app.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.ConversationHistoryAdapter;
import com.aivox.app.databinding.ActivityConversationHistoryBinding;
import com.aivox.app.util.SpaceItemDecoration;
import com.aivox.base.util.SPUtil;
import com.aivox.common.base.AppApplication;
import com.aivox.common.base.BaseFragmentActivity;
import com.aivox.common.p003db.maneger.ConversationDbManager;
import com.blankj.utilcode.util.SizeUtils;

/* JADX INFO: loaded from: classes.dex */
public class ConversationHistoryActivity extends BaseFragmentActivity {
    @Override // com.aivox.common.base.BaseFragmentActivity
    public void initView() {
        ActivityConversationHistoryBinding activityConversationHistoryBinding = (ActivityConversationHistoryBinding) DataBindingUtil.setContentView(this, C0726R.layout.activity_conversation_history);
        ConversationHistoryAdapter conversationHistoryAdapter = new ConversationHistoryAdapter(C0726R.layout.item_conversation_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.context, 1, true);
        activityConversationHistoryBinding.rvHistory.addItemDecoration(new SpaceItemDecoration(SizeUtils.dp2px(64.0f)));
        activityConversationHistoryBinding.rvHistory.setLayoutManager(linearLayoutManager);
        activityConversationHistoryBinding.rvHistory.setAdapter(conversationHistoryAdapter);
        conversationHistoryAdapter.setNewData(ConversationDbManager.getInstance(AppApplication.getIns().getDaoSession()).queryLocalList((String) SPUtil.get(SPUtil.USER_ID, "0")));
    }
}
