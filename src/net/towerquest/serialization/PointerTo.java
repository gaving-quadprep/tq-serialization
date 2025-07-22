package net.towerquest.serialization;

public class PointerTo<T> implements Serializable {
	@Pointer
	public T value;
	public PointerTo() {}
	public PointerTo(T value) {
		this.value = value;
	}
}
