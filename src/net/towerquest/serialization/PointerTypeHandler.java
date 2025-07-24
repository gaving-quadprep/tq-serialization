package net.towerquest.serialization;

public class PointerTypeHandler implements TypeHandler<Object, PointerValue, PointerValue> {

	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		if (dc != null) {
			for (int i = 0; i < dc.annotations.length; i++)
				if (dc.annotations[i] instanceof Pointer)
					return true;
		}
		return false;
	}

	@Override
	public PointerValue encode(DataContext dc, Object obj, Serializer parent) {
		PointerValue ret = new PointerValue();
		parent.runAfterSerializing(() -> {
			ret.value = parent.getEncodedDataLocation(obj);
		});
		return ret;
	}
	
	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		return data instanceof PointerValue;
	}

	@Override
	public Object decode(DataContext dc, PointerValue data, Deserializer parent) {
		// TODO figure out how to set it to null and change it later
		
		return null;
	}
	
}
