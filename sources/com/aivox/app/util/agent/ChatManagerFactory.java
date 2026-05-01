package com.aivox.app.util.agent;

/* JADX INFO: loaded from: classes.dex */
public class ChatManagerFactory {
    private static BaseChatManager<?> currentManager;
    private static ChatEngineType currentType;

    public static BaseChatManager<?> getManager(ChatEngineType chatEngineType) {
        BaseChatManager<?> baseChatManager;
        if (currentType == chatEngineType && (baseChatManager = currentManager) != null) {
            return baseChatManager;
        }
        synchronized (ChatManagerFactory.class) {
            currentType = chatEngineType;
            int i = C08461.$SwitchMap$com$aivox$app$util$agent$ChatEngineType[chatEngineType.ordinal()];
            if (i == 1) {
                currentManager = N8nManager.getInstance();
            } else if (i == 2) {
                currentManager = CozeManager.getInstance();
            }
        }
        return currentManager;
    }

    /* JADX INFO: renamed from: com.aivox.app.util.agent.ChatManagerFactory$1 */
    static /* synthetic */ class C08461 {
        static final /* synthetic */ int[] $SwitchMap$com$aivox$app$util$agent$ChatEngineType;

        static {
            int[] iArr = new int[ChatEngineType.values().length];
            $SwitchMap$com$aivox$app$util$agent$ChatEngineType = iArr;
            try {
                iArr[ChatEngineType.N8N.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aivox$app$util$agent$ChatEngineType[ChatEngineType.COZE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static BaseChatManager<?> getCurrent() {
        BaseChatManager<?> baseChatManager = currentManager;
        return baseChatManager == null ? getManager(ChatEngineType.N8N) : baseChatManager;
    }
}
