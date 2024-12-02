package com.academy.rahulshetty;

import com.academy.rahulshetty.files.GoogleMaps;
import com.academy.rahulshetty.files.Location;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GoogleMapsAPITestWithSpecBuilder {
    @Test
    public void GoogleMapsAddAPI() {

        //Додавання і використання RequestSpecBuilder/ResponseSpecification для скорочення написання коду, що повторюється
        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        ResponseSpecification response = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

        //Serialization
        GoogleMaps gm = new GoogleMaps();
        gm.setAccuracy(45);
        gm.setName("frontline house");
        gm.setPhone_number("+380954672491");
        gm.setAddress("country, post code, street, home number");
        List<String> myList= new ArrayList<String>(); //оскільки поле types є колекцією строк - ми не можемо наповнити її звичайними строками, тому треба створити кастомну колекцію, наповнити її, а потім передати в вигляді агрументу
        myList.add("Park");
        myList.add("Shoes market");
        myList.add("Playground");
        gm.setTypes(myList);
        gm.setWebsite("https://rahulshettyacademy.com/practice-project");
        gm.setLanguage("ukrainian");
        Location loc = new Location(); // локація - це кастомний тип змінних, тому так само створюємо об'єкт цього класу, додаємо в нього значення і підставляємо
        loc.setLat(-38.383494);
        loc.setLng(33.427362);
        gm.setLocation(loc);

        String googleMapsAddAPIResponse =
                given()
                        .spec(request)
                .body(gm)
                .when().log().all()
                .post("/maps/api/place/add/json")
                .then()
                        .spec(response)
                        .extract().response().asString();

        System.out.println(googleMapsAddAPIResponse);
    }
}
