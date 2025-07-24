package net.towerquest.serialization.prims;

public class PrimByte extends PrimNumber<Byte> {
	public PrimByte(byte value) {
		super(value);
	}
	public PrimByte(Primitive<?> p) {
		super(p);
		if (p instanceof PrimBoolean)
			this.value = (byte) (((PrimBoolean)p).value ? 1 : 0);
		if (p instanceof PrimNumber)
			this.value = ((PrimNumber<?>)p).value.byteValue();
		if (p instanceof PrimString)
			this.value = Byte.parseByte(((PrimString)p).value);
	}
	
	@Override
	public void scaleBy(double scale) {
		this.value = (byte) (this.value * scale);
	}
}
