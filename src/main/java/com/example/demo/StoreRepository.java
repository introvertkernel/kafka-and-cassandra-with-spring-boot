package com.example.demo;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface StoreRepository extends CassandraRepository<SuperStore, UUID> {
}
