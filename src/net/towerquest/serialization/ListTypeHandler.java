package net.towerquest.serialization;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListTypeHandler implements TypeHandler<Object, ListType>{

	@Override
	public boolean canEncode(Field f, Object obj) {
		return obj.getClass().isArray() || obj instanceof Collection;
	}

	@Override
	public ListType encode(Field f, Object obj, Serializer parent) {
		Class<?> type = obj.getClass();
		ListType destList = new ListType();
		if (type.isArray()) {
			Class<?> ListType = type.getComponentType();
			Object[] sourceList = (Object[])obj;
			
			for (int i = 0; i < sourceList.length; i++) {
				Object value = sourceList[i];
				if (value != null)
					destList.values.add(parent.encode(null, value));
				else
					destList.values.add(null);
			}
			
		} else {
			assert obj instanceof Collection;
			for (Object i : (Collection)obj)
				destList.values.add(parent.encode(null, i));
		}
		return destList;
	}

	@Override
	public boolean canDecode(Field f, ListType data) {
		return (f.getType().isArray() || Collection.class.isAssignableFrom(f.getType()));
	}

	@Override
	public Object decode(Field f, ListType data, Deserializer parent) {
		List<Object> list = new ArrayList<Object>();
		for (SerializedDataType i : data.values)
			list.add(parent.decode(f, i));
		if (f.getType().isArray())
			return list.toArray();
		else
			return list;
	}
}
