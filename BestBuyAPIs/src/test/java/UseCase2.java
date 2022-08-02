import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

// Create a new service, update it with a
// new name and retrieve service details to validate if the new service name is present in the json response body
public class UseCase2 {

    public static String updateName = "Best Buy New Service Test 4";
    public static String actualId;

    public static void main(String[] args) {

        // Part 1:
        RestAssured.baseURI = "http://localhost:3030";

        String res = given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"name\": \"" + updateName + "\"\n" +
                        "}")
                .when().post("/services")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(res);
        int id = js.getInt("id");
        actualId = Integer.toString(id);
        System.out.println("Actual Id : " + actualId);

        System.out.println("=================================================================");


        // Part 2:
        RestAssured.baseURI = "http://localhost:3030";

        String updateNameReq = given().log().all().header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"name\": \"" + updateName + "\"\n" +
                        "}")
                .when().put("/services/" + actualId + "")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println("=================================================================");

        // Part 3:
        RestAssured.baseURI = "http://localhost:3030";

        String getServices = given().log().all()
                .get("/services/" + actualId + "")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getServices);
        String actualName = js1.get("name");
        System.out.println("Actual Name : " + actualName);
        Assert.assertEquals(actualName, updateName);

    }

}