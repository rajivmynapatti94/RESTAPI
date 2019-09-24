package RESTAPIs;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import envPackage.ResponseConverter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResponseWithForLoop {
	
	
	@Test
	public void testing() {
	
	RestAssured.baseURI="https://maps.googleapis.com";
	
	Response res = given().
	param("location", "-33.8670522,151.1957362").
	param("radius", "1500").
	param("type", "restaurant").
	param("keyword", "cruise").
	param("key", "AIzaSyD1ufX1a5pt6wWusfU4BW6DXq4SSvaZLmI").log().all().
	
	when().
	get("/maps/api/place/nearbysearch/json").
	
	//then().log().all().
	then().
	assertThat().
	statusCode(200).and().contentType(ContentType.JSON).extract().response();
	
	JsonPath js = ResponseConverter.jsPath(res);
	
	int count = js.get("results.size");
	System.out.println("The count of the data in results array are " + count);
	
	for(int i=0; i<count; i++) {
		
		System.out.println("The name of the Restaurant is : " + js.get("results["+i+"].name"));
	}
		

}
}