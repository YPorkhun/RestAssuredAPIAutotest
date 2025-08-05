package interviewTestApp;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class BaseTestInterviewApp {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://3.68.165.45";
    }

    protected Response createPlayer() {

        String editorLogin = "supervisor";
        String login = "user_" + System.currentTimeMillis();
        String screenName = "Screen_" + System.currentTimeMillis();

        Response responseCreatePlayer =
                given()
                        .contentType(ContentType.JSON)
                        .queryParam("age", 44)
                        .queryParam("gender", "female")
                        .queryParam("login", login)
                        .queryParam("password", "pass1234!@#$")
                        .queryParam("role", "user")
                        .queryParam("screenName", screenName)
                        .when()
                        .get("/player/create/{editor}", editorLogin)
                        .then()
                        .statusCode(200)
                        .extract().response();
        return responseCreatePlayer;
    }
}
