package com.aivox.app.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.aivox.app.C0726R;
import com.aivox.common.model.AudioInfoBean;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AudioAndFolderActionPopup {
    private final Context mContext;
    private int mHeight;
    private final List<AudioInfoBean> mInfoList = new ArrayList();
    private final OnPopupInteractionListener mListener;
    private PopupWindow mPopupWindow;
    private int mWidth;
    private TextView tvRename;

    public interface OnPopupInteractionListener {
        void doAudioDelete(List<AudioInfoBean> list);

        void doAudioRename(AudioInfoBean audioInfoBean);
    }

    public static AudioAndFolderActionPopup create(Context context, OnPopupInteractionListener onPopupInteractionListener) {
        return new AudioAndFolderActionPopup(context, onPopupInteractionListener);
    }

    private AudioAndFolderActionPopup(Context context, OnPopupInteractionListener onPopupInteractionListener) {
        this.mContext = context;
        this.mListener = onPopupInteractionListener;
        initViews();
    }

    protected void initViews() {
        final View viewInflate = LayoutInflater.from(this.mContext).inflate(C0726R.layout.audio_item_popup_layout, (ViewGroup) null);
        this.tvRename = (TextView) viewInflate.findViewById(C0726R.id.tv_rename);
        viewInflate.measure(0, 0);
        this.mPopupWindow = new PopupWindow(viewInflate, -1, -2);
        viewInflate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.aivox.app.view.AudioAndFolderActionPopup.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                viewInflate.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        this.mPopupWindow.setFocusable(false);
        this.mPopupWindow.setTouchable(true);
        this.mPopupWindow.setOutsideTouchable(false);
        this.mPopupWindow.setTouchInterceptor(new View.OnTouchListener() { // from class: com.aivox.app.view.AudioAndFolderActionPopup.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == 0 && (x < 0 || x >= AudioAndFolderActionPopup.this.mWidth || y < 0 || y >= AudioAndFolderActionPopup.this.mHeight)) {
                    Log.d("TAG", "onTouch outside:mWidth=" + AudioAndFolderActionPopup.this.mWidth + ",mHeight=" + AudioAndFolderActionPopup.this.mHeight);
                    return false;
                }
                if (motionEvent.getAction() != 4) {
                    return false;
                }
                Log.d("TAG", "onTouch outside event:mWidth=" + AudioAndFolderActionPopup.this.mWidth + ",mHeight=" + AudioAndFolderActionPopup.this.mHeight);
                return true;
            }
        });
        this.tvRename.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioAndFolderActionPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2410lambda$initViews$0$comaivoxappviewAudioAndFolderActionPopup(view2);
            }
        });
        viewInflate.findViewById(C0726R.id.tv_delete).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioAndFolderActionPopup$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2411lambda$initViews$1$comaivoxappviewAudioAndFolderActionPopup(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initViews$0$com-aivox-app-view-AudioAndFolderActionPopup, reason: not valid java name */
    /* synthetic */ void m2410lambda$initViews$0$comaivoxappviewAudioAndFolderActionPopup(View view2) {
        if (this.mListener != null) {
            if (!this.mInfoList.isEmpty()) {
                this.mListener.doAudioRename(this.mInfoList.get(0));
            }
            this.mPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$1$com-aivox-app-view-AudioAndFolderActionPopup, reason: not valid java name */
    /* synthetic */ void m2411lambda$initViews$1$comaivoxappviewAudioAndFolderActionPopup(View view2) {
        if (this.mListener != null) {
            if (!this.mInfoList.isEmpty()) {
                this.mListener.doAudioDelete(this.mInfoList);
            }
            this.mPopupWindow.dismiss();
        }
    }

    public void showAtLocation(View view2, int i, int i2, int i3) {
        this.mWidth = i2;
        this.mHeight = i3;
        this.mPopupWindow.dismiss();
        if (this.mPopupWindow.isShowing()) {
            return;
        }
        this.mPopupWindow.showAtLocation(view2, i, i2, i3);
    }

    public void updateUiAndAudioData(List<AudioInfoBean> list) {
        this.mInfoList.clear();
        this.mInfoList.addAll(list);
        if (list.size() > 1) {
            this.tvRename.setVisibility(8);
        } else {
            this.tvRename.setVisibility(0);
        }
        this.mPopupWindow.update();
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mPopupWindow.setOnDismissListener(onDismissListener);
    }

    public boolean isShowing() {
        return this.mPopupWindow.isShowing();
    }

    public void dismiss() {
        this.mPopupWindow.dismiss();
    }
}
