package com.lxj.xpopup.impl;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.C2213R;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class AttachListPopupView extends AttachPopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    protected int contentGravity;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;

    public AttachListPopupView(Context context, int i, int i2) {
        super(context);
        this.contentGravity = 17;
        this.bindLayoutId = i;
        this.bindItemLayoutId = i2;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        int i = this.bindLayoutId;
        return i == 0 ? C2213R.layout._xpopup_attach_impl_list : i;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(C2213R.id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        List listAsList = Arrays.asList(this.data);
        int i = this.bindItemLayoutId;
        if (i == 0) {
            i = C2213R.layout._xpopup_adapter_text;
        }
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(listAsList, i) { // from class: com.lxj.xpopup.impl.AttachListPopupView.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(ViewHolder viewHolder, String str, int i2) {
                viewHolder.setText(C2213R.id.tv_text, str);
                if (AttachListPopupView.this.iconIds != null && AttachListPopupView.this.iconIds.length > i2) {
                    viewHolder.getView(C2213R.id.iv_image).setVisibility(0);
                    viewHolder.getView(C2213R.id.iv_image).setBackgroundResource(AttachListPopupView.this.iconIds[i2]);
                } else {
                    viewHolder.getView(C2213R.id.iv_image).setVisibility(8);
                }
                if (AttachListPopupView.this.bindItemLayoutId == 0) {
                    if (AttachListPopupView.this.popupInfo.isDarkTheme) {
                        ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(AttachListPopupView.this.getResources().getColor(C2213R.color._xpopup_white_color));
                    } else {
                        ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(AttachListPopupView.this.getResources().getColor(C2213R.color._xpopup_dark_color));
                    }
                    ((LinearLayout) viewHolder.getView(C2213R.id._ll_temp)).setGravity(AttachListPopupView.this.contentGravity);
                }
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.AttachListPopupView.2
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener, com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view2, RecyclerView.ViewHolder viewHolder, int i2) {
                if (AttachListPopupView.this.selectListener != null) {
                    AttachListPopupView.this.selectListener.onSelect(i2, (String) easyAdapter.getData().get(i2));
                }
                if (AttachListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                    AttachListPopupView.this.dismiss();
                }
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    protected void applyTheme() {
        if (this.bindLayoutId == 0) {
            if (this.popupInfo.isDarkTheme) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
            this.attachPopupContainer.setBackground(XPopupUtils.createDrawable(getResources().getColor(this.popupInfo.isDarkTheme ? C2213R.color._xpopup_dark_color : C2213R.color._xpopup_light_color), this.popupInfo.borderRadius));
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(true);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(false);
    }

    public AttachListPopupView setStringData(String[] strArr, int[] iArr) {
        this.data = strArr;
        this.iconIds = iArr;
        return this;
    }

    public AttachListPopupView setContentGravity(int i) {
        this.contentGravity = i;
        return this;
    }

    public AttachListPopupView setOnSelectListener(OnSelectListener onSelectListener) {
        this.selectListener = onSelectListener;
        return this;
    }
}
