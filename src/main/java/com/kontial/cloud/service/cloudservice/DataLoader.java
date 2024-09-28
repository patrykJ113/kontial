package com.kontial.cloud.service.cloudservice;

import com.kontial.cloud.service.cloudservice.model.Person;
import com.kontial.cloud.service.cloudservice.persistence.InMemoryDataSource;
import com.kontial.cloud.service.cloudservice.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final InMemoryDataSource inMemoryDataSource;

    public DataLoader(PersonRepository personRepository, InMemoryDataSource inMemoryDataSource) {
        this.personRepository = personRepository;
        this.inMemoryDataSource = inMemoryDataSource;
    }

    @Override
    public void run(String... args) {
        // Load data from InMemoryDataSource and save to H2 database
        personRepository.saveAll(inMemoryDataSource.getAll());
    }
}