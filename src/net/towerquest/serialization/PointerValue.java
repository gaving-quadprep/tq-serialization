package net.towerquest.serialization;

public class PointerValue implements SerializedDataType {
	public SerializedDataType value;
	
	public PointerValue(SerializedDataType value) {
		this.value = value;
	}
	public PointerValue() {}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof PointerValue)
			return ((PointerValue)other).value == value;
		return false;
	}
	
	@Override
	public String toString() {
		String str = "null";
		if (value != null)
			str = value.toString();
		return "Pointer to " + str;
	}
}