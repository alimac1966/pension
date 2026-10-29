package org.macnair.pension.infrastructure.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class ProjectionResourceTest {

    @Test
    void returnsProjectionRowsForValidRequest() {
        var response = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "pensionBalance": 200000,
                          "growthRate": 0.04,
                          "inflation": 0.02,
                          "mortgageBalance": 10000,
                          "spending": 30000,
                          "savings": 5000,
                          "taxAllowance": 12000,
                          "statePension": 10000
                        }
                        """)
                .when()
                .post("/projection")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        assertTrue(response.jsonPath().getList("$").size() > 1);
        assertEquals(45_000, response.jsonPath().getFloat("[0].taxFreePension"), 0.001);
        assertEquals(150_000, response.jsonPath().getFloat("[0].taxablePension"), 0.001);
    }

    @Test
    void servesDashboardAtRoot() {
        given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .contentType("text/html")
                .body(containsString("Financial Planning"));
    }
}
