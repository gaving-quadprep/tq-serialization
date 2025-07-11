package net.towerquest.serialization;

public interface Serializable {
	public default SerializedData serialize(Serializer serializer) {
		return serializer.serialize(this);
	}
	
	// erm this happens after regular deserialization
	public default void deserialize(SerializedData sd, Deserializer deserializer) {
		
	}
}