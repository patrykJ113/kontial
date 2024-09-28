package com.kontial.cloud.service.cloudservice.persistence;

import com.kontial.cloud.service.cloudservice.model.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryDataSource {

    private static List<Person> persons = new ArrayList<>(); // Initialize the list

    static {
        // Add sample Person objects to the list
        persons.add(new Person("h2314", "Thomas", LocalDate.of(1982, 5, 15)));
        persons.add(new Person("f5962", "Thomas", LocalDate.of(1974, 8, 22)));
        persons.add(new Person("e5891", "Evelin", LocalDate.of(1996, 11, 30)));
        persons.add(new Person("t7811", "Oliver", LocalDate.of(1988, 3, 10)));
        persons.add(new Person("z5894", "Oliver", LocalDate.of(1990, 7, 5)));
        persons.add(new Person("s8971", "Oliver", LocalDate.of(1992, 12, 18)));
        persons.add(new Person("u5841", "Oliver", LocalDate.of(1989, 6, 25)));
        persons.add(new Person("n2361", "Jennifer", LocalDate.of(1991, 2, 14)));
        persons.add(new Person("w2054", "John", LocalDate.of(1980, 1, 1)));
        persons.add(new Person("x9815", "Mike", LocalDate.of(1985, 9, 9)));
        persons.add(new Person("c6358", "Henry", LocalDate.of(1978, 4, 23)));
        persons.add(new Person("a2601", "Lucas", LocalDate.of(2000, 10, 30)));
        persons.add(new Person("e8450", "Alice", LocalDate.of(1995, 12, 25)));
        persons.add(new Person("w9640", "Alice", LocalDate.of(1993, 7, 7)));
        persons.add(new Person("e5036", "Alice", LocalDate.of(1992, 8, 18)));
        persons.add(new Person("t8405", "Andrea", LocalDate.of(1984, 3, 3)));
        persons.add(new Person("u7840", "Ava", LocalDate.of(1997, 5, 19)));
        persons.add(new Person("i6922", "Ava", LocalDate.of(1999, 6, 22)));
    }

    public List<Person> getAll() {
        return persons;
    }
}
