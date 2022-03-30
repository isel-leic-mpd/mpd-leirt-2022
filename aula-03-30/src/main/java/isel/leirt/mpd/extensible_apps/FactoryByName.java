package isel.leirt.mpd.extensible_apps;

import isel.leirt.mpd.extensible_apps.exceptions.ObjectCreationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class FactoryByName {
    private  final String DYNAMIC_PACKAGE;

    public FactoryByName(String dynPackage) {
        this.DYNAMIC_PACKAGE = dynPackage;
    }

    public  Class<?> getClassOfName(String className)
            throws ClassNotFoundException {
        return Class.forName(DYNAMIC_PACKAGE + "." + className);
    }

    public  Object createInstance(String className)
    {
        try {
            Class<?> cls = getClassOfName(className);
            return cls.getConstructor().newInstance();
        }
        catch(  ClassNotFoundException |
                NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e) {
            throw new ObjectCreationException(e.getMessage());
        }
    }

    public  Object createInstance(String className, Object ... args)
    {
        try {
            Class<?> cls = getClassOfName(className);
            Class<?>[] parms =
                Arrays.stream(args)
                       .map(a -> a.getClass())
                        .toArray(sz -> new Class[sz]);
            return createInstance(cls, parms, args);
        }
        catch(ClassNotFoundException e) {
            throw new ObjectCreationException(e);
        }
    }

    public  Object createInstance(Class<?> cls,
                                  Class<?>[] parms,
                                  Object[]  args) {
        try {

            Constructor<?>[] ctors = cls.getConstructors();

            for (Constructor<?> ctor : ctors) {
                String[] classNames =
                        Arrays.stream(ctor.getParameterTypes())
                                .map(t -> t.getName())
                                .toArray(sz -> new String[sz]);
                System.out.println(ctor);
            }

            Constructor<?> ctor = cls.getConstructor(parms);
            return ctor.newInstance(args);
        } catch (NoSuchMethodException |
                InstantiationException |
                IllegalAccessException |
                InvocationTargetException e) {
            throw new ObjectCreationException(e);
        }
    }

}
