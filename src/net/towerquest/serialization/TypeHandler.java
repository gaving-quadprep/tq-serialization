package net.towerquest.serialization;

import java.lang.reflect.Field;

public interface TypeHandler<ObjType, EncodeType extends SerializedDataType, DecodeType extends SerializedDataType> {
	// TODO rename these to make it make at least a little bit of sense
	public boolean canEncode(DataContext dc, Object obj);
	public EncodeType encode(DataContext dc, ObjType obj, Serializer parent);
	// sdt to avoid classcastexception
	public boolean canDecode(DataContext dc, SerializedDataType data);
	public ObjType decode(DataContext dc, DecodeType data, Deserializer parent);
}