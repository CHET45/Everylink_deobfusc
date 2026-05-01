package com.aivox.common_ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class LabelsView extends ViewGroup implements View.OnClickListener {
    private static final String KEY_BG_RES_ID_STATE = "key_bg_res_id_state";
    private static final String KEY_COMPULSORY_LABELS_STATE = "key_select_compulsory_state";
    private static final String KEY_LABELS_STATE = "key_labels_state";
    private static final String KEY_LINE_MARGIN_STATE = "key_line_margin_state";
    private static final String KEY_MAX_SELECT_STATE = "key_max_select_state";
    private static final String KEY_PADDING_STATE = "key_padding_state";
    private static final String KEY_SELECT_LABELS_STATE = "key_select_labels_state";
    private static final String KEY_SELECT_TYPE_STATE = "key_select_type_state";
    private static final String KEY_SUPER_STATE = "key_super_state";
    private static final String KEY_TEXT_COLOR_STATE = "key_text_color_state";
    private static final String KEY_TEXT_SIZE_STATE = "key_text_size_state";
    private static final String KEY_WORD_MARGIN_STATE = "key_word_margin_state";
    private ArrayList<Integer> mCompulsorys;
    private Context mContext;
    private int mLabelBgResId;
    private OnLabelClickListener mLabelClickListener;
    private OnLabelSelectChangeListener mLabelSelectChangeListener;
    private ArrayList<Object> mLabels;
    private int mLineMargin;
    private int mMaxSelect;
    private ArrayList<Integer> mSelectLabels;
    private SelectType mSelectType;
    private ColorStateList mTextColor;
    private int mTextPaddingBottom;
    private int mTextPaddingLeft;
    private int mTextPaddingRight;
    private int mTextPaddingTop;
    private float mTextSize;
    private int mWordMargin;
    private static final int KEY_DATA = C1034R.id.tag_key_data;
    private static final int KEY_POSITION = C1034R.id.tag_key_position;

    public interface LabelTextProvider<T> {
        CharSequence getLabelText(TextView textView, int i, T t);
    }

    public interface OnLabelClickListener {
        void onLabelClick(TextView textView, Object obj, int i);
    }

    public interface OnLabelSelectChangeListener {
        void onLabelSelectChange(TextView textView, Object obj, boolean z, int i);
    }

    public enum SelectType {
        NONE(1),
        SINGLE(2),
        SINGLE_IRREVOCABLY(3),
        MULTI(4);

        int value;

        SelectType(int i) {
            this.value = i;
        }

        static SelectType get(int i) {
            if (i == 1) {
                return NONE;
            }
            if (i == 2) {
                return SINGLE;
            }
            if (i == 3) {
                return SINGLE_IRREVOCABLY;
            }
            if (i == 4) {
                return MULTI;
            }
            return NONE;
        }
    }

    public LabelsView(Context context) {
        super(context);
        this.mLabels = new ArrayList<>();
        this.mSelectLabels = new ArrayList<>();
        this.mCompulsorys = new ArrayList<>();
        this.mContext = context;
    }

    public LabelsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLabels = new ArrayList<>();
        this.mSelectLabels = new ArrayList<>();
        this.mCompulsorys = new ArrayList<>();
        this.mContext = context;
        getAttrs(context, attributeSet);
    }

    public LabelsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLabels = new ArrayList<>();
        this.mSelectLabels = new ArrayList<>();
        this.mCompulsorys = new ArrayList<>();
        this.mContext = context;
        getAttrs(context, attributeSet);
    }

    private void getAttrs(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1034R.styleable.labels_view);
            this.mSelectType = SelectType.get(typedArrayObtainStyledAttributes.getInt(C1034R.styleable.labels_view_selectType, 1));
            this.mMaxSelect = typedArrayObtainStyledAttributes.getInteger(C1034R.styleable.labels_view_maxSelect, 0);
            this.mTextColor = typedArrayObtainStyledAttributes.getColorStateList(C1034R.styleable.labels_view_labelTextColor);
            this.mTextSize = typedArrayObtainStyledAttributes.getDimension(C1034R.styleable.labels_view_labelTextSize, sp2px(context, 14.0f));
            this.mTextPaddingLeft = typedArrayObtainStyledAttributes.getDimensionPixelOffset(C1034R.styleable.labels_view_labelTextPaddingLeft, 0);
            this.mTextPaddingTop = typedArrayObtainStyledAttributes.getDimensionPixelOffset(C1034R.styleable.labels_view_labelTextPaddingTop, 0);
            this.mTextPaddingRight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(C1034R.styleable.labels_view_labelTextPaddingRight, 0);
            this.mTextPaddingBottom = typedArrayObtainStyledAttributes.getDimensionPixelOffset(C1034R.styleable.labels_view_labelTextPaddingBottom, 0);
            this.mLineMargin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(C1034R.styleable.labels_view_lineMargin, 0);
            this.mWordMargin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(C1034R.styleable.labels_view_wordMargin, 0);
            this.mLabelBgResId = typedArrayObtainStyledAttributes.getResourceId(C1034R.styleable.labels_view_labelBackground, 0);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int size = (View.MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight();
        int i3 = 0;
        int iMax = 0;
        int iMax2 = 0;
        int measuredWidth = 0;
        boolean z = true;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            measureChild(childAt, i, i2);
            if (size < childAt.getMeasuredWidth() + measuredWidth) {
                i3 = i3 + this.mLineMargin + iMax;
                iMax2 = Math.max(iMax2, measuredWidth);
                iMax = 0;
                measuredWidth = 0;
                z = true;
            }
            iMax = Math.max(iMax, childAt.getMeasuredHeight());
            if (z) {
                z = false;
            } else {
                measuredWidth += this.mWordMargin;
            }
            measuredWidth += childAt.getMeasuredWidth();
        }
        setMeasuredDimension(measureWidth(i, Math.max(iMax2, measuredWidth)), measureHeight(i2, i3 + iMax));
    }

    private int measureWidth(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode != 1073741824) {
            int paddingLeft = i2 + getPaddingLeft() + getPaddingRight();
            size = mode == Integer.MIN_VALUE ? Math.min(paddingLeft, size) : paddingLeft;
        }
        return Math.max(size, getSuggestedMinimumWidth());
    }

    private int measureHeight(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode != 1073741824) {
            int paddingTop = i2 + getPaddingTop() + getPaddingBottom();
            size = mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
        }
        return Math.max(size, getSuggestedMinimumHeight());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i5 = i3 - i;
        int childCount = getChildCount();
        int iMax = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (i5 < childAt.getMeasuredWidth() + paddingLeft + getPaddingRight()) {
                paddingLeft = getPaddingLeft();
                paddingTop = paddingTop + this.mLineMargin + iMax;
                iMax = 0;
            }
            childAt.layout(paddingLeft, paddingTop, childAt.getMeasuredWidth() + paddingLeft, childAt.getMeasuredHeight() + paddingTop);
            paddingLeft = paddingLeft + childAt.getMeasuredWidth() + this.mWordMargin;
            iMax = Math.max(iMax, childAt.getMeasuredHeight());
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        ColorStateList colorStateList = this.mTextColor;
        if (colorStateList != null) {
            bundle.putParcelable(KEY_TEXT_COLOR_STATE, colorStateList);
        }
        bundle.putFloat(KEY_TEXT_SIZE_STATE, this.mTextSize);
        bundle.putInt(KEY_BG_RES_ID_STATE, this.mLabelBgResId);
        bundle.putIntArray(KEY_PADDING_STATE, new int[]{this.mTextPaddingLeft, this.mTextPaddingTop, this.mTextPaddingRight, this.mTextPaddingBottom});
        bundle.putInt(KEY_WORD_MARGIN_STATE, this.mWordMargin);
        bundle.putInt(KEY_LINE_MARGIN_STATE, this.mLineMargin);
        bundle.putInt(KEY_SELECT_TYPE_STATE, this.mSelectType.value);
        bundle.putInt(KEY_MAX_SELECT_STATE, this.mMaxSelect);
        if (!this.mSelectLabels.isEmpty()) {
            bundle.putIntegerArrayList(KEY_SELECT_LABELS_STATE, this.mSelectLabels);
        }
        if (!this.mCompulsorys.isEmpty()) {
            bundle.putIntegerArrayList(KEY_COMPULSORY_LABELS_STATE, this.mCompulsorys);
        }
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
            ColorStateList colorStateList = (ColorStateList) bundle.getParcelable(KEY_TEXT_COLOR_STATE);
            if (colorStateList != null) {
                setLabelTextColor(colorStateList);
            }
            setLabelTextSize(bundle.getFloat(KEY_TEXT_SIZE_STATE, this.mTextSize));
            int i = bundle.getInt(KEY_BG_RES_ID_STATE, this.mLabelBgResId);
            if (i != 0) {
                setLabelBackgroundResource(i);
            }
            int[] intArray = bundle.getIntArray(KEY_PADDING_STATE);
            if (intArray != null && intArray.length == 4) {
                setLabelTextPadding(intArray[0], intArray[1], intArray[2], intArray[3]);
            }
            setWordMargin(bundle.getInt(KEY_WORD_MARGIN_STATE, this.mWordMargin));
            setLineMargin(bundle.getInt(KEY_LINE_MARGIN_STATE, this.mLineMargin));
            setSelectType(SelectType.get(bundle.getInt(KEY_SELECT_TYPE_STATE, this.mSelectType.value)));
            setMaxSelect(bundle.getInt(KEY_MAX_SELECT_STATE, this.mMaxSelect));
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(KEY_COMPULSORY_LABELS_STATE);
            if (integerArrayList != null && !integerArrayList.isEmpty()) {
                setCompulsorys(integerArrayList);
            }
            ArrayList<Integer> integerArrayList2 = bundle.getIntegerArrayList(KEY_SELECT_LABELS_STATE);
            if (integerArrayList2 == null || integerArrayList2.isEmpty()) {
                return;
            }
            int size = integerArrayList2.size();
            int[] iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = integerArrayList2.get(i2).intValue();
            }
            setSelects(iArr);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public void setLabels(List<String> list) {
        setLabels(list, new LabelTextProvider<String>() { // from class: com.aivox.common_ui.LabelsView.1
            @Override // com.aivox.common_ui.LabelsView.LabelTextProvider
            public CharSequence getLabelText(TextView textView, int i, String str) {
                return str.trim();
            }
        });
    }

    public <T> void setLabels(List<T> list, LabelTextProvider<T> labelTextProvider) {
        innerClearAllSelect();
        removeAllViews();
        this.mLabels.clear();
        if (list != null) {
            this.mLabels.addAll(list);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                addLabel(list.get(i), i, labelTextProvider);
            }
        }
        if (this.mSelectType == SelectType.SINGLE_IRREVOCABLY) {
            setSelects(0);
        }
    }

    public <T> List<T> getLabels() {
        return this.mLabels;
    }

    private <T> void addLabel(T t, int i, LabelTextProvider<T> labelTextProvider) {
        TextView textView = new TextView(this.mContext);
        textView.setPadding(this.mTextPaddingLeft, this.mTextPaddingTop, this.mTextPaddingRight, this.mTextPaddingBottom);
        textView.setTextSize(0, this.mTextSize);
        ColorStateList colorStateListValueOf = this.mTextColor;
        if (colorStateListValueOf == null) {
            colorStateListValueOf = ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK);
        }
        textView.setTextColor(colorStateListValueOf);
        int i2 = this.mLabelBgResId;
        if (i2 != 0) {
            textView.setBackgroundResource(i2);
        }
        textView.setTag(KEY_DATA, t);
        textView.setTag(KEY_POSITION, Integer.valueOf(i));
        textView.setOnClickListener(this);
        addView(textView);
        textView.setText(labelTextProvider.getLabelText(textView, i, t));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        int i;
        if (view2 instanceof TextView) {
            TextView textView = (TextView) view2;
            if (this.mSelectType != SelectType.NONE) {
                if (textView.isSelected()) {
                    if (this.mSelectType != SelectType.SINGLE_IRREVOCABLY && !this.mCompulsorys.contains((Integer) textView.getTag(KEY_POSITION))) {
                        setLabelSelect(textView, false);
                    }
                } else if (this.mSelectType == SelectType.SINGLE || this.mSelectType == SelectType.SINGLE_IRREVOCABLY) {
                    innerClearAllSelect();
                    setLabelSelect(textView, true);
                } else if (this.mSelectType == SelectType.MULTI && ((i = this.mMaxSelect) <= 0 || i > this.mSelectLabels.size())) {
                    setLabelSelect(textView, true);
                }
            }
            OnLabelClickListener onLabelClickListener = this.mLabelClickListener;
            if (onLabelClickListener != null) {
                onLabelClickListener.onLabelClick(textView, textView.getTag(KEY_DATA), ((Integer) textView.getTag(KEY_POSITION)).intValue());
            }
        }
    }

    private void setLabelSelect(TextView textView, boolean z) {
        if (textView.isSelected() != z) {
            textView.setSelected(z);
            if (z) {
                this.mSelectLabels.add((Integer) textView.getTag(KEY_POSITION));
            } else {
                this.mSelectLabels.remove((Integer) textView.getTag(KEY_POSITION));
            }
            OnLabelSelectChangeListener onLabelSelectChangeListener = this.mLabelSelectChangeListener;
            if (onLabelSelectChangeListener != null) {
                onLabelSelectChangeListener.onLabelSelectChange(textView, textView.getTag(KEY_DATA), z, ((Integer) textView.getTag(KEY_POSITION)).intValue());
            }
        }
    }

    public void clearAllSelect() {
        if (this.mSelectType != SelectType.SINGLE_IRREVOCABLY) {
            if (this.mSelectType == SelectType.MULTI && !this.mCompulsorys.isEmpty()) {
                clearNotCompulsorySelect();
            } else {
                innerClearAllSelect();
            }
        }
    }

    private void innerClearAllSelect() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            setLabelSelect((TextView) getChildAt(i), false);
        }
        this.mSelectLabels.clear();
    }

    private void clearNotCompulsorySelect() {
        int childCount = getChildCount();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            if (!this.mCompulsorys.contains(Integer.valueOf(i))) {
                setLabelSelect((TextView) getChildAt(i), false);
                arrayList.add(Integer.valueOf(i));
            }
        }
        this.mSelectLabels.removeAll(arrayList);
    }

    public void setSelects(List<Integer> list) {
        if (list != null) {
            int size = list.size();
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = list.get(i).intValue();
            }
            setSelects(iArr);
        }
    }

    public void setSelects(int... iArr) {
        if (this.mSelectType != SelectType.NONE) {
            ArrayList arrayList = new ArrayList();
            int childCount = getChildCount();
            int i = (this.mSelectType == SelectType.SINGLE || this.mSelectType == SelectType.SINGLE_IRREVOCABLY) ? 1 : this.mMaxSelect;
            for (int i2 : iArr) {
                if (i2 < childCount) {
                    TextView textView = (TextView) getChildAt(i2);
                    if (!arrayList.contains(textView)) {
                        setLabelSelect(textView, true);
                        arrayList.add(textView);
                    }
                    if (i > 0 && arrayList.size() == i) {
                        break;
                    }
                }
            }
            for (int i3 = 0; i3 < childCount; i3++) {
                TextView textView2 = (TextView) getChildAt(i3);
                if (!arrayList.contains(textView2)) {
                    setLabelSelect(textView2, false);
                }
            }
        }
    }

    public void setCompulsorys(List<Integer> list) {
        if (this.mSelectType != SelectType.MULTI || list == null) {
            return;
        }
        this.mCompulsorys.clear();
        this.mCompulsorys.addAll(list);
        innerClearAllSelect();
        setSelects(list);
    }

    public void setCompulsorys(int... iArr) {
        if (this.mSelectType != SelectType.MULTI || iArr == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        setCompulsorys(arrayList);
    }

    public List<Integer> getCompulsorys() {
        return this.mCompulsorys;
    }

    public void clearCompulsorys() {
        if (this.mSelectType != SelectType.MULTI || this.mCompulsorys.isEmpty()) {
            return;
        }
        this.mCompulsorys.clear();
        innerClearAllSelect();
    }

    public List<Integer> getSelectLabels() {
        return this.mSelectLabels;
    }

    public <T> List<T> getSelectLabelDatas() {
        ArrayList arrayList = new ArrayList();
        int size = this.mSelectLabels.size();
        for (int i = 0; i < size; i++) {
            Object tag = getChildAt(this.mSelectLabels.get(i).intValue()).getTag(KEY_DATA);
            if (tag != null) {
                arrayList.add(tag);
            }
        }
        return arrayList;
    }

    public void setLabelBackgroundResource(int i) {
        if (this.mLabelBgResId != i) {
            this.mLabelBgResId = i;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ((TextView) getChildAt(i2)).setBackgroundResource(this.mLabelBgResId);
            }
        }
    }

    public void setLabelTextPadding(int i, int i2, int i3, int i4) {
        if (this.mTextPaddingLeft == i && this.mTextPaddingTop == i2 && this.mTextPaddingRight == i3 && this.mTextPaddingBottom == i4) {
            return;
        }
        this.mTextPaddingLeft = i;
        this.mTextPaddingTop = i2;
        this.mTextPaddingRight = i3;
        this.mTextPaddingBottom = i4;
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            ((TextView) getChildAt(i5)).setPadding(i, i2, i3, i4);
        }
    }

    public int getTextPaddingLeft() {
        return this.mTextPaddingLeft;
    }

    public int getTextPaddingTop() {
        return this.mTextPaddingTop;
    }

    public int getTextPaddingRight() {
        return this.mTextPaddingRight;
    }

    public int getTextPaddingBottom() {
        return this.mTextPaddingBottom;
    }

    public void setLabelTextSize(float f) {
        if (this.mTextSize != f) {
            this.mTextSize = f;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((TextView) getChildAt(i)).setTextSize(0, f);
            }
        }
    }

    public float getLabelTextSize() {
        return this.mTextSize;
    }

    public void setLabelTextColor(int i) {
        setLabelTextColor(ColorStateList.valueOf(i));
    }

    public void setLabelTextColor(ColorStateList colorStateList) {
        this.mTextColor = colorStateList;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView textView = (TextView) getChildAt(i);
            ColorStateList colorStateListValueOf = this.mTextColor;
            if (colorStateListValueOf == null) {
                colorStateListValueOf = ColorStateList.valueOf(ViewCompat.MEASURED_STATE_MASK);
            }
            textView.setTextColor(colorStateListValueOf);
        }
    }

    public ColorStateList getLabelTextColor() {
        return this.mTextColor;
    }

    public void setLineMargin(int i) {
        if (this.mLineMargin != i) {
            this.mLineMargin = i;
            requestLayout();
        }
    }

    public int getLineMargin() {
        return this.mLineMargin;
    }

    public void setWordMargin(int i) {
        if (this.mWordMargin != i) {
            this.mWordMargin = i;
            requestLayout();
        }
    }

    public int getWordMargin() {
        return this.mWordMargin;
    }

    public void setSelectType(SelectType selectType) {
        if (this.mSelectType != selectType) {
            this.mSelectType = selectType;
            innerClearAllSelect();
            if (this.mSelectType == SelectType.SINGLE_IRREVOCABLY) {
                setSelects(0);
            }
            if (this.mSelectType != SelectType.MULTI) {
                this.mCompulsorys.clear();
            }
        }
    }

    public SelectType getSelectType() {
        return this.mSelectType;
    }

    public void setMaxSelect(int i) {
        if (this.mMaxSelect != i) {
            this.mMaxSelect = i;
            if (this.mSelectType == SelectType.MULTI) {
                innerClearAllSelect();
            }
        }
    }

    public int getMaxSelect() {
        return this.mMaxSelect;
    }

    public void setOnLabelClickListener(OnLabelClickListener onLabelClickListener) {
        this.mLabelClickListener = onLabelClickListener;
    }

    public void setOnLabelSelectChangeListener(OnLabelSelectChangeListener onLabelSelectChangeListener) {
        this.mLabelSelectChangeListener = onLabelSelectChangeListener;
    }

    public static int sp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }
}
