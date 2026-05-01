package com.aivox.app.adapter;

import com.aivox.app.C0726R;
import com.aivox.base.common.Constant;
import com.aivox.base.util.DateUtil;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common_ui.C1034R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class AudioRecycleBinListAdapter extends BaseQuickAdapter<AudioInfoBean, BaseViewHolder> {
    public AudioRecycleBinListAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AudioInfoBean audioInfoBean) {
        boolean zAnyMatch = audioInfoBean.getTagGroupList().stream().anyMatch(new Predicate() { // from class: com.aivox.app.adapter.AudioRecycleBinListAdapter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AudioRecycleBinListAdapter.lambda$convert$0((AudioInfoBean.TagGroupList) obj);
            }
        });
        baseViewHolder.addOnClickListener(C0726R.id.cl_1);
        baseViewHolder.setImageResource(C0726R.id.iv_favorite, zAnyMatch ? C1034R.drawable.ic_star_highlight : C1034R.drawable.ic_star_normal);
        baseViewHolder.setVisible(C0726R.id.iv_favorite, true);
        baseViewHolder.setText(C0726R.id.item_info_duration, DateUtil.numberToTime(audioInfoBean.getFileTime()));
        baseViewHolder.setText(C0726R.id.item_name, audioInfoBean.getTitle());
        baseViewHolder.setText(C0726R.id.item_info_tv, DateUtil.getDateForList(audioInfoBean.getStartTime(), false) + " • ");
        baseViewHolder.setBackgroundRes(C0726R.id.f113cl, audioInfoBean.isCheck() ? C1034R.drawable.bg_record_item_selected : 0);
    }

    static /* synthetic */ boolean lambda$convert$0(AudioInfoBean.TagGroupList tagGroupList) {
        return tagGroupList.getType().intValue() == Constant.FOLDER_TYPE_FAVORITE;
    }
}
