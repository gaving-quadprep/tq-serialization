package net.towerquest.serialization;

import java.lang.reflect.Field;

public interface TypeHandler<ObjType, SDType extends SerializedDataType> {
	// TODO rename these to make it make at least a little bit of sense
	public boolean canEncode(ObjType obj);
	public SDType encode(ObjType obj, Serializer parent);
	public boolean canDecode(Field f, SDType data);
	public ObjType decode(Field f, SDType data, Deserializer parent);
}
