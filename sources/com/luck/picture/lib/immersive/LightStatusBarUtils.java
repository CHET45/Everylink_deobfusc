package com.luck.picture.lib.immersive;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.lang.reflect.Field;
import org.videolan.libvlc.MediaDiscoverer;

/* JADX INFO: loaded from: classes3.dex */
public class LightStatusBarUtils {
    public static void setLightStatusBarAboveAPI23(Activity activity, boolean z, boolean z2, boolean z3, boolean z4) {
        setLightStatusBar(activity, z, z2, z3, z4);
    }

    public static void setLightStatusBar(Activity activity, boolean z) {
        setLightStatusBar(activity, false, false, false, z);
    }

    public static void setLightStatusBar(Activity activity, boolean z, boolean z2, boolean z3, boolean z4) {
        int lightStatusBarAvailableRomType = RomUtils.getLightStatusBarAvailableRomType();
        if (lightStatusBarAvailableRomType == 1) {
            if (RomUtils.getMIUIVersionCode() >= 7) {
                setAndroidNativeLightStatusBar(activity, z, z2, z3, z4);
                return;
            } else {
                setMIUILightStatusBar(activity, z, z2, z3, z4);
                return;
            }
        }
        if (lightStatusBarAvailableRomType == 2) {
            setFlymeLightStatusBar(activity, z, z2, z3, z4);
        } else {
            if (lightStatusBarAvailableRomType != 3) {
                return;
            }
            setAndroidNativeLightStatusBar(activity, z, z2, z3, z4);
        }
    }

    private static boolean setMIUILightStatusBar(Activity activity, boolean z, boolean z2, boolean z3, boolean z4) {
        initStatusBarStyle(activity, z, z2);
        Class<?> cls = activity.getWindow().getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE).invoke(activity.getWindow(), Integer.valueOf(z4 ? i : 0), Integer.valueOf(i));
            return true;
        } catch (Exception unused) {
            setAndroidNativeLightStatusBar(activity, z, z2, z3, z4);
            return false;
        }
    }

    private static boolean setFlymeLightStatusBar(Activity activity, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5 = false;
        if (activity == null) {
            return false;
        }
        initStatusBarStyle(activity, z, z2);
        try {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            int i = declaredField.getInt(null);
            int i2 = declaredField2.getInt(attributes);
            declaredField2.setInt(attributes, z4 ? i | i2 : (~i) & i2);
            activity.getWindow().setAttributes(attributes);
            try {
                if (RomUtils.getFlymeVersion() >= 7) {
                    setAndroidNativeLightStatusBar(activity, z, z2, z3, z4);
                }
                return true;
            } catch (Exception unused) {
                z5 = true;
                setAndroidNativeLightStatusBar(activity, z, z2, z3, z4);
                return z5;
            }
        } catch (Exception unused2) {
        }
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean z, boolean z2, boolean z3, boolean z4) {
        try {
            if (z3) {
                Window window = activity.getWindow();
                if (z && z2) {
                    if (z4) {
                        window.getDecorView().setSystemUiVisibility(8448);
                    } else {
                        window.getDecorView().setSystemUiVisibility(256);
                    }
                } else if (z || z2) {
                    if (z || !z2) {
                        return;
                    }
                    if (z4) {
                        window.getDecorView().setSystemUiVisibility(9472);
                    } else {
                        window.getDecorView().setSystemUiVisibility(MediaDiscoverer.Event.Started);
                    }
                } else if (z4) {
                    window.getDecorView().setSystemUiVisibility(9472);
                } else {
                    window.getDecorView().setSystemUiVisibility(MediaDiscoverer.Event.Started);
                }
            } else {
                View decorView = activity.getWindow().getDecorView();
                if (z4) {
                    decorView.setSystemUiVisibility(8192);
                } else {
                    decorView.setSystemUiVisibility(0);
                }
            }
        } catch (Exception unused) {
        }
    }

    private static void initStatusBarStyle(Activity activity, boolean z, boolean z2) {
        if (z && z2) {
            activity.getWindow().getDecorView().setSystemUiVisibility(256);
            return;
        }
        if (!z && !z2) {
            activity.getWindow().getDecorView().setSystemUiVisibility(MediaDiscoverer.Event.Started);
        } else {
            if (z || !z2) {
                return;
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(MediaDiscoverer.Event.Started);
        }
    }
}
