package frameWork.core.authority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.TYPE, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {
	public String ANONYMOUS = "_anonymous";
	public String USER = "_user";
	
	String[] allowRoll() default {
		USER
	};
}