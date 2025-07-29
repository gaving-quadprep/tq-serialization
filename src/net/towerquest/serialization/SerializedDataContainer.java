package net.towerquest.serialization;

public interface SerializedDataContainer<K extends SerializedDataType, V extends SerializedDataType> extends SerializedDataType {
	public V get(K key);
	public default V getOrDefault(K key, V defaultValue) {
		V v = get(key);
		return v == null ? defaultValue : v;
	}
	public void set(K key, V value);
	public V remove(K key);
	public default V setIfAbsent(K key, V value) {
		V v = get(key);
		if (v == null)
			set(key, value);
		return v;
	}
}
