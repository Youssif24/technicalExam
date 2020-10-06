package com.example.elnady.technical.controller;

import com.example.elnady.technical.data.entity.Person;
import com.example.elnady.technical.service.PersonService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PersonControllerTest {


    /*
    * Rest Assured
    * given                  given: this properties may be content type, object, path variable
    * when                   when: this method with this url is called
    * then                   then: verify that the result is...........
    * */

    //private final String CONTEXT_PATH = "";
    @Autowired
    PersonService personService;

    @BeforeEach
    void setUp() {
        baseURI = "http://localhost";
        port = 8080;
    }

    @Test
    void getPersons() {
        Response response = given()
                .contentType("application/json")
                .when()
                .get("/persons")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        List<Person> personList = response.jsonPath().getList("$");
        Assert.assertNotNull(personList);
        System.out.println(personList.toString());
    }

    @Test
    void getPersonById() {
        Person person=personService.create(new Person("Rodri",23));
        System.out.println(person.getId());

        Response response = given()
                .contentType("application/json")
                .pathParam("id", person.getId()) //send id as a path param
                .when()
                .get("/persons/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        Person result_person = response.getBody().as(Person.class);
        Assert.assertNotNull(result_person);
        Assert.assertEquals(person.getId(), result_person.getId());
        //Assert.assertEquals(person.getName(),result_person.getName());
        String responseString=response.getBody().asString();
        System.out.println(responseString);
    }

    @Test
    void addNewPerson() {
        Response response = given()
                .contentType("application/json")
                .body(new Person( 1,"Mok", 22))
                .when()
                .post("/persons")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        String username = response.jsonPath().getString("name");
        Assert.assertNotNull(username);
        Assert.assertEquals("Mok", username);
    }

    @Test
    void updatePerson() {
        Person person = personService.create(new Person("Eric",20));
        Response response=given()
                .pathParam("id",person.getId())
                .body(person)
                .contentType("application/json")
                .when()
                .put("/persons/{id}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response();

        Person result_person=response.getBody().as(Person.class);
        Assert.assertNotNull(result_person);
        Assert.assertEquals("Eric",result_person.getName());
        System.out.println(response.getBody().asString());
    }

    @Test
    void deletePerson() {
        //RestAssured.registerParser("text/plain;charset=UTF-8",Parser.JSON);
        Person person=personService.create(new Person(1,"Jack",25));
        Response response=given()
                .contentType("application/json")
                .pathParam("id",1)
                .when()
                .delete("/persons/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.TEXT)
                .log().all()
                .extract()
                .response();

        String result=response.getBody().asString();
        Assert.assertNotNull(result);
        Assert.assertEquals("Deleted",result);
        System.out.println(result);

    }
}