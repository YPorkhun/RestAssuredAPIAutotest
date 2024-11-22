package com.academy.rahulshetty;

import com.academy.rahulshetty.files.Requests;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class LibraryAPI {
    @Test
    public void addBook () {
        RestAssured.baseURI = "http://216.10.245.166";
        String responseAddBook = given().header("Content-Type","application/json")
                .body(Requests.AddBook())
                .when()
                .post("/Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = new JsonPath(responseAddBook);
        String bookID = js.getString("ID");
        System.out.println("Unique ID: " + bookID);
    }
}
