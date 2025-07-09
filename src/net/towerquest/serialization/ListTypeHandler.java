package net.towerquest.serialization;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

public class ListTypeHandler implements TypeHandler<Object, ListType>{

	@Override
	public boolean canHandle(Object obj) {
		return obj.getClass().isArray() || obj instanceof Collection;
	}

	@Override
	public ListType encode(Object obj, Serializer parent) {
		Class<?> type = obj.getClass();
		ListType destList = new ListType();
		if (type.isArray()) {
			Class<?> ListType = type.getComponentType();
			Object[] sourceList = (Object[])obj;
			
			for (int i = 0; i < sourceList.length; i++) {
				destList.values.add(parent.encode(sourceList[i]));
			}
			
		} else {
			assert obj instanceof Collection;
			for (Object i : (Collection)obj) {
				destList.values.add(parent.encode(i));
			}
		}
		return destList;
	}

}
