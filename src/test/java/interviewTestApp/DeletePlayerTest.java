package interviewTestApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class DeletePlayerTest extends BaseTestInterviewApp {

    String editorLogin = "supervisor";

    @Test
    public void success_DeletePlayer_user_role() {

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
                .delete("/player/delete/" + editorLogin)
                .then()
                .statusCode(204);

        response.print();
    }

    @Test
    public void unsuccess_DeletePlayer_user_role() {

        String playerId = "000";   //wrong player id

        String requestBody = String.format("""
            {
              "playerId": %s
            }
            """, playerId);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .delete("/player/delete/" + editorLogin)
                .then()
                .statusCode(400);
    }
    }

