package com.example.elnady.technical.controller;

import com.example.elnady.technical.data.entity.Person;
import com.example.elnady.technical.service.FrontLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/frontline")
public class FrontLineController {

    @Autowired
    private FrontLineService frontLineService;

    @GetMapping(value = "")
    public Iterable<Person> getAll()
    {
        return frontLineService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Person getPersonById(@PathVariable int id)
    {
        return frontLineService.findById(id);
    }

    @PostMapping(value = "")
    public Person addFrontPerson(@RequestBody Person person)
    {
        return frontLineService.create(person);
    }

    @PutMapping(value = "/{id}")
    public Person updateFrontPerson(@RequestBody Person person)
    {
        return frontLineService.update(person);
    }

}
