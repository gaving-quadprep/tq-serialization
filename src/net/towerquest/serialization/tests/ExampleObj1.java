package net.towerquest.serialization.tests;

import java.awt.Color;

import net.towerquest.serialization.Pointer;
import net.towerquest.serialization.PointerTo;
import net.towerquest.serialization.ScaleBy;
import net.towerquest.serialization.Serializable;
import net.towerquest.serialization.Typeless;
import net.towerquest.serialization.UseOrdinal;

// Shouldn't contain 'class'
@Typeless
public class ExampleObj1 implements Serializable {
	
	enum Language {
		JAVA, JAVASCRIPT, C, PYTHON;
	};
	
	public transient int shouldntBeSerialized = 2;
	public static byte alsoShouldNotBeSerialized = 27;
	
	public String name = "\\ \" \n \t <&>";
	
	// Should be -4.000000000000001
	@SuppressWarnings("unused")
	private final double asdf = -4.000000000000001;
	// Should be 0
	float jkl;
	
	// Should be 1 or 1.0
	float one = 1.0f;
	// Should not be saved
	Double notZero;
	
	boolean bool = true;
	
	ExampleObj2 exampleObject;
	protected ExampleObj2[] list;
	PointerTo<ExampleObj2>[] pointerArray;
	@Pointer
	PointerTo<ExampleObj2>[] pointerToPointerArray;

	// Should be 1
	@ScaleBy(7)
	float scale = (1f/7f);

	// Should be 0
	int notNull = 0;
	// Should be "false"
	String isThisGoingToBeReadyOnTime = "false";
	
	// Should be "JAVA"
	Language whatThisIsWrittenIn = Language.JAVA;
	// Should be 2
	@UseOrdinal
	Language otherLanguage = Language.C;
	
	public ExampleObj1() {}
	
	// to stop the deserialized one from being the same
	public void init() {

		// Shoule be "Object One"
		ExampleObj2 obj2 = new ExampleObj2("Object One", 0);
		// Should be 0 red, 255 green, 255 blue (depending on the java definition)
		obj2.color = Color.CYAN;
		// Should be 4294967297
		obj2.value = 4294967297l;
		// Should be 1984
		obj2.otherValue = 1984l;
		
		ExampleObj2 otherObj2 = new ExampleObj2("Object Two", 1);
		// Should not be saved
		otherObj2.color = null;
		// Should be ""
		otherObj2.name = "";
		// Should be -34
		otherObj2.value = -34;
		// Should not be saved
		otherObj2.otherValue = null;
		// Should point to the first one
		otherObj2.pointer = obj2;

		// Should point to the second one
		obj2.pointer = otherObj2;
		
		exampleObject = obj2;
		
		ExampleObj3 obj3 = new ExampleObj3("Object", 3);
		obj3.otherString = "Three";
		

		// Should be object, null, object, object (which has other string)
		this.list = new ExampleObj2[] {obj2, null, otherObj2, obj3};
		this.pointerArray = new PointerTo[] {
				new PointerTo<ExampleObj2>(obj2),
				new PointerTo<ExampleObj2>(otherObj2),
				new PointerTo<ExampleObj2>(obj3)};
		this.pointerToPointerArray = pointerArray;
	}
}
