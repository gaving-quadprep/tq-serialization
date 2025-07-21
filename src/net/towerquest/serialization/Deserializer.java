package net.towerquest.serialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Deserializer {
	
	private List<TypeHandler> typeHandlers = new ArrayList<TypeHandler>();
	
	public Object decode(Field f, SerializedDataType data) {
		for (TypeHandler th : typeHandlers) {
			if (th.canDecode(f, data)) {
				Object ret = th.decode(f, data, this);
				return ret;
			}
		}
		return null;
	}
	public <T extends Serializable> T deserializeObject(Class<T> clazz, SerializedDataType sdt) {
		// I'm hungry
		Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
		assert constructors.length > 0 : "An object must have constructors to be deserialized";
		
		T t;
		for (Constructor c : constructors) {
			c.setAccessible(true);
			if (c.getParameterCount() == 0) {
				try {
					t = (T) c.newInstance();
					c.setAccessible(false);
					break;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			c.setAccessible(false);
		}
		
		
		for (Field f : SerializationUtils.getAllFields(clazz)) {
			f.setAccessible(true);
			if (!Modifier.isTransient(f.getModifiers())) {
				
			}
			f.setAccessible(false);
		}
		
		return null;
	}
	public Serializable deserialize(SerializedDataType sdt) {
		return null;
	}
}
