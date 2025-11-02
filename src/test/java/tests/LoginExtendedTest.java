package tests;

import io.restassured.response.ValidatableResponse;
import models.LoginBodyModel;
import models.LoginResponseModel;
import models.MissingPasswordModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static specs.LoginSpec.*;
import static specs.UserSpec.userIdRequestSpec;

public class LoginExtendedTest extends TestBase {
    @Test
    @DisplayName("Успешная авторизация")
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

        String token = response.getToken();
        assertFalse(token.trim().isEmpty(), "Токен не должен быть пустым");
        assertTrue(token.length() >= 8, "Длина токена должна быть больше или равна 8 символам");
        assertTrue(token.matches("[A-Za-z0-9]+"), "Токен должен содержать только буквенно-цифровые символы");
    }

    @Test
    @DisplayName("Тест c пропущенным паролем")
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
    @DisplayName("Успешное удаление пользователя")
    void deleteUserSuccessfullyTest() {

        ValidatableResponse response = step("Удаляем пользователя", () -> {
            return given(userIdRequestSpec)
                    .spec(baseRequestSpec)
                    .when()
                    .delete("/users/{id}")
                    .then()
                    .spec(responseSpec(204));
        });
    }
}
