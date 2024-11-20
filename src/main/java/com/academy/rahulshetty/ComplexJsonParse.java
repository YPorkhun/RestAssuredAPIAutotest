package com.academy.rahulshetty;

import com.academy.rahulshetty.files.Requests;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(Requests.Courses());  // такий підхід використовується, коли розробка API ще не почалась (див. files.Courses)

        // Print number of courses returned by API
        int coursesCount = js.getInt("courses.size()");
        System.out.println(coursesCount);

        // Print Purchase Amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(purchaseAmount);

        // Print Title of the first course
        String titleOfFirstCourse = js.getString("courses[0].title");
        System.out.println(titleOfFirstCourse);

        // Print All course titles and their respective Prices
        for (int i =0; i<coursesCount; i++) {
            String titleOfAllCourses = js.getString("courses[" + i + "].title");
            int priceOfCourses = js.getInt("courses[" + i + "].price");
            System.out.println(titleOfAllCourses);
            System.out.println(priceOfCourses);
        }

        // Print number of copies sold by RPA Course
            System.out.println("Print number of copies sold by RPA Course");
            for (int i =0; i<coursesCount; i++)
            {
                String titleOfAllCourses = js.getString("courses["+ i + "].title");
                if (titleOfAllCourses.equalsIgnoreCase("RPA"))
                {
                    int numberOfCopiesRPACourse = js.getInt("courses["+ i + "].copies");
                    System.out.println(numberOfCopiesRPACourse);
                    break;
                }
            }
    }
        // Verify if Sum of all Course prices matches with Purchase Amount
    @Test
    public void sumOfAllCourses() {
        JsonPath js = new JsonPath(Requests.Courses());
        int coursesCount = js.getInt("courses.size()");
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");

        int sum = 0;
        for (int i =0; i<coursesCount; i++) {

            int coursesPrice = js.getInt("courses["+ i + "].price");
            int coursesCopies = js.getInt("courses["+ i + "].copies");
            int sumOfAllCourses = coursesCopies * coursesPrice;
            System.out.println(sumOfAllCourses);
            sum = sum + sumOfAllCourses;

        }
        System.out.println("Sum of all courses = "+ sum);
        Assert.assertEquals(sum, purchaseAmount);
    }

}
