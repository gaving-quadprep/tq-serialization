package net.towerquest.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import net.towerquest.serialization.prims.*;

public class Serializer {
	
	private List<TypeHandler> typeHandlers = new ArrayList<TypeHandler>();
	
	public SerializedDataType encode(Object obj) {
		for (TypeHandler th : typeHandlers) {
			if (th.canEncode(obj)) {
				return th.encode(obj, this);
			}
		}
		return null;
	}
	
	public SerializedData serialize(Serializable s) {
		SerializedData root = new SerializedData();
		
		List<Field> fields = SerializationUtils.getAllFields(s.getClass());
		
		try {
			for (Field f : fields) {
				f.setAccessible(true);
				if (!Modifier.isTransient(f.getModifiers())) {
					String name = f.getName();
					Object obj = f.get(s);
					if (obj != null) {
						SerializedDataType val = encode(obj);
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
	
	public Serializer() {
		typeHandlers.add(new ListTypeHandler());
		typeHandlers.add(new PrimitiveTypeHandler());
		typeHandlers.add(new SerializableObjectTypeHandler());
		typeHandlers.add(new EnumTypeHandler());
		typeHandlers.add(new ColorTypeHandler());
	}
}
