package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class homework2 {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }
        /*
        SPARTAN API
        -Given accept type is json
        -And path param id is 20
        -When user sends a get request to "/api/spartans/{id}"
        -Then status code is 200
        -And content-type is "application/json;charset=UTF-8"
        -And response header contains Date
        -And Transfer-Encoding is chunked
        -And response payload values match the following:
        -id is 20,
        name is "Lothario",
        gender is "Male",
        phone is 7551551687
         */
        @Test
        public void QA1(){
            Response response = given().accept(ContentType.JSON)
                    .and().pathParam("id",20)
                    .when().get("/api/spartans/{id}");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json;charset=UTF-8");

            Assert.assertTrue(response.headers().hasHeaderWithName("Date"));

            Assert.assertTrue(response.getHeader("Transfer-Encoding").equals("chunked"));

            JsonPath jsonPath = response.jsonPath();
            int id = jsonPath.getInt("id");
            String name = jsonPath.getString("name");
            String  gender=jsonPath.getString("gender");
            long phone=jsonPath.getLong("phone");

            System.out.println("id = " + id);
            System.out.println("name = " + name);
            System.out.println("gender = " + gender);
            System.out.println("phone = " + phone);

            assertEquals(id,20);
            assertEquals(name,"Lothario");
            assertEquals(gender,"Male");
            assertEquals(phone,7551551687l);

        }
    /*
    Q2:
        -Given accept type is json
        -And query param gender = Female
        -And query param nameContains = r
        -When user sends a get request to "/api/spartans/search"
        -Then status code is 200
        -And content-type is "application/json;charset=UTF-8"
        -And all genders are Female
        -And all names contains r
        -And size is 20
        -And totalPages is 1
        -And sorted is false
     */
    @Test
    public void QA2(){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("gender","Female");
        queryMap.put("nameContains","r");

        Response response = given().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/api/spartans/search");

        assertEquals(response.statusCode(),200);

        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        JsonPath jsonPath = response.jsonPath();

        List<String> firstNames = jsonPath.getList("content.name");
        for (String firstName : firstNames) {
            Assert.assertTrue(firstName.contains("r") | firstName.contains("R"));
        }
        List<String> gender = jsonPath.getList("content.gender");
        for (String s : gender) {
            assertEquals(s,"Female");
        }

        int size=jsonPath.getInt("size");
        int totalPage=jsonPath.getInt("totalPages");
        boolean sorted=jsonPath.getBoolean("sort.sorted");

        Assert.assertEquals(size,20);
        Assert.assertEquals(totalPage,1);
        Assert.assertEquals(sorted,false);
    }

}
