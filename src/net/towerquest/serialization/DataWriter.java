package net.towerquest.serialization;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface DataWriter {
	public void writeObject(SerializedData data, OutputStream out) throws IOException;
	public void writeData(SerializedDataType data, OutputStream out) throws IOException;
}
