package net.towerquest.serialization;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class DataContext {
	public Class<?> clazz;
	public Annotation[] annotations;
	public DataContext(Class<?> clazz, Annotation[] annotations) {
		this.clazz = clazz;
		this.annotations = annotations;
	}
	public DataContext(Field f) {
		this.clazz = f.getType();
		this.annotations = f.getAnnotations();
	}
}
