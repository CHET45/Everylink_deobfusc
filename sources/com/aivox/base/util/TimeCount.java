package com.aivox.base.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.aivox.base.C0874R;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class TimeCount extends CountDownTimer {
    private String formatDateStr;
    private Context mContext;
    private OnFinishListener onFinishListener;
    private TextView textView;
    public int txt;
    public int txtBgIds;
    public int txtBgIds_;
    public int txtColorIds;
    public int txtColorIds_;
    public int txtIds_;

    public interface OnFinishListener {
        void onFinish();
    }

    public void setFormatDateStr(String str) {
        this.formatDateStr = str;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public TimeCount(long j, long j2) {
        super(j, j2);
        this.txtBgIds = C0874R.drawable.btn_logout;
        this.txtBgIds_ = C0874R.drawable.btn_logout_;
        this.txtColorIds = C0874R.color.home_txt;
        this.txtColorIds_ = C0874R.color.white;
        this.txt = C0874R.string.send_code;
        this.txtIds_ = C0874R.string.code_countdown;
        this.formatDateStr = null;
    }

    public TimeCount(long j, long j2, TextView textView, Context context) {
        super(j, j2);
        this.txtBgIds = C0874R.drawable.btn_logout;
        this.txtBgIds_ = C0874R.drawable.btn_logout_;
        this.txtColorIds = C0874R.color.home_txt;
        this.txtColorIds_ = C0874R.color.white;
        this.txt = C0874R.string.send_code;
        this.txtIds_ = C0874R.string.code_countdown;
        this.formatDateStr = null;
        this.textView = textView;
        this.mContext = context;
    }

    public void setTxt(int i) {
        this.txt = i;
    }

    public void setTxtIds_(int i) {
        this.txtIds_ = i;
    }

    public void setTxtBgIds(int i) {
        this.txtBgIds = i;
    }

    public void setTxtBgIds_(int i) {
        this.txtBgIds_ = i;
    }

    public void setTxtColorIds(int i) {
        this.txtColorIds = i;
    }

    public void setTxtColorIds_(int i) {
        this.txtColorIds_ = i;
    }

    @Override // android.os.CountDownTimer
    public void onTick(long j) {
        this.textView.setClickable(false);
        this.textView.setTextColor(this.mContext.getResources().getColor(this.txtColorIds_));
        this.textView.setBackgroundResource(this.txtBgIds_);
        if (this.formatDateStr == null) {
            this.textView.setText(String.format(this.mContext.getResources().getString(this.txtIds_), String.valueOf(j / 1000)));
            return;
        }
        Date date = new Date(j);
        this.textView.setText(new SimpleDateFormat(this.formatDateStr).format(date));
    }

    @Override // android.os.CountDownTimer
    public void onFinish() {
        OnFinishListener onFinishListener = this.onFinishListener;
        if (onFinishListener == null) {
            this.textView.setText(this.txt);
            this.textView.setClickable(true);
            this.textView.setTextColor(this.mContext.getResources().getColor(this.txtColorIds));
            this.textView.setBackgroundResource(this.txtBgIds);
            return;
        }
        onFinishListener.onFinish();
    }
}
