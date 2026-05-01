package com.lzy.okgo.cookie.store;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.lzy.okgo.OkGo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.UByte;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* JADX INFO: loaded from: classes4.dex */
public class PersistentCookieStore implements CookieStore {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private static final String COOKIE_PREFS = "okgo_cookie";
    private static final String LOG_TAG = "PersistentCookieStore";
    private final SharedPreferences cookiePrefs;
    private final HashMap<String, ConcurrentHashMap<String, Cookie>> cookies;

    public PersistentCookieStore() {
        Cookie cookieDecodeCookie;
        SharedPreferences sharedPreferences = OkGo.getContext().getSharedPreferences(COOKIE_PREFS, 0);
        this.cookiePrefs = sharedPreferences;
        this.cookies = new HashMap<>();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (entry.getValue() != null && !entry.getKey().startsWith(COOKIE_NAME_PREFIX)) {
                for (String str : TextUtils.split((String) entry.getValue(), PunctuationConst.COMMA)) {
                    String string = this.cookiePrefs.getString(COOKIE_NAME_PREFIX + str, null);
                    if (string != null && (cookieDecodeCookie = decodeCookie(string)) != null) {
                        if (!this.cookies.containsKey(entry.getKey())) {
                            this.cookies.put(entry.getKey(), new ConcurrentHashMap<>());
                        }
                        this.cookies.get(entry.getKey()).put(str, cookieDecodeCookie);
                    }
                }
            }
        }
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + PunctuationConst.f488AT + cookie.domain();
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public List<Cookie> loadCookie(HttpUrl httpUrl) {
        ArrayList arrayList = new ArrayList();
        if (this.cookies.containsKey(httpUrl.host())) {
            for (Cookie cookie : this.cookies.get(httpUrl.host()).values()) {
                if (isCookieExpired(cookie)) {
                    removeCookie(httpUrl, cookie);
                } else {
                    arrayList.add(cookie);
                }
            }
        }
        return arrayList;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public void saveCookie(HttpUrl httpUrl, List<Cookie> list) {
        if (!this.cookies.containsKey(httpUrl.host())) {
            this.cookies.put(httpUrl.host(), new ConcurrentHashMap<>());
        }
        for (Cookie cookie : list) {
            if (isCookieExpired(cookie)) {
                removeCookie(httpUrl, cookie);
            } else {
                saveCookie(httpUrl, cookie, getCookieToken(cookie));
            }
        }
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public void saveCookie(HttpUrl httpUrl, Cookie cookie) {
        if (!this.cookies.containsKey(httpUrl.host())) {
            this.cookies.put(httpUrl.host(), new ConcurrentHashMap<>());
        }
        if (isCookieExpired(cookie)) {
            removeCookie(httpUrl, cookie);
        } else {
            saveCookie(httpUrl, cookie, getCookieToken(cookie));
        }
    }

    private void saveCookie(HttpUrl httpUrl, Cookie cookie, String str) {
        this.cookies.get(httpUrl.host()).put(str, cookie);
        SharedPreferences.Editor editorEdit = this.cookiePrefs.edit();
        editorEdit.putString(httpUrl.host(), TextUtils.join(PunctuationConst.COMMA, this.cookies.get(httpUrl.host()).keySet()));
        editorEdit.putString(COOKIE_NAME_PREFIX + str, encodeCookie(new SerializableHttpCookie(cookie)));
        editorEdit.apply();
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public boolean removeCookie(HttpUrl httpUrl, Cookie cookie) {
        String cookieToken = getCookieToken(cookie);
        if (!this.cookies.containsKey(httpUrl.host()) || !this.cookies.get(httpUrl.host()).containsKey(cookieToken)) {
            return false;
        }
        this.cookies.get(httpUrl.host()).remove(cookieToken);
        SharedPreferences.Editor editorEdit = this.cookiePrefs.edit();
        if (this.cookiePrefs.contains(COOKIE_NAME_PREFIX + cookieToken)) {
            editorEdit.remove(COOKIE_NAME_PREFIX + cookieToken);
        }
        editorEdit.putString(httpUrl.host(), TextUtils.join(PunctuationConst.COMMA, this.cookies.get(httpUrl.host()).keySet()));
        editorEdit.apply();
        return true;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public boolean removeCookie(HttpUrl httpUrl) {
        if (!this.cookies.containsKey(httpUrl.host())) {
            return false;
        }
        Set<String> setKeySet = this.cookies.get(httpUrl.host()).keySet();
        SharedPreferences.Editor editorEdit = this.cookiePrefs.edit();
        for (String str : setKeySet) {
            if (this.cookiePrefs.contains(COOKIE_NAME_PREFIX + str)) {
                editorEdit.remove(COOKIE_NAME_PREFIX + str);
            }
        }
        editorEdit.remove(httpUrl.host()).apply();
        this.cookies.remove(httpUrl.host());
        return true;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public boolean removeAllCookie() {
        this.cookiePrefs.edit().clear().apply();
        this.cookies.clear();
        return true;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public List<Cookie> getAllCookie() {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = this.cookies.keySet().iterator();
        while (it.hasNext()) {
            arrayList.addAll(this.cookies.get(it.next()).values());
        }
        return arrayList;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public List<Cookie> getCookie(HttpUrl httpUrl) {
        ArrayList arrayList = new ArrayList();
        ConcurrentHashMap<String, Cookie> concurrentHashMap = this.cookies.get(httpUrl.host());
        if (concurrentHashMap != null) {
            arrayList.addAll(concurrentHashMap.values());
        }
        return arrayList;
    }

    private String encodeCookie(SerializableHttpCookie serializableHttpCookie) {
        if (serializableHttpCookie == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(byteArrayOutputStream).writeObject(serializableHttpCookie);
            return byteArrayToHexString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e);
            return null;
        }
    }

    private Cookie decodeCookie(String str) {
        try {
            return ((SerializableHttpCookie) new ObjectInputStream(new ByteArrayInputStream(hexStringToByteArray(str))).readObject()).getCookie();
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in decodeCookie", e);
            return null;
        } catch (ClassNotFoundException e2) {
            Log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", e2);
            return null;
        }
    }

    private String byteArrayToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            int i = b & UByte.MAX_VALUE;
            if (i < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    private byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
