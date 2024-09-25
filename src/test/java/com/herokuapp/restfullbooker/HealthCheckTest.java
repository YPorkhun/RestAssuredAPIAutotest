package com.herokuapp.restfullbooker;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest extends BaseTest {
    //BDD syntax
    @Test
    public void healthCheckTest() {

               given().
                       spec(spec)
              .when()
                       .get("/ping")
              .then()
                       .assertThat()
                       .statusCode(201);
    }
}
