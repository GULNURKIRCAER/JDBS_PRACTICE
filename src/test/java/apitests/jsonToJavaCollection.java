package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class jsonToJavaCollection {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void SpartanToMap(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);

        //we will convert json response to java map
        Map<String,Object> jsonDataMap = response.body().as(Map.class);
        //pom'a GSON EKLEDIK.VE CALISTI.YOKSA ERROR VERDI.

        System.out.println("jsonDataMap = " + jsonDataMap);

        String name = (String) jsonDataMap.get("name");
        assertEquals(name,"Meta");

        BigDecimal phone = new BigDecimal(String.valueOf(jsonDataMap.get("phone")));

        System.out.println("phone = " + phone);
    }
    @Test
    public void allSpartansToListOfMap() {
        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        assertEquals(response.statusCode(), 200);

        //response.prettyPrint();
        //we need to e-serialization full json body to list of map
        List<Map<String, Object>> allSpartanList = response.body().as(List.class);

        System.out.println("allSpartanList = " + allSpartanList);//LIST
        //System.out.println("jsonDataMap = " + allSpartanList.get(0));
        System.out.println(allSpartanList.get(0).get("name"));

        //save spartans 3in a map
        Map<String, Object> spartan3 = allSpartanList.get(2);
        System.out.println(spartan3);
        //boyle tek tek yapmak yerine toplu da yapilir

        int count = 1;
        for (Map<String, Object> map : allSpartanList) {
            System.out.println(count + ".list=" + map);
            count++;
        }
    }
    @Test
    public  void regionToMap(){

        Response response= when().get("http://52.90.239.48:1000/ords/hr/regions");


        assertEquals(response.statusCode(), 200);

        Map<String,Object> regionMap = response.body().as(Map.class);
        //pom'a GSON EKLEDIK.VE CALISTI.YOKSA ERROR VERDI.

        System.out.println( regionMap.get("count"));
        System.out.println(regionMap.get("hasMore"));
        System.out.println(regionMap.get("items"));//object FULL LIST

        List<Map<String, Object>> ITEMSList= (List<Map<String, Object>>) regionMap.get("items");

        System.out.println(ITEMSList.get(0));//first item
        System.out.println(ITEMSList.get(0).get("region_name"));


    }


    }
