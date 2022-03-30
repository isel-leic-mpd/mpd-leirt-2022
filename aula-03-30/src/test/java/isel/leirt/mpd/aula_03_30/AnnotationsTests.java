package isel.leirt.mpd.aula_03_30;

import isel.leirt.mpd.annotations.Attribute;
import isel.leirt.mpd.annotations.data.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import static isel.leirt.mpd.annotations.MoreReflexUtils.getAnnotationsForField;



public class AnnotationsTests {

    @Test
    public void get_field_annotation() {
        Person p = new Person("Carlos",
                    LocalDate.of(2000, 2, 3));
        Annotation[] annotations =
                getAnnotationsForField("name", p);

        for(Annotation a : annotations) {
            System.out.println(a.annotationType().getSimpleName());
            if (a.annotationType() == Attribute.class) {
                Attribute attr = (Attribute) a;
                System.out.println(attr.dbName());
            }
        }

        Assertions.assertEquals(1, annotations.length);
        Assertions.assertEquals(Attribute.class, annotations[0].annotationType());

    }
}
