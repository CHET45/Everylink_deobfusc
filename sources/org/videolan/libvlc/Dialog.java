package org.videolan.libvlc;

import android.os.Handler;
import android.os.Looper;
import org.videolan.libvlc.interfaces.ILibVLC;

/* JADX INFO: loaded from: classes5.dex */
public abstract class Dialog {
    public static final int TYPE_ERROR = 0;
    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_PROGRESS = 3;
    public static final int TYPE_QUESTION = 2;
    private static Callbacks sCallbacks;
    private static Handler sHandler;
    private Object mContext;
    protected String mText;
    private final String mTitle;
    protected final int mType;

    public interface Callbacks {
        void onCanceled(Dialog dialog);

        void onDisplay(ErrorMessage errorMessage);

        void onDisplay(LoginDialog loginDialog);

        void onDisplay(ProgressDialog progressDialog);

        void onDisplay(QuestionDialog questionDialog);

        void onProgressUpdate(ProgressDialog progressDialog);
    }

    private static native void nativeSetCallbacks(ILibVLC iLibVLC, boolean z);

    public void dismiss() {
    }

    protected Dialog(int i, String str, String str2) {
        this.mType = i;
        this.mTitle = str;
        this.mText = str2;
    }

    public int getType() {
        return this.mType;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getText() {
        return this.mText;
    }

    public void setContext(Object obj) {
        this.mContext = obj;
    }

    public Object getContext() {
        return this.mContext;
    }

    public static void setCallbacks(ILibVLC iLibVLC, Callbacks callbacks) {
        if (callbacks != null && sHandler == null) {
            sHandler = new Handler(Looper.getMainLooper());
        }
        sCallbacks = callbacks;
        nativeSetCallbacks(iLibVLC, callbacks != null);
    }

    public static class ErrorMessage extends Dialog {
        private ErrorMessage(String str, String str2) {
            super(0, str, str2);
        }
    }

    protected static abstract class IdDialog extends Dialog {
        protected long mId;

        private native void nativeDismiss(long j);

        protected IdDialog(long j, int i, String str, String str2) {
            super(i, str, str2);
            this.mId = j;
        }

        @Override // org.videolan.libvlc.Dialog
        public void dismiss() {
            long j = this.mId;
            if (j != 0) {
                nativeDismiss(j);
                this.mId = 0L;
            }
        }
    }

    public static class LoginDialog extends IdDialog {
        private final boolean mAskStore;
        private final String mDefaultUsername;

        private native void nativePostLogin(long j, String str, String str2, boolean z);

        @Override // org.videolan.libvlc.Dialog.IdDialog, org.videolan.libvlc.Dialog
        public /* bridge */ /* synthetic */ void dismiss() {
            super.dismiss();
        }

        private LoginDialog(long j, String str, String str2, String str3, boolean z) {
            super(j, 1, str, str2);
            this.mDefaultUsername = str3;
            this.mAskStore = z;
        }

        public String getDefaultUsername() {
            return this.mDefaultUsername;
        }

        public boolean asksStore() {
            return this.mAskStore;
        }

        public void postLogin(String str, String str2, boolean z) {
            if (this.mId != 0) {
                nativePostLogin(this.mId, str, str2, z);
                this.mId = 0L;
            }
        }
    }

    public static class QuestionDialog extends IdDialog {
        public static final int TYPE_ERROR = 2;
        public static final int TYPE_NORMAL = 0;
        public static final int TYPE_WARNING = 1;
        private final String mAction1Text;
        private final String mAction2Text;
        private final String mCancelText;
        private final int mQuestionType;

        private native void nativePostAction(long j, int i);

        @Override // org.videolan.libvlc.Dialog.IdDialog, org.videolan.libvlc.Dialog
        public /* bridge */ /* synthetic */ void dismiss() {
            super.dismiss();
        }

        private QuestionDialog(long j, String str, String str2, int i, String str3, String str4, String str5) {
            super(j, 2, str, str2);
            this.mQuestionType = i;
            this.mCancelText = str3;
            this.mAction1Text = str4;
            this.mAction2Text = str5;
        }

        public int getQuestionType() {
            return this.mQuestionType;
        }

        public String getCancelText() {
            return this.mCancelText;
        }

        public String getAction1Text() {
            return this.mAction1Text;
        }

        public String getAction2Text() {
            return this.mAction2Text;
        }

        public void postAction(int i) {
            if (this.mId != 0) {
                nativePostAction(this.mId, i);
                this.mId = 0L;
            }
        }
    }

    public static class ProgressDialog extends IdDialog {
        private final String mCancelText;
        private final boolean mIndeterminate;
        private float mPosition;

        @Override // org.videolan.libvlc.Dialog.IdDialog, org.videolan.libvlc.Dialog
        public /* bridge */ /* synthetic */ void dismiss() {
            super.dismiss();
        }

        private ProgressDialog(long j, String str, String str2, boolean z, float f, String str3) {
            super(j, 3, str, str2);
            this.mIndeterminate = z;
            this.mPosition = f;
            this.mCancelText = str3;
        }

        public boolean isIndeterminate() {
            return this.mIndeterminate;
        }

        public boolean isCancelable() {
            return this.mCancelText != null;
        }

        public float getPosition() {
            return this.mPosition;
        }

        public String getCancelText() {
            return this.mCancelText;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void update(float f, String str) {
            this.mPosition = f;
            this.mText = str;
        }
    }

    private static void displayErrorFromNative(String str, String str2) {
        final ErrorMessage errorMessage = new ErrorMessage(str, str2);
        sHandler.post(new Runnable() { // from class: org.videolan.libvlc.Dialog.1
            @Override // java.lang.Runnable
            public void run() {
                if (Dialog.sCallbacks != null) {
                    Dialog.sCallbacks.onDisplay(errorMessage);
                }
            }
        });
    }

    private static Dialog displayLoginFromNative(long j, String str, String str2, String str3, boolean z) {
        final LoginDialog loginDialog = new LoginDialog(j, str, str2, str3, z);
        sHandler.post(new Runnable() { // from class: org.videolan.libvlc.Dialog.2
            @Override // java.lang.Runnable
            public void run() {
                if (Dialog.sCallbacks != null) {
                    Dialog.sCallbacks.onDisplay(loginDialog);
                }
            }
        });
        return loginDialog;
    }

    private static Dialog displayQuestionFromNative(long j, String str, String str2, int i, String str3, String str4, String str5) {
        final QuestionDialog questionDialog = new QuestionDialog(j, str, str2, i, str3, str4, str5);
        sHandler.post(new Runnable() { // from class: org.videolan.libvlc.Dialog.3
            @Override // java.lang.Runnable
            public void run() {
                if (Dialog.sCallbacks != null) {
                    Dialog.sCallbacks.onDisplay(questionDialog);
                }
            }
        });
        return questionDialog;
    }

    private static Dialog displayProgressFromNative(long j, String str, String str2, boolean z, float f, String str3) {
        final ProgressDialog progressDialog = new ProgressDialog(j, str, str2, z, f, str3);
        sHandler.post(new Runnable() { // from class: org.videolan.libvlc.Dialog.4
            @Override // java.lang.Runnable
            public void run() {
                if (Dialog.sCallbacks != null) {
                    Dialog.sCallbacks.onDisplay(progressDialog);
                }
            }
        });
        return progressDialog;
    }

    private static void cancelFromNative(Dialog dialog) {
        sHandler.post(new Runnable() { // from class: org.videolan.libvlc.Dialog.5
            @Override // java.lang.Runnable
            public void run() {
                Dialog dialog2 = Dialog.this;
                if (dialog2 instanceof IdDialog) {
                    ((IdDialog) dialog2).dismiss();
                }
                if (Dialog.sCallbacks == null || Dialog.this == null) {
                    return;
                }
                Dialog.sCallbacks.onCanceled(Dialog.this);
            }
        });
    }

    private static void updateProgressFromNative(Dialog dialog, final float f, final String str) {
        sHandler.post(new Runnable() { // from class: org.videolan.libvlc.Dialog.6
            @Override // java.lang.Runnable
            public void run() {
                if (Dialog.this.getType() != 3) {
                    throw new IllegalArgumentException("dialog is not a progress dialog");
                }
                ProgressDialog progressDialog = (ProgressDialog) Dialog.this;
                progressDialog.update(f, str);
                if (Dialog.sCallbacks != null) {
                    Dialog.sCallbacks.onProgressUpdate(progressDialog);
                }
            }
        });
    }
}
