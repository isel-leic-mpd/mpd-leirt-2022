package isel.leirt.mpd.aula_03_16;

import com.google.gson.Gson;
import isel.leirt.mpd.reflection_intro.data.Person;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflexTests {

    @Test
    public void get_objects_from_json_tests() {
        Gson gson = new Gson();

        String jsonPersons =
                """
                    [
                        { 
                            "name" : "Joao",
                            "id" :  123,
                            "birthDate" : "2000/03/23"
                        },
                         { 
                            "name" : "Maria",
                            "id" :  456,
                            "birthDate" : "2010/06/25"
                        },
                        { 
                            "name" : "Benjamim",
                            "id" :  789,
                            "birthDate" : "2020/08/01"
                        }
                    ]""";

        Person[] expectedPersons = {
                new Person("Joao", 123, "2000/03/23"),
                new Person("Maria", 456, "2010/06/25"),
                new Person("Benjamim", 789, "2020/08/01")
        };
        
        Person[] persons = gson.fromJson(jsonPersons, Person[].class);

        for(Person p : persons)
            System.out.println(p);

        assertArrayEquals(expectedPersons, persons);
    }




}
