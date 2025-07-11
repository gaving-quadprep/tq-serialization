package net.towerquest.serialization;

import java.awt.Color;
import java.lang.reflect.Field;

import net.towerquest.serialization.prims.PrimInt;

public class ColorTypeHandler implements TypeHandler<Color, SerializedData> {

	@Override
	public boolean canEncode(Color obj) {
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

	@Override
	public boolean canDecode(Field f, SerializedData data) {
		return (f.getType() == Color.class);
	}

	@Override
	public Color decode(Field f, SerializedData data, Deserializer parent) {
		return new Color(
				((PrimInt)data.getOrDefault("red", new PrimInt(0))).value,
				((PrimInt)data.getOrDefault("green", new PrimInt(0))).value,
				((PrimInt)data.getOrDefault("blue", new PrimInt(0))).value,
				((PrimInt)data.getOrDefault("alpha", new PrimInt(0))).value);
	}

}
