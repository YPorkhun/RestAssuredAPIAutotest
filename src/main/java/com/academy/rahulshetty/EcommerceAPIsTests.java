package com.academy.rahulshetty;
import com.academy.rahulshetty.files.LoginRequest;
import com.academy.rahulshetty.files.LoginResponse;
import com.academy.rahulshetty.files.Orders;
import com.academy.rahulshetty.files.OrdersDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceAPIsTests {
    RequestSpecification request = new RequestSpecBuilder()
            .setBaseUri("https://rahulshettyacademy.com")
            .setContentType(ContentType.JSON)
            .build();
    ResponseSpecification response = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();
    @Test
    public void EcommerceAPIsE2E () {

        //Login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("postman@gmail.com");
        loginRequest.setUserPassword("Hello123@");

        RequestSpecification requestSpecification =
                given()
                .spec(request)
                .body(loginRequest);

        LoginResponse loginResponse =
                requestSpecification
                        .when().log().all()
                        .post("/api/ecom/auth/login")
                        .then()
                        .spec(response)
                        .extract().response().as(LoginResponse.class);

        System.out.println(loginResponse.getToken());
        String token = loginResponse.getToken();
        System.out.println(loginResponse.getUserId());
        String userId = loginResponse.getUserId();

        // Create Product
        RequestSpecification newRequestCreateProduct = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                        .addHeader("Authorization", token)
                                .build();
        RequestSpecification requestCreateProduct =
                given()
                .spec(newRequestCreateProduct)
                .param("productName", "qwerty")
                .param("productAddedBy", userId)
                .param("productCategory","fashion")
                .param("productSubCategory","shirts")
                .param("productPrice","11500")
                .param("productDescription","Addias Originals")
                .param("productFor","women")
                .multiPart("productImage", new File("C:\\Users\\Yuliia\\Desktop\\400_0_1639743366-8157.jpg"));

        String responseCreateProduct = requestCreateProduct
                .when()
                .post("/api/ecom/product/add-product")
                .then().extract().response().asString();

        JsonPath js = new JsonPath(responseCreateProduct);
        String productId = js.getString("productId");
        System.out.println(productId);

        // Create Order
        RequestSpecification newRequestCreateOrder = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        OrdersDetails ordersDetails = new OrdersDetails();
        ordersDetails.setCountry("Ukraine");
        ordersDetails.setProductOrderedId(productId);

        List<OrdersDetails> ordersDetailsList = new ArrayList<>();
        ordersDetailsList.add(ordersDetails);

        Orders orders = new Orders();
        orders.setOrders(ordersDetailsList);

        RequestSpecification requestCreateOrder = given()
                .spec(newRequestCreateOrder)
                .log().all()
                .body(orders);

        String responseCreateOrder = requestCreateOrder
                .when()
                .post("/api/ecom/order/create-order")
                .then().log().all()
                .extract().response().asString();

        System.out.println(responseCreateOrder);

        //Delete Product
        RequestSpecification newRequestDeleteProduct = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .build();

        RequestSpecification requestDeleteProduct =
                given()
                        .log().all()
                        .spec(newRequestDeleteProduct)
                        .pathParam("productId", productId);

        String responseDeleteProduct = requestDeleteProduct
                        .when()
                        .delete("/api/ecom/product/delete-product/{productId}")
                        .then().log().all()
                        .extract().response().asString();

        JsonPath js1 = new JsonPath(responseDeleteProduct);
        Assert.assertEquals(js1.getString("message"), "Product Deleted Successfully");
    }
}
