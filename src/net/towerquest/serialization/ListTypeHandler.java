package net.towerquest.serialization;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListTypeHandler implements TypeHandler<Object, ListType, ListType>{

	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		return obj.getClass().isArray() || obj instanceof Collection;
	}

	@Override
	public ListType encode(DataContext dc, Object obj, Serializer parent) {
		Class<?> type = obj.getClass();
		ListType destList = new ListType();
		if (type.isArray()) {
			Class<?> listType = type.getComponentType();
			Object[] sourceList = (Object[])obj;
			
			for (int i = 0; i < sourceList.length; i++) {
				Object value = sourceList[i];
				if (value != null)
					destList.values.add(parent.encode(new DataContext(listType), value));
				else
					destList.values.add(null);
			}
			
		} else {
			assert obj instanceof Collection;
			for (Object i : (Collection<?>)obj)
				destList.values.add(parent.encode(null, i));
		}
		return destList;
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		if (dc != null)
			return (dc.clazz.isArray() || List.class.isAssignableFrom(dc.clazz))
					&& data instanceof ListType;
		return data instanceof ListType;
	}

	@Override
	public Object decode(DataContext dc, ListType data, Deserializer parent) {
		List<Object> list = new ArrayList<Object>();
		Class<?> componentType = dc.clazz.getComponentType();
		for (SerializedDataType i : data.values) {
			Object decoded = parent.decode(new DataContext(componentType), i);
			list.add(decoded);
		}
		if (dc.clazz.isArray()) {
			// 1984
			return list.toArray((Object[])Array.newInstance(componentType, 0));
		} else if (Set.class.isAssignableFrom(dc.clazz)) {
			return new HashSet<Object>(list);
		} else {
			return list;
		}
	}
}
