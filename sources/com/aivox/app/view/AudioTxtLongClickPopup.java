package com.aivox.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.aivox.app.C0726R;
import com.aivox.common.C0958R;

/* JADX INFO: loaded from: classes.dex */
public class AudioTxtLongClickPopup {
    private View itemEdit;
    private View itemEditSpeaker;
    private View itemNote;
    private View itemPic;
    private View itemShare;
    private final Context mContext;
    private int mItemPicCount;
    private int mItemPos;
    private final AudioTxtLongClickListener mListener;
    private PopupWindow mPopupWindow;

    public interface AudioTxtLongClickListener {
        void addPicTag(int i);

        void editName(int i);

        void longClick2Copy();

        void longClick2Delete();

        void longClick2EditContent(int i);

        void longClick2EditMark(int i);

        void longClick2Share();

        void longClick2TranslateSingle();
    }

    public static AudioTxtLongClickPopup create(Context context, boolean z) {
        return new AudioTxtLongClickPopup(context, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private AudioTxtLongClickPopup(Context context, boolean z) {
        this.mContext = context;
        this.mListener = (AudioTxtLongClickListener) context;
        initViews();
    }

    protected void initViews() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(C0726R.layout.pop_audio_txt_longclick, (ViewGroup) null);
        PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -2);
        this.mPopupWindow = popupWindow;
        popupWindow.setAnimationStyle(C0958R.style.LeftTopPopAnim);
        viewInflate.findViewById(C0726R.id.item_edit).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2419lambda$initViews$0$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
        viewInflate.findViewById(C0726R.id.item_mark).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2420lambda$initViews$1$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
        viewInflate.findViewById(C0726R.id.item_trans_single).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2421lambda$initViews$2$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
        viewInflate.findViewById(C0726R.id.item_copy).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2422lambda$initViews$3$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
        viewInflate.findViewById(C0726R.id.item_delete).setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2423lambda$initViews$4$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
        this.itemPic = viewInflate.findViewById(C0726R.id.item_pic);
        this.itemShare = viewInflate.findViewById(C0726R.id.item_share);
        this.itemEditSpeaker = viewInflate.findViewById(C0726R.id.item_edit_speaker);
        this.itemNote = viewInflate.findViewById(C0726R.id.item_mark);
        this.itemEdit = viewInflate.findViewById(C0726R.id.item_edit);
        this.itemPic.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2424lambda$initViews$5$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
        this.itemShare.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2425lambda$initViews$6$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
        this.itemEditSpeaker.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.AudioTxtLongClickPopup$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m2426lambda$initViews$7$comaivoxappviewAudioTxtLongClickPopup(view2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initViews$0$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2419lambda$initViews$0$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.longClick2EditContent(this.mItemPos);
            this.mPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$1$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2420lambda$initViews$1$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.longClick2EditMark(this.mItemPos);
            this.mPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$2$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2421lambda$initViews$2$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.longClick2TranslateSingle();
            this.mPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$3$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2422lambda$initViews$3$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.longClick2Copy();
            this.mPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$4$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2423lambda$initViews$4$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.longClick2Delete();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$5$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2424lambda$initViews$5$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.addPicTag(this.mItemPicCount);
            this.mPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$6$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2425lambda$initViews$6$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.longClick2Share();
            this.mPopupWindow.dismiss();
        }
    }

    /* JADX INFO: renamed from: lambda$initViews$7$com-aivox-app-view-AudioTxtLongClickPopup, reason: not valid java name */
    /* synthetic */ void m2426lambda$initViews$7$comaivoxappviewAudioTxtLongClickPopup(View view2) {
        AudioTxtLongClickListener audioTxtLongClickListener = this.mListener;
        if (audioTxtLongClickListener != null) {
            audioTxtLongClickListener.editName(this.mItemPos);
            this.mPopupWindow.dismiss();
        }
    }

    public void show(int i, int i2, View view2, int i3) {
        this.mItemPos = i;
        this.mItemPicCount = i2;
        this.mPopupWindow.dismiss();
        if (this.mPopupWindow.isShowing()) {
            return;
        }
        this.mPopupWindow.showAtLocation(view2, 0, 0, i3);
    }

    public void hideItem() {
        this.itemEditSpeaker.setVisibility(8);
        this.itemEdit.setVisibility(8);
        this.itemPic.setVisibility(8);
        this.itemNote.setVisibility(8);
        this.itemShare.setVisibility(8);
    }

    public void update(int i, int i2) {
        this.mItemPos = i;
        this.mItemPicCount = i2;
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

    public void changeMode(boolean z) {
        this.itemPic.setVisibility(z ? 8 : 0);
        this.itemEdit.setVisibility(z ? 8 : 0);
        this.itemNote.setVisibility(z ? 8 : 0);
        this.itemEditSpeaker.setVisibility(z ? 8 : 0);
        this.itemShare.setVisibility(8);
        this.mPopupWindow.update();
    }

    public void changeMultiSpeakerMode(boolean z) {
        this.itemEditSpeaker.setVisibility(z ? 0 : 8);
        this.mPopupWindow.update();
    }
}
