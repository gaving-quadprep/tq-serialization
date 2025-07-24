package net.towerquest.serialization.prims;

public class PrimBoolean extends Primitive<Boolean> {
	public PrimBoolean(boolean value) {
		super(value);
	}
	public PrimBoolean(Primitive<?> p) {
		super(p);
		if (p instanceof PrimByte)
			this.value = !((PrimByte)p).value.equals((byte)0);
		if (p instanceof PrimInt)
			this.value = !((PrimInt)p).value.equals(0);
		if (p instanceof PrimString)
			this.value = Boolean.valueOf(((PrimString)p).value);
	}
}
