package net.towerquest.serialization.prims;

import net.towerquest.serialization.SerializedDataType;

public abstract class Primitive<T> implements SerializedDataType {

	public T value;
	
	public Primitive(T value) {
		this.value = value;
	}
	
	public Primitive(Primitive<?> p) {
		if (p.getClass() == this.getClass())
			this.value = (T) p.value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	/**
	 * Does not handle arrays (literally 1984)
	 */
	public static Class<? extends Primitive<?>> getPrimitiveClassFromType(Class<?> clazz) {
		if (clazz == boolean.class || clazz == Boolean.class)
			return PrimBoolean.class;
		if (clazz == byte.class || clazz == Byte.class)
			return PrimByte.class;
		if (clazz == double.class || clazz == Double.class)
			return PrimDouble.class;
		if (clazz == float.class || clazz == Float.class)
			return PrimFloat.class;
		if (clazz == int.class || clazz == Integer.class)
			return PrimInt.class;
		if (clazz == long.class || clazz == Long.class)
			return PrimLong.class;
		if (clazz == short.class || clazz == Short.class)
			return PrimShort.class;
		if (clazz == String.class)
			return PrimString.class;
		return null;
	}
	
	public static Class<?> primitiveToWrapper(Class<?> prim) {
		assert prim.isPrimitive() : "The provided class is not primitive";

		if (prim == boolean.class)
			return Boolean.class;
		if (prim == byte.class)
			return Byte.class;
		if (prim == double.class)
			return Double.class;
		if (prim == float.class)
			return Float.class;
		if (prim == int.class)
			return Integer.class;
		if (prim == long.class)
			return Long.class;
		if (prim == short.class)
			return Short.class;
		return (Class<?>) (Object) "Ever wonder what happens when you mix hot lava and chicken? I did, and you're about to find out.";
	}
}
