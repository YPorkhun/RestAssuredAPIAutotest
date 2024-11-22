package com.academy.rahulshetty.files;

public class Requests {

    public static String Courses() {
        return "{ \"dashboard\": \n" +
                "{\n" +
                "\"purchaseAmount\": 910,\n" +
                "\"website\": \"rahulshettyacademy.com\"\n" +
                "},\n" +
                "\"courses\": [\n" +
                "{\n" +
                "\"title\": \"Selenium Python\",\n" +
                "\"price\": 50,\n" +
                "\"copies\": 6\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"Cypress\",\n" +
                "\"price\": 40,\n" +
                "\"copies\": 4\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"RPA\",\n" +
                "\"price\": 45,\n" +
                "\"copies\": 10\n" +
                "}\n" +
                "]\n" +
                "}";
    }

    public static String AddBook() {
          return "{\n" +
                  "\"name\":\"Learn Appium Automation with Java\",\n" +
                  "\"isbn\":\"bcd\",\n" +
                  "\"aisle\":\"227req\",\n" +
                  "\"author\":\"John foe\"\n" +
                  "}\n";
    }
}