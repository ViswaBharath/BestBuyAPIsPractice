import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;

public class UseCase3 {

    public static void main(String[] args) {

        // Retrieve a count of all the services and print IDs and the names of all the services

        RestAssured.baseURI = "http://localhost:3030";
        String getServices = given().log().all()
                .get("/services?$limit=50")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(getServices);
        String serviceCount = js.getString("total");
        System.out.println("Total count of all services : " + serviceCount);

        int count = js.getInt("limit");
        for (int i = 0; i < count; i++) {
            String id = js.getString("data[" + i + "].id");
            String name = js.getString("data[" + i + "].name");
            System.out.println("Id :" + id + "\n Name:" + name);

        }
        System.out.println("Response :" + getServices);

    }

}