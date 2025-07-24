package net.towerquest.serialization.prims;

public class PrimString extends Primitive<String> {
	public PrimString(String value) {
		super(value);
	}
	public PrimString(Primitive<?> p) {
		super(p);
		if (!(p instanceof PrimString))
			this.value = p.value.toString();
	}
}
