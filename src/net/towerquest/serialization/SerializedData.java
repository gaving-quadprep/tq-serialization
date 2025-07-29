package net.towerquest.serialization;

import java.util.HashMap;
import java.util.Map;

import net.towerquest.serialization.prims.PrimString;

public class SerializedData implements SerializedDataContainer<PrimString, SerializedDataType> {
	public final Map<String, SerializedDataType> values = new HashMap<String, SerializedDataType>();
	
	public void set(String k, SerializedDataType v) {
		values.put(k, v);
	}
	
	public SerializedDataType get(String k) {
		return values.get(k);
	}
	
	public SerializedDataType getOrDefault(String k, SerializedDataType defaultValue) {
		return values.getOrDefault(k, defaultValue);
	}
	
	public SerializedDataType remove(String key) {
		return values.remove(key);
	}
	

	@Override
	public SerializedDataType get(PrimString key) {
		return get(key.value);
	}

	@Override
	public void set(PrimString key, SerializedDataType value) {
		set(key.value, value);
	}

	@Override
	public SerializedDataType remove(PrimString key) {
		return remove(key.value);
	}
}