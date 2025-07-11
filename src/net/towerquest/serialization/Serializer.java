	package net.towerquest.serialization;

import java.lang.reflect.Field;
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
				String name = f.getName();
				SerializedDataType val = encode(f.get(s));
				root.values.put(name, val);
				f.setAccessible(false);
			}
		} catch (Exception e) {
			
		}
		
		return root;
		
	}
}
