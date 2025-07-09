package net.towerquest.serialization;

import java.util.ArrayList;
import java.util.List;

import net.towerquest.serialization.prims.Primitive;

public class ListType implements SerializedDataType {
	public List<SerializedDataType> values = new ArrayList<SerializedDataType>();
}
