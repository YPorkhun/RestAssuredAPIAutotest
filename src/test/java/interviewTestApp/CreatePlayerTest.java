package interviewTestApp;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class CreatePlayerTest extends BaseTestInterviewApp {

    String editorLogin = "supervisor";
    String invalidAge_1 = "11";
    String invalidAge_2 = "61";
    String login = "user_" + System.currentTimeMillis();
    String screenName = "Screen_" + System.currentTimeMillis();


    @Test
    public void success_CreatePlayer_user_role() {
        Response response = createPlayer();
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");
    }

    @Test
    public void unsuccess_CreatePlayer_under_age() {

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("age", invalidAge_1)
                .queryParam("gender", "female")
                .queryParam("login", login)
                .queryParam("password", "pass1234!@#$")
                .queryParam("role", "user")
                .queryParam("screenName", screenName)
                .when()
                .get("/player/create/{editor}", editorLogin)
                .then()
                .statusCode(400)
                .extract().response();
        response.print();
    }

    @Test
    public void unsuccess_CreatePlayer_over_age() {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("age", invalidAge_2)
                .queryParam("gender", "female")
                .queryParam("login", login)
                .queryParam("password", "pass1234!@#$")
                .queryParam("role", "user")
                .queryParam("screenName", screenName)
                .when()
                .get("/player/create/{editor}", editorLogin)
                .then()
                .statusCode(400)
                .extract().response();
        response.print();;
    }
}


