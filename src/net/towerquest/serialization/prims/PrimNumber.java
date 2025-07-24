package net.towerquest.serialization.prims;

public abstract class PrimNumber<T extends Number> extends Primitive<T> {

	public PrimNumber(T value) {
		super(value);
	}
	
	public PrimNumber(Primitive<?> p) {
		super(p);
	}
	
	public abstract void scaleBy(double scale);

}
