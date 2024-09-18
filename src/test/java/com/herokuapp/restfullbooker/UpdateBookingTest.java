package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTest extends BaseTest {

    @Test
    public void updateBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking from response
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Kyrylo");
        body.put("lastname", "Porkhun");
        body.put("totalprice", 1500);
        body.put("depositpaid", false);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin","2024-09-14");
        bookingdates.put("checkout","2024-09-24");
        body.put("bookingdates",bookingdates );
        body.put("additionalneeds", "Breakfast");

        //Update booking
        Response responseUpdate = RestAssured.given().contentType(ContentType.JSON).
                body(body.toString()).put("https://restful-booker.herokuapp.com/booking/" + bookingid);
        responseUpdate.print();

        //Make sure that response with code 200
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Status code should be 200 but is not");

        //Verifications
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Kyrylo", "Firstname in response does not equal to Eric");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Porkhun", "Lastname in response does not equal to Wilson");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 1500, "Total price in response does not equal to 673");

        boolean depositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "Depositpaid is true but should be false");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-09-14", "Actual checkin is not expected");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-09-24", "Actual checkout is not expected");

        String actualAdditionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Breakfast", "Actual additional needs is not expected");

        softAssert.assertAll();
    }
}
