package frameWork.base.core.viewCompiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;

import frameWork.base.core.state.AttributeMap;
import frameWork.base.core.viewCompiler.Scope;
import frameWork.base.core.viewCompiler.ViewCompiler;
import frameWork.base.core.viewCompiler.ViewerWriter;

public class ViewCompilerTest {
	//ViewChareet
	//ViewSrcReadBuffer
	public static void main(final String[] args) throws Throwable {
		final Scope scope = new Scope();
		final ViewerWriter out = new ViewerWriter();
		scope.startScope();
		scope.put("out", out);
		scope.put("session", new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return "";
			}
		});
		scope.put("application", new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				return null;
			}
		});
		scope.put("request", new AttributeMap() {
			
			@Override
			public void setAttribute(final String name, final Object value) {
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				return null;
			}
			
			@Override
			public Object getAttribute(final String name) {
				if (name.equals("data")) {
					final List<List<List<String>>> list1 = new ArrayList<>();
					for (int i = 0; i < 50; i++) {
						final List<List<String>> list2 = new ArrayList<>();
						for (int j = 0; j < 50; j++) {
							final List<String> list3 = new ArrayList<>();
							for (int k = 0; k < 100; k++) {
								list3.add("test" + i + " " + j + " " + k);
							}
							list2.add(list3);
						}
						list1.add(list2);
					}
					return list1;
				}
				return "";
			}
		});
		JOptionPane.showMessageDialog(null, "s");
		ViewCompiler.parse(new File("input.jsp"), null, scope);
		JOptionPane.showMessageDialog(null, "e");
	}
	
	//	@Test
	//	public void isNull() {
	//		assertNull(TargetFilter.parse(null));
	//	}
}
