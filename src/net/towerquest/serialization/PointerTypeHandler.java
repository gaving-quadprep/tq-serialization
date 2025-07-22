package net.towerquest.serialization;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class PointerTypeHandler implements TypeHandler<Object, PointerValue, PointerValue> {

	@Override
	public boolean canEncode(Field f, Object obj) {
		if (f != null) {
			Annotation[] annotations = f.getAnnotations();
			for (int i = 0; i < annotations.length; i++)
				if (annotations[i] instanceof Pointer)
					return true;
		}
		return false;
	}

	@Override
	public PointerValue encode(Field f, Object obj, Serializer parent) {
		PointerValue ret = new PointerValue();
		parent.runAfterSerializing(() -> {
			ret.value = parent.getEncodedDataLocation(obj);
		});
		return ret;
	}

	@Override
	public boolean canDecode(Field f, SerializedDataType data) {
		return data instanceof PointerValue;
	}

	@Override
	public Object decode(Field f, PointerValue data, Deserializer parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
