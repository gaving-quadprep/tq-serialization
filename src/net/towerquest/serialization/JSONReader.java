package net.towerquest.serialization;

import java.io.IOException;
import java.io.Reader;

import net.towerquest.serialization.prims.PrimBoolean;
import net.towerquest.serialization.prims.PrimDouble;
import net.towerquest.serialization.prims.PrimInt;
import net.towerquest.serialization.prims.PrimLong;
import net.towerquest.serialization.prims.PrimString;

public class JSONReader extends StringDataReader {
	/*  if you are wondering why a lot of this code seems strange,
		i made it to be compatible with readers that don't support
		the mark() feature.
		by the time i realized i could wrap it in a bufferedreader,
		it was too late*/
	
	
	public String readString(Reader reader) throws IOException {
		assert reader.read() == '"';
		StringBuilder sb = new StringBuilder();
		int backslashCount = 0;
		while (true) {
			int nextChar = reader.read();
			if (nextChar == '\\')
				backslashCount++;
			else {
				if (nextChar == '"' && (backslashCount % 2 == 0))
					break;
				backslashCount = 0;
			}
			sb.append((char)nextChar);
		}
		
		return sb.toString()
				.replace("\\\"", "\"")
				.replace("\\b", "\b")
				.replace("\\f", "\f")
				.replace("\\n", "\n")
				.replace("\\r", "\r")
				.replace("\\t", "\t")
				.replace("\\\\", "\\");
	}
	
	@Override
	public SerializedData readObject(Reader reader) throws IOException {
		char c = skipWhitespace(reader, true);
		assert c == '{';
		SerializedData sd = new SerializedData();
		char nextChar;
		do {
			scanWhitespace(reader, true);
			reader.mark(2);
			nextChar = (char) reader.read();
			reader.reset();
			assert nextChar == '"' : "Invalid character ("+nextChar+")";
			if (nextChar == '"') {
				String key = readString(reader);
				c = skipWhitespace(reader, true);
				assert c == ':' : "Missing :";
				scanWhitespace(reader, true);
				SerializedDataType value = readData(reader);
				sd.add(key, value);
			}
			nextChar = skipWhitespace(reader, true);
			assert nextChar == ',' || nextChar == '}' : "Invalid character ("+nextChar+")";
		} while (nextChar != '}');

		return sd;
	}
	
	@Override
	public SerializedDataType readData(Reader reader) throws IOException {
		reader.mark(2);
		char firstChar = (char) reader.read();
		reader.reset();
		switch (firstChar) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '.':
		case '-':
			StringBuilder numberString = new StringBuilder();
			char next;
			boolean isFloat = false;
			do {
				reader.mark(2);
				next = (char) reader.read();
				numberString.append(next);
				if (next == '.')
					isFloat = true;
			} while (next == '.' || next == '-' || next == 'e' || (next >= '0' && next <= '9'));
			reader.reset();
			numberString.deleteCharAt(numberString.length() - 1);
			if (isFloat)
				return new PrimDouble(Double.parseDouble(numberString.toString()));
			else {
				long value = Long.parseLong(numberString.toString());
				if (value < Integer.MAX_VALUE && value > Integer.MIN_VALUE)
					return new PrimInt((int) value);
				else
					return new PrimLong(value);
			}
		case '"':
			return new PrimString(readString(reader));
		case 't':
			assert new String(readChars(reader, 4)).equals("true") : "Not a proper boolean";
			return new PrimBoolean(true);
		case 'f':
			assert new String(readChars(reader, 5)).equals("false")  : "Not a proper boolean";
			return new PrimBoolean(false);
		case 'n':
			assert new String(readChars(reader, 4)).equals("null") : "Not a proper null value";
			return null;
		case '[':
			ListType list = new ListType();
			char next2;
			
			// position at first element
			reader.read();
			scanWhitespace(reader, true);
			do {
				list.values.add(readData(reader));
				next2 = skipWhitespace(reader, true);
			} while (next2 == ',');
			assert next2 == ']' : "List not properly closed";
			return list;
		case '{':
			return readObject(reader);
		}
		return null;
	}

	@Override
	public SerializedData read(Reader reader) throws IOException {
		assert reader.markSupported() : "Please use a reader that supports mark() (like BufferedReader)";
		return readObject(reader);
	}

}
