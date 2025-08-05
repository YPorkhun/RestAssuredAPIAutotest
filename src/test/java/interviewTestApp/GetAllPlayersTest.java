package interviewTestApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
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
        List<Integer> playerIDs = response.jsonPath().getList("players");
        Assert.assertFalse(playerIDs.isEmpty(), "List of players is empty but shouldn't be");
    }
    @Test
    public void unsuccess_GetPlayer_all() {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/player/get/all" ) //wrong http method
                        .then()
                        .statusCode(405)
                        .extract().response();

        response.print();

        Assert.assertEquals(response.getStatusCode(), 405, "Status code should be 405 but is not");
        String errorMessage = response.jsonPath().getString("error");
        Assert.assertEquals(errorMessage, "Method Not Allowed");
    }
    }

