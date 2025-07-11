package net.towerquest.serialization;

import java.util.ArrayList;
import java.util.List;

public class ListType implements SerializedDataType {
	public List<SerializedDataType> values = new ArrayList<SerializedDataType>();
}
