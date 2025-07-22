package net.towerquest.serialization;

import java.lang.reflect.Field;

import net.towerquest.serialization.prims.PrimString;
import net.towerquest.serialization.prims.Primitive;

public class EnumTypeHandler implements TypeHandler<Enum<?>, Primitive, Primitive> {

	@Override
	public boolean canEncode(Field f, Object obj) {
		return (obj instanceof Enum);
	}

	@Override
	public Primitive encode(Field f, Enum<?> obj, Serializer parent) {
		return new PrimString(obj.name());
	}

	@Override
	public boolean canDecode(Field f, SerializedDataType data) {
		return (Enum.class.isAssignableFrom(f.getType()));
	}

	@Override
	public Enum<?> decode(Field f, Primitive data, Deserializer parent) {
		return Enum.valueOf((Class<Enum>) f.getType(), ((PrimString)data).value);
	}
	
}