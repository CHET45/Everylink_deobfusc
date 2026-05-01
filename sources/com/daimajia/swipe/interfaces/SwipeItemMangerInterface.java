package com.daimajia.swipe.interfaces;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.util.Attributes;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface SwipeItemMangerInterface {
    void closeAllExcept(SwipeLayout swipeLayout);

    void closeAllItems();

    void closeItem(int i);

    Attributes.Mode getMode();

    List<Integer> getOpenItems();

    List<SwipeLayout> getOpenLayouts();

    boolean isOpen(int i);

    void openItem(int i);

    void removeShownLayouts(SwipeLayout swipeLayout);

    void setMode(Attributes.Mode mode);
}
