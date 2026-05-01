package com.app.hubert.guide.core;

import android.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.GuideLayout;
import com.app.hubert.guide.lifecycle.FragmentLifecycleAdapter;
import com.app.hubert.guide.lifecycle.ListenerFragment;
import com.app.hubert.guide.lifecycle.V4ListenerFragment;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.util.LogUtil;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Controller {
    private static final String LISTENER_FRAGMENT = "listener_fragment";
    private Activity activity;
    private boolean alwaysShow;
    private int current;
    private GuideLayout currentLayout;
    private Fragment fragment;
    private List<GuidePage> guidePages;
    private int indexOfChild;
    private String label;
    private FrameLayout mParentView;
    private OnGuideChangedListener onGuideChangedListener;
    private OnPageChangedListener onPageChangedListener;
    private int showCounts;

    /* JADX INFO: renamed from: sp */
    private SharedPreferences f350sp;
    private androidx.fragment.app.Fragment v4Fragment;

    public Controller(Builder builder) {
        this.indexOfChild = -1;
        this.activity = builder.activity;
        this.fragment = builder.fragment;
        this.v4Fragment = builder.v4Fragment;
        this.onGuideChangedListener = builder.onGuideChangedListener;
        this.onPageChangedListener = builder.onPageChangedListener;
        this.label = builder.label;
        this.alwaysShow = builder.alwaysShow;
        this.guidePages = builder.guidePages;
        this.showCounts = builder.showCounts;
        View viewFindViewById = builder.anchor;
        viewFindViewById = viewFindViewById == null ? this.activity.findViewById(R.id.content) : viewFindViewById;
        if (viewFindViewById instanceof FrameLayout) {
            this.mParentView = (FrameLayout) viewFindViewById;
        } else {
            FrameLayout frameLayout = new FrameLayout(this.activity);
            ViewGroup viewGroup = (ViewGroup) viewFindViewById.getParent();
            this.indexOfChild = viewGroup.indexOfChild(viewFindViewById);
            viewGroup.removeView(viewFindViewById);
            int i = this.indexOfChild;
            if (i >= 0) {
                viewGroup.addView(frameLayout, i, viewFindViewById.getLayoutParams());
            } else {
                viewGroup.addView(frameLayout, viewFindViewById.getLayoutParams());
            }
            frameLayout.addView(viewFindViewById, new ViewGroup.LayoutParams(-1, -1));
            this.mParentView = frameLayout;
        }
        this.f350sp = this.activity.getSharedPreferences(NewbieGuide.TAG, 0);
    }

    public void show() {
        final int i = this.f350sp.getInt(this.label, 0);
        if (this.alwaysShow || i < this.showCounts) {
            this.mParentView.post(new Runnable() { // from class: com.app.hubert.guide.core.Controller.1
                @Override // java.lang.Runnable
                public void run() {
                    if (Controller.this.guidePages != null && Controller.this.guidePages.size() != 0) {
                        Controller.this.current = 0;
                        Controller.this.showGuidePage();
                        if (Controller.this.onGuideChangedListener != null) {
                            Controller.this.onGuideChangedListener.onShowed(Controller.this);
                        }
                        Controller.this.addListenerFragment();
                        Controller.this.f350sp.edit().putInt(Controller.this.label, i + 1).apply();
                        return;
                    }
                    throw new IllegalStateException("there is no guide to show!! Please add at least one Page.");
                }
            });
        }
    }

    public void showPage(int i) {
        if (i < 0 || i > this.guidePages.size() - 1) {
            throw new InvalidParameterException("The Guide page position is out of range. current:" + i + ", range: [ 0, " + this.guidePages.size() + " )");
        }
        if (this.current == i) {
            return;
        }
        this.current = i;
        this.currentLayout.setOnGuideLayoutDismissListener(new GuideLayout.OnGuideLayoutDismissListener() { // from class: com.app.hubert.guide.core.Controller.2
            @Override // com.app.hubert.guide.core.GuideLayout.OnGuideLayoutDismissListener
            public void onGuideLayoutDismiss(GuideLayout guideLayout) {
                Controller.this.showGuidePage();
            }
        });
        this.currentLayout.remove();
    }

    public void showPreviewPage() {
        int i = this.current - 1;
        this.current = i;
        showPage(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGuidePage() {
        GuideLayout guideLayout = new GuideLayout(this.activity, this.guidePages.get(this.current), this);
        guideLayout.setOnGuideLayoutDismissListener(new GuideLayout.OnGuideLayoutDismissListener() { // from class: com.app.hubert.guide.core.Controller.3
            @Override // com.app.hubert.guide.core.GuideLayout.OnGuideLayoutDismissListener
            public void onGuideLayoutDismiss(GuideLayout guideLayout2) {
                Controller.this.showNextOrRemove();
            }
        });
        this.mParentView.addView(guideLayout, new FrameLayout.LayoutParams(-1, -1));
        this.currentLayout = guideLayout;
        OnPageChangedListener onPageChangedListener = this.onPageChangedListener;
        if (onPageChangedListener != null) {
            onPageChangedListener.onPageChanged(this.current);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNextOrRemove() {
        if (this.current < this.guidePages.size() - 1) {
            this.current++;
            showGuidePage();
        } else {
            OnGuideChangedListener onGuideChangedListener = this.onGuideChangedListener;
            if (onGuideChangedListener != null) {
                onGuideChangedListener.onRemoved(this);
            }
            removeListenerFragment();
        }
    }

    public void resetLabel() {
        resetLabel(this.label);
    }

    public void resetLabel(String str) {
        this.f350sp.edit().putInt(str, 0).apply();
    }

    public void remove() {
        GuideLayout guideLayout = this.currentLayout;
        if (guideLayout != null && guideLayout.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) this.currentLayout.getParent();
            viewGroup.removeView(this.currentLayout);
            if (!(viewGroup instanceof FrameLayout)) {
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
                View childAt = viewGroup.getChildAt(0);
                viewGroup.removeAllViews();
                if (childAt != null) {
                    int i = this.indexOfChild;
                    if (i > 0) {
                        viewGroup2.addView(childAt, i, viewGroup.getLayoutParams());
                    } else {
                        viewGroup2.addView(childAt, viewGroup.getLayoutParams());
                    }
                }
            }
        }
        OnGuideChangedListener onGuideChangedListener = this.onGuideChangedListener;
        if (onGuideChangedListener != null) {
            onGuideChangedListener.onRemoved(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addListenerFragment() {
        if (this.fragment != null) {
            compatibleFragment(this.fragment);
            FragmentManager childFragmentManager = this.fragment.getChildFragmentManager();
            ListenerFragment listenerFragment = (ListenerFragment) childFragmentManager.findFragmentByTag(LISTENER_FRAGMENT);
            if (listenerFragment == null) {
                listenerFragment = new ListenerFragment();
                childFragmentManager.beginTransaction().add(listenerFragment, LISTENER_FRAGMENT).commitAllowingStateLoss();
            }
            listenerFragment.setFragmentLifecycle(new FragmentLifecycleAdapter() { // from class: com.app.hubert.guide.core.Controller.4
                @Override // com.app.hubert.guide.lifecycle.FragmentLifecycleAdapter, com.app.hubert.guide.lifecycle.FragmentLifecycle
                public void onDestroyView() {
                    LogUtil.m373i("ListenerFragment.onDestroyView");
                    Controller.this.remove();
                }
            });
        }
        androidx.fragment.app.Fragment fragment = this.v4Fragment;
        if (fragment != null) {
            androidx.fragment.app.FragmentManager childFragmentManager2 = fragment.getChildFragmentManager();
            V4ListenerFragment v4ListenerFragment = (V4ListenerFragment) childFragmentManager2.findFragmentByTag(LISTENER_FRAGMENT);
            if (v4ListenerFragment == null) {
                v4ListenerFragment = new V4ListenerFragment();
                childFragmentManager2.beginTransaction().add(v4ListenerFragment, LISTENER_FRAGMENT).commitAllowingStateLoss();
            }
            v4ListenerFragment.setFragmentLifecycle(new FragmentLifecycleAdapter() { // from class: com.app.hubert.guide.core.Controller.5
                @Override // com.app.hubert.guide.lifecycle.FragmentLifecycleAdapter, com.app.hubert.guide.lifecycle.FragmentLifecycle
                public void onDestroyView() {
                    LogUtil.m373i("v4ListenerFragment.onDestroyView");
                    Controller.this.remove();
                }
            });
        }
    }

    private void removeListenerFragment() {
        if (this.fragment != null) {
            FragmentManager childFragmentManager = this.fragment.getChildFragmentManager();
            ListenerFragment listenerFragment = (ListenerFragment) childFragmentManager.findFragmentByTag(LISTENER_FRAGMENT);
            if (listenerFragment != null) {
                childFragmentManager.beginTransaction().remove(listenerFragment).commitAllowingStateLoss();
            }
        }
        androidx.fragment.app.Fragment fragment = this.v4Fragment;
        if (fragment != null) {
            androidx.fragment.app.FragmentManager childFragmentManager2 = fragment.getChildFragmentManager();
            V4ListenerFragment v4ListenerFragment = (V4ListenerFragment) childFragmentManager2.findFragmentByTag(LISTENER_FRAGMENT);
            if (v4ListenerFragment != null) {
                childFragmentManager2.beginTransaction().remove(v4ListenerFragment).commitAllowingStateLoss();
            }
        }
    }

    private void compatibleFragment(Fragment fragment) {
        try {
            Field declaredField = Fragment.class.getDeclaredField("mChildFragmentManager");
            declaredField.setAccessible(true);
            declaredField.set(fragment, null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e2) {
            throw new RuntimeException(e2);
        }
    }
}
