package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest {

    @Test
    public void getBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Set path parameter
        spec.pathParams("bookingId", responseCreate.jsonPath().getInt("bookingid"));

        //Get response from booking
        Response response = RestAssured.given(spec).get("booking/{bookingId}");
        response.print();

        //Make sure that response with code 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");

        // Verify all fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Yuliia", "Firstname in response does not equal to Eric");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Porkhun", "Lastname in response does not equal to Wilson");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 1000, "Total price in response does not equal to 673");

        boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "Depositpaid is false but should be true");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-09-14", "Actual checkin is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-09-24", "Actual checkout is not expected");

        softAssert.assertAll();

    }
}