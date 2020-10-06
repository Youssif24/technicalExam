package com.example.elnady.technical.service;

import com.example.elnady.technical.data.entity.Person;
import java.util.List;
import javassist.NotFoundException;

public interface PersonService {

    // returns all persons
    List<Person> getAll();

    // return user object with specific id
    Person getById(int id) throws NotFoundException;

    // add new person
    Person create(Person person);

    // update person with id
    Person update(int id, Person person) throws NotFoundException;

    // delete person with id
    String deleteById(int id);
}
