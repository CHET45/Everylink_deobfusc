package com.aivox.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MyPageAdapter extends PagerAdapter {
    private final Context context;
    private List<Integer> mImgResList;

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view2, Object obj) {
        return view2 == obj;
    }

    public MyPageAdapter(Context context, List<Integer> list) {
        new ArrayList();
        this.context = context;
        this.mImgResList = list;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mImgResList.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View viewInflate = LayoutInflater.from(this.context).inflate(C0958R.layout.page_item, viewGroup, false);
        ((ImageView) viewInflate.findViewById(C0958R.id.iv_item)).setImageResource(this.mImgResList.get(i).intValue());
        viewGroup.addView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}
