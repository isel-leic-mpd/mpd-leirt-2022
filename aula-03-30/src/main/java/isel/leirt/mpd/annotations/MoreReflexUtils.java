package isel.leirt.mpd.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class MoreReflexUtils {

    public static Annotation[]
        getAnnotationsForField(String fieldName, Object obj) {

        try {
            Class<?> cls = obj.getClass();
            Field f = cls.getDeclaredField(fieldName);
            return f.getAnnotations();
        }
        catch(NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
