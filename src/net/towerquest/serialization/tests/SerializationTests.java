package net.towerquest.serialization.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import net.towerquest.serialization.JSONWriter;
import net.towerquest.serialization.DataWriter;
import net.towerquest.serialization.SerializedData;
import net.towerquest.serialization.Serializer;

public class SerializationTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExampleObj1 object = new ExampleObj1();
		Serializer serializer = new Serializer();
		SerializedData data = serializer.serialize(object);
		
		DataWriter dataWriter = new JSONWriter();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			dataWriter.writeObject(data, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(output.toString());
	}

}
