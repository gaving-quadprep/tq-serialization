package net.towerquest.serialization;

import java.util.HashMap;
import java.util.Map;

public class SerializedData implements SerializedDataType {
	public final Map<String, SerializedDataType> values = new HashMap<String, SerializedDataType>();
	
	public void add(String k, SerializedDataType v) {
		values.put(k, v);
	}
	
	public SerializedDataType get(String k) {
		return values.get(k);
	}
	
	public SerializedDataType getOrDefault(String k, SerializedDataType defaultValue) {
		return values.getOrDefault(k, defaultValue);
	}
}