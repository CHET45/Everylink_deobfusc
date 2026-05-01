package com.lxj.xpopup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.aivox.base.common.Constant;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.core.PositionPopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupType;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.impl.BottomListPopupView;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.lxj.xpopup.impl.ConfirmPopupView;
import com.lxj.xpopup.impl.InputConfirmPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnImageViewerLongPressListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.util.XPermission;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class XPopup {
    private static int primaryColor = Color.parseColor("#121212");
    private static int animationDuration = Constant.EVENT.BLE_FILE_LIST;
    private static int statusBarBgColor = Color.parseColor("#55000000");
    private static int navigationBarColor = 0;
    private static int shadowBgColor = Color.parseColor("#7F000000");
    public static PointF longClickPoint = null;

    private XPopup() {
    }

    public static void setShadowBgColor(int i) {
        shadowBgColor = i;
    }

    public static int getShadowBgColor() {
        return shadowBgColor;
    }

    public static void setStatusBarBgColor(int i) {
        statusBarBgColor = i;
    }

    public static int getStatusBarBgColor() {
        return statusBarBgColor;
    }

    public static void setNavigationBarColor(int i) {
        navigationBarColor = i;
    }

    public static int getNavigationBarColor() {
        return navigationBarColor;
    }

    public static void setPrimaryColor(int i) {
        primaryColor = i;
    }

    public static int getPrimaryColor() {
        return primaryColor;
    }

    public static void setAnimationDuration(int i) {
        if (i >= 0) {
            animationDuration = i;
        }
    }

    public static int getAnimationDuration() {
        return animationDuration;
    }

    public static void fixLongClick(View view2) {
        view2.setOnTouchListener(new View.OnTouchListener() { // from class: com.lxj.xpopup.XPopup.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view3, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    XPopup.longClickPoint = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                }
                if ("xpopup".equals(view3.getTag()) && motionEvent.getAction() == 2) {
                    view3.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == 1) {
                    view3.getParent().requestDisallowInterceptTouchEvent(false);
                    view3.setTag(null);
                }
                return false;
            }
        });
        view2.setTag("xpopup");
    }

    public static class Builder {
        private Context context;
        private final PopupInfo popupInfo = new PopupInfo();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder popupType(PopupType popupType) {
            this.popupInfo.popupType = popupType;
            return this;
        }

        public Builder dismissOnBackPressed(Boolean bool) {
            this.popupInfo.isDismissOnBackPressed = bool;
            return this;
        }

        public Builder dismissOnTouchOutside(Boolean bool) {
            this.popupInfo.isDismissOnTouchOutside = bool;
            return this;
        }

        public Builder autoDismiss(Boolean bool) {
            this.popupInfo.autoDismiss = bool;
            return this;
        }

        public Builder hasShadowBg(Boolean bool) {
            this.popupInfo.hasShadowBg = bool;
            return this;
        }

        public Builder hasBlurBg(boolean z) {
            this.popupInfo.hasBlurBg = Boolean.valueOf(z);
            return this;
        }

        public Builder atView(View view2) {
            this.popupInfo.atView = view2;
            return this;
        }

        public Builder watchView(View view2) {
            this.popupInfo.watchView = view2;
            this.popupInfo.watchView.setOnTouchListener(new View.OnTouchListener() { // from class: com.lxj.xpopup.XPopup.Builder.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view3, MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 0) {
                        return false;
                    }
                    Builder.this.popupInfo.touchPoint = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                    return false;
                }
            });
            return this;
        }

        public Builder popupAnimation(PopupAnimation popupAnimation) {
            this.popupInfo.popupAnimation = popupAnimation;
            return this;
        }

        public Builder customAnimator(PopupAnimator popupAnimator) {
            this.popupInfo.customAnimator = popupAnimator;
            return this;
        }

        public Builder popupHeight(int i) {
            this.popupInfo.popupHeight = i;
            return this;
        }

        public Builder popupWidth(int i) {
            this.popupInfo.popupWidth = i;
            return this;
        }

        public Builder maxWidth(int i) {
            this.popupInfo.maxWidth = i;
            return this;
        }

        public Builder maxHeight(int i) {
            this.popupInfo.maxHeight = i;
            return this;
        }

        public Builder autoOpenSoftInput(Boolean bool) {
            this.popupInfo.autoOpenSoftInput = bool;
            return this;
        }

        public Builder moveUpToKeyboard(Boolean bool) {
            this.popupInfo.isMoveUpToKeyboard = bool;
            return this;
        }

        public Builder popupPosition(PopupPosition popupPosition) {
            this.popupInfo.popupPosition = popupPosition;
            return this;
        }

        public Builder hasStatusBarShadow(boolean z) {
            this.popupInfo.hasStatusBarShadow = Boolean.valueOf(z);
            return this;
        }

        public Builder hasStatusBar(boolean z) {
            this.popupInfo.hasStatusBar = Boolean.valueOf(z);
            return this;
        }

        public Builder hasNavigationBar(boolean z) {
            this.popupInfo.hasNavigationBar = Boolean.valueOf(z);
            return this;
        }

        public Builder navigationBarColor(int i) {
            this.popupInfo.navigationBarColor = i;
            return this;
        }

        public Builder statusBarBgColor(int i) {
            this.popupInfo.statusBarBgColor = i;
            return this;
        }

        public Builder offsetX(int i) {
            this.popupInfo.offsetX = i;
            return this;
        }

        public Builder offsetY(int i) {
            this.popupInfo.offsetY = i;
            return this;
        }

        public Builder enableDrag(boolean z) {
            this.popupInfo.enableDrag = Boolean.valueOf(z);
            return this;
        }

        public Builder isCenterHorizontal(boolean z) {
            this.popupInfo.isCenterHorizontal = z;
            return this;
        }

        public Builder isRequestFocus(boolean z) {
            this.popupInfo.isRequestFocus = z;
            return this;
        }

        public Builder autoFocusEditText(boolean z) {
            this.popupInfo.autoFocusEditText = z;
            return this;
        }

        public Builder isDarkTheme(boolean z) {
            this.popupInfo.isDarkTheme = z;
            return this;
        }

        public Builder isClickThrough(boolean z) {
            this.popupInfo.isClickThrough = z;
            return this;
        }

        public Builder enableShowWhenAppBackground(boolean z) {
            this.popupInfo.enableShowWhenAppBackground = z;
            return this;
        }

        public Builder isThreeDrag(boolean z) {
            this.popupInfo.isThreeDrag = z;
            return this;
        }

        public Builder isDestroyOnDismiss(boolean z) {
            this.popupInfo.isDestroyOnDismiss = z;
            return this;
        }

        public Builder borderRadius(float f) {
            this.popupInfo.borderRadius = f;
            return this;
        }

        public Builder positionByWindowCenter(boolean z) {
            this.popupInfo.positionByWindowCenter = z;
            return this;
        }

        public Builder isViewMode(boolean z) {
            this.popupInfo.isViewMode = z;
            return this;
        }

        public Builder shadowBgColor(int i) {
            this.popupInfo.shadowBgColor = i;
            return this;
        }

        public Builder animationDuration(int i) {
            this.popupInfo.animationDuration = i;
            return this;
        }

        public Builder keepScreenOn(boolean z) {
            this.popupInfo.keepScreenOn = z;
            return this;
        }

        public Builder setPopupCallback(XPopupCallback xPopupCallback) {
            this.popupInfo.xPopupCallback = xPopupCallback;
            return this;
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnConfirmListener onConfirmListener, OnCancelListener onCancelListener, boolean z, int i) {
            popupType(PopupType.Center);
            ConfirmPopupView confirmPopupView = new ConfirmPopupView(this.context, i);
            confirmPopupView.setTitleContent(charSequence, charSequence2, null);
            confirmPopupView.setCancelText(charSequence3);
            confirmPopupView.setConfirmText(charSequence4);
            confirmPopupView.setListener(onConfirmListener, onCancelListener);
            confirmPopupView.isHideCancel = z;
            confirmPopupView.popupInfo = this.popupInfo;
            return confirmPopupView;
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnConfirmListener onConfirmListener, OnCancelListener onCancelListener, boolean z) {
            return asConfirm(charSequence, charSequence2, charSequence3, charSequence4, onConfirmListener, onCancelListener, z, 0);
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, OnConfirmListener onConfirmListener, OnCancelListener onCancelListener) {
            return asConfirm(charSequence, charSequence2, null, null, onConfirmListener, onCancelListener, false, 0);
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, OnConfirmListener onConfirmListener) {
            return asConfirm(charSequence, charSequence2, null, null, onConfirmListener, null, false, 0);
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnInputConfirmListener onInputConfirmListener, OnCancelListener onCancelListener, int i) {
            popupType(PopupType.Center);
            InputConfirmPopupView inputConfirmPopupView = new InputConfirmPopupView(this.context, i);
            inputConfirmPopupView.setTitleContent(charSequence, charSequence2, charSequence4);
            inputConfirmPopupView.inputContent = charSequence3;
            inputConfirmPopupView.setListener(onInputConfirmListener, onCancelListener);
            inputConfirmPopupView.popupInfo = this.popupInfo;
            return inputConfirmPopupView;
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnInputConfirmListener onInputConfirmListener) {
            return asInputConfirm(charSequence, charSequence2, charSequence3, charSequence4, onInputConfirmListener, null, 0);
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, OnInputConfirmListener onInputConfirmListener) {
            return asInputConfirm(charSequence, charSequence2, null, charSequence3, onInputConfirmListener, null, 0);
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, OnInputConfirmListener onInputConfirmListener) {
            return asInputConfirm(charSequence, charSequence2, null, null, onInputConfirmListener, null, 0);
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, int[] iArr, int i, OnSelectListener onSelectListener, int i2, int i3) {
            popupType(PopupType.Center);
            CenterListPopupView onSelectListener2 = new CenterListPopupView(this.context, i2, i3).setStringData(charSequence, strArr, iArr).setCheckedPosition(i).setOnSelectListener(onSelectListener);
            onSelectListener2.popupInfo = this.popupInfo;
            return onSelectListener2;
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, int[] iArr, int i, OnSelectListener onSelectListener) {
            return asCenterList(charSequence, strArr, iArr, i, onSelectListener, 0, 0);
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, OnSelectListener onSelectListener) {
            return asCenterList(charSequence, strArr, null, -1, onSelectListener);
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, int[] iArr, OnSelectListener onSelectListener) {
            return asCenterList(charSequence, strArr, iArr, -1, onSelectListener);
        }

        public LoadingPopupView asLoading(CharSequence charSequence, int i) {
            popupType(PopupType.Center);
            LoadingPopupView title = new LoadingPopupView(this.context, i).setTitle(charSequence);
            title.popupInfo = this.popupInfo;
            return title;
        }

        public LoadingPopupView asLoading(CharSequence charSequence) {
            return asLoading(charSequence, 0);
        }

        public LoadingPopupView asLoading() {
            return asLoading(null);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, int i, boolean z, OnSelectListener onSelectListener, int i2, int i3) {
            popupType(PopupType.Bottom);
            BottomListPopupView onSelectListener2 = new BottomListPopupView(this.context, i2, i3).setStringData(charSequence, strArr, iArr).setCheckedPosition(i).setOnSelectListener(onSelectListener);
            onSelectListener2.popupInfo = this.popupInfo;
            return onSelectListener2;
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, int i, boolean z, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, i, z, onSelectListener, 0, 0);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, null, -1, true, onSelectListener);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, -1, true, onSelectListener);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, int i, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, i, true, onSelectListener);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, boolean z, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, -1, z, onSelectListener);
        }

        public AttachListPopupView asAttachList(String[] strArr, int[] iArr, OnSelectListener onSelectListener, int i, int i2, int i3) {
            popupType(PopupType.AttachView);
            AttachListPopupView onSelectListener2 = new AttachListPopupView(this.context, i, i2).setStringData(strArr, iArr).setContentGravity(i3).setOnSelectListener(onSelectListener);
            onSelectListener2.popupInfo = this.popupInfo;
            return onSelectListener2;
        }

        public AttachListPopupView asAttachList(String[] strArr, int[] iArr, OnSelectListener onSelectListener, int i, int i2) {
            return asAttachList(strArr, iArr, onSelectListener, i, i2, 17);
        }

        public AttachListPopupView asAttachList(String[] strArr, int[] iArr, OnSelectListener onSelectListener) {
            return asAttachList(strArr, iArr, onSelectListener, 0, 0, 17);
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, Object obj, XPopupImageLoader xPopupImageLoader) {
            popupType(PopupType.ImageViewer);
            ImageViewerPopupView xPopupImageLoader2 = new ImageViewerPopupView(this.context).setSingleSrcView(imageView, obj).setXPopupImageLoader(xPopupImageLoader);
            xPopupImageLoader2.popupInfo = this.popupInfo;
            return xPopupImageLoader2;
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, Object obj, boolean z, int i, int i2, int i3, boolean z2, int i4, XPopupImageLoader xPopupImageLoader, OnImageViewerLongPressListener onImageViewerLongPressListener) {
            popupType(PopupType.ImageViewer);
            ImageViewerPopupView longPressListener = new ImageViewerPopupView(this.context).setSingleSrcView(imageView, obj).isInfinite(z).setPlaceholderColor(i).setPlaceholderStrokeColor(i2).setPlaceholderRadius(i3).isShowSaveButton(z2).setBgColor(i4).setXPopupImageLoader(xPopupImageLoader).setLongPressListener(onImageViewerLongPressListener);
            longPressListener.popupInfo = this.popupInfo;
            return longPressListener;
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, int i, List<Object> list, OnSrcViewUpdateListener onSrcViewUpdateListener, XPopupImageLoader xPopupImageLoader) {
            return asImageViewer(imageView, i, list, false, true, -1, -1, -1, true, Color.rgb(32, 36, 46), onSrcViewUpdateListener, xPopupImageLoader, null);
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, int i, List<Object> list, boolean z, boolean z2, int i2, int i3, int i4, boolean z3, int i5, OnSrcViewUpdateListener onSrcViewUpdateListener, XPopupImageLoader xPopupImageLoader, OnImageViewerLongPressListener onImageViewerLongPressListener) {
            popupType(PopupType.ImageViewer);
            ImageViewerPopupView longPressListener = new ImageViewerPopupView(this.context).setSrcView(imageView, i).setImageUrls(list).isInfinite(z).isShowPlaceholder(z2).setPlaceholderColor(i2).setPlaceholderStrokeColor(i3).setPlaceholderRadius(i4).isShowSaveButton(z3).setBgColor(i5).setSrcViewUpdateListener(onSrcViewUpdateListener).setXPopupImageLoader(xPopupImageLoader).setLongPressListener(onImageViewerLongPressListener);
            longPressListener.popupInfo = this.popupInfo;
            return longPressListener;
        }

        public BasePopupView asCustom(BasePopupView basePopupView) {
            if (basePopupView instanceof CenterPopupView) {
                popupType(PopupType.Center);
            } else if (basePopupView instanceof BottomPopupView) {
                popupType(PopupType.Bottom);
            } else if (basePopupView instanceof AttachPopupView) {
                popupType(PopupType.AttachView);
            } else if (basePopupView instanceof ImageViewerPopupView) {
                popupType(PopupType.ImageViewer);
            } else if (basePopupView instanceof PositionPopupView) {
                popupType(PopupType.Position);
            }
            basePopupView.popupInfo = this.popupInfo;
            return basePopupView;
        }
    }

    public static void requestOverlayPermission(Context context, XPermission.SimpleCallback simpleCallback) {
        XPermission.create(context, new String[0]).requestDrawOverlays(simpleCallback);
    }
}
