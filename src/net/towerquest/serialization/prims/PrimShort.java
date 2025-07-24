package net.towerquest.serialization.prims;

public class PrimShort extends PrimNumber<Short> {
	public PrimShort(short value) {
		super(value);
	}
	public PrimShort(Primitive<?> p) {
		super(p);
		if (p instanceof PrimBoolean)
			this.value = (short) (((PrimBoolean)p).value ? 1 : 0);
		if (p instanceof PrimNumber)
			this.value = ((PrimNumber<?>)p).value.shortValue();
		if (p instanceof PrimString)
			this.value = Short.parseShort(((PrimString)p).value);
	}
	
	@Override
	public void scaleBy(double scale) {
		this.value = (short) (this.value * scale);
	}
}
