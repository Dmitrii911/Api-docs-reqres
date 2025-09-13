package tests;

import io.restassured.response.ValidatableResponse;
import models.LoginBodyModel;
import models.LoginResponseModel;
import models.MissingPasswordModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LoginSpec.*;

public class LoginExtendedTest extends TestBase {
    @Test
    void successfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail(TestData.validEmail);
        authData.setPassword(TestData.validPassword);

        LoginResponseModel response = step("Запрос", () ->
                given(baseRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(LoginResponseModel.class));
        step("Проверка ответа", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void missingPasswordTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail(TestData.validEmail);

        MissingPasswordModel response = step("Запрос", () ->
                given(baseRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(MissingPasswordModel.class));
        step("Проверка ответа", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    public void deleteUserSuccessfullyTest2() {

        ValidatableResponse response = step("Удаляем пользователя", () ->
                given(baseRequestSpec)
                       // .spec(baseRequestSpec)
                        .when()
                        .delete("/users/{id}")
                        .then()
                        .spec(responseSpec(204)));

    }
}
