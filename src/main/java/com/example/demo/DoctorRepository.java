package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface DoctorRepository extends CrudRepository<Doctor, UUID> {
    Doctor findByFirstName(String userName);
}
