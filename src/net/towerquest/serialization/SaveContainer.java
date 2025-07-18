package net.towerquest.serialization;

import java.util.List;

public class SaveContainer {
	public SerializedData main;
	public List<SerializedDataType> looseObjects;
	
	public SaveContainer(SerializedData main, List<SerializedDataType> looseObjects) {
		this.main = main;
		this.looseObjects = looseObjects;
	}
}