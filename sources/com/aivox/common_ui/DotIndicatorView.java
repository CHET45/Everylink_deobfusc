package com.aivox.common_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;
import com.blankj.utilcode.util.SizeUtils;

/* JADX INFO: loaded from: classes.dex */
public class DotIndicatorView extends LinearLayout {
    private final Context mContext;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DotIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        setOrientation(0);
    }

    public void bindWithViewPager(ViewPager viewPager) {
        int i = 0;
        final int count = viewPager.getAdapter() == null ? 0 : viewPager.getAdapter().getCount();
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(16.0f), SizeUtils.dp2px(16.0f));
        layoutParams.setMarginStart(SizeUtils.dp2px(4.0f));
        layoutParams.setMarginEnd(SizeUtils.dp2px(4.0f));
        while (i < count) {
            ImageView imageView = new ImageView(this.mContext);
            imageView.setImageResource(i == 0 ? C1034R.drawable.bg_dot_highlight : C1034R.drawable.bg_dot_normal);
            imageView.setLayoutParams(layoutParams);
            addView(imageView);
            i++;
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.aivox.common_ui.DotIndicatorView.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                DotIndicatorView.this.removeAllViews();
                int i3 = 0;
                while (i3 < count) {
                    ImageView imageView2 = new ImageView(DotIndicatorView.this.mContext);
                    imageView2.setImageResource(i3 == i2 ? C1034R.drawable.bg_dot_highlight : C1034R.drawable.bg_dot_normal);
                    imageView2.setLayoutParams(layoutParams);
                    DotIndicatorView.this.addView(imageView2);
                    i3++;
                }
            }
        });
    }
}
