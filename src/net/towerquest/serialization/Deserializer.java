package net.towerquest.serialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import net.towerquest.serialization.prims.PrimitiveTypeHandler;

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
	public <T extends Serializable> T deserializeObject(Class<T> clazz, SerializedData sd) {
		// I'm hungry
		Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
		assert constructors.length > 0 : "An object must have constructors to be deserialized";
		
		T t = null;
		for (Constructor<T> c : constructors) {
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
		
		assert t != null : "No constructors without parameters found";
		
		for (Field f : SerializationUtils.getAllFields(clazz)) {
			f.setAccessible(true);
			if (!Modifier.isTransient(f.getModifiers())) {
				PreviousName prevName = f.getAnnotation(PreviousName.class);
				String fieldName;
				if (prevName != null) {
					fieldName = prevName.value();
				} else {
					fieldName = f.getName();
				}
				SerializedDataType fieldData = sd.get(fieldName);
				System.out.println(fieldData);
				if (fieldData != null) {
					Object decoded = decode(f, fieldData);
					System.out.println(decoded);
					if (decoded != null) {
						System.out.println("not null: " + fieldName);
						try {
							f.set(t, decoded);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						System.out.println("null: " + fieldName);
					}
				}
			}
			f.setAccessible(false);
		}
		
		return t;
	}
	public <T extends Serializable> T deserialize(SerializedData sdt, Class<T> type) {
		return deserializeObject(type, sdt);
	}
	public Deserializer() {
		typeHandlers.add(new PointerTypeHandler());
		typeHandlers.add(new ListTypeHandler());
		typeHandlers.add(new PrimitiveTypeHandler());
		typeHandlers.add(new SerializableObjectTypeHandler());
		typeHandlers.add(new EnumTypeHandler());
		typeHandlers.add(new ColorTypeHandler());
	}
}
