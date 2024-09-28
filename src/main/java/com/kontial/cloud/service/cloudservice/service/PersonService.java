package com.kontial.cloud.service.cloudservice.service;

import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(String id) {
        return personRepository.findById(id);
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(String id, Person updatedPerson) {
        return personRepository.save(updatedPerson);
    }

    public void deletePerson(String id) {
        personRepository.deleteById(id);
    }

    public Map<String, Long> getPersonNameSummary() {
        List<Person> allPersons = personRepository.findAll();

        return allPersons.stream()
                .collect(Collectors.groupingBy(
                        Person::getName,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        java.util.LinkedHashMap::new
                ));
    }
}