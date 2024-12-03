package com.academy.rahulshetty;

import com.academy.rahulshetty.files.GoogleMaps;
import com.academy.rahulshetty.files.Location;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;

public class GoogleMapsAPITest {
    @Test
    public void GoogleMapsAddAPI() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

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
                .queryParam("key", "qaclick123")
                .body(gm)
                .when().log().all()
                .post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(googleMapsAddAPIResponse);
    }
}
