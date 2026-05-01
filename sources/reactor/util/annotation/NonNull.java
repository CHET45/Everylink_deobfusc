package reactor.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/* JADX INFO: loaded from: classes5.dex */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Nonnull
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface NonNull {
}
