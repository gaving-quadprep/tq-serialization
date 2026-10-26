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
			bb.flip();
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
		if (!dc.clazz.isArray())
			return false;
		Class<?> componentType = dc.clazz.getComponentType();
		return componentType.isPrimitive() || Number.class.isAssignableFrom(componentType);
	}

	@Override
	public Object decode(DataContext dc, ByteArrayType data, Deserializer parent) {
		if (dc.clazz.equals(byte[].class)) {
			return data.value;
		}
		if (dc.clazz.equals(Byte[].class)) {
			int len = data.value.length;
			Byte[] array = new Byte[len];
			for (int i = 0; i < len; i++) {
				array[i] = data.value[i];
			}
			return array;
		}
		if (dc.clazz.isArray()) {
			Class<?> componentType = dc.clazz.getComponentType();
			if (componentType.isPrimitive() || Primitive.isPrimitiveWrapper(componentType)) {
				Class<?> wrapperType = Primitive.getPrimitiveClassFromType(componentType);
				ByteBuffer bb = ByteBuffer.wrap(data.value);
				bb.position(0);
				int len = data.value.length / Primitive.byteSize(componentType);
				// this has to be Object and not Object[]
				Object array = new int[len];
				for (int i = 0; i < len; i++) {
					Object obj = null;
					if (wrapperType == Boolean.class)
						obj = bb.get() != 0;
					if (wrapperType == Byte.class)
						obj = bb.get();
					if (wrapperType == Character.class)
						obj = bb.getChar();
					if (wrapperType == Double.class)
						obj = bb.getDouble();
					if (wrapperType == Float.class)
						obj = bb.getFloat();
					if (wrapperType == Integer.class)
						obj = bb.getInt();
					if (wrapperType == Long.class)
						obj = bb.getLong();
					if (wrapperType == Short.class)
						obj = bb.getShort();
					Array.set(array, i, obj);
				}
			}
		}
		
		return null;
	}

}
