package net.towerquest.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.towerquest.serialization.prims.*;

public class Serializer {
	
	private List<TypeHandler> typeHandlers = new ArrayList<TypeHandler>();
	
	private Map<Object, SerializedDataType> objectMap = new HashMap<Object, SerializedDataType>();
	
	public SerializedDataType encode(Field f, Object obj) {
		for (TypeHandler th : typeHandlers) {
			if (th.canEncode(f, obj)) {
				SerializedDataType ret = th.encode(f, obj, this);
				objectMap.putIfAbsent(obj, ret);
				return ret;
			}
		}
		return null;
	}
	
	
	
	public SerializedData serializeObject(Serializable s) {
		SerializedData root = new SerializedData();
		
		List<Field> fields = SerializationUtils.getAllFields(s.getClass());
		
		try {
			for (Field f : fields) {
				f.setAccessible(true);
				if (!Modifier.isTransient(f.getModifiers())) {
					String name = f.getName();
					Object obj = f.get(s);
					if (obj != null) {
						SerializedDataType val = encode(f, obj);
						root.values.put(name, val);
					}
				}
				f.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return root;
		
	}
	public SerializedData serialize(Serializable s) {
		objectMap.clear();
		return serializeObject(s);
	}
	
	public Serializer() {
		typeHandlers.add(new PointerTypeHandler());
		typeHandlers.add(new ListTypeHandler());
		typeHandlers.add(new PrimitiveTypeHandler());
		typeHandlers.add(new SerializableObjectTypeHandler());
		typeHandlers.add(new EnumTypeHandler());
		typeHandlers.add(new ColorTypeHandler());
	}
}
