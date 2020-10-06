package com.example.elnady.technical.service;

import com.example.elnady.technical.data.entity.Person;
import com.example.elnady.technical.data.repo.PersonRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultPersonService implements PersonService {


    private final PersonRepository personRepository;

    // returns all persons
    @Override
    public List<Person> getAll() {
        Iterable<Person> iterable = personRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
    }

    // return user object with specific id
    @Override
    public Person getById(int id) throws NotFoundException {
        Person person = personRepository.findById(id).get();
        if (person != null) {
            return person;
        } else {
            throw new NotFoundException("Person with id: " + id + " Not found");
        }
    }

    // add new person
    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    // update person with id
    @Override
    public Person update(int id, Person person) throws NotFoundException {
        Person updatedPerson = personRepository.findById(id).get();
        if (person != null) {
            updatedPerson.setName(person.getName());
            updatedPerson.setAge(person.getAge());
            personRepository.save(updatedPerson);
            return updatedPerson;
        } else {
            throw new NotFoundException("Person with id: " + id + " Not found");
        }
    }

    // delete person with id
    @Override
    public String deleteById(int id) {
        Person person=personRepository.findById(id).get();
        if (person != null)
        {
            personRepository.deleteById(id);
            return "Deleted";
        }
        else
        {
            return "Not Found";
        }
    }
}
