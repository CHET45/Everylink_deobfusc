package com.aivox.libOpus.presenter;

import android.util.Log;
import com.aivox.base.common.Constant;
import com.aivox.libOpus.presenter.DecodeOpusPresenter;
import com.aivox.libOpus.utils.OpusUtils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DecodeOpusPresenter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\bf\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u001a\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0003H\u0016J\b\u0010\u000f\u001a\u00020\u0007H\u0016J\u0012\u0010\u0010\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\fH\u0002J\u001a\u0010\u0012\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\u0014"}, m1901d2 = {"Lcom/aivox/libOpus/presenter/DecodeOpusPresenter;", "", "byteArrayToShortArray", "", "byteArray", "", "cancelDecode", "", "decodeOpusFile", "path", "", "newThreadRun", "", "opusDecode", "formatShortArray", "opusDecodeFinish", "opusFileDecoder", "needDecoder", "readFile", "Companion", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public interface DecodeOpusPresenter {
    public static final int BUFFER_LENGTH = 80;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final int DEFAULT_AUDIO_SAMPLE_RATE = 16000;
    public static final int DEFAULT_OPUS_CHANNEL = 1;

    short[] byteArrayToShortArray(byte[] byteArray);

    void cancelDecode();

    void decodeOpusFile(String path, boolean newThreadRun);

    void opusDecode(short[] formatShortArray);

    void opusDecodeFinish();

    void readFile(String path, boolean newThreadRun);

    /* JADX INFO: compiled from: DecodeOpusPresenter.kt */
    @Metadata(m1900d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, m1901d2 = {"Lcom/aivox/libOpus/presenter/DecodeOpusPresenter$Companion;", "", "()V", "BUFFER_LENGTH", "", "DEFAULT_AUDIO_SAMPLE_RATE", "DEFAULT_OPUS_CHANNEL", "TAG", "", "getTAG", "()Ljava/lang/String;", "decodeOpusFilePath", "isCancel", "", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int BUFFER_LENGTH = 80;
        public static final int DEFAULT_AUDIO_SAMPLE_RATE = 16000;
        public static final int DEFAULT_OPUS_CHANNEL = 1;
        private static String decodeOpusFilePath;
        private static boolean isCancel;

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getTAG() {
            Intrinsics.checkNotNullExpressionValue("DecodeOpusPresenter", "getSimpleName(...)");
            return "DecodeOpusPresenter";
        }
    }

    /* JADX INFO: compiled from: DecodeOpusPresenter.kt */
    @Metadata(m1902k = 3, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class DefaultImpls {
        public static void opusDecode(DecodeOpusPresenter decodeOpusPresenter, short[] formatShortArray) {
            Intrinsics.checkNotNullParameter(formatShortArray, "formatShortArray");
        }

        public static void opusDecodeFinish(DecodeOpusPresenter decodeOpusPresenter) {
        }

        public static /* synthetic */ void decodeOpusFile$default(DecodeOpusPresenter decodeOpusPresenter, String str, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeOpusFile");
            }
            if ((i & 2) != 0) {
                z = true;
            }
            decodeOpusPresenter.decodeOpusFile(str, z);
        }

        public static void decodeOpusFile(final DecodeOpusPresenter decodeOpusPresenter, String path, boolean z) throws IOException {
            Intrinsics.checkNotNullParameter(path, "path");
            Log.i(DecodeOpusPresenter.INSTANCE.getTAG(), "decodeOpusFile: " + path + ", newThreadRun: " + z);
            Companion companion = DecodeOpusPresenter.INSTANCE;
            Companion.decodeOpusFilePath = path;
            Companion companion2 = DecodeOpusPresenter.INSTANCE;
            Companion.isCancel = false;
            if (!z) {
                opusFileDecoder$default(decodeOpusPresenter, false, 1, null);
            } else {
                new Thread(new Runnable() { // from class: com.aivox.libOpus.presenter.DecodeOpusPresenter$DefaultImpls$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        DecodeOpusPresenter.DefaultImpls.decodeOpusFile$lambda$0(decodeOpusPresenter);
                    }
                }).start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void decodeOpusFile$lambda$0(DecodeOpusPresenter this$0) throws IOException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            opusFileDecoder$default(this$0, false, 1, null);
        }

        public static /* synthetic */ void readFile$default(DecodeOpusPresenter decodeOpusPresenter, String str, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: readFile");
            }
            if ((i & 2) != 0) {
                z = true;
            }
            decodeOpusPresenter.readFile(str, z);
        }

        public static void readFile(final DecodeOpusPresenter decodeOpusPresenter, String path, boolean z) throws IOException {
            Intrinsics.checkNotNullParameter(path, "path");
            Log.i(DecodeOpusPresenter.INSTANCE.getTAG(), "decodeOpusFile: " + path + ", newThreadRun: " + z);
            Companion companion = DecodeOpusPresenter.INSTANCE;
            Companion.decodeOpusFilePath = path;
            Companion companion2 = DecodeOpusPresenter.INSTANCE;
            Companion.isCancel = false;
            if (!z) {
                opusFileDecoder(decodeOpusPresenter, false);
            } else {
                new Thread(new Runnable() { // from class: com.aivox.libOpus.presenter.DecodeOpusPresenter$DefaultImpls$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        DecodeOpusPresenter.DefaultImpls.readFile$lambda$1(decodeOpusPresenter);
                    }
                }).start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void readFile$lambda$1(DecodeOpusPresenter this$0) throws IOException {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            opusFileDecoder(this$0, false);
        }

        public static void cancelDecode(DecodeOpusPresenter decodeOpusPresenter) {
            Companion companion = DecodeOpusPresenter.INSTANCE;
            Companion.isCancel = true;
        }

        public static /* synthetic */ void opusFileDecoder$default(DecodeOpusPresenter decodeOpusPresenter, boolean z, int i, Object obj) throws IOException {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: opusFileDecoder");
            }
            if ((i & 1) != 0) {
                z = true;
            }
            opusFileDecoder(decodeOpusPresenter, z);
        }

        private static void opusFileDecoder(DecodeOpusPresenter decodeOpusPresenter, boolean z) throws IOException {
            int i;
            String str = Companion.decodeOpusFilePath;
            if (str == null || str.length() == 0) {
                decodeOpusPresenter.opusDecodeFinish();
                return;
            }
            OpusUtils instant = OpusUtils.INSTANCE.getInstant();
            long jCreateDecoder = instant.createDecoder(16000, 1);
            try {
                FileInputStream fileInputStream = new FileInputStream(Companion.decodeOpusFilePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                while (true) {
                    if (Companion.isCancel) {
                        break;
                    }
                    byte[] bArr = new byte[80];
                    try {
                        i = bufferedInputStream.read(bArr, 0, 80);
                    } catch (Exception unused) {
                        i = -1;
                    }
                    if (i < 0) {
                        Log.i(DecodeOpusPresenter.INSTANCE.getTAG(), "OpusFileDecoder compare");
                        break;
                    }
                    if (z) {
                        short[] sArr = new short[Constant.EVENT.BLE_SHOW_CONNECT_DIALOG];
                        int iDecode = instant.decode(jCreateDecoder, bArr, sArr);
                        if (iDecode <= 0) {
                            Log.e(DecodeOpusPresenter.INSTANCE.getTAG(), "opusDecode error : " + iDecode);
                            break;
                        } else {
                            short[] sArr2 = new short[iDecode];
                            System.arraycopy(sArr, 0, sArr2, 0, iDecode);
                            decodeOpusPresenter.opusDecode(sArr2);
                        }
                    } else {
                        decodeOpusPresenter.opusDecode(decodeOpusPresenter.byteArrayToShortArray(bArr));
                    }
                }
                instant.destroyDecoder(jCreateDecoder);
                bufferedInputStream.close();
                fileInputStream.close();
                decodeOpusPresenter.opusDecodeFinish();
            } catch (Exception unused2) {
                decodeOpusPresenter.opusDecodeFinish();
            }
        }

        public static short[] byteArrayToShortArray(DecodeOpusPresenter decodeOpusPresenter, byte[] byteArray) {
            Intrinsics.checkNotNullParameter(byteArray, "byteArray");
            short[] sArr = new short[byteArray.length / 2];
            ByteBuffer.wrap(byteArray).order(ByteOrder.nativeOrder()).asShortBuffer().get(sArr);
            return sArr;
        }
    }
}
