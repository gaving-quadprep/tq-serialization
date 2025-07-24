package net.towerquest.serialization.prims;

public class PrimDouble extends PrimNumber<Double> {
	public PrimDouble(double value) {
		super(value);
	}
	public PrimDouble(Primitive<?> p) {
		super(p);
		if (p instanceof PrimBoolean)
			this.value = ((PrimBoolean)p).value ? 1d : 0d;
		if (p instanceof PrimNumber)
			this.value = ((PrimNumber<?>)p).value.doubleValue();
		if (p instanceof PrimString)
			this.value = Double.parseDouble(((PrimString)p).value);
	}
	@Override
	public void scaleBy(double scale) {
		this.value *= scale;
	}
}
