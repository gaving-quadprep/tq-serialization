package net.towerquest.serialization;

import java.io.OutputStream;

public interface Writer {
	public void writeObject(SerializedDataType data, OutputStream stream);
}
