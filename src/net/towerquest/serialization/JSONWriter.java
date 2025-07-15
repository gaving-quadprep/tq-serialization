package net.towerquest.serialization;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.towerquest.serialization.prims.*;

public class JSONWriter extends StringDataWriter {
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
			// TODO set some sort of id because json doesn't support pointers
		}
		if (data == null)
			writer.write("null");
	}

	@Override
	public void write(SerializedData data, Writer out) throws IOException {
		writeObject(data, out);
	}
}
