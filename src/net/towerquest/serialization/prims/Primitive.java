package net.towerquest.serialization.prims;

import net.towerquest.serialization.SerializedDataType;

public abstract class Primitive implements SerializedDataType {
	/**
	 * Does not handle arrays (literally 1984)
	 */
	
	public Object value;
	
	public static Class<? extends Primitive> getPrimitiveClassFromType(Class<?> clazz) {
		if (clazz == Boolean.TYPE || clazz == Boolean.class)
			return PrimBoolean.class;
		if (clazz == Byte.TYPE || clazz == Byte.class)
			return PrimByte.class;
		if (clazz == Double.TYPE || clazz == Double.class)
			return PrimDouble.class;
		if (clazz == Float.TYPE || clazz == Float.class)
			return PrimFloat.class;
		if (clazz == Integer.TYPE || clazz == Integer.class)
			return PrimInt.class;
		if (clazz == Long.TYPE || clazz == Long.class)
			return PrimLong.class;
		if (clazz == Short.TYPE || clazz == Short.class)
			return PrimShort.class;
		if (clazz == String.class)
			return PrimString.class;
		return null;
	}
}
