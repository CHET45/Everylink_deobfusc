package com.aivox.common_ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.aivox.base.C0874R;
import com.aivox.common_ui.PowerfulEditText;
import com.blankj.utilcode.util.KeyboardUtils;

/* JADX INFO: loaded from: classes.dex */
public class HeadTitleLinearView extends LinearLayout {
    private LinearLayout activityBack;
    private PowerfulEditText etSearch;
    private ImageView leftIcon;
    private LinearLayout llSearch;
    private Toolbar mHeadRel;
    private ImageView rightIcon;
    private TextView rightName;
    private SwitchCompat rightSwitch;
    private ImageView rightTwoIcon;
    private TextView titleLeft;
    private ImageView titleLogo;
    private ScrollForeverTextView titleName;

    public HeadTitleLinearView(Context context) {
        this(context, null);
    }

    public HeadTitleLinearView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HeadTitleLinearView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        inflate(context, C1034R.layout.common_toolbar, this);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.HeadTitleLinearView);
        int color = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.HeadTitleLinearView_common_bg, getResources().getColor(android.R.color.transparent));
        int color2 = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.HeadTitleLinearView_common_title_color, getResources().getColor(C0874R.color.txt_color_primary));
        int color3 = typedArrayObtainStyledAttributes.getColor(C1034R.styleable.HeadTitleLinearView_common_right_txt_color, getResources().getColor(C0874R.color.gray_3));
        boolean z = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HeadTitleLinearView_common_back_visible, true);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HeadTitleLinearView_common_right_txt_isVisible, false);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HeadTitleLinearView_common_right_icon1_isVisible, false);
        boolean z4 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HeadTitleLinearView_common_right_icon2_isVisible, false);
        boolean z5 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HeadTitleLinearView_common_search_isVisible, false);
        boolean z6 = typedArrayObtainStyledAttributes.getBoolean(C1034R.styleable.HeadTitleLinearView_common_show_switch, false);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(C1034R.styleable.HeadTitleLinearView_common_left_img_ids);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(C1034R.styleable.HeadTitleLinearView_common_right_icon1_ids);
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(C1034R.styleable.HeadTitleLinearView_common_right_icon2_ids);
        String string = typedArrayObtainStyledAttributes.getString(C1034R.styleable.HeadTitleLinearView_common_right_txt);
        String string2 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.HeadTitleLinearView_common_title_txt);
        String string3 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.HeadTitleLinearView_common_search_hint);
        String string4 = typedArrayObtainStyledAttributes.getString(C1034R.styleable.HeadTitleLinearView_common_search_btn_txt);
        typedArrayObtainStyledAttributes.recycle();
        this.rightSwitch = (SwitchCompat) findViewById(C1034R.id.right_switch);
        this.activityBack = (LinearLayout) findViewById(C1034R.id.lly_back);
        this.leftIcon = (ImageView) findViewById(C1034R.id.imgBack);
        this.titleLeft = (TextView) findViewById(C1034R.id.tv_title_left);
        this.titleName = (ScrollForeverTextView) findViewById(C1034R.id.tv_title);
        this.rightName = (TextView) findViewById(C1034R.id.tv_btn);
        this.rightIcon = (ImageView) findViewById(C1034R.id.right_icon);
        this.rightTwoIcon = (ImageView) findViewById(C1034R.id.right_two_icon);
        this.llSearch = (LinearLayout) findViewById(C1034R.id.ll_search);
        this.etSearch = (PowerfulEditText) findViewById(C1034R.id.et_search);
        this.mHeadRel = (Toolbar) findViewById(C1034R.id.common_toolbar);
        this.titleLogo = (ImageView) findViewById(C1034R.id.iv_title_logo);
        TextView textView = (TextView) findViewById(C1034R.id.serach_tv);
        setBack(color);
        if (drawable != null) {
            this.leftIcon.setImageDrawable(drawable);
        }
        if (drawable2 != null) {
            this.rightIcon.setImageDrawable(drawable2);
        }
        if (drawable3 != null) {
            this.rightTwoIcon.setImageDrawable(drawable3);
        }
        this.rightSwitch.setVisibility(z6 ? 0 : 8);
        this.activityBack.setVisibility(z ? 0 : 8);
        this.rightName.setVisibility(z2 ? 0 : 8);
        this.rightIcon.setVisibility(z3 ? 0 : 8);
        this.rightTwoIcon.setVisibility(z4 ? 0 : 8);
        this.llSearch.setVisibility(z5 ? 0 : 8);
        this.titleLogo.setVisibility(TextUtils.isEmpty(string2) ? 0 : 8);
        this.etSearch.setHint(string3);
        if (!TextUtils.isEmpty(string4)) {
            textView.setText(string4);
        }
        final Activity activity = (Activity) context;
        this.activityBack.setOnClickListener(new View.OnClickListener() { // from class: com.aivox.common_ui.HeadTitleLinearView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                HeadTitleLinearView.lambda$init$0(activity, view2);
            }
        });
        if (!TextUtils.isEmpty(string2)) {
            this.titleName.setText(string2);
            this.titleName.setTextColor(color2);
        }
        if (!TextUtils.isEmpty(string)) {
            this.rightName.setText(string);
        }
        this.rightName.setTextColor(color3);
    }

    static /* synthetic */ void lambda$init$0(Activity activity, View view2) {
        KeyboardUtils.hideSoftInput(view2);
        activity.finish();
    }

    public int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public void setTitleLeft(int i) {
        this.titleLeft.setText(getContext().getString(i));
    }

    public void setTitleLeftVisible(boolean z) {
        this.titleLeft.setVisibility(z ? 0 : 8);
    }

    public void setLeftIcon(Drawable drawable) {
        this.leftIcon.setImageDrawable(drawable);
    }

    public void setTitleVisible(int i) {
        this.titleName.setVisibility(i);
    }

    public void setTitle(int i) {
        setTitle(getContext().getString(i));
    }

    public void setTitle(String str) {
        this.titleName.setText(str);
        this.titleLogo.setVisibility(4);
    }

    public void setTitleColor(int i) {
        this.titleName.setTextColor(i);
    }

    public void setRightName(String str) {
        this.rightName.setVisibility(0);
        this.rightName.setText(str);
    }

    public void setRightSize(float f) {
        this.rightName.setVisibility(0);
        this.rightName.setTextSize(f);
    }

    public void setRightColor(int i) {
        this.rightName.setVisibility(0);
        this.rightName.setTextColor(i);
    }

    public void setRightTextColor(int i, int i2, int i3) {
        this.rightName.setVisibility(0);
        this.rightName.setTextColor(Color.rgb(i, i2, i3));
    }

    public void setRightIconUrl(Drawable drawable) {
        this.rightIcon.setVisibility(0);
        this.rightIcon.setImageDrawable(drawable);
    }

    public void setRightTwoIconUrl(Drawable drawable) {
        this.rightTwoIcon.setVisibility(0);
        this.rightTwoIcon.setImageDrawable(drawable);
    }

    public void setBack(int i) {
        this.mHeadRel.setBackgroundColor(i);
    }

    public void setBackColor() {
        this.mHeadRel.setBackgroundColor(Color.parseColor("#85bcf5"));
    }

    public void setRightSwitchListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.rightSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setOnLeftTextListener(View.OnClickListener onClickListener) {
        this.titleName.setOnClickListener(onClickListener);
    }

    public void setOnBackListener(View.OnClickListener onClickListener) {
        this.activityBack.setOnClickListener(onClickListener);
    }

    public void setRightListener(View.OnClickListener onClickListener) {
        this.rightName.setOnClickListener(onClickListener);
    }

    public void setRightIconListener(View.OnClickListener onClickListener) {
        this.rightIcon.setOnClickListener(onClickListener);
    }

    public void setRightTwoIconListener(View.OnClickListener onClickListener) {
        this.rightTwoIcon.setOnClickListener(onClickListener);
    }

    public View getRightBtn() {
        return this.rightName;
    }

    public View getRightIcon() {
        return this.rightIcon;
    }

    public void setRightTextVisible(int i) {
        this.rightName.setVisibility(i);
    }

    public void hideBackBtn() {
        this.activityBack.setVisibility(8);
    }

    public LinearLayout getActivityBack() {
        return this.activityBack;
    }

    public void setRightIconVisible(int i) {
        this.rightIcon.setVisibility(i);
    }

    public void setRightIcon2Visible(int i) {
        this.rightTwoIcon.setVisibility(i);
    }

    public void setRightIcon2Res(int i) {
        this.rightTwoIcon.setImageResource(i);
    }

    public ImageView getRightTwoIcon() {
        return this.rightTwoIcon;
    }

    public void setLeftIconVisible() {
        this.activityBack.setVisibility(8);
    }

    public void setSearchVisible(int i) {
        this.llSearch.setVisibility(i);
    }

    public PowerfulEditText getEtSearch() {
        return this.etSearch;
    }

    public void setSearchListener(TextWatcher textWatcher, PowerfulEditText.OnRightClickListener onRightClickListener) {
        this.etSearch.addTextChangedListener(textWatcher);
        this.etSearch.setOnRightClickListener(onRightClickListener);
    }
}
