package frameWork.core.targetFilter;

import frameWork.core.fileSystem.FileSystem;

public class TargetFilter {
	public static TargetFilter parse(final String target) {
		if (target == null) {
			return null;
		}
		
		final String[] uri = (target.startsWith("/") ? target.substring(1) : target).split("/");
		final StringBuilder className = new StringBuilder();
		final String methodName;
		if (uri.length == 0) {
			className.append("Index");
			methodName = "index";
		}
		else if (uri.length == 1) {
			if (uri[0].isEmpty()) {
				className.append("Index");
			}
			else {
				className.append(Character.toUpperCase(uri[0].charAt(0))).append(uri[0].substring(1));
			}
			methodName = "index";
		}
		else {
			final int index = uri.length - 2;
			for (int j = 0; j < index; j++) {
				if (uri[j].contains(".")) {
					return null;
				}
				className.append(uri[j]).append('.');
			}
			
			final String dummyClassName = uri[index];
			if (dummyClassName.contains(".")) {
				return null;
			}
			className.append(Character.toUpperCase(dummyClassName.charAt(0))).append(dummyClassName.substring(1));
			
			final String dummyMethodName = uri[index + 1];
			if (dummyMethodName.contains(".")) {
				methodName = dummyMethodName.split("\\.")[0];
			}
			else {
				methodName = dummyMethodName;
			}
		}
		return new TargetFilter(className.toString(), methodName);
	}
	
	public final String className;
	public final String methodName;
	public final String view;
	
	TargetFilter(final String className, final String methodName) {
		this.className = FileSystem.Config.getString("packageName", "controller") + "." + className;
		this.methodName = methodName;
		view = "/" + className.replace(".", "/") + "/" + methodName + ".jsp";
	}
	
	@Override
	public String toString() {
		return className + "." + methodName;
	}
	
	@Override
	public boolean equals(final Object obj) {
		return toString().equals(obj.toString());
	}
	
}
