package frameWork.base.core.authority;

import java.lang.reflect.Method;

public final class AuthorityChecker {
	
	public static <T> boolean check(final Class<T> className, final Method methodName, final Role... roles) {
		return checkAuthority(className.getAnnotation(Authority.class), roles)
		        && checkAuthority(methodName.getAnnotation(Authority.class), roles);
	}
	
	static boolean checkAuthority(final Authority annotation, final Role[] roles) {
		if (annotation != null) {
			for (final Role allowRole : annotation.allowRole()) {
				for (final Role role : roles) {
					if (role.equals(allowRole)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}