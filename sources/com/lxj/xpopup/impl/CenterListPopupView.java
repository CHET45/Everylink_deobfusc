package com.lxj.xpopup.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.widget.CheckView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;

/* JADX INFO: loaded from: classes4.dex */
public class CenterListPopupView extends CenterPopupView {
    int checkedPosition;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;
    CharSequence title;
    TextView tv_title;

    public CenterListPopupView(Context context, int i, int i2) {
        super(context);
        this.checkedPosition = -1;
        this.bindLayoutId = i;
        this.bindItemLayoutId = i2;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return this.bindLayoutId == 0 ? C2213R.layout._xpopup_center_impl_list : this.bindLayoutId;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.recyclerView = (RecyclerView) findViewById(C2213R.id.recyclerView);
        if (this.bindLayoutId != 0) {
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        TextView textView = (TextView) findViewById(C2213R.id.tv_title);
        this.tv_title = textView;
        if (textView != null) {
            if (TextUtils.isEmpty(this.title)) {
                this.tv_title.setVisibility(8);
                if (findViewById(C2213R.id.xpopup_divider) != null) {
                    findViewById(C2213R.id.xpopup_divider).setVisibility(8);
                }
            } else {
                this.tv_title.setText(this.title);
            }
        }
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(Arrays.asList(this.data), this.bindItemLayoutId == 0 ? C2213R.layout._xpopup_adapter_text_match : this.bindItemLayoutId) { // from class: com.lxj.xpopup.impl.CenterListPopupView.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(ViewHolder viewHolder, String str, int i) {
                viewHolder.setText(C2213R.id.tv_text, str);
                if (CenterListPopupView.this.iconIds != null && CenterListPopupView.this.iconIds.length > i) {
                    viewHolder.getView(C2213R.id.iv_image).setVisibility(0);
                    viewHolder.getView(C2213R.id.iv_image).setBackgroundResource(CenterListPopupView.this.iconIds[i]);
                } else {
                    viewHolder.getView(C2213R.id.iv_image).setVisibility(8);
                }
                if (CenterListPopupView.this.checkedPosition != -1) {
                    if (viewHolder.getViewOrNull(C2213R.id.check_view) != null) {
                        viewHolder.getView(C2213R.id.check_view).setVisibility(i != CenterListPopupView.this.checkedPosition ? 8 : 0);
                        ((CheckView) viewHolder.getView(C2213R.id.check_view)).setColor(XPopup.getPrimaryColor());
                    }
                    ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(i == CenterListPopupView.this.checkedPosition ? XPopup.getPrimaryColor() : CenterListPopupView.this.getResources().getColor(C2213R.color._xpopup_title_color));
                } else {
                    if (viewHolder.getViewOrNull(C2213R.id.check_view) != null) {
                        viewHolder.getView(C2213R.id.check_view).setVisibility(8);
                    }
                    ((TextView) viewHolder.getView(C2213R.id.tv_text)).setGravity(17);
                }
                if (CenterListPopupView.this.bindItemLayoutId == 0) {
                    if (CenterListPopupView.this.popupInfo.isDarkTheme) {
                        ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(CenterListPopupView.this.getResources().getColor(C2213R.color._xpopup_white_color));
                    } else {
                        ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(CenterListPopupView.this.getResources().getColor(C2213R.color._xpopup_dark_color));
                    }
                }
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.CenterListPopupView.2
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener, com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view2, RecyclerView.ViewHolder viewHolder, int i) {
                if (CenterListPopupView.this.selectListener != null && i >= 0 && i < easyAdapter.getData().size()) {
                    CenterListPopupView.this.selectListener.onSelect(i, (String) easyAdapter.getData().get(i));
                }
                if (CenterListPopupView.this.checkedPosition != -1) {
                    CenterListPopupView.this.checkedPosition = i;
                    easyAdapter.notifyDataSetChanged();
                }
                if (CenterListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                    CenterListPopupView.this.dismiss();
                }
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(true);
        this.tv_title.setTextColor(getResources().getColor(C2213R.color._xpopup_white_color));
        findViewById(C2213R.id.xpopup_divider).setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_dark_divider));
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(false);
        this.tv_title.setTextColor(getResources().getColor(C2213R.color._xpopup_dark_color));
        findViewById(C2213R.id.xpopup_divider).setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_divider));
    }

    public CenterListPopupView setStringData(CharSequence charSequence, String[] strArr, int[] iArr) {
        this.title = charSequence;
        this.data = strArr;
        this.iconIds = iArr;
        return this;
    }

    public CenterListPopupView setOnSelectListener(OnSelectListener onSelectListener) {
        this.selectListener = onSelectListener;
        return this;
    }

    public CenterListPopupView setCheckedPosition(int i) {
        this.checkedPosition = i;
        return this;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getMaxWidth() {
        return this.popupInfo.maxWidth == 0 ? (int) (super.getMaxWidth() * 0.8f) : this.popupInfo.maxWidth;
    }
}
