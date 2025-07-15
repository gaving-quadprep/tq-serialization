package net.towerquest.serialization;

import java.lang.reflect.Field;

public class SerializableObjectTypeHandler implements TypeHandler<Serializable, SerializedData> {
	@Override
	public boolean canEncode(Field f, Object obj) {
		return (obj instanceof Serializable);
	}

	@Override
	public SerializedData encode(Field f, Serializable obj, Serializer parent) {
		return parent.serialize(obj);
	}

	@Override
	public boolean canDecode(Field f, SerializedData data) {
		return Serializable.class.isAssignableFrom(f.getType());
	}

	@Override
	public Serializable decode(Field f, SerializedData data, Deserializer parent) {
		return 	parent.deserialize(data);
	}

}
