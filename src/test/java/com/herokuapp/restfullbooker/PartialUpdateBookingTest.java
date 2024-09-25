package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTest extends BaseTest {
    @Test
    public void partialUpdateBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking from response
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Create new JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "SomeNewName");

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin","2024-10-15");
        bookingdates.put("checkout","2024-10-23");

        body.put("bookingdates",bookingdates );

        //Partial update booking using basic authentication
        Response responseUpdate = RestAssured.given(spec).contentType(ContentType.JSON)
                .auth().preemptive()
                .basic("admin","password123").
                body(body.toString())
                .patch("/booking/" + bookingid);
        responseUpdate.print();

        //Make sure that response with code 200
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200 but is not");

        //Verifications
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "SomeNewName", "Firstname in response does not equal to SomeNewName");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Porkhun", "Lastname in response does not equal to Wilson");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 1000, "Total price in response does not equal to 673");

        boolean depositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "Depositpaid is false but should be true");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-10-15", "Actual checkin is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-10-23", "Actual checkout is not expected");

        softAssert.assertAll();
    }
}
