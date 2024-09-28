package com.kontial.cloud.service.cloudservice.repository;

import com.kontial.cloud.service.cloudservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
    // JpaRepository already provides basic CRUD methods
}