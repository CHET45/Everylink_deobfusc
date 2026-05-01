package com.example.gjylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.gjylibrary.MyEditText;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GjySerialnumberLayout extends RelativeLayout {
    private int codeNumber;
    private Context context;
    List<MyEditText> editViews;
    private LinearLayout ll_content;
    private OnInputListener onInputListener;
    private int textColor;

    public interface OnInputListener {
        void onSucess(String str);
    }

    public GjySerialnumberLayout(Context context) {
        this(context, null);
    }

    public GjySerialnumberLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GjySerialnumberLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        loadView(attributeSet);
    }

    private void loadView(AttributeSet attributeSet) {
        this.ll_content = (LinearLayout) LayoutInflater.from(this.context).inflate(C1419R.layout.verification_code, this).findViewById(C1419R.id.ll_code_content);
        TypedArray typedArrayObtainStyledAttributes = this.context.obtainStyledAttributes(attributeSet, C1419R.styleable.Verificationcode);
        this.textColor = typedArrayObtainStyledAttributes.getColor(C1419R.styleable.Verificationcode_code_text_color, getResources().getColor(C1419R.color.teal_200));
        int i = typedArrayObtainStyledAttributes.getInt(C1419R.styleable.Verificationcode_code_number, 16);
        this.codeNumber = i;
        if (i > 8 && i % 2 == 1) {
            this.codeNumber = i + 1;
        }
        initView();
    }

    private void initView() {
        int i;
        this.editViews = new ArrayList();
        LinearLayout linearLayout = new LinearLayout(this.context);
        LinearLayout linearLayout2 = new LinearLayout(this.context);
        int i2 = 0;
        while (true) {
            i = this.codeNumber;
            if (i2 >= i) {
                break;
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
            View viewInflate = LayoutInflater.from(this.context).inflate(C1419R.layout.verifation_code_item, (ViewGroup) null);
            final MyEditText myEditText = (MyEditText) viewInflate.findViewById(C1419R.id.tv_code);
            myEditText.setTextColor(this.textColor);
            myEditText.setBackground(getResources().getDrawable(C1419R.drawable.bg_text_normal));
            myEditText.setId(i2);
            myEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.example.gjylibrary.GjySerialnumberLayout.1
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view2, boolean z) {
                    if (z) {
                        myEditText.setBackground(GjySerialnumberLayout.this.getResources().getDrawable(C1419R.drawable.bg_text_focus));
                    } else {
                        myEditText.setBackground(GjySerialnumberLayout.this.getResources().getDrawable(C1419R.drawable.bg_text_normal));
                    }
                }
            });
            myEditText.addTextChangedListener(new TextWatcher() { // from class: com.example.gjylibrary.GjySerialnumberLayout.2
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    if (editable == null || editable.length() <= 0) {
                        return;
                    }
                    String string = editable.toString();
                    int id = myEditText.getId();
                    int i3 = 0;
                    String str = "";
                    if (string.length() + id <= GjySerialnumberLayout.this.codeNumber) {
                        if (string.length() <= 1 || id >= GjySerialnumberLayout.this.codeNumber - 1) {
                            if (id < GjySerialnumberLayout.this.codeNumber - 1) {
                                GjySerialnumberLayout.this.showCode(id + 1, "");
                                myEditText.setBackground(GjySerialnumberLayout.this.getResources().getDrawable(C1419R.drawable.bg_text_complete));
                                return;
                            }
                            while (i3 < GjySerialnumberLayout.this.codeNumber) {
                                str = str + ((Object) GjySerialnumberLayout.this.editViews.get(i3).getText());
                                i3++;
                            }
                            if (GjySerialnumberLayout.this.onInputListener != null) {
                                GjySerialnumberLayout.this.onInputListener.onSucess(str);
                                return;
                            }
                            return;
                        }
                        for (int i4 = id; i4 < GjySerialnumberLayout.this.editViews.size(); i4++) {
                            GjySerialnumberLayout.this.editViews.get(i4).setText("");
                        }
                        while (i3 < string.length()) {
                            GjySerialnumberLayout.this.showCode(i3 + id, string.charAt(i3) + "");
                            i3++;
                        }
                        GjySerialnumberLayout.this.editViews.get((id + string.length()) - 1).setSelection(1);
                        return;
                    }
                    myEditText.setText("");
                    Toast.makeText(GjySerialnumberLayout.this.context, "长度超过" + GjySerialnumberLayout.this.codeNumber + "，请检查", 0).show();
                }
            });
            myEditText.setOnKeyListener(new View.OnKeyListener() { // from class: com.example.gjylibrary.GjySerialnumberLayout.3
                @Override // android.view.View.OnKeyListener
                public boolean onKey(View view2, int i3, KeyEvent keyEvent) {
                    if (i3 == 67 && keyEvent.getAction() == 0) {
                        int id = myEditText.getId();
                        if (myEditText.getText().toString().equals("")) {
                            if (id >= 1) {
                                GjySerialnumberLayout.this.removeCode(id - 1);
                            }
                            return true;
                        }
                    }
                    return false;
                }
            });
            myEditText.setZTListener(new MyEditText.onTextContextMenuItemListener() { // from class: com.example.gjylibrary.GjySerialnumberLayout.4
                @Override // com.example.gjylibrary.MyEditText.onTextContextMenuItemListener
                public boolean onTextContextMenuItem(int i3, String str) {
                    if (str.length() > GjySerialnumberLayout.this.codeNumber) {
                        Toast.makeText(GjySerialnumberLayout.this.context, "长度超过" + GjySerialnumberLayout.this.codeNumber + "，请检查", 0).show();
                    } else if (str.length() > 0) {
                        for (int i4 = 0; i4 < GjySerialnumberLayout.this.editViews.size(); i4++) {
                            GjySerialnumberLayout.this.editViews.get(i4).setText("");
                        }
                        GjySerialnumberLayout.this.showCode(0, "");
                        for (int i5 = 0; i5 < str.length(); i5++) {
                            GjySerialnumberLayout.this.showCode(i5, str.charAt(i5) + "");
                        }
                        GjySerialnumberLayout.this.editViews.get(str.length() - 1).setSelection(1);
                    }
                    return false;
                }
            });
            int i3 = this.codeNumber;
            if (i3 <= 8) {
                linearLayout.addView(viewInflate, layoutParams);
            } else if (i2 >= i3 / 2) {
                linearLayout2.addView(viewInflate, layoutParams);
            } else {
                linearLayout.addView(viewInflate, layoutParams);
            }
            myEditText.setInputType(2);
            this.editViews.add(myEditText);
            i2++;
        }
        if (i <= 8) {
            this.ll_content.addView(linearLayout);
        } else {
            this.ll_content.addView(linearLayout);
            this.ll_content.addView(linearLayout2);
        }
        this.editViews.get(this.codeNumber - 1).setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        this.editViews.get(0).setFocusable(true);
        this.editViews.get(0).setFocusableInTouchMode(true);
        this.editViews.get(0).requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCode(int i, String str) {
        MyEditText myEditText = this.editViews.get(i);
        myEditText.setFocusable(true);
        myEditText.setFocusableInTouchMode(true);
        myEditText.requestFocus();
        myEditText.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeCode(int i) {
        MyEditText myEditText = this.editViews.get(i);
        myEditText.setFocusable(true);
        myEditText.setFocusableInTouchMode(true);
        myEditText.requestFocus();
        myEditText.setText("");
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }
}
