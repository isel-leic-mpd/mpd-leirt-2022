package isel.leirt.mpd.aula_03_23;

import isel.leirt.mpd.aula_03_23.reflection_intro.ReflexUtils;
import isel.leirt.mpd.aula_03_23.reflection_intro.XmlSerializer;
import isel.leirt.mpd.aula_03_23.reflection_intro.data.C;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Add;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Expr;
import org.dom4j.Document;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static isel.leirt.mpd.aula_03_23.reflection_intro.ReflexUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReflexTests {
    @Test
    public void get_declared_fields_test() {
        C c = new C(5, "teste", 2.0);

        Field[] fields = ReflexUtils.getObjectFields(c);

        for(Field f : fields) {
            System.out.printf("%s : %s in %s\n",
                    f.getName(),
                    f.getType().getName(),
                    f.getDeclaringClass().getName());
        }
    }

    @Test
    public void simpleSerializeTest() {
        C c = new C(10, "teste", 2.0);

        Document doc = XmlSerializer.toXml(c);

        String xmlText = doc.asXML();

        System.out.println(xmlText);

        C newC = (C) XmlSerializer.fromXml(xmlText);

        System.out.println(c);
        System.out.println(newC);


        assertTrue(c.getClass() == C.class);
        assertTrue(newC.getClass() == C.class);


        assertEquals(c, newC);

    }


    @Test
    public void show_object_fields() {
        C c = new C(10, "teste", 3.0);

        Field[] cFields = getPublicFields(c);

        for (Field f : cFields) {
            System.out.println(f.getName() + ":" +
                    f.getType().getName() +
                    " in " +
                    f.getDeclaringClass().getName());
        }
    }

    @Test
    public void show_all_object_fields() {
        C c = new C(10, "teste", 3.0);

        List<Field> cFields = getAllFields(c);

        for (Field f : cFields) {
            System.out.println(f.getName() + ":" +
                    f.getType().getName() +
                    " in " +
                    f.getDeclaringClass().getName());
        }
    }

    @Test
    public void check_interface_implemented_by_object_class() {
        assertTrue(clsImplements(String.class, CharSequence.class));
        assertTrue(clsImplements(Add.class, Expr.class));
    }

    @Test
    public void get_value_from_an_object_field() {
        C c = new C(10, "teste", 3.0);

        try {
            Object fieldVal = getValueFromField(c, "sVal");

            assertEquals(String.class, fieldVal.getClass());
            assertEquals("teste", fieldVal);
            System.out.println("field value = " + fieldVal);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
