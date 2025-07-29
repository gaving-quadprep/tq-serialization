package net.towerquest.serialization;

public class SerializableObjectTypeHandler implements TypeHandler<Serializable, SerializedDataType, SerializedDataType> {
	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		return (obj instanceof Serializable);
	}

	@Override
	public SerializedDataType encode(DataContext dc, Serializable obj, Serializer parent) {
		return obj.serialize(parent);
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		return Serializable.class.isAssignableFrom(dc.clazz);
	}

	@Override
	public Serializable decode(DataContext dc, SerializedDataType data, Deserializer parent) {
		Class<Serializable> type = (Class<Serializable>) dc.clazz;
		Serializable obj = parent.create(type, data);
		if (obj != null)
			obj.deserialize(data, parent);
		return obj;
	}

}
