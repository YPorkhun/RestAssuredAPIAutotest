package interviewTestApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class UpdatePlayerTest extends BaseTestInterviewApp {

    String editorLogin = "supervisor";

    @Test
    public void success_UpdatePlayer() {

        Response response = createPlayer();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");

        String playerId = response.jsonPath().getString("id");
        String gender = response.jsonPath().getString("gender");
        String login = response.jsonPath().getString("login");
        String password = response.jsonPath().getString("password");
        String role = response.jsonPath().getString("role");
        String updated_screenName = "test_user";
        String updated_age = "55";

        String requestBody = String.format("""
            {
  
                    "age": %s,
                    "gender": "%s",
                    "login": "%s",
                    "password": "%s",
                    "role": "%s",
                    "screenName": "%s"
            
            }
            """, updated_age, gender, login, password, role, updated_screenName);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch("/player/update/" + editorLogin + "/" + playerId)
                .then()
                .statusCode(200);

        response.print();
    }

    @Test
    public void unsuccess_UpdatePlayer_over_age() {

        Response response = createPlayer();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");

        String playerId = response.jsonPath().getString("id");
        String gender = response.jsonPath().getString("gender");
        String login = response.jsonPath().getString("login");
        String password = response.jsonPath().getString("password");
        String role = response.jsonPath().getString("role");
        String updated_screenName = "test_user";
        String updated_over_age = "81";

        String requestBody = String.format("""
            {
  
                    "age": %s,
                    "gender": "%s",
                    "login": "%s",
                    "password": "%s",
                    "role": "%s",
                    "screenName": "%s"
            
            }
            """, updated_over_age, gender, login, password, role, updated_screenName);
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch("/player/update/" + editorLogin + "/" + playerId)
                .then()
                .statusCode(400);

        response.print();
    }
    }

