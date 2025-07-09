package net.towerquest.serialization;

public interface TypeHandler<ObjType, SDType extends SerializedDataType> {
	// TODO rename these to make it make at least a little bit of sense
	public boolean canHandle(ObjType obj);
	public SDType encode(ObjType obj, Serializer parent);
}
