package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest {

    @Test
    public void getBookingTest() {

        //Get response from booking
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/4");
        response.print();

        //Make sure that response with code 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");

        // Verify all fields
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstName, "Mark", "Firstname in response does not equal to Mark");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Wilson", "Lastname in response does not equal to Wilson");

        int price = response.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 723, "Total price in response does not equal to 723");

        boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "Depositpaid is false but should be true");

        String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2022-04-17", "Actual checkin is not expected");

        String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2023-04-07", "Actual checkout is not expected");

        softAssert.assertAll();

    }
}