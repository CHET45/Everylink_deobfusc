package com.aivox.common_ui.pulltorefresh.extras;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import com.aivox.common_ui.pulltorefresh.PullToRefreshBase;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class SoundPullEventListener<V extends View> implements PullToRefreshBase.OnPullEventListener<V> {
    private final Context mContext;
    private MediaPlayer mCurrentMediaPlayer;
    private final HashMap<PullToRefreshBase.State, Integer> mSoundMap = new HashMap<>();

    public SoundPullEventListener(Context context) {
        this.mContext = context;
    }

    @Override // com.aivox.common_ui.pulltorefresh.PullToRefreshBase.OnPullEventListener
    public final void onPullEvent(PullToRefreshBase<V> pullToRefreshBase, PullToRefreshBase.State state, PullToRefreshBase.Mode mode) {
        Integer num = this.mSoundMap.get(state);
        if (num != null) {
            playSound(num.intValue());
        }
    }

    public void addSoundEvent(PullToRefreshBase.State state, int i) {
        this.mSoundMap.put(state, Integer.valueOf(i));
    }

    public void clearSounds() {
        this.mSoundMap.clear();
    }

    public MediaPlayer getCurrentMediaPlayer() {
        return this.mCurrentMediaPlayer;
    }

    private void playSound(int i) {
        MediaPlayer mediaPlayer = this.mCurrentMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mCurrentMediaPlayer.release();
        }
        MediaPlayer mediaPlayerCreate = MediaPlayer.create(this.mContext, i);
        this.mCurrentMediaPlayer = mediaPlayerCreate;
        if (mediaPlayerCreate != null) {
            mediaPlayerCreate.start();
        }
    }
}
