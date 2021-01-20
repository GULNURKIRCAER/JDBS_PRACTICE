package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class homework1 {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }

    /*
    ORDS API:
    - Given accept type is Json
    - Path param value- US
    - When users sends request to /countries
    - Then status code is 200
    - And Content - Type is Json
    - And country_id is US
    - And Country_name is United States of America
    - And Region_id is 2
    */

    @Test
    public void QA1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id","US")
                .when().get("/countries/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        JsonPath jsonPath = response.jsonPath();

        String country_id = jsonPath.getString("country_id");
        String country_name = jsonPath.getString("country_name");
        int region_id = jsonPath.getInt("region_id");

        System.out.println("country_id = " + country_id);
        System.out.println("country_name = " + country_name);
        System.out.println("region_id = " + region_id);

        assertEquals(country_id,"US");
        assertEquals(country_name,"United States of America");
        assertEquals(region_id,2);


    }
    /*
    ORDS API:
    - Given accept type is Json
    - Path param value- US
    - When users sends request to /countries
    - Then status code is 200
    - And Content - Type is Json
    - And country_id is US
    - And Country_name is United States of America
    - And Region_id is 2
    */
    @Test
    public void Q1WithHamcrest(){ //CHAIN WAY
        given().accept(ContentType.JSON)
                .and().pathParam("id","US").
                when().get("/countries/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType(equalTo("application/json"))
                .and().assertThat().body("country_id",equalTo("US"),
                "country_name",equalTo("United States of America"),
                "region_id",equalTo(2));


    }
        /*
        - Given accept type is Json
        - Query param value - q={"department_id":80}
        - When users sends request to /employees
        - Then status code is 200
        - And Content - Type is Json
        - And all job_ids start with 'SA'
        - And all department_ids are 80
        - Count is 25
        */
        @Test
        public void QA2(){

            Response response = given().accept(ContentType.JSON).and().queryParam("q","{\"department_id\":80}")
                    .when().get("/employees");

            assertEquals(response.statusCode(),200);

            assertEquals(response.contentType(),"application/json");

            JsonPath jsonPath = response.jsonPath();

            List<String> job_id =jsonPath.getList("items.job_id");
            for (String job_IDS : job_id) {
                assertTrue(job_IDS.startsWith("SA"));
            }
            List<Integer> department_id = jsonPath.getList("items.department_id");
            for (Integer department_IDS : department_id) {
                assertTrue(department_IDS.equals(80));
            }
            int count = jsonPath.getInt("count");
                assertEquals(count,25);
        }
    /*
    - Given accept type is Json
    - Query param value - q={"department_id":80}
    - When users sends request to /employees
    - Then status code is 200
    - And Content - Type is Json
    - And all job_ids start with 'SA'
    - And all department_ids are 80
    - Count is 25
    */
    @Test
    public void Q2WithHamcrest(){ //CHAIN WAY

        for(int i=0;i<25;i++) {
            given().accept(ContentType.JSON)
                    .and().queryParam("q", "{\"department_id\":80}").
                    when().get("/employees")
                    .then().statusCode(200)
                    .and().assertThat().contentType(equalTo("application/json"))
                    .and().assertThat().body("items.job_id["+i+"]", startsWith("SA"),
                    "items.department_id["+i+"]", equalTo(80),
                    "count", equalTo(25));

        }
    }

        /*
        Q3:
    - Given accept type is Json
    - Query param value q= region_id 3
    - When users sends request to /countries
    - Then status code is 200
    - And all regions_id is 3
    - And count is 6
    - And hasMore is false
    - And Country_name are;
        Australia,China,India,Japan,Malaysia,Singapore
         */

    @Test
    public void QA3(){
        Response response = given().accept(ContentType.JSON).and().queryParam("q","{\"region_id\":3}")
                .when().get("/countries");

        JsonPath jsonPath = response.jsonPath();
        List<String> country_names=new ArrayList<>(Arrays.asList("Australia","China","India","Japan","Malaysia","Singapore"));

        List<String> country_name = jsonPath.getList("items.findAll {it.region_id==3}.country_name");
        assertEquals(country_name,country_names);

        List<Integer> region_id = jsonPath.getList("items.region_id");
        for (Integer region_IDS : region_id) {
            assertTrue(region_IDS.equals(3));
        }

        int count = jsonPath.getInt("count");
        assertEquals(count,6);

        boolean hasMore = jsonPath.getBoolean("hasMore");
        assertEquals(hasMore,false);
    }
    /*
       Q3:
   - Given accept type is Json
   - Query param value q= region_id 3
   - When users sends request to /countries
   - Then status code is 200
   - And all regions_id is 3
   - And count is 6
   - And hasMore is false
   - And Country_name are;
       Australia,China,India,Japan,Malaysia,Singapore
        */
    @Test
    public void Q3WithHamcrest(){ //CHAIN WAY
        for(int i=0;i<6;i++) {
            given().accept(ContentType.JSON)
                    .and().queryParam("q", "{\"region_id\":3}").
                    when().get("/countries")
                    .then().statusCode(200)
                    .and().assertThat().contentType(equalTo("application/json"))
                    .and().assertThat().body("items.region_id[" + i + "]", equalTo(3),
                    "hasMore", equalTo(false),
                    "count", equalTo(6),"items.country_name",hasItems("Australia","China","India","Japan","Malaysia","Singapore"));
        }

    }
}
