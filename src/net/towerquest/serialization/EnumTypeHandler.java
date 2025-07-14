package net.towerquest.serialization;

import java.lang.reflect.Field;

import net.towerquest.serialization.prims.PrimString;

public class EnumTypeHandler implements TypeHandler<Enum, SerializedDataType> {

	@Override
	public boolean canEncode(Object obj) {
		return (obj instanceof Enum);
	}

	@Override
	public SerializedDataType encode(Enum obj, Serializer parent) {
		return new PrimString(obj.name());
	}

	@Override
	public boolean canDecode(Field f, SerializedDataType data) {
		return (Enum.class.isAssignableFrom(f.getType()));
	}

	@Override
	public Enum decode(Field f, SerializedDataType data, Deserializer parent) {
		return Enum.valueOf((Class<Enum>) f.getType(), ((PrimString)data).value);
	}
	
}