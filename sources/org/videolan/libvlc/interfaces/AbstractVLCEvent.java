package org.videolan.libvlc.interfaces;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractVLCEvent {
    protected final long arg1;
    protected final long arg2;
    protected final float argf1;
    protected final String args1;
    public final int type;

    public interface Listener<T extends AbstractVLCEvent> {
        void onEvent(T t);
    }

    public void release() {
    }

    public AbstractVLCEvent(int i) {
        this.type = i;
        this.arg2 = 0L;
        this.arg1 = 0L;
        this.argf1 = 0.0f;
        this.args1 = null;
    }

    public AbstractVLCEvent(int i, long j) {
        this.type = i;
        this.arg1 = j;
        this.arg2 = 0L;
        this.argf1 = 0.0f;
        this.args1 = null;
    }

    public AbstractVLCEvent(int i, long j, long j2) {
        this.type = i;
        this.arg1 = j;
        this.arg2 = j2;
        this.argf1 = 0.0f;
        this.args1 = null;
    }

    public AbstractVLCEvent(int i, float f) {
        this.type = i;
        this.arg2 = 0L;
        this.arg1 = 0L;
        this.argf1 = f;
        this.args1 = null;
    }

    public AbstractVLCEvent(int i, long j, String str) {
        this.type = i;
        this.arg1 = j;
        this.arg2 = 0L;
        this.argf1 = 0.0f;
        this.args1 = str;
    }
}
