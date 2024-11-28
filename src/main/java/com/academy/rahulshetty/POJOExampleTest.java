package com.academy.rahulshetty;

import com.academy.rahulshetty.files.Api;
import com.academy.rahulshetty.files.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;

public class POJOExampleTest {
    public static void main(String[] args) {

        String response = given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");
        System.out.println(accessToken);

        //Serialization and deserialization
        POJOExample pojoEx = given()
                .queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(POJOExample.class);

        System.out.println(pojoEx.getLinkedIn());
        System.out.println(pojoEx.getInstructor());
        System.out.println(pojoEx.getCourses().getApi().get(0).getCourseTitle());

        //Витягти з json значення price
        List<Api> coursesPrice = pojoEx.getCourses().getApi();
        for (int i = 0; i < coursesPrice.size(); i++) {
            if (coursesPrice.get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java"))
            {
                System.out.println(coursesPrice.get(i).getPrice());
            }
        }

        //Отримати назви курсів з webAutomation
        String [] expectedArray1 = {"Selenium Webdriver Java","Cypress","Protractor"}; //масив строк, необхідний для порівнянння результату з тесту про отримання заголовків курсів
        List <String> expectedArray = Arrays.asList(expectedArray1); // конвертація масиву строк в колекцію, щоб їх можна було порівняти
        ArrayList <String> actualArray = new ArrayList <String>(); // масив для запису отриманих заголовків

        List <WebAutomation> webAuto = pojoEx.getCourses().getWebAutomation();
        for (int i = 0; i < webAuto.size(); i++) {
            actualArray.add(webAuto.get(i).getCourseTitle());
        }
        Assert.assertEquals(expectedArray, actualArray);
    }
}