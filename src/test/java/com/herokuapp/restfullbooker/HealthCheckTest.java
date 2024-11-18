package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
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
    @Test
    public void headersAndCookiesTest() {
        // First way is to declare headers and cookies before

        Header someHeader = new Header("some_name","some_value");
        spec.header(someHeader);

        Cookie someCookie = new Cookie.Builder("some_cookie", "some_value").build();
        spec.cookie(someCookie);

        Response response = RestAssured.given(spec)
                .cookie("Test cookie name","test cookie value")
                .header("Test header", "Test header value").log().all().get("/ping");

        //Second way is to get headers and cookies separately

        Headers headers = response.getHeaders();
        System.out.println("Headers: " + headers);

        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server: " + serverHeader2);

        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: " + cookies);
    }
}
