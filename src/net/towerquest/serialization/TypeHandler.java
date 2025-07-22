package net.towerquest.serialization;

import java.lang.reflect.Field;

public interface TypeHandler<ObjType, EncodeType extends SerializedDataType, DecodeType extends SerializedDataType> {
	// TODO rename these to make it make at least a little bit of sense
	public boolean canEncode(Field f, Object obj);
	public EncodeType encode(Field f, ObjType obj, Serializer parent);
	// sdt to avoid classcastexception
	public boolean canDecode(Field f, SerializedDataType data);
	public ObjType decode(Field f, DecodeType data, Deserializer parent);
}