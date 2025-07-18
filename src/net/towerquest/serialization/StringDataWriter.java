package net.towerquest.serialization;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.towerquest.serialization.prims.PrimBoolean;
import net.towerquest.serialization.prims.PrimByte;
import net.towerquest.serialization.prims.PrimDouble;
import net.towerquest.serialization.prims.PrimFloat;
import net.towerquest.serialization.prims.PrimInt;
import net.towerquest.serialization.prims.PrimLong;
import net.towerquest.serialization.prims.PrimShort;

public abstract class StringDataWriter implements DataWriter {
	
	public abstract void writeObject(SerializedData data, Writer out) throws IOException;
	public abstract String escape(String s);
	/**
	 * Default function includes some common boolean and number conversions.
	 */
	public void writeData(SerializedDataType data, Writer out) throws IOException {
		if (data instanceof PrimBoolean)
			out.write(((PrimBoolean)data).value ? "true" : "false");
		if (data instanceof PrimByte)
			out.write(Byte.toString(((PrimByte)data).value));
		if (data instanceof PrimDouble)
			out.write(Double.toString(((PrimDouble)data).value));
		if (data instanceof PrimFloat)
			out.write(Float.toString(((PrimFloat)data).value));
		if (data instanceof PrimInt)
			out.write(Integer.toString(((PrimInt)data).value));
		if (data instanceof PrimLong)
			out.write(Long.toString(((PrimLong)data).value));
		if (data instanceof PrimShort)
			out.write(Short.toString(((PrimShort)data).value));
	}
	
	public abstract void write(SerializedData data, Writer out) throws IOException;

	@Override
	public void writeObject(SerializedData data, OutputStream out) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(out);
		BufferedWriter bw = new BufferedWriter(osw);
		writeObject(data, bw);
		bw.close();
		osw.close();
	}
	@Override
	public void writeData(SerializedDataType data, OutputStream out) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(out);
		BufferedWriter bw = new BufferedWriter(osw);
		writeData(data, bw);
		bw.close();
		osw.close();
	}
	@Override
	public void write(SerializedData data, OutputStream out) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(out);
		BufferedWriter bw = new BufferedWriter(osw);
		write(data, bw);
		bw.close();
		osw.close();
	}
	
	
}
