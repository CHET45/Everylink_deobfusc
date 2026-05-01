package com.lxj.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
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
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.CheckView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class BottomListPopupView extends BottomPopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    int checkedPosition;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;
    CharSequence title;
    TextView tv_cancel;
    TextView tv_title;
    View vv_divider;

    public BottomListPopupView(Context context, int i, int i2) {
        super(context);
        this.checkedPosition = -1;
        this.bindLayoutId = i;
        this.bindItemLayoutId = i2;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        int i = this.bindLayoutId;
        return i == 0 ? C2213R.layout._xpopup_bottom_impl_list : i;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(C2213R.id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        this.tv_title = (TextView) findViewById(C2213R.id.tv_title);
        this.tv_cancel = (TextView) findViewById(C2213R.id.tv_cancel);
        this.vv_divider = findViewById(C2213R.id.vv_divider);
        TextView textView = this.tv_cancel;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.impl.BottomListPopupView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    BottomListPopupView.this.dismiss();
                }
            });
        }
        if (this.tv_title != null) {
            if (TextUtils.isEmpty(this.title)) {
                this.tv_title.setVisibility(8);
                if (findViewById(C2213R.id.xpopup_divider) != null) {
                    findViewById(C2213R.id.xpopup_divider).setVisibility(8);
                }
            } else {
                this.tv_title.setText(this.title);
            }
        }
        List listAsList = Arrays.asList(this.data);
        int i = this.bindItemLayoutId;
        if (i == 0) {
            i = C2213R.layout._xpopup_adapter_text_match;
        }
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(listAsList, i) { // from class: com.lxj.xpopup.impl.BottomListPopupView.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(ViewHolder viewHolder, String str, int i2) {
                viewHolder.setText(C2213R.id.tv_text, str);
                if (BottomListPopupView.this.iconIds != null && BottomListPopupView.this.iconIds.length > i2) {
                    viewHolder.getView(C2213R.id.iv_image).setVisibility(0);
                    viewHolder.getView(C2213R.id.iv_image).setBackgroundResource(BottomListPopupView.this.iconIds[i2]);
                } else {
                    viewHolder.getView(C2213R.id.iv_image).setVisibility(8);
                }
                if (BottomListPopupView.this.checkedPosition != -1) {
                    if (viewHolder.getViewOrNull(C2213R.id.check_view) != null) {
                        viewHolder.getView(C2213R.id.check_view).setVisibility(i2 != BottomListPopupView.this.checkedPosition ? 8 : 0);
                        ((CheckView) viewHolder.getView(C2213R.id.check_view)).setColor(XPopup.getPrimaryColor());
                    }
                    ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(i2 == BottomListPopupView.this.checkedPosition ? XPopup.getPrimaryColor() : BottomListPopupView.this.getResources().getColor(C2213R.color._xpopup_title_color));
                } else {
                    if (viewHolder.getViewOrNull(C2213R.id.check_view) != null) {
                        viewHolder.getView(C2213R.id.check_view).setVisibility(8);
                    }
                    ((TextView) viewHolder.getView(C2213R.id.tv_text)).setGravity(17);
                }
                if (BottomListPopupView.this.bindItemLayoutId == 0) {
                    if (BottomListPopupView.this.popupInfo.isDarkTheme) {
                        ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(BottomListPopupView.this.getResources().getColor(C2213R.color._xpopup_white_color));
                    } else {
                        ((TextView) viewHolder.getView(C2213R.id.tv_text)).setTextColor(BottomListPopupView.this.getResources().getColor(C2213R.color._xpopup_dark_color));
                    }
                }
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.BottomListPopupView.3
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.SimpleOnItemClickListener, com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view2, RecyclerView.ViewHolder viewHolder, int i2) {
                if (BottomListPopupView.this.selectListener != null) {
                    BottomListPopupView.this.selectListener.onSelect(i2, (String) easyAdapter.getData().get(i2));
                }
                if (BottomListPopupView.this.checkedPosition != -1) {
                    BottomListPopupView.this.checkedPosition = i2;
                    easyAdapter.notifyDataSetChanged();
                }
                BottomListPopupView.this.postDelayed(new Runnable() { // from class: com.lxj.xpopup.impl.BottomListPopupView.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BottomListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                            BottomListPopupView.this.dismiss();
                        }
                    }
                }, 100L);
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    public BottomListPopupView setStringData(CharSequence charSequence, String[] strArr, int[] iArr) {
        this.title = charSequence;
        this.data = strArr;
        this.iconIds = iArr;
        return this;
    }

    public BottomListPopupView setOnSelectListener(OnSelectListener onSelectListener) {
        this.selectListener = onSelectListener;
        return this;
    }

    public BottomListPopupView setCheckedPosition(int i) {
        this.checkedPosition = i;
        return this;
    }

    protected void applyTheme() {
        if (this.bindLayoutId == 0) {
            if (this.popupInfo.isDarkTheme) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(true);
        this.tv_title.setTextColor(getResources().getColor(C2213R.color._xpopup_white_color));
        TextView textView = this.tv_cancel;
        if (textView != null) {
            textView.setTextColor(getResources().getColor(C2213R.color._xpopup_white_color));
        }
        findViewById(C2213R.id.xpopup_divider).setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_dark_divider));
        View view2 = this.vv_divider;
        if (view2 != null) {
            view2.setBackgroundColor(Color.parseColor("#1B1B1B"));
        }
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(C2213R.color._xpopup_dark_color), this.popupInfo.borderRadius, this.popupInfo.borderRadius, 0.0f, 0.0f));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(false);
        this.tv_title.setTextColor(getResources().getColor(C2213R.color._xpopup_dark_color));
        TextView textView = this.tv_cancel;
        if (textView != null) {
            textView.setTextColor(getResources().getColor(C2213R.color._xpopup_dark_color));
        }
        findViewById(C2213R.id.xpopup_divider).setBackgroundColor(getResources().getColor(C2213R.color._xpopup_list_divider));
        View view2 = this.vv_divider;
        if (view2 != null) {
            view2.setBackgroundColor(getResources().getColor(C2213R.color._xpopup_white_color));
        }
        getPopupImplView().setBackground(XPopupUtils.createDrawable(getResources().getColor(C2213R.color._xpopup_light_color), this.popupInfo.borderRadius, this.popupInfo.borderRadius, 0.0f, 0.0f));
    }
}
