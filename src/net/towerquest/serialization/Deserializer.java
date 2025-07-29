package net.towerquest.serialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.towerquest.serialization.prims.PrimString;
import net.towerquest.serialization.prims.PrimitiveTypeHandler;

public class Deserializer {
	
	private List<TypeHandler<?,?,?>> typeHandlers = new ArrayList<TypeHandler<?,?,?>>();
	
	private List<Runnable> functionsToRun = new ArrayList<Runnable>();
	
	private Map<SerializedDataType, Object> objectMap = new HashMap<SerializedDataType, Object>();
	
	public void runAfterDeserializing(Runnable function) {
		 functionsToRun.add(function);
	}
	
	public Object getDecodedDataLocation(SerializedDataType sdt) {
		return objectMap.get(sdt);
	}
	
	public Object decode(DataContext dc, SerializedDataType data) {
		if (data == null) {
			return null;
		}
		for (TypeHandler th : typeHandlers) {
			if (th.canDecode(dc, data)) {
				Object ret = th.decode(dc, data, this);
				if (!(data instanceof PointerValue))
					objectMap.putIfAbsent(data, ret);
				return ret;
			}
		}
		return null;
	}
	
	public <T extends Serializable> T create(Class<T> clazz, SerializedDataType data) {
		SerializedData sd = null;
		if (data instanceof SerializedData)
			sd = (SerializedData)data;
		
		if (sd != null) {
			PrimString objType = (PrimString) sd.get("class");
			if (objType != null) {
				if (!clazz.getName().equals(objType.value)) {
					try {
						clazz = (Class<T>) Class.forName(objType.value);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Constructor<T>[] constructors = (Constructor<T>[]) clazz.getDeclaredConstructors();
		assert constructors.length > 0 : "An object must have constructors to be deserialized";
		
		T t = null;
		for (Constructor<T> c : constructors) {
			try {
				c.setAccessible(true);
				if (c.getParameterCount() == 0) {
					t = (T) c.newInstance();
					c.setAccessible(false);
					break;
				} else {
					UseFields uf = c.getAnnotation(UseFields.class);
					if (uf != null) {
						assert sd != null : "A SerializedData container is necessary for UseFields";
						String[] names = uf.value();
						Object[] params = new Object[names.length];
						Class<?>[] classes = c.getParameterTypes();
						
						for (int i = 0; i < names.length; i++) {
							params[i] = decode(new DataContext(classes[i]), sd.get(names[i]));
						}
						
						t = (T) c.newInstance(params);
						c.setAccessible(false);
						break;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.setAccessible(false);
		}
		
		assert t != null : "No valid constructors found for "+clazz.getName();
		
		return t;
	}
	
	public void deserializeObject(SerializedData sd, Serializable obj) {
		Class<?> clazz = (Class<?>) obj.getClass();
		for (Field f : SerializationUtils.getAllFields(clazz)) {
			f.setAccessible(true);
			if (!Modifier.isTransient(f.getModifiers())) {
				PreviousName prevName = f.getAnnotation(PreviousName.class);
				
				SerializedDataType fieldData = sd.get(f.getName());
				if (fieldData == null && prevName != null)
					fieldData = sd.get(prevName.value());
				// this works for some reason
				SerializedDataType fieldData2 = fieldData;
				if (fieldData != null) {
					if (fieldData instanceof PointerValue) {
						runAfterDeserializing(() -> {
							try {
								f.setAccessible(true);
								f.set(obj, getDecodedDataLocation(((PointerValue)fieldData2).value));
								f.setAccessible(false);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
					} else {
						Object decoded = decode(new DataContext(f), fieldData);
						if (decoded != null) {
							try {
								f.set(obj, decoded);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			f.setAccessible(false);
		}
	}
	public <T extends Serializable> T deserialize(SerializedData sdt, Class<T> type) {
		functionsToRun.clear();
		objectMap.clear();
		
		SerializedData main = (SerializedData) sdt.get("main");
		T ret = create(type, main);
		ret.deserialize(main, this);
		for (Runnable r : functionsToRun) {
			r.run();
		}
		
		return ret;
	}
	public Deserializer() {
		typeHandlers.add(new SerializableObjectTypeHandler());
		typeHandlers.add(new PointerTypeHandler());
		typeHandlers.add(new ListTypeHandler());
		typeHandlers.add(new PrimitiveTypeHandler());
		typeHandlers.add(new EnumTypeHandler());
		typeHandlers.add(new ColorTypeHandler());
	}
}
