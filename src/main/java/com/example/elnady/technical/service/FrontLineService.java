package com.example.elnady.technical.service;

import com.example.elnady.technical.data.entity.Person;
import java.util.List;

public interface FrontLineService {

    List<Person> getAll();

    Person findById(int id);

    Person create(Person person);
    Person update(Person person);

    void deleteById(int id);
}