package net.towerquest.serialization.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.towerquest.serialization.DataReader;
import net.towerquest.serialization.DataWriter;
import net.towerquest.serialization.Deserializer;
import net.towerquest.serialization.JSONReader;
import net.towerquest.serialization.JSONWriter;
import net.towerquest.serialization.SerializedData;
import net.towerquest.serialization.Serializer;

public class SerializationTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExampleObj1 object = new ExampleObj1();
		object.init();
		Serializer serializer = new Serializer();
		Deserializer deserializer = new Deserializer();
		SerializedData data = serializer.serialize(object);

		DataWriter dataWriter = new JSONWriter();
		DataReader dataReader = new JSONReader();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			dataWriter.write(data, output);
			SerializedData data2 = dataReader.read(new ByteArrayInputStream(output.toByteArray()));
			ExampleObj1 otherObject = deserializer.deserialize(data2, ExampleObj1.class);
			SerializedData data3 = serializer.serialize(otherObject);
			output.reset();
			dataWriter.write(data3, output);
			System.out.println(output.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
