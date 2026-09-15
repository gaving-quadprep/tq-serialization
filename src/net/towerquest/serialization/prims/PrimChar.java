package net.towerquest.serialization.prims;

public class PrimChar extends Primitive<Character> {
	public PrimChar(char value) {
		super(value);
	}
	public PrimChar(Primitive<?> p) {
		super(p);
		if (p instanceof PrimNumber)
			this.value = (Character)p.value;
		if (p instanceof PrimString) {
			String str = ((String)p.value);
			this.value = str.length() > 0 ? str.charAt(0) : '\0';
		}
	}
}
