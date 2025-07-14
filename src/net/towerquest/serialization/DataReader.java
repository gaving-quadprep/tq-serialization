package net.towerquest.serialization;

import java.io.IOException;
import java.io.InputStream;

public interface DataReader {
	public SerializedData readObject(InputStream reader) throws IOException;
	public SerializedDataType readData(InputStream stream) throws IOException;
}
