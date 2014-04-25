package frameWork.core.viewCompiler;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import frameWork.core.viewCompiler.script.bytecode.ByteBytecode;
import frameWork.core.viewCompiler.script.bytecode.DoubleBytecode;
import frameWork.core.viewCompiler.script.bytecode.FloatBytecode;
import frameWork.core.viewCompiler.script.bytecode.InstanceBytecode;
import frameWork.core.viewCompiler.script.bytecode.IntegerBytecode;
import frameWork.core.viewCompiler.script.bytecode.LongBytecode;
import frameWork.core.viewCompiler.script.bytecode.ObjectBytecode;
import frameWork.core.viewCompiler.script.bytecode.ShortBytecode;
import frameWork.core.viewCompiler.script.expression.BooleanScript;
import frameWork.core.viewCompiler.script.expression.CharacterScript;
import frameWork.core.viewCompiler.script.expression.StringScript;

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
	
	public InstanceBytecode declaration(final String type, final String key) {
		switch ( type ) {
			case "long" :
				return put(key, new LongBytecode(0));
			case "int" :
				return put(key, new IntegerBytecode(0));
			case "short" :
				return put(key, new ShortBytecode((short) 0));
			case "byte" :
				return put(key, new ByteBytecode((byte) 0));
			case "char" :
				return put(key, new CharacterScript((char) 0));
			case "double" :
				return put(key, new DoubleBytecode(0));
			case "float" :
				return put(key, new FloatBytecode(0));
			case "boolean" :
				return put(key, new BooleanScript(false));
			default :
				return put(key, new ObjectBytecode(getImport(type), null));
		}
	}
	
	public InstanceBytecode create(@SuppressWarnings("rawtypes") final Class type, final Object obj) {
		if (obj == null) {
			return new ObjectBytecode(type, null);
		}
		else if (obj instanceof Long) {
			return new LongBytecode((Long) obj);
		}
		else if (obj instanceof Integer) {
			return new IntegerBytecode((Integer) obj);
		}
		else if (obj instanceof Short) {
			return new ShortBytecode((Short) obj);
		}
		else if (obj instanceof Byte) {
			return new ByteBytecode((Byte) obj);
		}
		else if (obj instanceof Character) {
			return new CharacterScript((Character) obj);
		}
		else if (obj instanceof Double) {
			return new DoubleBytecode((Double) obj);
		}
		else if (obj instanceof Float) {
			return new FloatBytecode((Float) obj);
		}
		else if (obj instanceof String) {
			return new StringScript((String) obj);
		}
		else if (obj instanceof Boolean) {
			return new BooleanScript((Boolean) obj);
		}
		
		return new ObjectBytecode(type, obj);
	}
	
	public void put(final String key, final Object object) {
		put(key, create(object.getClass(), object));
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
	
	public InstanceBytecode get(@SuppressWarnings("rawtypes") final Class type, final String key) {
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
	
	public Class<?> getImport(final String key) {
		Class<?> class1 = classMap.get(key);
		if (class1 == null) {
			try {
				class1 = Class.forName("java.lang." + key);
				classMap.put(key, class1);
			}
			catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if ((class1 == null) && key.equals("var")) {
			class1 = Object.class;
			classMap.put(key, Object.class);
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
