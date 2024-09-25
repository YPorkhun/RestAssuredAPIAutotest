package com.herokuapp.restfullbooker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;


public class GetBookingIdsTest extends BaseTest {

    //Java coding style
    @Test
    public void getBookingIdsTest() {
        //Get response from booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();

        //Make sure that response with code 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but is not");

        //Make sure that response isn't empty and has a booking id
        List<Integer> bookingIDs = response.jsonPath().getList("bookingid");
        Assert.assertFalse(bookingIDs.isEmpty(), "List of bookings is empty but shouldn't be");
    }
}
