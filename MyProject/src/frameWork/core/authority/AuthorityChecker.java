package frameWork.core.authority;

import java.lang.reflect.Method;

public class AuthorityChecker {
	
	public static <T> boolean check(final Class<T> c, final Method method, final String... authStrings) {
		return checkAuthority(c.getAnnotation(Authority.class), authStrings)
		        && checkAuthority(method.getAnnotation(Authority.class), authStrings);
	}
	
	static boolean checkAuthority(final Authority annotation, final String[] authStrings) {
		if (annotation != null) {
			final String[] allowRoll = annotation.allowRoll();
			for (final String roll : allowRoll) {
				for (final String auth : authStrings) {
					if (auth.equalsIgnoreCase(roll)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}