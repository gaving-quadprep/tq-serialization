package net.towerquest.serialization;

public interface Serializable {
	public default SerializedDataType serialize(Serializer serializer) {
		return serializer.serializeObject(this);
	}
	
	// erm this happens after regular deserialization
	public default void deserialize(SerializedDataType sd, Deserializer deserializer) {
		if (sd instanceof SerializedData)
			deserializer.deserializeObject((SerializedData)sd, this);
	}
}