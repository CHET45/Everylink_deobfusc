package com.aivox.app.media;

/* JADX INFO: loaded from: classes.dex */
public interface IDecoderStateListener {
    void decodeOneFrame(BaseDecoder baseDecoder);

    void decoderDestroy(BaseDecoder baseDecoder);

    void decoderError(BaseDecoder baseDecoder, String str);

    void decoderFinish(BaseDecoder baseDecoder);

    void decoderPause(BaseDecoder baseDecoder);

    void decoderPrepare(BaseDecoder baseDecoder);

    void decoderReady(BaseDecoder baseDecoder);

    void decoderRunning(BaseDecoder baseDecoder);
}
