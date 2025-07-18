package net.towerquest.serialization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public abstract class StringDataReader implements DataReader {

	public abstract SerializedData readObject(Reader reader) throws IOException;
	public abstract SerializedDataType readData(Reader reader) throws IOException;
	public abstract SerializedData read(Reader reader) throws IOException;
	
	public boolean isWhitespace(char c, boolean newlinesAllowed) {
		return c == ' ' || c == '\t' || ((c == '\n' || c == '\r') && newlinesAllowed);
	}

	//moves the position to the end of the whitespace
	public void scanWhitespace(Reader reader, boolean newlines) throws IOException {
		assert reader.markSupported();
		int c;
		do {
			reader.mark(2);
			c = reader.read();
		} while (isWhitespace((char) c, newlines));
		reader.reset();
	}
	
	/**
	 * like scanWhitespace but for readers without marks
	 */
	public char skipWhitespace(Reader reader, boolean newlines) throws IOException {
		int c;
		do {
			c = reader.read();
		} while (isWhitespace((char) c, newlines));
		return (char)c;
	}

	public char[] readChars(Reader reader, int numChars) throws IOException {
		char[] buffer = new char[numChars];
		reader.read(buffer);
		return buffer;
	}
	
	@Override
	public SerializedData readObject(InputStream in) throws IOException {
		InputStreamReader isw = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isw);
		SerializedData ret =  readObject(br);
		br.close();
		isw.close();
		return ret;
	}

	@Override
	public SerializedDataType readData(InputStream in) throws IOException {
		InputStreamReader isw = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isw);
		SerializedDataType ret =  readData(br);
		br.close();
		isw.close();
		return ret;
	}
	@Override
	public SerializedData read(InputStream in) throws IOException {
		InputStreamReader isw = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isw);
		SerializedData ret =  read(br);
		br.close();
		isw.close();
		return ret;
	}


}
