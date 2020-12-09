package DAY8;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import static io.restassured.RestAssured.baseURI;


public class SpartanFlow {

    int id;
    Map<String,Object> putRequestMap = new HashMap<>();
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");

    }

    @Test(priority = 1)
    public void POSTNewSpartan() {
        putRequestMap.put("name","GULNUR");
        putRequestMap.put("gender","Female");
        putRequestMap.put("phone",1231312321l);

        Response response=given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(putRequestMap)
                .when()
                .post("/api/spartans");

        id= response.path("data.id");

    }
    @Test(priority = 2)
    public void PUTExistingSpartan(){

        putRequestMap.put("name","EROL");
        putRequestMap.put("gender","Male");
        putRequestMap.put("phone",1231312321l);

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",id)//we are updating
                .and()
                .body(putRequestMap).
                when()
                .put("/api/spartans/{id}")
                .then().log().all()
                .assertThat().statusCode(204);
    }


    @Test(priority = 3)
    public void PATCHExistingSpartan(){
        putRequestMap.put("name","TJ");

        given().log().all()
                .and()
                .contentType(ContentType.JSON)
                .and()
                .pathParam("id",id)
                .and()
                .body(putRequestMap).
                when()
                .patch("/api/spartans/{id}") //PATCH NOT PUT
                .then().log().all()
                .assertThat().statusCode(204);
    }

    @Test(priority = 4)
    public void GETThatSpartan(){

        given().accept(ContentType.JSON)
                .and().pathParam("id",id).
                when().get("/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType(equalTo("application/json"))
                .and().assertThat().body("id",equalTo(id),
                "name",equalTo("TJ"),
                "gender",equalTo("Male"),
                "phone",equalTo(1231312321));
    }

    @Test(priority = 5)
    public void DELETEThatSpartan(){
        given().pathParam("id",id).when().
                delete("/api/spartans/{id}")
                .then()
                .statusCode(204).log().all();
    }

}
