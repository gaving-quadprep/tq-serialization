package net.towerquest.serialization.tests;

import net.towerquest.serialization.UseFields;

public class ExampleObj3 extends ExampleObj2 {
	// should inherit other values

	// TODO: make this inherited
	@UseFields({"name", "value"})
	public ExampleObj3(String name, long value) {
		super(name, value);
		System.out.println("My name is "+name);
	}

	// should be deserialized
	String otherString;
}
