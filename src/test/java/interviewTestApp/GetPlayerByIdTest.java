package interviewTestApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class GetPlayerByIdTest extends BaseTestInterviewApp {

    @Test
    public void success_GetPlayer_by_id() {

        Response response = createPlayer();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");
        String playerId = response.jsonPath().getString("id");

        String requestBody = String.format("""
            {
              "playerId": %s
            }
            """, playerId);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/player/get")
                .then()
                .statusCode(200);

        response.print();
    }
    @Test
    public void unsuccess_GetPlayer_by_id_invalid_requestBody() {

        Response response = createPlayer();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");
        String playerId = response.jsonPath().getString("id");

        String requestBody = String.format("""
            {
              "player": %s
            }
            """, playerId);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/player/get")
                .then()
                .statusCode(404);

        response.print();
    }
    }

