package DAY8;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

public class SSLTest {

    @Test
    public void test1(){
        given()
                .relaxedHTTPSValidation()
                .when().get("https://untrusted-root.badssl.com/")
                .prettyPrint();


    }

    @Test
    public void keyStore(){
        given()
                .keyStore("pathoffile","password")
                .when().get("myapi");

    }
}