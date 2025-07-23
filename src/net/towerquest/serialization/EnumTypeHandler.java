package net.towerquest.serialization;

import java.lang.reflect.Field;

import net.towerquest.serialization.prims.PrimString;
import net.towerquest.serialization.prims.Primitive;

public class EnumTypeHandler implements TypeHandler<Enum<?>, Primitive, Primitive> {

	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		return (obj instanceof Enum);
	}

	@Override
	public Primitive encode(DataContext dc, Enum<?> obj, Serializer parent) {
		return new PrimString(obj.name());
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		return (Enum.class.isAssignableFrom(dc.clazz));
	}

	@Override
	public Enum<?> decode(DataContext dc, Primitive data, Deserializer parent) {
		return Enum.valueOf((Class<Enum>) dc.clazz, ((PrimString)data).value);
	}
	
}