package net.towerquest.serialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.towerquest.serialization.prims.PrimString;
import net.towerquest.serialization.prims.Primitive;
import net.towerquest.serialization.prims.PrimitiveTypeHandler;

public class Deserializer {
	
	private List<TypeHandler> typeHandlers = new ArrayList<TypeHandler>();
	
	private List<Runnable> functionsToRun = new ArrayList<Runnable>();
	
	private Map<SerializedDataType, Object> objectMap = new HashMap<SerializedDataType, Object>();
	
	public void runAfterSerializing(Runnable function) {
		 functionsToRun.add(function);
	}
	
	public Object getDecodedDataLocation(SerializedDataType sdt) {
		return objectMap.get(sdt);
	}
	
	public Object decode(DataContext dc, SerializedDataType data) {
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
	
	public <T extends Serializable> T create(Class<T> clazz, SerializedData sd) {
		
		// I'm hungry
		Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
		assert constructors.length > 0 : "An object must have constructors to be deserialized";
		
		T t = null;
		for (Constructor<T> c : constructors) {
			try {
				if (c.getParameterCount() == 0) {
					c.setAccessible(true);
					t = (T) c.newInstance();
					c.setAccessible(false);
					break;
				} else {
					c.setAccessible(true);
					UseFields uf = c.getAnnotation(UseFields.class);
					if (uf != null) {
						String[] names = uf.value();
						Object[] params = new Object[names.length];
						Class<?>[] classes = c.getParameterTypes();
						
						for (int i = 0; i < names.length; i++) {
							params[i] = decode(new DataContext(classes[i]), sd.get(names[i]));
							System.out.println(params[i]);
							if (classes[i].isPrimitive()) {
								//params[i] = params[i].
								
							}
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
		
		assert t != null : "No valid constructors found";
		
		return t;
	}
	
	public <T extends Serializable> T deserializeObject(Class<T> clazz, SerializedData sd, T t) {
		
		for (Field f : SerializationUtils.getAllFields(clazz)) {
			f.setAccessible(true);
			if (!Modifier.isTransient(f.getModifiers())) {
				PreviousName prevName = f.getAnnotation(PreviousName.class);
				
				SerializedDataType fieldData = sd.get(f.getName());
				if (fieldData == null && prevName != null)
					fieldData = sd.get(prevName.value());
				
				if (fieldData != null) {
					Object decoded = decode(new DataContext(f), fieldData);
					if (decoded != null) {
						try {
							f.set(t, decoded);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			f.setAccessible(false);
		}
		
		return t;
	}
	public <T extends Serializable> T deserialize(SerializedData sdt, Class<T> type) {
		return deserializeObject(type, sdt, create(type, sdt));
	}
	public Deserializer() {
		typeHandlers.add(new PointerTypeHandler());
		typeHandlers.add(new ListTypeHandler());
		typeHandlers.add(new PrimitiveTypeHandler());
		typeHandlers.add(new SerializableObjectTypeHandler());
		typeHandlers.add(new EnumTypeHandler());
		typeHandlers.add(new ColorTypeHandler());
	}
}
