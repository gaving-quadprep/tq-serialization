package net.towerquest.serialization.prims;

public class PrimLong extends PrimNumber<Long> {
	public PrimLong(long value) {
		super(value);
	}
	public PrimLong(Primitive<?> p) {
		super(p);
		if (p instanceof PrimBoolean)
			this.value = ((PrimBoolean)p).value ? 1l : 0l;
		if (p instanceof PrimNumber)
			this.value = ((PrimNumber<?>)p).value.longValue();
		if (p instanceof PrimString)
			this.value = Long.parseLong(((PrimString)p).value);
	}
	
	@Override
	public void scaleBy(double scale) {
		this.value = (long) (this.value * scale);
	}
}
