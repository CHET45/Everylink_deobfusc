package com.tencent.beacon.base.store;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/* JADX INFO: renamed from: com.tencent.beacon.base.store.a */
/* JADX INFO: compiled from: BeaconProperties.java */
/* JADX INFO: loaded from: classes4.dex */
public class SharedPreferencesC2686a implements SharedPreferences {

    /* JADX INFO: renamed from: a */
    private static volatile SharedPreferencesC2686a f1368a;

    /* JADX INFO: renamed from: b */
    private boolean f1369b;

    /* JADX INFO: renamed from: c */
    private C2692g f1370c;

    /* JADX INFO: renamed from: d */
    private a f1371d;

    /* JADX INFO: renamed from: e */
    private SharedPreferences f1372e;

    /* JADX INFO: renamed from: f */
    private KeyValueStorage f1373f;

    /* JADX INFO: renamed from: com.tencent.beacon.base.store.a$a */
    /* JADX INFO: compiled from: BeaconProperties.java */
    public static class a implements SharedPreferences.Editor {

        /* JADX INFO: renamed from: a */
        private C2692g f1374a;

        /* JADX INFO: renamed from: b */
        private KeyValueStorage f1375b;

        a(C2692g c2692g) {
            this.f1374a = c2692g;
        }

        /* JADX INFO: renamed from: a */
        public void m1396a(KeyValueStorage keyValueStorage) {
            this.f1375b = keyValueStorage;
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor clear() {
            KeyValueStorage keyValueStorage = this.f1375b;
            if (keyValueStorage != null) {
                keyValueStorage.clear();
            } else {
                this.f1374a.m1419a();
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public boolean commit() {
            return true;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String str, boolean z) {
            KeyValueStorage keyValueStorage = this.f1375b;
            if (keyValueStorage != null) {
                keyValueStorage.putBoolean(str, z);
            } else {
                this.f1374a.m1423b(str, Boolean.valueOf(z));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String str, float f) {
            KeyValueStorage keyValueStorage = this.f1375b;
            if (keyValueStorage != null) {
                keyValueStorage.putFloat(str, f);
            } else {
                this.f1374a.m1423b(str, Float.valueOf(f));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String str, int i) {
            KeyValueStorage keyValueStorage = this.f1375b;
            if (keyValueStorage != null) {
                keyValueStorage.putInt(str, i);
            } else {
                this.f1374a.m1423b(str, Integer.valueOf(i));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String str, long j) {
            KeyValueStorage keyValueStorage = this.f1375b;
            if (keyValueStorage != null) {
                keyValueStorage.putLong(str, j);
            } else {
                this.f1374a.m1423b(str, Long.valueOf(j));
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String str, String str2) {
            KeyValueStorage keyValueStorage = this.f1375b;
            if (keyValueStorage != null) {
                keyValueStorage.putString(str, str2);
            } else {
                this.f1374a.m1423b(str, str2);
            }
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String str, Set<String> set) {
            this.f1374a.m1424b(str, (Set) set);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String str) {
            KeyValueStorage keyValueStorage = this.f1375b;
            if (keyValueStorage != null) {
                keyValueStorage.remove(str);
            } else {
                this.f1374a.m1422b(str);
            }
            return this;
        }
    }

    private SharedPreferencesC2686a() {
    }

    /* JADX INFO: renamed from: a */
    public static SharedPreferencesC2686a m1391a() {
        if (f1368a == null) {
            synchronized (SharedPreferencesC2686a.class) {
                if (f1368a == null) {
                    f1368a = new SharedPreferencesC2686a();
                }
            }
        }
        return f1368a;
    }

    /* JADX INFO: renamed from: b */
    public void m1395b() {
        Map<String, ?> mapM1421b = this.f1370c.m1421b();
        if (mapM1421b == null || mapM1421b.isEmpty()) {
            return;
        }
        try {
            for (Map.Entry<String, ?> entry : mapM1421b.entrySet()) {
                C2695c.m1474d("beacon properties migrate to mmkv key: " + entry.getKey() + ", value: " + entry.getValue(), new Object[0]);
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    this.f1371d.remove(key);
                } else if (value instanceof Boolean) {
                    this.f1371d.putBoolean(key, ((Boolean) value).booleanValue());
                } else if (value instanceof Integer) {
                    this.f1371d.putInt(key, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    this.f1371d.putLong(key, ((Long) value).longValue());
                } else if (value instanceof Float) {
                    this.f1371d.putFloat(key, ((Float) value).floatValue());
                } else if (value instanceof Double) {
                    this.f1371d.putFloat(key, ((Double) value).floatValue());
                } else if (value instanceof String) {
                    this.f1371d.putString(key, (String) value);
                } else {
                    this.f1371d.putString(key, String.valueOf(value));
                }
            }
        } catch (Exception e) {
            C2695c.m1468b("Failed to migrate KV storage", e);
        }
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String str) {
        KeyValueStorage keyValueStorage = this.f1373f;
        return keyValueStorage != null ? keyValueStorage.contains(str) : this.f1370c.m1420a(str);
    }

    @Override // android.content.SharedPreferences
    public Map<String, ?> getAll() {
        return this.f1370c.m1421b();
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String str, boolean z) {
        KeyValueStorage keyValueStorage = this.f1373f;
        if (keyValueStorage != null) {
            return keyValueStorage.getBoolean(str, z);
        }
        Object objM1392a = m1392a(str, Boolean.valueOf(z));
        return objM1392a instanceof Boolean ? ((Boolean) objM1392a).booleanValue() : z;
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String str, float f) {
        KeyValueStorage keyValueStorage = this.f1373f;
        if (keyValueStorage != null) {
            return keyValueStorage.getFloat(str, f);
        }
        Object objM1392a = m1392a(str, Float.valueOf(f));
        return objM1392a instanceof Number ? ((Number) objM1392a).floatValue() : f;
    }

    @Override // android.content.SharedPreferences
    public int getInt(String str, int i) {
        KeyValueStorage keyValueStorage = this.f1373f;
        if (keyValueStorage != null) {
            return keyValueStorage.getInt(str, i);
        }
        Object objM1392a = m1392a(str, Integer.valueOf(i));
        return objM1392a instanceof Number ? ((Number) objM1392a).intValue() : i;
    }

    @Override // android.content.SharedPreferences
    public long getLong(String str, long j) {
        KeyValueStorage keyValueStorage = this.f1373f;
        if (keyValueStorage != null) {
            return keyValueStorage.getLong(str, j);
        }
        Object objM1392a = m1392a(str, Long.valueOf(j));
        return objM1392a instanceof Number ? ((Number) objM1392a).longValue() : j;
    }

    @Override // android.content.SharedPreferences
    public String getString(String str, String str2) {
        KeyValueStorage keyValueStorage = this.f1373f;
        if (keyValueStorage != null) {
            return keyValueStorage.getString(str, str2);
        }
        Object objM1392a = m1392a(str, str2);
        return objM1392a instanceof String ? (String) objM1392a : str2;
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String str, Set<String> set) {
        return !this.f1369b ? set : this.f1370c.m1418a(str, (Set) set);
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override // android.content.SharedPreferences
    public a edit() {
        if (!this.f1369b) {
            C2700h.m1485a("BeaconProperties has not init!");
            m1393a(C2630c.m1059c().m1067b());
        }
        return this.f1371d;
    }

    /* JADX INFO: renamed from: a */
    public synchronized void m1393a(Context context) {
        if (this.f1369b || context == null) {
            return;
        }
        try {
            this.f1370c = C2692g.m1400a(context, "prop_" + C2629b.m1049c(context).replace(context.getPackageName(), ""));
            a aVar = new a(this.f1370c);
            this.f1371d = aVar;
            KeyValueStorage keyValueStorage = this.f1373f;
            if (keyValueStorage != null) {
                aVar.m1396a(keyValueStorage);
                if (this.f1373f.getBoolean("key_need_migrate", true)) {
                    m1395b();
                    this.f1371d.putBoolean("key_need_migrate", false);
                }
            }
            this.f1369b = true;
        } catch (IOException e) {
            C2695c.m1465a(e);
            C2624j.m1031e().m1024a("504", "[properties] PropertiesFile create error!", e);
            this.f1369b = false;
        }
    }

    /* JADX INFO: renamed from: a */
    public void m1394a(KeyValueStorage keyValueStorage) {
        this.f1373f = keyValueStorage;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: a */
    private <T> Object m1392a(String str, T t) {
        if (!this.f1369b) {
            return t;
        }
        Object objM1417a = this.f1370c.m1417a(str, t);
        if (objM1417a == null || objM1417a == t) {
            Context contextM1067b = C2630c.m1059c().m1067b();
            if (this.f1372e == null) {
                this.f1372e = contextM1067b.getSharedPreferences("DENGTA_META", 0);
            }
            if (t instanceof Boolean) {
                objM1417a = Boolean.valueOf(this.f1372e.getBoolean(str, ((Boolean) t).booleanValue()));
            } else if (t instanceof String) {
                objM1417a = this.f1372e.getString(str, (String) t);
            } else if (t instanceof Integer) {
                objM1417a = Integer.valueOf(this.f1372e.getInt(str, ((Integer) t).intValue()));
            } else if (t instanceof Long) {
                objM1417a = Long.valueOf(this.f1372e.getLong(str, ((Long) t).longValue()));
            } else if (t instanceof Float) {
                objM1417a = Float.valueOf(this.f1372e.getFloat(str, ((Float) t).floatValue()));
            }
            if (objM1417a != null && objM1417a != t) {
                this.f1370c.m1423b(str, objM1417a);
            }
        }
        return objM1417a == null ? t : objM1417a;
    }
}
