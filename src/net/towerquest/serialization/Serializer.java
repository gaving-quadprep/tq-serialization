package net.towerquest.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.towerquest.serialization.prims.*;

public class Serializer {
	
	public final List<TypeHandler<?,?,?>> typeHandlers = new ArrayList<TypeHandler<?,?,?>>();
	private List<Runnable> functionsToRun = new ArrayList<Runnable>();
	private Map<Object, SerializedDataType> objectMap = new HashMap<Object, SerializedDataType>();
	private Set<PointerValue> pointers = new HashSet<>();
	private Map<Class<? extends Serializable>, String> registry = new HashMap<Class<? extends Serializable>, String>();
	
	// TODO use registry
	
	public void runAfterSerializing(Runnable function) {
		 functionsToRun.add(function);
	}
	
	public SerializedDataType getEncodedDataLocation(Object obj) {
		return objectMap.get(obj);
	}
	
	public SerializedDataType encode(DataContext dc, Object obj) {
		for (TypeHandler th : typeHandlers) {
			if (th.canEncode(dc, obj)) {
				SerializedDataType ret = th.encode(dc, obj, this);
				if (ret instanceof PointerValue) {
					// pointer to the pointer
					pointers.add(new PointerValue(ret));
				} else {
					objectMap.putIfAbsent(obj, ret);
				}
				return ret;
			}
		}
		return null;
	}
	
	
	public SerializedData serializeObject(Serializable s) {
		SerializedData root = new SerializedData();
		
		List<Field> fields = SerializationUtils.getAllFields(s.getClass());
		
		try {
			for (Field f : fields) {
				f.setAccessible(true);
				if (!Modifier.isTransient(f.getModifiers())) {
					String name = f.getName();
					Object obj = f.get(s);
					if (obj != null) {
						SerializedDataType val = encode(new DataContext(f), obj);
						root.values.put(name, val);
					}
				}
				f.setAccessible(false);
			}
			if (s.getClass().getAnnotation(Typeless.class) == null) {
				root.values.put("class", new PrimString(s.getClass().getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return root;
		
	}
	
	public SerializedData serialize(Serializable s) {
		SerializedData ret = new SerializedData();
		ret.set("main", serializeObject(s));
		// TODO figure out why pointers are duplicated
		System.out.println(pointers);
		ret.set("pointers", new ListType(new ArrayList<SerializedDataType>(pointers)));
		for (Runnable r : functionsToRun) {
			r.run();
		}
		// moved down here to avoid memory leak
		objectMap.clear();
		functionsToRun.clear();
		pointers.clear();
		return ret;
	}
	
	public Serializer() {
		typeHandlers.add(new PointerTypeHandler());
		typeHandlers.add(new PrimitiveTypeHandler());
		typeHandlers.add(new ByteArrayTypeHandler()); // must come before ListTypeHandler
		typeHandlers.add(new ListTypeHandler());
		typeHandlers.add(new SerializableObjectTypeHandler());
		typeHandlers.add(new EnumTypeHandler());
		typeHandlers.add(new ColorTypeHandler());
	}
}
