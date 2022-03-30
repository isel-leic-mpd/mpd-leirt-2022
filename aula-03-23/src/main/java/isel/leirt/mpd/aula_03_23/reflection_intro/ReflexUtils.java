package isel.leirt.mpd.aula_03_23.reflection_intro;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflexUtils {

    private static <T> void appendList(List<T> list, T[] values) {
        for(T val : values)
            list.add(val);
    }

    public static Field[] getObjectFields(Object o) {
        List<Field> fields = new ArrayList<>();
        Class<?> curr = o.getClass();

        do {
             appendList(fields, curr.getDeclaredFields());
             curr = curr.getSuperclass();
        }
        while (curr != null);
        return fields.toArray(sz -> new Field[sz]);
    }


    public static Field[] getPublicFields(Object obj) {
        Class<?> objClass = obj.getClass();
        return objClass.getFields();

    }

    public static List<Field> getAllFields(Object obj) {
        List<Field> fields = new ArrayList<>();
        Class<?> objClass = obj.getClass();
        Class<?> currClass = objClass;
        do {
           Field[] cFields = currClass.getDeclaredFields();
           appendList(fields, cFields);
           currClass = currClass.getSuperclass();
        }
        while(currClass != null);
        return fields;
    }

    public static boolean clsImplements(Class<?> cls,
                                        Class<?> checkedInterface) {

        Class<?> currClass = cls;
        if (cls.isInterface() || !checkedInterface.isInterface())
            return false;
        do {
            Class<?>[] implemented = currClass.getInterfaces();
            for (Class<?> intr : implemented)
                if (intr == checkedInterface)
                    return true;
            currClass = currClass.getSuperclass();
        }
        while(currClass != null);
        return false;
    }

    public static Field getObjectField(Object obj, String fieldName) {
        Class<?> objClass = obj.getClass();
        Class<?> currClass = objClass;
        do {
            Field[] cFields = currClass.getDeclaredFields();
            for(Field f : cFields) {
                if (f.getName().equals(fieldName)) return f;
            }
            currClass = currClass.getSuperclass();
        }
        while(currClass != null);
        return null;
    }

    public static Object getValueFromField(Object obj,
                                           String fieldName)
            throws IllegalArgumentException,
            IllegalAccessException {
        Field f = getObjectField(obj, fieldName);
        if (f == null) return null;
        f.setAccessible(true);
        return f.get(obj);
    }
}
