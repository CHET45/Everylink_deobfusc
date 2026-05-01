package com.example.gjylibrary;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.appcompat.widget.AppCompatEditText;

/* JADX INFO: loaded from: classes3.dex */
public class MyEditText extends AppCompatEditText {
    private onTextContextMenuItemListener onTextContextMenuItemListener;

    public interface onTextContextMenuItemListener {
        boolean onTextContextMenuItem(int i, String str);
    }

    public void setZTListener(onTextContextMenuItemListener ontextcontextmenuitemlistener) {
        this.onTextContextMenuItemListener = ontextcontextmenuitemlistener;
    }

    public MyEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MyEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyEditText(Context context) {
        super(context);
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.EditText, android.widget.TextView
    public boolean onTextContextMenuItem(int i) {
        if (i != 16908322) {
            return false;
        }
        this.onTextContextMenuItemListener.onTextContextMenuItem(i, ((ClipboardManager) getContext().getSystemService("clipboard")).getText().toString());
        return false;
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 23 || keyCode == 66) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}
