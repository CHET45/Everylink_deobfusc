package reactor.core.publisher;

import reactor.core.Scannable;

/* JADX INFO: loaded from: classes5.dex */
class InternalProducerAttr extends Scannable.Attr<Boolean> {
    static final InternalProducerAttr INSTANCE = new InternalProducerAttr(true);

    private InternalProducerAttr(Boolean bool) {
        super(bool);
    }
}
