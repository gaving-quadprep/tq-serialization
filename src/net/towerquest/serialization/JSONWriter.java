package net.towerquest.serialization;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.towerquest.serialization.prims.*;

public class JSONWriter extends StringDataWriter {
	private Map<SerializedDataType, String> paths = new HashMap<SerializedDataType, String>();
	
	// i stole this from stack overflow because i'm too lazy to add a library
	@Override
	public String escape(String raw) {
		String escaped = raw;
		escaped = escaped.replace("\\", "\\\\");
		escaped = escaped.replace("\"", "\\\"");
		escaped = escaped.replace("\b", "\\b");
		escaped = escaped.replace("\f", "\\f");
		escaped = escaped.replace("\n", "\\n");
		escaped = escaped.replace("\r", "\\r");
		escaped = escaped.replace("\t", "\\t");
		// TODO: escape other non-printing characters using uXXXX notation
		return escaped;
	}
	
	public void writeString(Writer writer, String str) throws IOException {
		writer.write("\"" + escape(str) + "\"");
	}
	
	public void writeObject(SerializedData data, Writer writer) throws IOException {
		Set<Entry<String, SerializedDataType>> entries = data.values.entrySet();
		int size = entries.size();
		
		writer.write("{");
		
		int i = 0;
		for(Entry<String, SerializedDataType> entry : entries) {
			i++;
			
			writeString(writer, entry.getKey());
			writer.write(":");
			writeData(entry.getValue(), writer);
			
			if (i == size)
				break;
			writer.write(",");
		}
		writer.write("}");
	}
	
	public void writeData(SerializedDataType data, Writer writer) throws IOException {
		super.writeData(data, writer);
		if (data instanceof SerializedData)
			writeObject((SerializedData) data, writer);
		
		if (data instanceof PrimString)
			writeString(writer, ((PrimString)data).value);
		
		if (data instanceof ListType) {
			List<SerializedDataType> values = ((ListType)data).values;
			int size = values.size();
			
			writer.write("[");
			for (int i = 0; i < size; i++) {
				writeData(values.get(i), writer);
				if (i == size - 1)
					break;
				writer.write(",");
			}
			writer.write("]");
		}
		if (data instanceof PointerValue) {
			String path2 = paths.get(((PointerValue)data).value);
			if (path2 != null)
				writeString(writer, path2);
		}
		if (data == null)
			writer.write("null");
	}

	// we need to know the path to each object before serializing it
	private void traverse(SerializedDataType sdt, String path) {
		paths.putIfAbsent(sdt, path);
		if (sdt instanceof SerializedData) {
			for (Entry<String, SerializedDataType> entry : 
				((SerializedData)sdt).values.entrySet()) {
				traverse(entry.getValue(), path + "/" + entry.getKey());
			}
		}
		if (sdt instanceof ListType) {
			List<SerializedDataType> values = ((ListType)sdt).values;
			for (int i = 0; i < values.size(); i++) {
				traverse(values.get(i), path + "/" + String.valueOf(i));
			}
		}
	}
	

	@Override
	public void write(SerializedData data, Writer out) throws IOException {
		paths.clear();
		traverse(data, "");
		writeObject(data, out);
	}
}
