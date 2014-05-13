package frameWork.base.core.authority;

import java.lang.reflect.Method;


public class AuthorityChecker {
	
	public static <T> boolean check(final Class<T> c, final Method method, final Role... authStrings) {
		return checkAuthority(c.getAnnotation(Authority.class), authStrings)
		        && checkAuthority(method.getAnnotation(Authority.class), authStrings);
	}
	
	static boolean checkAuthority(final Authority annotation, final Role[] authStrings) {
		if (annotation != null) {
			for (final Role roll : annotation.allowRole()) {
				for (final Role auth : authStrings) {
					if (auth.equals(roll)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}