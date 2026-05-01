package com.aivox.app.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.aivox.app.C0726R;
import com.aivox.base.C0874R;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.ScreenUtil;
import com.aivox.common.C0958R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AudioDoubleSpeedPop {
    boolean isDown;
    ImageView ivDown;
    ImageView ivUp;
    List<TextView> list;
    private Context mContext;
    private View.OnClickListener mOnClickListener;
    private PopupWindow mPopupWindow;
    int nowSelect;
    TextView tv05;
    TextView tv1;
    TextView tv15;
    TextView tv2;

    public AudioDoubleSpeedPop(View.OnClickListener onClickListener, Context context, int i) {
        this.list = new ArrayList();
        this.mOnClickListener = onClickListener;
        this.mContext = context;
        this.nowSelect = i;
        this.isDown = false;
        initView();
    }

    public AudioDoubleSpeedPop(View.OnClickListener onClickListener, Context context, int i, boolean z) {
        this.list = new ArrayList();
        this.mOnClickListener = onClickListener;
        this.mContext = context;
        this.nowSelect = i;
        this.isDown = z;
        initView();
    }

    private void initView() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(C0726R.layout.pop_audio_double_speed, (ViewGroup) null);
        viewInflate.measure(0, 0);
        this.ivUp = (ImageView) viewInflate.findViewById(C0726R.id.up_arrow);
        this.ivDown = (ImageView) viewInflate.findViewById(C0726R.id.down_arrow);
        if (this.isDown) {
            this.ivUp.setVisibility(8);
            this.ivDown.setVisibility(0);
        }
        TextView textView = (TextView) viewInflate.findViewById(C0726R.id.speed_2x);
        this.tv2 = textView;
        textView.setOnClickListener(this.mOnClickListener);
        TextView textView2 = (TextView) viewInflate.findViewById(C0726R.id.speed_1_5x);
        this.tv15 = textView2;
        textView2.setOnClickListener(this.mOnClickListener);
        TextView textView3 = (TextView) viewInflate.findViewById(C0726R.id.speed_1x);
        this.tv1 = textView3;
        textView3.setOnClickListener(this.mOnClickListener);
        TextView textView4 = (TextView) viewInflate.findViewById(C0726R.id.speed_0_5x);
        this.tv05 = textView4;
        textView4.setOnClickListener(this.mOnClickListener);
        this.list.add(this.tv2);
        this.list.add(this.tv15);
        this.list.add(this.tv1);
        this.list.add(this.tv05);
        refreshColor(2);
        PopupWindow popupWindow = new PopupWindow(viewInflate, -2, -2);
        this.mPopupWindow = popupWindow;
        popupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        this.mPopupWindow.setAnimationStyle(this.isDown ? C0958R.style.BottomPopAnim : C0958R.style.TopPopAnim);
        this.mPopupWindow.update();
    }

    public void refreshColor(int i) {
        Iterator<TextView> it = this.list.iterator();
        while (it.hasNext()) {
            it.next().setTextColor(this.mContext.getResources().getColor(C0874R.color.white));
        }
        this.list.get(i).setTextColor(this.mContext.getResources().getColor(C0874R.color.home_txt));
    }

    public void onShow(View view2) {
        if (this.mPopupWindow.isShowing()) {
            return;
        }
        int width = (view2.getWidth() - this.mPopupWindow.getContentView().getMeasuredWidth()) / 2;
        LogUtil.m338i("宽度：" + ScreenUtil.getScreenWidth(this.mContext) + ";" + view2.getWidth() + ";" + view2.getHeight() + ";" + this.mPopupWindow.getContentView().getMeasuredWidth() + ";" + width + ";" + this.mPopupWindow.getWidth());
        if (this.isDown) {
            int[] iArr = new int[2];
            view2.getLocationInWindow(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            LogUtil.m338i("x:" + i + ";y:" + i2);
            PopupWindow popupWindow = this.mPopupWindow;
            popupWindow.showAtLocation(view2, 0, i, i2 - popupWindow.getContentView().getMeasuredHeight());
            return;
        }
        this.mPopupWindow.showAsDropDown(view2, width, 15);
    }

    public void onDismiss() {
        if (this.mPopupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
    }
}
