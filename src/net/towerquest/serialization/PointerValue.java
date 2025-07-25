package net.towerquest.serialization;

public class PointerValue implements SerializedDataType {
	SerializedDataType value;
	
	public PointerValue(SerializedDataType value) {
		this.value = value;
	}
	public PointerValue() {}
}