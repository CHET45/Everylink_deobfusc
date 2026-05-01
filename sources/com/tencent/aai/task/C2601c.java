package com.tencent.aai.task;

import android.os.Process;
import com.tencent.aai.audio.data.PcmAudioDataSource;
import com.tencent.aai.exception.ClientException;
import com.tencent.aai.exception.ClientExceptionType;
import com.tencent.aai.log.AAILogger;
import com.tencent.aai.task.listener.AudioRecognizeBufferListener;
import com.tencent.aai.task.listener.AudioRecognizerListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

/* JADX INFO: renamed from: com.tencent.aai.task.c */
/* JADX INFO: loaded from: classes4.dex */
public class C2601c {

    /* JADX INFO: renamed from: a */
    public String f963a = C2601c.class.getName();

    /* JADX INFO: renamed from: b */
    public final DecimalFormatSymbols f964b;

    /* JADX INFO: renamed from: c */
    public final DecimalFormat f965c;

    /* JADX INFO: renamed from: d */
    public int f966d;

    /* JADX INFO: renamed from: e */
    public int f967e;

    /* JADX INFO: renamed from: f */
    public boolean f968f;

    /* JADX INFO: renamed from: g */
    public boolean f969g;

    /* JADX INFO: renamed from: h */
    public int f970h;

    /* JADX INFO: renamed from: i */
    public AudioRecognizeBufferListener f971i;

    /* JADX INFO: renamed from: j */
    public c f972j;

    /* JADX INFO: renamed from: k */
    public PcmAudioDataSource f973k;

    /* JADX INFO: renamed from: l */
    public AudioRecognizerListener f974l;

    /* JADX INFO: renamed from: m */
    public boolean f975m;

    /* JADX INFO: renamed from: com.tencent.aai.task.c$b */
    public static class b {

        /* JADX INFO: renamed from: a */
        public int f976a = 5000;

        /* JADX INFO: renamed from: b */
        public int f977b = 40;

        /* JADX INFO: renamed from: c */
        public boolean f978c = true;

        /* JADX INFO: renamed from: d */
        public boolean f979d = true;

        /* JADX INFO: renamed from: e */
        public int f980e = 16000;

        /* JADX INFO: renamed from: f */
        public int f981f = 40;

        /* JADX INFO: renamed from: g */
        public PcmAudioDataSource f982g;

        /* JADX INFO: renamed from: a */
        public b m946a(int i) {
            this.f976a = i;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public b m947a(PcmAudioDataSource pcmAudioDataSource) {
            this.f982g = pcmAudioDataSource;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public b m948a(boolean z) {
            this.f979d = z;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2601c m949a() {
            return new C2601c(this.f976a, this.f977b, this.f978c, this.f980e, this.f981f, this.f982g, this.f979d);
        }

        /* JADX INFO: renamed from: b */
        public b m950b(int i) {
            this.f977b = i;
            return this;
        }

        /* JADX INFO: renamed from: b */
        public b m951b(boolean z) {
            this.f978c = z;
            return this;
        }

        /* JADX INFO: renamed from: c */
        public b m952c(int i) {
            this.f981f = i;
            return this;
        }
    }

    /* JADX INFO: renamed from: com.tencent.aai.task.c$c */
    public class c implements Runnable {

        /* JADX INFO: renamed from: a */
        public volatile boolean f983a;

        public c() {
            this.f983a = false;
        }

        /* JADX INFO: renamed from: a */
        public final float m953a(short[] sArr, int i) {
            long j = 0;
            for (short s : sArr) {
                j += (long) (s * s);
            }
            double dLog10 = Math.log10(j / ((double) i)) * 10.0d;
            if (dLog10 < 0.0d) {
                return 0.0f;
            }
            String str = C2601c.this.f965c.format(dLog10);
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException e) {
                AAILogger.error(C2601c.this.f963a, "formatted value is " + str);
                AAILogger.error(C2601c.this.f963a, "parse float exception: " + e.getLocalizedMessage());
                return 0.0f;
            }
        }

        /* JADX INFO: renamed from: a */
        public final void m954a() {
            AAILogger.info(C2601c.this.f963a, "handle on finish.");
            if (C2601c.this.f974l != null) {
                C2601c.this.f974l.onFinish();
            }
        }

        /* JADX INFO: renamed from: a */
        public final void m955a(float f) {
            if (C2601c.this.f974l == null || f == 0.0f) {
                return;
            }
            C2601c.this.f974l.onVoiceDb(f);
        }

        /* JADX INFO: renamed from: a */
        public final void m956a(int i) {
            AAILogger.debug(C2601c.this.f963a, "on volume callback..");
            if (C2601c.this.f974l != null) {
                C2601c.this.f974l.onVolume(i);
            }
        }

        /* JADX INFO: renamed from: a */
        public final void m957a(ClientException clientException) {
            AAILogger.debug(C2601c.this.f963a, "handle on error.");
            if (C2601c.this.f974l != null) {
                C2601c.this.f974l.onError(clientException);
            }
        }

        /* JADX INFO: renamed from: b */
        public final int m958b(short[] sArr, int i) {
            long j = 0;
            for (short s : sArr) {
                j += (long) (s * s);
            }
            double dLog10 = Math.log10(j / ((double) i)) * 10.0d;
            return (int) (dLog10 <= 40.0d ? 0.0d : dLog10 - 40.0d);
        }

        /* JADX INFO: renamed from: b */
        public final void m959b() {
            AAILogger.info(C2601c.this.f963a, "handle on recording");
            if (C2601c.this.f974l != null) {
                C2601c.this.f974l.onStart();
            }
        }

        /* JADX INFO: renamed from: c */
        public final void m960c(short[] sArr, int i) {
            if (C2601c.this.f974l != null) {
                C2601c.this.f974l.audioDatas(sArr, i);
            }
        }

        /* JADX INFO: renamed from: c */
        public boolean m961c() {
            try {
                C2601c.this.f973k.start();
                m959b();
                AAILogger.info(C2601c.this.f963a, "AudioRecord start success.");
                return true;
            } catch (ClientException e) {
                e.printStackTrace();
                m957a(new ClientException(e.getCode(), e.getMessage()));
                AAILogger.info(C2601c.this.f963a, "AudioRecord start failed.");
                return false;
            } catch (Exception e2) {
                AAILogger.info(C2601c.this.f963a, "AudioRecord start failed.->" + e2.toString());
                e2.printStackTrace();
                return false;
            }
        }

        /* JADX INFO: renamed from: d */
        public void m962d() {
            this.f983a = true;
            AAILogger.info(C2601c.this.f963a, "AaiAudioRecord runnable is ready to stop.");
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            String str2;
            Process.setThreadPriority(-19);
            int i = C2601c.this.f970h;
            short[] sArr = new short[i];
            if (m961c()) {
                int i2 = C2601c.this.f970h;
                short[] sArr2 = new short[i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    sArr2[i3] = i3 % 2 == 0 ? (short) 5 : (short) -5;
                }
                int iMax = Math.max(C2601c.this.f966d - C2601c.this.f970h, 0);
                while (!this.f983a) {
                    int i4 = C2601c.this.f973k.read(sArr, i);
                    AAILogger.info(C2601c.this.f963a, "pcmAudioDataSource read Length = " + i4);
                    if (C2601c.this.f975m) {
                        m960c(Arrays.copyOf(sArr, i), i4);
                    }
                    if (i4 <= -1) {
                        this.f983a = true;
                    } else if (i4 == 0) {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        AAILogger.debug(C2601c.this.f963a, "read audio data size = " + i4);
                        AAILogger.debug(C2601c.this.f963a, "volumeCallbackControl = " + iMax + "minVolumeCallbackTimeInShort = " + C2601c.this.f966d);
                        iMax += i4;
                        if (iMax >= C2601c.this.f966d) {
                            m956a(m958b(sArr, i4));
                            m955a(m953a(sArr, i4));
                            iMax -= C2601c.this.f966d;
                        }
                        if (!this.f983a && C2601c.this.f971i != null) {
                            C2601c.this.f971i.onSliceComplete(new AudioPcmData(Arrays.copyOf(sArr, i4)));
                        }
                    }
                }
                try {
                    C2601c.this.f973k.stop();
                } catch (IllegalStateException e2) {
                    AAILogger.warn(C2601c.this.f963a, "pcmAudioDataSource Exception" + e2.toString());
                    e2.printStackTrace();
                }
                m954a();
                str = C2601c.this.f963a;
                str2 = "AaiAudioRecord runnable is finished.";
            } else {
                str = C2601c.this.f963a;
                str2 = "audio record thread init or start failed..";
            }
            AAILogger.info(str, str2);
        }
    }

    public C2601c(int i, int i2, boolean z, int i3, int i4, PcmAudioDataSource pcmAudioDataSource, boolean z2) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.US);
        this.f964b = decimalFormatSymbols;
        this.f965c = new DecimalFormat("#.##", decimalFormatSymbols);
        this.f969g = true;
        this.f967e = i;
        this.f966d = i2 * (i3 / 1000);
        this.f968f = z;
        this.f969g = z2;
        this.f970h = (i3 * i4) / 1000;
        this.f975m = pcmAudioDataSource.isSetSaveAudioRecordFiles();
        this.f973k = pcmAudioDataSource;
        this.f972j = new c();
    }

    /* JADX INFO: renamed from: a */
    public int m939a() {
        return this.f967e;
    }

    /* JADX INFO: renamed from: a */
    public void m940a(AudioRecognizeBufferListener audioRecognizeBufferListener) {
        this.f971i = audioRecognizeBufferListener;
    }

    /* JADX INFO: renamed from: a */
    public void m941a(AudioRecognizerListener audioRecognizerListener) {
        this.f974l = audioRecognizerListener;
    }

    /* JADX INFO: renamed from: b */
    public boolean m942b() {
        return this.f968f;
    }

    /* JADX INFO: renamed from: c */
    public boolean m943c() {
        return this.f969g;
    }

    /* JADX INFO: renamed from: d */
    public void m944d() throws ClientException {
        AAILogger.info(this.f963a, "AaiAudioRecord is starting.");
        if (this.f973k == null) {
            AAILogger.info(this.f963a, "Audio source data is null");
            throw new ClientException(ClientExceptionType.AUDIO_SOURCE_DATA_NULL);
        }
        try {
            new Thread(this.f972j).start();
            AAILogger.info(this.f963a, "AaiAudioRecord runnable is starting.");
        } catch (IllegalStateException unused) {
            AAILogger.info(this.f963a, "AaiAudioRecord start failed.");
            this.f972j = null;
            throw new ClientException(ClientExceptionType.AUDIO_RECOGNIZE_THREAD_START_FAILED);
        }
    }

    /* JADX INFO: renamed from: e */
    public void m945e() {
        if (this.f972j == null) {
            AAILogger.info(this.f963a, "stop failed : recording thread is not exit.");
        } else {
            AAILogger.info(this.f963a, "AaiAudioRecord is ready to stop.");
            this.f972j.m962d();
        }
    }
}
