package net.towerquest.serialization;

public class PointerTo<T> implements Serializable {
	@Pointer
	public T value;
	
	public PointerTo(T value) {
		this();
		this.value = value;
	}
	
	private PointerTo() {}
	
	@Override
	public SerializedDataType serialize(Serializer serializer) {
		DataContext dc;
		try {
			dc = new DataContext(this.getClass().getField("value"));
			return serializer.encode(dc, value);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void deserialize(SerializedDataType sd, Deserializer deserializer) {
		if (sd instanceof PointerValue) {
			deserializer.runAfterDeserializing(() -> {
				this.value = (T) deserializer.getDecodedDataLocation(((PointerValue)sd).value);
			});
		}
	}
}
