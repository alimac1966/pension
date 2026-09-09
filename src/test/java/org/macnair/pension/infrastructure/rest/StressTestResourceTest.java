package org.macnair.pension.infrastructure.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class StressTestResourceTest {

    @Test
    void testStressTestEndpoint() {

        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "pensionBalance": 100000,
                  "defaultGrowthRate": 0.06,
                  "mortgageBalance": 50000,
                  "defaultInterestRate": 0.04,
                  "mortgageMonthlyPayment": 500,
                  "monthlyDrawdown": 1500
                }
                """)
                .when()
                .post("/pension/stress-test")
                .then()
                .statusCode(200)
                .body("monthsUntilDepletion", greaterThan(0));
    }
}
