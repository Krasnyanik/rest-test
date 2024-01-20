
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.http.cookie.Cookie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;

public class RestTest {
    private final static String URL = "http://localhost:8080/";

    @Test
    void test1(){
        Spec.installSpec(Spec.requestSpec(URL),Spec.responseSpec200());

        Cookies cookie = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .body("{\n" +
                        "  \"name\": \"Картофель\",\n" +
                        "  \"type\": \"VEGETABLE\",\n" +
                        "  \"exotic\":false\n" +
                        "}")
                .post("api/food")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getDetailedCookies();


        Response response = given()
                .cookies(cookie)
                .contentType(ContentType.JSON)
                .when()
                .get("api/food");

        Assertions.assertEquals("[Апельсин, Капуста, Помидор, Яблоко, Картофель]", response.jsonPath().getString("name"));






    }
}
