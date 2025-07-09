package net.towerquest.serialization;

import java.util.HashMap;
import java.util.Map;

public class SerializedData implements SerializedDataType {
	public final Map<String, SerializedDataType> values = new HashMap<String, SerializedDataType>();
	
	public void add(String k, SerializedDataType v) {
		values.put(k, v);
	}
}