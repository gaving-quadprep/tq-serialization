package net.towerquest.serialization;

import java.util.ArrayList;
import java.util.List;

import net.towerquest.serialization.prims.PrimInt;

public class ListType implements SerializedDataContainer<PrimInt, SerializedDataType> {
	public List<SerializedDataType> values = new ArrayList<SerializedDataType>();

	@Override
	public SerializedDataType get(PrimInt index) {
		return values.get(index.value);
	}

	@Override
	public void set(PrimInt key, SerializedDataType value) {
		values.set(key.value, value);
	}

	@Override
	public SerializedDataType remove(PrimInt index) {
		return values.remove((int)index.value);
	}
}
