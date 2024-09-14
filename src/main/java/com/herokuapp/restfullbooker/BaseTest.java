package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {
    protected static Response createBooking() {

        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Yuliia");
        body.put("lastname", "Porkhun");
        body.put("totalprice", 1000);
        body.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin","2024-09-14");
        bookingdates.put("checkout","2024-09-24");
        body.put("bookingdates",bookingdates );
        body.put("additionalneeds", "Breakfast");

        //Get response
        Response response = RestAssured.given().contentType(ContentType.JSON).body(body.toString()).post("https://restful-booker.herokuapp.com/booking");
        return response;
    }
}
