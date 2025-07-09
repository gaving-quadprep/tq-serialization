package net.towerquest.serialization.prims;

import net.towerquest.serialization.SerializedDataType;

public abstract class Primitive implements SerializedDataType {
	/**
	 * Does not handle arrays (literally 1984)
	 */
	public static Class<? extends Primitive> getPrimitiveClassFromType(Class<?> type) {
		if(!type.isPrimitive())
			return null;
		if(type == Boolean.TYPE)
			return PrimBoolean.class;
		if(type == Byte.TYPE)
			return PrimByte.class;
		if(type == Double.TYPE)
			return PrimDouble.class;
		if(type == Float.TYPE)
			return PrimFloat.class;
		if(type == Integer.TYPE)
			return PrimInt.class;
		if(type == Long.TYPE)
			return PrimLong.class;
		if(type == Short.TYPE)
			return PrimShort.class;
		// java stirng ca
		if(type == String.class)
			return PrimString.class;
		return null;
	}
}
