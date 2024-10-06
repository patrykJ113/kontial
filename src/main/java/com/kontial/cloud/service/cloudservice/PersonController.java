package com.kontial.cloud.service.cloudservice;

import com.kontial.cloud.service.cloudservice.contoller.PersonSummary;
import com.kontial.cloud.service.cloudservice.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
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
	@CrossOrigin(origins = "http://localhost:4200")
	public List<?> getAllPersons(@RequestParam(name = "date", required = false) String date) {
		if("year".equalsIgnoreCase(date)) {
			return personService.getAllPersons().stream()
					.map(person -> new PersonSummary(person.getId(), person.getName(), person.getBirthday().getYear()))
					.collect(Collectors.toList());
		}
		return personService.getAllPersons();
	}

	@GetMapping("/persons/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Person> getPersonById(@PathVariable String id) {
		Optional<Person> person = personService.getPersonById(id);
		return person.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/persons")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Map<String, String>> createPerson(@RequestBody PersonDTO personDTO) throws Exception{

			DateConverter dc = new DateConverter();
			LocalDate birthday = dc.convertToLocalDate(personDTO.getBirthday());
			Person person = new Person(personDTO.getId(), personDTO.getName(), birthday);
			personService.createPerson(person);

			Map<String, String> response = new HashMap<>();
			response.put("message", "Person added successfully");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/persons/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody PersonDTO personDTO) throws Exception{
		DateConverter dc = new DateConverter();
		LocalDate birthday = dc.convertToLocalDate(personDTO.getBirthday());
		Person person = new Person(personDTO.getId(), personDTO.getName(), birthday);
		Person updatedPerson = personService.updatePerson(id, person);
		return ResponseEntity.ok(updatedPerson);
	}

	@DeleteMapping("/persons/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Void> deletePerson(@PathVariable String id) {
		personService.deletePerson(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/persons/summary")
	@CrossOrigin(origins = "http://localhost:4200")
	public Map<String, Long> getPersonNameSummary() {
		return personService.getPersonNameSummary();
	}
}
