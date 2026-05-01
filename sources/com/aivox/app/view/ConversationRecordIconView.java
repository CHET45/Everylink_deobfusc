package com.aivox.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.aivox.app.C0726R;
import com.aivox.app.databinding.ViewConversationRecordBinding;
import com.aivox.base.C0874R;
import com.aivox.common.util.VibrateHelp;
import com.aivox.common_ui.C1034R;

/* JADX INFO: loaded from: classes.dex */
public class ConversationRecordIconView extends ConstraintLayout {
    private Context context;
    private long downTime;
    private boolean isCountDown;
    private boolean isOnLeft;
    private boolean isRecording;
    private ViewConversationRecordBinding mBinding;
    private onTouchChangeListener mOnTouchChangeListener;
    private onStatusChangeListener onStatusChangeListener;
    private Integer rippleColor;
    private TypedArray typedArray;

    public interface onStatusChangeListener {
        void onStatusChange(boolean z);
    }

    public interface onTouchChangeListener {
        void onEnd(boolean z, boolean z2);

        void onStart(boolean z);
    }

    public ConversationRecordIconView(Context context) {
        this(context, null);
    }

    public ConversationRecordIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ConversationRecordIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mBinding = ViewConversationRecordBinding.inflate(LayoutInflater.from(context), this, true);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0726R.styleable.ConversationRecordIconView);
        this.typedArray = typedArrayObtainStyledAttributes;
        Integer numValueOf = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(C0726R.styleable.ConversationRecordIconView_ripple_color, ContextCompat.getColor(context, C0874R.color.red)));
        this.rippleColor = numValueOf;
        this.mBinding.setRippersColor(numValueOf);
        this.mBinding.tvTime.setTextColor(this.typedArray.getColor(C0726R.styleable.ConversationRecordIconView_time_text_color, ContextCompat.getColor(context, C0874R.color.black_text)));
        this.mBinding.ivPause.setImageResource(this.typedArray.getResourceId(C0726R.styleable.ConversationRecordIconView_pause_icon, C1034R.mipmap.ic_record_pause_dark));
        this.mBinding.ivMic.setImageResource(this.typedArray.getResourceId(C0726R.styleable.ConversationRecordIconView_mic_icon, C1034R.drawable.ic_mic_other));
        this.isOnLeft = this.typedArray.getBoolean(C0726R.styleable.ConversationRecordIconView_is_left_icon, true);
        this.context = context;
        refresh();
        this.mBinding.clParent.setOnTouchListener(new View.OnTouchListener() { // from class: com.aivox.app.view.ConversationRecordIconView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    if (ConversationRecordIconView.this.mOnTouchChangeListener != null) {
                        ConversationRecordIconView.this.mOnTouchChangeListener.onStart(ConversationRecordIconView.this.isOnLeft);
                    }
                    ConversationRecordIconView.this.downTime = System.currentTimeMillis();
                    VibrateHelp.vSimple(view2.getContext(), 60);
                } else if (motionEvent.getAction() == 1 && ConversationRecordIconView.this.mOnTouchChangeListener != null) {
                    ConversationRecordIconView.this.mOnTouchChangeListener.onEnd(ConversationRecordIconView.this.isOnLeft, System.currentTimeMillis() - ConversationRecordIconView.this.downTime < 200);
                }
                return true;
            }
        });
    }

    public void setOnTouchChangeListener(onTouchChangeListener ontouchchangelistener) {
        this.mOnTouchChangeListener = ontouchchangelistener;
    }

    public void setOnStatusChangeListener(final onStatusChangeListener onstatuschangelistener) {
        this.onStatusChangeListener = onstatuschangelistener;
        this.mBinding.ivMic.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.ConversationRecordIconView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                onstatuschangelistener.onStatusChange(true);
            }
        });
        this.mBinding.ripple.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.ConversationRecordIconView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                onstatuschangelistener.onStatusChange(false);
            }
        });
        this.mBinding.tvTime.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.ConversationRecordIconView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                onstatuschangelistener.onStatusChange(false);
            }
        });
        this.mBinding.ivPause.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.view.ConversationRecordIconView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                onstatuschangelistener.onStatusChange(false);
            }
        });
    }

    private void refresh() {
        int i = 8;
        this.mBinding.ivMic.setVisibility(this.isRecording ? 8 : 0);
        this.mBinding.ripple.setVisibility(this.isRecording ? 0 : 8);
        if (this.isRecording) {
            this.mBinding.ripple.startRippleAnimation();
        } else {
            this.mBinding.ripple.stopRippleAnimation();
        }
        this.mBinding.tvTime.setVisibility((this.isRecording && this.isCountDown) ? 0 : 8);
        ImageView imageView = this.mBinding.ivPause;
        if (this.isRecording && !this.isCountDown) {
            i = 0;
        }
        imageView.setVisibility(i);
    }

    public void refresh(boolean z, boolean z2, int i) {
        this.isCountDown = z2;
        this.isRecording = z;
        this.mBinding.tvTime.setText(i + "″");
        refresh();
    }
}
