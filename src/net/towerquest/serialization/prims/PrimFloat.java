package net.towerquest.serialization.prims;

public class PrimFloat extends PrimNumber<Float> {
	public PrimFloat(float value) {
		super(value);
	}
	public PrimFloat(Primitive<?> p) {
		super(p);
		if (p instanceof PrimBoolean)
			this.value = ((PrimBoolean)p).value ? 1f : 0f;
		if (p instanceof PrimNumber)
			this.value = ((PrimNumber<?>)p).value.floatValue();
		if (p instanceof PrimString)
			this.value = Float.parseFloat(((PrimString)p).value);
	}
	@Override
	public void scaleBy(double scale) {
		this.value *= (float)scale;
	}
}
