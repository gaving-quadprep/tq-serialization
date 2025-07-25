package net.towerquest.serialization;

import java.awt.Color;

import net.towerquest.serialization.prims.PrimInt;

public class ColorTypeHandler implements TypeHandler<Color, SerializedData, SerializedData> {

	@Override
	public boolean canEncode(DataContext dc, Object obj) {
		return (obj instanceof Color);
	}

	@Override
	public SerializedData encode(DataContext dc, Color obj, Serializer parent) {
		SerializedData sd = new SerializedData();
		sd.set("red", new PrimInt(obj.getRed()));
		sd.set("green", new PrimInt(obj.getGreen()));
		sd.set("blue", new PrimInt(obj.getBlue()));
		sd.set("alpha", new PrimInt(obj.getAlpha()));
		return sd;
	}

	@Override
	public boolean canDecode(DataContext dc, SerializedDataType data) {
		return (dc.clazz == Color.class);
	}

	@Override
	public Color decode(DataContext dc, SerializedData data, Deserializer parent) {
		return new Color(
				((PrimInt)data.getOrDefault("red", new PrimInt(0))).value,
				((PrimInt)data.getOrDefault("green", new PrimInt(0))).value,
				((PrimInt)data.getOrDefault("blue", new PrimInt(0))).value,
				((PrimInt)data.getOrDefault("alpha", new PrimInt(0))).value);
	}

}
