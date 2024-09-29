package com.kontial.cloud.service.cloudservice.service;

import com.kontial.cloud.service.cloudservice.exceptions.ValidationException;
import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(String id) {
        return personRepository.findById(id);
    }

    public Person createPerson(Person person) throws Exception {
        validatePerson(person);
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

    private void validatePerson(Person person) throws Exception {
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
            errors.put("ID", "must start with a letter and be followed by 4 digits");
        }

        if (personRepository.existsById(person.getId())) {
            errors.put("id", "ID Already exists");
        }

        if(!errors.isEmpty()) {
            throw new ValidationException("Invalid Parameters", errors);
        }

    }

//    private boolean isValidBirthday(LocalDate birthday) {
//
//        if (birthday == null) {
//            return false;
//        }
//
//        try {
//            // Check if birthday is in the correct format (dd-MM-yyyy)
//            birthday.format(DATE_FORMAT);
//            return true;
//        } catch (DateTimeParseException e) {
//            return false;
//        }
//    }
}