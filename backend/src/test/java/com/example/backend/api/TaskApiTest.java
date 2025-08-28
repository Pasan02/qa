package com.example.backend.api;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void addTask_returns201AndTaskBody() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\": \"Buy milk\"}")
        .when()
            .post("/api/tasks")
        .then()
            .statusCode(201)
            .body("title", equalTo("Buy milk"));
    }

    @Test
    void addTask_withBlankTitle_returns400() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"title\": \"   \"}")
        .when()
            .post("/api/tasks")
        .then()
            .statusCode(400);
    }
}
