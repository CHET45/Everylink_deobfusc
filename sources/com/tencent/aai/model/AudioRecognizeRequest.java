package com.tencent.aai.model;

import com.tencent.aai.audio.data.PcmAudioDataSource;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class AudioRecognizeRequest {
    private final Map<String, Object> apiParams;
    private String extraUserAgent;
    private final PcmAudioDataSource pcmAudioDataSource;
    private final int requestId;
    private long timestamp;
    private int voice_format;

    public static class Builder {
        private final Map<String, Object> apiParams;
        private String extraUserAgent = "";
        public PcmAudioDataSource pcmAudioDataSource;

        public Builder() {
            TreeMap treeMap = new TreeMap();
            this.apiParams = treeMap;
            treeMap.put("engine_model_type", "16k_zh");
            treeMap.put("needvad", 1);
        }

        public AudioRecognizeRequest build() {
            AudioRecognizeRequest audioRecognizeRequest = new AudioRecognizeRequest(this.apiParams, this.pcmAudioDataSource);
            audioRecognizeRequest.extraUserAgent = this.extraUserAgent;
            return audioRecognizeRequest;
        }

        public Builder pcmAudioDataSource(PcmAudioDataSource pcmAudioDataSource) {
            this.pcmAudioDataSource = pcmAudioDataSource;
            return this;
        }

        public Builder setApiParam(String str, Object obj) {
            this.apiParams.put(str, obj);
            return this;
        }

        public Builder setConvert_num_mode(int i) {
            this.apiParams.put("convert_num_mode", Integer.valueOf(i));
            return this;
        }

        public Builder setCustomizationId(String str) {
            this.apiParams.put("customization_id", str);
            return this;
        }

        public Builder setEngineModelType(String str) {
            this.apiParams.put("engine_model_type", str);
            return this;
        }

        public Builder setExtraUserAgent(String str) {
            this.extraUserAgent = str;
            return this;
        }

        public Builder setFilterDirty(int i) {
            this.apiParams.put("filter_dirty", Integer.valueOf(i));
            return this;
        }

        public Builder setFilterModal(int i) {
            this.apiParams.put("filter_modal", Integer.valueOf(i));
            return this;
        }

        public Builder setFilterPunc(int i) {
            this.apiParams.put("filter_punc", Integer.valueOf(i));
            return this;
        }

        public Builder setHotWordId(String str) {
            this.apiParams.put("hotword_id", str);
            return this;
        }

        public Builder setMaxSpeakTime(int i) {
            this.apiParams.put("max_speak_time", Integer.valueOf(i));
            return this;
        }

        public Builder setNeedvad(int i) {
            this.apiParams.put("needvad", Integer.valueOf(i));
            return this;
        }

        public Builder setNoiseThreshold(float f) {
            this.apiParams.put("noise_threshold", Float.valueOf(f));
            return this;
        }

        public Builder setReinforceHotword(int i) {
            this.apiParams.put("reinforce_hotword", Integer.valueOf(i));
            return this;
        }

        public Builder setVadSilenceTime(int i) {
            this.apiParams.put("vad_silence_time", Integer.valueOf(i));
            return this;
        }

        public Builder setWordInfo(int i) {
            this.apiParams.put("word_info", Integer.valueOf(i));
            return this;
        }
    }

    private AudioRecognizeRequest(Map<String, Object> map, PcmAudioDataSource pcmAudioDataSource) {
        this.extraUserAgent = "";
        this.voice_format = 10;
        TreeMap treeMap = new TreeMap();
        this.apiParams = treeMap;
        treeMap.putAll(map);
        this.timestamp = System.currentTimeMillis() / 1000;
        this.pcmAudioDataSource = pcmAudioDataSource;
        this.requestId = UUID.randomUUID().hashCode();
    }

    public void UpdateTimestamp() {
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public Object getApiParam(String str) {
        return this.apiParams.get(str);
    }

    public Map<String, Object> getApiParams() {
        return this.apiParams;
    }

    public int getConvert_num_mode() {
        Object obj = this.apiParams.get("convert_num_mode");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public String getCustomization_id() {
        return (String) this.apiParams.get("customization_id");
    }

    public String getEngineModelType() {
        return (String) this.apiParams.get("engine_model_type");
    }

    public String getExtraUserAgent() {
        return this.extraUserAgent;
    }

    public int getFilter_dirty() {
        Object obj = this.apiParams.get("filter_dirty");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public int getFilter_modal() {
        Object obj = this.apiParams.get("filter_modal");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public int getFilter_punc() {
        Object obj = this.apiParams.get("filter_punc");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public String getHotWordId() {
        return (String) this.apiParams.get("hotword_id");
    }

    public int getMaxSpeakTime() {
        Object obj = this.apiParams.get("max_speak_time");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public int getNeedvad() {
        Object obj = this.apiParams.get("needvad");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public float getNoise_threshold() {
        Object obj = this.apiParams.get("noise_threshold");
        if (obj == null) {
            return 0.0f;
        }
        return ((Float) obj).floatValue();
    }

    public PcmAudioDataSource getPcmAudioDataSource() {
        return this.pcmAudioDataSource;
    }

    public int getReinforce_hotword() {
        Object obj = this.apiParams.get("reinforce_hotword");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public int getRequestId() {
        return this.requestId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getVad_silence_time() {
        Object obj = this.apiParams.get("vad_silence_time");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public int getVoice_format() {
        return this.voice_format;
    }

    public int getWord_info() {
        Object obj = this.apiParams.get("word_info");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public void setVoice_format(int i) {
        this.voice_format = i;
    }
}
