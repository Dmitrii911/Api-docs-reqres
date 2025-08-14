import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.openqa.selenium.devtools.v135.audits.model.MixedContentResourceType.JSON;

public class ReqresTests {
    @BeforeAll
    static void setUp() {
        // Устанавливаем базовую точку входа для API
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void postLoginTest() {

            String authData = "{  \"username\": \"Log\",\n" +
                    "  \"email\": \"string@mail.ru\",\n" +
                    "  \"password\": \"string123\"}";

            given()
                    .header("x-api-key", "reqres-free-v1")
                    .body(authData)
                    .contentType(String.valueOf(JSON))
                    .log().uri()

                    .when()
                    .post("/api/login")

                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .body("token", notNullValue());
        }

    @Test
    void testGetUserByID() {

         int userID = 2;

        given()
                //.header("x-api-key", "reqres-free-v1")
                .pathParam("id", userID)
                .log().uri()

                .when()
                .get("/api/users/{id}")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(userID)) ;
    }

    @Test
    void fetchesUsersTest() {
        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    }

