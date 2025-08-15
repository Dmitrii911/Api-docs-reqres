import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected static String apiKey = System.getProperty("API_KEY", "reqres-free-v1");

    protected RequestSpecification requestSpec;
    protected static int userID = 2;

    public TestBase() {
        this.requestSpec = RestAssured.given();
        this.requestSpec.pathParam("id", userID);
    }

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
}
