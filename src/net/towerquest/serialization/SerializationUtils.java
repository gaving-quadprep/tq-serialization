package net.towerquest.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public abstract class SerializationUtils {
	public static <T extends Serializable> List<Field> getAllFields(Class<T> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers())) {
				System.out.println(f);
			}
					
		}
		Class<T> superClass = (Class<T>) clazz.getSuperclass();
		if (superClass == Object.class)
			return null;
		else
			return getAllFields((Class<T>) clazz.getSuperclass());
	}
}
