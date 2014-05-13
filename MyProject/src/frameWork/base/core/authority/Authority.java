package frameWork.base.core.authority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.TYPE, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
	Role[] allowRole() default {
		Role.USER
	};
}