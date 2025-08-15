
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTests extends TestBase {
    // static public int userID = 2;

    @Test
    void testErrorLogin() {
        String authData = "{\\\"email\\\": \\\"login@reqres.in\\\", \\\"password\\\": \\\"pass123\\\"}";
        given()
                .body(authData)
                .contentType("application/json")
                .log().uri()
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", notNullValue());
    }

    @Test
    void testGetSingleUserSuccess() {
        given(requestSpec)
                .log().uri()
                .when()
                .get("/users/{id}")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
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
                .log().status()
                .statusCode(200);
    }

    @Test
    public void testDeleteUserSuccessfully() {
        given(requestSpec)
                .header("x-api-key", apiKey)
                .log().uri()
                .when()
                .delete("/users/{id}")
                .then()
                .log().status()
                .assertThat().statusCode(204);
    }

    @Test
    public void testDeleteUserFailsWithForbiddenAccess() {
        given(requestSpec)
                .header("x-api-key", apiKey + 2)
                .log().uri()
                .when()
                .delete("/users/{id}")
                .then()
                .log().status()
                .assertThat().statusCode(403);
    }

    @Test
    public void testUpdateUserWithPutRequest() {
        given(requestSpec)
                .header("x-api-key", apiKey)
                .log().uri()
                .when()
                .put("/users/{id}")
                .then()
                .log().status()
                .statusCode(200);
    }
}

