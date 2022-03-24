package isel.leirt.mpd.reflection_intro;

import isel.leirt.mpd.reflection_intro.data.A;

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















    public static Field[] getFields(Object obj) {
        Class<?> objClass = obj.getClass();
        return objClass.getFields();

    }
}
