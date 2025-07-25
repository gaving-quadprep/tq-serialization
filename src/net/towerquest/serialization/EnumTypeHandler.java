package net.towerquest.serialization;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.towerquest.serialization.prims.PrimInt;
import net.towerquest.serialization.prims.PrimString;
import net.towerquest.serialization.prims.Primitive;

public class EnumTypeHandler implements TypeHandler<Enum<?>, Primitive<?>, Primitive<?>> {

	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		return (obj instanceof Enum);
	}

	@Override
	public Primitive<?> encode(DataContext dc, Enum<?> obj, Serializer parent) {
		for (Annotation a : dc.annotations)
			if (a instanceof UseOrdinal)
				return new PrimInt(obj.ordinal());
		return new PrimString(obj.name());
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		return (Enum.class.isAssignableFrom(dc.clazz));
	}

	@Override
	public Enum<?> decode(DataContext dc, Primitive<?> data, Deserializer parent) {
		if (data instanceof PrimInt)
			try {
				Method m = dc.clazz.getMethod("values");
				m.setAccessible(true);
				return ((Enum[])m.invoke(null))
						[((PrimInt)data).value];
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		return Enum.valueOf((Class<Enum>) dc.clazz, ((PrimString)data).value);
	}
	
}