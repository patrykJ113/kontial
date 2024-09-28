package com.kontial.cloud.service.cloudservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.service.PersonService;
import org.springframework.web.bind.annotation.*;

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

	// Nested class to represent the summary
	public static class PersonSummary {
		private String id;
		private String name;
		private int year;

		public PersonSummary(String id, String name, int year) {
			this.id = id;
			this.name = name;
			this.year = year;
		}

		// Getters and Setters
		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public int getYear() {
			return year;
		}
	}

	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {
		Optional<Person> person = personService.getPersonById(id);
		return person.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/persons")
	public ResponseEntity<Person> createPerson(@RequestBody Person person) {
		Person createdPerson = personService.createPerson(person);
		return ResponseEntity.ok(createdPerson);
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
