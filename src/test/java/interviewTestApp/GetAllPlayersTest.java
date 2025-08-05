package interviewTestApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllPlayersTest extends BaseTestInterviewApp {

    @Test
    public void success_GetPlayer_all() {

        Response response =
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/player/get/all" )
                .then()
                .statusCode(200)
                .extract().response();

        response.print();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");
    }
    }

