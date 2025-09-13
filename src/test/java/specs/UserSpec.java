package specs;

import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static tests.TestBase.apiKey;

public class UserSpec {
    public static RequestSpecification userIdRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .pathParam("id", 2)
            .header("x-api-key", apiKey)
            .contentType("application/json");
}