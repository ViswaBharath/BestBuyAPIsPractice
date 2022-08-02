import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import static io.restassured.RestAssured.*;

public class UseCase1 {

    public static String expName = "Best Buy New Service Test";

      public static void main(String[] args) {

          //Create a New Service

    RestAssured.baseURI = "http://localhost:3030";
        String res =  given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"name\": \""+expName+"\"\n" +
                        "}")
                .when().post("/services")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(res);
        String actualName = js.get("name");
        System.out.println("Actual Name : " +actualName);
        Assert.assertEquals(actualName,expName);

    }
}