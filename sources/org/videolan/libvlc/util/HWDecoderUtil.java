package org.videolan.libvlc.util;

import com.github.houbb.heaven.constant.MethodConst;
import java.util.HashMap;

/* JADX INFO: loaded from: classes5.dex */
public class HWDecoderUtil {
    private static final DecoderBySOC[] sBlacklistedDecoderBySOCList = {new DecoderBySOC("ro.product.board", "MSM8225", Decoder.NONE), new DecoderBySOC("ro.product.board", "hawaii", Decoder.NONE)};
    private static final DecoderBySOC[] sDecoderBySOCList = {new DecoderBySOC("ro.product.brand", "SEMC", Decoder.NONE), new DecoderBySOC("ro.board.platform", "msm7627", Decoder.NONE), new DecoderBySOC("ro.product.brand", "Amazon", Decoder.MEDIACODEC), new DecoderBySOC("ro.board.platform", "omap3", Decoder.OMX), new DecoderBySOC("ro.board.platform", "rockchip", Decoder.OMX), new DecoderBySOC("ro.board.platform", "rk29", Decoder.OMX), new DecoderBySOC("ro.board.platform", "msm7630", Decoder.OMX), new DecoderBySOC("ro.board.platform", "s5pc", Decoder.OMX), new DecoderBySOC("ro.board.platform", "montblanc", Decoder.OMX), new DecoderBySOC("ro.board.platform", "exdroid", Decoder.OMX), new DecoderBySOC("ro.board.platform", "sun6i", Decoder.OMX), new DecoderBySOC("ro.board.platform", "exynos4", Decoder.MEDIACODEC), new DecoderBySOC("ro.board.platform", "omap4", Decoder.ALL), new DecoderBySOC("ro.board.platform", "tegra", Decoder.ALL), new DecoderBySOC("ro.board.platform", "tegra3", Decoder.ALL), new DecoderBySOC("ro.board.platform", "msm8660", Decoder.ALL), new DecoderBySOC("ro.board.platform", "exynos5", Decoder.ALL), new DecoderBySOC("ro.board.platform", "rk30", Decoder.ALL), new DecoderBySOC("ro.board.platform", "rk31", Decoder.ALL), new DecoderBySOC("ro.board.platform", "mv88de3100", Decoder.ALL), new DecoderBySOC("ro.hardware", "mt83", Decoder.ALL)};
    private static final AudioOutputBySOC[] sAudioOutputBySOCList = {new AudioOutputBySOC("ro.product.brand", "Amazon", AudioOutput.OPENSLES), new AudioOutputBySOC("ro.product.manufacturer", "Amazon", AudioOutput.OPENSLES)};
    private static final HashMap<String, String> sSystemPropertyMap = new HashMap<>();

    public enum AudioOutput {
        OPENSLES,
        AUDIOTRACK,
        ALL
    }

    public enum Decoder {
        UNKNOWN,
        NONE,
        OMX,
        MEDIACODEC,
        ALL
    }

    private static class DecoderBySOC {
        public final Decoder dec;
        public final String key;
        public final String value;

        public DecoderBySOC(String str, String str2, Decoder decoder) {
            this.key = str;
            this.value = str2;
            this.dec = decoder;
        }
    }

    private static class AudioOutputBySOC {
        public final AudioOutput aout;
        public final String key;
        public final String value;

        public AudioOutputBySOC(String str, String str2, AudioOutput audioOutput) {
            this.key = str;
            this.value = str2;
            this.aout = audioOutput;
        }
    }

    public static Decoder getDecoderFromDevice() {
        for (DecoderBySOC decoderBySOC : sBlacklistedDecoderBySOCList) {
            String systemPropertyCached = getSystemPropertyCached(decoderBySOC.key);
            if (systemPropertyCached != null && systemPropertyCached.contains(decoderBySOC.value)) {
                return decoderBySOC.dec;
            }
        }
        if (AndroidUtil.isJellyBeanMR2OrLater) {
            return Decoder.ALL;
        }
        for (DecoderBySOC decoderBySOC2 : sDecoderBySOCList) {
            String systemPropertyCached2 = getSystemPropertyCached(decoderBySOC2.key);
            if (systemPropertyCached2 != null && systemPropertyCached2.contains(decoderBySOC2.value)) {
                return decoderBySOC2.dec;
            }
        }
        return Decoder.UNKNOWN;
    }

    public static AudioOutput getAudioOutputFromDevice() {
        for (AudioOutputBySOC audioOutputBySOC : sAudioOutputBySOCList) {
            String systemPropertyCached = getSystemPropertyCached(audioOutputBySOC.key);
            if (systemPropertyCached != null && systemPropertyCached.contains(audioOutputBySOC.value)) {
                return audioOutputBySOC.aout;
            }
        }
        return AudioOutput.ALL;
    }

    private static String getSystemPropertyCached(String str) {
        HashMap<String, String> map = sSystemPropertyMap;
        String str2 = map.get(str);
        if (str2 != null) {
            return str2;
        }
        String systemProperty = getSystemProperty(str, "none");
        map.put(str, systemProperty);
        return systemProperty;
    }

    private static String getSystemProperty(String str, String str2) {
        try {
            Class<?> clsLoadClass = ClassLoader.getSystemClassLoader().loadClass("android.os.SystemProperties");
            return (String) clsLoadClass.getMethod(MethodConst.GET_PREFIX, String.class, String.class).invoke(clsLoadClass, str, str2);
        } catch (Exception unused) {
            return str2;
        }
    }
}
