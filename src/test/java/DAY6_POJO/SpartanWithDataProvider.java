package DAY6_POJO;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.ExcelUtil;
import io.restassured.http.ContentType;

import java.math.BigDecimal;

public class SpartanWithDataProvider {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @DataProvider
    public Object [][] spartanData(){

        ExcelUtil newSpartan = new ExcelUtil("src/test/resources/mockaroo.xlsx","Sheet1");
        Object[][] dataArray = newSpartan.getDataArrayWithoutFirstRow();
        return dataArray;
    }

    @Test(dataProvider = "spartanData")
    public void newTenSpartans(String name, String gender, String phone){

        long phoneFinal = new BigDecimal((String.valueOf(phone))).longValue();

        Spartan spartanEU = new Spartan();
        spartanEU.setName(name);
        spartanEU.setGender(gender);
        spartanEU.setPhone(phoneFinal);

        Response response1=
                given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when()
                .post("/api/spartans");


        int idFromPost = response1.path("data.id");
        System.out.println("id = " + idFromPost);

        given().accept(ContentType.JSON)
                .pathParam("id", idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();


    }
}


