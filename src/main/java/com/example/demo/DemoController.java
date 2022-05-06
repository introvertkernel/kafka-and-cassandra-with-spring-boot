package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Controller("/")
public class DemoController {
    @Autowired
    DoctorRepository doctorRepository;

    @GetMapping("save/{name}")
    public Doctor saveToCassandra(@PathVariable String name){
        return doctorRepository.save(new Doctor(UUID.randomUUID(), name, "", new HashSet<>(Arrays.asList("surgery"))));
    }
}
