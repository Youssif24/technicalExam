package com.example.elnady.technical.controller;

import com.example.elnady.technical.data.entity.Person;
import com.example.elnady.technical.service.PersonService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping(value = "")
    public Iterable<Person> getPersons() {
        return personService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Person getPersonById(@PathVariable(value = "id") int id) throws NotFoundException {
        return personService.getById(id);
    }

    @PostMapping(value = "")
    public Person addNewPerson(@RequestBody Person newPerson) {
        return personService.create(newPerson);
    }

    @PutMapping(value = "/{id}")
    public Person updatePerson(@PathVariable(value = "id") int id, @RequestBody Person updatePerson) throws NotFoundException {
        return personService.update(id, updatePerson);
    }

    @DeleteMapping(value = "/{id}")
    public String deletePerson(@PathVariable(value = "id") int id) {
         return personService.deleteById(id);
    }

}
