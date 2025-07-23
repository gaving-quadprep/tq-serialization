package net.towerquest.serialization;

import java.lang.reflect.Field;

public class SerializableObjectTypeHandler implements TypeHandler<Serializable, SerializedData, SerializedData> {
	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		return (obj instanceof Serializable);
	}

	@Override
	public SerializedData encode(DataContext dc, Serializable obj, Serializer parent) {
		return parent.serializeObject(obj);
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		return Serializable.class.isAssignableFrom(dc.clazz) && data instanceof SerializedData;
	}

	@Override
	public Serializable decode(DataContext dc, SerializedData data, Deserializer parent) {
		return parent.deserialize(data, (Class<Serializable>) dc.clazz);
	}

}
