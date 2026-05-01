package com.aivox.common_ui.cityselect.util;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class GsonUtil {
    private static final TypeAdapter<String> STRING = new TypeAdapter<String>() { // from class: com.aivox.common_ui.cityselect.util.GsonUtil.1
        @Override // com.google.gson.TypeAdapter
        /* JADX INFO: renamed from: read, reason: avoid collision after fix types in other method */
        public String read2(JsonReader jsonReader) {
            try {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return "";
                }
                return jsonReader.nextString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, String str) {
            try {
                if (str == null) {
                    jsonWriter.value("");
                } else {
                    jsonWriter.value(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private static final TypeAdapter<Integer> INTEGER = new TypeAdapter<Integer>() { // from class: com.aivox.common_ui.cityselect.util.GsonUtil.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        /* JADX INFO: renamed from: read */
        public Integer read2(JsonReader jsonReader) {
            try {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return 0;
                }
                return Integer.valueOf(jsonReader.nextInt());
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, Integer num) {
            try {
                if (num == null) {
                    jsonWriter.value(0L);
                } else {
                    jsonWriter.value(num);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private static final TypeAdapter<Double> DOUBLE = new TypeAdapter<Double>() { // from class: com.aivox.common_ui.cityselect.util.GsonUtil.3
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        /* JADX INFO: renamed from: read */
        public Double read2(JsonReader jsonReader) {
            try {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return Double.valueOf(0.0d);
                }
                return Double.valueOf(jsonReader.nextDouble());
            } catch (Exception e) {
                e.printStackTrace();
                return Double.valueOf(0.0d);
            }
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, Double d) {
            try {
                if (d == null) {
                    jsonWriter.value(0.0d);
                } else {
                    jsonWriter.value(d);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private static final TypeAdapter<Long> LONG = new TypeAdapter<Long>() { // from class: com.aivox.common_ui.cityselect.util.GsonUtil.4
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.TypeAdapter
        /* JADX INFO: renamed from: read */
        public Long read2(JsonReader jsonReader) {
            try {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return 0L;
                }
                return Long.valueOf(jsonReader.nextLong());
            } catch (Exception e) {
                e.printStackTrace();
                return 0L;
            }
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, Long l) {
            try {
                if (l == null) {
                    jsonWriter.value(0L);
                } else {
                    jsonWriter.value(l);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static String getString(Object obj) {
        return buildGson().toJson(obj);
    }

    public static <T> T getBean(String str, JSONObject jSONObject, Class<T> cls) {
        try {
            return (T) buildGson().fromJson(jSONObject.getString(str), (Class) cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getBean(String str, Class<T> cls) {
        try {
            return (T) buildGson().fromJson(str, (Class) cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getBean(JSONObject jSONObject, Class<T> cls) {
        try {
            return (T) buildGson().fromJson(jSONObject.toString(), (Class) cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getJSONObjectFromMapNom(Map<String, Object> map) {
        try {
            return new JSONObject(buildGson().toJson(map));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getJSONObjectFromString(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            return jSONObject;
        }
    }

    public static JSONObject getJSONObjectFromMap(Map<String, String> map) {
        try {
            return new JSONObject(buildGson().toJson(map));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> getBeanList(String str, TypeToken<List<T>> typeToken) {
        try {
            return (List) buildGson().fromJson(str, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> getBeanList(JSONObject jSONObject, TypeToken<List<T>> typeToken) {
        try {
            return (List) buildGson().fromJson(jSONObject.toString(), typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, STRING).registerTypeAdapter(Integer.class, INTEGER).registerTypeAdapter(Double.class, DOUBLE).registerTypeAdapter(Long.class, LONG);
        gsonBuilder.serializeNulls();
        return gsonBuilder.create();
    }

    public static int getJSONType(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        char c = str.substring(0, 1).toCharArray()[0];
        if (c == '{') {
            return 2;
        }
        return c == '[' ? 1 : 0;
    }
}
