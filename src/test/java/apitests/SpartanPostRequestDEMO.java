package apitests;

import DAY6_POJO.Spartan;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SpartanPostRequestDEMO {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test

    public void postWithString() {
        //SENDING json body as a string
       Response response= given().accept(ContentType.JSON).and().contentType(ContentType.JSON).body("{\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"name\": \"Michael\",\n" +
                "    \"phone\": 8984354643\n" +
                "}").when().post("/api/spartans/");

       response.prettyPrint();

        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json");

        assertEquals(response.path("success"),"A Spartan is Born!");

        JsonPath json=response.jsonPath();

        assertEquals(json.getString("data.name"),"Michael");
        assertEquals(json.getString("data.gender"),"Male");
        assertEquals(json.getString("data.phone"),"8984354643");

    }

    @Test
    public void PostMethodWithMap() {
        //create a map to be used as a body for post request
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("name","MichaelMAP");
        requestMap.put("gender","Male");
        requestMap.put("phone",8984354643L);

        Response response= given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON).
                body(requestMap).
                when().post("/api/spartans/");

        assertEquals(response.statusCode(),201);

        response.prettyPrint();

    }

    @Test
    public void PostWithPOJO() {

        //create spartan object and used as a body for post request
        Spartan spartan=new Spartan();
        spartan.setName("MikePOJO");
        spartan.setGender("Male");
        spartan.setPhone(1324356789L);

        Response response=given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON).
                and().body(spartan).
                when().post("/api/spartans/");

        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json");

        response.prettyPrint();

        //==============GET REQUEST==============

        Response response2= given().accept(ContentType.JSON).
                and().pathParam("id",121).
                and().when().get("/api/spartans/{id}");

        Spartan spartan1=response2.body().as(Spartan.class);

        System.out.println(spartan1.toString());


    }
}
