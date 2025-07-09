package net.towerquest.serialization;

import java.awt.Color;

import net.towerquest.serialization.prims.PrimInt;

public class ColorTypeHandler implements TypeHandler<Color, SerializedData> {

	@Override
	public boolean canHandle(Color obj) {
		return (obj instanceof Color);
	}

	@Override
	public SerializedData encode(Color obj, Serializer parent) {
		SerializedData sd = new SerializedData();
		sd.add("red", new PrimInt(obj.getRed()));
		sd.add("green", new PrimInt(obj.getGreen()));
		sd.add("blue", new PrimInt(obj.getBlue()));
		sd.add("alpha", new PrimInt(obj.getAlpha()));
		return sd;
	}

}
