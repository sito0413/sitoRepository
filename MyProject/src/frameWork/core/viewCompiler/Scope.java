package frameWork.core.viewCompiler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import frameWork.core.viewCompiler.script.bytecode.ObjectScript;

public class Scope {
	private final Map<String, Class<?>> classMap = new HashMap<>();
	private Map<String, ObjectScript> objectMap = new HashMap<>();
	private final Deque<Map<String, ObjectScript>> objectMapQueue = new ArrayDeque<>();
	
	public void startScope() {
		if (objectMap != null) {
			objectMapQueue.offerFirst(objectMap);
		}
		objectMap = new HashMap<>();
	}
	
	public void endScope() {
		objectMap = objectMapQueue.pollFirst();
		
	}
	
	public void put(final String key, final ObjectScript object) {
		Map<String, ObjectScript> map = objectMap;
		if (!map.containsKey(key)) {
			for (final Map<String, ObjectScript> m : objectMapQueue) {
				if (m.containsKey(key)) {
					map = m;
					break;
				}
			}
		}
		map.put(key, object);
	}
	
	public ObjectScript get(final String key) {
		ObjectScript object = objectMap.get(key);
		if (object == null) {
			for (final Map<String, ObjectScript> map : objectMapQueue) {
				object = map.get(key);
				if (object != null) {
					break;
				}
			}
		}
		return object;
	}
	
	public Class<?> getImport(final String key) {
		return classMap.get(key);
	}
	
	public void putImport(final String key, final Class<?> c) {
		classMap.put(key, c);
	}
	
	public void removeImport(final String key) {
		classMap.remove(key);
	}
	
}
