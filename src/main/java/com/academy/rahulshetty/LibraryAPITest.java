package com.academy.rahulshetty;

import com.academy.rahulshetty.files.Requests;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LibraryAPITest {

    //given - all input details (headers, content type)
    //when - submit the API (resource, http methods)
    //then - validate the response
    @Test (dataProvider = "BooksData")
    public void addBook (String isbn , String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String responseAddBook = given().header("Content-Type","application/json")
                .body(Requests.AddBook(isbn,aisle))
                .when()
                .post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = new JsonPath(responseAddBook);
        String bookID = js.getString("ID");
        System.out.println("Unique ID: " + bookID);
    }

    @DataProvider (name = "BooksData")
    public Object[][] getData ()
    {
       return new Object[][] {{"bla","123"},{"kla","456"},{"mla","789"}};
    }
}
