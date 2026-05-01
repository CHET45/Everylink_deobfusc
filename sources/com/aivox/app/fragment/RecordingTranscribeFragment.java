package com.aivox.app.fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.app.C0726R;
import com.aivox.app.adapter.TranscribeAdapter;
import com.aivox.app.databinding.FragmentRecordingTranscribeBinding;
import com.aivox.common.base.BaseBindingFragment;
import com.aivox.common.model.Transcribe;
import com.blankj.utilcode.util.LogUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RecordingTranscribeFragment extends BaseBindingFragment {
    private static final String TAG = "RecordingTranscribeFragment";
    private int audioId;
    private CountDownTimer countDownTimer;
    private FragmentRecordingTranscribeBinding mBinding;
    private TranscribeAdapter transcribeAdapter;
    private final List<Transcribe> transcribeList = new ArrayList();
    private boolean autoScroll = true;
    private boolean isCountingDown = false;

    public static RecordingTranscribeFragment getInstance() {
        return new RecordingTranscribeFragment();
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public View initBindingAndViews(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        this.mBinding = FragmentRecordingTranscribeBinding.inflate(layoutInflater, viewGroup, false);
        this.countDownTimer = new CountDownTimer(3000L, 1000L) { // from class: com.aivox.app.fragment.RecordingTranscribeFragment.1
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                RecordingTranscribeFragment.this.isCountingDown = false;
                if (RecordingTranscribeFragment.this.autoScroll) {
                    return;
                }
                RecordingTranscribeFragment.this.mBinding.ivToBottom.setVisibility(0);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mActivity);
        linearLayoutManager.setOrientation(1);
        this.mBinding.recyclerview.setLayoutManager(linearLayoutManager);
        this.transcribeAdapter = new TranscribeAdapter(this.mActivity, C0726R.layout.item_transcribe, true);
        this.mBinding.recyclerview.setAdapter(this.transcribeAdapter);
        this.mBinding.recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.aivox.app.fragment.RecordingTranscribeFragment.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    LogUtils.eTag(RecordingTranscribeFragment.TAG, "--空闲--");
                    return;
                }
                if (i != 1) {
                    if (i == 2) {
                        LogUtils.eTag(RecordingTranscribeFragment.TAG, "--驻留--");
                    }
                } else {
                    LogUtils.eTag(RecordingTranscribeFragment.TAG, "--拖拽--");
                    if (RecordingTranscribeFragment.this.transcribeAdapter.getItemCount() > 5) {
                        RecordingTranscribeFragment.this.autoScroll = false;
                        RecordingTranscribeFragment.this.startTimeCount();
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                if (recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent() >= recyclerView.computeVerticalScrollRange()) {
                    LogUtils.eTag(RecordingTranscribeFragment.TAG, "BOTTOM");
                    RecordingTranscribeFragment.this.autoScroll = true;
                    RecordingTranscribeFragment.this.mBinding.ivToBottom.setVisibility(4);
                    if (RecordingTranscribeFragment.this.countDownTimer != null) {
                        RecordingTranscribeFragment.this.countDownTimer.cancel();
                    }
                    RecordingTranscribeFragment.this.isCountingDown = false;
                }
            }
        });
        this.mBinding.ivToBottom.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.app.fragment.RecordingTranscribeFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m322x3406b1f0(view2);
            }
        });
        return this.mBinding.getRoot();
    }

    /* JADX INFO: renamed from: lambda$initBindingAndViews$0$com-aivox-app-fragment-RecordingTranscribeFragment */
    /* synthetic */ void m322x3406b1f0(View view2) {
        this.mBinding.ivToBottom.setVisibility(4);
        this.mBinding.recyclerview.scrollToPosition(this.transcribeAdapter.getItemCount() - 1);
    }

    @Override // com.aivox.common.base.BaseBindingFragment
    public void clearBinding() {
        this.mBinding = null;
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            this.isCountingDown = false;
            countDownTimer.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimeCount() {
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer == null || this.isCountingDown) {
            return;
        }
        this.isCountingDown = true;
        countDownTimer.start();
    }

    public void setTranscribeList(List<Transcribe> list) {
        this.transcribeList.clear();
        this.transcribeList.addAll(list);
        if (this.mBinding == null) {
            return;
        }
        if (!this.transcribeList.isEmpty()) {
            this.mBinding.tvRecordPrepare.setVisibility(8);
        }
        TranscribeAdapter transcribeAdapter = this.transcribeAdapter;
        if (transcribeAdapter != null) {
            transcribeAdapter.setmDate(list);
        }
        if (this.mBinding == null || this.transcribeAdapter.getItemCount() <= 0 || !this.autoScroll) {
            return;
        }
        this.mBinding.recyclerview.scrollToPosition(this.transcribeAdapter.getItemCount() - 1);
    }

    public void setAudioId(int i) {
        this.audioId = i;
    }

    public void setCurSpeechIndex(int i) {
        this.transcribeAdapter.setCurSpeechIndex(i);
    }

    public void setFontSize(float f) {
        this.transcribeAdapter.setFontsize(f);
    }

    public void pauseAutoScroll() {
        this.autoScroll = false;
        this.mBinding.ivToBottom.setVisibility(0);
    }

    public void resumeAutoScroll() {
        this.autoScroll = true;
        FragmentRecordingTranscribeBinding fragmentRecordingTranscribeBinding = this.mBinding;
        if (fragmentRecordingTranscribeBinding != null) {
            fragmentRecordingTranscribeBinding.ivToBottom.setVisibility(4);
            this.mBinding.recyclerview.scrollToPosition(this.transcribeAdapter.getItemCount() - 1);
        }
    }
}
