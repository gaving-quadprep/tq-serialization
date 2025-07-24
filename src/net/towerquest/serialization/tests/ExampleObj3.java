package net.towerquest.serialization.tests;

public class ExampleObj3 extends ExampleObj2 {
	// should inherit other values

	public ExampleObj3(String name, long value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}

	// should be deserialized
	String otherString;
}
