package com.kontial.cloud.service.cloudservice;

import com.kontial.cloud.service.cloudservice.persistence.InMemoryDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class PersonController {

	private InMemoryDataSource inMemoryDataSource;

	@Autowired
	public PersonController(InMemoryDataSource inMemoryDataSource) {
		this.inMemoryDataSource = inMemoryDataSource;
	}

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public ResponseEntity<?> persons() {
		var persons = inMemoryDataSource.getAll();
		return ResponseEntity.ok(persons);
	}
}
