package net.towerquest.serialization.prims;

import java.lang.reflect.Field;

import net.towerquest.serialization.SerializedDataType;
import net.towerquest.serialization.Serializer;
import net.towerquest.serialization.TypeHandler;

public class PrimitiveTypeHandler implements TypeHandler<Object, Primitive> {
	@Override
	public boolean canHandle(Object obj) {
		Class<?> type = obj.getClass();
		return (type.isPrimitive() || (
				obj instanceof Boolean ||
				obj instanceof Byte ||
				obj instanceof Double ||
				obj instanceof Float ||
				obj instanceof Integer ||
				obj instanceof Long ||
				obj instanceof Short ||
				obj instanceof String));
	}

	@Override
	public Primitive encode(Object obj, Serializer parent) {
		if(obj instanceof Boolean)
			return new PrimBoolean((Boolean)obj);
		if(obj instanceof Byte)
			return new PrimByte((Byte)obj);
		if(obj instanceof Double)
			return new PrimDouble((Double)obj);
		if(obj instanceof Float)
			return new PrimFloat((Float)obj);
		if(obj instanceof Integer)
			return new PrimInt((Integer)obj);
		if(obj instanceof Long)
			return new PrimLong((Long)obj);
		if(obj instanceof Short)
			return new PrimShort((Short)obj);
		if(obj instanceof String)
			return new PrimString((String)obj);
		return null;
	}

}
