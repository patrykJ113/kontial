package com.kontial.cloud.service.cloudservice;

import com.kontial.cloud.service.cloudservice.dto.PersonDTO;
import com.kontial.cloud.service.cloudservice.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api")
public class PersonController {

	private final PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@GetMapping("/persons")
	public List<PersonSummary> getAllPersons() {
		return personService.getAllPersons().stream()
				.map(person -> new PersonSummary(person.getId(), person.getName(), person.getBirthday().getYear()))
				.collect(Collectors.toList());
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {
		Optional<Person> person = personService.getPersonById(id);
		return person.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/persons")
	public ResponseEntity<?> createPerson(@RequestBody PersonDTO personDTO) {
		try {
			DateConverter dc = new DateConverter();
			LocalDate birthday = dc.convertToLocalDate(personDTO.getBirthday());
			Person person = new Person(personDTO.getId(), personDTO.getName(), birthday);
			personService.createPerson(person);
			return new ResponseEntity<>("Person added successfully", HttpStatus.OK);
		} catch (ValidationException e) {
			return new ResponseEntity<>(e.getData(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/persons/{id}")
	public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person person) {
		Person updatedPerson = personService.updatePerson(id, person);
		return ResponseEntity.ok(updatedPerson);
	}

	@DeleteMapping("/persons/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable String id) {
		personService.deletePerson(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/persons/summary")
	public Map<String, Long> getPersonNameSummary() {
		return personService.getPersonNameSummary();
	}
}
