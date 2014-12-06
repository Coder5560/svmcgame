package utils.factory;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class MyClassReflection{

	public static void setValue(Class<?> c, String name, Object object,
			Object value) {
		try {
			Field field = ClassReflection.getDeclaredField(c, name);
			field.setAccessible(true);
			field.set(object, value);
		} catch (ReflectionException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getValue(Class<?> c, String name, Object object,
			Class<T> toValue) {
		try {
			Field field = ClassReflection.getDeclaredField(c, name);
			field.setAccessible(true);
			return (T) field.get(object);
		} catch (ReflectionException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Method getMethod(Class<?> c, String name, Class... values) {
		try {
			return ClassReflection.getDeclaredMethod(c, name, values);
		} catch (ReflectionException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public static Class forName(String name) {
		try {
			return ClassReflection.forName(name);
		} catch (ReflectionException e) {
			e.printStackTrace();
			return null;
		}
	}

}
