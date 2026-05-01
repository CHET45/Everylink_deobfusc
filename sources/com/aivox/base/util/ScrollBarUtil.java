package com.aivox.base.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.aivox.base.C0874R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class ScrollBarUtil {
    public static void tintScrollbarColor(Context context, RecyclerView recyclerView) {
        try {
            Method declaredMethod = View.class.getDeclaredMethod("getScrollCache", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(recyclerView, new Object[0]);
            Object obj = objInvoke.getClass().getField("scrollBar").get(objInvoke);
            Class<?> cls = obj.getClass();
            Field declaredField = cls.getDeclaredField("mVerticalThumb");
            Field declaredField2 = cls.getDeclaredField("mHorizontalThumb");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            Drawable drawable = (Drawable) declaredField.get(obj);
            Drawable drawable2 = (Drawable) declaredField2.get(obj);
            Drawable drawableTintDrawable = tintDrawable(drawable, C0874R.color.scorllbar);
            Drawable drawableTintDrawable2 = tintDrawable(drawable2, C0874R.color.scorllbar);
            declaredField.set(obj, drawableTintDrawable);
            declaredField2.set(obj, drawableTintDrawable2);
        } catch (Exception unused) {
        }
    }

    public static Drawable tintDrawable(Drawable drawable, int i) {
        Drawable drawableWrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawableWrap, i);
        return drawableWrap;
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colorStateList) {
        Drawable drawableWrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(drawableWrap, colorStateList);
        return drawableWrap;
    }

    public static int getColorById(Context context, int i) {
        return context.getResources().getColor(i);
    }
}
