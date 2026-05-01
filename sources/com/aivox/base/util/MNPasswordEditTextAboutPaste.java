package com.aivox.base.util;

import android.content.Context;
import android.util.AttributeSet;
import com.maning.pswedittextlibrary.MNPasswordEditText;

/* JADX INFO: loaded from: classes.dex */
public class MNPasswordEditTextAboutPaste extends MNPasswordEditText {
    public static final int ID_COPY = 16908321;
    public static final int ID_CUT = 16908320;
    public static final int ID_PASTE = 16908322;
    public static final int ID_PASTE_AS_PLAIN_TEXT = 16908337;
    public static final int ID_REDO = 16908339;
    public static final int ID_REPLACE = 16908340;
    public static final int ID_SELECT_ALL = 16908319;
    public static final int ID_SHARE = 16908341;
    public static final int ID_UNDO = 16908338;
    OnTextContextMenuItemListener onTextContextMenuItemListener;

    public interface OnTextContextMenuItemListener {
        void etMenuItem(int i);
    }

    public MNPasswordEditTextAboutPaste(Context context) {
        super(context);
    }

    public MNPasswordEditTextAboutPaste(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        OnTextContextMenuItemListener onTextContextMenuItemListener = this.onTextContextMenuItemListener;
        if (onTextContextMenuItemListener != null) {
            onTextContextMenuItemListener.etMenuItem(i);
        }
        return super.onTextContextMenuItem(i);
    }

    public void setOnTextContextMenuItemListener(OnTextContextMenuItemListener onTextContextMenuItemListener) {
        this.onTextContextMenuItemListener = onTextContextMenuItemListener;
    }
}
