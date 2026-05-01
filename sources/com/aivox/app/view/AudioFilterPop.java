package com.aivox.app.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.util.LayoutUtil;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ScreenUtil;
import com.aivox.common.C0958R;
import com.aivox.common_ui.C1034R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AudioFilterPop {
    Activity activity;
    View lineAuth;
    View lineDisAuth;
    List<TextView> list = new ArrayList();
    private Context mContext;
    private View.OnClickListener mOnClickListener;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private PopupWindow mPopupWindow;
    int nowSelect;
    TextView tvAll;
    TextView tvAuth;
    TextView tvDisAuth;
    TextView tvDisSync;
    TextView tvDisTranscribe;
    TextView tvSync;
    TextView tvTranscribe;

    public AudioFilterPop(View.OnClickListener onClickListener, PopupWindow.OnDismissListener onDismissListener, Context context, Activity activity, int i) {
        this.mOnClickListener = onClickListener;
        this.mOnDismissListener = onDismissListener;
        this.mContext = context;
        this.activity = activity;
        this.nowSelect = i;
        initView();
    }

    private void initView() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(C0726R.layout.pop_audio_filter, (ViewGroup) null);
        viewInflate.measure(0, 0);
        TextView textView = (TextView) viewInflate.findViewById(C0726R.id.filter_all);
        this.tvAll = textView;
        textView.setOnClickListener(this.mOnClickListener);
        TextView textView2 = (TextView) viewInflate.findViewById(C0726R.id.filter_sync);
        this.tvSync = textView2;
        textView2.setOnClickListener(this.mOnClickListener);
        TextView textView3 = (TextView) viewInflate.findViewById(C0726R.id.filter_dis_sync);
        this.tvDisSync = textView3;
        textView3.setOnClickListener(this.mOnClickListener);
        TextView textView4 = (TextView) viewInflate.findViewById(C0726R.id.filter_transcribe);
        this.tvTranscribe = textView4;
        textView4.setOnClickListener(this.mOnClickListener);
        TextView textView5 = (TextView) viewInflate.findViewById(C0726R.id.filter_dis_transcribe);
        this.tvDisTranscribe = textView5;
        textView5.setOnClickListener(this.mOnClickListener);
        TextView textView6 = (TextView) viewInflate.findViewById(C0726R.id.filter_auth);
        this.tvAuth = textView6;
        textView6.setOnClickListener(this.mOnClickListener);
        TextView textView7 = (TextView) viewInflate.findViewById(C0726R.id.filter_dis_auth);
        this.tvDisAuth = textView7;
        textView7.setOnClickListener(this.mOnClickListener);
        this.lineAuth = viewInflate.findViewById(C0726R.id.line_auth);
        this.lineDisAuth = viewInflate.findViewById(C0726R.id.line_disauth);
        this.list.add(this.tvAll);
        this.list.add(this.tvSync);
        this.list.add(this.tvDisSync);
        this.list.add(this.tvTranscribe);
        this.list.add(this.tvDisTranscribe);
        LayoutUtil.viewsGone(8, this.tvAuth, this.tvDisAuth, this.lineAuth, this.lineDisAuth);
        this.tvDisTranscribe.setBackgroundResource(C1034R.drawable.ripple_bg_trans_bottom);
        refreshColor(0);
        PopupWindow popupWindow = new PopupWindow(viewInflate, ScreenUtil.getScreenWidth(this.mContext) / 3, -2);
        this.mPopupWindow = popupWindow;
        popupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setOnDismissListener(this.mOnDismissListener);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        this.mPopupWindow.setAnimationStyle(C0958R.style.TopPopAnim);
        this.mPopupWindow.update();
    }

    public void refreshColor(int i) {
        Iterator<TextView> it = this.list.iterator();
        while (it.hasNext()) {
            it.next().setTextColor(this.mContext.getResources().getColor(C0874R.color.white));
        }
        this.list.get(i).setTextColor(this.mContext.getResources().getColor(C0874R.color.red));
    }

    public void onShow(View view2) {
        if (this.mPopupWindow.isShowing()) {
            return;
        }
        int width = (view2.getWidth() - this.mPopupWindow.getWidth()) / 2;
        LogUtil.m338i("宽度：" + ScreenUtil.getScreenWidth(this.mContext) + ";" + view2.getWidth() + ";" + this.mPopupWindow.getContentView().getMeasuredWidth() + ";" + width + ";" + this.mPopupWindow.getWidth());
        this.mPopupWindow.showAsDropDown(view2, width, -20);
    }

    public void backgroundAlpha(float f) {
        WindowManager.LayoutParams attributes = this.activity.getWindow().getAttributes();
        attributes.alpha = f;
        this.activity.getWindow().addFlags(2);
        this.activity.getWindow().setAttributes(attributes);
    }

    public void onDismiss() {
        if (this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
    }
}
