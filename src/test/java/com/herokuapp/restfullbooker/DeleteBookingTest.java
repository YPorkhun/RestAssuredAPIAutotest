package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTest extends BaseTest {
    @Test
    public void deleteBookingTest() {

        //Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //Get bookingId of new booking from response
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //Delete booking using basic authentication
        Response responseDelete = RestAssured.given(spec).contentType(ContentType.JSON)
                .auth().preemptive()
                .basic("admin","password123")
                .delete("/booking/" + bookingid);
        responseDelete.print();

        //Make sure that response with code 201
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Status code should be 201 but is not");

        //Make sure that this booking id doesn't exist after deleting
        Response responseGet = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingid);
        responseGet.print();
        Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Body should be 'Not Found', but it's not");
    }
}
