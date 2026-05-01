package org.videolan.libvlc;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import org.videolan.libvlc.interfaces.IVLCVout;

/* JADX INFO: loaded from: classes5.dex */
public class AWindow implements IVLCVout {
    private static final int AWINDOW_REGISTER_ERROR = 0;
    private static final int AWINDOW_REGISTER_FLAGS_HAS_VIDEO_LAYOUT_LISTENER = 2;
    private static final int AWINDOW_REGISTER_FLAGS_SUCCESS = 1;
    private static final int ID_MAX = 2;
    private static final int ID_SUBTITLES = 1;
    private static final int ID_VIDEO = 0;
    private static final int SURFACE_STATE_ATTACHED = 1;
    private static final int SURFACE_STATE_INIT = 0;
    private static final int SURFACE_STATE_READY = 2;
    private static final String TAG = "AWindow";
    private final NativeLock mNativeLock;
    private final SurfaceCallback mSurfaceCallback;
    private SurfaceTextureThread mSurfaceTextureThread;
    private final AtomicInteger mSurfacesState = new AtomicInteger(0);
    private IVLCVout.OnNewVideoLayoutListener mOnNewVideoLayoutListener = null;
    private ArrayList<IVLCVout.Callback> mIVLCVoutCallbacks = new ArrayList<>();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private long mCallbackNativeHandle = 0;
    private int mMouseAction = -1;
    private int mMouseButton = -1;
    private int mMouseX = -1;
    private int mMouseY = -1;
    private int mWindowWidth = -1;
    private int mWindowHeight = -1;
    private final SurfaceHelper[] mSurfaceHelpers = {null, null};
    private final Surface[] mSurfaces = {null, null};

    public interface SurfaceCallback {
        void onSurfacesCreated(AWindow aWindow);

        void onSurfacesDestroyed(AWindow aWindow);
    }

    private static native void nativeOnMouseEvent(long j, int i, int i2, int i3, int i4);

    private static native void nativeOnWindowSize(long j, int i, int i2);

    private boolean setBuffersGeometry(Surface surface, int i, int i2, int i3) {
        return false;
    }

    private class SurfaceHelper {
        private final int mId;
        private Surface mSurface;
        private final SurfaceHolder mSurfaceHolder;
        private final SurfaceHolder.Callback mSurfaceHolderCallback;
        private final TextureView.SurfaceTextureListener mSurfaceTextureListener;
        private final SurfaceView mSurfaceView;
        private final TextureView mTextureView;

        private SurfaceHelper(int i, SurfaceView surfaceView) {
            this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: org.videolan.libvlc.AWindow.SurfaceHelper.1
                @Override // android.view.SurfaceHolder.Callback
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    if (surfaceHolder == SurfaceHelper.this.mSurfaceHolder) {
                        SurfaceHelper.this.setSurface(surfaceHolder.getSurface());
                        return;
                    }
                    throw new IllegalStateException("holders are different");
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    AWindow.this.onSurfaceDestroyed();
                }
            };
            this.mSurfaceTextureListener = createSurfaceTextureListener();
            this.mId = i;
            this.mTextureView = null;
            this.mSurfaceView = surfaceView;
            this.mSurfaceHolder = surfaceView.getHolder();
        }

        private SurfaceHelper(int i, TextureView textureView) {
            this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: org.videolan.libvlc.AWindow.SurfaceHelper.1
                @Override // android.view.SurfaceHolder.Callback
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    if (surfaceHolder == SurfaceHelper.this.mSurfaceHolder) {
                        SurfaceHelper.this.setSurface(surfaceHolder.getSurface());
                        return;
                    }
                    throw new IllegalStateException("holders are different");
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    AWindow.this.onSurfaceDestroyed();
                }
            };
            this.mSurfaceTextureListener = createSurfaceTextureListener();
            this.mId = i;
            this.mSurfaceView = null;
            this.mSurfaceHolder = null;
            this.mTextureView = textureView;
        }

        private SurfaceHelper(int i, Surface surface, SurfaceHolder surfaceHolder) {
            this.mSurfaceHolderCallback = new SurfaceHolder.Callback() { // from class: org.videolan.libvlc.AWindow.SurfaceHelper.1
                @Override // android.view.SurfaceHolder.Callback
                public void surfaceChanged(SurfaceHolder surfaceHolder2, int i2, int i3, int i4) {
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceCreated(SurfaceHolder surfaceHolder2) {
                    if (surfaceHolder2 == SurfaceHelper.this.mSurfaceHolder) {
                        SurfaceHelper.this.setSurface(surfaceHolder2.getSurface());
                        return;
                    }
                    throw new IllegalStateException("holders are different");
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceDestroyed(SurfaceHolder surfaceHolder2) {
                    AWindow.this.onSurfaceDestroyed();
                }
            };
            this.mSurfaceTextureListener = createSurfaceTextureListener();
            this.mId = i;
            this.mSurfaceView = null;
            this.mTextureView = null;
            this.mSurfaceHolder = surfaceHolder;
            this.mSurface = surface;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSurface(Surface surface) {
            if (surface.isValid() && AWindow.this.getNativeSurface(this.mId) == null) {
                this.mSurface = surface;
                AWindow.this.setNativeSurface(this.mId, surface);
                AWindow.this.onSurfaceCreated();
            }
        }

        private void attachSurfaceView() {
            this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
            setSurface(this.mSurfaceHolder.getSurface());
        }

        private void attachTextureView() {
            this.mTextureView.setSurfaceTextureListener(this.mSurfaceTextureListener);
            SurfaceTexture surfaceTexture = this.mTextureView.getSurfaceTexture();
            if (surfaceTexture != null) {
                this.mSurfaceTextureListener.onSurfaceTextureAvailable(surfaceTexture, this.mTextureView.getWidth(), this.mTextureView.getHeight());
            }
        }

        private void attachSurface() {
            SurfaceHolder surfaceHolder = this.mSurfaceHolder;
            if (surfaceHolder != null) {
                surfaceHolder.addCallback(this.mSurfaceHolderCallback);
            }
            setSurface(this.mSurface);
        }

        public void attach() {
            if (this.mSurfaceView != null) {
                attachSurfaceView();
            } else if (this.mTextureView != null) {
                attachTextureView();
            } else {
                if (this.mSurface != null) {
                    attachSurface();
                    return;
                }
                throw new IllegalStateException();
            }
        }

        private void releaseTextureView() {
            TextureView textureView = this.mTextureView;
            if (textureView != null) {
                textureView.setSurfaceTextureListener(null);
            }
        }

        public void release() {
            this.mSurface = null;
            AWindow.this.setNativeSurface(this.mId, null);
            SurfaceHolder surfaceHolder = this.mSurfaceHolder;
            if (surfaceHolder != null) {
                surfaceHolder.removeCallback(this.mSurfaceHolderCallback);
            }
            releaseTextureView();
        }

        public boolean isReady() {
            return this.mSurfaceView == null || this.mSurface != null;
        }

        public Surface getSurface() {
            return this.mSurface;
        }

        SurfaceHolder getSurfaceHolder() {
            return this.mSurfaceHolder;
        }

        private TextureView.SurfaceTextureListener createSurfaceTextureListener() {
            return new TextureView.SurfaceTextureListener() { // from class: org.videolan.libvlc.AWindow.SurfaceHelper.2
                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                    SurfaceHelper.this.setSurface(new Surface(surfaceTexture));
                }

                @Override // android.view.TextureView.SurfaceTextureListener
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                    AWindow.this.onSurfaceDestroyed();
                    return true;
                }
            };
        }
    }

    public AWindow(SurfaceCallback surfaceCallback) {
        this.mSurfaceTextureThread = new SurfaceTextureThread();
        this.mNativeLock = new NativeLock();
        this.mSurfaceCallback = surfaceCallback;
    }

    private void ensureInitState() throws IllegalStateException {
        if (this.mSurfacesState.get() != 0) {
            throw new IllegalStateException("Can't set view when already attached. Current state: " + this.mSurfacesState.get() + ", mSurfaces[ID_VIDEO]: " + this.mSurfaceHelpers[0] + " / " + this.mSurfaces[0] + ", mSurfaces[ID_SUBTITLES]: " + this.mSurfaceHelpers[1] + " / " + this.mSurfaces[1]);
        }
    }

    private void setView(int i, SurfaceView surfaceView) {
        ensureInitState();
        if (surfaceView == null) {
            throw new NullPointerException("view is null");
        }
        SurfaceHelper surfaceHelper = this.mSurfaceHelpers[i];
        if (surfaceHelper != null) {
            surfaceHelper.release();
        }
        this.mSurfaceHelpers[i] = new SurfaceHelper(i, surfaceView);
    }

    private void setView(int i, TextureView textureView) {
        ensureInitState();
        if (textureView == null) {
            throw new NullPointerException("view is null");
        }
        SurfaceHelper surfaceHelper = this.mSurfaceHelpers[i];
        if (surfaceHelper != null) {
            surfaceHelper.release();
        }
        this.mSurfaceHelpers[i] = new SurfaceHelper(i, textureView);
    }

    private void setSurface(int i, Surface surface, SurfaceHolder surfaceHolder) {
        ensureInitState();
        if (!surface.isValid() && surfaceHolder == null) {
            throw new IllegalStateException("surface is not attached and holder is null");
        }
        SurfaceHelper surfaceHelper = this.mSurfaceHelpers[i];
        if (surfaceHelper != null) {
            surfaceHelper.release();
        }
        this.mSurfaceHelpers[i] = new SurfaceHelper(i, surface, surfaceHolder);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setVideoView(SurfaceView surfaceView) {
        setView(0, surfaceView);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setVideoView(TextureView textureView) {
        setView(0, textureView);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setVideoSurface(Surface surface, SurfaceHolder surfaceHolder) {
        setSurface(0, surface, surfaceHolder);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setVideoSurface(SurfaceTexture surfaceTexture) {
        setSurface(0, new Surface(surfaceTexture), null);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setSubtitlesView(SurfaceView surfaceView) {
        setView(1, surfaceView);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setSubtitlesView(TextureView textureView) {
        setView(1, textureView);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setSubtitlesSurface(Surface surface, SurfaceHolder surfaceHolder) {
        setSurface(1, surface, surfaceHolder);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setSubtitlesSurface(SurfaceTexture surfaceTexture) {
        setSurface(1, new Surface(surfaceTexture), null);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void attachViews(IVLCVout.OnNewVideoLayoutListener onNewVideoLayoutListener) {
        if (this.mSurfacesState.get() == 0) {
            if (this.mSurfaceHelpers[0] != null) {
                this.mSurfacesState.set(1);
                synchronized (this.mNativeLock) {
                    this.mOnNewVideoLayoutListener = onNewVideoLayoutListener;
                    this.mNativeLock.buffersGeometryConfigured = false;
                    this.mNativeLock.buffersGeometryAbort = false;
                }
                for (int i = 0; i < 2; i++) {
                    SurfaceHelper surfaceHelper = this.mSurfaceHelpers[i];
                    if (surfaceHelper != null) {
                        surfaceHelper.attach();
                    }
                }
                return;
            }
        }
        throw new IllegalStateException("already attached or video view not configured");
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void attachViews() {
        attachViews(null);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void detachViews() {
        if (this.mSurfacesState.get() == 0) {
            return;
        }
        this.mSurfacesState.set(0);
        this.mHandler.removeCallbacksAndMessages(null);
        synchronized (this.mNativeLock) {
            this.mOnNewVideoLayoutListener = null;
            this.mNativeLock.buffersGeometryAbort = true;
            this.mNativeLock.notifyAll();
        }
        for (int i = 0; i < 2; i++) {
            SurfaceHelper surfaceHelper = this.mSurfaceHelpers[i];
            if (surfaceHelper != null) {
                surfaceHelper.release();
            }
            this.mSurfaceHelpers[i] = null;
        }
        Iterator<IVLCVout.Callback> it = this.mIVLCVoutCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onSurfacesDestroyed(this);
        }
        SurfaceCallback surfaceCallback = this.mSurfaceCallback;
        if (surfaceCallback != null) {
            surfaceCallback.onSurfacesDestroyed(this);
        }
        this.mSurfaceTextureThread.release();
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public boolean areViewsAttached() {
        return this.mSurfacesState.get() != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSurfaceCreated() {
        if (this.mSurfacesState.get() != 1) {
            throw new IllegalArgumentException("invalid state");
        }
        SurfaceHelper[] surfaceHelperArr = this.mSurfaceHelpers;
        SurfaceHelper surfaceHelper = surfaceHelperArr[0];
        SurfaceHelper surfaceHelper2 = surfaceHelperArr[1];
        if (surfaceHelper == null) {
            throw new NullPointerException("videoHelper shouldn't be null here");
        }
        if (surfaceHelper.isReady()) {
            if (surfaceHelper2 == null || surfaceHelper2.isReady()) {
                this.mSurfacesState.set(2);
                Iterator<IVLCVout.Callback> it = this.mIVLCVoutCallbacks.iterator();
                while (it.hasNext()) {
                    it.next().onSurfacesCreated(this);
                }
                SurfaceCallback surfaceCallback = this.mSurfaceCallback;
                if (surfaceCallback != null) {
                    surfaceCallback.onSurfacesCreated(this);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSurfaceDestroyed() {
        detachViews();
    }

    boolean areSurfacesWaiting() {
        return this.mSurfacesState.get() == 1;
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void sendMouseEvent(int i, int i2, int i3, int i4) {
        synchronized (this.mNativeLock) {
            long j = this.mCallbackNativeHandle;
            if (j != 0 && (this.mMouseAction != i || this.mMouseButton != i2 || this.mMouseX != i3 || this.mMouseY != i4)) {
                nativeOnMouseEvent(j, i, i2, i3, i4);
            }
            this.mMouseAction = i;
            this.mMouseButton = i2;
            this.mMouseX = i3;
            this.mMouseY = i4;
        }
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void setWindowSize(int i, int i2) {
        synchronized (this.mNativeLock) {
            long j = this.mCallbackNativeHandle;
            if (j != 0 && (this.mWindowWidth != i || this.mWindowHeight != i2)) {
                nativeOnWindowSize(j, i, i2);
            }
            this.mWindowWidth = i;
            this.mWindowHeight = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setNativeSurface(int i, Surface surface) {
        synchronized (this.mNativeLock) {
            this.mSurfaces[i] = surface;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Surface getNativeSurface(int i) {
        Surface surface;
        synchronized (this.mNativeLock) {
            surface = this.mSurfaces[i];
        }
        return surface;
    }

    private static class NativeLock {
        private boolean buffersGeometryAbort;
        private boolean buffersGeometryConfigured;

        private NativeLock() {
            this.buffersGeometryConfigured = false;
            this.buffersGeometryAbort = false;
        }
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void addCallback(IVLCVout.Callback callback) {
        if (this.mIVLCVoutCallbacks.contains(callback)) {
            return;
        }
        this.mIVLCVoutCallbacks.add(callback);
    }

    @Override // org.videolan.libvlc.interfaces.IVLCVout
    public void removeCallback(IVLCVout.Callback callback) {
        this.mIVLCVoutCallbacks.remove(callback);
    }

    private Surface getVideoSurface() {
        return getNativeSurface(0);
    }

    private Surface getSubtitlesSurface() {
        return getNativeSurface(1);
    }

    private int registerNative(long j) {
        int i;
        if (j == 0) {
            throw new IllegalArgumentException("nativeHandle is null");
        }
        synchronized (this.mNativeLock) {
            if (this.mCallbackNativeHandle != 0) {
                return 0;
            }
            this.mCallbackNativeHandle = j;
            int i2 = this.mMouseAction;
            if (i2 != -1) {
                nativeOnMouseEvent(j, i2, this.mMouseButton, this.mMouseX, this.mMouseY);
            }
            int i3 = this.mWindowWidth;
            if (i3 != -1 && (i = this.mWindowHeight) != -1) {
                nativeOnWindowSize(this.mCallbackNativeHandle, i3, i);
            }
            return this.mOnNewVideoLayoutListener != null ? 3 : 1;
        }
    }

    private void unregisterNative() {
        synchronized (this.mNativeLock) {
            if (this.mCallbackNativeHandle == 0) {
                throw new IllegalArgumentException("unregister called when not registered");
            }
            this.mCallbackNativeHandle = 0L;
        }
    }

    private void setVideoLayout(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        this.mHandler.post(new Runnable() { // from class: org.videolan.libvlc.AWindow.1
            @Override // java.lang.Runnable
            public void run() {
                if (AWindow.this.mOnNewVideoLayoutListener != null) {
                    AWindow.this.mOnNewVideoLayoutListener.onNewVideoLayout(AWindow.this, i, i2, i3, i4, i5, i6);
                }
            }
        });
    }

    private static class SurfaceTextureThread implements Runnable, SurfaceTexture.OnFrameAvailableListener {
        private boolean mDoRelease;
        private boolean mFrameAvailable;
        private boolean mIsAttached;
        private Looper mLooper;
        private Surface mSurface;
        private SurfaceTexture mSurfaceTexture;
        private Thread mThread;

        private SurfaceTextureThread() {
            this.mSurfaceTexture = null;
            this.mSurface = null;
            this.mFrameAvailable = false;
            this.mLooper = null;
            this.mThread = null;
            this.mIsAttached = false;
            this.mDoRelease = false;
        }

        private synchronized boolean createSurface() {
            if (this.mSurfaceTexture == null) {
                Thread thread = new Thread(this);
                this.mThread = thread;
                thread.start();
                while (this.mSurfaceTexture == null) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                        return false;
                    }
                }
                this.mSurface = new Surface(this.mSurfaceTexture);
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean attachToGLContext(int i) {
            if (!createSurface()) {
                return false;
            }
            this.mSurfaceTexture.attachToGLContext(i);
            this.mFrameAvailable = false;
            this.mIsAttached = true;
            return true;
        }

        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
        public synchronized void onFrameAvailable(SurfaceTexture surfaceTexture) {
            if (surfaceTexture == this.mSurfaceTexture) {
                if (this.mFrameAvailable) {
                    throw new IllegalStateException("An available frame was not updated");
                }
                this.mFrameAvailable = true;
                notify();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Looper.prepare();
            synchronized (this) {
                this.mLooper = Looper.myLooper();
                SurfaceTexture surfaceTexture = new SurfaceTexture(0);
                this.mSurfaceTexture = surfaceTexture;
                surfaceTexture.detachFromGLContext();
                this.mSurfaceTexture.setOnFrameAvailableListener(this);
                notify();
            }
            Looper.loop();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void detachFromGLContext() {
            if (this.mDoRelease) {
                this.mLooper.quit();
                this.mLooper = null;
                try {
                    this.mThread.join();
                } catch (InterruptedException unused) {
                }
                this.mThread = null;
                this.mSurface.release();
                this.mSurface = null;
                this.mSurfaceTexture.release();
                this.mSurfaceTexture = null;
                this.mDoRelease = false;
            } else {
                this.mSurfaceTexture.detachFromGLContext();
            }
            this.mIsAttached = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean waitAndUpdateTexImage(float[] fArr) {
            synchronized (this) {
                while (!this.mFrameAvailable) {
                    try {
                        wait(500L);
                    } catch (InterruptedException unused) {
                    }
                    if (!this.mFrameAvailable) {
                        return false;
                    }
                }
                this.mFrameAvailable = false;
                this.mSurfaceTexture.updateTexImage();
                this.mSurfaceTexture.getTransformMatrix(fArr);
                return true;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized Surface getSurface() {
            if (!createSurface()) {
                return null;
            }
            return this.mSurface;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void release() {
            if (this.mSurfaceTexture != null) {
                if (this.mIsAttached) {
                    this.mDoRelease = true;
                } else {
                    this.mSurface.release();
                    this.mSurface = null;
                    this.mSurfaceTexture.release();
                    this.mSurfaceTexture = null;
                }
            }
        }
    }

    boolean SurfaceTexture_attachToGLContext(int i) {
        return this.mSurfaceTextureThread.attachToGLContext(i);
    }

    private void SurfaceTexture_detachFromGLContext() {
        this.mSurfaceTextureThread.detachFromGLContext();
    }

    private boolean SurfaceTexture_waitAndUpdateTexImage(float[] fArr) {
        return this.mSurfaceTextureThread.waitAndUpdateTexImage(fArr);
    }

    private Surface SurfaceTexture_getSurface() {
        return this.mSurfaceTextureThread.getSurface();
    }
}
