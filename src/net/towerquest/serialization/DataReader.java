package net.towerquest.serialization;

import java.io.IOException;
import java.io.InputStream;

public interface DataReader {
	public SerializedData readObject(InputStream in) throws IOException;
	public SerializedDataType readData(InputStream in) throws IOException;
	public SerializedData read(InputStream in) throws IOException;
}
