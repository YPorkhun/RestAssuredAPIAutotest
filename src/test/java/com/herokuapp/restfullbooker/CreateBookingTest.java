package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTest extends BaseTest {

    //@Test
    public void createBookingTest() {

        Response response = createBooking();
        response.print();

        //Make sure that response with code 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");

        //Verifications
        SoftAssert softAssert = new SoftAssert();

        String actualFirstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstName, "Yuliia", "Firstname in response does not equal to Eric");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Porkhun", "Lastname in response does not equal to Wilson");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, 1000, "Total price in response does not equal to 673");

        boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositPaid, "Depositpaid is false but should be true");

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2024-09-14", "Actual checkin is not expected");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2024-09-24", "Actual checkout is not expected");

        String actualAdditionalNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualAdditionalNeeds, "Breakfast", "Actual additional needs is not expected");

        softAssert.assertAll();
    }

    @Test
    public void createBookingWithPOJOTest() {
        //Create body using POJOs
        Bookingdates bookingdates = new Bookingdates("2024-10-23","2024-10-30");
        Booking booking = new Booking("Kateryna","Posvaliuk", 800, true, bookingdates, "Breakfast");

        //Get response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");
        response.print();
        BookingId bookingId = response.as(BookingId.class);

        //Make sure that response with code 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");

        System.out.println("Request booking : " + booking.toString());
        System.out.println("Response booking : " + bookingId.toString());

        //Verifications
        Assert.assertEquals(bookingId.getBooking().toString(), booking.toString());
    }
}
