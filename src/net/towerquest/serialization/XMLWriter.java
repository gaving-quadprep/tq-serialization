package net.towerquest.serialization;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.Map.Entry;

import net.towerquest.serialization.prims.*;

public class XMLWriter extends StringDataWriter {
	// fun fact: i specifically called the parameter "s", not "str" or "string", so that the .replace function would be aligned
	@Override
	public String escape(String s) {
		return s.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&apos;")
				.replace("\n", "&#10;");
	}
	
	public void indent(Writer out, int count) throws IOException {
		for (int i=0; i<count; i++) {
			out.write("\t");
		}
	}
	
	private boolean isAmbiguous(SerializedDataType obj) {
		if (obj instanceof PrimByte || obj instanceof PrimDouble
				|| obj instanceof PrimLong || obj instanceof PrimShort
				|| obj instanceof ListType)
			return true;
		if (obj instanceof PrimString) {
			String str = ((PrimString)obj).value;
			// checks for numeric strings
			try {  
				Double.parseDouble(str);  
				return true;
			} catch(NumberFormatException e) {}

			//empty values
			if (str.equals(""))
				return true;
			//booleans
			if (str.equals("true") || str.equals("false"))
				return true;
		}
		return false;
	}

	@Override
	public void writeObject(SerializedData data, Writer out) throws IOException {
		writeObject(data, out, 0);
		
	}
	
	private void writeEntry(String key, SerializedDataType value, Writer out, int indentation) throws IOException {
		indent(out, indentation);
		out.write("<" + key);
		if (isAmbiguous(value)) {
			out.write(" tq-class=\"" + value.getClass().getSimpleName() + "\"");
		}
		out.write(">");
		writeData(value, out, indentation);
		out.write("</" + key + ">");
	}

	public void writeObject(SerializedData data, Writer out, int indentation) throws IOException {
		Set<Entry<String, SerializedDataType>> entries = data.values.entrySet();
		int size = entries.size();

		int i = 0;
		for(Entry<String, SerializedDataType> entry : entries) {
			i++;
			
			writeEntry(entry.getKey(), entry.getValue(), out, indentation);
			out.write("\n");
			
			if (i == size)
				break;
		}
		
	}

	@Override
	public void writeData(SerializedDataType data, Writer out) throws IOException {
		writeData(data, out, 0);
	}

	public void writeData(SerializedDataType data, Writer out, int indentation) throws IOException {
		super.writeData(data, out);

		if (data instanceof PrimString)
			out.write(escape(((PrimString)data).value));
		if (data instanceof SerializedData) {
			out.write("\n");
			writeObject((SerializedData)data, out, indentation + 1);
			indent(out, indentation);
		}
		// xml does not have a format for arrays
		if (data instanceof ListType) {
			out.write("\n");
			for (SerializedDataType data1 : ((ListType)data).values) {
				if (data1 != null)
					writeEntry("item", data1, out, indentation + 1);
				else {
					indent(out, indentation+1);
					out.write("<item />");
				}
				out.write("\n");
			}
			indent(out, indentation);
		}
		if (data instanceof PointerValue) {
			// /tqs/value1
		}
	}

	@Override
	public void write(SerializedData data, Writer out) throws IOException {
		out.write("<?xml version=\"1.0\"?>\n");
		out.write("<tqs>\n");
		writeObject(data, out, 1);
		out.write("</tqs>\n");
	}

}
