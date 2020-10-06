package com.example.elnady.technical.config;


import com.example.elnady.technical.data.repo.PersonRepository;
import com.example.elnady.technical.service.DefaultPersonService;
import com.example.elnady.technical.service.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonServiceConfiguration {

    @Bean
    public PersonService personService(PersonRepository personRepository){
        return new DefaultPersonService(personRepository);
    }
}

