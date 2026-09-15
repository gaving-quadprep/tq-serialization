package net.towerquest.serialization;

public class ByteArrayType implements SerializedDataType {
	//TODO add this
	byte[] value;
	
	public ByteArrayType(byte[] value) {
		this.value = value;
	}
}
