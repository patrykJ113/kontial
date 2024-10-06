package com.kontial.cloud.service.cloudservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPersonNameSummary() {
        List<Person> persons = Arrays.asList(
                new Person("1", "Thomas", LocalDate.of(1990, 1, 1)),
                new Person("2", "Evelin", LocalDate.of(1995, 2, 1)),
                new Person("3", "Oliver", LocalDate.of(1985, 3, 1)),
                new Person("4", "Thomas", LocalDate.of(1988, 4, 1)),
                new Person("5", "Oliver", LocalDate.of(1992, 5, 1)),
                new Person("6", "Oliver", LocalDate.of(2000, 6, 1))
        );

        when(personRepository.findAll()).thenReturn(persons);

        Map<String, Long> summary = personService.getPersonNameSummary();

        Map<String, Long> expectedSummary = new HashMap<>();
        expectedSummary.put("Evelin", 1L);
        expectedSummary.put("Oliver", 3L);
        expectedSummary.put("Thomas", 2L);

        assertEquals(expectedSummary, summary);
    }
}
