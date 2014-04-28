package frameWork.core.viewCompiler.script;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;

@SuppressWarnings("rawtypes")
public class Scope {
	private final Map<String, Class<?>> classMap = new HashMap<>();
	private Map<String, InstanceBytecode> objectMap = new HashMap<>();
	private final Deque<Map<String, InstanceBytecode>> objectMapQueue = new ArrayDeque<>();
	
	public void startScope() {
		if (objectMap != null) {
			objectMapQueue.offerFirst(objectMap);
		}
		objectMap = new HashMap<>();
	}
	
	public void endScope() {
		objectMap = objectMapQueue.pollFirst();
		
	}
	
	public void put(final String key, final Object object) {
		put(key, new ObjectBytecode(object.getClass(), object));
	}
	
	public InstanceBytecode put(final String key, final InstanceBytecode object) {
		Map<String, InstanceBytecode> map = objectMap;
		if (!map.containsKey(key)) {
			for (final Map<String, InstanceBytecode> m : objectMapQueue) {
				if (m.containsKey(key)) {
					map = m;
					break;
				}
			}
		}
		map.put(key, object);
		return object;
	}
	
	public InstanceBytecode get(final Class type, final String key) {
		InstanceBytecode object = objectMap.get(key);
		if (object == null) {
			for (final Map<String, InstanceBytecode> map : objectMapQueue) {
				object = map.get(key);
				if (object != null) {
					break;
				}
			}
		}
		if (object == null) {
			object = new ObjectBytecode(type, null);
		}
		return object;
	}
	
	public Class<?> getImport(final String key) throws ScriptException {
		ClassNotFoundException exception = null;
		Class<?> class1 = classMap.get(key);
		if (class1 == null) {
			try {
				class1 = Class.forName("java.lang." + key);
				classMap.put(key, class1);
			}
			catch (final ClassNotFoundException e) {
				exception = e;
			}
		}
		if ((class1 == null) && key.equals("var")) {
			class1 = Object.class;
			classMap.put(key, Object.class);
		}
		
		if ((class1 == null) && (exception != null)) {
			throw ScriptException.IllegalStateException(exception);
		}
		return class1;
	}
	
	public void putImport(final String key, final Class<?> c) {
		classMap.put(key, c);
	}
	
	public void removeImport(final String key) {
		classMap.remove(key);
	}
}
