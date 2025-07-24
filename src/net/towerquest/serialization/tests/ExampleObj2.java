package net.towerquest.serialization.tests;

import java.awt.Color;

import net.towerquest.serialization.Pointer;
import net.towerquest.serialization.Serializable;
import net.towerquest.serialization.UseFields;

public class ExampleObj2 implements Serializable {
	String name;
	long value;
	Long otherValue;
	Color color;
	@Pointer ExampleObj2 pointer; 
	
	@UseFields({"name", "value"})
	public ExampleObj2(String name, long value) {
		System.out.println("Constructed with: " + name + ", " + String.valueOf(value));
		this.name = name;
		this.value = value;
	}
}
