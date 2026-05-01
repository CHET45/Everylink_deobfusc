package com.lxj.xpopup.core;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.BlurAnimator;
import com.lxj.xpopup.animator.EmptyAnimator;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.animator.ShadowBgAnimator;
import com.lxj.xpopup.animator.TranslateAlphaAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BasePopupView extends FrameLayout implements LifecycleObserver {
    private final Runnable attachTask;
    protected BlurAnimator blurAnimator;
    public FullScreenDialog dialog;
    Runnable dismissWithRunnable;
    protected Runnable doAfterDismissTask;
    protected Runnable doAfterShowTask;
    protected Handler handler;
    private boolean hasModifySoftMode;
    private boolean hasMoveUp;
    private final Runnable initTask;
    protected boolean isCreated;
    protected PopupAnimator popupContentAnimator;
    public PopupInfo popupInfo;
    public PopupStatus popupStatus;
    private int preSoftMode;
    protected ShadowBgAnimator shadowBgAnimator;
    private ShowSoftInputTask showSoftInputTask;
    private final int touchSlop;

    /* JADX INFO: renamed from: x */
    private float f856x;

    /* JADX INFO: renamed from: y */
    private float f857y;

    protected void applyDarkTheme() {
    }

    protected void applyLightTheme() {
    }

    protected void beforeDismiss() {
    }

    protected void beforeShow() {
    }

    protected int getImplLayoutId() {
        return -1;
    }

    protected abstract int getInnerLayoutId();

    protected List<String> getInternalFragmentNames() {
        return null;
    }

    protected PopupAnimator getPopupAnimator() {
        return null;
    }

    protected void initPopupContent() {
    }

    protected void onCreate() {
    }

    protected void onDismiss() {
    }

    protected void onKeyboardHeightChange(int i) {
    }

    protected void onShow() {
    }

    public BasePopupView(Context context) {
        super(context);
        this.popupStatus = PopupStatus.Dismiss;
        this.isCreated = false;
        this.hasModifySoftMode = false;
        this.preSoftMode = -1;
        this.hasMoveUp = false;
        this.handler = new Handler(Looper.getMainLooper());
        this.attachTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.1
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.attachToHost();
                KeyboardUtils.registerSoftInputChangedListener(BasePopupView.this.getHostWindow(), BasePopupView.this, new KeyboardUtils.OnSoftInputChangedListener() { // from class: com.lxj.xpopup.core.BasePopupView.1.1
                    @Override // com.lxj.xpopup.util.KeyboardUtils.OnSoftInputChangedListener
                    public void onSoftInputChanged(int i) {
                        BasePopupView.this.onKeyboardHeightChange(i);
                        if (BasePopupView.this.popupInfo != null && BasePopupView.this.popupInfo.xPopupCallback != null) {
                            BasePopupView.this.popupInfo.xPopupCallback.onKeyBoardStateChanged(BasePopupView.this, i);
                        }
                        if (i != 0) {
                            if (BasePopupView.this.hasMoveUp) {
                                return;
                            }
                            if ((BasePopupView.this instanceof FullScreenPopupView) && BasePopupView.this.popupStatus == PopupStatus.Showing) {
                                return;
                            }
                            if ((BasePopupView.this instanceof PartShadowPopupView) && BasePopupView.this.popupStatus == PopupStatus.Showing) {
                                return;
                            }
                            XPopupUtils.moveUpToKeyboard(i, BasePopupView.this);
                            BasePopupView.this.hasMoveUp = true;
                            return;
                        }
                        XPopupUtils.moveDown(BasePopupView.this);
                        BasePopupView.this.hasMoveUp = false;
                    }
                });
                BasePopupView.this.init();
            }
        };
        this.initTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.2
            @Override // java.lang.Runnable
            public void run() {
                if (BasePopupView.this.getHostWindow() == null) {
                    return;
                }
                if (BasePopupView.this.popupInfo.xPopupCallback != null) {
                    BasePopupView.this.popupInfo.xPopupCallback.beforeShow(BasePopupView.this);
                }
                BasePopupView.this.beforeShow();
                BasePopupView basePopupView = BasePopupView.this;
                if (!(basePopupView instanceof FullScreenPopupView)) {
                    basePopupView.focusAndProcessBackPress();
                }
                BasePopupView basePopupView2 = BasePopupView.this;
                if ((basePopupView2 instanceof AttachPopupView) || (basePopupView2 instanceof BubbleAttachPopupView) || (basePopupView2 instanceof PositionPopupView) || (basePopupView2 instanceof PartShadowPopupView)) {
                    return;
                }
                basePopupView2.initAnimator();
                BasePopupView.this.doShowAnimation();
                BasePopupView.this.doAfterShow();
            }
        };
        this.doAfterShowTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.3
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.popupStatus = PopupStatus.Show;
                BasePopupView.this.onShow();
                BasePopupView basePopupView = BasePopupView.this;
                if (basePopupView instanceof FullScreenPopupView) {
                    basePopupView.focusAndProcessBackPress();
                }
                if (BasePopupView.this.popupInfo != null && BasePopupView.this.popupInfo.xPopupCallback != null) {
                    BasePopupView.this.popupInfo.xPopupCallback.onShow(BasePopupView.this);
                }
                if (BasePopupView.this.getHostWindow() == null || XPopupUtils.getDecorViewInvisibleHeight(BasePopupView.this.getHostWindow()) <= 0 || BasePopupView.this.hasMoveUp) {
                    return;
                }
                XPopupUtils.moveUpToKeyboard(XPopupUtils.getDecorViewInvisibleHeight(BasePopupView.this.getHostWindow()), BasePopupView.this);
            }
        };
        this.doAfterDismissTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.8
            @Override // java.lang.Runnable
            public void run() {
                View viewFindViewById;
                BasePopupView.this.popupStatus = PopupStatus.Dismiss;
                if (BasePopupView.this.popupInfo == null) {
                    return;
                }
                if (BasePopupView.this.popupInfo.autoOpenSoftInput.booleanValue()) {
                    BasePopupView basePopupView = BasePopupView.this;
                    if (basePopupView instanceof PartShadowPopupView) {
                        KeyboardUtils.hideSoftInput(basePopupView);
                    }
                }
                BasePopupView.this.onDismiss();
                XPopup.longClickPoint = null;
                if (BasePopupView.this.popupInfo.xPopupCallback != null) {
                    BasePopupView.this.popupInfo.xPopupCallback.onDismiss(BasePopupView.this);
                }
                if (BasePopupView.this.dismissWithRunnable != null) {
                    BasePopupView.this.dismissWithRunnable.run();
                    BasePopupView.this.dismissWithRunnable = null;
                }
                if (BasePopupView.this.popupInfo.isRequestFocus && BasePopupView.this.popupInfo.isViewMode && BasePopupView.this.getWindowDecorView() != null && (viewFindViewById = BasePopupView.this.getWindowDecorView().findViewById(R.id.content)) != null) {
                    viewFindViewById.setFocusable(true);
                    viewFindViewById.setFocusableInTouchMode(true);
                }
                BasePopupView.this.detachFromHost();
            }
        };
        if (context instanceof Application) {
            throw new IllegalArgumentException("XPopup的Context必须是Activity类型！");
        }
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        View viewInflate = LayoutInflater.from(context).inflate(getInnerLayoutId(), (ViewGroup) this, false);
        viewInflate.setAlpha(0.0f);
        addView(viewInflate);
    }

    public BasePopupView show() {
        FullScreenDialog fullScreenDialog;
        Activity activityContext2Activity = XPopupUtils.context2Activity(this);
        if (activityContext2Activity != null && !activityContext2Activity.isFinishing() && this.popupInfo != null && this.popupStatus != PopupStatus.Showing && this.popupStatus != PopupStatus.Dismissing) {
            this.popupStatus = PopupStatus.Showing;
            if (this.popupInfo.isRequestFocus) {
                KeyboardUtils.hideSoftInput(activityContext2Activity.getWindow());
            }
            if (!this.popupInfo.isViewMode && (fullScreenDialog = this.dialog) != null && fullScreenDialog.isShowing()) {
                return this;
            }
            this.handler.post(this.attachTask);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachToHost() {
        if (this.popupInfo == null) {
            throw new IllegalArgumentException("如果弹窗对象是复用的，则不要设置isDestroyOnDismiss(true)");
        }
        if (getContext() instanceof FragmentActivity) {
            ((FragmentActivity) getContext()).getLifecycle().addObserver(this);
        }
        if (this.popupInfo.isViewMode) {
            ViewGroup viewGroup = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
            View viewFindViewById = viewGroup.findViewById(R.id.navigationBarBackground);
            int measuredHeight = viewFindViewById != null ? viewFindViewById.getMeasuredHeight() : 0;
            if (getParent() != null) {
                ((ViewGroup) getParent()).removeView(this);
            }
            viewGroup.addView(this, new FrameLayout.LayoutParams(-1, viewGroup.getMeasuredHeight() - measuredHeight));
            return;
        }
        if (this.dialog == null) {
            this.dialog = new FullScreenDialog(getContext()).setContent(this);
        }
        this.dialog.show();
    }

    protected View getWindowDecorView() {
        if (getHostWindow() == null) {
            return null;
        }
        return (ViewGroup) getHostWindow().getDecorView();
    }

    protected void init() {
        if (this.shadowBgAnimator == null) {
            this.shadowBgAnimator = new ShadowBgAnimator(this, getAnimationDuration(), getShadowBgColor());
        }
        if (this.popupInfo.hasBlurBg.booleanValue()) {
            BlurAnimator blurAnimator = new BlurAnimator(this, getShadowBgColor());
            this.blurAnimator = blurAnimator;
            blurAnimator.hasShadowBg = this.popupInfo.hasShadowBg.booleanValue();
            this.blurAnimator.decorBitmap = XPopupUtils.view2Bitmap(XPopupUtils.context2Activity(this).getWindow().getDecorView());
        }
        if ((this instanceof AttachPopupView) || (this instanceof BubbleAttachPopupView) || (this instanceof PartShadowPopupView) || (this instanceof PositionPopupView) || !this.isCreated) {
            initPopupContent();
        }
        if (!this.isCreated) {
            this.isCreated = true;
            onCreate();
            if (this.popupInfo.xPopupCallback != null) {
                this.popupInfo.xPopupCallback.onCreated(this);
            }
        }
        this.handler.postDelayed(this.initTask, 10L);
    }

    protected void initAnimator() {
        BlurAnimator blurAnimator;
        getPopupContentView().setAlpha(1.0f);
        if (this.popupInfo.customAnimator != null) {
            PopupAnimator popupAnimator = this.popupInfo.customAnimator;
            this.popupContentAnimator = popupAnimator;
            popupAnimator.targetView = getPopupContentView();
        } else {
            PopupAnimator popupAnimatorGenAnimatorByPopupType = genAnimatorByPopupType();
            this.popupContentAnimator = popupAnimatorGenAnimatorByPopupType;
            if (popupAnimatorGenAnimatorByPopupType == null) {
                this.popupContentAnimator = getPopupAnimator();
            }
        }
        if (this.popupInfo.hasShadowBg.booleanValue()) {
            this.shadowBgAnimator.initAnimator();
        }
        if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.initAnimator();
        }
        PopupAnimator popupAnimator2 = this.popupContentAnimator;
        if (popupAnimator2 != null) {
            popupAnimator2.initAnimator();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detachFromHost() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.isViewMode) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this);
                return;
            }
            return;
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog != null) {
            fullScreenDialog.dismiss();
        }
    }

    public Window getHostWindow() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.isViewMode) {
            return ((Activity) getContext()).getWindow();
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog == null) {
            return null;
        }
        return fullScreenDialog.getWindow();
    }

    protected void doAfterShow() {
        this.handler.removeCallbacks(this.doAfterShowTask);
        this.handler.postDelayed(this.doAfterShowTask, getAnimationDuration());
    }

    public void focusAndProcessBackPress() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.isRequestFocus) {
            return;
        }
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();
        if (Build.VERSION.SDK_INT >= 28) {
            addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.lxj.xpopup.core.BasePopupView.4
                @Override // android.view.View.OnUnhandledKeyEventListener
                public boolean onUnhandledKeyEvent(View view2, KeyEvent keyEvent) {
                    return BasePopupView.this.processKeyEvent(keyEvent.getKeyCode(), keyEvent);
                }
            });
        } else {
            setOnKeyListener(new BackPressListener());
        }
        ArrayList arrayList = new ArrayList();
        XPopupUtils.findAllEditText(arrayList, (ViewGroup) getPopupContentView());
        if (arrayList.size() > 0) {
            if (this.popupInfo.isViewMode) {
                this.preSoftMode = getHostWindow().getAttributes().softInputMode;
                getHostWindow().setSoftInputMode(16);
                this.hasModifySoftMode = true;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                EditText editText = (EditText) arrayList.get(i);
                if (Build.VERSION.SDK_INT >= 28) {
                    editText.addOnUnhandledKeyEventListener(new View.OnUnhandledKeyEventListener() { // from class: com.lxj.xpopup.core.BasePopupView.5
                        @Override // android.view.View.OnUnhandledKeyEventListener
                        public boolean onUnhandledKeyEvent(View view2, KeyEvent keyEvent) {
                            return BasePopupView.this.processKeyEvent(keyEvent.getKeyCode(), keyEvent);
                        }
                    });
                } else if (!XPopupUtils.hasSetKeyListener(editText)) {
                    editText.setOnKeyListener(new BackPressListener());
                }
                if (i == 0) {
                    if (this.popupInfo.autoFocusEditText) {
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
                            showSoftInput(editText);
                        }
                    } else if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
                        showSoftInput(this);
                    }
                }
            }
            return;
        }
        if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
            showSoftInput(this);
        }
    }

    protected void showSoftInput(View view2) {
        if (this.popupInfo != null) {
            ShowSoftInputTask showSoftInputTask = this.showSoftInputTask;
            if (showSoftInputTask == null) {
                this.showSoftInputTask = new ShowSoftInputTask(view2);
            } else {
                this.handler.removeCallbacks(showSoftInputTask);
            }
            this.handler.postDelayed(this.showSoftInputTask, 10L);
        }
    }

    public void dismissOrHideSoftInput() {
        if (KeyboardUtils.sDecorViewInvisibleHeightPre == 0) {
            dismiss();
        } else {
            KeyboardUtils.hideSoftInput(this);
        }
    }

    static class ShowSoftInputTask implements Runnable {
        View focusView;

        public ShowSoftInputTask(View view2) {
            this.focusView = view2;
        }

        @Override // java.lang.Runnable
        public void run() {
            View view2 = this.focusView;
            if (view2 != null) {
                KeyboardUtils.showSoftInput(view2);
            }
        }
    }

    protected boolean processKeyEvent(int i, KeyEvent keyEvent) {
        PopupInfo popupInfo;
        if (i != 4 || keyEvent.getAction() != 1 || (popupInfo = this.popupInfo) == null) {
            return false;
        }
        if (popupInfo.isDismissOnBackPressed.booleanValue() && (this.popupInfo.xPopupCallback == null || !this.popupInfo.xPopupCallback.onBackPressed(this))) {
            dismissOrHideSoftInput();
        }
        return true;
    }

    class BackPressListener implements View.OnKeyListener {
        BackPressListener() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view2, int i, KeyEvent keyEvent) {
            return BasePopupView.this.processKeyEvent(i, keyEvent);
        }
    }

    /* JADX INFO: renamed from: com.lxj.xpopup.core.BasePopupView$9 */
    static /* synthetic */ class C22409 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.ScaleAlphaFromCenter.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightTop.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightBottom.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromLeft.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromTop.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromRight.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromBottom.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromLeft.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromTop.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromRight.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromBottom.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeft.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftTop.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromTop.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightTop.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRight.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightBottom.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromBottom.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftBottom.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.NoAnimation.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
        }
    }

    protected PopupAnimator genAnimatorByPopupType() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || popupInfo.popupAnimation == null) {
            return null;
        }
        switch (C22409.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[this.popupInfo.popupAnimation.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 6:
            case 7:
            case 8:
            case 9:
                return new TranslateAlphaAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 10:
            case 11:
            case 12:
            case 13:
                return new TranslateAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 22:
                return new EmptyAnimator(getPopupContentView(), getAnimationDuration());
            default:
                return null;
        }
    }

    protected void doShowAnimation() {
        BlurAnimator blurAnimator;
        ShadowBgAnimator shadowBgAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.hasShadowBg.booleanValue() && !this.popupInfo.hasBlurBg.booleanValue() && (shadowBgAnimator = this.shadowBgAnimator) != null) {
            shadowBgAnimator.animateShow();
        } else if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateShow();
        }
        PopupAnimator popupAnimator = this.popupContentAnimator;
        if (popupAnimator != null) {
            popupAnimator.animateShow();
        }
    }

    protected void doDismissAnimation() {
        BlurAnimator blurAnimator;
        ShadowBgAnimator shadowBgAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.hasShadowBg.booleanValue() && !this.popupInfo.hasBlurBg.booleanValue() && (shadowBgAnimator = this.shadowBgAnimator) != null) {
            shadowBgAnimator.animateDismiss();
        } else if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateDismiss();
        }
        PopupAnimator popupAnimator = this.popupContentAnimator;
        if (popupAnimator != null) {
            popupAnimator.animateDismiss();
        }
    }

    public View getPopupContentView() {
        return getChildAt(0);
    }

    public View getPopupImplView() {
        return ((ViewGroup) getPopupContentView()).getChildAt(0);
    }

    public int getAnimationDuration() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        if (popupInfo.popupAnimation == PopupAnimation.NoAnimation) {
            return 1;
        }
        return this.popupInfo.animationDuration >= 0 ? this.popupInfo.animationDuration : XPopup.getAnimationDuration() + 1;
    }

    public int getShadowBgColor() {
        PopupInfo popupInfo = this.popupInfo;
        return (popupInfo == null || popupInfo.shadowBgColor == 0) ? XPopup.getShadowBgColor() : this.popupInfo.shadowBgColor;
    }

    public int getStatusBarBgColor() {
        PopupInfo popupInfo = this.popupInfo;
        return (popupInfo == null || popupInfo.statusBarBgColor == 0) ? XPopup.getStatusBarBgColor() : this.popupInfo.statusBarBgColor;
    }

    protected int getMaxWidth() {
        return this.popupInfo.maxWidth;
    }

    protected int getMaxHeight() {
        return this.popupInfo.maxHeight;
    }

    protected int getPopupWidth() {
        return this.popupInfo.popupWidth;
    }

    protected int getPopupHeight() {
        return this.popupInfo.popupHeight;
    }

    public void dismiss() {
        this.handler.removeCallbacks(this.attachTask);
        this.handler.removeCallbacks(this.initTask);
        if (this.popupStatus == PopupStatus.Dismissing || this.popupStatus == PopupStatus.Dismiss) {
            return;
        }
        this.popupStatus = PopupStatus.Dismissing;
        clearFocus();
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.xPopupCallback != null) {
            this.popupInfo.xPopupCallback.beforeDismiss(this);
        }
        beforeDismiss();
        doDismissAnimation();
        doAfterDismiss();
    }

    public void smartDismiss() {
        this.handler.post(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.6
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.delayDismiss(r0.getAnimationDuration() + 50);
            }
        });
    }

    public void delayDismiss(long j) {
        if (j < 0) {
            j = 0;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.7
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.dismiss();
            }
        }, j);
    }

    public void delayDismissWith(long j, Runnable runnable) {
        this.dismissWithRunnable = runnable;
        delayDismiss(j);
    }

    protected void doAfterDismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.autoOpenSoftInput.booleanValue() && !(this instanceof PartShadowPopupView)) {
            KeyboardUtils.hideSoftInput(this);
        }
        this.handler.removeCallbacks(this.doAfterDismissTask);
        this.handler.postDelayed(this.doAfterDismissTask, getAnimationDuration());
    }

    public void dismissWith(Runnable runnable) {
        this.dismissWithRunnable = runnable;
        dismiss();
    }

    public boolean isShow() {
        return this.popupStatus != PopupStatus.Dismiss;
    }

    public boolean isDismiss() {
        return this.popupStatus == PopupStatus.Dismiss;
    }

    public void toggle() {
        if (isShow()) {
            dismiss();
        } else {
            show();
        }
    }

    protected void tryRemoveFragments() {
        if (getContext() instanceof FragmentActivity) {
            FragmentManager supportFragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
            List<Fragment> fragments = supportFragmentManager.getFragments();
            List<String> internalFragmentNames = getInternalFragmentNames();
            if (fragments == null || fragments.size() <= 0 || internalFragmentNames == null) {
                return;
            }
            for (int i = 0; i < fragments.size(); i++) {
                if (internalFragmentNames.contains(fragments.get(i).getClass().getSimpleName())) {
                    supportFragmentManager.beginTransaction().remove(fragments.get(i)).commitAllowingStateLoss();
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        onDetachedFromWindow();
        detachFromHost();
        destroy();
    }

    public void destroy() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null) {
            popupInfo.atView = null;
            this.popupInfo.watchView = null;
            this.popupInfo.xPopupCallback = null;
            if (this.popupInfo.customAnimator != null && this.popupInfo.customAnimator.targetView != null) {
                this.popupInfo.customAnimator.targetView.animate().cancel();
            }
            if (this.popupInfo.isViewMode) {
                tryRemoveFragments();
            }
            if (this.popupInfo.isDestroyOnDismiss) {
                this.popupInfo = null;
            }
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog != null) {
            fullScreenDialog.contentView = null;
            this.dialog = null;
        }
        ShadowBgAnimator shadowBgAnimator = this.shadowBgAnimator;
        if (shadowBgAnimator != null && shadowBgAnimator.targetView != null) {
            this.shadowBgAnimator.targetView.animate().cancel();
        }
        BlurAnimator blurAnimator = this.blurAnimator;
        if (blurAnimator == null || blurAnimator.targetView == null) {
            return;
        }
        this.blurAnimator.targetView.animate().cancel();
        if (this.blurAnimator.decorBitmap == null || this.blurAnimator.decorBitmap.isRecycled()) {
            return;
        }
        this.blurAnimator.decorBitmap.recycle();
        this.blurAnimator.decorBitmap = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeCallbacksAndMessages(null);
        if (this.popupInfo != null) {
            if (getWindowDecorView() != null) {
                KeyboardUtils.removeLayoutChangeListener(getWindowDecorView(), this);
            }
            if (this.popupInfo.isViewMode && this.hasModifySoftMode) {
                getHostWindow().setSoftInputMode(this.preSoftMode);
                this.hasModifySoftMode = false;
            }
            if (this.popupInfo.isDestroyOnDismiss) {
                destroy();
            }
        }
        if (getContext() != null && (getContext() instanceof FragmentActivity)) {
            ((FragmentActivity) getContext()).getLifecycle().removeObserver(this);
        }
        this.popupStatus = PopupStatus.Dismiss;
        this.showSoftInputTask = null;
        this.hasMoveUp = false;
    }

    private void passClickThrough(MotionEvent motionEvent) {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.isClickThrough) {
            return;
        }
        if (this.popupInfo.isViewMode) {
            ViewGroup viewGroup = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt != this) {
                    childAt.dispatchTouchEvent(motionEvent);
                }
            }
            return;
        }
        ((Activity) getContext()).dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        PopupInfo popupInfo;
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        getPopupContentView().getGlobalVisibleRect(rect);
        if (!XPopupUtils.isInRect(motionEvent.getX(), motionEvent.getY(), rect)) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.f856x = motionEvent.getX();
                this.f857y = motionEvent.getY();
                passClickThrough(motionEvent);
            } else if (action == 1 || action == 2 || action == 3) {
                float fSqrt = (float) Math.sqrt(Math.pow(motionEvent.getX() - this.f856x, 2.0d) + Math.pow(motionEvent.getY() - this.f857y, 2.0d));
                if (!XPopupUtils.isInRect(motionEvent.getX(), motionEvent.getY(), rect2)) {
                    passClickThrough(motionEvent);
                }
                if (fSqrt < this.touchSlop && (popupInfo = this.popupInfo) != null && popupInfo.isDismissOnTouchOutside.booleanValue()) {
                    dismiss();
                    getPopupImplView().getGlobalVisibleRect(rect2);
                }
                this.f856x = 0.0f;
                this.f857y = 0.0f;
            }
        }
        return true;
    }
}
