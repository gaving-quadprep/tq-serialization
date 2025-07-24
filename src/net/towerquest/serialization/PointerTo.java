package net.towerquest.serialization;

public class PointerTo<T> implements Serializable {
	@Pointer
	public T value;
	public PointerTo() {}
	public PointerTo(T value) {
		this.value = value;
	}
	
	@Override
	public SerializedDataType serialize(Serializer serializer) {
		DataContext dc;
		try {
			dc = new DataContext(this.getClass().getField("value"));
			return serializer.encode(dc, value);
		} catch (Exception e) {
			// nuh uh
			return (SerializedDataType) (Object) "no";
		}
	}
}
