package net.towerquest.serialization.prims;

public class PrimInt extends PrimNumber<Integer> {
	public PrimInt(int value) {
		super(value);
	}
	public PrimInt(Primitive<?> p) {
		super(p);
		if (p instanceof PrimBoolean)
			this.value = ((PrimBoolean)p).value ? 1 : 0;
		if (p instanceof PrimNumber)
			this.value = ((PrimNumber<?>)p).value.intValue();
		if (p instanceof PrimString)
			this.value = Integer.parseInt(((PrimString)p).value);
	}
	@Override
	public void scaleBy(double scale) {
		this.value = (int) (this.value * scale);
	}
}
