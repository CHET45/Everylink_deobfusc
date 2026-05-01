package com.aivox.app.util;

import com.azure.xml.implementation.aalto.util.XmlConsts;
import com.microsoft.azure.storage.table.TableQuery;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: PhotoIntentUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, m1901d2 = {"Lcom/aivox/app/util/PhotoIntentUtil;", "", "()V", "Companion", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class PhotoIntentUtil {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Regex PHOTO_INTENT_PATTERN = new Regex("(?i)(?:\\b(?:拍(?:照|张照|照片|个照|一下|张照片|个照片|下来)?|捕获|记录|截屏|截图|抓拍)\\b|\\b(?:这(?:个|是什么)?|识别|辨认|辨别|扫描)(?:一下|这个|眼前|图片|物品|物体|文字|场景)?\\b|\\b(?:帮我|请)?(?:读一下|看看|看一下|描述|告诉我)(?:这个|眼前)?\\b|\\b(?:眼前|当前|现在)(?:有什么|是什么|的场景?)?\\b|\\b(?:take(?: a)? (?:photo|picture|shot)|snap(?: a)? (?:photo|picture)?|capture|screenshot)\\b|\\b(?:recogni[sz]e|identify|scan|detect)(?: (?:this|that|it))?\\b|\\bwhat(?:'s| is) (?:this|that|it|am I looking at)\\??\\b|\\b(?:read|describe|tell me about) (?:this|that|it)\\b)");

    /* JADX INFO: compiled from: PhotoIntentUtil.kt */
    @Metadata(m1900d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, m1901d2 = {"Lcom/aivox/app/util/PhotoIntentUtil$Companion;", "", "()V", "PHOTO_INTENT_PATTERN", "Lkotlin/text/Regex;", "detectPhotoIntentAdvanced", "", "text", "", "hasContextualPhotoIntent", "hasNegativeWords", "hasSimplePhotoIntent", "hasStrongNegativeContext", "negativeWord", "app_everylinkRelease"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean detectPhotoIntentAdvanced(String text) {
            Intrinsics.checkNotNullParameter(text, "text");
            if (StringsKt.contains$default((CharSequence) text, (CharSequence) "看到了什么", false, 2, (Object) null) || hasSimplePhotoIntent(text)) {
                return true;
            }
            return !hasNegativeWords(text) && hasContextualPhotoIntent(text);
        }

        private final boolean hasSimplePhotoIntent(String text) {
            return PhotoIntentUtil.PHOTO_INTENT_PATTERN.containsMatchIn(text);
        }

        private final boolean hasNegativeWords(String text) {
            List<String> listListOf = CollectionsKt.listOf((Object[]) new String[]{"不", "别", "不要", "无需", "不需要", "停止", "取消", "don't", "do not", TableQuery.Operators.NOT, "stop", "cancel", XmlConsts.XML_SA_NO, "never", "without"});
            if ((listListOf instanceof Collection) && listListOf.isEmpty()) {
                return false;
            }
            for (String str : listListOf) {
                if (StringsKt.contains((CharSequence) text, (CharSequence) str, true) && PhotoIntentUtil.INSTANCE.hasStrongNegativeContext(text, str)) {
                    return true;
                }
            }
            return false;
        }

        private final boolean hasStrongNegativeContext(String text, String negativeWord) {
            List listListOf = CollectionsKt.listOf((Object[]) new Regex[]{new Regex(negativeWord + ".*(?:拍|识别|照)", RegexOption.IGNORE_CASE), new Regex(negativeWord + ".*要", RegexOption.IGNORE_CASE), new Regex("无需.*(?:拍|识别)", RegexOption.IGNORE_CASE), new Regex("不需要.*(?:拍照|识别)", RegexOption.IGNORE_CASE), new Regex("(?:don't|do not|stop|cancel).*(?:take|snap|capture|scan|recogni[sz]e|identify|photo|picture)", RegexOption.IGNORE_CASE), new Regex("no (?:more )?(?:photos?|pictures?|scanning|recognition)", RegexOption.IGNORE_CASE), new Regex("without (?:taking|snapping|scanning)", RegexOption.IGNORE_CASE), new Regex(negativeWord + ".*(?:take|snap|capture|scan|photo|picture)", RegexOption.IGNORE_CASE)});
            if ((listListOf instanceof Collection) && listListOf.isEmpty()) {
                return false;
            }
            Iterator it = listListOf.iterator();
            while (it.hasNext()) {
                if (((Regex) it.next()).containsMatchIn(text)) {
                    return true;
                }
            }
            return false;
        }

        private final boolean hasContextualPhotoIntent(String text) {
            List listListOf = CollectionsKt.listOf((Object[]) new Regex[]{new Regex("(帮|给|替我|帮我|开始).*拍", RegexOption.IGNORE_CASE), new Regex("(想|要|需要).*(照片|照|识别|辨认)", RegexOption.IGNORE_CASE), new Regex("(记录|留念|保存).*一下", RegexOption.IGNORE_CASE), new Regex("这个.*(拍|识别|什么)", RegexOption.IGNORE_CASE), new Regex("眼前.*(拍|识别|什么)", RegexOption.IGNORE_CASE), new Regex("(can you|could you|would you|please|start).*(take|snap|capture|scan|recogni[sz]e|identify)", RegexOption.IGNORE_CASE), new Regex("I (?:want to|need to|'d like to|wanna).*(take|snap|capture|scan|recogni[sz]e)", RegexOption.IGNORE_CASE), new Regex("I (?:need|want) a (?:photo|picture|scan)", RegexOption.IGNORE_CASE), new Regex("(take|snap|capture|scan|recogni[sz]e|identify) (?:this|that|it)", RegexOption.IGNORE_CASE), new Regex("what (?:am I looking at|do I see)", RegexOption.IGNORE_CASE)});
            if ((listListOf instanceof Collection) && listListOf.isEmpty()) {
                return false;
            }
            Iterator it = listListOf.iterator();
            while (it.hasNext()) {
                if (((Regex) it.next()).containsMatchIn(text)) {
                    return true;
                }
            }
            return false;
        }
    }
}
