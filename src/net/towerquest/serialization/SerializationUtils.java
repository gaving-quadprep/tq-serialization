package net.towerquest.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class SerializationUtils {
	public static <T> List<Field> getAllFields(Class<T> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		List<Field> fieldList = new ArrayList<Field>();
		for (Field f : fields)
			if (!Modifier.isStatic(f.getModifiers()))
				fieldList.add(f);
		Class<? super T> superClass = clazz.getSuperclass();
		if (superClass != Object.class)
			fieldList.addAll(getAllFields(superClass));
		return fieldList;
	}
}
