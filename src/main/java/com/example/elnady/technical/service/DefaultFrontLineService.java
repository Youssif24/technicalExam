package com.example.elnady.technical.service;

import com.example.elnady.technical.data.entity.Person;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j  //this annotation for logging
public class DefaultFrontLineService implements FrontLineService {

    private final RestTemplate restTemplate;
    private final String personsEndpointUrl;

    @Override
    public List<Person> getAll() {
        return restTemplate.exchange(personsEndpointUrl,
            HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
            }).getBody();
    }

    @Override
    public Person findById(int id) {
        URI uriComponents = UriComponentsBuilder.fromUriString(personsEndpointUrl)
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();

        return restTemplate.getForObject(uriComponents, Person.class);

        //also i can use this
        /*HashMap<String,Integer> param=new HashMap<>();
        param.put("id",id);

        return restTemplate.getForObject(personsEndpointUrl+"/{id}", Person.class,param);*/
    }

    @Override
    public Person create(Person person) {
        HttpEntity<Person> httpEntity = new HttpEntity<>(person);

        return restTemplate.postForObject(personsEndpointUrl, httpEntity, Person.class);

        // i can send person directly without creating httpEntity
        // return restTemplate.postForObject(personsEndpointUrl, person, Person.class);
    }

    @Override
    public Person update(Person person) {
        HttpEntity<Person> httpEntity = new HttpEntity<>(person);

        return restTemplate.exchange(personsEndpointUrl, HttpMethod.PUT, httpEntity, Person.class)
            .getBody();
    }

    @Override
    public void deleteById(int id) {
        URI uriComponents = UriComponentsBuilder.fromUriString(personsEndpointUrl)
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();

        ResponseEntity<Void> responseEntity = restTemplate.exchange(uriComponents,
            HttpMethod.DELETE, null, Void.class);

        log.debug("Delete person with Id: {} status code: {}", id, responseEntity.getStatusCode().value());
    }
}
