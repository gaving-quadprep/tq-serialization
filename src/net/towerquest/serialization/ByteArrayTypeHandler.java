package net.towerquest.serialization;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

import net.towerquest.serialization.prims.Primitive;

public class ByteArrayTypeHandler implements TypeHandler<Object, ByteArrayType, ByteArrayType> {
	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		boolean hasAnnotation = false;
		if (dc != null) {
			for (int i = 0; i < dc.annotations.length; i++) {
				if (dc.annotations[i] instanceof SaveAsByteArray) {
					hasAnnotation = true;
					break;
				}
			}
		}
		if(!hasAnnotation)
			return false;
		Class<?> clazz = obj.getClass();
		Class<?> clazz2;
		if (clazz.isArray()) {
			clazz2 = clazz.getComponentType();
		} else {
			return false;
			// TODO check if it's a collection, and if so, what the generic is
		}
		// TODO if it can somehow be converted into a byte array
		return clazz2.isPrimitive() || Number.class.isAssignableFrom(clazz2);
	}

	@Override
	public ByteArrayType encode(DataContext dc, Object obj, Serializer parent) {
		if (obj instanceof byte[]) {
			return new ByteArrayType((byte[])obj);
		}
		if (obj instanceof Byte[]) {
			int len = ((Byte[])obj).length;
			byte[] array = new byte[len];
			for (int i = 0; i < len; i++) {
				array[i] = ((Byte[])obj)[i];
			}
			return new ByteArrayType(array);
		}
		Class<?> componentType = obj.getClass().getComponentType();
		if (componentType.isPrimitive() || Primitive.isPrimitiveWrapper(componentType)) {
			Class<?> wrapperType = Primitive.getPrimitiveClassFromType(componentType);
			int len = Array.getLength(obj);
			ByteBuffer bb = ByteBuffer.allocate(len * Primitive.byteSize(componentType));
			for (int i = 0; i < len; i++) {
				Object obj2 = Array.get(obj, i);
				if (wrapperType == Boolean.class)
					bb.put(((Boolean)obj2) ? (byte)1 : (byte)0);
				if (wrapperType == Byte.class)
					bb.put((Byte)obj2);
				if (wrapperType == Character.class)
					bb.putChar((Character)obj2);
				if (wrapperType == Double.class)
					bb.putDouble((Double)obj2);
				if (wrapperType == Float.class)
					bb.putFloat((Float)obj2);
				if (wrapperType == Integer.class)
					bb.putInt((Integer)obj2);
				if (wrapperType == Long.class)
					bb.putLong((Long)obj2);
				if (wrapperType == Short.class)
					bb.putShort((Short)obj2);
			}
			return new ByteArrayType(bb.array());
		}
		return null;
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte[] decode(DataContext dc, ByteArrayType data, Deserializer parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
