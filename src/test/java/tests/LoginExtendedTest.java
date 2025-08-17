package tests;

import models.LoginBodyModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginExtendedTest extends TestBase {
    @Test
    void successfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail(getValidEmail());
        authData.setPassword(getValidPassword());

       LoginResponseModel response =  step("Запрос", ()->
               given()
               .header("x-api-key", apiKey)
                .body(authData)
                .contentType("application/json")
                .log().uri()
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class));
step("Ответ", ()->
       assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }
}
