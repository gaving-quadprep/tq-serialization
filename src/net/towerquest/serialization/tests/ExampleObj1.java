package net.towerquest.serialization.tests;

import java.awt.Color;

import net.towerquest.serialization.ScaleBy;
import net.towerquest.serialization.Serializable;

public class ExampleObj1 implements Serializable {
	
	enum Language {
		JAVA, JAVASCRIPT, C, PYTHON;
	};
	
	public transient int shouldntBeSerialized = 2;
	public static byte alsoShouldNotBeSerialized = 27;
	
	public String name = "\\ \" \n \t <&>";
	
	private final double asdf = -4.000000000000001;
	float jkl;
	
	float one = 1.0f;
	Double notZero;
	
	boolean isThisLibraryGoingToWork = true;
	
	ExampleObj2 exampleObject;
	protected ExampleObj2[] list;

	// Should be 1
	@ScaleBy(7)
	float scale = (1f/7f);

	// Should be 0
	int notNull = 0;
	// Should be "false"
	String isThisGoingToBeReadyOnTime = "false";
	
	// Should be "JAVA"
	Language whatThisIsWrittenIn = Language.JAVA;
	// Should not be saved
	Language otherLanguage;
	
	public ExampleObj1() {
		ExampleObj2 obj2 = new ExampleObj2();
		obj2.color = Color.CYAN;
		obj2.name = "John Object";
		obj2.value = 4294967297l;
		obj2.otherValue = 1984l;
		
		ExampleObj2 otherObj2 = new ExampleObj2();
		otherObj2.color = null;
		otherObj2.name = "";
		otherObj2.value = -34;
		otherObj2.otherValue = null;
		
		exampleObject = obj2;
		
		this.list = new ExampleObj2[] {obj2, null, otherObj2};
	}
}
