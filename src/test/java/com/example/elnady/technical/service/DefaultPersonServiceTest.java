package com.example.elnady.technical.service;

import com.example.elnady.technical.data.entity.Person;
import com.example.elnady.technical.data.repo.PersonRepository;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class DefaultPersonServiceTest {

    //    @Mock
   //    PersonService personService;

    @Mock
    PersonRepository personRepository;

    //inject all mocks in this class
    @InjectMocks
    DefaultPersonService defaultPersonService;

    List<Person> personList;
    Person testPerson;

    @BeforeEach
    void setUp() {
        //initialize the test class : DefaultPersonService
        MockitoAnnotations.initMocks(this);

        testPerson = new Person(0, "Test", 19);
        Person person1 = new Person(1, "Henric", 28);
        Person person2 = new Person(2, "Sergi", 30);
        personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);
    }

    @Test
    void getAll() {
        when(personRepository.findAll()).thenReturn(personList);
        List<Person> resultPersonList = defaultPersonService.getAll();
        Assert.assertNotNull(resultPersonList);
        Assert.assertEquals(resultPersonList.size(), 2);
        Assert.assertEquals(resultPersonList.get(0).getName(), "Henric");
    }

    @Test
    void getById() throws NotFoundException {
        //Person person = new Person(0, "Kati", 27);
        when(personRepository.findById(anyInt())).thenReturn(Optional.of(testPerson));
        Person result_person = defaultPersonService.getById(testPerson.getId());
        Assert.assertNotNull(result_person);
        Assert.assertEquals(result_person.getName(), testPerson.getName());
    }

    @Test
    void create() {
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);
        Person result_person = defaultPersonService.create(testPerson);
        Assert.assertNotNull(result_person);
        Assert.assertEquals(result_person.getAge(), testPerson.getAge());
    }

    @Test
    void update() throws NoSuchElementException, NotFoundException {
        //Person person = new Person(1, "David", 22);
        when(personRepository.findById(1)).thenReturn(Optional.of(testPerson));
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        Person result_person = defaultPersonService.update(1, testPerson);
        Assert.assertNotNull(result_person);
        Assert.assertEquals(result_person.getId(), testPerson.getId());
        Assert.assertEquals(result_person.getAge(), testPerson.getAge());
    }

    @Test
    void deleteByIdTest() {
        doNothing().when(personRepository).deleteById(anyInt());
        when(personRepository.findById(anyInt())).thenReturn(Optional.of(new Person(0, "test", 20)));
        String result = defaultPersonService.deleteById(0);
        Assert.assertEquals(result, "Deleted");
    }
}