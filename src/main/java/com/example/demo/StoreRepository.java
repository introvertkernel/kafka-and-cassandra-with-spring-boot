package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface StoreRepository extends CrudRepository<SuperStore, UUID> {
}
