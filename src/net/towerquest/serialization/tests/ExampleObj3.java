package net.towerquest.serialization.tests;

import net.towerquest.serialization.SaveAsByteArray;
import net.towerquest.serialization.UseFields;

public class ExampleObj3 extends ExampleObj2 {
	// should inherit other values

	// TODO: make this inherited
	@UseFields({"name", "value"})
	public ExampleObj3(String name, long value) {
		super(name, value);
		System.out.println("My name is "+name);
		j = new int[] {0, 1, Integer.MAX_VALUE, Integer.MIN_VALUE, 674820348};
	}

	// should be deserialized
	String otherString;
	
	@SaveAsByteArray
	int[] j;
}
