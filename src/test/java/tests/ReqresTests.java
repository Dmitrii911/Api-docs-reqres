package tests;

import models.LoginBodyModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static specs.LoginSpec.*;
import static specs.UserSpec.userIdRequestSpec;

public class ReqresTests extends TestBase {

    @Test
    void testErrorLogin() {
        LoginBodyModel loginBodyModel = new LoginBodyModel("login@reqres.in", "pass123");
        given(baseRequestSpec)
                .body(loginBodyModel)
                .when()
                .post("/login")
                .then()
                .spec(responseSpec(400))
                .body("error", notNullValue());
    }

    @Test
    void testGetSingleUserSuccess() {
        Integer userID = 2;
        given(userIdRequestSpec)
                .log().uri()
                .when()
                .get("/users/{id}")
                .then()
                .spec(responseSpec(200))
                .body("data.id", equalTo(userID));
    }

    @Test
    void testGetUsersSuccess() {
        given()
                .header("x-api-key", apiKey)
                .log().uri()
                .when()
                .get("/users")
                .then()
                .spec(responseSpec(200));
    }

    @Test
    public void testDeleteUserSuccessfully() {
        given(userIdRequestSpec)
                .log().uri()
                .when()
                .delete("/users/{id}")
                .then()
                .spec(responseSpec(204));
    }

    @Test
    public void testDeleteUserFailsWithForbiddenAccess() {
        given(userIdRequestSpec)
                .header("x-api-key", apiKey + 2)
                .log().uri()
                .when()
                .delete("/users/{id}")
                .then()
                .spec(responseSpec(403));
    }

    @Test
    public void testUpdateUserWithPutRequest() {
        given(userIdRequestSpec)
                .header("x-api-key", apiKey)
                .log().uri()
                .when()
                .put("/users/{id}")
                .then()
                .spec(responseSpec(200));
    }
}

