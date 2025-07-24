package net.towerquest.serialization.prims;

import java.lang.annotation.Annotation;

import net.towerquest.serialization.DataContext;
import net.towerquest.serialization.Deserializer;
import net.towerquest.serialization.ScaleBy;
import net.towerquest.serialization.SerializedDataType;
import net.towerquest.serialization.Serializer;
import net.towerquest.serialization.TypeHandler;

public class PrimitiveTypeHandler implements TypeHandler<Object, Primitive<?>, Primitive<?>> {
	@Override
	public boolean canEncode(DataContext dc, Object obj) {
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
	public Primitive<?> encode(DataContext dc, Object obj, Serializer parent) {
		double scaleBy = 1;
		for (Annotation a : dc.annotations)
			if (a instanceof ScaleBy)
				scaleBy = ((ScaleBy) a).value();
		Primitive<?> ret = null;
		if(obj instanceof Boolean)
			ret = new PrimBoolean((Boolean)obj);
		if(obj instanceof Byte)
			ret = new PrimByte((Byte)obj);
		if(obj instanceof Double)
			ret = new PrimDouble((Double)obj);
		if(obj instanceof Float)
			ret = new PrimFloat((Float)obj);
		if(obj instanceof Integer)
			ret = new PrimInt((Integer)obj);
		if(obj instanceof Long)
			ret = new PrimLong((Long)obj);
		if(obj instanceof Short)
			ret = new PrimShort((Short)obj);
		if(obj instanceof String)
			ret = new PrimString((String)obj);
		
		if (ret instanceof PrimNumber && scaleBy != 1)
			((PrimNumber<?>)ret).scaleBy(scaleBy);
		return ret;
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		Class<?> type = dc.clazz;
		return (type.isPrimitive() || 
				type == Boolean.class ||
				type == Byte.class ||
				type == Double.class ||
				type == Float.class ||
				type == Integer.class ||
				type == Long.class ||
				type == Short.class ||
				type == String.class);
	}

	@Override
	public Object decode(DataContext dc, Primitive<?> data, Deserializer parent) {
		Primitive<?> ret;
		try {
			ret = Primitive.getPrimitiveClassFromType(dc.clazz)
					.getConstructor(Primitive.class)
					.newInstance(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret = data;
		}
		
		double scaleBy = 1;
		for (Annotation a : dc.annotations)
			if (a instanceof ScaleBy)
				scaleBy = ((ScaleBy) a).value();
		
		if (ret instanceof PrimNumber && scaleBy != 1)
			((PrimNumber<?>)ret).scaleBy(1/scaleBy);
		
		return ret.value;
	}

}
