package com.kontial.cloud.service.cloudservice.service;

import com.kontial.cloud.service.cloudservice.exception.PersonNotFoundException;
import com.kontial.cloud.service.cloudservice.exception.ValidationException;
import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.repository.PersonRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private static final String ID_REGEX = "^[a-zA-Z]\\d{4}$";

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        try {
            return personRepository.findAll();
        } catch (DataAccessException e) {
            throw new RuntimeException("Error retrieving persons from the database");
        }
    }

    public Optional<Person> getPersonById(String id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person not found with ID: " + id);
        }
        try {
            return personRepository.findById(id);
        } catch (PersonNotFoundException e) {
            throw new RuntimeException("Error when getting  person");
        }
    }

    public Person createPerson(Person person) throws Exception {
        validatePerson(person, true);
        try {
            return personRepository.save(person);
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong on our side when creating a new Person. Please try again later.");
        }
    }

    public Person updatePerson(String id, Person updatedPerson) throws Exception {
        validatePerson(updatedPerson, false);
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with ID: " + id + " was not found");
        }
        try {
            updatedPerson.setId(id);
            return personRepository.save(updatedPerson);
        } catch (RuntimeException e) {
            throw new RuntimeException("Something went wrong on our side when updating Person. Please try again later.");
        }
    }

    public void deletePerson(String id) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException("Person with ID: " + id + " was not found");
        }
        try {
            personRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Something went wrong on our side when deleting Person. Please try again later.");
        }
    }

    public Map<String, Long> getPersonNameSummary() {
        try {
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
        } catch (DataAccessException e) {
            throw new RuntimeException("Error when retrieving person name summary from the database");
        }
    }

    private void validatePerson(Person person, Boolean checkId) throws Exception {
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(person.getId());
        HashMap<String, String> errors = new HashMap<>();

        if (person.getName() == null || person.getName().isEmpty()) {
            errors.put("name", "Name cannot be empty");
        }

        if (person.getBirthday() == null) {
            errors.put("birthday", "Birthday is invalid");
        }

        if(!matcher.matches()) {
            errors.put("id", "ID must start with a letter and be followed by 4 digits");
        }

        if (checkId && personRepository.existsById(person.getId())) {
            errors.put("exists", "ID Already exists");
        }

        if(!errors.isEmpty()) {
            throw new ValidationException("Invalid Parameters", errors);
        }

    }
    
}