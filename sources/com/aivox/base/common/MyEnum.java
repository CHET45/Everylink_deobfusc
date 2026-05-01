package com.aivox.base.common;

import androidx.exifinterface.media.ExifInterface;
import com.aivox.base.C0874R;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class MyEnum {

    public enum AUDIO_PLAY_STATUS {
        IDLE(0, "未播放或已结束"),
        PLAYING(1, "播放中"),
        PAUSED(2, "暂停中");

        public final Integer code;
        public final String msg;

        AUDIO_PLAY_STATUS(Integer num, String str) {
            this.code = num;
            this.msg = str;
        }
    }

    public enum TRANS_TYPE {
        TENCENT(1),
        NULL(0);

        public final int type;

        TRANS_TYPE(int i) {
            this.type = i;
        }
    }

    public enum TRANSLATE_LANGUAGE {
        NONE(-1, "none", "none", "未设置", "XX", C0874R.string.lang_res_none, "Press and hold the mic icon to speak"),
        ZH(0, "zh", "zh", "简体中文", "简", C0874R.string.lang_res_zhs, "按住麦克风开始讲话。"),
        EN(1, "en", "en", "英语", "EN", C0874R.string.lang_res_en, "Press and hold the mic icon to speak"),
        EN_LOCAL(11101, "en", "en", "英语", "EN", C0874R.string.lang_res_en, "Press and hold the mic icon to speak"),
        JA(2, "ja", "jp", "日语", "JA", C0874R.string.lang_res_jp, "マイクアイコンを別途押し続けて話してください。"),
        KO(3, "ko", "ko", "韩语", "KO", C0874R.string.lang_res_kr, "마이크 아이콘을 별도로 누른 채로 말해주세요."),
        AR(4, "ar", "ar", "阿拉伯语", "AR", C0874R.string.lang_res_ar, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        ZH_T(6, "cht", "cn", "繁體中文", "繁", C0874R.string.lang_res_zht, "按著麥克風開始說話。"),
        AR_01(7, "ar", "ar", "阿拉伯语（阿拉伯联合大公国）", "AR", C0874R.string.lang_res_ar_uae, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_02(8, "ar", "ar", "阿拉伯语（巴林）", "AR", C0874R.string.lang_res_ar_bahrain, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_03(9, "ar", "ar", "阿拉伯语（阿尔及利亚）", "AR", C0874R.string.lang_res_ar_algeria, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_04(10, "ar", "ar", "阿拉伯语（埃及）", "AR", C0874R.string.lang_res_ar_egypt, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_05(11, "ar", "ar", "阿拉伯语（以色列）", "AR", C0874R.string.lang_res_ar_israel, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_06(12, "ar", "ar", "阿拉伯语（伊拉克）", "AR", C0874R.string.lang_res_ar_iraq, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_07(13, "ar", "ar", "阿拉伯语（约旦）", "AR", C0874R.string.lang_res_ar_jordan, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_08(14, "ar", "ar", "阿拉伯语（科威特）", "AR", C0874R.string.lang_res_ar_kuwait, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_09(15, "ar", "ar", "阿拉伯语（黎巴嫩）", "AR", C0874R.string.lang_res_ar_lebanon, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_10(16, "ar", "ar", "阿拉伯语（利比亚）", "AR", C0874R.string.lang_res_ar_libya, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_11(17, "ar", "ar", "阿拉伯语（摩洛哥）", "AR", C0874R.string.lang_res_ar_morocco, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_12(18, "ar", "ar", "阿拉伯语（阿曼）", "AR", C0874R.string.lang_res_ar_oman, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_13(19, "ar", "ar", "阿拉伯语（巴勒斯坦民族权利机构）", "AR", C0874R.string.lang_res_ar_palestine, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_14(20, "ar", "ar", "阿拉伯语（卡达）", "AR", C0874R.string.lang_res_ar_qatar, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_15(21, "ar", "ar", "阿拉伯语（沙岛地阿拉伯）", "AR", C0874R.string.lang_res_ar_saudi_arabia, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_16(22, "ar", "ar", "阿拉伯语（叙利亚）", "AR", C0874R.string.lang_res_ar_syria, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_17(23, "ar", "ar", "阿拉伯语（突尼斯）", "AR", C0874R.string.lang_res_ar_tunisia, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        AR_18(24, "ar", "ar", "阿拉伯语（也门）", "AR", C0874R.string.lang_res_ar_yemen, "اضغط واستمر في الضغط على أيقونة الميكروفون بشكل منفصل للتحدث."),
        DE(25, "de", "de", "德语（德国）", "DE", C0874R.string.lang_res_de_germany, "Halten Sie das Mikrofon gedrückt und sprechen Sie."),
        DE_01(26, "de", "de", "德语（奥地利）", "DE", C0874R.string.lang_res_de_austria, "Halten Sie das Mikrofon gedrückt und sprechen Sie."),
        DE_02(27, "de", "de", "德语（瑞士）", "DE", C0874R.string.lang_res_de_switzerland, "Halten Sie das Mikrofon gedrückt und sprechen Sie."),
        EN_11(28, "en", "en", "英语（澳洲）", "EN", C0874R.string.lang_res_en_australia, "Press and hold the mic icon to speak"),
        EN_02(29, "en", "en", "英语（加拿大）", "EN", C0874R.string.lang_res_en_canada, "Press and hold the mic icon to speak"),
        EN_03(30, "en", "en", "英语（英国）", "EN", C0874R.string.lang_res_en_uk, "Press and hold the mic icon to speak"),
        EN_04(31, "en", "en", "英语（加纳）", "EN", C0874R.string.lang_res_en_ghana, "Press and hold the mic icon to speak"),
        EN_05(32, "en", "en", "英语（肯亚）", "EN", C0874R.string.lang_res_en_kenya, "Press and hold the mic icon to speak"),
        EN_06(33, "en", "en", "英语（奈及利亚）", "EN", C0874R.string.lang_res_en_nigeria, "Press and hold the mic icon to speak"),
        EN_07(34, "en", "en", "英语（纽西兰）", "EN", C0874R.string.lang_res_en_new_zealand, "Press and hold the mic icon to speak"),
        EN_08(35, "en", "en", "英语（菲律宾）", "EN", C0874R.string.lang_res_en_philippines, "Press and hold the mic icon to speak"),
        EN_09(36, "en", "en", "英语（新加坡）", "EN", C0874R.string.lang_res_en_singapore, "Press and hold the mic icon to speak"),
        EN_10(37, "en", "en", "英语（坦桑尼亚）", "EN", C0874R.string.lang_res_en_tanzania, "Press and hold the mic icon to speak"),
        EN_01(38, "en", "en", "英语（美国）", "EN", C0874R.string.lang_res_en_us, "Press and hold the mic icon to speak"),
        EN_12(39, "en", "en", "英语（南非）", "EN", C0874R.string.lang_res_en_south_africa, "Press and hold the mic icon to speak"),
        EN_13(40, "en", "en", "英语（香港特别行政区）", "EN", C0874R.string.lang_res_en_hong_kong, "Press and hold the mic icon to speak"),
        EN_14(41, "en", "en", "英语（爱尔兰）", "EN", C0874R.string.lang_res_en_ireland, "Press and hold the mic icon to speak"),
        EN_15(42, "en", "en", "英语（印度）", "EN", C0874R.string.lang_res_en_india, "Press and hold the mic icon to speak"),
        ES_01(43, "es", "es", "西班牙语（阿根廷）", "ES", C0874R.string.lang_res_es_argentina, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_02(44, "es", "es", "西班牙语（玻利维亚）", "ES", C0874R.string.lang_res_es_bolivia, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_03(45, "es", "es", "西班牙语（智利）", "ES", C0874R.string.lang_res_es_chile, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_04(46, "es", "es", "西班牙语（哥伦比亚）", "ES", C0874R.string.lang_res_es_colombia, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_05(47, "es", "es", "西班牙语（哥斯达黎加）", "ES", C0874R.string.lang_res_es_costa_rica, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_06(48, "es", "es", "西班牙语（古巴）", "ES", C0874R.string.lang_res_es_cuba, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_07(49, "es", "es", "西班牙语（多明尼加共和国）", "ES", C0874R.string.lang_res_es_dominican_rep, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_08(50, "es", "es", "西班牙语（西班牙）", "ES", C0874R.string.lang_res_es_spain, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_09(51, "es", "es", "西班牙语（赤道几内亚）", "ES", C0874R.string.lang_res_es_eq_guinea, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_10(52, "es", "es", "西班牙语（瓜地马拉）", "ES", C0874R.string.lang_res_es_guatemala, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_11(53, "es", "es", "西班牙语（宏都拉斯）", "ES", C0874R.string.lang_res_es_honduras, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_12(54, "es", "es", "西班牙语（墨西哥）", "ES", C0874R.string.lang_res_es_mexico, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_13(55, "es", "es", "西班牙语（尼加拉瓜）", "ES", C0874R.string.lang_res_es_nicaragua, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_14(56, "es", "es", "西班牙语（巴拿马）", "ES", C0874R.string.lang_res_es_panama, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_15(57, "es", "es", "西班牙语（秘鲁）", "ES", C0874R.string.lang_res_es_peru, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_16(58, "es", "es", "西班牙语（波多黎各）", "ES", C0874R.string.lang_res_es_puerto_rico, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_17(59, "es", "es", "西班牙语（巴拉圭）", "ES", C0874R.string.lang_res_es_paraguay, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_18(60, "es", "es", "西班牙语（美国）", "ES", C0874R.string.lang_res_es_us, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_19(61, "es", "es", "西班牙语（乌拉圭）", "ES", C0874R.string.lang_res_es_uruguay, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        ES_20(62, "es", "es", "西班牙语（委内瑞拉）", "ES", C0874R.string.lang_res_es_venezuela, "Presione y mantenga presionado el icono del micrófono por separado para hablar."),
        VI(63, "vi", "vi", "越南语", "VI", C0874R.string.lang_res_vi, "Nhấn và giữ biểu tượng micro riêng để nói."),
        FR_01(64, "fr", "fr", "法语（比利时）", "FR", C0874R.string.lang_res_fr_belgium, "Maintenez le microphone enfoncé pour commencer à parler."),
        FR_02(65, "fr", "fr", "法语（加拿大）", "FR", C0874R.string.lang_res_fr_canada, "Maintenez le microphone enfoncé pour commencer à parler."),
        FR_03(66, "fr", "fr", "法语（瑞士）", "FR", C0874R.string.lang_res_fr_switzerland, "Maintenez le microphone enfoncé pour commencer à parler."),
        FR_04(67, "fr", "fr", "法语（法国）", "FR", C0874R.string.lang_res_fr_france, "Maintenez le microphone enfoncé pour commencer à parler."),
        TH(68, "th", "th", "泰语", "TH", C0874R.string.lang_res_th, "กดและถือไอคอนไมโครโฟนค้างไว้เพื่อพูด"),
        PT(69, "pt", "pt", "葡萄牙语", "PT", C0874R.string.lang_res_pt, "Pressione e segure o ícone do microfone separadamente para falar."),
        TR(70, "tr", "tr", "土耳其语", "TR", C0874R.string.lang_res_tr, "Mikrofon simgesine ayrı ayrı basılı tutun ve konuşun."),
        YUE(71, "yue", "yue", "粤语", "YU", C0874R.string.lang_res_yue, "握住麥克風開始講話。"),
        YUE_HK(72, "yue", "yue", "粤语", "YU", C0874R.string.lang_res_yue_hk, "握住麥克風開始講話。"),
        ID(73, "id", "id", "印尼语", "ID", C0874R.string.lang_res_id, "Tekan dan tahan ikon mikrofon secara terpisah untuk berbicara."),
        FIL(74, "fil", "fil", "菲律宾语", "FIL", C0874R.string.lang_res_fil, "Tumigil at i-hold ang ikono ng mikropono sa iisang pagkakataon upang mag-usap."),
        MS(75, "ms", "ms", "马来西亚语", "MS", C0874R.string.lang_res_ms, "Tekan mikrofon untuk mula bercakap.");

        public final String alias;
        public final String desc;
        public final String display;
        public final String name;
        public final String notice;
        public final int textRes;
        public final int type;

        TRANSLATE_LANGUAGE(int i, String str, String str2, String str3, String str4, int i2, String str5) {
            this.type = i;
            this.name = str;
            this.alias = str2;
            this.desc = str3;
            this.display = str4;
            this.textRes = i2;
            this.notice = str5;
        }

        public static TRANSLATE_LANGUAGE getLanguage(int i) {
            for (TRANSLATE_LANGUAGE translate_language : values()) {
                if (translate_language.type == i) {
                    return translate_language;
                }
            }
            return NONE;
        }

        public static String getNotice(int i) {
            for (TRANSLATE_LANGUAGE translate_language : values()) {
                if (translate_language.type == i) {
                    return translate_language.notice;
                }
            }
            return NONE.notice;
        }
    }

    public enum DEVICE_MODEL {
        WATER3F_AI(0, "Water3F AI", C0874R.drawable.pic_device_water_3f_ai, C0874R.drawable.pic_device_water_3f_ai_dot, true),
        AIDIO_X1(1, "» aidio X1 «", C0874R.drawable.pic_device_aidio_x1, C0874R.drawable.pic_device_aidio_x1_dot, true),
        TRANSLATER_T3(2, "Translater T3", C0874R.drawable.pic_device_translater_t3, C0874R.drawable.pic_device_translater_t3_dot, false),
        HAPPYLEMON_HL3(3, "Happylemon HL3", C0874R.drawable.pic_device_happy_lemon_hl3, C0874R.drawable.pic_device_happy_lemon_hl3_dot, false),
        GK111_PRO(4, "GK111 PRO", C0874R.drawable.pic_device_gk111_pro, C0874R.drawable.pic_device_gk111_pro, true),
        AIVOX_S58(5, "aivox S58", C0874R.drawable.pic_device_aivox_s58, C0874R.drawable.pic_device_aivox_s58, true),
        MARVO_AI_FUTURE(6, "marvo AI Future", C0874R.drawable.pic_device_marvo_ai_future, C0874R.drawable.pic_device_marvo_ai_future_dot, true),
        AIVOX_R7(7, "aivox R7", C0874R.drawable.pic_device_water_3f_ai, C0874R.drawable.pic_device_water_3f_ai_dot, true),
        OWS_YT09(8, "OWS YT09", C0874R.drawable.pic_device_yt09_ows, C0874R.drawable.pic_device_yt09_ows_dot, false),
        DIGI_GIFT_EAR(9, "DIGI GIFT EAR", C0874R.drawable.pic_device_water_3f_ai, C0874R.drawable.pic_device_water_3f_ai_dot, false),
        DIGI_GIFT_BOX(10, "DIGI GIFT BOX", C0874R.drawable.pic_device_digi_gift_box, C0874R.drawable.pic_device_digi_gift_box, false),
        AI_GLASSES_PAI_08(11, "AI Glasses PAI-08", C0874R.drawable.pic_device_echo_eye, C0874R.drawable.pic_device_echo_eye, false),
        HY_15(12, "HY-15", C0874R.drawable.pic_device_echo_eye, C0874R.drawable.pic_device_echo_eye, false),
        HY_16(13, "LensBot HY-16", C0874R.drawable.pic_device_echo_eye, C0874R.drawable.pic_device_echo_eye, false);

        public final String name;
        public final int picDotRes;
        public final int picRes;
        public final boolean ttsEnable;
        public final int type;

        DEVICE_MODEL(int i, String str, int i2, int i3, boolean z) {
            this.type = i;
            this.name = str;
            this.picRes = i2;
            this.picDotRes = i3;
            this.ttsEnable = z;
        }

        public static DEVICE_MODEL getEarphone(String str) {
            for (DEVICE_MODEL device_model : values()) {
                if (device_model.name.equals(str)) {
                    return device_model;
                }
            }
            return WATER3F_AI;
        }

        public static boolean isJieLiDevice(String str) {
            int i = C08751.$SwitchMap$com$aivox$base$common$MyEnum$DEVICE_MODEL[getEarphone(str).ordinal()];
            return i == 1 || i == 2 || i == 3;
        }

        public static int getDeviceIcon(String str) {
            for (DEVICE_MODEL device_model : values()) {
                if (device_model.name.equals(str)) {
                    return device_model.picRes;
                }
            }
            return WATER3F_AI.picRes;
        }

        public static int getDeviceDotIcon(String str) {
            for (DEVICE_MODEL device_model : values()) {
                if (device_model.name.equals(str)) {
                    return device_model.picDotRes;
                }
            }
            return WATER3F_AI.picDotRes;
        }

        public static String getDeviceName(int i) {
            for (DEVICE_MODEL device_model : values()) {
                if (device_model.type == i) {
                    return device_model.name;
                }
            }
            return WATER3F_AI.name;
        }

        public static boolean getTtsEnable(String str) {
            for (DEVICE_MODEL device_model : values()) {
                if (Objects.equals(device_model.name, str)) {
                    return device_model.ttsEnable;
                }
            }
            return false;
        }
    }

    /* JADX INFO: renamed from: com.aivox.base.common.MyEnum$1 */
    static /* synthetic */ class C08751 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$base$common$MyEnum$DEVICE_MODEL;

        static {
            int[] iArr = new int[DEVICE_MODEL.values().length];
            $SwitchMap$com$aivox$base$common$MyEnum$DEVICE_MODEL = iArr;
            try {
                iArr[DEVICE_MODEL.OWS_YT09.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$base$common$MyEnum$DEVICE_MODEL[DEVICE_MODEL.DIGI_GIFT_EAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aivox$base$common$MyEnum$DEVICE_MODEL[DEVICE_MODEL.DIGI_GIFT_BOX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum BLE_DEVICE_MODEL {
        OWS_YT09(0, "OWS YT09", "YT9XLSWE"),
        DIGI_GIFT_EAR(1, "DIGI GIFT EAR", "XXXXXXXX"),
        DIGI_GIFT_BOX(2, "DIGI GIFT BOX", "S59XLSWE"),
        AI_GLASSES_PAI_08(3, "AI Glasses PAI-08", "HSC"),
        HY_15(4, "HY-15", "HSC"),
        HY_16(5, "LensBot HY-16", "HSC");

        public final String filterName;
        public final String name;
        public final int type;

        BLE_DEVICE_MODEL(int i, String str, String str2) {
            this.name = str;
            this.type = i;
            this.filterName = str2;
        }

        public static BLE_DEVICE_MODEL getEarphone(String str) {
            for (BLE_DEVICE_MODEL ble_device_model : values()) {
                if (ble_device_model.name.equals(str)) {
                    return ble_device_model;
                }
            }
            return OWS_YT09;
        }
    }

    public enum DEVICE_VOICE_MODE {
        NORMAL(0, 18, "普通"),
        DENOISE(1, 19, "降噪"),
        AMBIENT(2, 20, "环境");

        public final Integer code;
        public final Integer index;
        public final String msg;

        DEVICE_VOICE_MODE(Integer num, Integer num2, String str) {
            this.index = num;
            this.code = num2;
            this.msg = str;
        }

        public static DEVICE_VOICE_MODE getVoiceMode(int i, boolean z) {
            for (DEVICE_VOICE_MODE device_voice_mode : values()) {
                if (z && device_voice_mode.code.intValue() == i) {
                    return device_voice_mode;
                }
                if (!z && device_voice_mode.index.intValue() == i) {
                    return device_voice_mode;
                }
            }
            return NORMAL;
        }
    }

    public enum DEVICE_EFFECT {
        DYNAMIC(0, 16, "动态", C0874R.string.eq_mode_dynamic),
        VOCAL(1, 17, "人声", C0874R.string.eq_mode_vocal),
        OFF(2, 13, "关闭", C0874R.string.eq_mode_off),
        BASS(3, 14, "低音", C0874R.string.eq_mode_bass),
        CLEAR(4, 15, "清晰", C0874R.string.eq_mode_clear);

        public final Integer code;
        public final Integer index;
        public final String msg;
        public final int msgRes;

        DEVICE_EFFECT(Integer num, Integer num2, String str, int i) {
            this.index = num;
            this.code = num2;
            this.msg = str;
            this.msgRes = i;
        }

        public static DEVICE_EFFECT getEffect(int i, boolean z) {
            for (DEVICE_EFFECT device_effect : values()) {
                if (z && device_effect.code.intValue() == i) {
                    return device_effect;
                }
                if (!z && device_effect.index.intValue() == i) {
                    return device_effect;
                }
            }
            return OFF;
        }
    }

    public enum TTS_LANGUAGE_TYPE {
        ZH(0, "zh-CN", "zh"),
        EN(1, "en-US", "en"),
        JP(2, "ja-JP", "ja"),
        KO(3, "ko-KR", "ko"),
        AR(4, "ar-SA", "ar"),
        CHT(6, "zh-TW", "cht"),
        DE(25, "de-DE", "de"),
        ES(43, "es-US", "es"),
        VI(63, "vi-VN", "vi"),
        FR(67, "fr-FR", "fr"),
        TH(68, "th-TH", "th"),
        PT(69, "pt-PT", "pt"),
        TR(70, "tr-TR", "tr"),
        YUE(71, "yue-CN", "yue"),
        YUE_HK(72, "yue-CN", "yue"),
        ID(73, "id-ID", "id"),
        FIL(74, "fil-PH", "fil");

        public final String name;
        public final String transName;
        public final int type;

        TTS_LANGUAGE_TYPE(int i, String str, String str2) {
            this.type = i;
            this.name = str;
            this.transName = str2;
        }

        public static String getTtsLanguageName(int i) {
            for (TTS_LANGUAGE_TYPE tts_language_type : values()) {
                if (tts_language_type.type == i) {
                    return tts_language_type.name;
                }
            }
            return "en-US";
        }

        public static String getTtsLanguageName(String str) {
            for (TTS_LANGUAGE_TYPE tts_language_type : values()) {
                if (tts_language_type.transName.equals(str)) {
                    return tts_language_type.name;
                }
            }
            return "en-US";
        }
    }

    public enum TENCENT_LANGUAGE_TYPE {
        ZH(0, "16k_zh"),
        EN(1, "16k_en"),
        JP(2, "16k_ja"),
        KO(3, "16k_ko"),
        FR(4, "16k_fr"),
        DE(5, "16k_de"),
        ES(7, "16k_es"),
        YUE(9, "16k_ca"),
        YUE_HK(99, "16k_ca"),
        TH(10, "16k_th"),
        ID(11, "16k_id"),
        VI(12, "16k_vi"),
        FIL(14, "16k_fil"),
        PT(15, "16k_pt"),
        TR(16, "16k_tr"),
        AR(17, "16k_ar"),
        MS(18, "16k_ms"),
        ZH_T(18, "16k_zh-TW");

        public final String name;
        public final int type;

        TENCENT_LANGUAGE_TYPE(int i, String str) {
            this.type = i;
            this.name = str;
        }

        public static String getTencentLanguageName(int i) {
            if ("zh".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return ZH.name;
            }
            if ("cht".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return ZH_T.name;
            }
            if ("en".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return EN.name;
            }
            if ("ja".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return JP.name;
            }
            if ("ko".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return KO.name;
            }
            if ("fr".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return FR.name;
            }
            if ("de".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return DE.name;
            }
            if ("es".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return ES.name;
            }
            if ("yue".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return YUE.name;
            }
            if ("th".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return TH.name;
            }
            if ("id".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return ID.name;
            }
            if ("vi".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return VI.name;
            }
            if ("fil".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return FIL.name;
            }
            if ("pt".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return PT.name;
            }
            if ("tr".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return TR.name;
            }
            if ("ar".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return AR.name;
            }
            if ("ms".equals(TRANSLATE_LANGUAGE.getLanguage(i).name)) {
                return MS.name;
            }
            return "16k_en";
        }
    }

    public enum SPEAKER {
        SPEAKER_A(0, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "00", C0874R.color.ic_solid_color_primary, C0874R.color.txt_color_tertiary),
        SPEAKER_B(1, "B", "01", C0874R.color.ic_solid_color_contrast, C0874R.color.txt_color_tertiary),
        SPEAKER_C(2, "C", "02", C0874R.color.ic_solid_color_downplay, C0874R.color.txt_color_primary),
        SPEAKER_D(3, "D", "03", C0874R.color.ic_solid_color_tertiary, C0874R.color.txt_color_tertiary),
        SPEAKER_E(4, ExifInterface.LONGITUDE_EAST, "04", C0874R.color.ic_solid_color_warning, C0874R.color.txt_color_primary),
        SPEAKER_F(5, "F", "05", C0874R.color.ic_solid_color_primary, C0874R.color.txt_color_tertiary),
        SPEAKER_G(6, "G", "06", C0874R.color.ic_solid_color_contrast, C0874R.color.txt_color_tertiary),
        SPEAKER_H(7, "H", "07", C0874R.color.ic_solid_color_downplay, C0874R.color.txt_color_primary),
        SPEAKER_I(8, "I", "08", C0874R.color.ic_solid_color_tertiary, C0874R.color.txt_color_tertiary),
        SPEAKER_J(9, "J", "09", C0874R.color.ic_solid_color_warning, C0874R.color.txt_color_primary),
        SPEAKER_K(10, "K", "10", C0874R.color.ic_solid_color_primary, C0874R.color.txt_color_tertiary),
        SPEAKER_L(11, "L", "11", C0874R.color.ic_solid_color_contrast, C0874R.color.txt_color_tertiary),
        SPEAKER_M(12, "M", "12", C0874R.color.ic_solid_color_downplay, C0874R.color.txt_color_primary),
        SPEAKER_N(13, "N", "13", C0874R.color.ic_solid_color_tertiary, C0874R.color.txt_color_tertiary),
        SPEAKER_O(14, "O", "14", C0874R.color.ic_solid_color_warning, C0874R.color.txt_color_primary),
        SPEAKER_P(15, "P", "15", C0874R.color.ic_solid_color_primary, C0874R.color.txt_color_tertiary),
        SPEAKER_Q(16, "Q", "16", C0874R.color.ic_solid_color_contrast, C0874R.color.txt_color_tertiary),
        SPEAKER_R(17, "R", "17", C0874R.color.ic_solid_color_downplay, C0874R.color.txt_color_primary),
        SPEAKER_S(18, ExifInterface.LATITUDE_SOUTH, "18", C0874R.color.ic_solid_color_tertiary, C0874R.color.txt_color_tertiary),
        SPEAKER_T(19, ExifInterface.GPS_DIRECTION_TRUE, "19", C0874R.color.ic_solid_color_warning, C0874R.color.txt_color_primary);

        public final int bgColorRes;
        public final String display;
        public final int index;
        public final String speaker;
        public final int textColorRes;

        SPEAKER(int i, String str, String str2, int i2, int i3) {
            this.index = i;
            this.display = str;
            this.speaker = str2;
            this.bgColorRes = i2;
            this.textColorRes = i3;
        }

        public static SPEAKER getSpeaker(String str) {
            for (SPEAKER speaker : values()) {
                if (speaker.speaker.equals(str)) {
                    return speaker;
                }
            }
            return SPEAKER_A;
        }
    }

    public enum AUDIO_UPLOAD_STATE {
        NONE(0),
        UPLOADING(1),
        SYNC2CLOUD(2),
        SYNC2CLOUD_SUCCESS(3),
        SYNC2CLOUD_FAIL(4),
        SYNC2PHONE(5),
        SYNC2PHONE_SUCCESS(6),
        SYNC2PHONE_FAIL(7),
        WAITING(8);

        public final int type;

        AUDIO_UPLOAD_STATE(int i) {
            this.type = i;
        }
    }

    public enum TRANS_STATE {
        NOT_TRANS(0),
        TRANSCRIBED(1),
        ON_TRANS(2),
        TRANS_FAIL(3);

        public final int type;

        TRANS_STATE(int i) {
            this.type = i;
        }
    }

    public enum TRANS_MODEL_STATUS {
        NOT_START(0),
        RECORDING_WITHOUT_TRANS(1),
        TRANSCRIBING(2),
        RECORD_PAUSED(3),
        FINISHED(4);

        public final int type;

        TRANS_MODEL_STATUS(int i) {
            this.type = i;
        }
    }
}
